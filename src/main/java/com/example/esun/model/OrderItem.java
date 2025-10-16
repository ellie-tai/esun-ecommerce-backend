package com.example.esun.model;

public class OrderItem {
    private long orderItemSn;
    private String orderId;
    private String productId;
    private int quantity;
    private int unitPrice;
    private int itemPrice;

    public long getOrderItemSn(){ return orderItemSn; }
    public void setOrderItemSn(long orderItemSn){ this.orderItemSn = orderItemSn; }
    public String getOrderId(){ return orderId; }
    public void setOrderId(String orderId){ this.orderId = orderId; }
    public String getProductId(){ return productId; }
    public void setProductId(String productId){ this.productId = productId; }
    public int getQuantity(){ return quantity; }
    public void setQuantity(int quantity){ this.quantity = quantity; }
    public int getUnitPrice(){ return unitPrice; }
    public void setUnitPrice(int unitPrice){ this.unitPrice = unitPrice; }
    public int getItemPrice(){ return itemPrice; }
    public void setItemPrice(int itemPrice){ this.itemPrice = itemPrice; }
}
