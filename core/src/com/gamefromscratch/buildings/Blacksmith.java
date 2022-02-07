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
import com.gamefromscratch.units.Worker;

public class Blacksmith extends Building {
    Sprite house;
    Sprite foundation;
    Sprite checker;

    GameScreen screen;
    private String name;
    public Blacksmith(){

    }
    public Blacksmith(GameScreen screen, float x, float y, int playerID, Worker worker) {
        super(playerID, screen);
        this.screen = screen;
        this.name = "Blacksmith";
        maxHp = 1500;

        checker = new Sprite(new Texture(Gdx.files.internal("buildings/Blacksmith" + playerID + ".png")));
        house = new Sprite(new Texture(Gdx.files.internal("buildings/Blacksmith" + playerID + ".png")));
        foundation = new Sprite(new Texture(Gdx.files.internal("buildings/foundation.png")));
        house.setTexture(foundation.getTexture());

        setTc();
        setTouchable(Touchable.enabled);
        screen.miniMap.addPlayerToMinimap(this, playerID);

        house.setOrigin(house.getWidth() / 2, house.getHeight() / 2);
        house.setPosition(x - house.getWidth() / 2, y - house.getHeight() / 2);
        setBounds(house.getX(), house.getY(), house.getWidth(), house.getHeight());

        screen.buildingList.add(this);

        addListener(new ClickListener(Input.Buttons.RIGHT) {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("clicked on blacksmith");
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
            house.setTexture(checker.getTexture());
            this.setReady();
        }
    }

    @Override
    public void draw(Batch batch, float alpha) {
        house.draw(batch);
    }


    public static int getWoodBuildAmount() {
        return 200;
    }

    public static int getGoldBuildAmount() {
        return 150;
    }


    public static Sprite getBuildingSprite(int playerID) {
        return new Sprite(new Texture(Gdx.files.internal("buildings/Blacksmith" + playerID + ".png")));
    }
}
