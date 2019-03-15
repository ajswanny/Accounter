/*
Copyright (c) 2019, Alexander Joseph Swanson Villares
alexanderjswanson@icloud.com | https://github.com/ajswanny
*/


package Accounter.java.window.new_client;


import Accounter.fxml.calendar.monthly.CalendarViewController;
import Accounter.java.client.Client;
import Accounter.java.client.Corporation;

import java.io.IOException;

public class NewCorporationWindow extends NewClientWindow {
    // TODO: Optimize.

    /* Constructors*/
    public NewCorporationWindow(CalendarViewController calendar_view_controller) {

        // Define the 'Accounter' instance.
        this.calendar_view_controller = calendar_view_controller;

        // Start the application UI.
        this.start(this.primary_stage);

        // Perform necessary refactorings.
        perform_refactorings();

    }


    /**
     *
     * @param name
     * @param notes
     * @param age
     */
    @Override
    protected void create_new_client(String name, String notes, int age) {

        // Define the new Client object.
        Client client = new Corporation(name, notes, age);

        try {

            // Serialize the new Client.
            client.serialize("src/Accounter/data/clients/" + client.get_NID() + ".ser", true);

        } catch (IOException exception) {

            // Output status.
            System.out.println("Encountered IOException at src/Accounter/java/window/new_client/NewClientWindow.java" +
                    "when attempting to serialize a newly created Client.");

            // Output the error message.
            exception.printStackTrace();


        }

        // Indicate that a new Client was created.
        created_clients = true;

    }


    private void perform_refactorings() {

        // Redefine the "Client.Identifier" label text.
        this.rccrvb_sc1__identifier__label.setText("Name:");

    }

}
