package com.gamefromscratch.buildings;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.gamefromscratch.screen.GameScreen;
import com.gamefromscratch.units.Unit;
import com.gamefromscratch.units.Warrior;
import com.gamefromscratch.units.Worker;
import java.util.ArrayList;
import java.util.List;


public class TownCenter extends Building {

    List<Worker> workerList = new ArrayList<>();
    List<Unit> warriorList = new ArrayList<>();
    public int workerCost = 50;

    public boolean capacityResearch = false;
    public boolean costResearch = false;
    public boolean specialResearch = false;

    public Barracks barracks;
    public boolean speedResearch = false;
    public boolean damageResearch = false;

    private String name;


    Sprite townCenter;
    GameScreen screen;

    public TownCenter(){

    }
    public TownCenter(GameScreen screen, int x, int y, int playerID) {
        super(playerID, screen);
        townCenter = new Sprite(new Texture("towncentertextures/TownCenter" + playerID + ".png"));
        this.screen = screen;
        this.name = "Town Center";
        maxHp = 2500;
        hp = 2500;
        ready = true;

        setTouchable(Touchable.enabled);

        screen.miniMap.addPlayerToMinimap(this, playerID);

        townCenter.setOrigin(townCenter.getWidth() / 2, townCenter.getHeight() / 2);

        townCenter.setPosition(x - townCenter.getWidth() / 2, y - townCenter.getHeight() / 2);
        setBounds(townCenter.getX(), townCenter.getY(), townCenter.getWidth(), townCenter.getHeight());
        createInitialWorkers();
        screen.stage.addActor(this);
        addListener(new ClickListener(Input.Buttons.RIGHT) {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("clicked on townCenter yo");

                return true;
            }
        });

        screen.popBank.increaseMax(5, playerID, this);

    }

    public String getName() {
        return name;
    }

    private void createInitialWorkers() {
        Worker workerActor1 = new Worker(screen, this, this.getX() + townCenter.getWidth() / 2, this.getY() - this.getHeight() / 2, playerID);
        Worker workerActor2 = new Worker(screen, this, this.getX() + townCenter.getWidth() / 2 + 100, this.getY() - this.getHeight() / 2, playerID);
        Worker workerActor3 = new Worker(screen, this, this.getX() + townCenter.getWidth() / 2 - 100, this.getY() - this.getHeight() / 2, playerID);
        workerList.add(workerActor1);
        workerList.add(workerActor2);
        workerList.add(workerActor3);

    }


    @Override
    public void draw(Batch batch, float alpha) {
        townCenter.draw(batch);
    }


    public void CreateWorker() {
        if (screen.popBank.canAddOneCurrent(playerID)) {
            if (screen.reBank.takeFromBank(workerCost, 0, playerID) && screen.popBank.addOneCurrent(playerID)) {
                Worker workerActor2 = new Worker(screen, this, this.getX() + townCenter.getWidth() / 2, this.getY() - 25, playerID);
                screen.stage.addActor(workerActor2);
                screen.miniMap.addPlayerToMinimap(workerActor2, playerID);
                workerActor2.moveTo(new Vector2(this.getX() + townCenter.getWidth() / 2 - 5, this.getY() - this.getHeight() / 2 - 75), false);
                workerList.add(workerActor2);

            }
        }
    }


    public void workerCapacityResearch() {
        for (Worker worker : workerList) {
            worker.goldCapacity = 15;
            worker.woodCapacity = 25;
            capacityResearch = true;
        }
    }

    public void workerCostResearch() {
        workerCost = 45;
        costResearch = true;
    }

    public void specialUnitResearch() {
        barracks.specialWood += barracks.specialGold * 2;
        barracks.specialGold = 0;
        specialResearch = true;
    }

    public void workerSpeedResearch() {
        for (Worker worker : workerList) {
            worker.chopSpeed = 30;
            speedResearch = true;
        }
    }

    public void warriorDamage() {
        barracks.warriorDamage += 3;
        for (Unit unit : warriorList) {
            if (unit instanceof Warrior) {
                ((Warrior) unit).attackDamage += 3;
            }
        }
        damageResearch = true;
    }

}
