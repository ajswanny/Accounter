/*
 * Created by Alexander Swanson on 4/7/19 2:34 PM.
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
    private VBox appointmentInfoLabelsContainer;
    private LocalDate localDate;

    /* Constructors */
    public DayGridPaneBase(int dayOfMonth, LocalDate localDate) {
        super();

        // Init date label
        Label label = new Label(String.valueOf(dayOfMonth));
        AnchorPane.setLeftAnchor(label, 5.0);
        AnchorPane.setTopAnchor(label, 5.0);

        // Init container for AppointmentInfoLabels
        appointmentInfoLabelsContainer = new VBox();
        AnchorPane.setLeftAnchor(appointmentInfoLabelsContainer, 0.0);
        AnchorPane.setBottomAnchor(appointmentInfoLabelsContainer, 0.0);
        AnchorPane.setRightAnchor(appointmentInfoLabelsContainer, 0.0);

        this.getChildren().addAll(label, appointmentInfoLabelsContainer);
        this.localDate = localDate;
    }

    public DayGridPaneBase(int dayOfMonth, ArrayList<AppointmentInfoLabel> appointments, LocalDate localDate) {
        super();

        // Init date label
        Label label = new Label(String.valueOf(dayOfMonth));
        AnchorPane.setLeftAnchor(label, 5.0);
        AnchorPane.setTopAnchor(label, 5.0);

        // Init container for AppointmentInfoLabels
        appointmentInfoLabelsContainer = new VBox();
        AnchorPane.setLeftAnchor(appointmentInfoLabelsContainer, 0.0);
        AnchorPane.setBottomAnchor(appointmentInfoLabelsContainer, 0.0);
        AnchorPane.setRightAnchor(appointmentInfoLabelsContainer, 0.0);

        if (!appointments.isEmpty()) {
            appointmentInfoLabels = new ArrayList<>(appointments);
            appointmentInfoLabelsContainer.getChildren().addAll(appointmentInfoLabels);
        }

        this.getChildren().addAll(label, appointmentInfoLabelsContainer);
        this.localDate = localDate;
    }

    /* Methods */
    public void addNewAppointmentInfoLabel(AppointmentInfoLabel appointmentInfoLabel) {
        appointmentInfoLabels.add(appointmentInfoLabel);
        appointmentInfoLabelsContainer.getChildren().add(appointmentInfoLabel);
    }

}
