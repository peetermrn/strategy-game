package com.gamefromscratch.map;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class TiledMapClickListener extends ClickListener {

    private SmallMap actor;

    public TiledMapClickListener(SmallMap actor) {
        this.actor = actor;
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {
        System.out.println(actor.getX() + " " + actor.getY() + " has been clicked.");
    }
}
