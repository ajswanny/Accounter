package accounter.java.client;

import accounter.java.Appointment;

import java.util.ArrayList;

public class Individual extends Client {

    /* Fields */
    private String firstName;
    private String lastName;
    private ArrayList<Appointment> appointments;


    /* Constructors */
    public Individual(String firstName, String lastName) {

        this.firstName = firstName;
        this.lastName = lastName;
        sid = firstName + " " + lastName;

    }


    /* Methods */
    public void createNewAppointment(String name) {
        appointments.add(new Appointment(name));
    }

    /* Getters */
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public ArrayList<Appointment> getAppointments() {
        return appointments;
    }

}
