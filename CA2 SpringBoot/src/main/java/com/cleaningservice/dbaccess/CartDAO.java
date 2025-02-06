package com.cleaningservice.dbaccess;

import com.cleaningservice.model.CartItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartDAO {
    public boolean addToCart(int userId, int serviceId) {
        String sql = "INSERT INTO cart (userID, serviceID, quantity) VALUES (?, ?, 1) " +
                     "ON DUPLICATE KEY UPDATE quantity = quantity + 1";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, serviceId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<CartItem> getCartItems(int userID) {
        String sql = "SELECT c.cartID, c.quantity, s.ServiceName, s.Price, (c.quantity * s.Price) AS totalPrice " +
                     "FROM cart c JOIN service s ON c.serviceID = s.ServiceID WHERE c.userID = ?";
        List<CartItem> cartItems = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
            	CartItem item = new CartItem();
                item.setCartId(rs.getInt("cartID"));
                item.setServiceName(rs.getString("ServiceName"));
                item.setQuantity(rs.getInt("quantity"));
                item.setPrice(rs.getDouble("Price"));
                item.setTotalPrice(rs.getDouble("totalPrice"));
                cartItems.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cartItems;
    }
}
