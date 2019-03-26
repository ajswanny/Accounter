package accounter.controller;

import accounter.App;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;

import java.net.URL;
import java.util.ResourceBundle;

public class CalendarController extends FXMLController {

    @FXML
    private MenuItem accounterSettings;

    @FXML
    private MenuItem accounterQuit;

    @FXML
    private MenuItem newIndividual;

    public CalendarController() {
        System.out.println("Initialized instance of CalendarController.");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        initializeCoreResources();

        defineMenuItemActions();

    }

    private void defineMenuItemActions() {

        accounterSettings.setOnAction(event -> instance.requestDisplayForNewWindow(App.ApplicationWindow.APPLICATION_SETTINGS));
        accounterQuit.setOnAction(event -> instance.requestApplicationClose());
        newIndividual.setOnAction(event -> instance.requestDisplayForNewWindow(App.ApplicationWindow.NEW_INDIVIDUAL_DIALOGUE));

    }

}
