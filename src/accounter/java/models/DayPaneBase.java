/*
 * Created by Alexander Swanson on 4/9/19 11:40 AM.
 * Email: alexanderjswanson@icloud.com.
 * Copyright © 2019. All rights reserved.
 */

package accounter.java.models;

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
        appointmentInfoButtonContainer.setLayoutX(10.0);
        appointmentInfoButtonContainer.setLayoutY(10.0);
    }

    public DayPaneBase(int dayOfMonth, LocalDate localDate) {
        this(localDate);

        // Init date label
        Label label = new Label(String.valueOf(dayOfMonth));
        AnchorPane.setLeftAnchor(label, 5.0);
        AnchorPane.setTopAnchor(label, 5.0);

        // Init container for AppointmentInfoLabels
        AnchorPane.setLeftAnchor(appointmentInfoButtonContainer, 0.0);
        AnchorPane.setRightAnchor(appointmentInfoButtonContainer, 0.0);
        appointmentInfoButtonContainer.setMaxWidth(this.getWidth());
        appointmentInfoButtonContainer.setMaxHeight(this.getHeight());

        this.getChildren().addAll(label, appointmentInfoButtonContainer);
    }

    public DayPaneBase(int dayOfMonth, ArrayList<AppointmentInfoLabel> appointments, LocalDate localDate) {
        this(localDate);

        // Init date label
        Label label = new Label(String.valueOf(dayOfMonth));
        AnchorPane.setLeftAnchor(label, 5.0);
        AnchorPane.setTopAnchor(label, 5.0);

        // Init container for AppointmentInfoLabels
        AnchorPane.setLeftAnchor(appointmentInfoButtonContainer, 1.0);
        AnchorPane.setRightAnchor(appointmentInfoButtonContainer, 1.0);
        appointmentInfoButtonContainer.setMaxWidth(this.getWidth());
        appointmentInfoButtonContainer.setMaxHeight(this.getHeight());

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

    /* Getters */
    public LocalDate getLocalDate() {
        return localDate;
    }

}
