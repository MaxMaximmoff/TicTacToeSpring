package com.maximoff.TicTacToeSpring.controller;

import com.maximoff.TicTacToeSpring.controllers.GamePlayController;
import com.maximoff.TicTacToeSpring.exceptions.InvalidGameException;
import com.maximoff.TicTacToeSpring.exceptions.NotFoundException;
import com.maximoff.TicTacToeSpring.models.Gameplay;
import com.maximoff.TicTacToeSpring.models.Player;
import com.maximoff.TicTacToeSpring.services.GameplayService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class GamePlayControllerUnitTest {

    GamePlayController gamePlayController;

    @BeforeEach
    void setup() {
        Gameplay gameplay = new Gameplay();
        ArrayList<Player> players = new ArrayList<>();

        gameplay.setPlayers(players);
        SimpMessagingTemplate simpMessagingTemplate = null;
        GameplayService gameplayService = Mockito.mock(GameplayService.class);
        when(gameplayService.createGame(new Player("1", "Vasya", "X"))).thenReturn(gameplay);
//        this.gamePlayController = new GamePlayController(gameplayService, simpMessagingTemplate);
        this.gamePlayController = new GamePlayController(gameplayService);
    }

    @Test
    void startTest() {
        System.out.println("тест: startTest()...");
        System.out.println(gamePlayController.start(new Player("1", "Vasya", "X")).getBody());
        assertEquals(HttpStatus.OK, gamePlayController.start(new Player("1", "Vasya", "X")).getStatusCode());
    }

    @Test
    void connectRandomTest() throws NotFoundException, InvalidGameException {
        System.out.println("тест: connectRandomTest()...");
        assertEquals(HttpStatus.OK, gamePlayController.connectRandom(new Player("2", "Petya", "O")).getStatusCode());
    }

}
