/*
 * Created by Alexander Swanson on 4/7/19 8:06 PM.
 * Email: alexanderjswanson@icloud.com.
 * Copyright © 2019. All rights reserved.
 */

package accounter.controller.client_info;

import accounter.controller.FXMLController;
import accounter.java.Appointment;
import accounter.java.client.Individual;
import accounter.java.models.AppointmentInfoLabel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class IndividualInfoController extends FXMLController {

    /* Fields */
    private static IndividualInfoController individualInfoControllerInstance;

    @FXML
    public Label firstName;

    @FXML
    public Label lastName;

    @FXML
    public VBox appointmentsContainer;

    /* Constructors */
    public IndividualInfoController() {
        System.out.println("Initialized instance of ClientInfoController.");
        individualInfoControllerInstance = this;
    }

    /* Methods */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
    }

    private void initAppointmentsList(ArrayList<Appointment> appointments) {
        if (appointments == null) {
            appointmentsContainer.getChildren().setAll(new Label("\t\tNo appointments"));
        } else {
            appointmentsContainer.getChildren().clear();
            for (Appointment appointment : appointments) {
                appointmentsContainer.getChildren().add(new AppointmentInfoLabel(appointment, true));
            }
        }
    }

    public static void setRespectiveClientData(Individual respectiveClient) {
        individualInfoControllerInstance.firstName.setText(respectiveClient.getFirstName());
        individualInfoControllerInstance.lastName.setText(respectiveClient.getLastName());
        individualInfoControllerInstance.initAppointmentsList(respectiveClient.getAppointments());
    }

}
