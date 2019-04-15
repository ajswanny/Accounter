/*
 * Created by Alexander Swanson on 4/14/19 10:55 PM.
 * Email: alexanderjswanson@icloud.com.
 * Copyright © 2019. All rights reserved.
 */

package accounter.java.client;

import accounter.java.Appointment;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Client implements Serializable {

    String sid;

    protected ArrayList<Appointment> appointments;

    /* Methods */
    public void defineNewAppointment(Appointment appointment) {
        try {
            appointments.add(appointment);
        } catch (NullPointerException e) {
            appointments = new ArrayList<>();
            appointments.add(appointment);
        }
    }

    @Override
    public String toString() {
        return sid;
    }

    /* Getters */
    public ArrayList<Appointment> getAppointments() {
        return appointments;
    }

}
