package com.cleaningservice.controller;

import com.cleaningservice.dbaccess.BookingDAO;
import com.cleaningservice.model.Booking;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "http://localhost:8080")
public class BookingController {

    private final BookingDAO bookingDAO = new BookingDAO();

    @PostMapping
    public String createBooking(@RequestParam int userID,
                                @RequestParam int serviceID,
                                @RequestParam String bookingDate) {
        // Create a new booking object
        Booking booking = new Booking();
        booking.setUserID(userID);
        booking.setServiceID(serviceID);
        booking.setStatus("Pending"); // Default status
        booking.setBookingDate(Timestamp.valueOf(bookingDate + " 00:00:00"));

        // Call DAO to save the booking
        boolean success = bookingDAO.createBooking(booking);

        if (success) {
            return "Booking created successfully!";
        } else {
            return "Failed to create booking.";
        }
    }
}
