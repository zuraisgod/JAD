package com.cleaningservice.dbaccess;

import com.cleaningservice.model.Inquiry;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InquiryDAO {
    private static final String INSERT_INQUIRY = "INSERT INTO inquiries (userID, serviceID, subject, message, status, createdAt, resolvedAt) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_INQUIRY = "UPDATE inquiries SET userID = ?, serviceID = ?, subject = ?, message = ?, status = ?, createdAt = ?, resolvedAt = ? WHERE inquiryID = ?";
    private static final String GET_ALL_INQUIRIES = "SELECT * FROM inquiries";
    private static final String GET_INQUIRY_BY_USER = "SELECT * FROM inquiries WHERE userID = ?";
    private static final String DELETE_INQUIRY = "DELETE FROM inquiries WHERE inquiryID = ?";
    private static final String SEARCH_INQUIRY = "SELECT * FROM inquiries WHERE subject LIKE ? OR message LIKE ?";

    public boolean addInquiry(Inquiry inquiry) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_INQUIRY)) {
            stmt.setInt(1, inquiry.getUserID());
            stmt.setInt(2, inquiry.getServiceID());
            stmt.setString(3, inquiry.getSubject());
            stmt.setString(4, inquiry.getMessage());
            stmt.setString(5, inquiry.getStatus());
            stmt.setTimestamp(6, Timestamp.valueOf(inquiry.getCreatedAt()));
            stmt.setTimestamp(7, inquiry.getResolvedAt() != null ? Timestamp.valueOf(inquiry.getResolvedAt()) : null);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Inquiry> getAllInquiries() {
        List<Inquiry> inquiries = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(GET_ALL_INQUIRIES);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                inquiries.add(new Inquiry(
                        rs.getInt("inquiryID"),
                        rs.getInt("userID"),
                        rs.getInt("serviceID"),
                        rs.getString("subject"),
                        rs.getString("message"),
                        rs.getString("status"),
                        rs.getTimestamp("createdAt").toLocalDateTime(),
                        rs.getTimestamp("resolvedAt") != null ? rs.getTimestamp("resolvedAt").toLocalDateTime() : null
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return inquiries;
    }

    public List<Inquiry> getInquiryByUserId(int userId) {
        List<Inquiry> inquiries = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(GET_INQUIRY_BY_USER)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                inquiries.add(new Inquiry(
                        rs.getInt("inquiryID"),
                        rs.getInt("userID"),
                        rs.getInt("serviceID"),
                        rs.getString("subject"),
                        rs.getString("message"),
                        rs.getString("status"),
                        rs.getTimestamp("createdAt").toLocalDateTime(),
                        rs.getTimestamp("resolvedAt") != null ? rs.getTimestamp("resolvedAt").toLocalDateTime() : null
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return inquiries;
    }

    public boolean putInquiry(int inquiryID, Inquiry inquiry) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_INQUIRY)) {
            stmt.setInt(1, inquiry.getUserID());
            stmt.setInt(2, inquiry.getServiceID());
            stmt.setString(3, inquiry.getSubject());
            stmt.setString(4, inquiry.getMessage());
            stmt.setString(5, inquiry.getStatus());
            stmt.setTimestamp(6, Timestamp.valueOf(inquiry.getCreatedAt()));
            stmt.setTimestamp(7, inquiry.getResolvedAt() != null ? Timestamp.valueOf(inquiry.getResolvedAt()) : null);
            stmt.setInt(8, inquiryID);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteInquiry(int inquiryID) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE_INQUIRY)) {
            stmt.setInt(1, inquiryID);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Inquiry> searchInquiry(String keyword) {
        List<Inquiry> inquiries = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SEARCH_INQUIRY)) {
            String searchKeyword = "%" + keyword + "%";
            stmt.setString(1, searchKeyword);
            stmt.setString(2, searchKeyword);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                inquiries.add(new Inquiry(
                        rs.getInt("inquiryID"),
                        rs.getInt("userID"),
                        rs.getInt("serviceID"),
                        rs.getString("subject"),
                        rs.getString("message"),
                        rs.getString("status"),
                        rs.getTimestamp("createdAt").toLocalDateTime(),
                        rs.getTimestamp("resolvedAt") != null ? rs.getTimestamp("resolvedAt").toLocalDateTime() : null
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return inquiries;
    }
    private static final String GET_INQUIRY_BY_SERVICE = "SELECT * FROM inquiries WHERE serviceID = ?";

    public List<Inquiry> getInquiryByServiceId(int serviceId) {
        List<Inquiry> inquiries = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(GET_INQUIRY_BY_SERVICE)) {
            stmt.setInt(1, serviceId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                inquiries.add(new Inquiry(
                        rs.getInt("inquiryID"),
                        rs.getInt("userID"),
                        rs.getInt("serviceID"),
                        rs.getString("subject"),
                        rs.getString("message"),
                        rs.getString("status"),
                        rs.getTimestamp("createdAt").toLocalDateTime(),
                        rs.getTimestamp("resolvedAt") != null ? rs.getTimestamp("resolvedAt").toLocalDateTime() : null
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return inquiries;
    }

}
