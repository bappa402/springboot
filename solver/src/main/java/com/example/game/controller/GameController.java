package com.example.game.controller;

import com.example.game.model.Coordinates;
import com.example.game.service.GameService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/game")
public class GameController {

    private final GameService service;

    public GameController(GameService service) {
        this.service = service;
    }

    @PostMapping("/update")
    @ResponseBody
    public Coordinates update(@RequestBody Coordinates coords) {
        service.updateCoordinates(coords);
        return coords;
    }

    @GetMapping
    public String getGame() {
        return "game"; // resolves: src/main/resources/templates/game.html
    }

    @GetMapping("/state")
    @ResponseBody
    public Coordinates getState() {
        return service.getLastCoordinates();
    }

}
