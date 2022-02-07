package com.gamefromscratch.buildings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.gamefromscratch.screen.GameScreen;
import com.gamefromscratch.units.Unit;

public class Building extends Actor {
    public int hp = 1;
    public int maxHp = 1000;
    public boolean alive = true;
    public boolean ready = false;
    public int playerID;
    public TownCenter tc;


    Rectangle bounds;
    GameScreen screen;
    public Building(){

    }
    public Building(int playerID, GameScreen screen) {
        this.screen = screen;
        this.playerID = playerID;
    }

    public Rectangle getBounds() {
        bounds = new Rectangle((int) getX() + 10, (int) getY() + 10, (int) getWidth() - 20, (int) getHeight() - 30);
        return bounds;
    }


    public void takeOne() {
    }

    public void setTc() {
        for (Building building : screen.buildingList) {
            if (building instanceof TownCenter && building.playerID == playerID) {
                this.tc = (TownCenter) building;
            }
        }
    }


    public void destroy(Unit unit, int damage) {
        hp -= damage;
        if (hp <= 0 && alive) {
            unit.building = null;
            unit.unit = null;
            unit.goToStand();
            death();
        }
    }

    public void death() {
        if (screen.selectedActor == this) {
            screen.selectedActor = null;
            screen.clearAllUiTables();
        }
        if (this instanceof TownCenter) {
            screen.popBank.decreaseMax(5, playerID, this);
        }
        if (this instanceof House) {
            screen.popBank.decreaseMax(10, playerID, this);
        }
        Sprite death = new Sprite(new Texture(Gdx.files.internal("buildings/rubble.png")));
        death.setPosition(getX(), getY());
        death.setSize(getWidth(), getHeight());
        screen.graves.add(death);

        screen.miniMap.removePlayerFromMinimap(this);
        screen.buildingList.remove(this);
        this.alive = false;
        this.setPosition(0, 0);
        remove();
    }


    public int getHp() {
        return hp;
    }


    public int getMaxHp() {
        return maxHp;
    }


    public boolean isReady() {
        return ready;

    }


    public void setReady() {
        hp = maxHp;
        ready = true;
    }


    public int getPlayerID() {
        return playerID;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (screen.selectedActor == this) {
            screen.informationUiTable.setTexts(this);
        }
    }


}
