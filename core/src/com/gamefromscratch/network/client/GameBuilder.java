package com.gamefromscratch.network.client;

import com.badlogic.gdx.scenes.scene2d.Action;

import java.util.ArrayList;
import java.util.List;

public class GameBuilder {
    private int connections = 0;
    private List<Action> runningActions = new ArrayList<>();
    public GameBuilder() {

    }
    public GameBuilder(int connections) {
        this.connections = connections;
    }

    public Integer getConnections() {
        return connections;
    }

    public List<Action> getActions() {
        return runningActions;
    }
}
