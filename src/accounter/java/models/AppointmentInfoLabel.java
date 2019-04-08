/*
 * Created by Alexander Swanson on 4/7/19 8:06 PM.
 * Email: alexanderjswanson@icloud.com.
 * Copyright © 2019. All rights reserved.
 */

package accounter.java.models;

import accounter.java.Appointment;
import javafx.scene.control.Label;
import org.jetbrains.annotations.NotNull;

public class AppointmentInfoLabel extends Label {

    public AppointmentInfoLabel(@NotNull Appointment appointment, boolean withDate) {
        super();
        if (withDate) {
            this.setText(appointment.toString());
        } else {
            this.setText(appointment.toStringWithoutDate());
        }
    }

}
