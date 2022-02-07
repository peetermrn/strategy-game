package com.gamefromscratch.other;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.gamefromscratch.buildings.Building;
import com.gamefromscratch.resources.Resource;
import com.gamefromscratch.screen.GameScreen;
import com.gamefromscratch.units.Unit;
import com.gamefromscratch.units.Worker;

public class BuildingPlaceChecker {
    GameScreen screen;

    public BuildingPlaceChecker(GameScreen screen) {
        this.screen = screen;
    }

    public boolean checkBuildingPlace(Sprite sprite, float x, float y) {
        Rectangle rec = new Rectangle(x - sprite.getWidth() / 2, y - sprite.getHeight() / 2, sprite.getWidth(), sprite.getHeight());
        for (Resource res : screen.resourceList) {
            if (res.getBounds().overlaps(rec) || rec.overlaps(res.getBounds())) {
                return false;
            }
        }
        for (Building building : screen.buildingList) {
            if (building.getBounds().overlaps(rec) || rec.overlaps(building.getBounds())) {
                return false;
            }
        }
        for (Worker worker : screen.workerList) {
            if (worker.getBounds().overlaps(rec) || rec.overlaps(worker.getBounds())) {
                return false;
            }
        }
        for (Unit unit : screen.unitList) {
            if (unit.getBounds().overlaps(rec) || rec.overlaps(unit.getBounds())) {
                return false;
            }
        }
        return true;
    }
}
