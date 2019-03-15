/*
Copyright (c) 2019, Alexander Joseph Swanson Villares
alexanderjswanson@icloud.com | https://github.com/ajswanny
*/


package Accounter.fxml.new_appointment;


import Accounter.java.window.new_client_appointment.NewAppointmentWindow;
import javafx.fxml.FXML;
import javafx.scene.control.Label;


/**
 *
 */
public class NewAppointmentWindowController {

    /**
     *
     */
    @FXML
    public Label test_label = new Label();

    /**
     *
     */
    private NewAppointmentWindow main_app;


    /* Constructor */
    /**
     *
     */
    public NewAppointmentWindowController() {

    }


    /* Methods */
    /**
     * Automatically called after the FXML file has been loaded.
     */
    @FXML
    private void initialize() {

    }

    /**
     * Defines the main application for this FXML controller.
     * @param main_app
     */
    public void set_main_app(NewAppointmentWindow main_app) {

        this.main_app = main_app;

    }


}
