/*
Copyright (c) 2019, Alexander Joseph Swanson Villares
alexanderjswanson@icloud.com | https://github.com/ajswanny
*/

package Accounter.java.client;


import Accounter.java.enums.ClientType;

import java.time.Instant;
import java.util.ArrayList;


public class Corporation extends Client {

    /* Attributes */


    /**
     * Default constructor.
     */
    public Corporation() {

        this("", "", 0);

    }


    /**
     * Constructor to define CLIENT of type PERSON with identifier provided.
     *
     * @param identifier The common identifier.
     */
    public Corporation(String identifier) {

        // Call greater constructor.
        this(identifier, "", 0);

    }


    /**
     *
     * @param identifier The common identifier.
     * @param notes The CLIENT notes.
     * @param age The CLIENT age.
     */
    public Corporation(String identifier, String notes, int age) {

        // Define the common identifier.
        this.identifier = identifier;

        // Define the Client age.
        this.age = age;

        // Define the Client notes.
        this.notes = notes;

        // Define the Client type.
        this.client_type = ClientType.Corporation;

        // Define the unique numeric ID.
        this.NID = Instant.now().toEpochMilli();

        // Define the Appointments ArrayList.
        this.appointments = new ArrayList<>();

    }


}
