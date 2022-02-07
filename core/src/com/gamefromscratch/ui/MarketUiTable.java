package com.gamefromscratch.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.gamefromscratch.buildings.Market;
import com.gamefromscratch.database.ResourceBank;
import com.gamefromscratch.MainGame;
import com.gamefromscratch.screen.GameScreen;

public class MarketUiTable extends Table {


    ResourceBank reBank;
    Button tree;
    Button tree2;

    Label ratio;
    float cof;

    int numberOfPlayers;
    Market market;
    GameScreen screen;

    public MarketUiTable(ResourceBank reBank, int numberOfPlayers, Market market, float cof, GameScreen screen) {
        this.cof = cof;
        this.screen = screen;
        this.market = market;
        this.setSize(410, 200);
        this.setPosition(0, 0);
        this.setBounds(0, 0, this.getWidth(), this.getHeight());
        this.setTouchable(Touchable.enabled);
        this.align(Align.topLeft);
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


        this.numberOfPlayers = numberOfPlayers;

        this.reBank = reBank;
        Label emptyLabel = new Label("", MainGame.gameSkin, "default");
        emptyLabel.setColor(Color.BLACK);
        this.add(emptyLabel).left();
        row();

        ratio = new Label(100 + " wood = " + Math.round(cof * 100) + " gold", MainGame.gameSkin, "default");
        ratio.setColor(Color.BLACK);

        this.add(ratio).size(180, 40).center();
        row();

        Button sellButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Icons/wood.png")))));
        this.add(sellButton).left().size(100, 100);


        sellButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                sellMethod();
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        Button buyButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Icons/gold.png")))));
        this.add(buyButton).left().size(100, 100);


        buyButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                buyMethod();
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });


    }

    private void sellMethod() {
        if (reBank.getWood(market.playerID) >= 100) {
            reBank.takeFromBank(100, 0, market.playerID);
            reBank.addToBank(0, (int) (cof * 100), market.playerID);
            if (cof > 0.15) {
                cof *= 0.8;
                screen.marketCof = cof;
            }
            ratio.setText(100 + " wood = " + Math.round(cof * 100) + " gold");

        }
    }

    private void buyMethod() {
        if (reBank.getGold(market.playerID) >= cof * 100) {
            reBank.takeFromBank(0, (int) (cof * 100), market.playerID);
            reBank.addToBank(100, 0, market.playerID);
            if (cof < 6) {
                cof *= 1.1;
                screen.marketCof = cof;
            }
            ratio.setText(100 + " wood = " + Math.round(cof * 100) + " gold");
        }
    }


    public void clearTable() {
        this.remove();
    }

}


