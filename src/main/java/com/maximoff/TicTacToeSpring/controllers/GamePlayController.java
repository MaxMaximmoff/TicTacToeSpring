package com.maximoff.TicTacToeSpring.controllers;

import com.maximoff.TicTacToeSpring.exceptions.NotFoundException;
import com.maximoff.TicTacToeSpring.exceptions.InvalidGameException;
import com.maximoff.TicTacToeSpring.models.Gameplay;
import com.maximoff.TicTacToeSpring.models.Json;
import com.maximoff.TicTacToeSpring.models.Player;
import com.maximoff.TicTacToeSpring.models.Step;
import com.maximoff.TicTacToeSpring.services.GameplayService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/gameplay")
public class GamePlayController {

    private final GameplayService gameplayService;

//    private final SimpMessagingTemplate simpMessagingTemplate;

//    public GamePlayController(GameplayService gameplayService, SimpMessagingTemplate simpMessagingTemplate) {
//        this.gameplayService = gameplayService;
//        this.simpMessagingTemplate = simpMessagingTemplate;
//    }
    

    @PostMapping("/start")
    public ResponseEntity<Gameplay> start(@RequestBody Player player) {
        log.info("start game request: {}", player);
        return ResponseEntity.ok(gameplayService.createGame(player));
    }

    @PostMapping("/connect")
    public ResponseEntity<Gameplay> connectRandom(@RequestBody Player player) throws NotFoundException, InvalidGameException {
        log.info("connect request {}", player);
        return ResponseEntity.ok(gameplayService.connectToGame(player));
    }

    @PostMapping("/game")
    public ResponseEntity<Gameplay> gamePlay(@RequestBody Step request) throws NotFoundException, InvalidGameException {
        log.info("step request: {}", request);
        Gameplay gameplay = gameplayService.makeStep(request);
//        simpMessagingTemplate.convertAndSend("/topic/game-progress/", gameplay);
        return ResponseEntity.ok(gameplay);
    }

    @GetMapping("/game")
    public ResponseEntity<Gameplay> gamePlay_(@RequestParam("text") String text) throws NotFoundException, InvalidGameException {
        log.info("step text request: {}", text);
        Gameplay gameplay = gameplayService.getGamplay(text);
//        simpMessagingTemplate.convertAndSend("/topic/game-progress/", gameplay);
        return ResponseEntity.ok(gameplay);
    }

    @GetMapping("/playback")
    public ResponseEntity<Json> gamePlay1(@RequestParam("json") String json_type) throws NotFoundException, InvalidGameException {
        log.info("json request: {}", json_type);
        Json json = gameplayService.getJsonStor(json_type);
        return ResponseEntity.ok(json);
    }


}
