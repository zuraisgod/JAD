package com.cleaningservice.dbaccess;

import com.cleaningservice.model.Feedback;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
public class FeedbackDAO {

    public boolean saveFeedback(Feedback feedback) {
        String sql = "INSERT INTO feedback (userId, bookingId, rating, comments) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, feedback.getUserId());
            stmt.setInt(2, feedback.getBookingId());
            stmt.setInt(3, feedback.getRating());
            stmt.setString(4, feedback.getComments());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
