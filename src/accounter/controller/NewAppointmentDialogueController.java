/*
 * Created by Alexander Swanson on 4/14/19 10:55 PM.
 * Email: alexanderjswanson@icloud.com.
 * Copyright © 2019. All rights reserved.
 */

package accounter.controller;

import accounter.App;
import accounter.java.client.Client;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

public class NewAppointmentDialogueController extends FXMLController {

    private Client respectiveClient;

    @FXML
    private TextField nameInput;

    @FXML
    private DatePicker dateInput;

    @FXML
    private Spinner<Integer> hoursSpinner;

    @FXML
    private Spinner<String> minutesSpinner;

    @FXML
    private Spinner<App.TimePeriod> periodSpinner;

    @FXML
    private Button createNewAppointment;

    public NewAppointmentDialogueController() {
        System.out.println("Initialized instance of NewAppointmentDialogueController.");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        initCoreResources();
        initSpinners();

        // Set default Date value
        dateInput.setValue(LocalDate.now());

        createNewAppointment.setOnAction(event -> {

            // Parse and register input.
            String hoursValue = hoursSpinner.getValue().toString();
            if (hoursValue.length() == 1) { hoursValue = "0" + hoursValue ; }
            String time = hoursValue + ":" + minutesSpinner.getValue() + " " + periodSpinner.getValue().toString();
            instance.createNewAppointment(
                    respectiveClient,
                    nameInput.getText(),
                    dateInput.getValue(),
                    LocalTime.parse(time, DateTimeFormatter.ofPattern("hh:mm a", Locale.US)),
                    periodSpinner.getValue()
            );
            instance.requestCloseForWindow(App.ApplicationWindow.NEW_APPOINTMENT_DIALOGUE);

            // Reset values.
            nameInput.clear();
            dateInput.setValue(LocalDate.now());

        });

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
        minutesSpinner.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<>(
                FXCollections.observableArrayList(minuteStrings)
        ));

        // Period Spinner
        periodSpinner.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<>(
                FXCollections.observableArrayList(App.TimePeriod.AM, App.TimePeriod.PM)
        ));

        hoursSpinner.getStyleClass().clear();
        minutesSpinner.getStyleClass().clear();
        periodSpinner.getStyleClass().clear();

    }

    public void setRespectiveClient(Client respectiveClient) {
        this.respectiveClient = respectiveClient ;
    }

}
