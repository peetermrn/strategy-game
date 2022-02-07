package tests.units;


import com.gamefromscratch.units.Knight;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestUnits {
    @Test
    public void testKnightIsFullHp() {
        Knight knight = new Knight();
        assertEquals(knight.hp, knight.maxHp);
    }

    @Test
    public void testKnightIsAlive() {
        Knight knight = new Knight();
        assertTrue(knight.isAlive());
    }

    @Test
    public void testKnightIsAutomaticallyInAttackStance() {
        Knight knight = new Knight();
        assertTrue(knight.attackBoolean);
    }

}
