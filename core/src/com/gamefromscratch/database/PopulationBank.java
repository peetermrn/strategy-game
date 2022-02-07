package com.gamefromscratch.database;

import com.gamefromscratch.buildings.Building;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PopulationBank {
    Map<Integer, Integer> currentBank = new HashMap<>();
    Map<Integer, Integer> maxBank = new HashMap<>();
    private int playerAmount;
    public List<Building> buildings = new ArrayList<>();

    public PopulationBank(int playerAmount) {
        this.playerAmount = playerAmount;
        for (int i = 1; i <= playerAmount; i++) {
            currentBank.put(i, 3);
            maxBank.put(i, 0);
        }
    }

    public void increaseMax(int amount, int playerId, Building building) {
        maxBank.put(playerId, maxBank.get(playerId) + amount);
        buildings.add(building);
    }

    public void decreaseMax(int amount, int playerId, Building building) {
        if (buildings.contains(building)) {
            buildings.remove(building);
            maxBank.put(playerId, maxBank.get(playerId) - amount);
        }
    }

    public boolean canAddOneCurrent(int playerId) {
        return currentBank.get(playerId) < Math.min(maxBank.get(playerId), 50);
    }

    public boolean addOneCurrent(int playerId) {
        if (currentBank.get(playerId) < maxBank.get(playerId)) {
            currentBank.put(playerId, currentBank.get(playerId) + 1);
            return true;
        }
        return false;
    }


    public void takeOneCurrent(int playerId) {
        if (currentBank.get(playerId) < 0) {
            currentBank.put(playerId, 0);
        } else {
            currentBank.put(playerId, currentBank.get(playerId) - 1);
        }


    }


    public int getMax(int playerID) {
        return maxBank.get(playerID);
    }

    public int getCurrent(int playerID) {
        return currentBank.get(playerID);
    }

    public int getPlayerAmount() {
        return playerAmount;
    }
}

