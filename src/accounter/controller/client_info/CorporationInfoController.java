package accounter.controller.client_info;

import accounter.controller.FXMLController;
import accounter.java.Appointment;
import accounter.java.client.Corporation;
import accounter.java.models.AppointmentInfoLabel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.jetbrains.annotations.NotNull;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CorporationInfoController extends FXMLController {

    /* Fields */
    private static CorporationInfoController corporationInfoControllerInstance;

    @FXML
    public Label name;

    @FXML
    public VBox appointmentsContainer;

    /* Constructors */
    public CorporationInfoController() {
        System.out.println("Initialized instance of ClientInfoController");
        corporationInfoControllerInstance = this;
    }

    /* Methods */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
    }

    private void initAppointmentsList(ArrayList<Appointment> appointments) {
        if (appointments == null) {
            appointmentsContainer.getChildren().setAll(new Label("\tNo appointments"));
        } else {
            appointmentsContainer.getChildren().clear();
            for (Appointment appointment : appointments) {
                appointmentsContainer.getChildren().add(new AppointmentInfoLabel(appointment));
            }
        }
    }

    public static void setRespectiveClientData(@NotNull Corporation respectiveClient) {
        corporationInfoControllerInstance.name.setText(respectiveClient.getName());
        corporationInfoControllerInstance.initAppointmentsList(respectiveClient.getAppointments());
    }

}
