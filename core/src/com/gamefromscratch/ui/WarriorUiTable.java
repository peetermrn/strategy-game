package com.gamefromscratch.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.gamefromscratch.MainGame;
import com.gamefromscratch.units.Unit;

public class WarriorUiTable extends Table {
    Unit unit;

    public WarriorUiTable(Unit unit) {
        this.unit = unit;
        this.setSize(410, 200);
        this.setPosition(0, 0);
        this.setBounds(0, 0, this.getWidth(), this.getHeight());
        this.setTouchable(Touchable.enabled);
        this.align(Align.topLeft);
        this.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ui/Darker_ui_background.png")))));
        this.setSkin(MainGame.gameSkin);

        addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        Label booleanLabel;
        if (unit.attackBoolean) {
            booleanLabel = new Label("CURRENT MODE:\nATTACK", MainGame.gameSkin, "default");
        } else {
            booleanLabel = new Label("CURRENT MODE:\nSTAND", MainGame.gameSkin, "default");
        }
        booleanLabel.setColor(Color.BLACK);


        Label resources2 = new Label("asd", MainGame.gameSkin, "default");
        resources2.setVisible(false);
        add(resources2);
        row().colspan(10);
        Label resources = new Label("asd", MainGame.gameSkin, "default");
        resources.setVisible(false);
        add(resources);

        Button stand = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Icons/shield.png")))));
        this.add(stand).size(80, 80);

        stand.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                unit.attackBoolean = false;
                unit.clearAllActions();
                booleanLabel.setText("CURRENT MODE:\nSTAND");
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        Label label = new Label("asd", MainGame.gameSkin, "default");
        label.setVisible(false);
        this.add(label).size(15, 15);

        Button attack = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Icons/attack.png")))));
        this.add(attack).size(80, 80);


        attack.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                unit.attackBoolean = true;
                booleanLabel.setText("CURRENT MODE:\nATTACK");



            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        Label label1 = new Label("asd", MainGame.gameSkin, "default");
        label1.setVisible(false);
        this.add(label1).size(15, 15);

        this.add(booleanLabel).size(15, 15);


        row();
        row().colspan(10);
        row().center();
        Label text = new Label("", MainGame.gameSkin, "default");
        text.setVisible(false);
        text.setColor(Color.BLACK);
        text.setFontScale(0.7f);
        this.add(text).size(30, 90);


        stand.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                text.setText("  STAND:\n  Unit will not automatically attack other\n  players, but will still fight back when attacked.");
                text.setVisible(true);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                text.setVisible(false);
            }
        });
        attack.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                text.setText("  ATTACK:\n  Unit will automatically find closest \n  enemy unit or building and attack.");
                text.setVisible(true);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                text.setVisible(false);
            }
        });


    }


    public void clearTable() {
        this.remove();
    }

}



