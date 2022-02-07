package com.gamefromscratch.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.gamefromscratch.database.PopulationBank;
import com.gamefromscratch.MainGame;
import com.gamefromscratch.database.ResourceBank;

public class ResourceUiTable extends Table {
    public int woodAmount = 0;
    Button icon;
    public int goldAmount = 0;
    public Label woodCounter;
    public Label goldCounter;
    public Label popCounter;
    public Button wood;
    public Button gold;
    public Button pop;

    Button icon2;
    public Label woodCounter2;
    public Label goldCounter2;
    public Label popCounter2;
    public Button wood2;
    public Button gold2;
    public Button pop2;

    Button icon3;
    public Label woodCounter3;
    public Label goldCounter3;
    public Label popCounter3;
    public Button wood3;
    public Button gold3;
    public Button pop3;

    Button icon4;
    public Label woodCounter4;
    public Label goldCounter4;
    public Label popCounter4;
    public Button wood4;
    public Button gold4;
    public Button pop4;
    ResourceBank resBank;
    PopulationBank popBank;

    public ResourceUiTable(ResourceBank resBank, PopulationBank popBank) {
        this.resBank = resBank;
        this.popBank = popBank;
        setPosition(0, 720 - resBank.getPlayerAmount() * 30);
        this.setSize(290, resBank.getPlayerAmount() * 30);

        this.setBounds(this.getX(), this.getY(), this.getWidth(), this.getHeight());

        this.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ui/Darker_ui_background.png")))));
        this.setSkin(MainGame.gameSkin);
        this.align(1);

        addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });


        row().colspan(10);

        icon = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("minimap/player1_dot.png"))));
        icon.left();
        this.add(icon).size(30, 25);

        wood = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("icons/tree_icon.png")))));
        this.add(wood).size(30, 25);

        woodCounter = new Label(Integer.toString(woodAmount), MainGame.gameSkin, "default");
        woodCounter.setColor(Color.BLACK);
        this.add(woodCounter);


        gold = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("icons/gold_icon.png")))));
        this.add(gold).size(30, 30);

        goldCounter = new Label(Integer.toString(goldAmount), MainGame.gameSkin, "default");
        goldCounter.setColor(Color.BLACK);
        this.add(goldCounter);

        pop = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("icons/population_icon.png")))));
        this.add(pop).size(30, 30);

        popCounter = new Label("0/0", MainGame.gameSkin, "default");
        popCounter.setColor(Color.BLACK);
        this.add(popCounter);

        row().colspan(10);

        icon2 = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("minimap/player2_dot.png"))));
        icon2.left();
        this.add(icon2).size(30, 25);
        wood2 = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("icons/tree_icon.png")))));
        this.add(wood2).size(30, 25);

        woodCounter2 = new Label("", MainGame.gameSkin, "default");
        woodCounter2.setColor(Color.BLACK);
        this.add(woodCounter2);


        gold2 = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("icons/gold_icon.png")))));
        this.add(gold2).size(30, 30);

        goldCounter2 = new Label(Integer.toString(goldAmount), MainGame.gameSkin, "default");
        goldCounter2.setColor(Color.BLACK);
        this.add(goldCounter2);

        pop2 = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("icons/population_icon.png")))));
        this.add(pop2).size(30, 30);

        popCounter2 = new Label("0/0", MainGame.gameSkin, "default");
        popCounter2.setColor(Color.BLACK);
        this.add(popCounter2);

        if (popBank.getPlayerAmount() > 2) {
            row().colspan(10);
            icon3 = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("minimap/player3_dot.png"))));
            icon3.left();
            this.add(icon3).size(30, 25);
            wood3 = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("icons/tree_icon.png")))));
            this.add(wood3).size(30, 25);

            woodCounter3 = new Label("", MainGame.gameSkin, "default");
            woodCounter3.setColor(Color.BLACK);
            this.add(woodCounter3);


            gold3 = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("icons/gold_icon.png")))));
            this.add(gold3).size(30, 30);

            goldCounter3 = new Label(Integer.toString(goldAmount), MainGame.gameSkin, "default");
            goldCounter3.setColor(Color.BLACK);
            this.add(goldCounter3);

            pop3 = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("icons/population_icon.png")))));
            this.add(pop3).size(30, 30);

            popCounter3 = new Label("0/0", MainGame.gameSkin, "default");
            popCounter3.setColor(Color.BLACK);
            this.add(popCounter3);
        }
        if (popBank.getPlayerAmount() > 3) {
            row().colspan(10);
            icon4 = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("minimap/player4_dot.png"))));
            icon4.left();
            this.add(icon4).size(30, 25);

            wood4 = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("icons/tree_icon.png")))));
            this.add(wood4).size(30, 25);

            woodCounter4 = new Label("", MainGame.gameSkin, "default");
            woodCounter4.setColor(Color.BLACK);
            this.add(woodCounter4);


            gold4 = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("icons/gold_icon.png")))));
            this.add(gold4).size(30, 30);

            goldCounter4 = new Label(Integer.toString(goldAmount), MainGame.gameSkin, "default");
            goldCounter4.setColor(Color.BLACK);
            this.add(goldCounter4);

            pop4 = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("icons/population_icon.png")))));
            this.add(pop4).size(30, 30);

            popCounter4 = new Label("0/0", MainGame.gameSkin, "default");
            popCounter4.setColor(Color.BLACK);
            this.add(popCounter4);
        }
    }


    public void setAmounts() {
        woodCounter.setText(resBank.getWood(1));
        goldCounter.setText(resBank.getGold(1));
        popCounter.setText(popBank.getCurrent(1) + "/" + Math.min(popBank.getMax(1), 50));


        woodCounter2.setText(resBank.getWood(2));
        goldCounter2.setText(resBank.getGold(2));

        popCounter2.setText(popBank.getCurrent(2) + "/" + Math.min(popBank.getMax(2), 50));

        if (popBank.getPlayerAmount() > 2) {
            woodCounter3.setText(resBank.getWood(3));
            goldCounter3.setText(resBank.getGold(3));

            popCounter3.setText(popBank.getCurrent(3) + "/" + Math.min(popBank.getMax(3), 50));
        }
        if (popBank.getPlayerAmount() > 3) {
            woodCounter4.setText(resBank.getWood(4));
            goldCounter4.setText(resBank.getGold(4));

            popCounter4.setText(popBank.getCurrent(4) + "/" + Math.min(popBank.getMax(4), 50));
        }
    }
}



