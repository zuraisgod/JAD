package com.cleaningservice.model;

public class CartItem {
    private int cartId;         // ID of the cart
    private String serviceName; // Name of the service
    private int quantity;       // Quantity of the service added to the cart
    private double price;       // Price of the service
    private double totalPrice;  // Total price for the quantity of the service

    // Getter and Setter for cartId
    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    // Getter and Setter for serviceName
    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    // Getter and Setter for quantity
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Getter and Setter for price
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    // Getter and Setter for totalPrice
    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    // Optional: Override toString() for easier debugging
    @Override
    public String toString() {
        return "CartItem{" +
                "cartId=" + cartId +
                ", serviceName='" + serviceName + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
