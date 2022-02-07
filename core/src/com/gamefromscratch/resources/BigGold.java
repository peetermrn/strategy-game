package com.gamefromscratch.resources;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.gamefromscratch.screen.GameScreen;

public class BigGold extends Resource {
    GameScreen screen;
    private String name;
    Rectangle bounds;
    private int totalResource = 400;
    private int maxResource = 400;
    public boolean empty = false;
    Sprite goldSprite = new Sprite(new Texture(Gdx.files.internal("resources/Double gold.png")));


    public BigGold(GameScreen screen, int x, int y) {
        this.screen = screen;
        this.name = "Big gold";

        goldSprite.setOrigin(goldSprite.getWidth() / 2, goldSprite.getHeight() / 2);

        setTouchable(Touchable.enabled);

        goldSprite.setPosition(96 * x - goldSprite.getWidth() / 2, y * 96 - goldSprite.getHeight() / 2 - 32);
        setPosition(96 * x - goldSprite.getWidth() / 2, y * 96 - goldSprite.getHeight() / 2 - 32);
        setBounds(goldSprite.getX(), goldSprite.getY(), goldSprite.getWidth(), goldSprite.getHeight());
        addListener(new ClickListener(Input.Buttons.RIGHT) {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("clicked on double gold yo");

                return true;
            }
        });
        screen.stage.addActor(this);
        bounds = new Rectangle((int) getX(), (int) getY(), (int) getWidth(), (int) getHeight());


    }

    @Override
    public int getTotalResource() {
        return totalResource;
    }

    @Override
    public int getMaxResource() {
        return maxResource;
    }

    @Override
    public Rectangle getBounds() {
        return bounds;
    }

    public String getName() {
        return name;
    }
    @Override
    public void act(float delta) {
        super.act(delta);
        if (screen.selectedActor == this) {
            screen.informationUiTable.setTexts(this);
        }
    }

    @Override
    public void draw(Batch batch, float alpha) {
        batch.draw(goldSprite, getX(), getY());
    }

    @Override
    public void takeOne() {
        totalResource--;
        if (totalResource == 0) {
            screen.path.addNodes(this);
            screen.miniMap.removeResourceFromMap(this);
            screen.goldList.remove(this);
            screen.resourceList.remove(this);
            remove();
            this.empty = true;
        }
    }

    @Override
    public boolean isEmpty() {
        return empty;
    }
}
