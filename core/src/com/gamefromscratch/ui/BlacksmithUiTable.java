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
import com.gamefromscratch.buildings.*;
import com.gamefromscratch.database.ResourceBank;
import com.gamefromscratch.MainGame;

import java.util.ArrayList;
import java.util.List;

public class BlacksmithUiTable extends Table {
    private List<Button> buttons = new ArrayList<>();
    private Label text;

    public BlacksmithUiTable(TownCenter townCenter, ResourceBank bank) {
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

        Button button1 = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Icons/research.png")))));
        this.add(button1).size(100, 100);

        button1.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("Button");
                if (bank.takeFromBank(100, 100, townCenter.playerID)) {
                    townCenter.workerCapacityResearch();
                    button1.setVisible(false);
                }
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        buttons.add(button1);


        Button button2 = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Icons/worker.png")))));
        this.add(button2).size(100, 100);

        button2.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("Button");
                if (bank.takeFromBank(150, 50, townCenter.playerID)) {
                    townCenter.workerCostResearch();
                    button2.setVisible(false);

                }
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        buttons.add(button2);

        Button button3 = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Icons/sword.png")))));
        this.add(button3).size(100, 100);

        button3.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("Button");
                if (townCenter.barracks != null) {
                    if (bank.takeFromBank(100, 120, townCenter.playerID)) {
                        townCenter.warriorDamage();
                        button3.setVisible(false);
                    }
                }
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        buttons.add(button3);

        Button button4 = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Icons/time.png")))));
        this.add(button4).size(100, 100);

        button4.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("Button");
                if (bank.takeFromBank(100, 100, townCenter.playerID)) {
                    townCenter.workerSpeedResearch();
                    button4.setVisible(false);
                }

            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        buttons.add(button4);

        row();
        row().colspan(10);
        row().right();

        Label text = new Label("  Hover on icon to see info.", MainGame.gameSkin, "default");
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

        Button button5 = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Icons/gold.png")))));
        this.add(button5).left().size(100, 100);
        button5.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("Button");
                if (townCenter.barracks != null) {
                    if (bank.takeFromBank(300, 500, townCenter.playerID)) {
                        townCenter.specialUnitResearch();
                        button5.setVisible(false);
                    }
                }
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        buttons.add(button5);

        button1.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                text.setText("  Research:\n  Worker carry capacity\n  increased by 10.\n  Wood: 100 Gold: 100");
                text.setVisible(true);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                text.setText("  Hover on icon to see info.");
                text.setVisible(false);
            }
        });

        button2.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                text.setText("  Research:\n  Worker cost is decreased by 5.\n  Wood: 150 Gold: 50");
                text.setVisible(true);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                text.setText("  Hover on icon to see info.");
                text.setVisible(false);
            }
        });

        button3.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                text.setText("  Research (Required barracks):\n  Basic warrior attack\n  increased by 3 points.\n  Wood: 100 Gold: 120");
                text.setVisible(true);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                text.setText("  Hover on icon to see info.");
                text.setVisible(false);
            }
        });

        button4.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                text.setText("  Research:\n  Workers gather resources faster.\n  Wood: 100 Gold: 100");
                text.setVisible(true);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                text.setText("  Hover on icon to see info.");
                text.setVisible(false);
            }
        });
        button5.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                text.setText("  Research (Required barracks):\n  Special unit gold cost is\n  replaced by 2x wood cost.\n  Wood: 300 Gold: 500");
                text.setVisible(true);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                text.setText("  Hover on icon to see info.");
                text.setVisible(false);
            }
        });

        if (townCenter.capacityResearch) {
            button1.setVisible(false);
        }
        if (townCenter.costResearch) {
            button2.setVisible(false);
        }
        if (townCenter.damageResearch) {
            button3.setVisible(false);
        }
        if (townCenter.speedResearch) {
            button4.setVisible(false);
        }
        if (townCenter.specialResearch) {
            button5.setVisible(false);
        }

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

    public void clearTable() {
        this.remove();
    }
}
