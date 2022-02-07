package com.gamefromscratch.other;

import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.Comparator;

public class ActorComparator implements Comparator<Actor> {
    @Override
    public int compare(Actor a, Actor b) {
        return Float.compare(b.getY(), a.getY());

        /*public int compare(Actor first, Actor second) {
        if (first.getClass() == second.getClass()) {
            return 0;
        }
        if (first instanceof BigGold || first instanceof SmallGold) {
            return 1;
        }
        if (second instanceof BigGold || second instanceof SmallGold) {
            return -1;
        }
        if (first instanceof Tree && (second instanceof Unit)) {
            if (second.getY() + 5 > ((Tree) first).getBounds().getY() + ((Tree) first).getBounds().getHeight()) {
                return 1;
            }
            return -1;
        }
        if (second instanceof Tree && (first instanceof Unit)) {
            if (first.getY() + 5 > ((Tree) second).getBounds().getY() + ((Tree) second).getBounds().getHeight()) {
                return -1;
            }
            return 1;
        }
        if (first instanceof Building && (second instanceof Unit)) {
            if (second.getY() + 5 > ((Building) first).getBounds().getY() + ((Building) first).getBounds().getHeight()) {
                return 1;
            }
            return -1;
        }
        if (second instanceof Building && (first instanceof Unit)) {
            if (first.getY() + 5 > ((Building) second).getBounds().getY() + ((Building) second).getBounds().getHeight()) {
                return -1;
            }
            return 1;
        }


        return 0;*/


    }
}
