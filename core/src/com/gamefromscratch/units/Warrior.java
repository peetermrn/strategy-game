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
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.gamefromscratch.buildings.Barracks;
import com.gamefromscratch.buildings.Building;
import com.gamefromscratch.screen.GameScreen;

public class Warrior extends Unit {
    public static int maxHp = 75;
    public Integer attackDamage = 10;
    Sprite warrior = new Sprite(new Texture(Gdx.files.internal("unitsprites/spearman.png")));
    private String name;
    public int playerID;
    Rectangle bounds;
    GameScreen screen;
    public Texture currentTexture;
    private Barracks barracks;
    Animation<TextureRegion> walk;
    float time;
    private int COLUMN = 10;
    private int ROW = 1;

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
    //attack
    Texture attackUp;
    Texture attackDown;
    Texture attackLeft;
    Texture attackRight;

    TextureRegion region;
    private int currentTaskCounter;
    private CURRENT_TASK currentTask = CURRENT_TASK.IDLE;
    public Warrior(){

    }

    public Warrior(GameScreen screen, Barracks barracks, float x, float y, int playerID) {
        super(playerID, screen);
        this.screen = screen;
        this.barracks = barracks;
        this.playerID = playerID;
        this.name = "Spearman";

        maxHp = 75;
        hp = 75;

        right = new Texture(Gdx.files.internal("spearmananimationsforall/walkRight" + playerID + ".png"));
        ne = new Texture(Gdx.files.internal("spearmananimationsforall/walkUpRight" + playerID + ".png"));
        se = new Texture(Gdx.files.internal("spearmananimationsforall/walkDownRight" + playerID + ".png"));
        up = new Texture(Gdx.files.internal("spearmananimationsforall/walkUp" + playerID + ".png"));
        down = new Texture(Gdx.files.internal("spearmananimationsforall/walkDown" + playerID + ".png"));
        left = new Texture(Gdx.files.internal("spearmananimationsforall/walkLeft" + playerID + ".png"));
        sw = new Texture(Gdx.files.internal("spearmananimationsforall/walkDownLeft" + playerID + ".png"));
        nw = new Texture(Gdx.files.internal("spearmananimationsforall/walkUpLeft" + playerID + ".png"));
        stand = new Texture(Gdx.files.internal("spearmananimationsforall/stand" + playerID + ".png"));

        attackUp = new Texture(Gdx.files.internal("spearmananimationsforall/attackUp" + playerID + ".png"));
        attackDown = new Texture(Gdx.files.internal("spearmananimationsforall/attackDown" + playerID + ".png"));
        attackLeft = new Texture(Gdx.files.internal("spearmananimationsforall/attackLeft" + playerID + ".png"));
        attackRight = new Texture(Gdx.files.internal("spearmananimationsforall/attackRight" + playerID + ".png"));

        Animation(stand);
        setSpriteStart(x, y);
        spritePos(warrior.getX(), warrior.getY());

        this.setTouchable(Touchable.enabled);
        this.setName("Spearman");
        this.addListener(new ClickListener(Input.Buttons.LEFT) {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("SELECT");

                return true;
            }
        });
        warrior.setOrigin(warrior.getWidth() / 2, warrior.getHeight() / 2);
        screen.stage.addActor(this);
        screen.warriors.add(this);
        screen.unitList.add(this);
        screen.miniMap.addPlayerToMinimap(this, playerID);

