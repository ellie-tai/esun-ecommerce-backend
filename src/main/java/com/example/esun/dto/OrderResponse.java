package com.example.esun.dto;

import java.util.List;

public class OrderResponse {
    public static class Item {
        public String productId;
        public String productName;
        public int unitPrice;
        public int quantity;
        public int itemPrice;
    }
    private String orderId;
    private String memberId;
    private int totalPrice;
    private List<Item> items;

    public String getOrderId(){ return orderId; }
    public void setOrderId(String orderId){ this.orderId = orderId; }
    public String getMemberId(){ return memberId; }
    public void setMemberId(String memberId){ this.memberId = memberId; }
    public int getTotalPrice(){ return totalPrice; }
    public void setTotalPrice(int totalPrice){ this.totalPrice = totalPrice; }
    public List<Item> getItems(){ return items; }
    public void setItems(List<Item> items){ this.items = items; }
}
