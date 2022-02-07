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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Tree extends Resource {
    Sprite treeSprite;
    GameScreen screen;
    Rectangle bounds;
    private int totalResource = 100;
    private int maxResource = 100;
    public boolean empty = false;
    private String name;


    public Tree(GameScreen screen, int x, int y, boolean lonelyTree) {
        this.screen = screen;
        this.name = "Tree";
        if (lonelyTree) {
            treeSprite = new Sprite(new Texture(Gdx.files.internal("resources/Tree0002.png")));
            totalResource = 200;
            maxResource = 200;
        } else {
            Random rand = new Random();
            List<String> treePNGs = new ArrayList<>(Arrays.asList("resources/Tree001.png", "resources/Tree002.png", "resources/Tree003.png"));
            treeSprite = new Sprite(new Texture(Gdx.files.internal(treePNGs.get(rand.nextInt(3)))));
        }
        setBounds(treeSprite.getX(), treeSprite.getY(), treeSprite.getWidth(), treeSprite.getHeight());
        setTouchable(Touchable.enabled);

        treeSprite.setOrigin(treeSprite.getWidth() / 2, treeSprite.getHeight() / 2);
        if (y % 2 != 0) {
            treeSprite.setPosition(32 + 96 * x - treeSprite.getWidth() / 2, y * 96 - treeSprite.getHeight() / 2);
            setPosition(32 + 96 * x - treeSprite.getWidth() / 2, y * 96 - treeSprite.getHeight() / 2);
        } else {
            setPosition(96 * x - treeSprite.getWidth() / 2, y * 96 - treeSprite.getHeight() / 2);
            treeSprite.setPosition(96 * x - treeSprite.getWidth() / 2, y * 96 - treeSprite.getHeight() / 2);
        }
        this.screen.stage.addActor(this);

        addListener(new ClickListener(Input.Buttons.RIGHT) {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("clicked a tree yo");
                return true;
            }
        });
        bounds = new Rectangle((int) getX() + getWidth() / 3f, (int) getY(), (int) getWidth() / 3f, (int) getHeight() / 8f);

    }

    public String getName() {
        return name;
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
        batch.draw(treeSprite, getX(), getY());
    }

    @Override
    public void takeOne() {
        totalResource -= 1;
        if (totalResource == 0) {
            screen.path.addNodes(this);
            screen.miniMap.removeResourceFromMap(this);
            screen.treeList.remove(this);
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
    public boolean isEmpty() {
        return empty;
    }

    @Override
    public int getTotalResource() {
        return totalResource;
    }
}
