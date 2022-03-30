package com.maximoff.TicTacToeSpring.models;

/*
    Step - Класс описывающий шаг игры
*/

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "num",
        "playerId",
        "text"
})


public class Step {

    @JsonProperty("num")
    private String num;

    @JsonProperty("playerId")
    private String playerId;

    @JsonProperty("text")
    private String text;

    public Step(){
        super();
    }

    /**
     * @param num
     * @param playerId
     * @param text
     */
    public Step(String num, String playerId, String text) {
        super();
        this.num = num;
        this.playerId = playerId;
        this.text = text;
    }

    @JsonProperty("num")
    public String getNum() {
        return num;
    }

    @JsonProperty("num")
    public void setNum(String num) {
        this.num = num;
    }

    @JsonProperty("playerId")
    public String getPlayerId() {
        return playerId;
    }

    @JsonProperty("playerId")
    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    @JsonProperty("text")
    public String getStepValue() {
        return text;
    }

    @JsonProperty("text")
    public void setStepValue(String text) {
        this.text = text;
    }

}
