package battleship;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class WinnerHistory {
    private static final String WINNERS_FILE = "winners.txt";
    private static final DateTimeFormatter DATE_FORMATTER = 
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void saveWinner(String winnerName) {
        try {
            Path path = Paths.get(WINNERS_FILE);
            String timestamp = LocalDateTime.now().format(DATE_FORMATTER);
            String entry = String.format("%s - %s\n", timestamp, winnerName);
            
            if (Files.exists(path)) {
                Files.write(path, entry.getBytes(), StandardOpenOption.APPEND);
            } else {
                Files.write(path, entry.getBytes(), StandardOpenOption.CREATE);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getLastWinner() {
        try {
            Path path = Paths.get(WINNERS_FILE);
            if (Files.exists(path)) {
                List<String> lines = Files.readAllLines(path);
                if (!lines.isEmpty()) {
                    return lines.get(lines.size() - 1).trim();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Ingen vinnere ennå";
    }

    public static List<String> getAllWinners() {
        try {
            Path path = Paths.get(WINNERS_FILE);
            if (Files.exists(path)) {
                return Files.readAllLines(path);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return List.of("Ingen vinnere ennå");
    }
}