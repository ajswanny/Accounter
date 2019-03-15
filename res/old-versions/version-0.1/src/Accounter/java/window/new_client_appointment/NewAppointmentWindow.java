/*
Copyright (c) 2019, Alexander Joseph Swanson Villares
alexanderjswanson@icloud.com | https://github.com/ajswanny
*/


package Accounter.java.window.new_client_appointment;


import Accounter.fxml.calendar.monthly.CalendarViewController;
import Accounter.fxml.new_appointment.NewAppointmentWindowController;
import Accounter.java.Accounter;
import Accounter.java.appointment.Appointment;
import Accounter.java.client.Client;
import Accounter.java.simple_time.Time;
import Accounter.java.window.AccounterWindow;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.GregorianCalendar;


/**
 * A Window that allows for the creation of a new Appointment for any Client. The minimum fields necessary are a Name
 * and a Date.
 */
public class NewAppointmentWindow extends AccounterWindow {


    /* Class Attributes */
    /**
     * The reference to the Calendar View controller.
     */
    private CalendarViewController calendar_view_controller;

    /**
     * The Client this Window allows customization for.
     */
    private Client client;

    /**
     *
     */
    private boolean created_appointment = false;

    /**
     *
     */
    private ArrayList<String> minute_strings = new ArrayList<>(60);

    /**
     *
     */
    private ArrayList<String> time_period_strings = new ArrayList<>(2);


    /* Class UI Elements */
    /**
     *
     */
    private FXMLLoader fxml_loader = new FXMLLoader();

    /**
     * The first sub-container for the root-center VBox.
     * Short-name: sc1
     */
    private GridPane rcc_vb__sc1__gp = new GridPane();

    /**
     * The center Pane for the root BorderPane.
     * Short-name: rcc_vb
     */
    private VBox rc__center__vb = new VBox();

    /**
     *
     */
    private HBox sc1__date_selection__hb = new HBox();

    /**
     *
     */
    private HBox sc1__time_start_selection__hb = new HBox();

    /**
     *
     */
    private HBox sc1__time_end_selection__hb = new HBox();

    /**
     *
     */
    private Label sc1__appt_date__lbl = new Label("Date:");

    /**
     *
     */
    private Label sc1__start_time__lbl = new Label("Starts:");

    /**
     *
     */
    private Label sc1__end_time_lbl = new Label("Ends:");

    /**
     *
     */
    private Label rcc__invalid_data_entry__lbl = new Label();

    /**
     *
     */
    private Label sc1__time_start_colon__lbl = new Label(":");

    /**
     *
     */
    private Label sc1__time_end_colon__lbl = new Label(":");

    /**
     *
     */
    private Button rcc__save__btn = new Button("Accept");

    /**
     *
     */
    private TextField rcc__appt_name__tf = new TextField();

    /**
     *
     */
    private Spinner<Integer> sc1__start_time_hrs__sn = new Spinner<>(1, 12, 8);

    /**
     *
     */
    private Spinner<String> sc1__start_time_mins__sn = new Spinner<>();

    /**
     *
     */
    private Spinner<Integer> sc1__end_time_hrs__sn = new Spinner<>(1, 12, 10);

    /**
     *
     */
    private Spinner<String> sc1__end_time_mins__sn;

    /**
     *
     */
    private Spinner<Time.Period> sc1__start_time_period__sn;

    /**
     *
     */
    private Spinner<Time.Period> sc1__end_time_period__sn;

    /**
     *
     */
    private DatePicker sc1__appt_date__dp = new DatePicker();


    /* Constructors */
    /**
     * Constructs a NewAppointmentWindow with a respective Accounter application instance, the Client for which to
     * create new Appointments, and booleans indicating whether or not to start the UI and use FXML for this UI.
     */
    public NewAppointmentWindow(
            CalendarViewController calendar_view_controller, Client client, boolean start_ui
    ) {

        // Define the Client to be customized.
        this.client = client;

        // Define the reference to the Accounter application instance.
        this.calendar_view_controller = calendar_view_controller;

        // Start the UI.
        if (start_ui) start(primary_stage);

    }

