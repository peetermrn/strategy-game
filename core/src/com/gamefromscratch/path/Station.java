package com.gamefromscratch.path;

import java.util.StringJoiner;

public class Station implements GraphNode {
    private final String id;
    private final String name;
    private final int x;
    private final int y;

    public Station(String id, String name, int x, int y) {
        this.id = id;
        this.name = name;
        this.x = x;
        this.y = y;
    }

    @Override
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Station.class.getSimpleName() + "[", "]").add("id='" + id + "'")
                .add("name='" + name + "'").add("x=" + x).add("y=" + y).toString();
    }
}
