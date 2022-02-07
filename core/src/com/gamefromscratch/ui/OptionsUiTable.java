package com.gamefromscratch.ui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.gamefromscratch.database.ResourceBank;
import com.gamefromscratch.MainGame;
import com.gamefromscratch.screen.GameScreen;
import com.gamefromscratch.screen.MainMenu;

public class OptionsUiTable extends Table {
    private GameScreen screen;
    Game game;
    ResourceBank reBank;


    public OptionsUiTable(GameScreen screen, Game game, int playersAmount, ResourceBank reBank) {
        this.screen = screen;
        this.game = game;
        this.reBank = reBank;
        this.setSize(320, 200);
        this.setPosition(550, 415);
        this.setBounds(550, 415, this.getWidth(), this.getHeight());
        this.setTouchable(Touchable.enabled);
        this.align(Align.left);
        this.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ui/Darker_ui_background.png")))));
        this.setSkin(MainGame.gameSkin);
        align(3);
        this.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        Label emptyLabel1 = new Label("RESET MARKET COEFFICIENT", MainGame.gameSkin, "default");
        emptyLabel1.setVisible(false);
        emptyLabel1.setColor(Color.BLACK);
        this.add(emptyLabel1).left();
        row();


        Label add = new Label("ADD 1000 RESOURCES        ", MainGame.gameSkin, "default");

        add.setColor(Color.BLACK);
        this.add(add);
        add.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                add.setColor(Color.GRAY);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                add.setColor(Color.BLACK);
            }
        });
        add.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                reBank.addToEveryone(1000);
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        row();

        Label zero = new Label("ZERO ALL RESOURCES       ", MainGame.gameSkin, "default");

        zero.setColor(Color.BLACK);
        this.add(zero);
        zero.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                zero.setColor(Color.GRAY);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                zero.setColor(Color.BLACK);
            }
        });
        zero.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                reBank.setResources(0);
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        row();


        Label market = new Label("RESET MARKET COEFFICIENT", MainGame.gameSkin, "default");
        market.setColor(Color.BLACK);
        this.add(market);
        market.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                market.setColor(Color.GRAY);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                market.setColor(Color.BLACK);
            }
        });
        market.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                screen.marketCof = 1;
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        row();
        Label empty = new Label("RESET MARKET COEFFICIENT", MainGame.gameSkin, "default");
        empty.setColor(Color.BLACK);
        empty.setVisible(false);
        this.add(empty);
        row();


        Label leave = new Label("LEAVE GAME", MainGame.gameSkin, "default");


        leave.setColor(Color.BLACK);
        this.add(leave).left();
        leave.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                leave.setColor(Color.GRAY);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                leave.setColor(Color.BLACK);
            }
        });
        leave.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                screen.dispose();

                game.setScreen(new MainMenu(game));
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        row();

        Label cancel = new Label("CANCEL                  ", MainGame.gameSkin, "default");

        cancel.setColor(Color.BLACK);
        this.add(cancel).left();
        cancel.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                cancel.setColor(Color.GRAY);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                cancel.setColor(Color.BLACK);
            }
        });
        cancel.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                clearTable();
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        row();


    }


    public void clearTable() {
        screen.ot = null;
        this.remove();
    }

}



