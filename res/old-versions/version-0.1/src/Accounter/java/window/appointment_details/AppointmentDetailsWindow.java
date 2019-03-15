/*
Copyright (c) 2019, Alexander Joseph Swanson Villares
alexanderjswanson@icloud.com | https://github.com/ajswanny
*/


/* Package */
package Accounter.java.window.appointment_details;


/* Imports */
import Accounter.fxml.appointment_details.ApptDetailsWindowController;
import Accounter.fxml.calendar.monthly.CalendarViewController;
import Accounter.java.Accounter;
import Accounter.java.appointment.Appointment;
import Accounter.java.window.AccounterWindow;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;


/**
 *
 */
public class AppointmentDetailsWindow extends AccounterWindow {

    /* Class Attributes */
    /**
     * The reference to the controller for the Calendar View.
     */
    private CalendarViewController calendar_view_controller;

    /**
     * The Appointment this Window displays details for.
     */
    private Appointment appointment;


    /* Class UI Elements */
    /**
     *
     */
    private AnchorPane root_container = new AnchorPane();

    /**
     *
     */
    private FXMLLoader fxml_loader = new FXMLLoader();

    /**
     *
     */
    private ApptDetailsWindowController fxml_controller;


    /* Constructors */
    /**
     * The default constructor.
     */
    public AppointmentDetailsWindow() { }

    /**
     *
     * @param appointment
     */
    public AppointmentDetailsWindow(
            CalendarViewController calendar_view_controller, Appointment appointment, boolean start_ui
    ) {

        // Define the Appointment.
        this.appointment = appointment;

        // Initialize the Accounter instance reference.
        this.calendar_view_controller = calendar_view_controller;

        // Start the UI.
        if (start_ui) start(primary_stage);

    }


    /* Methods */
    /**
     *
     * @param primary_stage
     */
    @Override
    public void start(Stage primary_stage) {

        // Define the handler for requests to close the window.
        this.primary_stage.setOnCloseRequest(action_event -> stop());

        // Define the primary Stage resizability.
        primary_stage.setResizable(false);

        // Set the primary_stage title.
        primary_stage.setTitle("");

        // Set the stage modality.
        primary_stage.initModality(Modality.APPLICATION_MODAL);

        try {

            // Define the root layout from the respective FXML file.
            fxml_loader.setLocation(Accounter.class.getResource(
                    "../fxml/appointment_details/appointment_details.fxml"
            ));
            root_container = fxml_loader.load();

            // Define the primary Scene.
            primary_scene = new Scene(root_container);
            primary_stage.setScene(primary_scene);

            // Define the FXML controller.
            fxml_controller = fxml_loader.getController();
            fxml_controller.init(appointment);

        } catch (IOException exception) {

            // Output status.
            System.out.println("Error: likely could not locate FXML file for 'AppointmentDetailsWindow'.");
            exception.printStackTrace();

        }

        // Define the stylesheet for the Scene.
        primary_scene.getStylesheets().add(Accounter.class.getResource(
                "../css/appointment_details_window/appointment_details_window.css").toExternalForm()
        );

        // Show the Stage.
        primary_stage.show();

    }

    /**
     *
     */
    @Override
    public void stop() {

        // Update the calendar_view_controller root_container CENTER if any Appointment attributes have changed.
        if (fxml_controller.changed_appt_attributes) { calendar_view_controller.update_root_center(); }

    }


}
