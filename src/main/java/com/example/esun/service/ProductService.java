package com.example.esun.service;

import com.example.esun.dto.CreateProductRequest;
import com.example.esun.exception.ApiException;
import com.example.esun.model.Product;
import com.example.esun.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository repo;
    public ProductService(ProductRepository repo){ this.repo = repo; }

    public List<Product> listProducts(Boolean inStock){
        return repo.findAll(inStock != null && inStock);
    }

    public void createProduct(CreateProductRequest req){
        if(repo.findById(req.getProductId()).isPresent()){
            throw new ApiException("商品編號已存在: " + req.getProductId());
        }
        Product p = new Product(req.getProductId(), req.getProductName(), req.getPrice(), req.getQuantity());
        repo.insert(p);
    }
}
