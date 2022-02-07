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

public class Camp extends Building {
    Sprite house;
    Sprite camp;
    Sprite foundation;

    GameScreen screen;


    private String name;
    public Camp(){

    }
    public Camp(GameScreen screen, float x, float y, int playerID, Worker worker) {
        super(playerID, screen);
        this.screen = screen;
        this.name = "Camp";
        maxHp = 800;

        camp = new Sprite(new Texture(Gdx.files.internal("buildings/camp" + playerID + ".png")));
        house = new Sprite(new Texture(Gdx.files.internal("buildings/camp" + playerID + ".png")));
        foundation = new Sprite(new Texture(Gdx.files.internal("buildings/foundation.png")));

        house.setTexture(foundation.getTexture());
        house.setOrigin(house.getWidth() / 2, house.getHeight() / 2);
        house.setPosition(x - house.getWidth() / 2, y - house.getHeight() / 2);
        setBounds(house.getX(), house.getY(), house.getWidth(), house.getHeight());


        setTc();
        setTouchable(Touchable.enabled);
        screen.miniMap.addPlayerToMinimap(this, playerID);

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
            house.setTexture(camp.getTexture());
            screen.resourceDropOf.add(this);
            this.setReady();
        }

    }


    @Override
    public void draw(Batch batch, float alpha) {
        house.draw(batch);
    }

    public static int getWoodBuildAmount() {
        return 150;
    }

    public static int getGoldBuildAmount() {
        return 10;
    }


    public static Sprite getBuildingSprite(int playerID) {
        return new Sprite(new Texture(Gdx.files.internal("Buildings/camp" + playerID + ".png")));
    }
}
