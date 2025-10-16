package com.example.esun.repository;

import com.example.esun.model.Order;
import com.example.esun.model.OrderItem;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class OrderRepository {
    private final JdbcTemplate jdbc;
    public OrderRepository(JdbcTemplate jdbc){ this.jdbc = jdbc; }

    private final RowMapper<Order> orderMapper = (rs, rn) -> {
        Order o = new Order();
        o.setOrderId(rs.getString("order_id"));
        o.setMemberId(rs.getString("member_id"));
        o.setTotalPrice(rs.getInt("total_price"));
        o.setPayStatus(rs.getInt("pay_status"));
        o.setCreatedAt(rs.getObject("created_at", OffsetDateTime.class));
        return o;
    };

    private final RowMapper<OrderItem> itemMapper = (rs, rn) -> {
        OrderItem i = new OrderItem();
        i.setOrderItemSn(rs.getLong("order_item_sn"));
        i.setOrderId(rs.getString("order_id"));
        i.setProductId(rs.getString("product_id"));
        i.setQuantity(rs.getInt("quantity"));
        i.setUnitPrice(rs.getInt("unit_price"));
        i.setItemPrice(rs.getInt("item_price"));
        return i;
    };

    public void insertOrder(Order o){
        jdbc.update("INSERT INTO orders(order_id, member_id, total_price, pay_status, created_at) VALUES (?,?,?,?, ?)",
                o.getOrderId(), o.getMemberId(), o.getTotalPrice(), o.getPayStatus(), o.getCreatedAt());
    }

    public void insertItems(List<OrderItem> items){
        jdbc.batchUpdate("INSERT INTO order_items(order_id, product_id, quantity, unit_price, item_price) VALUES (?,?,?,?,?)",
                new BatchPreparedStatementSetter() {
                    @Override public void setValues(PreparedStatement ps, int i) throws SQLException {
                        OrderItem it = items.get(i);
                        ps.setString(1, it.getOrderId());
                        ps.setString(2, it.getProductId());
                        ps.setInt(3, it.getQuantity());
                        ps.setInt(4, it.getUnitPrice());
                        ps.setInt(5, it.getItemPrice());
                    }
                    @Override public int getBatchSize() { return items.size(); }
                });
    }

    public Optional<Order> findById(String id){
        List<Order> list = jdbc.query("SELECT order_id, member_id, total_price, pay_status, created_at FROM orders WHERE order_id=?", orderMapper, id);
        return list.stream().findFirst();
    }

    public List<OrderItem> findItemsByOrderId(String id){
        return jdbc.query("SELECT order_item_sn, order_id, product_id, quantity, unit_price, item_price FROM order_items WHERE order_id=? ORDER BY order_item_sn", itemMapper, id);
    }
}
