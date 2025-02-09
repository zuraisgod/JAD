package com.cleaningservice.controller;

import com.cleaningservice.dbaccess.BookingDAO;
import com.cleaningservice.dbaccess.BookingService;
import com.cleaningservice.model.Booking;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map; // Add this import statement!

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "http://localhost:8080")
public class BookingController {

	 @Autowired
	    private BookingService bookingService;
    private final BookingDAO bookingDAO = new BookingDAO();
    
    @PutMapping("/complete")
    public String completeBooking(@RequestBody Map<String, String> bookingDetails) {
        String userId = bookingDetails.get("userId");
        int serviceId = Integer.parseInt(bookingDetails.get("serviceId"));
        String dateTime = bookingDetails.get("dateTime");

        boolean success = bookingService.completeBooking(userId, serviceId, dateTime);
        return success ? "Booking completed successfully." : "Failed to complete booking.";
    }
    
    
    
    @GetMapping("/{userId}/completedBookings")
    public List<Map<String, Object>> getCompletedBookings(@PathVariable int userId) {
        return bookingDAO.getCompletedBookings(userId);
    }
    
    @GetMapping
    public List<Map<String, Object>> getAllBookings() {
        return bookingDAO.getAllBookingsWithDetails();
    }
    
    
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
    
    @GetMapping("/{bookingId}")
    public Map<String, Object> getBookingDetails(@PathVariable int bookingId) {
        return bookingDAO.getBookingDetails(bookingId);
    }

    
    @PutMapping("/{bookingId}/status")
    public ResponseEntity<String> updateBookingStatus(@PathVariable int bookingId, @RequestBody Map<String, String> requestBody) {
        String newStatus = requestBody.get("status");
        System.out.println("Booking ID: " + bookingId); // Debugging
        System.out.println("New Status: " + newStatus); // Debugging

        if (newStatus == null || newStatus.isEmpty()) {
            System.out.println("Invalid status provided."); // Debugging
            return ResponseEntity.badRequest().body("Invalid status.");
        }

        boolean success = bookingDAO.updateBookingStatus(bookingId, newStatus);
        if (success) {
            System.out.println("Status updated successfully in the database."); // Debugging
            return ResponseEntity.ok("Booking status updated successfully.");
        } else {
            System.out.println("Failed to update status in the database."); // Debugging
            return ResponseEntity.status(500).body("Failed to update booking status.");
        }
    }

}
