package exampleproject;

import battleship.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    @Test
    public void testSwitchTurns() {
        Game game = new Game("Alice", "Bob");
        Player initial = game.getCurrentPlayer();
        game.switchTurn();
        assertNotEquals(initial, game.getCurrentPlayer());
    }
}
