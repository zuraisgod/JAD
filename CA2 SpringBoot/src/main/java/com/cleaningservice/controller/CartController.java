package com.cleaningservice.controller;

import com.cleaningservice.dbaccess.CartDAO;
import com.cleaningservice.model.CartItem;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "http://localhost:8080")
public class CartController {

    private final CartDAO cartDAO = new CartDAO();

    // Add item to cart
    @PostMapping("/add")
    public String addToCart(@RequestParam int userID, @RequestParam int serviceID) {
        boolean success = cartDAO.addToCart(userID, serviceID);
        return success ? "Item successfully added to cart!" : "Failed to add item to cart.";
    }

    // Get all cart items for a user
    @GetMapping("/items/{userID}")
    public List<CartItem> getCartItems(@PathVariable int userID) {
        return cartDAO.getCartItems(userID);
    }

    // Clear the cart for a user
    @DeleteMapping("/clear/{userID}")
    public String clearCart(@PathVariable int userID) {
        boolean success = cartDAO.clearCart(userID);
        return success ? "Cart cleared successfully!" : "Failed to clear the cart.";
    }
}
