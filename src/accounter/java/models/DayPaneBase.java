/*
 * Created by Alexander Swanson on 4/14/19 10:55 PM.
 * Email: alexanderjswanson@icloud.com.
 * Copyright © 2019. All rights reserved.
 */

package accounter.java.models;

import accounter.java.Appointment;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.util.ArrayList;


public class DayPaneBase extends AnchorPane {

    /* Fields */
    private ArrayList<AppointmentInfoLabel> appointmentInfoLabels;
    private VBox appointmentInfoButtonContainer;
    private LocalDate localDate;

    /* Constructors */
    private DayPaneBase(LocalDate localDate) {
        super();
        this.localDate = localDate;
        appointmentInfoLabels = new ArrayList<>();
        appointmentInfoButtonContainer = new VBox();
        appointmentInfoButtonContainer.setLayoutY(30.0);
        AnchorPane.setLeftAnchor(appointmentInfoButtonContainer, 5.0);
    }

    public DayPaneBase(int dayOfMonth, LocalDate localDate) {
        this(localDate);

        // Init date label
        Label label = new Label(String.valueOf(dayOfMonth));
        AnchorPane.setLeftAnchor(label, 5.0);
        AnchorPane.setTopAnchor(label, 5.0);

        this.getChildren().addAll(label, appointmentInfoButtonContainer);
    }

    public DayPaneBase(int dayOfMonth, ArrayList<AppointmentInfoLabel> appointments, LocalDate localDate) {
        this(localDate);

        // Init date label
        Label label = new Label(String.valueOf(dayOfMonth));
        AnchorPane.setLeftAnchor(label, 5.0);
        AnchorPane.setTopAnchor(label, 5.0);

        if (!appointments.isEmpty()) {
            appointmentInfoLabels.addAll(appointments);
            appointmentInfoButtonContainer.getChildren().addAll(appointmentInfoLabels);
        }

        this.getChildren().addAll(label, appointmentInfoButtonContainer);
    }

    /* Methods */
    public void addNewAppointmentInfoLabel(AppointmentInfoLabel appointmentInfoLabel) {
        appointmentInfoLabels.add(appointmentInfoLabel);
        appointmentInfoButtonContainer.getChildren().add(appointmentInfoLabel);
    }

    public void removeAppointmentInfoLabelOfAppointment(Appointment appointment) {
        AppointmentInfoLabel toRemove = null;
        for (AppointmentInfoLabel appointmentInfoLabel : appointmentInfoLabels) {
            if (appointmentInfoLabel.appointment == appointment) {
                toRemove = appointmentInfoLabel;
            }
        }
        appointmentInfoLabels.remove(toRemove);
        appointmentInfoButtonContainer.getChildren().remove(toRemove);
    }

}
