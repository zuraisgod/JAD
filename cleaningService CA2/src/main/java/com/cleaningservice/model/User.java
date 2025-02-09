package com.cleaningservice.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class User {
    private int userId;
    private String name;
    private String email;
    private String contactNumber;
    private String address;
    private double totalValue;
    private String registrationDate; // Store as String
    private int totalActivity;

    // Getters and Setters
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public double getTotalValue() { return totalValue; }
    public void setTotalValue(double totalValue) { this.totalValue = totalValue; }

    public String getRegistrationDate() { return registrationDate; }
    
    public void setRegistrationDate(Timestamp timestamp) {
        if (timestamp != null) {
            this.registrationDate = timestamp.toLocalDateTime()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        } else {
            this.registrationDate = "N/A";
        }
    }

    public int getTotalActivity() { return totalActivity; }
    public void setTotalActivity(int totalActivity) { this.totalActivity = totalActivity; }
}