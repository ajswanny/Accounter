/*
Copyright (c) 2019, Alexander Joseph Swanson Villares
alexanderjswanson@icloud.com | https://github.com/ajswanny
*/


/* Package */
package Accounter.fxml.appointment_details;


/* Imports */
import Accounter.java.Accounter;
import Accounter.java.appointment.Appointment;
import Accounter.java.simple_time.Time;
import Accounter.java.window.appointment_details.AppointmentDetailsWindow;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;


/**
 *
 */
@SuppressWarnings("Duplicates")
public class ApptDetailsWindowController extends AppointmentDetailsWindow {


    /* Class Attributes */
    /**
     *
     */
    private Appointment appointment;

    /**
     *
     */
    public boolean changed_appt_attributes = false;

    /**
     *
     */
    private ArrayList<String> time_minute_strings = new ArrayList<>(60);


    /* Class UI Elements */
    /**
     *
     */
    @FXML
    Label rc__appt_date__lbl = new Label();

    /**
     *
     */
    @FXML
    Label rc__appt_time_starts_lbl = new Label();

    /**
     *
     */
    @FXML
    Label rc__appt_time_ends__lbl = new Label();

    /**
     *
     */
    @FXML
    Label rc__appt_with_client__lbl = new Label();

    /**
     *
     */
    @FXML
    Label rc__appt_client__lbl = new Label();

    /**
     *
     */
    @FXML
    Label rc__appt_notes__lbl = new Label();

    /**
     *
     */
    private Label rchb1__start_time_colon__lbl = new Label(":");

    /**
     *
     */
    private Label rchb2__end_time_colon__lbl = new Label(":");

    /**
     *
     */
    @FXML
    TextField rc__appt_name__tf = new TextField();

    /**
     *
     */
    @FXML
    DatePicker rc__date__dp =  new DatePicker();

    /**
     *
     */
    private Spinner<Integer> rchb1__start_time_hrs__sn = new Spinner<>(1, 12, 8);

    /**
     *
     */
    private Spinner<String> rchb1__start_time_mins__sn = new Spinner<>();

    /**
     *
     */
    private Spinner<Integer> rchb2__end_time_hrs__sn = new Spinner<>(1, 12, 10);

    /**
     *
     */
    private Spinner<String> rchb2__end_time_mins__sn;

    /**
     *
     */
    private Spinner<Time.Period> rchb1__start_time_period__sn;

    /**
     *
     */
    private Spinner<Time.Period> rchb2__end_time_period__sn;

    /**
     *
     */
    @FXML
    TextArea rc__appt_notes__ta = new TextArea();

    /**
     *
     */
    @FXML
    HBox rc__hb1 = new HBox();

    /**
     *
     */
    @FXML
    HBox rc__hb2 = new HBox();


    /* Constructors */
    public ApptDetailsWindowController() { }


    /* Methods */
    /**
     * Automatically called after the FXML file has been loaded.
     */
    @FXML
    public void initialize() { }

    /**
     * Defines the main application for this FXML controller and initializes the data for the UI.
     */
    public void init(Appointment appointment) {

        // Define the class attributes.
        this.appointment = appointment;

        // Initialize the program data.
        init_data();

        // Initialize the program UI.
        init_ui();

    }

    /**
     * Initializes program data.
     */
    private void init_data() {

        // Define the list of Strings representing the minutes of an hour.
        for (int i = 0; i < 60; i++) {

            // If the number has only one digit, add an initial zero.
            if (i < 10) {
                time_minute_strings.add("0" + i);
            } else {
                time_minute_strings.add(String.valueOf(i));
            }

        }

    }

    /**
     * Initializes the UI elements.
     */
    private void init_ui() {

        // Set the text for the Label indicating the Appointment name.
        rc__appt_name__tf.setText(appointment.get_name());

        // Enable text-wrapping for the TextArea displaying the Appointment name.
        rc__appt_notes__ta.setWrapText(true);

        // Initialize the JavaFX Spinners for time selection.
        init_spinners();

        //
        init_hb1();

        //
        init_hb2();

        // Set the default date of the DatePicker to the date of the Appointment.
        Calendar date = appointment.get_date();
        rc__date__dp.setValue(LocalDate.of(
                date.get(Calendar.YEAR), date.get(Calendar.MONTH) + 1, date.get(Calendar.DAY_OF_MONTH)
        ));

        // Style the DatePicker: disable the "Calendar Button" and other default styling.
        rc__date__dp.getEditor().setOnMouseClicked(event -> rc__date__dp.show());
        rc__date__dp.getStyleClass().clear();

        // Define a Listener to update the Date value of the Appointment if it is changed.
        rc__date__dp.getEditor().textProperty().addListener((observable, old_value, new_value) -> {
            LocalDate dp_val = rc__date__dp.getValue();
            appointment.set_date(new GregorianCalendar(
                    dp_val.getYear(), dp_val.getMonth().getValue() - 1, dp_val.getDayOfMonth())
            );
            changed_appt_attributes = true;
        });

        // Set this label's text to the name of the Client the Appointment corresponds to.
        rc__appt_client__lbl.setText(appointment.get_clients().get(0).get_identifier());

        // Define a Listener to update the Name value of the Appointment if it is changed.
        rc__appt_name__tf.textProperty().addListener((observable, oldValue, newValue) -> {
            appointment.set_name(newValue);
            changed_appt_attributes = true;
        });

    }

