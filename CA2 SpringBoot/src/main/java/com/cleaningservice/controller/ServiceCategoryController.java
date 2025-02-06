package com.cleaningservice.controller;

import com.cleaningservice.dbaccess.ServiceCategoryDAO;
import com.cleaningservice.model.ServiceCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8080") // Allow requests from JSP
@RestController
@RequestMapping("/api")
public class ServiceCategoryController {

    @Autowired
    private ServiceCategoryDAO serviceCategoryDAO;

    @GetMapping("/categories")
    public List<ServiceCategory> getAllCategories() {
        return serviceCategoryDAO.getAllCategories();
    }
}
