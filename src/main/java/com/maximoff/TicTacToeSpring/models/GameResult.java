package com.maximoff.TicTacToeSpring.models;

/*
    Класс обертка для хранения результата игры
 */

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "Player"
})
@Generated("me")


public class GameResult {

    @JsonProperty("Player")
    private Player player;


    public GameResult() {}

    /**
     * @param player
     */
    public GameResult(Player player) {
        super();
        this.player = player;
    }

    @JsonProperty("Player")
    public Player getPlayer() {
        return player;
    }

    @JsonProperty("Player")
    public void setPlayer(Player player) {
        this.player = player;
    }

}
