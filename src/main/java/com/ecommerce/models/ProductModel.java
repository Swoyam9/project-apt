package com.ecommerce.models;

import java.sql.Timestamp;

public class ProductModel {
    private int productId;
    private String name;
    private String description;
    private double price;
    private int stockQuantity;
    private int categoryId;
    private String categoryName;
    private String imageUrl;
    private String manufacturer;
    private String modelCompatibility;
    private Timestamp createdDate;

    public ProductModel() {}

    public ProductModel(String name, String description, double price, int stockQuantity, 
                        int categoryId, String manufacturer, String modelCompatibility) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.categoryId = categoryId;
        this.manufacturer = manufacturer;
        this.modelCompatibility = modelCompatibility;
    }

    // Getters and Setters
    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public int getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(int stockQuantity) { this.stockQuantity = stockQuantity; }
    public int getCategoryId() { return categoryId; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }
    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public String getManufacturer() { return manufacturer; }
    public void setManufacturer(String manufacturer) { this.manufacturer = manufacturer; }
    public String getModelCompatibility() { return modelCompatibility; }
    public void setModelCompatibility(String modelCompatibility) { this.modelCompatibility = modelCompatibility; }
    public Timestamp getCreatedDate() { return createdDate; }
    public void setCreatedDate(Timestamp createdDate) { this.createdDate = createdDate; }
}