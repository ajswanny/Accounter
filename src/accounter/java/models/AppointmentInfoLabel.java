/*
 * Created by Alexander Swanson on 3/28/19 7:11 PM.
 * Email: alexanderjswanson@icloud.com.
 * Copyright © 2019. All rights reserved.
 */

package accounter.java.models;

import accounter.java.Appointment;
import javafx.scene.control.Label;
import org.jetbrains.annotations.NotNull;

public class AppointmentInfoLabel extends Label {

    public AppointmentInfoLabel(@NotNull Appointment appointment) {
        super(appointment.toString());
    }

}
