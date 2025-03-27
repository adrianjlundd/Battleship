package battleship;

import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;

public class BattleshipApp extends Application {

    

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("Battleship");
        //primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("App.fxml"))));
    
        primaryStage.setScene(new BattleshipView().getScene());
        primaryStage.show();
    }

}
