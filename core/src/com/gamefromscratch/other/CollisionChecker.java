package com.gamefromscratch.other;

import com.gamefromscratch.buildings.Building;
import com.gamefromscratch.buildings.Camp;
import com.gamefromscratch.buildings.TownCenter;
import com.gamefromscratch.resources.BigGold;
import com.gamefromscratch.resources.Resource;
import com.gamefromscratch.resources.SmallGold;
import com.gamefromscratch.resources.Tree;
import com.gamefromscratch.screen.GameScreen;
import com.gamefromscratch.units.Unit;
import com.gamefromscratch.units.Worker;

public class CollisionChecker {
    GameScreen screen;

    public CollisionChecker(GameScreen screen) {
        this.screen = screen;
    }

    public void checkCollisions() {
        checkWarriorCollisions();
        checkWorkerCollisions();
    }

    public void checkWarriorCollisions() {
        for (Unit warrior : screen.warriors) {
            for (Resource resource : screen.resourceList) {
                if (warrior.getBounds().overlaps(resource.getBounds()) || resource.getBounds().overlaps(warrior.getBounds())) {
                    warrior.clearActions();
                    break;
                }
            }
            for (Building building : screen.buildingList) {
                if ((warrior.getBounds().overlaps(building.getBounds()) || building.getBounds().overlaps(warrior.getBounds()))) {
                    if (warrior.getPlayerID() == building.getPlayerID()) {

                        warrior.clearAllActions();
                        break;
                    } else {
                        warrior.warriorBuildingCollision(building);
                    }
                }
            }
            for (Unit unit : screen.unitList) {
                if ((warrior.getSecondBounds().overlaps(unit.getSecondBounds()) || unit.getSecondBounds().overlaps(warrior.getSecondBounds()))) {
                    if (warrior.getPlayerID() != unit.getPlayerID() && warrior.isAlive() && unit.isAlive()) {
                        warrior.warriorUnitCollision(unit);
                    }
                }
            }
        }
    }


    public void checkWorkerCollisions() {
        for (Worker worker : screen.workerList) {
            if (worker.currentTask == Worker.CURRENT_TASK.WALK) {
                for (Resource resource : screen.resourceList) {
                    if (worker.getBounds().overlaps(resource.getBounds()) || resource.getBounds().overlaps(worker.getBounds())) {
                        if (resource instanceof BigGold || resource instanceof SmallGold) {
                            if (worker.currentGold >= worker.goldCapacity) {
                                worker.isCarrying = true;
                                worker.goToCamp();
                            } else {

                                worker.workerGoldCollision(resource);
                            }
                        }
                        if (resource instanceof Tree) {
                            if (worker.currentWood >= worker.woodCapacity) {
                                worker.isCarrying = true;
                                worker.goToCamp();
                            } else {
                                worker.workerTreeCollision(resource);
                            }
                        }
                        break;
                    }
                }
                for (Building building : screen.buildingList) {
                    if (worker.getBounds().overlaps(building.getBounds()) || building.getBounds().overlaps(worker.getBounds())) {
                        worker.currentTask = Worker.CURRENT_TASK.IDLE;
                        worker.clearAllActions();
                        if (building instanceof TownCenter) {
                            if (building.playerID == worker.playerID) {
                                worker.dropOfResources();
                                worker.currentGold = 0;
                                worker.currentWood = 0;
                            }
                        }
                        if (building instanceof Camp) {
                            if (building.playerID == worker.playerID) {
                                worker.dropOfResources();
                                worker.currentGold = 0;
                                worker.currentWood = 0;
                            }
                        }
                        if (!building.isReady() && worker.currentTask != Worker.CURRENT_TASK.BUILD && worker.playerID == building.playerID) {
                            worker.workerHouseCollision(building);
                        }
                        if (worker.isCarrying && (building instanceof Camp || building instanceof TownCenter)) {
                            worker.goToClosestResource();
                        }
                        break;
                    }
                }
            }
        }
    }
}
