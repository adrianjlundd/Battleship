package exampleproject.test;


import battleship.WinnerHistory;
import org.junit.jupiter.api.*;
import java.io.IOException;
import java.nio.file.*;

import static org.junit.jupiter.api.Assertions.*;




public class WinnerHistoryTest {
    @Test
    public void testSaveAndRetrieveLastWinner() throws IOException {
        Path path = Paths.get("winners.txt");
        Files.deleteIfExists(path);

        WinnerHistory.saveWinner("Adrian");
        String last = WinnerHistory.getLastWinner();

        assertTrue(last.endsWith("Adrian"));
    }
}
