package com.example.esun.dto;

import java.util.List;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public class CreateOrderRequest {
    @NotBlank
    private String memberId;
    @NotEmpty
    private List<OrderItemRequest> items;
    public String getMemberId(){ return memberId; }
    public void setMemberId(String memberId){ this.memberId = memberId; }
    public List<OrderItemRequest> getItems(){ return items; }
    public void setItems(List<OrderItemRequest> items){ this.items = items; }
}
