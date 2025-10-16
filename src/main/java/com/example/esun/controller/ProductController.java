package com.example.esun.controller;

import com.example.esun.dto.CreateProductRequest;
import com.example.esun.model.Product;
import com.example.esun.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService service;
    public ProductController(ProductService service){ this.service = service; }

    @GetMapping
    public List<Product> list(@RequestParam(name = "inStock", required = false) Boolean inStock){
        return service.listProducts(inStock);
    }

    @PostMapping
    public void create(@Valid @RequestBody CreateProductRequest req){
        service.createProduct(req);
    }
}