    /**
     * Initializes the first HBox of the root_container.
     */
    private void init_hb1() {

        // Define Listener for change in Appointment start-time-hours.
        rchb1__start_time_hrs__sn.getEditor().textProperty().addListener((observable, old_value, new_value) -> {
            appointment.set_start_time(new Time(
                    rchb1__start_time_hrs__sn.getValue(),
                    Integer.valueOf(rchb1__start_time_mins__sn.getValue()),
                    rchb1__start_time_period__sn.getValue())
            );
            changed_appt_attributes = true;
        });

        // Define Listener for change in Appointment start-time-minutes.
        rchb1__start_time_mins__sn.getEditor().textProperty().addListener((observable, old_value, new_value) -> {
            appointment.set_start_time(new Time(
                    rchb1__start_time_hrs__sn.getValue(),
                    Integer.valueOf(new_value),
                    rchb1__start_time_period__sn.getValue())
            );
            changed_appt_attributes = true;
        });

        // Define Listener for change in Appointment start-time-period.
        rchb1__start_time_period__sn.getEditor().textProperty().addListener((observable, old_value, new_value) -> {
            appointment.set_start_time(new Time(
                    rchb1__start_time_hrs__sn.getValue(),
                    Integer.valueOf(rchb1__start_time_mins__sn.getValue()),
                    rchb1__start_time_period__sn.getValue())
            );
            changed_appt_attributes = true;
        });

        // Define the children for HBox 1.
        rc__hb1.getChildren().addAll(
                rchb1__start_time_hrs__sn, rchb1__start_time_colon__lbl, rchb1__start_time_mins__sn,
                rchb1__start_time_period__sn
        );

    }

    /**
     * Initializes the second HBox of the root_container.
     */
    private void init_hb2() {

        // Define Listener for change in Appointment end-time-hours.
        rchb2__end_time_hrs__sn.getEditor().textProperty().addListener((observable, old_value, new_value) -> {
            appointment.set_start_time(new Time(
                    rchb2__end_time_hrs__sn.getValue(),
                    Integer.valueOf(rchb2__end_time_mins__sn.getValue()),
                    rchb2__end_time_period__sn.getValue())
            );
            changed_appt_attributes = true;
        });

        // Define Listener for change in Appointment end-time-minutes.
        rchb2__end_time_mins__sn.getEditor().textProperty().addListener((observable, old_value, new_value) -> {
            appointment.set_start_time(new Time(
                    rchb2__end_time_hrs__sn.getValue(),
                    Integer.valueOf(new_value),
                    rchb2__end_time_period__sn.getValue())
            );
            changed_appt_attributes = true;
        });

        // Define Listener for change in Appointment end-time-period.
        rchb2__end_time_period__sn.getEditor().textProperty().addListener((observable, old_value, new_value) -> {
            appointment.set_start_time(new Time(
                    rchb2__end_time_hrs__sn.getValue(),
                    Integer.valueOf(rchb2__end_time_mins__sn.getValue()),
                    rchb2__end_time_period__sn.getValue())
            );
            changed_appt_attributes = true;
        });

        // Define the children for HBox 2.
        rc__hb2.getChildren().addAll(
                rchb2__end_time_hrs__sn, rchb2__end_time_colon__lbl, rchb2__end_time_mins__sn,
                rchb2__end_time_period__sn
        );

    }


    /**
     * Defines all the JavaFX Spinners and performs necessary customizations on them.
     */
    private void init_spinners() {

        // Define the contents of the MINUTE Spinners.
        rchb1__start_time_mins__sn = new Spinner<>(FXCollections.observableArrayList(time_minute_strings));
        rchb2__end_time_mins__sn = new Spinner<>(FXCollections.observableArrayList(time_minute_strings));

        // Define the contents of the TIME-PERIOD Spinners.
        rchb1__start_time_period__sn = new Spinner<>(FXCollections.observableArrayList(Time.Period.AM, Time.Period.PM));
        rchb2__end_time_period__sn = new Spinner<>(FXCollections.observableArrayList(Time.Period.AM, Time.Period.PM));

        // Clear the default Style Classes.
        rchb1__start_time_hrs__sn.getStyleClass().clear();
        rchb1__start_time_mins__sn.getStyleClass().clear();
        rchb1__start_time_period__sn.getStyleClass().clear();
        rchb2__end_time_hrs__sn.getStyleClass().clear();
        rchb2__end_time_mins__sn.getStyleClass().clear();
        rchb2__end_time_period__sn.getStyleClass().clear();

    }


}
