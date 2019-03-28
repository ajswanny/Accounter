package accounter.controller;

import accounter.java.client.Client;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class NewAppointmentDialogueController extends FXMLController {

    private Client respectiveClient;

    @FXML
    private TextField nameInput;

    @FXML
    private DatePicker dateInput;

    @FXML
    private Button createNewAppointment;

    public static NewAppointmentDialogueController newAppointmentDialogueControllerInstance;

    public NewAppointmentDialogueController() {
        System.out.println("Initialized instance of NewAppointmentDialogueController.");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        initCoreResources();

        createNewAppointment.setOnAction(event -> createNewAppointment());

    }

    private void createNewAppointment() {
    }

    public static void setRespectiveClient(Client client) {
//        newAppointmentDialogueControllerInstance.respectiveClient = client;
    }

}
