package com.cleaningservice.model;
public class Service {
    private int serviceId;
    private String serviceName;
    private String description;
    private double price;
    private String imagePath; // Matches the logic in DAO

    // Getters and Setters
    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImagePath() {
        return imagePath; // Updated getter name
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath; // Updated setter name
    }
}
