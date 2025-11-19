package com.example.solver.controller;

import com.example.solver.service.EquationSolverService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class SolverController {

    private final EquationSolverService solver = new EquationSolverService();

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("title", "Equation Solver by Bappa");
        return "index";
    }

    @GetMapping("/solver")
    public String solverForm(@RequestParam(value = "q", required = false) String q, Model model) {
        model.addAttribute("input", q != null ? q : "");
        model.addAttribute("messages", null);
        return "solver";
    }

    @PostMapping("/solver")
    public String solveSubmit(@RequestParam("coeffs") String coeffs, Model model) {
        List<String> messages = solver.solve(coeffs);

        model.addAttribute("input", coeffs);
        model.addAttribute("messages", messages);
        model.addAttribute("name", "bappa ");
        model.addAttribute("date", "20-NOV-2025");

        return "solver";
    }

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("name", "Bappa Bain, City-Kolkata🏢🌆");
        return "about";
    }
}
