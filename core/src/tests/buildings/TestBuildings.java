package tests.buildings;


import com.gamefromscratch.buildings.Barracks;
import com.gamefromscratch.buildings.Blacksmith;
import com.gamefromscratch.buildings.Camp;
import com.gamefromscratch.buildings.House;
import com.gamefromscratch.buildings.Market;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class TestBuildings {

    @Test
    public void testHousesInitialHpIsOne() {
        House house = new House();
        assertEquals(1,house.getHp());
        assertFalse(house.ready);
        assertTrue(house.alive);
    }
    @Test
    public void testBarracksInitialHpIsOne() {
        Barracks house = new Barracks();
        assertEquals(1,house.getHp());
        assertFalse(house.ready);
        assertTrue(house.alive);
    }
    @Test
    public void testMarketInitialHpIsOne() {
        Market house = new Market();
        assertEquals(1,house.getHp());
        assertFalse(house.ready);
        assertTrue(house.alive);
    }
    @Test
    public void testHCampInitialHpIsOne() {
        Camp house = new Camp();
        assertEquals(1,house.getHp());
        assertFalse(house.ready);
        assertTrue(house.alive);
    }
    @Test
    public void testBlacksmithInitialHpIsOne() {
        Blacksmith house = new Blacksmith();
        assertEquals(1,house.getHp());
        assertFalse(house.ready);
        assertTrue(house.alive);
    }
}
