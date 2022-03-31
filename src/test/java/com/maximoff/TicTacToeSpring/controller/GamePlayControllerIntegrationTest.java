package com.maximoff.TicTacToeSpring.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maximoff.TicTacToeSpring.models.Gameplay;
import com.maximoff.TicTacToeSpring.models.Player;
import com.maximoff.TicTacToeSpring.services.GameplayService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.isA;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
public class GamePlayControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GameplayService gameplayService;

    @Test
    public void startTest() throws Exception {
        Player player = new Player("1", "Vasya", "X");

        ArrayList<Player> players = new ArrayList<Player>(); // create list here
        players.add(player);
        Gameplay gameplay = new Gameplay();
        gameplay.setPlayers(players);

//         mock the behaviour of your GameplayService bean
        when(gameplayService.createGame(isA(Player.class))).thenReturn(gameplay);

        MvcResult result =
                mockMvc.perform(
                        post("/gameplay/start")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(player)))
                        .andDo(MockMvcResultHandlers.print())
                        .andExpect(status().isOk())
                        .andExpect(content().json("{\"Player\":[{\"id\":\"1\",\"name\":\"Vasya\",\"symbol\":\"X\"}]}"))
                        .andReturn();
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
