package accounter.java.models;

import accounter.java.Appointment;
import javafx.scene.control.Label;
import org.jetbrains.annotations.NotNull;

public class AppointmentInfoLabel extends Label {

    public AppointmentInfoLabel(@NotNull Appointment appointment) {
        super(appointment.toString());
    }

}
