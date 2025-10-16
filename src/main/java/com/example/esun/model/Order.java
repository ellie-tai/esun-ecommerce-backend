package com.example.esun.model;

import java.time.OffsetDateTime;

public class Order {
    private String orderId;
    private String memberId;
    private int totalPrice;
    private int payStatus; // 0=unpaid,1=paid
    private OffsetDateTime createdAt;

    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }
    public String getMemberId() { return memberId; }
    public void setMemberId(String memberId) { this.memberId = memberId; }
    public int getTotalPrice() { return totalPrice; }
    public void setTotalPrice(int totalPrice) { this.totalPrice = totalPrice; }
    public int getPayStatus() { return payStatus; }
    public void setPayStatus(int payStatus) { this.payStatus = payStatus; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
}
