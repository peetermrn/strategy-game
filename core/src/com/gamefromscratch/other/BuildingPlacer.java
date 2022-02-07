package com.gamefromscratch.other;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.gamefromscratch.buildings.Barracks;
import com.gamefromscratch.buildings.Blacksmith;
import com.gamefromscratch.buildings.Camp;
import com.gamefromscratch.buildings.House;
import com.gamefromscratch.buildings.Market;
import com.gamefromscratch.database.ResourceBank;
import com.gamefromscratch.screen.GameScreen;
import com.gamefromscratch.units.Worker;

public class BuildingPlacer {
    GameScreen screen;
    ResourceBank reBank;


    public BuildingPlacer(GameScreen screen,ResourceBank reBank) {
        this.reBank = reBank;
        this.screen = screen;
    }
    public void placeBuilding(float x, float y) {
        Actor selectedActor = screen.selectedActor;
        if (screen.workerUiTable.getSelectedBuilding() == 1 && reBank.isEnough(House.getWoodBuildAmount(), House.getGoldBuildAmount(), ((Worker) selectedActor).playerID)) {
            if (screen.buildingPlaceChecker.checkBuildingPlace(House.getBuildingSprite(((Worker) selectedActor).playerID), x, y)) {
                reBank.takeFromBank(House.getWoodBuildAmount(), House.getGoldBuildAmount(), ((Worker) selectedActor).playerID);
                screen.stage.addActor(new House(screen, x, y, ((Worker) selectedActor).getPlayerID(), ((Worker) selectedActor)));
            }
        } else if (screen.workerUiTable.getSelectedBuilding() == 2 && reBank.isEnough(Barracks.getWoodBuildAmount(), Barracks.getGoldBuildAmount(), ((Worker) selectedActor).playerID)) {
            if (screen.buildingPlaceChecker.checkBuildingPlace(Barracks.getBuildingSprite(((Worker) selectedActor).playerID), x, y)) {
                reBank.takeFromBank(Barracks.getWoodBuildAmount(), Barracks.getGoldBuildAmount(), ((Worker) selectedActor).playerID);
                screen.stage.addActor(new Barracks(screen, x, y, ((Worker) selectedActor).getPlayerID(), ((Worker) selectedActor)));
            }
        } else if (screen.workerUiTable.getSelectedBuilding() == 3 && reBank.isEnough(Market.getWoodBuildAmount(), Market.getGoldBuildAmount(), ((Worker) selectedActor).playerID)) {
            if (screen.buildingPlaceChecker.checkBuildingPlace(Market.getBuildingSprite(((Worker) selectedActor).playerID), x, y)) {
                reBank.takeFromBank(Market.getWoodBuildAmount(), Market.getGoldBuildAmount(), ((Worker) selectedActor).playerID);
                screen.stage.addActor(new Market(screen, x, y, ((Worker) selectedActor).getPlayerID(), ((Worker) selectedActor)));
            }
        } else if (screen.workerUiTable.getSelectedBuilding() == 4 && reBank.isEnough(Camp.getWoodBuildAmount(), Camp.getGoldBuildAmount(), ((Worker) selectedActor).playerID)) {
            if (screen.buildingPlaceChecker.checkBuildingPlace(Camp.getBuildingSprite(((Worker) selectedActor).playerID), x, y)) {
                reBank.takeFromBank(Camp.getWoodBuildAmount(), Camp.getGoldBuildAmount(), ((Worker) selectedActor).playerID);
                screen.stage.addActor(new Camp(screen, x, y, ((Worker) selectedActor).getPlayerID(), ((Worker) selectedActor)));
            }
        } else if (screen.workerUiTable.getSelectedBuilding() == 5 && reBank.isEnough(Blacksmith.getWoodBuildAmount(), Blacksmith.getGoldBuildAmount(), ((Worker) selectedActor).playerID)) {
            if (screen.buildingPlaceChecker.checkBuildingPlace(Blacksmith.getBuildingSprite(((Worker) selectedActor).playerID), x, y)) {
                reBank.takeFromBank(Blacksmith.getWoodBuildAmount(), Blacksmith.getGoldBuildAmount(), ((Worker) selectedActor).playerID);
                screen.stage.addActor(new Blacksmith(screen, x, y, ((Worker) selectedActor).getPlayerID(), ((Worker) selectedActor)));
            }
        }
        screen.workerUiTable.unmakeSelectedBuilding();
    }
}
