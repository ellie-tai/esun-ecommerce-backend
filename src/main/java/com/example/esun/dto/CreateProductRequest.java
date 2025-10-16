package com.example.esun.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateProductRequest {
    @NotBlank
    private String productId;
    @NotBlank
    private String productName;
    @Min(0)
    private int price;
    @Min(0)
    private int quantity;

    public String getProductId(){ return productId; }
    public void setProductId(String productId){ this.productId = productId; }
    public String getProductName(){ return productName; }
    public void setProductName(String productName){ this.productName = productName; }
    public int getPrice(){ return price; }
    public void setPrice(int price){ this.price = price; }
    public int getQuantity(){ return quantity; }
    public void setQuantity(int quantity){ this.quantity = quantity; }
}
