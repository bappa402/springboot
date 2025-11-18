package com.example.solver.controller;

import com.example.solver.service.EquationSolverService;
import com.example.solver.service.EquationSolverService.Result;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class SolverController {

    private final EquationSolverService solver = new EquationSolverService();

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/solver")
    public String solverForm(@RequestParam(value = "q", required = false) String q, Model model) {
        // prefill input if provided as query param ?q=1+2+-10
        model.addAttribute("input", q != null ? q : "");
        model.addAttribute("messages", null);
        return "solver";
    }

    @PostMapping("/solver")
    public String solveSubmit(@RequestParam("coeffs") String coeffs, Model model) {
        Result res = solver.solve(coeffs);
        model.addAttribute("input", coeffs);
        model.addAttribute("messages", res.messages);
        model.addAttribute("success", res.success);
        return "solver";
    }
}
