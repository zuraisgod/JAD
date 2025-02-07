package com.cleaningservice.model;

import java.sql.Timestamp;

public class Booking {
    private int bookingID; // Primary key
    private int userID;    // Foreign key referencing users table
    private int serviceID; // Foreign key referencing services table
    private String status; // Booking status (Pending, Confirmed, Completed, Cancelled)
    private Timestamp bookingDate; // Date and time of the booking

    // Getters and Setters
    public int getBookingID() {
        return bookingID;
    }

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getServiceID() {
        return serviceID;
    }

    public void setServiceID(int serviceID) {
        this.serviceID = serviceID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Timestamp bookingDate) {
        this.bookingDate = bookingDate;
    }
}
