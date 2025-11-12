package exampleproject.test;


import battleship.Ship;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class ShipTest {

    @Test
    public void testHitAndIsSunk() {
        Ship ship = new Ship(0, 0, 3, true);
        ship.hit(0);
        ship.hit(1);
        ship.hit(2);
        assertTrue(ship.isSunk());
    }

    @Test
    public void testInvalidHit() {
        Ship ship = new Ship(0, 0, 2, false);
        assertFalse(ship.hit(-1));
        assertFalse(ship.hit(2));
        assertFalse(ship.isHit(0));
    }

    @Test
    public void testIsHit() {
        Ship ship = new Ship(0, 0, 2, false);
        ship.hit(1);
        assertTrue(ship.isHit(1));
        assertFalse(ship.isHit(0));
    }
}
