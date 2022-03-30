package com.maximoff.TicTacToeSpring.storages;

import com.maximoff.TicTacToeSpring.models.Gameplay;

import java.util.HashMap;
import java.util.Map;

public class GameplayStorage {

    private static Gameplay gameplay = null;
    private static GameplayStorage instance = null;

    private GameplayStorage() {
        gameplay = new Gameplay();
    }

    public static synchronized GameplayStorage getInstance() {
        if (instance == null) {
            instance = new GameplayStorage();
        }
        return instance;
    }

    public Gameplay getGameplay() {
        return gameplay;
    }

    public void setGameplay(Gameplay gameplay) {
        this.gameplay = gameplay;
    }
}
