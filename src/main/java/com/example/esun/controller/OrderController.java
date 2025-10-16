package com.example.esun.controller;

import com.example.esun.dto.CreateOrderRequest;
import com.example.esun.dto.OrderResponse;
import com.example.esun.model.Order;
import com.example.esun.model.OrderItem;
import com.example.esun.repository.OrderRepository;
import com.example.esun.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final OrderRepository orderRepo;
    public OrderController(OrderService orderService, OrderRepository orderRepo){
        this.orderService = orderService;
        this.orderRepo = orderRepo;
    }

    @PostMapping
    public OrderResponse create(@Valid @RequestBody CreateOrderRequest req){
        return orderService.createOrder(req);
    }

    @GetMapping("/{orderId}")
    public Map<String, Object> get(@PathVariable String orderId){
        Order o = orderRepo.findById(orderId).orElse(null);
        List<OrderItem> items = orderRepo.findItemsByOrderId(orderId);
        Map<String, Object> m = new HashMap<>();
        m.put("order", o);
        m.put("items", items);
        return m;
    }
}
