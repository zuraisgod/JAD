package com.cleaningservice.dbaccess;

import com.cleaningservice.model.Service;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceDAO {
	public List<Service> getServicesByCategory(int categoryId) {
	    List<Service> services = new ArrayList<>();
	    String sql = "SELECT ServiceID, ServiceName, Description, Price, ImageURL FROM Service WHERE CategoryID = ?";
	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setInt(1, categoryId);
	        ResultSet rs = stmt.executeQuery();
	        while (rs.next()) {
	            Service service = new Service();
	            service.setServiceId(rs.getInt("ServiceID"));
	            service.setServiceName(rs.getString("ServiceName"));
	            service.setDescription(rs.getString("Description"));
	            service.setPrice(rs.getDouble("Price"));
	            service.setImagePath(rs.getString("ImageURL")); // Correct mapping
	            services.add(service);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return services;
	}
	  public double getServicePrice(int serviceId) {
	        String sql = "SELECT Price FROM services WHERE ServiceID = ?";
	        double price = 0;

	        try (Connection conn = DBConnection.getConnection();
	             PreparedStatement stmt = conn.prepareStatement(sql)) {

	            stmt.setInt(1, serviceId);
	            try (ResultSet rs = stmt.executeQuery()) {
	                if (rs.next()) {
	                    price = rs.getDouble("Price");
	                }
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return price;
	    }


}
