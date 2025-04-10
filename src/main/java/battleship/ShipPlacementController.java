package battleship;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ShipPlacementController {

    @FXML private GridPane placementBoard;
    @FXML private Label playerLabel, shipLabel;
    @FXML private ChoiceBox<String> shipChoiceBox, orientationChoiceBox;
    @FXML private Button confirmButton;

    private Player player;
    private Game game;
    private List<Ship> shipsToPlace;
    private Button[][] placementButtons;
    private int currentPlayerIndex; // 0 for player1, 1 for player2

    public void setGame(Game game, int currentPlayerIndex) {
        this.game = game;
        this.currentPlayerIndex = currentPlayerIndex;
        this.player = (currentPlayerIndex == 0) ? game.getCurrentPlayer() : game.getOpponent();
        playerLabel.setText(player.getName() + ": Place your ships");

        // Initialiser skip som skal plasseres
        shipsToPlace = new ArrayList<>();
        shipsToPlace.add(new Ship(0, 0, 5, true)); // Carrier (5)
        shipsToPlace.add(new Ship(0, 0, 4, true)); // Battleship (4)
        shipsToPlace.add(new Ship(0, 0, 3, true)); // Cruiser (3)
        shipsToPlace.add(new Ship(0, 0, 3, true)); // Submarine (3)
        shipsToPlace.add(new Ship(0, 0, 2, true)); // Destroyer (2)

        // Fyll shipChoiceBox med skipene
        List<String> shipOptions = new ArrayList<>();
        for (Ship ship : shipsToPlace) {
            String name = getShipName(ship.getLength());
            shipOptions.add(name + " (" + ship.getLength() + ")");
        }
        shipChoiceBox.setItems(FXCollections.observableArrayList(shipOptions));
        shipChoiceBox.getSelectionModel().selectFirst();

        // Initialiser orientationChoiceBox
        orientationChoiceBox.setItems(FXCollections.observableArrayList("Horizontal", "Vertical"));
        orientationChoiceBox.getSelectionModel().selectFirst();

        // Initialiser grid for plassering
        placementButtons = new Button[10][10];
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                Button cell = new Button(" ");
                cell.setMinSize(25, 25);
                cell.setStyle("-fx-background-color: lightblue;");
                int finalRow = row;
                int finalCol = col;
                cell.setOnAction(event -> handleCellClick(finalRow, finalCol));
                placementButtons[row][col] = cell;
                placementBoard.add(cell, col, row);
            }
        }

        // Oppdater brettet for å vise allerede plasserte skip
        updatePlacementBoard();
    }

    private void handleCellClick(int row, int col) {
        if (shipChoiceBox.getSelectionModel().isEmpty()) {
            return;
        }

        // Hent valgt skip og orientering
        int selectedIndex = shipChoiceBox.getSelectionModel().getSelectedIndex();
        if (selectedIndex < 0 || selectedIndex >= shipsToPlace.size()) {
            return;
        }
        Ship ship = shipsToPlace.get(selectedIndex);
        String orientation = orientationChoiceBox.getValue();
        if (orientation == null) {
            return; // Hvis ingen orientering er valgt
        }
        boolean isHorizontal = orientation.equals("Horizontal");

        // Oppdater skipets posisjon og orientering
        try {
            // Opprett et nytt Ship-objekt med riktig posisjon og orientering
            Ship newShip = new Ship(row, col, ship.getLength(), isHorizontal);

            // Sjekk om plasseringen er gyldig
            if (player.getBoard().isValidPlacement(newShip)) {
                player.getBoard().placeShip(newShip);
                shipsToPlace.remove(selectedIndex);

                // Oppdater shipChoiceBox
                List<String> shipOptions = new ArrayList<>();
                for (Ship remainingShip : shipsToPlace) {
                    String name = getShipName(remainingShip.getLength());
                    shipOptions.add(name + " (" + remainingShip.getLength() + ")");
                }
                shipChoiceBox.setItems(FXCollections.observableArrayList(shipOptions));
                if (!shipOptions.isEmpty()) {
                    shipChoiceBox.getSelectionModel().selectFirst();
                } else {
                    confirmButton.setDisable(false); // Aktiver "Confirm"-knappen når alle skip er plassert
                }

                // Oppdater brettet
                updatePlacementBoard();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updatePlacementBoard() {
        char[][] boardState = player.getBoard().getBoard();
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                if (boardState[row][col] == 'S') {
                    placementButtons[row][col].setText("S");
                    placementButtons[row][col].setStyle("-fx-background-color: green;");
                } else {
                    placementButtons[row][col].setText(" ");
                    placementButtons[row][col].setStyle("-fx-background-color: lightblue;");
                }
            }
        }
    }

    private String getShipName(int length) {
        switch (length) {
            case 5: return "Carrier";
            case 4: return "Battleship";
            case 3: return shipsToPlace.stream().filter(s -> s.getLength() == 3).count() == 2 ? "Cruiser" : "Submarine";
            case 2: return "Destroyer";
            default: return "Unknown";
        }
    }

    @FXML
    public void onConfirmPlacement() throws IOException {
        if (currentPlayerIndex == 0) {
            // Første spiller er ferdig, gå til neste spiller
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ShipPlacement.fxml"));
            Parent shipPlacementRoot = loader.load();
            ShipPlacementController controller = loader.getController();
            controller.setGame(game, 1); // Neste spiller (player2)
            Stage stage = (Stage) confirmButton.getScene().getWindow();
            Scene shipPlacementScene = new Scene(shipPlacementRoot, 600, 450);
            stage.setScene(shipPlacementScene);
            stage.setTitle("Battleship - Ship Placement (Player 2)");
            stage.show();
        } else {
            // Begge spillere er ferdig, gå til spillebrettet
            FXMLLoader loader = new FXMLLoader(getClass().getResource("GameBoard.fxml"));
            Parent gameBoardRoot = loader.load();
            GameBoardController gameBoardController = loader.getController();
            gameBoardController.setGame(game);
            Stage stage = (Stage) confirmButton.getScene().getWindow();
            Scene gameBoardScene = new Scene(gameBoardRoot, 600, 450);
            stage.setScene(gameBoardScene);
            stage.setTitle("Battleship - Game Board");
            stage.show();
        }
    }
}