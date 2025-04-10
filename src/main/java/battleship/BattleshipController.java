package battleship;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class BattleshipController {

    @FXML private TextField playerone, playertwo;
    @FXML private Label winnerLabel;  

    @FXML
public void initialize() {
    // Viser forrige vinner
    winnerLabel.setText("Siste vinner: " + WinnerHistory.getLastWinner());
}

    @FXML
    public void onStartNewGame() {
        try {
            // Henter spillernavn
            String playerOneName = playerone.getText().trim().isEmpty() ? "Player 1" : playerone.getText();
            String playerTwoName = playertwo.getText().trim().isEmpty() ? "Player 2" : playertwo.getText();

            // new game
            Game game = new Game(playerOneName, playerTwoName);

            // -- > ship placement
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ShipPlacement.fxml"));
           
            Parent shipPlacementRoot = loader.load();
            ShipPlacementController controller = loader.getController();
            controller.setGame(game, 0); // Starter med player1
            Stage stage = (Stage) playerone.getScene().getWindow();
            Scene shipPlacementScene = new Scene(shipPlacementRoot, 600, 450);
            stage.setScene(shipPlacementScene);
            stage.setTitle("Battleship - Ship Placement (Player 1)");
            stage.show();
        } catch (IOException e) {

            e.printStackTrace();
            System.out.println("Feil ved lasting av ShipPlacement.fxml: " + e.getMessage());
        }
    }
}