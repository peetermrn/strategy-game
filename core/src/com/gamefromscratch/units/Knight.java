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

public class Knight extends Unit {
    public static int COUNTERLIMIT = 70;

    private boolean alive = true;
    public static Integer attackDamage = 15;
    private String name;
    Sprite warrior;


    Rectangle bounds;

    public Texture currentTexture;


    Animation<TextureRegion> walk;
    float time;

    private int COLUMN = 11;
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
    private Knight.CURRENT_TASK currentTask = Knight.CURRENT_TASK.IDLE;

    public Knight() {

    }

    public Knight(GameScreen screen, Barracks barracks, float x, float y, int playerID) {
        super(playerID, screen);
        this.screen = screen;

        this.playerID = playerID;

        right = new Texture(Gdx.files.internal("warriors/right" + playerID + ".png"));
        ne = new Texture(Gdx.files.internal("warriors/upRight" + playerID + ".png"));
        se = new Texture(Gdx.files.internal("warriors/downRight" + playerID + ".png"));
        up = new Texture(Gdx.files.internal("warriors/up" + playerID + ".png"));
        down = new Texture(Gdx.files.internal("warriors/down" + playerID + ".png"));
        left = new Texture(Gdx.files.internal("warriors/left" + playerID + ".png"));
        sw = new Texture(Gdx.files.internal("warriors/downLeft" + playerID + ".png"));
        nw = new Texture(Gdx.files.internal("warriors/upLeft" + playerID + ".png"));
        stand = new Texture(Gdx.files.internal("warriors/stand" + playerID + ".png"));

        attackUp = new Texture(Gdx.files.internal("warriors/attackUp" + playerID + ".png"));
        attackDown = new Texture(Gdx.files.internal("warriors/attackDown" + playerID + ".png"));
        attackLeft = new Texture(Gdx.files.internal("warriors/attackLeft" + playerID + ".png"));
        attackRight = new Texture(Gdx.files.internal("warriors/attackRight" + playerID + ".png"));


        if (playerID == 1) {
            hp = 150;
            maxHp = 150;

            attackDamage = 15;
            this.name = "Berserk";
            warrior = new Sprite(new Texture(Gdx.files.internal("unitsprites/berserk.png")));
        }
        if (playerID == 2) {
            COUNTERLIMIT = 50;
            hp = 100;

            maxHp = 100;
            attackDamage = 20;
            speed = 90f;
            this.name = "Hussar";
            warrior = new Sprite(new Texture(Gdx.files.internal("unitsprites/hussar.png")));
        }
        if (playerID == 3) {
            hp = 120;
            maxHp = 120;

            attackDamage = 15;
            this.name = "Jaguar warrior";
            warrior = new Sprite(new Texture(Gdx.files.internal("unitsprites/jaguar.png")));
        }
        if (playerID == 4) {
            hp = 100;
            maxHp = 100;

            attackDamage = 20;
            speed = 80f;
            this.name = "Samurai";
            warrior = new Sprite(new Texture(Gdx.files.internal("unitsprites/samurai.png")));
        }


        Animation(stand);
        setSpriteStart(x, y);
        spritePos(warrior.getX(), warrior.getY());
        this.setTouchable(Touchable.enabled);
        this.setName("Knight");
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
        System.out.println(warrior.getWidth());


    }

    public String getName() {
        return name;
    }


    public void setSpriteStart(float x, float y) {
        warrior.setPosition(x - warrior.getWidth() / 2, y - warrior.getHeight() / 2);
    }

    public void spritePos(float x, float y) {
        this.setBounds(warrior.getX(), warrior.getY(), warrior.getWidth(), warrior.getHeight());
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
    public Rectangle getBounds() {
        bounds = new Rectangle((int) getX() + getWidth() / 2 - 10, (int) getY() + 10, 20, 20);
        return bounds;
    }


    @Override
    public void startSequence() {
        this.addAction(sequence);
        currentTask = Knight.CURRENT_TASK.WALK;
    }


    @Override
    public void startAttackAnimations(String direction, Unit target) {
        this.unit = target;

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

    @Override
    public void startAttack(String direction, Building target) {
        this.building = target;

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
            if (playerID == 1) {
                if (texture == stand) {
                    ROW = 1;
                    COLUMN = 6;
                } else if (texture.toString().contains("attack")) {
                    ROW = 1;
                    COLUMN = 10;
                } else {
                    ROW = 1;
                    COLUMN = 12;
                }

            } else if (playerID == 2) {
                if (texture == stand) {
                    ROW = 5;
                    COLUMN = 3;
                } else if (texture.toString().contains("attack")) {
                    ROW = 5;
                    COLUMN = 3;
                } else {
                    ROW = 1;
                    COLUMN = 11;
                }
            } else if (playerID == 3) {
                if (texture == stand) {
                    ROW = 10;
                    COLUMN = 1;
                } else if (texture.toString().contains("attack")) {
                    ROW = 1;
                    COLUMN = 10;
                } else {
                    ROW = 1;
                    COLUMN = 15;
                }
            } else if (playerID == 4) {
                if (texture == stand) {
                    ROW = 10;
                    COLUMN = 1;
                } else {
                    ROW = 1;
                    COLUMN = 10;
                }
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
        }
    }


    @Override
    public void act(float delta) {
        super.act(delta);
        if (this.getActions().size == 0 && currentTexture != stand && currentTask != Knight.CURRENT_TASK.ATTACK) {
            Animation(stand);
            currentTask = Knight.CURRENT_TASK.IDLE;
            destVector = null;
            building = null;
            unit = null;
        }
        setAnimation();
        if (currentTask == Knight.CURRENT_TASK.ATTACK) {
            currentTaskCounter++;
            if (currentTaskCounter >= COUNTERLIMIT) {
                if (building != null) {
                    building.destroy(this, attackDamage);
                    currentTaskCounter = 0;
                }
                if (unit != null) {
                    if (calculateEuclideanDistance(getSecondBounds().getX() + getSecondBounds().getWidth() / 2, getSecondBounds().getY() + getSecondBounds().getHeight() / 2, unit.getSecondBounds().getX() + unit.getSecondBounds().getWidth() / 2, unit.getSecondBounds().getY() + unit.getSecondBounds().getHeight() / 2) > 80) {
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
        if (attackBoolean && currentTask == Knight.CURRENT_TASK.IDLE && getActions().size == 0 && building == null && unit == null) {
            attackCloseEnemy();
        }
    }

    @Override
    public void draw(Batch batch, float alpha) {
        time += Gdx.graphics.getDeltaTime();
        TextureRegion current = walk.getKeyFrame(time, true);
        //setBounds(getX(), getY(), current.getRegionWidth(), current.getRegionHeight());
        batch.draw(current, getX(), getY());
    }

    @Override
    public boolean addListener(EventListener listener) {
        return super.addListener(listener);
    }


    public void setCurrentTask(Knight.CURRENT_TASK currentTask) {
        this.currentTask = currentTask;
    }

    public Integer getAttackDamage() {
        return attackDamage;
    }

    public enum CURRENT_TASK {
        IDLE, ATTACK, WALK
    }


    @Override
    public void goToStand() {
        if (currentTask != Knight.CURRENT_TASK.IDLE) {
            currentTask = Knight.CURRENT_TASK.IDLE;
            building = null;
            unit = null;
            Animation(stand);
        }
    }

    @Override
    public int getPlayerID() {
        return playerID;
    }
}
