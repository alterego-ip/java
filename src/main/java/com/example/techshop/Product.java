package com.example.techshop;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Product {
    private int id;
    private String name;
    private String brand;
    private String category;
    private int quantity;
    private double price;

    public Product() {} 

    public Product(int id, String name, String brand, String category, int quantity, double price) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.category = category;
        this.quantity = quantity;
        this.price = price;
    }

    @JsonProperty
    public int getId() { return id; }
    @JsonProperty
    public String getName() { return name; }
    @JsonProperty
    public String getBrand() { return brand; }
    @JsonProperty
    public String getCategory() { return category; }
    @JsonProperty
    public int getQuantity() { return quantity; }
    @JsonProperty
    public double getPrice() { return price; }
}