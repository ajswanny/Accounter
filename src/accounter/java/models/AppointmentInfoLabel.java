/*
 * Created by Alexander Swanson on 4/14/19 10:55 PM.
 * Email: alexanderjswanson@icloud.com.
 * Copyright © 2019. All rights reserved.
 */

package accounter.java.models;

import accounter.App;
import accounter.java.Appointment;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;

public class AppointmentInfoLabel extends Label {

    public Appointment appointment;

    public AppointmentInfoLabel(Appointment appointment, boolean withDate) {

        super();
        this.appointment = appointment;

        // Set Label text.
        if (withDate) {
            this.setText(appointment.toString());
        } else {
            this.setText(appointment.toStringWithoutDate());
        }

        // Action
        MenuItem deleteAppointment = new MenuItem("Delete");
        deleteAppointment.setOnAction(event -> App.getInstance().deleteAppointment(appointment));
        this.setContextMenu(new ContextMenu(deleteAppointment));

    }

}
