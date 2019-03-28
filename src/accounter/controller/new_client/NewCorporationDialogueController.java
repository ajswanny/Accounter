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
        super.initialize(location, resources);
        createNewCorporation.setOnAction(event -> instance.createNewCorporation(nameInput.getText()));
        nameInput.clear();
    }

}
