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

    @PostMapping("/add")
    public String addToCart(@RequestParam int userID, @RequestParam int serviceID) {
        boolean success = cartDAO.addToCart(userID, serviceID);

        if (success) {
            return "Item successfully added to cart!";
        } else {
            return "Failed to add item to cart.";
        }
    }

    @GetMapping("/items")
    public List<CartItem> getCartItems(@RequestParam int userID) {
        return cartDAO.getCartItems(userID);
    }

}