    /**
     * Defines all dependencies and starts the application.
     *
     * @param primary_stage
     */
    @Override
    public void start(Stage primary_stage) {

        // Define the handler for requests to close the window.
        this.primary_stage.setOnCloseRequest(event -> stop());

        // Define the primary Stage resizability.
        primary_stage.setResizable(false);

        // Set the primary_stage title.
        primary_stage.setTitle("New Appointment");

        // Set the stage modality.
        primary_stage.initModality(Modality.APPLICATION_MODAL);

        // Define the data to be used for this Window.
        init_data();

        // Define the UI for this Window.
        init_ui();

        // Show the stage.
        primary_stage.show();

    }

    /**
     * Performs all necessary cleanup and closes the NewAppointmentWindow.
     *
     * Info: Intentionally not optimized.
     */
    @Override
    public void stop() {

        // Refactor the Accounter application instance to account for newly created Appointment.
        if (created_appointment) { calendar_view_controller.update_root_center(); }

        // Close the window.
        primary_stage.close();

    }

    /**
     * Defines all data for the UI.
     */
    private void init_data() {

        // Define the list of Strings representing the minutes of an hour.
        for (int i = 0; i < 60; i++) {

            // If the number has only one digit, add an initial zero.
            if (i < 10) {
                minute_strings.add("0" + String.valueOf(i));
            } else {
                minute_strings.add(String.valueOf(i));
            }

        }

        // Define the list of Strings representing the time-period of a day.
        time_period_strings.add("AM");
        time_period_strings.add("PM");

    }

    /**
     * Initializes all UI elements.
     */
    private void init_ui() {

        // Define the primary Scene.
        primary_scene = new Scene(root_container);
        primary_stage.setScene(primary_scene);

        // Define the root BorderPane.
        def_rc_center();

    }

    /**
     * Defines the UI for the root-container's CENTER node.
     */
    private void def_rc_center() {

        // Set the center Pane for the root BorderPane.
        root_container.setCenter(rc__center__vb);

        // Set the alignment of the root-center VBox.
        rc__center__vb.setAlignment(Pos.TOP_LEFT);

        // Define the first sub-container for the root-center VBox.
        def_rcc_sc1();

        // Define the action for saving a new Appointment.
        rcc__save__btn.setOnAction(action_event -> {
            save_appointment(false);
            created_appointment = true;
            stop();
        });

        // Set the visibility for the Label indicating if a user has provided an invalid Time entry.
        rcc__invalid_data_entry__lbl.setVisible(false);

        // Customize the TextField for the Appointment name.
        rcc__appt_name__tf.setMaxWidth(150);
        rcc__appt_name__tf.setPromptText("Appointment Name");

        // Add the appropriate controls to the root-center.
        rc__center__vb.getChildren().addAll(
                rcc__appt_name__tf, rcc_vb__sc1__gp, rcc__save__btn, rcc__invalid_data_entry__lbl
        );

    }


    /**
     * Defines the first sub-container for the root-container's CENTER.
     */
    private void def_rcc_sc1() {

        def_sc1_spinners();

        // Set the alignment of the sub-container.
        rcc_vb__sc1__gp.setAlignment(Pos.TOP_CENTER);

        // Add the controls for the entry of the new Appointment's Start Time.
        sc1__time_start_selection__hb.getChildren().addAll(
                sc1__start_time_hrs__sn,
                sc1__time_start_colon__lbl,
                sc1__start_time_mins__sn,
                sc1__start_time_period__sn
        );
        rcc_vb__sc1__gp.addRow(1, sc1__start_time__lbl, sc1__time_start_selection__hb);

        // Add the controls for the entry of the new Appointment's End Time.
        sc1__time_end_selection__hb.getChildren().addAll(
                sc1__end_time_hrs__sn, sc1__time_end_colon__lbl, sc1__end_time_mins__sn, sc1__end_time_period__sn
        );
        rcc_vb__sc1__gp.addRow(2, sc1__end_time_lbl, sc1__time_end_selection__hb);

        // Set the default Date value.
        sc1__appt_date__dp.setValue(LocalDate.now());

        // Add the controls for the entry of the new Appointment's Date.
        sc1__date_selection__hb.getChildren().addAll(sc1__appt_date__dp);
        rcc_vb__sc1__gp.addRow(3, sc1__appt_date__lbl, sc1__appt_date__dp);

    }

