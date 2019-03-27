package accounter.controller.newclient;

import accounter.controller.FXMLController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class NewCorporationDialogueController extends FXMLController {

    @FXML
    public TextField nameInput;

    @FXML
    public Button createNewCorporation;

    public NewCorporationDialogueController() {
        System.out.println("Created instance of NewCorporationDialogueController.");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        initializeCoreResources();

        createNewCorporation.setOnAction(event -> instance.createNewCorporation(nameInput.getText()));
        nameInput.clear();

    }

}
