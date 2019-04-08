/*
 * Created by Alexander Swanson on 4/7/19 8:06 PM.
 * Email: alexanderjswanson@icloud.com.
 * Copyright © 2019. All rights reserved.
 */

package accounter.java.models;

import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.util.ArrayList;


public class DayGridPaneBase extends AnchorPane {

    /* Fields */
    private ArrayList<AppointmentInfoLabel> appointmentInfoLabels;
    private VBox appointmentInfoButtonContainer;
    private LocalDate localDate;

    /* Constructors */
    private DayGridPaneBase(LocalDate localDate) {
        super();
        this.localDate = localDate;
        appointmentInfoLabels = new ArrayList<>();
        appointmentInfoButtonContainer = new VBox();
    }

    public DayGridPaneBase(int dayOfMonth, LocalDate localDate) {
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

    public DayGridPaneBase(int dayOfMonth, ArrayList<AppointmentInfoLabel> appointments, LocalDate localDate) {
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
        AnchorPane.setLeftAnchor(appointmentInfoLabel, 0.0);
        AnchorPane.setRightAnchor(appointmentInfoLabel, 0.0);
        appointmentInfoLabels.add(appointmentInfoLabel);
        appointmentInfoButtonContainer.getChildren().add(appointmentInfoLabel);
    }

    /* Getters */
    public LocalDate getLocalDate() {
        return localDate;
    }

}
