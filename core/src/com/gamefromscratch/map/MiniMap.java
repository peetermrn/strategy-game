package com.gamefromscratch.map;

import com.badlogic.gdx.maps.tiled.TiledMap;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.gamefromscratch.buildings.TownCenter;
import com.gamefromscratch.resources.Resource;
import com.gamefromscratch.screen.GameScreen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MiniMap {

    public static final int MAP_HEIGHT = 3200;
    private final OrthogonalTiledMapRenderer renderer;
    private final OrthographicCamera camera = new OrthographicCamera();
    Camera cam2;
    Sprite rectangle;
    SpriteBatch sb;
    GameScreen gameScreen;
    Sprite goldDot;
    Sprite treeDot;
    List<Sprite> dots = new ArrayList<>();
    List<Sprite> actorSprites = new ArrayList<>();
    TiledMap map;
    Stage stage;
    Map<Resource, Sprite> resourcesMap = new HashMap<>();
    Map<Actor, Sprite> actorMap = new HashMap<>();

    public MiniMap(TiledMap map, Camera cam, GameScreen gameScreen, Stage stage) {
        this.map = map;
        cam2 = cam;
        this.stage = stage;
        renderer = new OrthogonalTiledMapRenderer(map);
        this.gameScreen = gameScreen;
        camera.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        rectangle = new Sprite(new Texture("minimap/rectangle.png"));
        rectangle.setOrigin(rectangle.getWidth() / 2, rectangle.getHeight() / 2);
        rectangle.setScale(2.55f);
        camera.zoom = 16f;
        sb = new SpriteBatch();


    }

    public void update(float x, float y) {
        camera.position.x = x;
        camera.position.y = y;
        camera.translate(-7020, -2550);
        camera.update();
        renderer.setView(camera);
    }

    public void createDots() {
        dots.clear();
        for (Resource gold : gameScreen.goldList) {
            goldDot = new Sprite(new Texture(Gdx.files.internal("minimap/yellow_dot.png")));
            goldDot.setScale(4f);
            goldDot.setPosition(gold.getX() + gold.getWidth() / 2 - goldDot.getWidth() / 2, MAP_HEIGHT - gold.getY() - gold.getHeight() / 2 + goldDot.getHeight() / 2);
            dots.add(goldDot);
            resourcesMap.put(gold, goldDot);
        }
        for (Resource tree : gameScreen.treeList) {
            treeDot = new Sprite(new Texture(Gdx.files.internal("minimap/green_dot.png")));
            treeDot.setScale(4f);
            treeDot.setPosition(tree.getBounds().getX() + tree.getBounds().getWidth() / 2 - treeDot.getWidth() / 2, MAP_HEIGHT - tree.getBounds().getY() - tree.getBounds().getHeight() / 2 + treeDot.getHeight() / 2);
            dots.add(treeDot);
            resourcesMap.put(tree, treeDot);
        }
    }


    public void addPlayerToMinimap(Actor actor, int playerID) {
        Sprite playerDot = new Sprite(new Texture(Gdx.files.internal("minimap/player" + playerID + "_dot.png")));
        playerDot.setScale(4f);
        if (actor instanceof TownCenter) {
            playerDot.setScale(7f);
        }
        playerDot.setPosition(actor.getX() + actor.getWidth() / 2 - playerDot.getWidth() / 2, MAP_HEIGHT - actor.getY() - actor.getHeight() / 2 + playerDot.getHeight() / 2);
        actorSprites.add(playerDot);
        actorMap.put(actor, playerDot);
    }

    public void removePlayerFromMinimap(Actor actor) {
        actorSprites.remove(actorMap.get(actor));
        actorMap.remove(actor);
    }

    public void removeResourceFromMap(Resource resource) {
        dots.remove(resourcesMap.get(resource));
        resourcesMap.remove(resource);
    }


    public void render() {
        sb.begin();
        renderer.render();
        sb.setProjectionMatrix(camera.combined);
        //players
        for (Map.Entry<Actor, Sprite> entry : actorMap.entrySet()) {
            Actor actor = entry.getKey();
            Sprite playerDot = entry.getValue();
            playerDot.setPosition(actor.getX() + actor.getWidth() / 2 - playerDot.getWidth() / 2, MAP_HEIGHT - actor.getY() - actor.getHeight() / 2 + playerDot.getHeight() / 2);
            playerDot.draw(sb);
        }
        //resources
        for (Sprite sprite : dots) {
            sprite.draw(sb);
        }

        rectangle.setPosition(cam2.position.x - rectangle.getWidth() / 2, MAP_HEIGHT - cam2.position.y - rectangle.getHeight() / 2);
        rectangle.draw(sb);

        sb.end();
    }
}
