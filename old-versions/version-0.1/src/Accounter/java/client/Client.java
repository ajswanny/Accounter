/*
Copyright (c) 2019, Alexander Joseph Swanson Villares
alexanderjswanson@icloud.com | https://github.com/ajswanny
*/


/* Package */
package Accounter.java.client;


/* Imports */
import Accounter.java.appointment.Appointment;
import Accounter.java.enums.ClientType;

import java.io.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 *
 */
@SuppressWarnings({"Duplicates", "WeakerAccess"})
public class Client implements java.io.Serializable {
    // TODO: Optimize.


    /* Class Fields */
    /**
     * The Client type.
     */
    protected ClientType client_type;

    /**
     * The common identifier.
     */
    protected String identifier;

    /**
     * The age of the Client.
     */
    protected int age;

    /**
     * The numeric identifier.
     */
    protected long NID;

    /**
     * The Map of appointments and their dates.
     */
    protected List<Appointment> appointments;

    /**
     * The notes about the Client.
     */
    protected String notes;

    /**
     * The serialization variable.
     */
    private final long serialVersionUID = 1L;


    /* Constructors */
    /**
     * The default constructor.
     */
    public Client() {

        // Call the superior constructor.
        this("", "", 0);

    }

    /**
     *
     * @param identifier
     * @param notes
     * @param age
     */
    public Client(String identifier, String notes, int age) {

        // Define the object attributes.
        this.identifier = identifier;
        this.age = age;
        this.appointments = new ArrayList<>();
        this.notes = notes;
        this.client_type = ClientType.Generic;

        // INITIALIZE_DATA a unique numeric ID.
        this.NID = Instant.now().toEpochMilli();

    }

    /* Methods */
    /**
     *
     * @param file_path
     * @throws IOException
     */
    public void serialize(String file_path, Boolean verbose) throws IOException {

        // Define the File IO object.
        FileOutputStream file_out = new FileOutputStream(file_path);

        // Define the Object IO object.
        ObjectOutputStream object_out = new ObjectOutputStream(file_out);

        // Serialize 'this' to the current defined File object.
        object_out.writeObject(this);

        // Close the File and Object streams.
        file_out.close();
        object_out.close();

        // Output status.
        if (verbose) { System.out.println("Serialized object to: " + file_path); }

    }

    /**
     * Adds a new Appointment to the Client's appointment container.
     *
     * @param appointment The appointment to be appended to the Class' collection.
     */
    public void add_appointment(Appointment appointment) {

        // Append the Appointment to 'appointments'.
        this.appointments.add(appointment);

    }

    /**
     *
     * @param date
     * @return
     */
    public List<Appointment> get_appointments(Calendar date) {

        // Define a container for Appointments to be returned.
        List<Appointment> result = new ArrayList<>();

        // Identify Appointments that occur at the same date as the date provided as a parameter.
        for (Appointment appointment : appointments) {

            if (appointment.get_date().equals(date)) {
                result.add(appointment);
            }

        }

        return result;

    }

    /**
     * Sets a new value for the desired Client field.
     *
     * @param client_field The Client field to change.
     * @param value The new value for the field to be changed.
     * @param <T> The type of the new value.
     * @throws ClassCastException Thrown if the type of the new value does not match the type of the Client field.
     */
    public <T> void set_field(ClientField client_field, T value) throws ClassCastException {

        switch (client_field) {

            case IDENTIFIER:
                set_identifier((String) value);
                break;

            case AGE:
                set_age(Integer.valueOf((String) value));
                break;

            case NID:
                set_NID((Integer) value);
                break;

            case NOTES:
                set_notes((String) value);
                break;

        }

    }


    /* Getters */
    /**
     *
     * @return
     */
    public ClientType get_client_type() {

        return client_type;

    }

    /**
     *
     * @return
     */
    public String get_identifier() {

        return identifier;

    }

    /**
     *
     * @return
     */
    public int get_age() {

        return age;

    }

    /**
     *
     * @return
     */
    public long get_NID() {

        return NID;

    }

    /**
     *
     * @return
     */
    public List<Appointment> get_appointments() {

        return appointments;

    }

    /**
     *
     * @return
     */
    public String get_notes() {

        return notes;

    }


    /* Setters */
    /**
     *
     * @param client_type
     */
    public void set_client_type(ClientType client_type) {

        this.client_type = client_type;

    }

    /**
     *
     * @param identifier
     */
    public void set_identifier(String identifier) {

        this.identifier = identifier;

    }

    /**
     *
     * @param age
     */
    public void set_age(int age) {

        this.age = age;

    }

    /**
     *
     * @param NID
     */
    protected void set_NID(int NID) {

        this.NID = NID;

    }

    /**
     *
     * @param appointments
     */
    protected void set_appointments(List<Appointment> appointments) {

        this.appointments = appointments;

    }

    /**
     *
     * @param notes
     */
    public void set_notes(String notes) {

        this.notes = notes;

    }

}
