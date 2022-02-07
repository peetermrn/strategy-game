package com.gamefromscratch.map;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class SmallMap extends Actor {
    String tmxFileName = "SmallMap.tmx";
    private TiledMap tiledMap;

    private TiledMapTileLayer tiledLayer;

    private TiledMapTileLayer.Cell cell;

    public SmallMap(TiledMap tiledMap, TiledMapTileLayer tiledLayer, TiledMapTileLayer.Cell cell) {
        this.tiledMap = tiledMap;
        this.tiledLayer = tiledLayer;
        this.cell = cell;
    }

    public String getTmxFileName() {
        return tmxFileName;
    }
}
