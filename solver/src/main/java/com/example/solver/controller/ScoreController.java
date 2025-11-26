package com.example.solver.controller;

import com.example.solver.service.ScoreService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ScoreController {

    private final ScoreService scoreService;

    public ScoreController(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    @GetMapping("/score")
    public String scorePage(Model model) {
        // Add all saved scores to the model
        model.addAttribute("scores", scoreService.getAllScores());
        return "score"; // Thymeleaf template: score.html
    }

    @PostMapping("/score/save")
    public String saveScore(@RequestParam String name,
                            @RequestParam String subject,
                            @RequestParam int score) {
        // Save score via the service
        scoreService.saveScore(name, subject, score);
        return "redirect:/score"; // Redirect back to the score page
    }
}
