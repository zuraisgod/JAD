package com.cleaningservice.dbaccess;

import com.cleaningservice.model.Booking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map; // Add this import statement!
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.ArrayList;

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
    
    public List<Map<String, Object>> getAllBookingsWithDetails() {
        String sql = "SELECT b.bookingID, b.status, b.bookingDate, " +
                     "u.name AS userName, u.email AS userEmail, " +
                     "s.serviceName, s.price " +
                     "FROM bookings b " +
                     "JOIN users u ON b.userID = u.userID " +
                     "JOIN service s ON b.serviceID = s.serviceID";
        List<Map<String, Object>> bookings = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> booking = new HashMap<>();
                booking.put("bookingId", rs.getInt("bookingID"));
                booking.put("status", rs.getString("status"));
                booking.put("bookingDate", rs.getTimestamp("bookingDate"));
                booking.put("userName", rs.getString("userName"));
                booking.put("userEmail", rs.getString("userEmail"));
                booking.put("serviceName", rs.getString("serviceName"));
                booking.put("price", rs.getDouble("price"));
                bookings.add(booking);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }
    

    public List<Map<String, Object>> getCompletedBookings(int userId) {
        String sql = "SELECT b.bookingID, s.serviceName, b.status, b.bookingDate, s.price " +
                     "FROM bookings b " +
                     "JOIN service s ON b.serviceID = s.serviceID " +
                     "WHERE b.userID = ? AND b.status = 'Completed'";
        List<Map<String, Object>> bookings = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Map<String, Object> booking = new HashMap<>();
                booking.put("bookingId", rs.getInt("bookingID"));
                booking.put("serviceName", rs.getString("serviceName"));
                booking.put("status", rs.getString("status"));
                booking.put("bookingDate", rs.getDate("bookingDate"));
                booking.put("price", rs.getDouble("Price"));
                bookings.add(booking);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }
    
    public Map<String, Object> getBookingDetails(int bookingId) {
        String sql = "SELECT b.bookingID, b.status, b.bookingDate, b.specialRequests, " +
                     "u.name AS userName, u.email AS userEmail, " +
                     "s.serviceName, s.price " +
                     "FROM bookings b " +
                     "JOIN users u ON b.userID = u.userID " +
                     "JOIN service s ON b.serviceID = s.serviceID " +
                     "WHERE b.bookingID = ?";
        Map<String, Object> booking = new HashMap<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, bookingId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    booking.put("bookingId", rs.getInt("bookingID"));
                    booking.put("status", rs.getString("status"));
                    booking.put("bookingDate", rs.getTimestamp("bookingDate"));
                    booking.put("specialRequests", rs.getString("specialRequests"));
                    booking.put("userName", rs.getString("userName"));
                    booking.put("userEmail", rs.getString("userEmail"));
                    booking.put("serviceName", rs.getString("serviceName"));
                    booking.put("price", rs.getDouble("price"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return booking;
    }

    public boolean updateBookingStatus(int bookingId, String newStatus) {
        String sql = "UPDATE bookings SET status = ? WHERE bookingID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            System.out.println("Executing SQL: " + sql); // Debugging
            stmt.setString(1, newStatus);
            stmt.setInt(2, bookingId);
            int rowsUpdated = stmt.executeUpdate();

            System.out.println("Rows Updated: " + rowsUpdated); // Debugging
            return rowsUpdated > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
