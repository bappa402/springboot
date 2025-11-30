package com.example.game.service;

import com.example.game.model.Coordinates;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class GameService {

    private final SimpMessagingTemplate messagingTemplate;
    private Coordinates lastCoords = new Coordinates(0, 0); // default or null

    public GameService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void updateCoordinates(Coordinates coord) {
        this.lastCoords = coord;
        messagingTemplate.convertAndSend("/topic/coords", coord);
    }

    public Coordinates getLastCoordinates() {
        return lastCoords;
    }
}
