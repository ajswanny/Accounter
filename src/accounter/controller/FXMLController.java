package accounter.controller;

import accounter.App;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public abstract class FXMLController implements Initializable {

    @FXML
    public AnchorPane root;

    Scene scene;

    protected App instance;

    void initializeCoreResources() {
        instance = App.getInstance();
        scene = new Scene(root);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public Scene getScene() {
        return scene;
    }

}
