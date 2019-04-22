/*
Copyright (c) 2019, Alexander Joseph Swanson Villares
alexanderjswanson@icloud.com | https://github.com/ajswanny
*/

package Accounter.java.client;


import Accounter.java.enums.ClientType;

import java.time.Instant;
import java.util.ArrayList;


public class Individual extends Client {


    /* Fields */

    // The Client's first name.
    private String first_name;

    // The Client's last name.
    private String last_name;


    /* Constructor(s) */
    /**
     * Default constructor.
     */
    public Individual() {

        // INITIALIZE_DATA an Individual with a blank name.
        this("", "", "", 0);

    }

    /**
     * Constructor to init CLIENT of type INDIVIDUAL with first_name & last_name provided, setting all other
     * fields as type defaults.
     *
     * @param first_name The first name of the CLIENT.
     * @param last_name The last name of the CLIENT.
     */
    public Individual(String first_name, String last_name) {

        // Call grater constructor.
        this(first_name, last_name, "", 0);

    }


    /**
     *
     * @param first_name The first name of the CLIENT.
     * @param last_name The last name of the CLIENT.
     * @param notes The notes about the CLIENT.
     * @param age The age of the CLIENT.
     */
    public Individual(String first_name, String last_name, String notes, int age) {

        super(first_name + "" + last_name, notes, age);

        // Define the first and last name.
        this.first_name = first_name;
        this.last_name = last_name;

        // Define the Client notes.
        this.notes = notes;

        // Define the Client age.
        this.age = age;

        // Define the common identifier.
        this.identifier = first_name + " " + last_name;

        // Define the Client type.
        this.client_type = ClientType.Individual;

        // INITIALIZE_DATA a unique numeric ID.
        this.NID = Instant.now().toEpochMilli();

        // INITIALIZE_DATA the Appointments ArrayList.
        this.appointments = new ArrayList<>();

    }

    /**
     * Sets a new value for the desired Client field.
     *
     * @param client_field The Client field to change.
     * @param value The new value for the field to be changed.
     * @param <T> The type of the new value.
     * @throws ClassCastException Thrown if the type of the new value does not match the type of the Client field.
     */
    @Override
    public <T> void set_field(ClientField client_field, T value) throws ClassCastException {

        switch (client_field) {

            case IDENTIFIER:
                set_identifier((String) value);
                break;

            case FIRST_NAME:
                set_first_name((String) value);
                break;

            case LAST_NAME:
                set_last_name((String) value);
                break;

            case AGE:
                set_age(Integer.valueOf((String) value));
                break;

            case NOTES:
                set_notes((String) value);
                break;

            case NID:
                set_age(Integer.valueOf((String) value));
                break;

        }

    }


    /* Getters */
    public String get_first_name() {

        return first_name;

    }

    public String get_last_name() {

        return last_name;

    }


    /* Setters */
    public void set_identifier() {

        identifier = first_name + " " + last_name;

    }

    public void set_first_name(String first_name) {

        this.first_name = first_name;
        set_identifier();

    }

    public void set_last_name(String last_name) {

        this.last_name = last_name;
        set_identifier();

    }


}
