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
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:8080") // Allow calls from JSP
@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    private UserDAO userDAO;

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<Map<String, Object>> login(@RequestParam("email") String email,
                                                     @RequestParam("password") String password) {
        try {
            // Fetch user by email
            User user = userDAO.getUserByEmail(email);

            if (user != null) {
                // Hash the provided password using SHA-256
                String hashedPassword = hashPassword(password);

                // Compare the hashed passwords
                if (hashedPassword.equals(user.getPassword())) {
                    // Prepare response with userId and role
                    Map<String, Object> response = new HashMap<>();
                    response.put("userId", user.getUserId()); // Include userId
                    response.put("role", user.getRole());    // Include role
                    return new ResponseEntity<>(response, HttpStatus.OK);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // Return 401 Unauthorized if login fails
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Hashes a password using SHA-256.
     *
     * @param password The plaintext password to be hashed.
     * @return The hashed password as a hexadecimal string.
     * @throws NoSuchAlgorithmException If the SHA-256 algorithm is not available.
     */
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
