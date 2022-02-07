package com.gamefromscratch.other;

import com.gamefromscratch.resources.BigGold;
import com.gamefromscratch.resources.SmallGold;
import com.gamefromscratch.resources.Tree;
import com.gamefromscratch.screen.GameScreen;

import java.util.Random;

public class ResourceGenerator {
    GameScreen screen;

    public ResourceGenerator(GameScreen screen) {
        this.screen = screen;

    }

    public void generateTreesAndGold() {
        for (int y = 34; y >= 1; y--) {
            for (int x = 0; x < 34; x++) {
                if (y < 32 && y > 3 && x < 31 && x > 2) {
                    if (((x * 96 > 900 && x * 96 < 2300) || (y * 96 > 900 && y * 96 < 2400)) && y < 31 && y > 4 && x < 30 && x > 3) {
                        Random rand = new Random();
                        int int_random = rand.nextInt(99);
                        if (int_random == 50) {
                            SmallGold smallGold = new SmallGold(screen, x, y);
                            screen.resourceList.add(smallGold);
                            screen.goldList.add(smallGold);
                        }
                        if (int_random == 52 || int_random == 51) {
                            BigGold bigGold = new BigGold(screen, x, y);
                            screen.resourceList.add(bigGold);
                            screen.goldList.add(bigGold);
                        }
                        if (int_random == 53) {
                            Tree tree = new Tree(screen, x, y, true);
                            screen.resourceList.add(tree);
                            screen.treeList.add(tree);
                        }
                    }
                } else {
                    Tree tree = new Tree(screen, x, y, false);
                    screen.resourceList.add(tree);
                    screen.treeList.add(tree);
                }
            }
        }
    }
}
