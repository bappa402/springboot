package com.example.solver.controller;

import com.example.solver.service.EquationSolverService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

@Controller

public class UserController {

    List<Map<String, String>> userList = new java.util.ArrayList<>();

    @PostConstruct
    public void init() {
        userList.add(Map.of("username", "user1", "password", "pass1"));
        userList.add(Map.of("username", "user2", "password", "pass2"));
        userList.add(Map.of("username", "admin", "password", "2020"));
        userList.add(Map.of("username", "guest", "password", "xy69"));
    }

    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("title", "Login Page");
        return "login";
    }

    @PostMapping("/login")
    public String loginSubmit(@RequestParam("username") String username,
            @RequestParam("password") String password,
            Model model) {

        for (Map<String, String> user : userList) {
            if (user.get("username").equals(username) && user.get("password").equals(password)) {
                model.addAttribute("message", "Login successful! Welcome, " + username + ".");

                if ("admin".equals(username)) {
                    model.addAttribute("usertype", "editor");
                }
                return "user_page";
            }
        }

        model.addAttribute("message", "Invalid credentials. Please try again.");
        return "login";
    }

    @PostMapping("/create_user")
    public String createUser(@RequestParam("new_username") String newUsername,
            @RequestParam("new_password") String newPassword,
            Model model) {

        for (Map<String, String> user : userList) {
            if (user.get("username").equals(newUsername)) {
                model.addAttribute("message", "Login successful! Welcome, " + "admin");
                model.addAttribute("usertype", "editor");
                model.addAttribute("message2", "Username already exists. Please choose another.");
                return "user_page";
            }
        }

        userList.add(Map.of("username", newUsername, "password", newPassword));
        model.addAttribute("message", "User created successfully! You can now log in.");
        return "login";
    }
}
