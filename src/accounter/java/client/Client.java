package accounter.java.client;

import accounter.java.Appointment;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Client implements Serializable {

    String sid;

    private ArrayList<Appointment> appointments;

    /* Methods */
    public void createNewAppointment(String name) {
        appointments.add(new Appointment(name));
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
