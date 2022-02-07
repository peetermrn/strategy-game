package com.gamefromscratch.units;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.gamefromscratch.buildings.Building;
import com.gamefromscratch.path.Station;
import com.gamefromscratch.resources.Resource;
import com.gamefromscratch.screen.GameScreen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Unit extends Actor {
    private boolean alive = true;
    private Rectangle bounds;
    public int playerID;
    GameScreen screen;
    float speed = 64f;
    public boolean attackBoolean = true;
    public int hp = 100;
    public Integer maxHp = 100;
    int prevX = -1;
    int prevY = -1;
    int curX;
    public boolean isCarrying = false;
    int curY;
    SequenceAction sequence = new SequenceAction();
    List<Vector2> vectors = new ArrayList<>();
    Map<MoveToAction, List<Float>> actionMap = new HashMap<>();
    Map<Vector2, String> vectorMap = new HashMap<>();
    private MoveToAction runningAction;
    public Unit unit;
    public Building building;
    public Vector2 destVector;

    public Unit(){

    }
    public Unit(int playerID, GameScreen screen) {
        this.playerID = playerID;
        this.screen = screen;

    }

    public void clearAllActions() {
        this.clearActions();
        vectors.clear();
        actionMap.clear();
        sequence = new SequenceAction();
        vectorMap.clear();
    }

    public void addToActionSequence(Vector2 location, float duration, boolean last) {
        if ((location.x > 0 && location.y < 3200) && (location.y > 0 && location.y < 3200)) {
            if (prevY < 0) {
                prevY = Math.round(getY()) / 32;
            }
            if (prevX < 0) {
                prevX = Math.round(getX()) / 32;
            }
            curX = Math.round(location.x) / 32;
            curY = Math.round(location.y) / 32;
            String string = calculateAnimation(prevX, prevY, curX, curY);
            if (last) {
                string = setStraightAnimation(prevX, prevY, (int) location.x, (int) location.y);
            }
            prevY = curY;
            prevX = curX;
            runningAction = new MoveToAction();
            runningAction.setPosition(location.x, location.y);
            runningAction.setDuration(duration);
            actionMap.put(runningAction, Arrays.asList(location.x, location.y));
            sequence.addAction(runningAction);
            vectors.add(location);
            vectorMap.put(location, string);
        }

    }

    public String calculateAnimation(int pX, int pY, int cX, int cY) {
        if (cX == pX && cY > pY) {
            return "up";
        }
        if (cX == pX && cY < pY) {
            return "down";
        }
        if (cX < pX && cY == pY) {
            return "left";
        }
        if (cX > pX && cY == pY) {
            return "right";
        }
        if (cX < pX && cY < pY) {
            return "sw";
        }
        if (cX < pX && cY > pY) {
            return "nw";
        }
        if (cX > pX && cY > pY) {
            return "ne";
        }
        return "se";
    }


    public void moveTo(Vector2 location, boolean manual) {
        if ((location.x > 0 && location.y < 3200) && (location.y > 0 && location.y < 3200)) {

            this.clearAllActions();
            building = null;
            unit = null;
            if (manual) {
                isCarrying = false;
            }
            destVector = new Vector2(location);
            float x = location.x;
            float y = location.y;
            float destX = location.x;
            float destY = location.y;
            prevX = -1;
            prevY = -1;
            vectors.add(new Vector2(getX(), getY()));

            if (screen.stage.hit(x, y, true) instanceof Resource || screen.stage.hit(x, y, true) instanceof Building) {
                List<Float> tempList = findClosestBorder(this, screen.stage.hit(x, y, true));
                destX = tempList.get(0);
                destY = tempList.get(1);
            }
            List<Station> result;

            result = screen.path.findRoute(this.getX() + this.getWidth() / 2, this.getY(), destX - getWidth() / 2, destY - getHeight() / 2);

            float duration = calculateEuclideanDistance(getX(), getY(), x, y) / speed;
            if (result.size() > 2) {
                result = result.subList(1, result.size());
                duration = calculateEuclideanDistance((float) result.get(result.size() - 1).getX() * 32, (float) result.get(result.size() - 1).getY() * 32, x, y) / 64f;
            }
            for (Station station : result) {
                float dur = calculateEuclideanDistance((float) station.getX() * 32 + 16, (float) station.getY() * 32 + 16, vectors.get(vectors.size() - 1).x, vectors.get(vectors.size() - 1).y);
                this.addToActionSequence(new Vector2((float) station.getX() * 32 + 16, (float) station.getY() * 32 + 16), dur / speed, false);

            }
            this.addToActionSequence(new Vector2(x - this.getWidth() / 2, y - getHeight() / 2), duration, true);
            startSequence();
        }


    }
    public void setAnimation(){
        if (getActions().size > 0) {
            List<Vector2> tempList = new ArrayList<>(vectors);
            tempList.sort(Comparator.comparing(n -> calculateEuclideanDistance(n.x, n.y, this.getX(), this.getY())));
            List<Vector2> tempList2 = new ArrayList<>();

            if (tempList.size() > 2) {
                tempList2.add(tempList.get(0));
                tempList2.add(tempList.get(1));
                tempList2.sort(Comparator.comparing(n -> vectors.indexOf(n)));
                setWalkingAnimation(tempList2.get(1));
            }
            if (tempList.size() == 2) {
                setWalkingAnimation(vectors.get(1));
            }

        }
    }


    public void attackCloseEnemy(){
        List<Unit> tempList = new ArrayList<>(screen.unitList);
        tempList.remove(this);
        tempList.removeIf(n -> n.playerID == playerID);
        tempList.sort(Comparator.comparing(n -> calculateEuclideanDistance(n.getX(), n.getY(), this.getX(), this.getY())));
        if (tempList.size() > 0 && tempList.get(0).playerID != playerID && calculateEuclideanDistance(getX(), getY(), tempList.get(0).getX(), tempList.get(0).getY()) < 500) {
            moveTo(new Vector2(tempList.get(0).getX(), tempList.get(0).getY()), false);
        } else {
            List<Building> tempListBuilding = new ArrayList<>(screen.buildingList);
            tempListBuilding.removeIf(n -> n.playerID == playerID);
            tempListBuilding.sort(Comparator.comparing(n -> calculateEuclideanDistance(n.getX(), n.getY(), this.getX(), this.getY())));
            if (tempListBuilding.size() > 0 && tempListBuilding.get(0).playerID != playerID && calculateEuclideanDistance(getX(), getY(), tempListBuilding.get(0).getX(), tempListBuilding.get(0).getY()) < 500) {
                moveTo(new Vector2(tempListBuilding.get(0).getX() + tempListBuilding.get(0).getWidth() / 2, tempListBuilding.get(0).getY() + tempListBuilding.get(0).getHeight() / 2), false);
            }
        }
    }

    public void startSequence() {
        System.out.println("VALE KOHT");
    }


    public float calculateEuclideanDistance(float x1, float y1, float x2, float y2) {
        return (float) Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }


    public void setWalkingAnimation(Vector2 vector) {

    }

    public void Animation(Texture texture) {
    }

    public String setStraightAnimation(int pX, int pY, int cX, int cY) {
        Vector2 location = new Vector2(cX, cY);
        pX = pX * 32;
        pY = pY * 32;

        if (location.x > (float) pX && Math.abs(location.y - (float) pY) < 100) {
            return "right";

        } else if (location.x < (float) pX && Math.abs(location.y - (float) pY) < 100) {

            return "left";
        } else if (location.y > (float) pY && Math.abs(location.x - (float) pX) < 100) {

            return "up";
        } else if (location.y < (float) pY && Math.abs(location.x - (float) pX) < 100) {

            return "down";
        } else if (location.x > (float) pX && location.y > (float) pY) {

            return "ne";
        } else if (location.x > (float) pX & location.y < (float) pY) {

            return "se";
        } else if (location.x < (float) pX && location.y > (float) pY) {

            return "nw";
        }

        return "sw";

    }
    public void warriorUnitCollision(Unit unit) {
        Rectangle unitRec = new Rectangle(unit.getBounds().getX(), unit.getBounds().getY(), unit.getBounds().getWidth(), unit.getBounds().getHeight());
        Rectangle warriorRec = new Rectangle(getBounds().getX(), getBounds().getY(), getBounds().getWidth(), getBounds().getHeight());
        float y_dif = Math.abs(unitRec.getY() + unitRec.getHeight() / 2 - warriorRec.getY() - warriorRec.getHeight() / 2);
        float x_dif = Math.abs(unitRec.getX() + unitRec.getWidth() / 2 - warriorRec.getX() - warriorRec.getWidth() / 2);
        if ((warriorRec.getY() + warriorRec.getHeight() / 2 > unitRec.getY() + unitRec.getHeight()) && (warriorRec.getX() + warriorRec.getWidth() / 2 > unitRec.getX()) && (warriorRec.getX() + warriorRec.getWidth() / 2 < unitRec.getX() + unitRec.getWidth())) {
            startAttackAnimations("down", unit);

        } else if (warriorRec.getY() + warriorRec.getHeight() / 2 < unitRec.getY() && warriorRec.getX() + warriorRec.getWidth() / 2 > unitRec.getX() && warriorRec.getX() + warriorRec.getWidth() / 2 < unitRec.getX() + unitRec.getWidth()) {
            startAttackAnimations("up", unit);
        } else if (warriorRec.getX() + warriorRec.getWidth() / 2 < unitRec.getX() && warriorRec.getY() + warriorRec.getHeight() / 2 > unitRec.getY() && warriorRec.getY() + warriorRec.getHeight() / 2 < unitRec.getY() + unitRec.getHeight()) {
            startAttackAnimations("right", unit);
        } else if (warriorRec.getX() + warriorRec.getWidth() / 2 > unitRec.getX() + unitRec.getWidth() && warriorRec.getY() + warriorRec.getHeight() / 2 > unitRec.getY() && warriorRec.getY() + warriorRec.getHeight() / 2 < unitRec.getY() + unitRec.getHeight()) {
            startAttackAnimations("left", unit);
        } else if (x_dif < y_dif) {
            if (warriorRec.getY() < unitRec.getY()) {
                startAttackAnimations("up", unit);
            } else {
                startAttackAnimations("down", unit);
            }
        } else if (x_dif > y_dif) {
            if (warriorRec.getX() < unitRec.getX()) {
                startAttackAnimations("right", unit);
            } else {
                startAttackAnimations("left", unit);
            }
        }
    }
    public void warriorBuildingCollision(Building building) {
        Rectangle buildingRec = new Rectangle(building.getBounds().getX(), building.getBounds().getY(), building.getBounds().getWidth(), building.getBounds().getHeight());
        Rectangle warriorRec = new Rectangle(getBounds().getX(), getBounds().getY(), getBounds().getWidth(), getBounds().getHeight());
        float y_dif = Math.abs(buildingRec.getY() + buildingRec.getHeight() / 2 - warriorRec.getY() - warriorRec.getHeight() / 2);
        float x_dif = Math.abs(buildingRec.getX() + buildingRec.getWidth() / 2 - warriorRec.getX() - warriorRec.getWidth() / 2);
        if ((warriorRec.getY() + warriorRec.getHeight() / 2 > buildingRec.getY() + buildingRec.getHeight()) && (warriorRec.getX() + warriorRec.getWidth() / 2 > buildingRec.getX()) && (warriorRec.getX() + warriorRec.getWidth() / 2 < buildingRec.getX() + buildingRec.getWidth())) {
            startAttack("down", building);
        } else if (warriorRec.getY() + warriorRec.getHeight() / 2 < buildingRec.getY() && warriorRec.getX() + warriorRec.getWidth() / 2 > buildingRec.getX() && warriorRec.getX() + warriorRec.getWidth() / 2 < buildingRec.getX() + buildingRec.getWidth()) {
            startAttack("up", building);
        } else if (warriorRec.getX() + warriorRec.getWidth() / 2 < buildingRec.getX() && warriorRec.getY() + warriorRec.getHeight() / 2 > buildingRec.getY() && warriorRec.getY() + warriorRec.getHeight() / 2 < buildingRec.getY() + buildingRec.getHeight()) {
            startAttack("right", building);
        } else if (warriorRec.getX() + warriorRec.getWidth() / 2 > buildingRec.getX() + buildingRec.getWidth() && warriorRec.getY() + warriorRec.getHeight() / 2 > buildingRec.getY() && warriorRec.getY() + warriorRec.getHeight() / 2 < buildingRec.getY() + buildingRec.getHeight()) {
            startAttack("left", building);
        } else if (x_dif < y_dif) {
            if (warriorRec.getY() < buildingRec.getY()) {
                startAttack("up", building);
            } else {
                startAttack("down", building);
            }
        } else if (x_dif > y_dif) {
            if (warriorRec.getX() < buildingRec.getX()) {
                startAttack("right", building);
            } else {
                startAttack("left", building);
            }
        }

    }
    public List<Float> findClosestBorder(Actor currentActor, Actor actor) {
        float actorX = actor.getX();
        float actorY = actor.getY();
        float actorWidth = actor.getWidth();
        float actorHeight = actor.getHeight();
        float x = currentActor.getX();
        float y = currentActor.getY();
        if (actor instanceof Building) {
            actorX = ((Building) actor).getBounds().getX();
            actorY = ((Building) actor).getBounds().getY();
            actorWidth = ((Building) actor).getBounds().getWidth();
            actorHeight = ((Building) actor).getBounds().getHeight();
        }
        if (actor instanceof Resource) {
            actorX = ((Resource) actor).getBounds().getX();
            actorY = ((Resource) actor).getBounds().getY();
            actorWidth = ((Resource) actor).getBounds().getWidth();
            actorHeight = ((Resource) actor).getBounds().getHeight();
        }
        Map<Float, List<Float>> tempMap = new HashMap<>();
        tempMap.put(calculateEuclideanDistance(x, y, actorX + actorWidth / 2, actorY - 32), Arrays.asList(actorX + actorWidth / 2, actorY - 32)); //bot
        tempMap.put(calculateEuclideanDistance(x, y, actorX + actorWidth / 2, actorY + actorHeight + 32), Arrays.asList(actorX + actorWidth / 2, actorY + actorHeight + 32)); //top
        tempMap.put(calculateEuclideanDistance(x, y, actorX - 32, actorY + actorHeight / 2), Arrays.asList(actorX - 32, actorY + actorHeight / 2)); //left
        tempMap.put(calculateEuclideanDistance(x, y, actorX + actorWidth + 32, actorY + actorHeight / 2), Arrays.asList(actorX + actorWidth + 32, actorY + actorHeight / 2)); //right
        List<Float> sortedKeys = new ArrayList<>(tempMap.keySet());


        Collections.sort(sortedKeys);
        return tempMap.get(sortedKeys.get(0));
    }




    public Integer getHp() {
        return hp;
    }

    public void startAttackAnimations(String direction, Unit target) {
        System.out.println("VALE KOHT");
    }

    public void startAttack(String direction, Building building) {

    }

    public Rectangle getBounds() {
        System.out.println("VALE KOHT VALE KOHT");
        return bounds;
    }

    public Rectangle getSecondBounds() {
        return bounds;
    }

    public int getPlayerID() {
        return playerID;
    }


    public boolean isAlive() {
        return alive;
    }


    public void goToStand() {

    }

    public void takeOne(Unit unit, int damage) {
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
        Sprite death;
        if (this instanceof Worker) {

            death = new Sprite(new Texture(Gdx.files.internal("corpses/worker" + playerID + ".png")));
        } else if (this instanceof Warrior) {

            death = new Sprite(new Texture(Gdx.files.internal("corpses/warrior" + playerID + ".png")));
        } else if (this instanceof ManAtArms) {

            death = new Sprite(new Texture(Gdx.files.internal("corpses/ManAtArms" + playerID + ".png")));
        } else {
            death = new Sprite(new Texture(Gdx.files.internal("corpses/knight" + playerID + ".png")));
        }
        if (this instanceof Worker) {
            death.setPosition(this.getX() - 20, this.getY() - 75);
        } else {
            death.setPosition(this.getX(), this.getY());
        }


        screen.graves.add(death);

        screen.miniMap.removePlayerFromMinimap(this);
        screen.popBank.takeOneCurrent(playerID);
        screen.warriors.remove(this);
        screen.unitList.remove(this);
        this.setPosition(0, 0);
        alive = false;

        remove();
    }

    public Integer getMaxHp() {
        return maxHp;
    }
}
