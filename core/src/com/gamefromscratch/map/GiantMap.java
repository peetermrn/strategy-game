package com.gamefromscratch.map;

public class GiantMap extends MapTemplate {
    String tmxFileName = "GiantMap.tmx";
    public GiantMap() {

    }
    @Override
    public String getTmxFileName() {
        return tmxFileName;
    }
}
