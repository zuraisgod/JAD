package com.cleaningservice.dbaccess;

import com.cleaningservice.model.Report;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReportDAO {
    private static final String INSERT_REPORT = "INSERT INTO reports (bookingID, userID, issueType, details, status, createdAt, resolvedAt) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String GET_ALL_REPORTS = "SELECT * FROM reports";
    private static final String GET_REPORT_BY_USER = "SELECT * FROM reports WHERE userID = ?";
    private static final String GET_REPORT_BY_BOOKING = "SELECT * FROM reports WHERE bookingID = ?";
    private static final String DELETE_REPORT = "DELETE FROM reports WHERE reportID = ?";

    public boolean addReport(Report report) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_REPORT)) {
            stmt.setInt(1, report.getBookingID());
            stmt.setInt(2, report.getUserID());
            stmt.setString(3, report.getIssueType());
            stmt.setString(4, report.getDetails());
            stmt.setString(5, report.getStatus());
            stmt.setTimestamp(6, Timestamp.valueOf(report.getCreatedAt()));
            stmt.setTimestamp(7, report.getResolvedAt() != null ? Timestamp.valueOf(report.getResolvedAt()) : null);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Report> getAllReports() {
        List<Report> reports = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(GET_ALL_REPORTS);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                reports.add(new Report(
                        rs.getInt("reportID"),
                        rs.getInt("bookingID"),
                        rs.getInt("userID"),
                        rs.getString("issueType"),
                        rs.getString("details"),
                        rs.getString("status"),
                        rs.getTimestamp("createdAt").toLocalDateTime(),
                        rs.getTimestamp("resolvedAt") != null ? rs.getTimestamp("resolvedAt").toLocalDateTime() : null
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reports;
    }

    public List<Report> getReportByUserId(int userId) {
        List<Report> reports = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(GET_REPORT_BY_USER)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                reports.add(new Report(
                        rs.getInt("reportID"),
                        rs.getInt("bookingID"),
                        rs.getInt("userID"),
                        rs.getString("issueType"),
                        rs.getString("details"),
                        rs.getString("status"),
                        rs.getTimestamp("createdAt").toLocalDateTime(),
                        rs.getTimestamp("resolvedAt") != null ? rs.getTimestamp("resolvedAt").toLocalDateTime() : null
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reports;
    }

    public List<Report> getReportByBookingId(int bookingId) {
        List<Report> reports = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(GET_REPORT_BY_BOOKING)) {
            stmt.setInt(1, bookingId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                reports.add(new Report(
                        rs.getInt("reportID"),
                        rs.getInt("bookingID"),
                        rs.getInt("userID"),
                        rs.getString("issueType"),
                        rs.getString("details"),
                        rs.getString("status"),
                        rs.getTimestamp("createdAt").toLocalDateTime(),
                        rs.getTimestamp("resolvedAt") != null ? rs.getTimestamp("resolvedAt").toLocalDateTime() : null
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reports;
    }

    public boolean deleteReport(int reportID) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE_REPORT)) {
            stmt.setInt(1, reportID);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
