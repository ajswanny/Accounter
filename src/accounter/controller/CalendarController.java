package accounter.controller;

import accounter.App;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class CalendarController extends FXMLController {

    @FXML
    private MenuItem accounterSettings;

    @FXML
    private MenuItem accounterQuit;

    public CalendarController() {
        System.out.println("Initialized instance of CalendarController.");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        initializeCoreResources();

        defineMenuItemActions();

    }

    private void defineMenuItemActions() {

        accounterSettings.setOnAction(event -> instance.requestNewSceneToDisplay(App.ApplicationScene.SETTINGS_SCENE));
        accounterQuit.setOnAction(event -> instance.requestApplicationClose());

    }

}
