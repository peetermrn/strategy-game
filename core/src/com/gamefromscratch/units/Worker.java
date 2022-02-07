package com.gamefromscratch.units;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.gamefromscratch.buildings.Building;
import com.gamefromscratch.buildings.Camp;
import com.gamefromscratch.buildings.TownCenter;
import com.gamefromscratch.resources.Resource;
import com.gamefromscratch.resources.Tree;
import com.gamefromscratch.screen.GameScreen;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Worker extends Unit {
    //walk
    Texture right;
    Texture ne;
    Texture se;
    Texture up;
    Texture down;
    Texture left;
    Texture sw;
    Texture nw;
    Texture stand;
    //mine
    Texture mineUp;
    Texture mineDown;
    Texture mineLeft;
    Texture mineRight;
    //chop tree
    Texture chopUp;
    Texture chopDown;
    Texture chopLeft;
    Texture chopRight;
    //build
    Texture buildUp;
    Texture buildDown;
    Texture buildLeft;
    Texture buildRight;

    TextureRegion region;
    private boolean alive = true;
    public Integer currentWood = 0;
    public Integer currentGold = 0;
    private boolean selected = false;
    public CURRENT_TASK currentTask = CURRENT_TASK.IDLE;
    private TownCenter townCenter;
    Sprite workerSprite = new Sprite(new Texture("unitsprites/worker_stand.png"));
    Rectangle bounds;
    public Texture currentTexture;
    public int woodCapacity = 20;
    public int goldCapacity = 10;
    public int chopSpeed = 30;
    Actor tempActor;
    Animation<TextureRegion> walk;
    float time;
    private int COLUMN = 3;
    private int ROW = 5;
    private int currentTaskCounter = 0;
    Resource resource;
    Building building;
    private String name;
    public Worker(){

    }
    public Worker(GameScreen screen, TownCenter townCenter, float x, float y, int playerID) {
        super(playerID, screen);
        right = new Texture(Gdx.files.internal("walkanimations/right" + playerID + ".png"));
        ne = new Texture(Gdx.files.internal("walkanimations/upRight" + playerID + ".png"));
        se = new Texture(Gdx.files.internal("walkanimations/downRight" + playerID + ".png"));
        up = new Texture(Gdx.files.internal("walkanimations/up_walk" + playerID + ".png"));
        down = new Texture(Gdx.files.internal("walkanimations/down_walk" + playerID + ".png"));
        left = new Texture(Gdx.files.internal("walkanimations/left" + playerID + ".png"));
        sw = new Texture(Gdx.files.internal("walkanimations/downLeft" + playerID + ".png"));
        nw = new Texture(Gdx.files.internal("walkanimations/upLeft" + playerID + ".png"));
        stand = new Texture(Gdx.files.internal("walkanimations/stand" + playerID + ".png"));

        mineUp = new Texture(Gdx.files.internal("mininganimation/mineup" + playerID + ".png"));
        mineDown = new Texture(Gdx.files.internal("mininganimation/mineDown" + playerID + ".png"));
        mineLeft = new Texture(Gdx.files.internal("mininganimation/mineLeft" + playerID + ".png"));
        mineRight = new Texture(Gdx.files.internal("mininganimation/mineRight" + playerID + ".png"));

        chopUp = new Texture(Gdx.files.internal("woodchoppinganimation/axeUp" + playerID + ".png"));
        chopDown = new Texture(Gdx.files.internal("woodchoppinganimation/axeDown" + playerID + ".png"));
        chopLeft = new Texture(Gdx.files.internal("woodchoppinganimation/axeLeft" + playerID + ".png"));
        chopRight = new Texture(Gdx.files.internal("woodchoppinganimation/axeRight" + playerID + ".png"));

        buildUp = new Texture(Gdx.files.internal("buildinganimation/buildUp" + playerID + ".png"));
        buildDown = new Texture(Gdx.files.internal("buildinganimation/buildDown" + playerID + ".png"));
        buildLeft = new Texture(Gdx.files.internal("buildinganimation/buildLeft" + playerID + ".png"));
        buildRight = new Texture(Gdx.files.internal("buildinganimation/buildRight" + playerID + ".png"));

        hp = 50;
        maxHp = 50;

        Animation(stand);
        this.screen = screen;
        this.playerID = playerID;
        setSpriteStart(x, y);
        this.setBounds(workerSprite.getX(), workerSprite.getY(), workerSprite.getWidth(), workerSprite.getHeight());
        this.setTouchable(Touchable.enabled);
        this.setName("Worker");
        this.name = "Worker";
        this.addListener(new ClickListener(Input.Buttons.LEFT) {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("SELECT");

                return true;
            }
        });
        workerSprite.setOrigin(workerSprite.getWidth() / 2, workerSprite.getWidth() / 2);
        this.townCenter = townCenter;
        screen.stage.addActor(this);
        screen.workerList.add(this);
        screen.unitList.add(this);
        screen.miniMap.addPlayerToMinimap(this, playerID);


    }

    public String getName() {
        return name;
    }

    public void startMiningAnimation(String direction, Resource resource) {
        determineDirection(direction, mineUp, mineDown, mineRight, mineLeft);
        isCarrying = false;
        this.resource = resource;
        setCurrentTask(CURRENT_TASK.GATHER_GOLD);
    }

    private void determineDirection(String direction, Texture mineUp, Texture mineDown, Texture mineRight, Texture mineLeft) {
        clearActions();
        if (direction.equals("up")) {
            Animation(mineUp);
        } else if (direction.equals("down")) {
            Animation(mineDown);
        } else if (direction.equals("right")) {
            Animation(mineRight);
        } else if (direction.equals("left")) {
            Animation(mineLeft);
        }
    }

    public void startBuildingAnimation(String direction, Building building) {
        this.building = building;
        determineDirection(direction, buildUp, buildDown, buildRight, buildLeft);
        setCurrentTask(CURRENT_TASK.BUILD);
    }

    public void startWoodChoppingAnimation(String direction, Resource resource) {
        determineDirection(direction, chopUp, chopDown, chopRight, chopLeft);
        this.resource = resource;
        isCarrying = false;
        setCurrentTask(CURRENT_TASK.GATHER_WOOD);
    }

    public void setSpriteStart(float x, float y) {
        workerSprite.setPosition(x - workerSprite.getWidth() / 2, y - workerSprite.getHeight() / 2);
    }

    @Override
    public Rectangle getBounds() {
        bounds = new Rectangle((int) getX() + getWidth() / 3, (int) getY(), (int) getWidth() / 3f, (int) getHeight() / 3f);
        return bounds;
    }

    @Override
    public Rectangle getSecondBounds() {
        bounds = new Rectangle((int) getX(), (int) getY(), (int) getWidth(), (int) getHeight());
        return bounds;
    }

    @Override
    public void startSequence() {
        this.addAction(sequence);
        currentTask = Worker.CURRENT_TASK.WALK;
    }


    @Override
    public void setWalkingAnimation(Vector2 vector) {
        String dir = vectorMap.get(vector);
        if (dir.equals("right")) {
            Animation(right);
        } else if (dir.equals("left")) {
            Animation(left);
        } else if (dir.equals("up")) {
            Animation(up);
        } else if (dir.equals("down")) {
            Animation(down);
        } else if (dir.equals("ne")) {
            Animation(ne);
        } else if (dir.equals("se")) {
            Animation(se);
        } else if (dir.equals("nw")) {
            Animation(nw);
        } else if (dir.equals("sw")) {
            Animation(sw);
        }
    }

    @Override
    public void Animation(Texture texture) {
        if (currentTexture != texture) {
            currentTaskCounter = 0;
            if (texture.equals(stand)) {
                COLUMN = 1;
                ROW = 15;
            }
            if (texture.toString().contains("build")) {
                COLUMN = 15;
                ROW = 1;
            }
            currentTexture = texture;
            TextureRegion[][] temp = TextureRegion.split(texture, texture.getWidth() / COLUMN, texture.getHeight() / ROW);
            TextureRegion[] frames = new TextureRegion[COLUMN * ROW];
            int index = 0;

            for (int i = 0; i < ROW; i++) {
                for (int j = 0; j < COLUMN; j++) {
                    frames[index++] = temp[i][j];
                }
            }
            if (texture == stand) {
                walk = new Animation<TextureRegion>(0.2f, frames);
            } else {
                walk = new Animation<TextureRegion>(0.08f, frames);
            }
            time = 0f;
            region = walk.getKeyFrame(0);
            COLUMN = 3;
            ROW = 5;
        }
    }


    @Override
    public void act(float delta) {
        super.act(delta);
        if (this.getActions().size == 0 && currentTexture != stand && (currentTask == CURRENT_TASK.IDLE || currentTask == CURRENT_TASK.WALK)) {
            currentTask = CURRENT_TASK.IDLE;
            Animation(stand);
            destVector = null;
        }
        if (isCarrying && currentTask == CURRENT_TASK.IDLE) {
            goToClosestResource();
        }
        if (currentTask == CURRENT_TASK.GATHER_WOOD || currentTask == CURRENT_TASK.GATHER_GOLD) {
            gatherResources();
        }
        setAnimation();
        if (currentTask == CURRENT_TASK.BUILD) {
            build();
        }
        if (screen.selectedActor == this) {
            screen.informationUiTable.setGoldAndWoodAmount(this, currentGold, currentWood);
        }
    }

    public void gatherResources() {
        currentTaskCounter++;
        if (resource.isEmpty()) {
            goToClosestResource();
        } else if (currentTaskCounter >= chopSpeed) {
            if (currentTask == CURRENT_TASK.GATHER_WOOD) {
                currentWood++;
            }
            if (currentTask == CURRENT_TASK.GATHER_GOLD) {
                currentGold++;
            }
            resource.takeOne();
            if (currentWood >= woodCapacity && currentTask == CURRENT_TASK.GATHER_WOOD && !isCarrying) {
                goToCamp();
                isCarrying = true;
            }
            if (currentGold >= goldCapacity && currentTask == CURRENT_TASK.GATHER_GOLD && !isCarrying) {
                goToCamp();
                isCarrying = true;
            }
            currentTaskCounter = 0;
        }
    }

    public void build() {
        currentTaskCounter++;
        if (currentTaskCounter >= 10) {
            building.takeOne();
            currentTaskCounter = 0;
        }
        if (building.isReady()) {
            currentTask = CURRENT_TASK.IDLE;
        }
    }

    public void goToClosestResource() {
        currentTask = CURRENT_TASK.WALK;
        float dist = 3200;
        Resource toGotTo = screen.resourceList.get(0);
        if (resource instanceof Tree) {
            for (Resource res : screen.treeList) {
                if (calculateEuclideanDistance(this.getX(), this.getY(), res.getX(), res.getY()) < dist) {
                    dist = calculateEuclideanDistance(this.getX(), this.getY(), res.getX(), res.getY());
                    toGotTo = res;
                }
            }
        } else {
            for (Resource res : screen.goldList) {
                if (calculateEuclideanDistance(this.getX(), this.getY(), res.getX(), res.getY()) < dist) {
                    dist = calculateEuclideanDistance(this.getX(), this.getY(), res.getX(), res.getY());
                    toGotTo = res;
                }
            }
        }
        tempActor = toGotTo;
        currentTask = CURRENT_TASK.WALK;
        this.moveTo(new Vector2(toGotTo.getBounds().getX() + toGotTo.getBounds().getWidth() / 2, toGotTo.getBounds().getY() + 20), false);
    }

    public void goToCamp() {
        List<Building> tempList = new ArrayList<>();
        tempList.add(townCenter);
        for (Building building : screen.resourceDropOf) {
            if (building instanceof Camp) {
                if (building.playerID == playerID) {
                    tempList.add(building);
                }
            }
        }
        tempList.sort(Comparator.comparing(n -> calculateEuclideanDistance(getX(), getY(), n.getX(), n.getY())));
        Building toGotTo = tempList.get(0);
        tempActor = toGotTo;
        this.moveTo(new Vector2(toGotTo.getBounds().getX() + toGotTo.getBounds().getWidth() / 2, toGotTo.getBounds().getY() + toGotTo.getBounds().getWidth() / 2), false);
    }

    public void workerGoldCollision(Resource resource) {
        if (currentTask != Worker.CURRENT_TASK.GATHER_GOLD) {
            clearAllActions();
            currentTask = Worker.CURRENT_TASK.GATHER_GOLD;
            isCarrying = false;
            Rectangle buildingRec = new Rectangle(resource.getBounds().getX(), resource.getBounds().getY(), resource.getBounds().getWidth(), resource.getBounds().getHeight());
            float y_dif = Math.abs(buildingRec.getY() + buildingRec.getHeight() / 2 - getY() - getHeight() / 2);
            float x_dif = Math.abs(buildingRec.getX() + buildingRec.getWidth() / 2 - getX() - getWidth() / 2);
            if ((getY() + getHeight() / 2 > buildingRec.getY() + buildingRec.getHeight()) && (getX() + getWidth() / 2 > buildingRec.getX()) && (getX() + getWidth() / 2 < buildingRec.getX() + buildingRec.getWidth())) {
                startMiningAnimation("down", resource);

            } else if (getY() + getHeight() / 2 < buildingRec.getY() && getX() + getWidth() / 2 > buildingRec.getX() && getX() + getWidth() / 2 < buildingRec.getX() + buildingRec.getWidth()) {
                startMiningAnimation("up", resource);
            } else if (getX() + getWidth() / 2 < buildingRec.getX() && getY() + getHeight() / 2 > buildingRec.getY() && getY() + getHeight() / 2 < buildingRec.getY() + buildingRec.getHeight()) {
                startMiningAnimation("right", resource);
            } else if (getX() + getWidth() / 2 > buildingRec.getX() + buildingRec.getWidth() && getY() + getHeight() / 2 > buildingRec.getY() && getY() + getHeight() / 2 < buildingRec.getY() + buildingRec.getHeight()) {
                startMiningAnimation("left", resource);
            } else if (x_dif < y_dif) {
                if (getY() < buildingRec.getY()) {
                    startMiningAnimation("up", resource);
                } else {
                    startMiningAnimation("down", resource);
                }
            } else if (x_dif > y_dif) {
                if (getX() < buildingRec.getX()) {
                    startMiningAnimation("right", resource);
                } else {
                    startMiningAnimation("left", resource);
                }
            }
        }
    }

    public void workerTreeCollision(Resource resource) {
        if (currentTask != Worker.CURRENT_TASK.GATHER_WOOD) {
            clearAllActions();
            currentTask = Worker.CURRENT_TASK.GATHER_WOOD;
            isCarrying = false;
            Rectangle buildingRec = new Rectangle(resource.getBounds().getX(), resource.getBounds().getY(), resource.getBounds().getWidth(), resource.getBounds().getHeight());
            float y_dif = Math.abs(buildingRec.getY() + buildingRec.getHeight() / 2 - getY() - getHeight() / 2);
            float x_dif = Math.abs(buildingRec.getX() + buildingRec.getWidth() / 2 - getX() - getWidth() / 2);
            if ((getY() + getHeight() / 2 > buildingRec.getY() + buildingRec.getHeight()) && (getX() + getWidth() / 2 > buildingRec.getX()) && (getX() + getWidth() / 2 < buildingRec.getX() + buildingRec.getWidth())) {
                startWoodChoppingAnimation("down", resource);

            } else if (getY() + getHeight() / 2 < buildingRec.getY() && getX() + getWidth() / 2 > buildingRec.getX() && getX() + getWidth() / 2 < buildingRec.getX() + buildingRec.getWidth()) {
                startWoodChoppingAnimation("up", resource);
            } else if (getX() + getWidth() / 2 < buildingRec.getX() && getY() + getHeight() / 2 > buildingRec.getY() && getY() + getHeight() / 2 < buildingRec.getY() + buildingRec.getHeight()) {
                startWoodChoppingAnimation("right", resource);
            } else if (getX() + getWidth() / 2 > buildingRec.getX() + buildingRec.getWidth() && getY() + getHeight() / 2 > buildingRec.getY() && getY() + getHeight() / 2 < buildingRec.getY() + buildingRec.getHeight()) {
                startWoodChoppingAnimation("left", resource);
            } else if (x_dif < y_dif) {
                if (getY() < buildingRec.getY()) {
                    startWoodChoppingAnimation("up", resource);
                } else {
                    startWoodChoppingAnimation("down", resource);
                }
            } else if (x_dif > y_dif) {
                if (getX() < buildingRec.getX()) {
                    startWoodChoppingAnimation("right", resource);
                } else {
                    startWoodChoppingAnimation("left", resource);
                }
            }
        }
    }

    public void workerHouseCollision(Building building) {
        Rectangle buildingRec = new Rectangle(building.getBounds().getX(), building.getBounds().getY(), building.getBounds().getWidth(), building.getBounds().getHeight());
        float y_dif = Math.abs(buildingRec.getY() + buildingRec.getHeight() / 2 - getY() - getHeight() / 2);
        float x_dif = Math.abs(buildingRec.getX() + buildingRec.getWidth() / 2 - getX() - getWidth() / 2);
        if ((getY() + getHeight() / 2 > buildingRec.getY() + buildingRec.getHeight()) && (getX() + getWidth() / 2 > buildingRec.getX()) && (getX() + getWidth() / 2 < buildingRec.getX() + buildingRec.getWidth())) {
            startBuildingAnimation("down", building);

        } else if (getY() + getHeight() / 2 < buildingRec.getY() && getX() + getWidth() / 2 > buildingRec.getX() && getX() + getWidth() / 2 < buildingRec.getX() + buildingRec.getWidth()) {
            startBuildingAnimation("up", building);
        } else if (getX() + getWidth() / 2 < buildingRec.getX() && getY() + getHeight() / 2 > buildingRec.getY() && getY() + getHeight() / 2 < buildingRec.getY() + buildingRec.getHeight()) {
            startBuildingAnimation("right", building);
        } else if (getX() + getWidth() / 2 > buildingRec.getX() + buildingRec.getWidth() && getY() + getHeight() / 2 > buildingRec.getY() && getY() + getHeight() / 2 < buildingRec.getY() + buildingRec.getHeight()) {
            startBuildingAnimation("left", building);
        } else if (x_dif < y_dif) {
            if (getY() < buildingRec.getY()) {
                startBuildingAnimation("up", building);
            } else {
                startBuildingAnimation("down", building);
            }
        } else if (x_dif > y_dif) {
            if (getX() < buildingRec.getX()) {
                startBuildingAnimation("right", building);
            } else {
                startBuildingAnimation("left", building);
            }
        }
    }

    @Override
    public void draw(Batch batch, float alpha) {
        time += Gdx.graphics.getDeltaTime();
        TextureRegion current = walk.getKeyFrame(time, true);
        batch.draw(current, getX(), getY());
    }

    @Override
    public boolean addListener(EventListener listener) {
        return super.addListener(listener);
    }


    public void setCurrentTask(CURRENT_TASK currentTask) {
        this.currentTask = currentTask;
    }

    public enum CURRENT_TASK {
        IDLE, BUILD, GATHER_WOOD, GATHER_GOLD, WALK
    }

    public void dropOfResources() {
        screen.reBank.addToBank(currentWood, currentGold, playerID);

        currentGold = 0;

        currentWood = 0;
    }

    public Integer getHp() {
        return hp;
    }

    @Override
    public int getPlayerID() {
        return playerID;
    }


    @Override
    public boolean isAlive() {
        return alive;
    }
}
