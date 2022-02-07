package com.gamefromscratch.database;

import java.util.HashMap;
import java.util.Map;

public class ResourceBank {
    Map<Integer, Integer> woodBank = new HashMap<>();
    Map<Integer, Integer> goldBank = new HashMap<>();
    private int playerAmount;

    public ResourceBank(int playerAmount) {
        this.playerAmount = playerAmount;
        for (int i = 1; i <= playerAmount; i++) {
            woodBank.put(i, 300);
            goldBank.put(i, 100);
        }
    }

    public void setResources(int amount) {
        for (int i = 1; i <= playerAmount; i++) {
            woodBank.put(i, amount);
            goldBank.put(i, amount);
        }
    }

    public void addToEveryone(int amount) {
        for (int i = 1; i <= playerAmount; i++) {
            addToBank(amount, amount, i);
        }

    }

    public void addToBank(int woodAmount, int goldAmount, int playerId) {
        woodBank.put(playerId, Math.min(woodBank.get(playerId) + woodAmount, 9999));
        goldBank.put(playerId, Math.min(goldBank.get(playerId) + goldAmount, 9999));
    }

    public boolean takeFromBank(int woodAmount, int goldAmount, int playerId) {
        if (woodBank.get(playerId) >= woodAmount && goldBank.get(playerId) >= goldAmount) {
            woodBank.put(playerId, woodBank.get(playerId) - woodAmount);
            goldBank.put(playerId, goldBank.get(playerId) - goldAmount);
            return true;
        }
        return false;
    }

    public boolean isEnough(int woodAmount, int goldAmount, int playerId) {
        return woodBank.get(playerId) >= woodAmount && goldBank.get(playerId) >= goldAmount;
    }

    public int getWood(int playerID) {
        return woodBank.get(playerID);
    }

    public int getGold(int playerID) {
        return goldBank.get(playerID);
    }

    public int getPlayerAmount() {
        return playerAmount;
    }
}
