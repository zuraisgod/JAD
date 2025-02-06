package com.cleaningservice.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users") // http://localhost:8080/users
public class UserController {

    @GetMapping
    public String getUser() {
        return "getUser() was being called!";
    }

    @PostMapping
    public String createUser() {
        return "createUser() was being called!";
    }

    @PutMapping
    public String updateUser() {
        return "updateUser() was being called!";
    }

    @DeleteMapping
    public String deleteUser() {
        return "deleteUser() was being called!";
    }
}

