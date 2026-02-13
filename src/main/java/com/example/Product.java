package com.example;

public class Product {
    private int id;
    private String name;
    private String brand;
    private String category;
    private int quantity;
    private double price;

    public Product(int id, String name, String brand, String category, int quantity, double price) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.category = category;
        this.quantity = quantity;
        this.price = price;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getBrand() { return brand; }
    public String getCategory() { return category; }
    public int getQuantity() { return quantity; }
    public double getPrice() { return price; }
}