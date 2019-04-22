/*
Copyright (c) 2019, Alexander Joseph Swanson Villares
alexanderjswanson@icloud.com | https://github.com/ajswanny
*/


/* Package */
package Accounter.java.calendar_gridpane;


/* Imports */
import Accounter.fxml.calendar.monthly.CalendarViewController;
import Accounter.java.Accounter;
import Accounter.java.appointment.Appointment;
import Accounter.java.client.Client;
import Accounter.java.window.appointment_details.AppointmentDetailsWindow;
import javafx.geometry.Pos;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import org.jetbrains.annotations.NotNull;

import java.util.*;


/**
 *
 */
@SuppressWarnings("JavaDoc")
public class DayGridPane extends GridPane {


    /* Class Attributes */
    /**
     * The integer indicating the date_day of the date_month this GridPane describes.
     */
    private int date_day;

    /**
     * The date this GridPane describes.
     */
    private Calendar calendar;

    /**
     * A collection of the Clients defined for the 'Accounter' instance that will use this Class.
     * This ArrayList allows for the identification of Client Appointments that will be described by
     * this GridPane.
     */
    private ArrayList<Client> clients;

    /**
     * A collection of the Client Appointments.
     */
    private ArrayList<Appointment> client_appointments;

    /**
     * The reference to the Accounter application's controller for the monthly calendar.
     */
    private CalendarViewController calendar_view_controller;


    /* Class UI Elements */
    /**
     * The Label indicating the number of the day of the date_month.
     */
    private Label day_of_month_num__label;


    /* Constructors */
    /**
     * The default constructor; initializes a vanilla DayGridPane.
     */
    public DayGridPane() {

        // Call the parent constructor.
        super();

    }

    /**
     * Initializes a DayGridPane.
     * @param year
     * @param month
     * @param day
     * @param client_array_list
     */
    public DayGridPane(
            CalendarViewController calendar_view_controller,
            int year,
            int month,
            int day,
            ArrayList<Client> client_array_list
    ) {

        // Call the parent constructor.
        super();

        // Initialize the Accounter instance reference.
        this.calendar_view_controller = calendar_view_controller;

        // Define the class attributes.
        date_day = day;
        clients = client_array_list;

        // Initialize the Date this GridPane describes.
        calendar = new GregorianCalendar(year, month, day);

        // Initialize the collection of Client Appointments.
        client_appointments = new ArrayList<>();

        // Initialize the class data.
        def_data();

        // Initialize the UI dependencies.
        def_ui();

    }


    /* Methods */
    /**
     * FOR DEVELOPMENT.
     */
    private void $DEV() {

    }

    /**
     * Defines the data to be used for functionality and UI of this object.
     */
    private void def_data() {

        // Define the collection of Client Appointments.
        for (Client client : clients) {
            client_appointments.addAll(client.get_appointments(calendar));
        }

    }

    /**
     * Defines the UI for this object.
     */
    private void def_ui() {

        // Initialize the Label for the number of the day.
        this.day_of_month_num__label = new Label(String.valueOf(date_day));

        // Define the indicator of the DAY_OF_MONTH.
        this.add(day_of_month_num__label, 0, 0);
        day_of_month_num__label.setAlignment(Pos.TOP_LEFT);

        //
        init_appointment_labels();

        // Define the preferred dimensions.
//        this.setPrefWidth(PREF_WIDTH);
//        this.setPrefHeight(PREF_HEIGHT);

    }

    /**
     * Initializes the info-Labels for every Client Appointment.
     */
    private void init_appointment_labels() {

        // Sort the list of Appointments.
        client_appointments.sort(Comparator.comparing(Appointment::get_start_time));

        // Define a loop counter for dynamic JavaFX Node placement.
        int loop_counter = 1;

        // Define the Appointment Label for every Appointment.
        for (Appointment appointment : client_appointments) {

            // Make the node_row_index i > 1 (i = 0 is the index for the number of the Day).
            def_appointment_label(appointment, loop_counter);

            // Increment the loop counter.
            loop_counter++;

        }

    }

    /**
     * Defines an 'Appointment' label to display the essential info of an Appointment and adds it to this DayGridPane.
     *
     * @param appointment
     * @param node_row_index
     */
    private void def_appointment_label(@NotNull Appointment appointment, int node_row_index) {

        // Define the Appointment 'name' label.
        Label appt_name_lbl = new Label(appointment.get_name());
        appt_name_lbl.setMaxWidth(80.0);

        // Define the Appointment 'start_time' Label.
        Label start_time_lbl = new Label(appointment.get_start_time().toString());

        // Define a Region to appropriately space the Labels.
        Region spacing = new Region();
        HBox.setHgrow(spacing, Priority.ALWAYS);

        // Define the container for the Labels.
        HBox container = new HBox(appt_name_lbl, spacing, start_time_lbl);

        // Define the action for opening the DetailsWindow for this Appointment.
        container.setOnMousePressed(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                new AppointmentDetailsWindow(calendar_view_controller, appointment, true);
            }
        });

        // Add the HBoxes to the main container.
        this.addRow(node_row_index, container);


        // Define the ContextMenu to allow for deletion of the Appointment.
        MenuItem delete_appt = new MenuItem("Delete");
        delete_appt.setOnAction(event -> {
            appointment.get_clients().get(0).get_appointments().remove(appointment);
            calendar_view_controller.update_root_center();

        });
        ContextMenu context_menu = new ContextMenu(delete_appt);

        // Define access to the ContextMenu.
        container.setOnContextMenuRequested(event ->
                context_menu.show(container.getScene().getWindow(), event.getScreenX(), event.getScreenY())
        );

    }


    /* Getters */
    public int get_date_day() {

        return date_day;

    }
}
