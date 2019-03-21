package accounter.controller;

import javafx.fxml.Initializable;
import javafx.scene.Scene;

import java.net.URL;
import java.util.ResourceBundle;

public abstract class FXMLController implements Initializable {

    protected Scene scene;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public Scene getScene() {
        return scene;
    }

}
