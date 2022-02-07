package com.gamefromscratch.screen;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class CameraListener extends InputAdapter {

    private final Stage stage;
    private final Camera camera;
    private final Vector3 curr;
    private final Vector3 last;
    private final Vector3 delta;
    //private final int mapWidth;
    //private final int mapHeight;

    public CameraListener(Stage stage) {
        this.stage = stage;
        this.camera = stage.getViewport().getCamera();

        curr = new Vector3();
        last = new Vector3(-1, -1, -1);
        delta = new Vector3();

        // TiledMapTileLayer layer = stage.getLevel().getMap().getFirstLayer();
        // mapWidth = layer.getWidth() * DDGame.TILE_HEIGHT;
        // mapHeight = layer.getHeight() * DDGame.TILE_HEIGHT;
    }

    /*
    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    private boolean isInMapBounds() {

        return camera.position.x >= camera.viewportWidth / 2f
                && camera.position.x <= mapWidth - camera.viewportWidth / 2f
                && camera.position.y >= camera.viewportHeight / 2f
                && camera.position.y <= mapHeight - camera.viewportHeight / 2f;

    }

    private void putInMapBounds() {

        if (camera.position.x < camera.viewportWidth / 2f)
            camera.position.x = camera.viewportWidth / 2f;
        else if (camera.position.x > mapWidth - camera.viewportWidth / 2f)
            camera.position.x = mapWidth - camera.viewportWidth / 2f;

        if (camera.position.y < camera.viewportHeight / 2f)
            camera.position.y = camera.viewportHeight / 2f;
        else if (camera.position.y > mapHeight - camera.viewportHeight / 2f)
            camera.position.y = mapHeight - camera.viewportHeight / 2f;

        stage.moveTo(
                camera.position.x,
                camera.position.y);

    }
     */

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
