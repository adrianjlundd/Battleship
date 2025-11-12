package exampleproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import battleship.Board;
import battleship.Ship;

public class BoardTest {
     @Test
    public void testPlaceShipValid() {
        Board board = new Board();
        Ship ship = new Ship(0, 0, 2, true);
        assertTrue(board.placeShip(ship));
        assertEquals('S', board.getBoard()[0][0]);
        assertEquals('S', board.getBoard()[0][1]);
    }

    @Test
    public void testShootHitAndMiss() {
        Board board = new Board();
        Ship ship = new Ship(0, 0, 1, true);
        board.placeShip(ship);

        assertTrue(board.shoot(0, 0)); // Hit
        assertEquals('X', board.getBoard()[0][0]);
        assertFalse(board.shoot(1, 1)); // Miss
        assertEquals('M', board.getBoard()[1][1]);
    }
}
