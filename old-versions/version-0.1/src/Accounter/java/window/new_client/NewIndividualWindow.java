/*
Copyright (c) 2019, Alexander Joseph Swanson Villares
alexanderjswanson@icloud.com | https://github.com/ajswanny
*/

package Accounter.java.window.new_client;


import Accounter.fxml.calendar.monthly.CalendarViewController;
import Accounter.java.client.Client;
import Accounter.java.client.Individual;
import Accounter.java.enums.ClientType;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class NewIndividualWindow extends NewClientWindow {
    // TODO: Optimize.


    /* Application UI Elements */
    private TextField rccrvb_sc1__first_name__tf;
    private TextField rccrvb_sc1__last_name__tf;



    /* Constructors */
    public NewIndividualWindow(CalendarViewController calendar_view_controller) {

        // Define the 'Accounter' instance.
        this.calendar_view_controller = calendar_view_controller;

        // Define the ClientType that this window allows for creation of.
        this.client_type = ClientType.Individual;

        // Start the application UI.
        this.start(this.primary_stage);

    }


    /* Methods */
    /**
     * Defines the first sub-container of the root CENTER root container.
     *
     * :method id: rccrvb_sc1
     */
    @Override
    protected void init_rccrvb_sc1() {

        // $DEVELOPMENT
        rccrvb__sc1__gp.setGridLinesVisible(true);

        // Define the sub-container alignment.
        rccrvb__sc1__gp.setAlignment(Pos.TOP_CENTER);

        // Define the sub-container vertical and horizontal gaps.
        rccrvb__sc1__gp.setVgap(15);
        rccrvb__sc1__gp.setHgap(15);


        // Define the Entry titles.
        Label rccrvb_sc1__first_name__label = new Label("First name:");
        Label rccrvb_sc1__last_name__label = new Label("Last name:");
        Label rccrvb_sc1__age__label = new Label("Age:");
        Label rccrvb_sc1__notes__label = new Label("Notes:");


        // Define the Client attribute TextFields.
        this.rccrvb_sc1__first_name__tf = new TextField();
        this.rccrvb_sc1__last_name__tf = new TextField();
        this.rccrvb_sc1__age__tf = new TextField();
        this.rccrvb_sc1__notes_tf = new TextField();


        // Add the controls to their respective container.
        rccrvb__sc1__gp.addRow(0, rccrvb_sc1__first_name__label, rccrvb_sc1__first_name__tf);
        rccrvb__sc1__gp.addRow(1, rccrvb_sc1__last_name__label, rccrvb_sc1__last_name__tf);
        rccrvb__sc1__gp.addRow(2, rccrvb_sc1__age__label, rccrvb_sc1__age__tf);
        rccrvb__sc1__gp.addRow(3, rccrvb_sc1__notes__label, rccrvb_sc1__notes_tf);

    }


    @Override
    protected void create_new_client(String name, String notes, int age) {

        // Define the new Client object.
        Client client = new Individual(
                rccrvb_sc1__first_name__tf.getText(),
                rccrvb_sc1__last_name__tf.getText(),
                notes,
                age
        );

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

}
