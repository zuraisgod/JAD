package com.cleaningservice.controller;

import com.cleaningservice.dbaccess.UserDAO;
import com.cleaningservice.model.User;
import org.springframework.web.bind.annotation.*;
import java.util.Map; // Add this import statement!
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.cleaningservice.model.DeleteRequest;
import java.util.List;

@RestController
@RequestMapping("/api/users")

public class UserController {

    private final UserDAO userDAO = new UserDAO();

    @GetMapping("/{userID}")
    public User getUserDetails(@PathVariable int userID) {
        return userDAO.getUserByID(userID);
    }

    @PostMapping("/update/{userId}")
    public String updateUser(@PathVariable int userId, @RequestBody User user) {
        // Ensure the correct userId is used
        user.setUserId(userId);
        boolean success = userDAO.updateUser(user);
        return success ? "User details updated successfully!" : "Failed to update user details.";
    }
  
    @PostMapping("/changePassword/{userId}")
    public String changePassword(@PathVariable int userId, @RequestBody Map<String, String> request) {
        String oldPassword = request.get("oldPassword");
        String newPassword = request.get("newPassword");

        // Retrieve user from the database
        User user = userDAO.getUserByID(userId);
        if (user == null) {
            return "User not found.";
        }

        // Validate old password
        String hashedOldPassword = hashPassword(oldPassword);
        if (!user.getPassword().equals(hashedOldPassword)) {
            return "Incorrect old password.";
        }

        // Hash the new password
        String hashedNewPassword = hashPassword(newPassword);

        // Update password in the database
        boolean success = userDAO.updatePassword(userId, hashedNewPassword);
        return success ? "Password changed successfully!" : "Failed to update password.";
    }


    // Helper method for hashing passwords with SHA-256
    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    @PostMapping("/deleteAccount")
    public ResponseEntity<String> deleteAccount(@RequestBody DeleteRequest request) {
        try {
            User user = userDAO.getUserByID(request.getUserId());
            if (user.getPassword().equals(request.getPassword())) {
                userDAO.deleteUserByID(request.getUserId());
                return ResponseEntity.ok("Account deleted successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid password.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting account.");
        }
    }
    
    @GetMapping("/{userId}/bookings")
    public List<Map<String, Object>> getUserBookings(@PathVariable int userId) {
        return userDAO.getUserBookings(userId);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }
    @GetMapping("/members")
    public List<User> getAllMembers() {
        return userDAO.getAllMembers();
    }

}