    /**
     * Defines all the JavaFX Spinners and performs necessary customizations on them.
     */
    private void def_sc1_spinners() {

        // Define the contents of the MINUTE Spinners.
        sc1__start_time_mins__sn = new Spinner<>(FXCollections.observableArrayList(minute_strings));
        sc1__end_time_mins__sn = new Spinner<>(FXCollections.observableArrayList(minute_strings));

        // Define the contents of the TIME-PERIOD Spinners.
        sc1__start_time_period__sn = new Spinner<>(FXCollections.observableArrayList(Time.Period.AM, Time.Period.PM));
        sc1__end_time_period__sn = new Spinner<>(FXCollections.observableArrayList(Time.Period.AM, Time.Period.PM));

        // Clear the default Style Classes.
        sc1__start_time_hrs__sn.getStyleClass().clear();
        sc1__start_time_mins__sn.getStyleClass().clear();
        sc1__start_time_period__sn.getStyleClass().clear();
        sc1__end_time_hrs__sn.getStyleClass().clear();
        sc1__end_time_mins__sn.getStyleClass().clear();
        sc1__end_time_period__sn.getStyleClass().clear();

    }

    /**
     *
     * @param verbose
     */
    private void save_appointment(boolean verbose) {

        // Fetch the Appointment Name data.
        String appt_name = rcc__appt_name__tf.getText();

        // Fetch the Appointment Time data.
        int start_time_hrs = sc1__start_time_hrs__sn.getValue();
        String start_time_mins = sc1__start_time_mins__sn.getValue();
        Time.Period start_time_period = sc1__start_time_period__sn.getValue();

        int end_time_hrs = sc1__end_time_hrs__sn.getValue();
        String end_time_mins = sc1__end_time_mins__sn.getValue();
        Time.Period end_time_period = sc1__end_time_period__sn.getValue();

        // Fetch the Appointment Date data.
        LocalDate dp_value = sc1__appt_date__dp.getValue();

        // Create the new Appointment.
        client.add_appointment(new Appointment(
                appt_name,
                new GregorianCalendar(dp_value.getYear(), dp_value.getMonthValue() - 1, dp_value.getDayOfMonth()),
                new Time(start_time_hrs, Integer.valueOf(start_time_mins), start_time_period),
                new Time(end_time_hrs, Integer.valueOf(end_time_mins), end_time_period),
                client
        ));

    }

    /**
     * Initializes all dependencies for the Window's FXML.
     */
    private void init_fxml() {

        try {

            // Define the root layout from the respective FXML file.
            fxml_loader.setLocation(Accounter.class.getResource(
                    "../fxml/new_appointment/new_appointment_window.fxml"
            ));
            root_container = fxml_loader.load();

            // Define the primary Scene.
            primary_scene = new Scene(root_container);
            primary_stage.setScene(primary_scene);

            // Define the FXML controller.
            /**
             *
             */
            NewAppointmentWindowController fxml_controller = fxml_loader.getController();
            fxml_controller.set_main_app(this);

        } catch (IOException exception) {

            // Output status.
            System.out.println("Error: likely could not locate FXML file for 'NewAppointmentWindow'.");
            exception.printStackTrace();

        }

    }

}
