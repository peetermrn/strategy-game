package com.gamefromscratch.path;

public class HaversineScorer implements Scorer<Station> {
    @Override
    public double computeCost(Station from, Station to) {
        double xDif = Math.toRadians(to.getX() - from.getX());
        double yDif = Math.toRadians(to.getY() - from.getY());
        double x1 = Math.toRadians(from.getX());
        double x2 = Math.toRadians(to.getX());
        double a = Math.pow(Math.sin(xDif / 2), 2) + Math.pow(Math.sin(yDif / 2), 2) * Math.cos(x1) * Math.cos(x2);
        return 2 * Math.asin(Math.sqrt(a));
    }
}
