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
import com.gamefromscratch.buildings.Barracks;
import com.gamefromscratch.MainGame;
import com.gamefromscratch.units.ManAtArms;
import com.gamefromscratch.units.Warrior;

public class BarracksUiTable extends Table {
    Barracks barracks;

    public BarracksUiTable(Barracks barracks) {
        this.barracks = barracks;
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


        Label resources2 = new Label("asd", MainGame.gameSkin, "default");
        resources2.setVisible(false);
        add(resources2);
        row().colspan(10);
        Label resources = new Label("asd", MainGame.gameSkin, "default");
        resources.setVisible(false);
        add(resources);

        Button warriorButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Icons/spearman.png")))));
        this.add(warriorButton).size(100, 100);

        warriorButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                barracks.createWarrior();
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        Label label = new Label("asd", MainGame.gameSkin, "default");
        label.setVisible(false);
        this.add(label).size(15, 15);

        Button destButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Icons/manatarms.png")))));
        this.add(destButton).size(100, 100);


        destButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                barracks.createManAtArms();


            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        Label label1 = new Label("asd", MainGame.gameSkin, "default");
        label1.setVisible(false);
        this.add(label1).size(15, 15);

        Button special = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Icons/special.png")))));
        this.add(special).size(100, 100);

        special.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                barracks.createSpecial();
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        row();
        row().colspan(10);
        row().center();
        Label text = new Label("asd", MainGame.gameSkin, "default");
        text.setVisible(false);
        text.setColor(Color.BLACK);
        text.setFontScale(0.8f);
        this.add(text).size(30, 90);


        warriorButton.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                text.setText("  Spearman:\n  Starter infantry.\n  HP: " + Warrior.maxHp + " Damage: " + barracks.warriorDamage + "   Wood: " + barracks.warriorWood + " Gold: " + barracks.warriorGold + "\n");
                text.setVisible(true);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                text.setVisible(false);
            }
        });
        destButton.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                text.setText("  Man at arms:\n  Stronger, but slower than spearman.\n  HP: " + ManAtArms.maxHp + " Damage: " + ManAtArms.attackDamage + "   Wood: " + barracks.manAtArmsWood + " Gold: " + barracks.getManAtArmsGold + "\n");
                text.setVisible(true);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                text.setVisible(false);
            }
        });
        special.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {

                if (barracks.playerID == 1) {
                    text.setText("  Berserk:\n  Heavy infantry. Strong but slow.\n  HP: " + 150 + " Damage: " + 15 + "   Wood: " + barracks.specialWood + " Gold: " + barracks.specialGold + "\n");
                }
                if (barracks.playerID == 2) {
                    text.setText("  Hussar:\n  Light infantry, quick but not very strong.\n  HP: " + 100 + " Damage: " + 20 + "   Wood: " + barracks.specialWood + " Gold: " + barracks.specialGold + "\n");
                }
                if (barracks.playerID == 3) {
                    text.setText("  Jaguar warrior:\n  Strong aztec infantry.\n  HP: " + 120 + " Damage: " + 15 + "   Wood: " + barracks.specialWood + " Gold: " + barracks.specialGold + "\n");
                }
                if (barracks.playerID == 4) {
                    text.setText("  Samurai:\n  Strong and fast infantry.\n  HP: " + 100 + " Damage: " + 20 + "   Wood: " + barracks.specialWood + " Gold: " + barracks.specialGold + "\n");
                }
                text.setFontScale(0.8f);
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
