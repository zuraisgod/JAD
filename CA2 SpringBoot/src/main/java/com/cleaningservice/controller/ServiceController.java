package com.cleaningservice.controller;

import com.cleaningservice.dbaccess.ServiceDAO;
import com.cleaningservice.model.Service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/services")

public class ServiceController {

    private final ServiceDAO serviceDAO = new ServiceDAO();

    @GetMapping("/{categoryId}")
    public List<Service> getServicesByCategory(@PathVariable int categoryId) {
        return serviceDAO.getServicesByCategory(categoryId);
    }


    @GetMapping
    public List<Service> getAllServices() {
        return serviceDAO.getAllServices();
    }
    @GetMapping("/service/{serviceId}")
    public ResponseEntity<Service> getServiceById(@PathVariable int serviceId) {
        Service service = serviceDAO.getServiceDetails(serviceId);
        if (service == null) {
            return ResponseEntity.notFound().build(); // Return 404 if not found
        }
        return ResponseEntity.ok(service); // Return service if found
    }

    
    @PutMapping("/{serviceId}")
    public String updateService(@PathVariable int serviceId, @RequestBody Service updatedService) {
        Service existingService = serviceDAO.getServiceDetails(serviceId);
        if (existingService == null) {
            return "Service not found";
        }

        existingService.setServiceName(updatedService.getServiceName());
        existingService.setDescription(updatedService.getDescription());
        existingService.setPrice(updatedService.getPrice());
        existingService.setImagePath(updatedService.getImagePath());
        existingService.setCategoryId(updatedService.getCategoryId());

        boolean success = serviceDAO.updateService(existingService);
        return success ? "Service updated successfully" : "Error updating service";
    }
    @PostMapping
    public String addService(@RequestBody Service service) {
        boolean isAdded = serviceDAO.addService(service.getCategoryId(), service.getServiceName(), service.getDescription(),
                service.getPrice(), service.getImagePath());
        if (isAdded) {
            return "Service added successfully";
        } else {
            return "Failed to add service";
        }
    }

    @DeleteMapping("/{id}")
    public String deleteService(@PathVariable int id) {
        boolean isDeleted = serviceDAO.deleteService(id);
        if (isDeleted) {
            return "Service deleted successfully";
        } else {
            return "Failed to delete service or service not found";
        }
    }
    @GetMapping("/search")
    public List<Service> searchServices(@RequestParam String keyword) {
        return serviceDAO.searchServices(keyword);
    }
    
}
