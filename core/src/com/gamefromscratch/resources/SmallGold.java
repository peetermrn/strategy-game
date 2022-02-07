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

public class SmallGold extends Resource {
    GameScreen screen;
    Rectangle bounds;
    private int totalResource = 100;
    private int maxResource = 100;
    public boolean empty = false;
    private String name;
    Sprite goldSprite = new Sprite(new Texture(Gdx.files.internal("resources/Gold Mine006.png")));

    public SmallGold(GameScreen screen, int x, int y) {
        this.screen = screen;
        this.name = "Small gold";

        setBounds(goldSprite.getX(), goldSprite.getY(), goldSprite.getWidth(), goldSprite.getHeight());
        setTouchable(Touchable.enabled);

        goldSprite.setPosition(96 * x - goldSprite.getWidth() / 2, y * 96 - goldSprite.getHeight() / 2);
        setPosition(96 * x - goldSprite.getWidth() / 2, y * 96 - goldSprite.getHeight() / 2);
        goldSprite.setOrigin(goldSprite.getWidth() / 2, goldSprite.getHeight() / 2);
        addListener(new ClickListener(Input.Buttons.RIGHT) {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("clicked on single gold yo");

                return true;
            }
        });
        screen.stage.addActor(this);

        bounds = new Rectangle((int) getX(), (int) getY(), (int) getWidth(), (int) getHeight());

    }

    @Override
    public Rectangle getBounds() {
        return bounds;
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

    public String getName() {
        return name;
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
    public int getMaxResource() {
        return maxResource;
    }

    @Override
    public int getTotalResource() {
        return totalResource;
    }

    @Override
    public boolean isEmpty() {
        return empty;
    }
}
