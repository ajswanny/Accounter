/*
 * Created by Alexander Swanson on 3/28/19 7:09 PM.
 * Email: alexanderjswanson@icloud.com.
 * Copyright © 2019. All rights reserved.
 */

package accounter.controller.new_client;

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
    private Button createNewIndividual;

    public NewIndividualDialogueController() {
        System.out.println("Created instance of NewIndividualDialogueController.");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        initCoreResources();

        createNewIndividual.setOnAction(event -> createNewIndividual());

    }

    private void createNewIndividual() {

        instance.createNewIndividual(firstNameInput.getText(), lastNameInput.getText());
        firstNameInput.clear(); lastNameInput.clear();

    }

}
