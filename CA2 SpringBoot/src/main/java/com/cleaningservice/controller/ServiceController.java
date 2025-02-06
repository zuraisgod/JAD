package com.cleaningservice.controller;

import com.cleaningservice.dbaccess.ServiceDAO;
import com.cleaningservice.model.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}
