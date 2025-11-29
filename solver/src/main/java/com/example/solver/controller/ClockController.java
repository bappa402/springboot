package com.example.solver.controller;

import com.example.solver.service.EquationSolverService;
import com.example.solver.service.EquationSolverService;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ClockController {

    EquationSolverService scsrv = new EquationSolverService();
    @GetMapping("/clock")
    public String clockPage(Model model) {
        model.addAttribute("hour", 6);
        model.addAttribute("tolerance", 2);
        return "clock"; // Thymeleaf template: clock.html
    }

    @PostMapping("/clock/result")
    public String processClockResult(@RequestParam int hour, @RequestParam int tolerance, Model model) {
        List mins = scsrv.calculateClockHour(hour, tolerance);
        model.addAttribute("mins", mins);
        model.addAttribute("hour", hour);
        model.addAttribute("tolerance", tolerance);
        return "clock"; // Thymeleaf template: clock.html
    }
}
