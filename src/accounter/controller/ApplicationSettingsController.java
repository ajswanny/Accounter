package accounter.controller;

import java.net.URL;
import java.util.ResourceBundle;

public class ApplicationSettingsController extends FXMLController {

    public ApplicationSettingsController() {
        System.out.println("Initialized instance of ApplicationSettingsController.");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeCoreResources();
    }

}
