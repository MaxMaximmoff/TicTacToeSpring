package com.maximoff.TicTacToeSpring.models;

/*
    Класс игрока
 */

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "name",
        "symbol"
})


public class Player {

    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private  String name;

    @JsonProperty("symbol")
    private  String symbol;


    public Player() {
    }

    /**
     * @param id
     * @param name
     * @param symbol
     */
    public Player(String id, String name, String symbol) {
        this.name = name;
        this.symbol = symbol;
        this.id = id;
    }

    @JsonProperty("id")
    public String getPlayerID() {
        return id;
    }

    @JsonProperty("id")
    public void setPlayerID(String id) {
        this.id = id;
    }

    @JsonProperty("name")
    public String getPlayerName() {
        return name;
    }

    @JsonProperty("name")
    public void setPlayerName(String name) {
        this.name = name;
    }

    @JsonProperty("symbol")
    public String getPlayerMark() {
        return symbol;
    }

    @JsonProperty("symbol")
    public void setPlayerMark(String symbol) {
        this.symbol = symbol;
    }

}
