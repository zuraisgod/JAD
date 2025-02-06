package com.cleaningservice.controller;

import com.cleaningservice.dbaccess.UserDAO;
import com.cleaningservice.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@CrossOrigin(origins = "http://localhost:8080") // Allow calls from JSP
@RestController
@RequestMapping("/api")
public class RegisterController {

    @Autowired
    private UserDAO userDAO;

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<String> register(@RequestParam("name") String name,
                                           @RequestParam("email") String email,
                                           @RequestParam("password") String password,
                                           @RequestParam("contactNumber") String contactNumber,
                                           @RequestParam("address") String address) {
        try {
            // Check if user already exists
            if (userDAO.getUserByEmail(email) != null) {
                return new ResponseEntity<>("Email already exists", HttpStatus.BAD_REQUEST);
            }

            // Hash password with SHA-256
            String hashedPassword = hashPassword(password);

            // Create new user object
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setPassword(hashedPassword);
            newUser.setRole("Member"); // Default role
            newUser.setUserId(0); // Auto-increment in DB

            // Save to database
            userDAO.createUser(newUser);

            return new ResponseEntity<>("User registered successfully", HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error processing request", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashedBytes = md.digest(password.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte b : hashedBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
