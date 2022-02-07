package com.gamefromscratch.resources;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Resource extends Actor {
    private int totalResource = 100;
    private int maxResource = 100;
    public boolean empty = false;

    public Rectangle getBounds() {
        return new Rectangle();
    }

    public void takeOne() {
        totalResource--;
        if (totalResource == 0) {
            remove();
            this.empty = true;
        }
    }

    public int getTotalResource() {
        return totalResource;
    }

    public int getMaxResource() {
        return maxResource;
    }

    public boolean isEmpty() {
        return empty;
    }

}
