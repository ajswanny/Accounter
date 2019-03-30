/*
 * Created by Alexander Swanson on 3/28/19 7:10 PM.
 * Email: alexanderjswanson@icloud.com.
 * Copyright © 2019. All rights reserved.
 */

package accounter.controller;

import accounter.java.client.Client;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class NewAppointmentDialogueController extends FXMLController {

    private Client respectiveClient;

    private enum TimePeriod { AM, PM }

    @FXML
    private TextField nameInput;

    @FXML
    private DatePicker dateInput;

    @FXML
    private Spinner<Integer> hoursSpinner;

    @FXML
    private Spinner<String> minutesSpinner;

    @FXML
    private Spinner<TimePeriod> periodSpinner;

    @FXML
    private Button createNewAppointment;

    public NewAppointmentDialogueController() {
        System.out.println("Initialized instance of NewAppointmentDialogueController.");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initCoreResources();
        initSpinners();
        createNewAppointment.setOnAction(event -> createNewAppointment());
    }

    private void initSpinners() {
        // Hours Spinner
        hoursSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 12, 8, 1));

        // Minutes Spinner
        ArrayList<String> minuteStrings = new ArrayList<>(60);
        for (int i = 0; i < 60; i++) {
            if (i < 10) {
                minuteStrings.add("0" + i);
            } else {
                minuteStrings.add(String.valueOf(i));
            }
        }
        minutesSpinner.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<>(FXCollections.observableArrayList(minuteStrings)));

        // Period Spinner
        periodSpinner.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<>(FXCollections.observableArrayList(TimePeriod.AM, TimePeriod.PM)));

        hoursSpinner.getStyleClass().clear();
        minutesSpinner.getStyleClass().clear();
        periodSpinner.getStyleClass().clear();
    }

    private void createNewAppointment() {
        instance.createNewAppointment(respectiveClient, nameInput.getText(), dateInput.getValue());
    }

    public void setRespectiveClient(Client respectiveClient) {
        this.respectiveClient = respectiveClient ;
    }

}
