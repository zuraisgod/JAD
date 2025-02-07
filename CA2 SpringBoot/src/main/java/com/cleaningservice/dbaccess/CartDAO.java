package com.cleaningservice.dbaccess;

import com.cleaningservice.model.CartItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartDAO {

    // Add item to cart
    public boolean addToCart(int userId, int serviceId) {
        String cartSql = "INSERT INTO cart (userID) VALUES (?) ON DUPLICATE KEY UPDATE cartID = LAST_INSERT_ID(cartID)";
        String cartItemSql = "INSERT INTO cartitem (cartID, serviceID, quantity) VALUES (?, ?, 1) " +
                             "ON DUPLICATE KEY UPDATE quantity = quantity + 1";

        try (Connection conn = DBConnection.getConnection()) {
            // Start transaction
            conn.setAutoCommit(false);

            // Insert or update cart
            int cartId = 0;
            try (PreparedStatement cartStmt = conn.prepareStatement(cartSql, Statement.RETURN_GENERATED_KEYS)) {
                cartStmt.setInt(1, userId);
                cartStmt.executeUpdate();
                ResultSet rs = cartStmt.getGeneratedKeys();
                if (rs.next()) {
                    cartId = rs.getInt(1);
                }
            }

            // Insert or update cart item
            try (PreparedStatement cartItemStmt = conn.prepareStatement(cartItemSql)) {
                cartItemStmt.setInt(1, cartId);
                cartItemStmt.setInt(2, serviceId);
                cartItemStmt.executeUpdate();
            }

            // Commit transaction
            conn.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get all cart items for a user
    public List<CartItem> getCartItems(int userID) {
        String sql = "SELECT ci.ServiceID, s.ServiceName, SUM(ci.Quantity) AS Quantity, s.Price, " +
                     "SUM(ci.Quantity * s.Price) AS TotalPrice " +
                     "FROM cartitem ci " +
                     "JOIN cart c ON ci.CartID = c.CartID " +
                     "JOIN service s ON ci.ServiceID = s.ServiceID " +
                     "WHERE c.UserID = ? " +
                     "GROUP BY ci.ServiceID, s.ServiceName, s.Price";

        List<CartItem> cartItems = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                CartItem item = new CartItem();
                item.setServiceId(rs.getInt("ServiceID"));
                item.setServiceName(rs.getString("ServiceName"));
                item.setQuantity(rs.getInt("Quantity"));
                item.setPrice(rs.getDouble("Price"));
                item.setTotalPrice(rs.getDouble("TotalPrice"));
                cartItems.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cartItems;
    }

    // Clear the cart for a user
    public boolean clearCart(int userID) {
        String sql = "DELETE FROM cart WHERE userID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userID);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
