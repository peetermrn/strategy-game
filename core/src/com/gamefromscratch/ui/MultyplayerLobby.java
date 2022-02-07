package com.gamefromscratch.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.gamefromscratch.MainGame;

public class MultyplayerLobby extends Table {
    Label standard = new Label("default", MainGame.gameSkin,"default");

    public MultyplayerLobby() {
        this.setSize(600, 400);
        this.setPosition(500, 200);
        this.setBounds(500, 200, this.getWidth(), this.getHeight());
        this.setTouchable(Touchable.enabled);
        //this.setDebug(true);
        this.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ui/Darker_ui_background.png")))));
        this.setSkin(MainGame.gameSkin);
        Label title = new Label("players", MainGame.gameSkin,"default");
        Label title1 = new Label("name", MainGame.gameSkin,"default");
        title.setColor(0, 0, 0, 1);
        title1.setColor(0, 0, 0, 1);
        this.add(title).padLeft(30).padTop(30).padRight(80);
        this.add(title1).padLeft(30).padTop(30);
        this.top();
    }

    public void addPlayer(String name) {
        Label games = new Label(name, MainGame.gameSkin,"default");
        games.setColor(Color.BLACK);
        standard.setColor(Color.BLACK);
        this.row();
        this.add(standard).padLeft(30).padTop(30).padRight(80);
        this.add(games).padLeft(30).padTop(30);
    }
}
