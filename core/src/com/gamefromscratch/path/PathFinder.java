package com.gamefromscratch.path;


import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.gamefromscratch.buildings.Building;
import com.gamefromscratch.resources.Resource;
import com.gamefromscratch.resources.Tree;
import com.gamefromscratch.screen.GameScreen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PathFinder {
    GameScreen screen;

    private Graph<Station> underground;

    private RouteFinder<Station> routeFinder;
    public Set<Station> stations = new HashSet<>();
    private Map<String, Set<String>> connections = new HashMap<>();

    private Set<Station> backUpStations = new HashSet<>();
    private List<String> stationStrings = new ArrayList<>();

    public PathFinder(GameScreen screen) {
        this.screen = screen;
    }

    public void calculateMap() {
        stations = new HashSet<>();
        connections = new HashMap<>();
        int mapWidth = 100;
        int mapHeight = 100;
        Rectangle bounds;
        Rectangle bounds2;


        for (int x = 0; x < mapWidth; x++) {
            for (int y = mapHeight - 1; y >= 0; y--) {
                bounds = new Rectangle(32 * x , 32 * y , 32, 32);
                int check = 0;
                for (Resource resource : screen.resourceList) {
                    bounds2 = new Rectangle(resource.getBounds().getX() - 32, resource.getBounds().getY() - 32, resource.getBounds().getWidth() + 64, resource.getBounds().getHeight() + 64);
                    if (resource instanceof Tree) {
                        bounds2 = new Rectangle(resource.getBounds().getX()-16 , resource.getBounds().getY() - 16, resource.getBounds().getWidth()+32, resource.getBounds().getHeight() + 32);

                    }
                    if (bounds.overlaps(bounds2) || bounds2.overlaps(bounds)) {
                        check = 1;
                        backUpStations.add(new Station(x + ";" + y, x + ";" + y, x, y));
                        break;
                    }
                }
                for (Building building : screen.buildingList) {
                    bounds2 = new Rectangle(building.getBounds().getX() - 20, building.getBounds().getY() - 20, building.getBounds().getWidth() + 40, building.getBounds().getHeight() + 40);
                    if (bounds.overlaps(bounds2) || bounds2.overlaps(bounds)) {
                        check = 1;
                        backUpStations.add(new Station(x + ";" + y, x + ";" + y, x, y));
                        break;
                    }
                }


                if (check == 0) {
                    stations.add(new Station(x + ";" + y, x + ";" + y, x, y));
                    stationStrings.add(x + ";" + y);
                }
            }
        }
        for (int x = 0; x < mapWidth; x++) {
            for (int y = mapHeight - 1; y >= 0; y--) {
                addConnections(x, y);
            }
        }
        underground = new Graph<>(stations, connections);
        routeFinder = new RouteFinder<>(underground, new HaversineScorer(), new HaversineScorer());

    }

    private Set<String> addConnections(int x, int y) {

        Set<String> tempSet = new HashSet<>();
        if (x + 1 < 100) {
            int temp = x + 1;
            if (stationStrings.contains(temp + ";" + y)) {
                tempSet.add(temp + ";" + y);
            }
        }
        if (x - 1 >= 0) {
            int temp = x - 1;
            if (stationStrings.contains(temp + ";" + y)) {
                tempSet.add(temp + ";" + y);
            }
        }
        if (y - 1 >= 0) {
            int temp = y - 1;
            if (stationStrings.contains(x + ";" + temp)) {
                tempSet.add(x + ";" + temp);
            }
        }
        if (y + 1 < 100) {
            int temp = y + 1;
            if (stationStrings.contains(x + ";" + temp)) {
                tempSet.add(x + ";" + temp);
            }
        }
        if (x + 1 < 100 && y + 1 < 100) {
            int temp1 = x + 1;
            int temp2 = y + 1;
            if (stationStrings.contains(temp1 + ";" + temp2)) {
                tempSet.add(temp1 + ";" + temp2);
            }
        }
        if (x - 1 >= 0 && y - 1 >= 0) {
            int temp1 = x - 1;
            int temp2 = y - 1;
            if (stationStrings.contains(temp1 + ";" + temp2)) {
                tempSet.add(temp1 + ";" + temp2);
            }
        }
        if (x - 1 >= 0 && y + 1 < 100) {
            int temp1 = x - 1;
            int temp2 = y + 1;
            if (stationStrings.contains(temp1 + ";" + temp2)) {
                tempSet.add(temp1 + ";" + temp2);
            }
        }
        if (x + 1 < 100 && y - 1 >= 0) {
            int temp1 = x + 1;
            int temp2 = y - 1;
            if (stationStrings.contains(temp1 + ";" + temp2)) {
                tempSet.add(temp1 + ";" + temp2);
            }
        }
        connections.put(x + ";" + y, tempSet);
        return tempSet;
    }

    public void addNodes(Actor actor) {
        Rectangle bounds;

        Rectangle rec = new Rectangle(actor.getX(), actor.getY() , actor.getWidth(), actor.getHeight());
        if (actor instanceof Tree) {
            rec = new Rectangle(((Tree) actor).getBounds().getX()-16, ((Tree) actor).getBounds().getY() - 16, ((Tree) actor).getBounds().getWidth()+32, ((Tree) actor).getBounds().getHeight() + 32);
        }
        if (actor instanceof Building) {
            rec = new Rectangle(((Building) actor).getBounds().getX() - 20, ((Building) actor).getBounds().getY() - 20, ((Building) actor).getBounds().getWidth() + 40, ((Building) actor).getBounds().getHeight() + 40);

        }

        for (Station station : backUpStations) {
            bounds = new Rectangle((int) station.getX() * 32, (int) station.getY() * 32, 32, 32);
            if (bounds.overlaps(rec) || rec.overlaps(bounds)) {
                stations.add(station);
                Set<String> con = addConnections((int) station.getX(), (int) station.getY());
                for (String conString : con) {
                    Set<String> tempSet = connections.get(conString);
                    tempSet.add(station.getId());
                    connections.put(conString, tempSet);
                }
            }
        }
        underground = new Graph<>(stations, connections);
        routeFinder = new RouteFinder<>(underground, new HaversineScorer(), new HaversineScorer());

    }

    public void removeNodes(Building building) {

        Rectangle bounds2 = new Rectangle(building.getBounds().getX() - 16, building.getBounds().getY() - 16, building.getBounds().getWidth() + 32, building.getBounds().getHeight() + 32);
        List<Station> tempList = new ArrayList<>();
        for (Station station : stations) {
            Rectangle rec = new Rectangle((int) station.getX() * 32 - 5, (int) station.getY() * 32 - 5, 45, 45);
            if (rec.overlaps(bounds2) || bounds2.overlaps(rec)) {
                tempList.add(station);
                removeConnections(station);

            }
        }
        stations.removeAll(tempList);
        underground = new Graph<>(stations, connections);
        routeFinder = new RouteFinder<>(underground, new HaversineScorer(), new HaversineScorer());
    }

    public void removeConnections(Station station) {
        int x = (int) station.getX();
        int y = (int) station.getY();
        String name = x + ";" + y;
        connections.remove(name);
        Map<String, Set<String>> tempMap = new HashMap<>();
        for (Map.Entry<String, Set<String>> entry : connections.entrySet()) {
            Set<String> tempSet = entry.getValue();
            tempSet.remove(name);
            tempMap.put(entry.getKey(), tempSet);
        }

        connections = tempMap;
    }


    public List<Station> findRoute(float fromX, float fromY, float toX, float toY) {
        int startX = Math.round(fromX) / 32;
        int startY = Math.round(fromY) / 32;
        int endX = Math.round(toX) / 32;
        int endY = Math.round(toY) / 32;

        try {
            return routeFinder.findRoute(underground.getNode(startX + ";" + startY), underground.getNode(endX + ";" + endY));
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}





