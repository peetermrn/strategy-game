package com.gamefromscratch.buildings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.gamefromscratch.screen.GameScreen;
import com.gamefromscratch.units.ManAtArms;
import com.gamefromscratch.units.Knight;
import com.gamefromscratch.units.Warrior;
import com.gamefromscratch.units.Worker;

public class Barracks extends Building {
    Sprite house;
    Sprite barracks;
    Sprite foundation;
    public Integer warriorWood = 70;
    public Integer warriorGold = 5;
    public Integer manAtArmsWood = 100;
    public Integer getManAtArmsGold = 50;
    public Integer specialWood = 150;
    public Integer specialGold = 75;

    public int warriorDamage = 10;
    public int manAtArmsDamage;
    public int specialDamage;

    private String name;

    GameScreen screen;

    public Barracks(){

    }
    public Barracks(GameScreen screen, float x, float y, int playerID, Worker worker) {
        super(playerID, screen);
        maxHp = 3000;
        this.screen = screen;
        this.name = "Barracks";
        setTc();
        tc.barracks = this;
        setTouchable(Touchable.enabled);
        screen.miniMap.addPlayerToMinimap(this, playerID);

        barracks = new Sprite(new Texture(Gdx.files.internal("buildings/barracks" + playerID + ".png")));
        house = new Sprite(new Texture(Gdx.files.internal("buildings/barracks" + playerID + ".png")));

        foundation = new Sprite(new Texture(Gdx.files.internal("buildings/foundation.png")));
        house.setTexture(foundation.getTexture());

        house.setOrigin(house.getWidth() / 2, house.getHeight() / 2);
        house.setPosition(x - house.getWidth() / 2, y - house.getHeight() / 2);
        setBounds(house.getX(), house.getY(), house.getWidth(), house.getHeight());

        if (playerID == 1) {
            specialWood = 150;
            specialGold = 75;
        }
        if (playerID == 2) {
            specialWood = 100;
            specialGold = 100;
        }
        if (playerID == 3) {
            specialWood = 150;
            specialGold = 80;
        }
        if (playerID == 4) {
            specialWood = 120;
            specialGold = 80;
        }


        screen.buildingList.add(this);

        addListener(new ClickListener(Input.Buttons.RIGHT) {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("clicked on barracks");
                return true;
            }
        });


        worker.moveTo(new Vector2(x, y), false);
        screen.path.removeNodes(this);
    }


    public String getName() {
        return name;
    }

    @Override
    public void takeOne() {
        hp += 25;
        if (hp >= maxHp) {
            house.setTexture(barracks.getTexture());
            this.setReady();

        }

    }


    public void createWarrior() {
        if (screen.popBank.canAddOneCurrent(playerID)) {
            if (screen.reBank.takeFromBank(warriorWood, warriorGold, playerID) && screen.popBank.addOneCurrent(playerID)) {
                Warrior warrior = new Warrior(screen, this, this.getX() + this.getWidth() / 2, this.getY() - 30, playerID);
                tc.warriorList.add(warrior);
                warriorDamage = warrior.getAttackDamage();
            }

        }
    }

    public void createManAtArms() {
        if (screen.popBank.canAddOneCurrent(playerID)) {
            if (screen.reBank.takeFromBank(manAtArmsWood, getManAtArmsGold, playerID) && screen.popBank.addOneCurrent(playerID)) {
                ManAtArms warrior = new ManAtArms(screen, this, this.getX() + this.getWidth() / 2, this.getY() - 30, playerID);
                tc.warriorList.add(warrior);
                manAtArmsDamage = warrior.getAttackDamage();
            }
        }
    }


    public void createSpecial() {
        if (screen.popBank.canAddOneCurrent(playerID)) {
            if (screen.reBank.takeFromBank(specialWood, specialGold, playerID) && screen.popBank.addOneCurrent(playerID)) {
                Knight warrior = new Knight(screen, this, this.getX() + this.getWidth() / 2, this.getY() - 30, playerID);
                tc.warriorList.add(warrior);
                specialDamage = warrior.getAttackDamage();
            }
        }
    }


    @Override
    public void draw(Batch batch, float alpha) {
        house.draw(batch);
    }


    public static int getWoodBuildAmount() {
        return 300;
    }

    public static int getGoldBuildAmount() {
        return 100;
    }


    public static Sprite getBuildingSprite(int playerID) {
        return new Sprite(new Texture(Gdx.files.internal("buildings/barracks" + playerID + ".png")));
    }
}
