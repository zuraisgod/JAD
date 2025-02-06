package com.cleaningservice.dbaccess;

import com.cleaningservice.model.Booking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BookingDAO {

    public boolean createBooking(Booking booking) {
        String sql = "INSERT INTO bookings (userID, serviceID, status, bookingDate) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, booking.getUserID());
            stmt.setInt(2, booking.getServiceID());
            stmt.setString(3, booking.getStatus());
            stmt.setTimestamp(4, booking.getBookingDate());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
