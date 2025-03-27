package battleship;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class BattleshipView {

    public Scene getScene() {
        return new Scene(test(), 500, 500);
    }

    public GridPane test() {
        GridPane out = new GridPane();
        for (int i = 0 ; i > 10 ; i++) {
            Button button = new Button();
            out.add(button, 0, i);
        }
        return out;
    }
}
