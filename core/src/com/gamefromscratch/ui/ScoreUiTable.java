package com.gamefromscratch.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.gamefromscratch.database.PopulationBank;
import com.gamefromscratch.MainGame;
import com.gamefromscratch.database.ResourceBank;
import com.gamefromscratch.screen.GameScreen;

public class ScoreUiTable extends Table {
    ResourceBank reBank;
    PopulationBank popBank;
    GameScreen gameScreen;
    Label label1;
    Label label2;
    Label label3;
    Label label4;

    public ScoreUiTable(ResourceBank resourceBank, PopulationBank populationBank, GameScreen screen) {
        this.reBank = resourceBank;
        this.popBank = populationBank;
        this.gameScreen = screen;
        this.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ui/hall.png")))));

        setPosition(1179 + 40, 202);
        this.setSize(60, 22 * reBank.getPlayerAmount());

        this.setBounds(this.getX(), this.getY(), this.getWidth(), this.getHeight());

        this.setSkin(MainGame.gameSkin);
        this.align(Align.bottomRight);

        addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });


        if (reBank.getPlayerAmount() > 3) {
            label4 = new com.badlogic.gdx.scenes.scene2d.ui.Label("", MainGame.gameSkin, "default");
            label4.setColor(255 / 255f, 164 / 255f, 4 / 255f, 1);
            label4.setFontScale(0.8f);
            add(label4);
            row();
        }
        if (reBank.getPlayerAmount() > 2) {
            label3 = new com.badlogic.gdx.scenes.scene2d.ui.Label("", MainGame.gameSkin, "default");
            label3.setColor(20 / 255f, 212 / 255f, 68 / 255f, 1);
            label3.setFontScale(0.8f);

            add(label3);
            row();
        }
        label2 = new com.badlogic.gdx.scenes.scene2d.ui.Label("", MainGame.gameSkin, "default");
        label2.setColor(252 / 255f, 12 / 255f, 12 / 255f, 1);
        label2.setFontScale(0.8f);
        add(label2);
        row();

        label1 = new com.badlogic.gdx.scenes.scene2d.ui.Label("", MainGame.gameSkin, "default");
        label1.setColor(Color.BLUE);
        label1.setFontScale(0.8f);
        add(label1);

        row();


    }

    public void setLabelText() {
        label1.setText(String.valueOf(popBank.getCurrent(1) * 50 + Math.round((reBank.getWood(1) + reBank.getWood(1)) / 2f)) + " ");

        label2.setText(String.valueOf(popBank.getCurrent(2) * 50 + Math.round((reBank.getWood(2) + reBank.getWood(2)) / 2f)) + " ");
        if (reBank.getPlayerAmount() > 2) {
            label3.setText(String.valueOf(popBank.getCurrent(3) * 50 + Math.round((reBank.getWood(3) + reBank.getWood(3)) / 2f)) + " ");
        }
        if (reBank.getPlayerAmount() > 3) {
            label4.setText(String.valueOf(popBank.getCurrent(4) * 50 + Math.round((reBank.getWood(4) + reBank.getWood(4)) / 2f)) + " ");
        }
    }
}
