package com.gamefromscratch.map;

import com.badlogic.gdx.maps.tiled.TiledMap;

public class MapTemplate {
    TiledMap tiledMap;
    String tmxFileName = "NewMap2";
    public MapTemplate(){
    }
    public String getTmxFileName() {
        return tmxFileName;
    }
}
