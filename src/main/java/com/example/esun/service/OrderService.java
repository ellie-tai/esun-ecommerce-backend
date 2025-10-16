package com.example.esun.service;

import com.example.esun.dto.CreateOrderRequest;
import com.example.esun.dto.OrderResponse;
import com.example.esun.exception.ApiException;
import com.example.esun.model.Order;
import com.example.esun.model.OrderItem;
import com.example.esun.model.Product;
import com.example.esun.repository.OrderRepository;
import com.example.esun.repository.ProductRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService {

    private final ProductRepository productRepo;
    private final OrderRepository orderRepo;
    private final JdbcTemplate jdbc;

    public OrderService(ProductRepository productRepo, OrderRepository orderRepo, JdbcTemplate jdbc){
        this.productRepo = productRepo;
        this.orderRepo = orderRepo;
        this.jdbc = jdbc;
    }

    private String newOrderId(){
        // MsYYYYMMDDhhmmss + random 4 digits to resemble the sample
        OffsetDateTime now = OffsetDateTime.now(ZoneOffset.ofHours(8));
        String base = String.format(Locale.ROOT, "Ms%04d%02d%02d%02d%02d%02d",
                now.getYear(), now.getMonthValue(), now.getDayOfMonth(),
                now.getHour(), now.getMinute(), now.getSecond());
        String rnd = String.valueOf(Math.abs(UUID.randomUUID().hashCode())).substring(0,4);
        return base + rnd;
    }

    @Transactional
    public OrderResponse createOrder(CreateOrderRequest req){
        if(req.getItems()==null || req.getItems().isEmpty()){
            throw new ApiException("訂單至少需要 1 個品項");
        }
        // Reserve stock via stored procedure per item, and collect pricing
        List<OrderItem> items = new ArrayList<>();
        int total = 0;

        for(var it : req.getItems()){
            Optional<Product> pOpt = productRepo.findById(it.getProductId());
            Product p = pOpt.orElseThrow(() -> new ApiException("找不到商品: " + it.getProductId()));

            // Call stored procedure sp_reserve_stock(product_id, qty) -> boolean ok
            Boolean ok = jdbc.queryForObject("CALL sp_reserve_stock(?, ?)", Boolean.class, p.getProductId(), it.getQuantity());
            if(ok == null || !ok){
                throw new ApiException("庫存不足或佔用失敗: " + p.getProductId());
            }
            int itemPrice = p.getPrice() * it.getQuantity();
            OrderItem oi = new OrderItem();
            oi.setProductId(p.getProductId());
            oi.setQuantity(it.getQuantity());
            oi.setUnitPrice(p.getPrice());
            oi.setItemPrice(itemPrice);
            items.add(oi);
            total += itemPrice;
        }
        Order order = new Order();
        order.setOrderId(newOrderId());
        order.setMemberId(req.getMemberId());
        order.setTotalPrice(total);
        order.setPayStatus(0);
        order.setCreatedAt(OffsetDateTime.now(ZoneOffset.ofHours(8)));
        orderRepo.insertOrder(order);
        for(OrderItem i: items){ i.setOrderId(order.getOrderId()); }
        orderRepo.insertItems(items);

        // Build response
        OrderResponse res = new OrderResponse();
        res.setOrderId(order.getOrderId());
        res.setMemberId(order.getMemberId());
        res.setTotalPrice(order.getTotalPrice());
        List<OrderResponse.Item> resItems = new ArrayList<>();
        for(OrderItem i: items){
            OrderResponse.Item ri = new OrderResponse.Item();
            ri.productId = i.getProductId();
            ri.productName = productRepo.findById(i.getProductId()).map(Product::getProductName).orElse("");
            ri.unitPrice = i.getUnitPrice();
            ri.quantity = i.getQuantity();
            ri.itemPrice = i.getItemPrice();
            resItems.add(ri);
        }
        res.setItems(resItems);
        return res;
    }
}
