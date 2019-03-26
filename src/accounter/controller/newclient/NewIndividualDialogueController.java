package accounter.controller.newclient;

import accounter.controller.FXMLController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class NewIndividualDialogueController extends FXMLController {

    @FXML
    private TextField firstNameInput;

    @FXML
    private TextField lastNameInput;

    @FXML
    private Button createIndividual;

    public NewIndividualDialogueController() {
        System.out.println("Created instance of NewIndividualDialogueController.");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        initializeCoreResources();

        createIndividual.setOnAction(event -> instance.createNewIndividual(firstNameInput.getText(), lastNameInput.getText()));

    }

}
