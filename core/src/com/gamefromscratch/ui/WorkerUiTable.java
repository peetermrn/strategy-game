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
import com.gamefromscratch.buildings.Barracks;
import com.gamefromscratch.buildings.Blacksmith;
import com.gamefromscratch.buildings.Camp;
import com.gamefromscratch.buildings.House;
import com.gamefromscratch.buildings.Market;
import com.gamefromscratch.MainGame;

import java.util.ArrayList;
import java.util.List;

public class WorkerUiTable extends Table {
    public int selectedUIBuilding = 0;
    private List<Button> buttons = new ArrayList<>();
    private Label text;

    public WorkerUiTable() {

        this.setSize(410, 200);
        this.setPosition(0, 0);
        this.setBounds(0, 0, 410, this.getHeight());
        this.setTouchable(Touchable.enabled);
        this.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("ui/Darker_ui_background.png")))));
        this.setSkin(MainGame.gameSkin);
        this.top();
        addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });


        row();
        row().colspan(10);

        Button HouseButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Icons/house.png")))));
        this.add(HouseButton).size(100, 100);

        HouseButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("HOUSE");
                selectedUIBuilding = 1;
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        buttons.add(HouseButton);


        Button BarracksButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Icons/barracks.png")))));
        this.add(BarracksButton).size(100, 100);

        BarracksButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("BARRACKS");
                selectedUIBuilding = 2;
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        buttons.add(BarracksButton);

        Button MarketButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Icons/market.png")))));
        this.add(MarketButton).size(100, 100);

        MarketButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("MARKET");
                selectedUIBuilding = 3;
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        buttons.add(MarketButton);

        Button CampButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Icons/camp.png")))));
        this.add(CampButton).size(100, 100);

        CampButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("CAMP");
                selectedUIBuilding = 4;
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        buttons.add(CampButton);

        row();
        row().colspan(10);
        row().right();

        Label text = new Label("  Hover on building to see info.", MainGame.gameSkin, "default");
        text.setVisible(true);
        text.setColor(Color.BLACK);
        text.setFontScale(0.8f, 0.8f);
        this.add(text).size(30, 50).left();
        this.text = text;

        Label text1 = new Label("asd", MainGame.gameSkin, "default");
        text1.setVisible(false);
        text1.setColor(Color.BLACK);
        text1.setFontScale(0.8f, 0.8f);
        this.add(text1).size(10, 50).left();

        Label text2 = new Label("asd", MainGame.gameSkin, "default");
        text2.setVisible(false);
        text2.setColor(Color.BLACK);
        text2.setFontScale(0.8f, 0.8f);
        this.add(text2).size(10, 50).left();

        Button BlacksmithButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Icons/blacksmith.png")))));
        this.add(BlacksmithButton).left().size(100, 100);
        BlacksmithButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("Blacksmith");
                selectedUIBuilding = 5;
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        buttons.add(BlacksmithButton);
        hideButtons();

        HouseButton.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                text.setText("  House:\n  Increases maximum population\n  space by 10\n  Wood: " + House.getWoodBuildAmount() + " Gold: " + House.getGoldBuildAmount());
                text.setVisible(true);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                text.setText("  Hover on building to see info.");
                text.setVisible(false);
            }
        });

        BarracksButton.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                text.setText("  Barracks:\n  Used to create different warriors.\n  Wood: " + Barracks.getWoodBuildAmount() + " Gold: " + Barracks.getGoldBuildAmount());
                text.setVisible(true);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                text.setText("  Hover on building to see info.");
                text.setVisible(false);
            }
        });

        MarketButton.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                text.setText("  Market:\n  Used to trade resources.\n  Wood: " + Market.getWoodBuildAmount() + " Gold: " + Market.getGoldBuildAmount());
                text.setVisible(true);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                text.setText("  Hover on building to see info.");
                text.setVisible(false);
            }
        });

        CampButton.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                text.setText("  Camp:\n  Deposit gold and wood.\n  Wood: " + Camp.getWoodBuildAmount() + " Gold: " + Camp.getGoldBuildAmount());
                text.setVisible(true);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                text.setText("  Hover on building to see info.");
                text.setVisible(false);
            }
        });
        BlacksmithButton.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                text.setText("  Blacksmith:\n  Used for upgrading units etc.\n  Wood: " + Blacksmith.getWoodBuildAmount() + " Gold: " + Blacksmith.getGoldBuildAmount());
                text.setVisible(true);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                text.setText("  Hover on building to see info.");
                text.setVisible(false);
            }
        });


    }


    public Integer getSelectedBuilding() {
        return selectedUIBuilding;
    }

    public void unmakeSelectedBuilding() {
        selectedUIBuilding = 0;
    }

    public void hideButtons() {
        text.setVisible(false);
        for (Button button : buttons) {
            button.setVisible(false);
        }
    }

    public void showButtons() {
        text.setVisible(true);
        for (Button button : buttons) {
            button.setVisible(true);
        }
    }


}
