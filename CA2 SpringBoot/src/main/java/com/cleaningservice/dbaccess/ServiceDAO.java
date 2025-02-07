package com.cleaningservice.dbaccess;

import com.cleaningservice.model.Service;
import com.cleaningservice.model.ServiceCategory;

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
	    private final ServiceCategoryDAO serviceCategoryDAO = new ServiceCategoryDAO();

	    public Service getServiceDetails(int serviceId) {
	        String sql = "SELECT s.ServiceID, s.ServiceName, s.Description, s.Price, s.ImageURL, s.CategoryID " +
	                     "FROM Service s " +
	                     "WHERE s.ServiceID = ?";
	        Service service = null;

	        try (Connection conn = DBConnection.getConnection();
	             PreparedStatement stmt = conn.prepareStatement(sql)) {

	            stmt.setInt(1, serviceId);

	            try (ResultSet rs = stmt.executeQuery()) {
	                if (rs.next()) {
	                    service = new Service();
	                    service.setServiceId(rs.getInt("ServiceID"));
	                    service.setServiceName(rs.getString("ServiceName"));
	                    service.setDescription(rs.getString("Description"));
	                    service.setPrice(rs.getDouble("Price"));
	                    service.setImagePath(rs.getString("ImageURL"));
	                    service.setCategoryId(rs.getInt("CategoryID"));

	                    // Fetch category details using ServiceCategoryDAO
	                    ServiceCategory category = serviceCategoryDAO.getServiceCategoryById(service.getCategoryId());
	                    if (category != null) {
	                        service.setCategoryName(category.getCategoryName());
	                    }
	                }
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return service;
	    }
	    
	    public List<Service> getAllServices() {
	        List<Service> services = new ArrayList<>();
	        String sql = "SELECT ServiceID, ServiceName, Description, Price, ImageURL, CategoryID FROM Service";

	        try (Connection conn = DBConnection.getConnection();
	             PreparedStatement stmt = conn.prepareStatement(sql);
	             ResultSet rs = stmt.executeQuery()) {

	            while (rs.next()) {
	                Service service = new Service();
	                service.setServiceId(rs.getInt("ServiceID"));
	                service.setServiceName(rs.getString("ServiceName"));
	                service.setDescription(rs.getString("Description"));
	                service.setPrice(rs.getDouble("Price"));
	                service.setImagePath(rs.getString("ImageURL"));
	                service.setCategoryId(rs.getInt("CategoryID"));

	                // Fetch category details using ServiceCategoryDAO
	                ServiceCategory category = serviceCategoryDAO.getServiceCategoryById(service.getCategoryId());
	                if (category != null) {
	                    service.setCategoryName(category.getCategoryName()); // Set category name
	                }
	                services.add(service);
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return services;
	    }
	    public boolean updateService(Service service) {
	        String query = "UPDATE Service SET ServiceName = ?, Description = ?, Price = ?, ImageURL = ?, CategoryID = ? WHERE ServiceID = ?";
	        
	        try (Connection conn = DBConnection.getConnection();
	             PreparedStatement stmt = conn.prepareStatement(query)) {
	            
	            stmt.setString(1, service.getServiceName());
	            stmt.setString(2, service.getDescription());
	            stmt.setDouble(3, service.getPrice());
	            stmt.setString(4, service.getImagePath());
	            stmt.setInt(5, service.getCategoryId());
	            stmt.setInt(6, service.getServiceId());
	            
	            int rowsUpdated = stmt.executeUpdate();
	            return rowsUpdated > 0;

	        } catch (SQLException e) {
	            e.printStackTrace();
	            return false;
	        }
	    }


}
