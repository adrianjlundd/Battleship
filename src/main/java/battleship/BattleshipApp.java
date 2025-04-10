package battleship;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class BattleshipApp extends Application {

    

    

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("Battleship");
        primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("AppBat.fxml"))));  
    
        //primaryStage.setScene(new BattleshipView().getScene());
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

}
