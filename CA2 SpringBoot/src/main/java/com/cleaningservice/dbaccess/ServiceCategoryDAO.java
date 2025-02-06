package com.cleaningservice.dbaccess;

import com.cleaningservice.model.ServiceCategory;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


@Repository
public class ServiceCategoryDAO {

    public List<ServiceCategory> getAllCategories() {
        List<ServiceCategory> categories = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String dbURL = "jdbc:mysql://localhost:3306/cleaningservice";
            String dbUser = "root";
            String dbPass = "root1234";
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(dbURL, dbUser, dbPass);

            String sql = "SELECT CategoryID, CategoryName, Description, ImagePath FROM servicecategory";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                categories.add(new ServiceCategory(
                    rs.getInt("CategoryID"),
                    rs.getString("CategoryName"),
                    rs.getString("Description"),
                    rs.getString("ImagePath")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ignored) {}
        }

        return categories;
    }

    public ServiceCategory getServiceCategoryById(int categoryId) {
        ServiceCategory category = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String dbURL = "jdbc:mysql://localhost:3306/cleaningservice";
            String dbUser = "root";
            String dbPass = "root1234";
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(dbURL, dbUser, dbPass);

            String sql = "SELECT CategoryID, CategoryName, Description, ImagePath FROM servicecategory WHERE CategoryID = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, categoryId);

            rs = stmt.executeQuery();
            if (rs.next()) {
                category = new ServiceCategory(
                    rs.getInt("CategoryID"),
                    rs.getString("CategoryName"),
                    rs.getString("Description"),
                    rs.getString("ImagePath")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ignored) {}
        }

        return category;
    }
    
}
