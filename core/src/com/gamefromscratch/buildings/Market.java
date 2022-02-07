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

public class Market extends Building {
    Sprite house;
    Sprite market;
    Sprite foundation;
    GameScreen screen;
    private String name;
    public Market(){

    }
    public Market(GameScreen screen, float x, float y, int playerID, Worker worker) {
        super(playerID, screen);
        this.screen = screen;
        this.name = "Market";
        maxHp = 2000;

        market = new Sprite(new Texture(Gdx.files.internal("buildings/market" + playerID + ".png")));
        house = new Sprite(new Texture(Gdx.files.internal("buildings/market" + playerID + ".png")));

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
            house.setTexture(market.getTexture());
            this.setReady();
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
        return 150;
    }


    public static Sprite getBuildingSprite(int playerID) {
        return new Sprite(new Texture(Gdx.files.internal("buildings/market" + playerID + ".png")));
    }
}
