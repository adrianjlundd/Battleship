package battleship;

import java.io.IOException;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GameBoardController {

    @FXML private GridPane playerBoard, opponentBoard;
    @FXML private Label playerOneLabel, playerTwoLabel, currentPlayerLabel;
    @FXML private VBox gameBoardPane;

    private Game game;
    private Button[][] playerButtons;
    private Button[][] opponentButtons;

    public void setGame(Game game) {
        this.game = game;

        // Sett spillernavn i etikettene
        playerOneLabel.setText("Player 1: " + game.getCurrentPlayer().getName());
        playerTwoLabel.setText("Player 2: " + game.getOpponent().getName());
        currentPlayerLabel.setText("Current Player: " + game.getCurrentPlayer().getName());

        // Generer gridene dynamisk
        playerButtons = new Button[10][10];
        opponentButtons = new Button[10][10];

        // Spilleren sitt eget brett (viser skip)
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                Button cell = new Button(" ");
                cell.setMinSize(25, 25);
                playerButtons[row][col] = cell;
                playerBoard.add(cell, col, row);
            }
        }

        // Motstanderens brett (for angrep)
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                Button cell = new Button(" ");
                cell.setMinSize(25, 25);
                int finalRow = row;
                int finalCol = col;
                cell.setOnAction(event -> handleCellClick(finalRow, finalCol));
                opponentButtons[row][col] = cell;
                opponentBoard.add(cell, col, row);
            }
        }

        // Oppdater brettene ved start
        updateBoards();
    }

    private void handleCellClick(int row, int col) {
        if (game == null || game.isGameOver()) {
            return;
        }

        // Angrip motstanderens brett
        boolean hit = game.attack(row, col);

        // Sjekk om spillet er over
        if (game.isGameOver()) {
            Player winner = game.getWinner();
            currentPlayerLabel.setText("Game Over! Winner: " + winner.getName());
            updateBoards(); // Oppdater brettene én siste gang ved spill-slutt
            showGameOverAlert(winner);
            return;
        }

        // Bytt tur hvis det var en bom
        if (!hit) {
            game.switchTurn();
            currentPlayerLabel.setText("Current Player: " + game.getCurrentPlayer().getName());
        }

        // Oppdater grensesnittet etter turbytte
        updateBoards();
    }

    private void updateBoards() {
        // Oppdater spillerens eget brett (viser skip, treff og bom)
        char[][] playerBoardState = game.getCurrentPlayer().getBoard().getBoard();
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                char cell = playerBoardState[row][col];
                if (cell == 'S') {
                    playerButtons[row][col].setText("S");
                    playerButtons[row][col].setStyle("-fx-background-color: green;");
                } else if (cell == 'X') {
                    playerButtons[row][col].setText("X");
                    playerButtons[row][col].setStyle("-fx-background-color: red;");
                } else if (cell == 'M') {
                    playerButtons[row][col].setText("M");
                    playerButtons[row][col].setStyle("-fx-background-color: gray;");
                } else {
                    playerButtons[row][col].setText(" ");
                    playerButtons[row][col].setStyle("-fx-background-color: lightblue;");
                }
            }
        }

        // Oppdater motstanderens brett (skjuler skip, viser kun treff og bom)
        char[][] opponentBoardState = game.getOpponent().getBoard().getBoard();
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                char cell = opponentBoardState[row][col];
                if (cell == 'X') {
                    opponentButtons[row][col].setText("X");
                    opponentButtons[row][col].setStyle("-fx-background-color: red;");
                } else if (cell == 'M') {
                    opponentButtons[row][col].setText("M");
                    opponentButtons[row][col].setStyle("-fx-background-color: gray;");
                } else {
                    opponentButtons[row][col].setText(" ");
                    opponentButtons[row][col].setStyle("-fx-background-color: lightblue;");
                }
            }
        }
    }

    private void showGameOverAlert(Player winner) {
        // Save the winner to file
        WinnerHistory.saveWinner(winner.getName());

        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText("Game Over!");
        alert.setContentText("Winner: " + winner.getName() + "\nWould you like to return to the main menu?");

        ButtonType backToMenuButton = new ButtonType("Back to Menu");
        ButtonType closeButton = new ButtonType("Close");
        alert.getButtonTypes().setAll(backToMenuButton, closeButton);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == backToMenuButton) {
            try {
                onBackToMenu();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Lukk vinduet
            Stage stage = (Stage) gameBoardPane.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    public void onBackToMenu() throws IOException {
        // Gå tilbake til startmenyen
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AppBat.fxml"));
        Parent startMenuRoot = loader.load();
        Stage stage = (Stage) gameBoardPane.getScene().getWindow();
        Scene startMenuScene = new Scene(startMenuRoot, 600, 450);
        stage.setScene(startMenuScene);
        stage.setTitle("Battleship");
        stage.show();
    }
}