        moveTo(new Vector2(x - 30, barracks.getY() - 170), true);

    }


    public String getName() {
        return name;
    }

    public Integer getHp() {
        return hp;
    }


    public Integer getAttackDamage() {
        return attackDamage;
    }

    public void setSpriteStart(float x, float y) {
        warrior.setPosition(x - warrior.getWidth() / 2, y - warrior.getHeight() / 2);
    }


    public void spritePos(float x, float y) {
        this.setBounds(warrior.getX(), warrior.getY(), warrior.getWidth(), warrior.getHeight());
    }

    @Override
    public Rectangle getBounds() {
        bounds = new Rectangle((int) getX() + getWidth() / 2 - 10, (int) getY(), 20, 20);
        return bounds;
    }


    @Override
    public void startSequence() {
        this.addAction(sequence);
        currentTask = CURRENT_TASK.WALK;
    }


    @Override
    public void startAttackAnimations(String direction, Unit target) {
        this.unit = target;
        building = null;

        if (currentTask != CURRENT_TASK.ATTACK) {
            clearActions();
            if (direction.equals("up")) {
                Animation(attackUp);
            } else if (direction.equals("down")) {
                Animation(attackDown);
            } else if (direction.equals("right")) {
                Animation(attackRight);
            } else if (direction.equals("left")) {
                Animation(attackLeft);
            }

            setCurrentTask(CURRENT_TASK.ATTACK);

        }
    }

    @Override
    public void startAttack(String direction, Building target) {
        this.building = target;
        unit = null;

        if (currentTask != CURRENT_TASK.ATTACK) {
            clearActions();
            if (direction.equals("up")) {
                Animation(attackUp);
            } else if (direction.equals("down")) {
                Animation(attackDown);
            } else if (direction.equals("right")) {
                Animation(attackRight);
            } else if (direction.equals("left")) {
                Animation(attackLeft);
            }

            setCurrentTask(CURRENT_TASK.ATTACK);

        }
    }

    @Override
    public Rectangle getSecondBounds() {
        bounds = new Rectangle((int) getX(), (int) getY(), (int) getWidth(), (int) getHeight());
        return bounds;
    }

    @Override
    public void Animation(Texture texture) {
        if (currentTexture != texture) {
            currentTaskCounter = 0;
            if (texture.equals(stand)) {
                COLUMN = 3;
                ROW = 3;
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
            COLUMN = 10;
            ROW = 1;
        }
    }


    @Override
    public void act(float delta) {
        super.act(delta);
        if (this.getActions().size == 0 && currentTexture != stand && currentTask != CURRENT_TASK.ATTACK) {
            Animation(stand);
            currentTask = CURRENT_TASK.IDLE;
            destVector = null;
            building = null;
            unit = null;
        }
        setAnimation();
        if (currentTask == CURRENT_TASK.ATTACK) {
            currentTaskCounter++;
            if (currentTaskCounter >= 70) {
                if (building != null) {
                    building.destroy(this, attackDamage);
                    currentTaskCounter = 0;
                }
                if (unit != null) {
                    if (calculateEuclideanDistance(getSecondBounds().getX() + getSecondBounds().getWidth() / 2, getSecondBounds().getY() + getSecondBounds().getHeight() / 2, unit.getSecondBounds().getX() + unit.getSecondBounds().getWidth() / 2, unit.getSecondBounds().getY() + unit.getSecondBounds().getHeight() / 2) > 95) {
                        goToStand();
                    } else {
                        unit.takeOne(this, attackDamage);
                        currentTaskCounter = 0;
                    }

                }
                if (building != null && !building.alive) {
                    goToStand();
                }
            }

        }
        if (attackBoolean && currentTask == Warrior.CURRENT_TASK.IDLE && getActions().size == 0 && building == null && unit == null) {
            attackCloseEnemy();
        }
    }

    @Override
    public void draw(Batch batch, float alpha) {
        time += Gdx.graphics.getDeltaTime();
        TextureRegion current = walk.getKeyFrame(time, true);
        setBounds(getX(), getY(), current.getRegionWidth(), current.getRegionHeight());
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
        IDLE, ATTACK, WALK
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
    public void goToStand() {
        if (currentTask != Warrior.CURRENT_TASK.IDLE) {
            currentTask = Warrior.CURRENT_TASK.IDLE;
            building = null;
            unit = null;
            Animation(stand);
        }
    }

}
