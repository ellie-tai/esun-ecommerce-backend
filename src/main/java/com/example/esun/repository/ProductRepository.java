package com.example.esun.repository;

import com.example.esun.model.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepository {
    private final JdbcTemplate jdbc;
    private final RowMapper<Product> mapper = (rs, rn) -> {
        Product p = new Product();
        p.setProductId(rs.getString("product_id"));
        p.setProductName(rs.getString("product_name"));
        p.setPrice(rs.getInt("price"));
        p.setQuantity(rs.getInt("quantity"));
        return p;
    };

    public ProductRepository(JdbcTemplate jdbc){ this.jdbc = jdbc; }

    public List<Product> findAll(boolean onlyInStock){
        if(onlyInStock){
            return jdbc.query("SELECT product_id, product_name, price, quantity FROM products WHERE quantity > 0 ORDER BY product_id", mapper);
        } else {
            return jdbc.query("SELECT product_id, product_name, price, quantity FROM products ORDER BY product_id", mapper);
        }
    }

    public Optional<Product> findById(String id){
        List<Product> list = jdbc.query("SELECT product_id, product_name, price, quantity FROM products WHERE product_id=?",
                mapper, id);
        return list.stream().findFirst();
    }

    public void insert(Product p){
        jdbc.update("INSERT INTO products(product_id, product_name, price, quantity) VALUES (?,?,?,?)",
                p.getProductId(), p.getProductName(), p.getPrice(), p.getQuantity());
    }

    public void updateQuantity(String productId, int newQty){
        jdbc.update("UPDATE products SET quantity=? WHERE product_id=?", newQty, productId);
    }
}
