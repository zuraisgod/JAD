package com.cleaningservice.model;

public class ServiceCategory {
    private int categoryId;
    private String categoryName;
    private String description;
    private String imagePath;

    // Constructor
    public ServiceCategory(int categoryId, String categoryName, String description, String imagePath) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.description = description;
        this.imagePath = imagePath;
    }

    // Getters and setters
    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
