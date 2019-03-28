package accounter.controller.client_info;

import accounter.controller.FXMLController;
import accounter.java.client.Individual;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.jetbrains.annotations.NotNull;

import java.net.URL;
import java.util.ResourceBundle;

public class IndividualInfoController extends FXMLController {

    private static IndividualInfoController individualInfoControllerInstance;

    @FXML
    private Label firstName;

    @FXML
    private Label lastName;

    @FXML
    private VBox appointmentsContrainer;

    public IndividualInfoController() {
        System.out.println("Initialized instance of ClientInfoController");
        individualInfoControllerInstance = this;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initCoreResources();
    }

    public static void setRespectiveClientData(@NotNull Individual respectiveClient) {
        individualInfoControllerInstance.firstName.setText(respectiveClient.getFirstName());
        individualInfoControllerInstance.lastName.setText(respectiveClient.getLastName());
    }

}
