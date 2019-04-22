/*
Copyright (c) 2019, Alexander Joseph Swanson Villares
alexanderjswanson@icloud.com | https://github.com/ajswanny
*/


package Accounter.java.window.new_client;


import Accounter.fxml.calendar.monthly.CalendarViewController;
import Accounter.java.client.Client;
import Accounter.java.enums.ClientType;
import Accounter.java.enums.MethodTask;
import Accounter.java.window.AccounterWindow;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

import static Accounter.java.enums.ClientType.Corporation;
import static Accounter.java.enums.ClientType.Generic;


class NewClientWindow extends AccounterWindow {
    // TODO: Optimize.


    /* Class Attributes */
    /**
     * The indicator of the type of Client this window allows creation for.
     */
    ClientType client_type;

    boolean created_clients = false;

    /**
     * The reference to the controller for the Calendar View.
     */
    CalendarViewController calendar_view_controller;


    /* Class UI Elements */
    /**
     * The container for all root CENTER sub-containers.
     * Short name: rccrvb
     */
    private VBox root_container__center_root__vb = new VBox();

    /**
     * The first sub-container for the root CENTER.
     * Short name: rccrvb_sc1
     */
    GridPane rccrvb__sc1__gp = new GridPane();

    /**
     * The second sub-container for the root CENTER.
     * Short name: rccrvb_sc2
     */
    private VBox rccrvb__sc2__vb = new VBox();

    /**
     * The data entry controls for the first sub-container of the root CENTER.
     */
    private TextField rccrvb_sc1__identifier__tf;
    Label rccrvb_sc1__identifier__label;
    TextField rccrvb_sc1__age__tf;
    TextField rccrvb_sc1__notes_tf;


    /* Constructors */
    NewClientWindow() {

        // Output status.
        System.out.println("Initialized a 'NewClientWindow' with its default constructor.");

        // Define the window's Client type.
        client_type = Generic;

    }

    /* Methods */
    @Override
    public void stop() {

        if (created_clients) {

            // Update the UI Client list and its container.
            calendar_view_controller.def_client_list(MethodTask.UPDATE_DATA);
            calendar_view_controller.update_root_center();

        }

        // Close the View.
        primary_stage.close();

    }

    /**
     * Defines the window's dependencies and initializes its UI.
     */
    @Override
    public void start(Stage primary_stage) {

        // Define the handler for requests to close the window.
        this.primary_stage.setOnCloseRequest(action_event -> {

            // Run the closing processes.
            stop();

        });

        // Define the primary Stage resizability.
        primary_stage.setResizable(true);

        // Define the primary Scene.
        primary_scene = new Scene(root_container, 600, 400);

        // Set the Scene.
        primary_stage.setScene(primary_scene);

        // Set the primary_stage title.
        primary_stage.setTitle("New Client");

        // Define the primary Stage dependencies.
        init_primary_stage_dependencies();

        // Set the stage modality.
        primary_stage.initModality(Modality.APPLICATION_MODAL);

        // Show the stage.
        primary_stage.show();

    }


    /**
     * Defines the primary stage's dependencies.
     */
    private void init_primary_stage_dependencies() {

        // Define the root container.
        init_root_container();

    }


    /**
     * Defines the window's root container.
     */
    private void init_root_container() {

        // Define the root CENTER.
        root_container.setCenter(root_container__center_root__vb);

        // INITIALIZE_DATA the root CENTER.
        init_root_center();

    }


    /**
     * Defines the root CENTER container.
     */
    private void init_root_center() {

        // Define the root CENTER's root container alignment.
        root_container__center_root__vb.setAlignment(Pos.TOP_CENTER);

        // Add the sub-containers to the root CENTER.
        root_container__center_root__vb.getChildren().addAll(
                rccrvb__sc1__gp,
                rccrvb__sc2__vb
        );

        // INITIALIZE_DATA the root CENTER sub-container 1.
        init_rccrvb_sc1();

        //INITIALIZE_DATA the root CENTER sub-container 2.
        init_root_cntr_sc2();

    }


    /**
     * Defines the first sub-container of the root CENTER root container.
     *
     * :method id: ircs1
     */
    protected void init_rccrvb_sc1() {

        // $DEVELOPMENT
        rccrvb__sc1__gp.setGridLinesVisible(true);

        // Define the sub-container alignment.
        rccrvb__sc1__gp.setAlignment(Pos.TOP_CENTER);

        // Define the sub-container vertical and horizontal gaps.
        rccrvb__sc1__gp.setVgap(15);
        rccrvb__sc1__gp.setHgap(15);


        // Define the Entry titles.
        this.rccrvb_sc1__identifier__label = new Label("Identifier:");
        Label rccrvb_sc1__age__label = new Label("Age:");
        Label rccrvb_sc1__notes__label = new Label("Notes:");


        // Define the Client attribute TextFields.
        this.rccrvb_sc1__identifier__tf = new TextField();
        this.rccrvb_sc1__age__tf = new TextField();
        this.rccrvb_sc1__notes_tf = new TextField();


        // Add the controls to their respective container.
        rccrvb__sc1__gp.addRow(0, rccrvb_sc1__identifier__label, rccrvb_sc1__identifier__tf);
        rccrvb__sc1__gp.addRow(1, rccrvb_sc1__age__label, rccrvb_sc1__age__tf);
        rccrvb__sc1__gp.addRow(2, rccrvb_sc1__notes__label, rccrvb_sc1__notes_tf);

    }


    /**
     * Defines the second sub-container of the root CENTER root container.
     *
     * :method id: ircs2
     */
    private void init_root_cntr_sc2() {
        // TODO: OPTIMIZE IF STATEMENT.

        // Define the status for invalid entry of the Client 'age' attribute Label.
        Label invalid_age__label = new Label("Please enter a valid number for Age.");
        rccrvb__sc1__gp.add(invalid_age__label, 0, 4);

        // Set the visibility as false, as the creation of a new Client has not yet been attempted.
        invalid_age__label.setVisible(false);

        // Define the button to confirm creation of a new Client.
        Button confirm_new_client__btn = new Button("Accept");
        confirm_new_client__btn.setOnAction(action_event -> {

            try {

                if (client_type.equals(Generic) | client_type.equals(Corporation)) {

                    // Define and serialize the new Client:Generic or Client:Corporation object.
                    create_new_client(
                            rccrvb_sc1__identifier__tf.getText(),
                            rccrvb_sc1__notes_tf.getText(),
                            Integer.valueOf(rccrvb_sc1__age__tf.getText())
                    );

                } else {

                    // Define and serialize the new Client:Individual object.
                    create_new_client(
                            "",
                            rccrvb_sc1__notes_tf.getText(),
                            Integer.valueOf(rccrvb_sc1__age__tf.getText())
                    );

                }

                stop();

            } catch (NumberFormatException e) {

                System.out.println("Unable to parse provided Client age to create new Client.");
                invalid_age__label.setVisible(true);

            }

        });

        // Define the second sub-container alignment.
        rccrvb__sc2__vb.setAlignment(Pos.TOP_CENTER);

        // Add the button to its VBox.
        confirm_new_client__btn.setAlignment(Pos.CENTER);
        rccrvb__sc2__vb.getChildren().add(confirm_new_client__btn);

    }


    /**
     * Creates a new Client object and serializes it.
     *
     * @param name
     * @param notes
     * @param age
     */
    protected void create_new_client(String name, String notes, int age) {

        // Define the new Client object.
        Client client = new Client(name, notes, age);

        try {

            // Serialize the new Client.
            client.serialize("src/Accounter/resources/clients/" + client.get_NID() + ".ser", true);

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
