package accounter.java;

import accounter.java.client.Client;

import java.io.Serializable;

public class Appointment implements Serializable {

    /* Fields */
    private String name;
    private String description;
    private Client attendee;


    /* Constructors */
    public Appointment(String name) {
        this.name = name;
    }

    public Appointment(String name, String description) {

        this(name);
        this.description = description;

    }

    public Appointment(String name, String description, Client attendee) {

        this(name, description);
        this.attendee= attendee;

    }


    /* Methods */
    public void setAttendee(Client attendee) {
        this.attendee = attendee;
    }


    /* Getters */
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Client getAttendee() {
        return attendee;
    }


    /* Setters */
    public void setDescription(String description) {
        this.description = description;
    }

}
