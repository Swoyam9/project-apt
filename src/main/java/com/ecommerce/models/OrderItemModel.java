package com.ecommerce.models;

public class OrderItemModel {
    private int orderItemId;
    private int orderId;
    private int productId;
    private String productName;
    private int quantity;
    private double priceAtTime;
    private double subtotal;

    public OrderItemModel() {}

    public OrderItemModel(int productId, int quantity, double priceAtTime) {
        this.productId = productId;
        this.quantity = quantity;
        this.priceAtTime = priceAtTime;
    }

    // Getters and Setters
    public int getOrderItemId() { return orderItemId; }
    public void setOrderItemId(int orderItemId) { this.orderItemId = orderItemId; }
    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }
    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public double getPriceAtTime() { return priceAtTime; }
    public void setPriceAtTime(double priceAtTime) { this.priceAtTime = priceAtTime; }
    public double getSubtotal() { return quantity * priceAtTime; }
}