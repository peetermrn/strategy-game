package com.gamefromscratch;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.gamefromscratch.screen.MainMenu;

public class MainGame extends Game {
    static public Skin gameSkin;

    @Override
    public void create() {
        //GameApplication game = new GameApplication();
        //game.create();
        //setScreen(game);
        gameSkin = new Skin(Gdx.files.internal("other/sgx-ui.json"));
        this.setScreen(new MainMenu(this));
    }
    public void render () {
        super.render();
    }

    public void dispose () {
    }
}
