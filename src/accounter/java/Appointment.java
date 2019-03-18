package accounter.java;

import accounter.java.client.Client;

import java.io.Serializable;
import java.util.ArrayList;

public class Appointment implements Serializable {

    /* Fields */
    private String name;
    private String description;
    private ArrayList<Client> attendees;


    /* Constructors */
    public Appointment(String name) {
        this.name = name;
    }

    public Appointment(String name, String description) {

        this(name);
        this.description = description;

    }

    public Appointment(String name, String description, ArrayList<Client> attendees) {

        this(name, description);
        this.attendees = attendees;

    }


    /* Methods */
    public void addAttendee(Client client) {
        attendees.add(client);
    }


    /* Getters */
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<Client> getAttendees() {
        return attendees;
    }


    /* Setters */
    public void setDescription(String description) {
        this.description = description;
    }

    public void setAttendees(ArrayList<Client> attendees) {
        this.attendees = attendees;
    }

}
