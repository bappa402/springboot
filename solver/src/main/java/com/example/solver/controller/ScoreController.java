package com.example.solver.controller;

import com.example.solver.service.ScoreService;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
        model.addAttribute("avgScorePerSubject", scoreService.getAvgScorePerSubject());
        return "score"; // Thymeleaf template: score.html
    }

    @GetMapping("/score/edit/{id}")
    public String editScore(@PathVariable Long id, Model model) {
    Map<String, Object> score = scoreService.getScoreById(id); // also JDBC
    model.addAttribute("score", score);
    return "edit_score";
}


    @PostMapping("/score/update/{id}")
    public String updateScore(
            @PathVariable Long id,
            @RequestParam String name,
            @RequestParam String subject,
            @RequestParam int score
    ) {
        scoreService.updateScore(id, name, subject, score);
        return "redirect:/score";
    }


    @PostMapping("/score/save")
    public String saveScore(@RequestParam String name,
                            @RequestParam String subject,
                            @RequestParam int score) {
        // Save score via the service
        scoreService.saveScore(name, subject, score);
        return "redirect:/score"; // Redirect back to the score page
    }

    @PostMapping("/score/delete/{id}")
        public String deleteScore(@PathVariable Long id) {
            scoreService.deleteById(id);
            return "redirect:/score";
        }

}
