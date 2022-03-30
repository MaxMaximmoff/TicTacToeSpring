package com.maximoff.TicTacToeSpring.models;

/*
    Класс Gameplay для хранения игроков, игры с шагами, результата игры
 */

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "Player",
    "Game",
    "GameResult"
})


public class Gameplay {

    private String[][] board;
    @JsonProperty("Player")
    private ArrayList<Player> players = null;
    @JsonProperty("Game")
    private Game game;
    @JsonProperty("GameResult")
    private GameResult gameResult;
    private GameStatus status;
    private Player curPlayer;


    public Gameplay() {
        super();
    }

    /**
     * @param players
     * @param game
     * @param gameResult
     */
    public Gameplay(ArrayList<Player> players, String[][] board,
                    Game game, GameResult gameResult, GameStatus status) {
        super();
        this.board = board;
        this.players = players;
        this.game = game;
        this.gameResult = gameResult;
        this.status = status;
    }

    public Player getCurPlayer() {
        return curPlayer;
    }

    public void setCurPlayer(Player curPlayer) {
        this.curPlayer = curPlayer;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

    public String[][] getBoard() {
        return board;
    }

    public void setBoard(String[][] board) {
        this.board = board;
    }

    @JsonProperty("Player")
    public ArrayList<Player> getPlayers() {
        return players;
    }

    @JsonProperty("Player")
    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    @JsonProperty("Game")
    public Game getGame() {
        return game;
    }

    @JsonProperty("Game")
    public void setGame(Game game) {
        this.game = game;
    }

    @JsonProperty("GameResult")
    public GameResult getGameResult() {
        return gameResult;
    }

    @JsonProperty("GameResult")
    public void setGameResult(GameResult gameResult) {
        this.gameResult = gameResult;
    }

}
