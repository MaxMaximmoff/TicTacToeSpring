package com.maximoff.TicTacToeSpring.models;

/*
    Класс для хранения шагов игры
 */

import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "Step"
})


public class Game {

    @JsonProperty("Step")
    private ArrayList<Step> steps = null;

    public Game() {
        super();
    }

    /**
     * @param steps
     */
    public Game(ArrayList<Step> steps) {
        super();
        this.steps = steps;
    }

    @JsonProperty("Step")
    public ArrayList<Step> getSteps() {
        return steps;
    }

    @JsonProperty("Step")
    public void setSteps(ArrayList<Step> steps) {
        this.steps = steps;
    }


}
