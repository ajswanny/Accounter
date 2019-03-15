/*
Copyright (c) 2019, Alexander Joseph Swanson Villares
alexanderjswanson@icloud.com | https://github.com/ajswanny
*/


/* Package */
package Accounter.java.window.client_details;


/* Imports */
import Accounter.fxml.calendar.monthly.CalendarViewController;
import Accounter.java.client.ClientField;
import Accounter.java.customs.label.TAEditableLabel;
import Accounter.java.customs.label.TFEditableLabel;
import Accounter.java.window.AccounterWindow;
import Accounter.java.appointment.Appointment;
import Accounter.java.client.Client;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;
import java.util.Locale;


@SuppressWarnings("Duplicates")
public class ClientDetailsWindow extends AccounterWindow {

    /* Class Fields */
    /**
     * The reference to the Calendar View controller.
     */
    CalendarViewController calendar_view_controller;

    /**
     * The reference to the Client whose details are displayed by this Window.
     */
    protected Client client;


    /* Class UI Elements */
    /**
     * The root container CENTER root container.
     */
    private VBox root_center__root = new VBox();

    /**
     * The root container CENTER top GridPane.
     */
    GridPane rcc1__gp = new GridPane();

    /**
     * The root container CENTER middle VBox.
     */
    private VBox root_center_2__vb = new VBox();

    /**
     *
     */
    private ScrollPane root_center_2__vb__sp = new ScrollPane();

    /**
     *
     */
    private GridPane rcc2__vb__gp = new GridPane();

    /**
     * The root container CENTER bottom GridPane.
     */
    private GridPane rcc3__gp = new GridPane();

    /**
     *
     */
    private Button rcc3__edit__btn = new Button("Edit");

    /**
     *
     */
    private Label rcc1__identifier__lbl = new Label("Identifier: ");


    /* Constructors */
    /**
     * Initializes a new ClientDetailsWindow for the provided Client.
     *
     * @param client The Client to init a DetailsWindow for.
     * @param calendar_view_controller The reference to the Accounter application.
     */
    public ClientDetailsWindow(Client client, CalendarViewController calendar_view_controller) {

        // Define the reference to the Client.
        this.client = client;

        // Define the reference to the Accounter application.
        this.calendar_view_controller = calendar_view_controller;

    }


    /* Methods */
    /**
     * Starts the UI for this Window.
     */
    public void start_ui() {

        // Start the UI.
        start(primary_stage);

    }

    /**
     *
     * @param primary_stage
     */
    @Override
    public void start(Stage primary_stage) {

        // Define the root container.
        this.root_container = new BorderPane();


        // Define the primary Stage.
        this.primary_stage = new Stage();

        // Define the primary Stage resizability.
        primary_stage.setResizable(false);

        // Define the Stage dependencies.
        init_primary_stage();


        // Define the primary Scene.
        this.primary_scene = new Scene(root_container, 400, 600);


        // Set the Scene.
        primary_stage.setScene(primary_scene);

        // Define the title of the window.
        primary_stage.setTitle("About Client");

        // Set Stage modality.
        primary_stage.initModality(Modality.APPLICATION_MODAL);

        // Show the Stage.
        primary_stage.show();

    }

    /**
     *
     */
    private void init_primary_stage() {

        // Define the root container.
        init_root_container();

    }


    /**
     * Note:
     *      - 'init_root_center()' must be called after the addition of sub-containers to the CENTER
     *      root due to allow for safe method overrides.
     */
    private void init_root_container() {
        // TODO: Investigate if :important:1 is true.

        // Define the root_center's secondary containers.
        root_center__root.setPadding(new Insets(25));
        root_center__root.getChildren().addAll(
                rcc1__gp,
                root_center_2__vb,
                rcc3__gp
        );


        // Define the root CENTER.
        init_root_center();

        // Set the root container CENTER.
        root_container.setCenter(root_center__root);

    }

    /**
     *
     */
    private void init_root_center() {

        /* Define the root center container 1 */
        // Define the GridPane alignment.
        rcc1__gp.setAlignment(Pos.TOP_LEFT);

        // Define the GridPane padding.
        rcc1__gp.setPadding(new Insets(0, 0, 15, 0));

        // Define the vertical and horizontal gaps among the GridPane children.
        rcc1__gp.setVgap(20);
        rcc1__gp.setHgap(15);

        // $DEVELOPMENT
        rcc1__gp.setGridLinesVisible(true);

        // Initialize the GridPane dependencies.
        init_root_center_1();


        /* Define the root container center: 2 */
        // Define the GridPane alignment.
        root_center_2__vb.setAlignment(Pos.TOP_LEFT);

        // Define the GridPane padding.
        root_center_2__vb.setPadding(new Insets(0, 0, 15, 0));

        // Initialize the GridPane dependencies.
        init_root_center_2();


        /* Define the root container center: 3 */
        // Define the GridPane alignment.
        rcc3__gp.setAlignment(Pos.TOP_LEFT);

        // Define the GridPane padding.
        rcc3__gp.setPadding(new Insets(0, 0, 0, 0));

        // $DEVELOPMENT
        rcc3__gp.setGridLinesVisible(true);

        // Initialize the GridPane dependencies.
        init_root_center_3();

    }

    /**
     *
     */
    protected void init_root_center_1() {

        // Define the Labels for the Client field titles.
        Label identifier_label = new Label("Identifier: ");
        Label age_label = new Label("Age: ");

        // Add the labels to the root_center GridPane.
        rcc1__gp.add(identifier_label, 0, 0);
        rcc1__gp.add(age_label, 2, 0);


        // Define the Labels for the Client fields.
        Label identifier =
                new TFEditableLabel(client.get_identifier(), client, ClientField.IDENTIFIER, calendar_view_controller);
        Label age = new TFEditableLabel(Integer.toString(client.get_age()), client, ClientField.AGE, calendar_view_controller);

        // Add the labels to the root_center GridPane.
        rcc1__gp.add(identifier, 1, 0);
        rcc1__gp.add(age, 3, 0);

    }

    /**
     *
     */
    private void init_root_center_2() {

        // Define the Labels for the Client field titles.
        Label appointments_label = new Label("Appointments: ");

        // Configure the GridPane.
        rcc2__vb__gp.setHgap(15);
        rcc2__vb__gp.getStyleClass().add("rcc2__vb__gp");

        /* Define the list of Appointments for the Client. */
        // Define a counter to appropriately locate the Client Appointments.
        int loop_counter = 0;

        // Generate a listing for each Client Appointment.
        for (Appointment appointment : client.get_appointments()) {

            // Define the Appointment 'name' label.
            rcc2__vb__gp.add(new Label(appointment.get_name()), 0, loop_counter);


            // Define the Appointment 'date' label.
            Label date_label = new Label(
                    new SimpleDateFormat("EEEEE MMMMM dd", Locale.ENGLISH).format(appointment.get_date().getTime())
            );
            rcc2__vb__gp.add(date_label, 1, loop_counter);


            // Declare the Appointment 'time' labels to be conditionally defined.
            Label start_time_label;
            Label end_time_label;
            Label time_label;

            // Test for the definition of the Appointment 'time' labels.
            switch (appointment.get_defined_times()) {

                case ALL:

                    // Create the Label for the start- and end-times.
                    time_label = new Label(
                            appointment.get_start_time().toString() +
                                 "  - " +
                                 appointment.get_end_time().toString());

                    // Add the Label to the GridPane.
                    rcc2__vb__gp.add(time_label, 2, loop_counter);

                    break;

                case START_TIME:

                    // Define the Appointment 'start_time' Label.
                    start_time_label = new Label(appointment.get_start_time().toString());

                    // Add the Label to the GridPane.
                    rcc2__vb__gp.add(start_time_label,2, loop_counter);

                    break;

                case END_TIME:

                    // Define the Appointment 'end_time' Label.
                    end_time_label = new Label(appointment.get_end_time().toString());

                    // Add the Label to the GridPane.
                    rcc2__vb__gp.add(end_time_label, 2, loop_counter);

                    break;

            }

            // Increment the loop counter.
            loop_counter++;

            // $DEVELOPMENT
            rcc2__vb__gp.setGridLinesVisible(true);

        }


        // Define the Labels' CSS hooks.
        for (Node node: rcc2__vb__gp.getChildren()) {
            node.getStyleClass().add("root_center_2__gp__time_labels");
        }


        // Define the ScrollPane parameters to contain the Client's Appointments.
        root_center_2__vb__sp.setContent(rcc2__vb__gp);
        root_center_2__vb__sp.setPrefViewportHeight(50);


        // Add the UI elements to the main container of Node 2 of the root center.
        root_center_2__vb.getChildren().addAll(
                appointments_label,
                root_center_2__vb__sp
        );

    }

    /**
     *
     */
    private void init_root_center_3() {

        // Define the Labels for the Client field titles.
        Label notes_label = new Label("Notes: ");

        // Add the labels to the root_center GridPane.
        rcc3__gp.add(notes_label, 0, 0);

        // Define the Labels for the Client fields.
        TAEditableLabel notes_field = new TAEditableLabel(client.get_notes(), client, ClientField.NOTES, calendar_view_controller);
        notes_field.setPrefHeight(150);
        notes_field.setPrefWidth(150);

        // Add the labels to the root_center GridPane.
        rcc3__gp.add(notes_field, 1, 1);

    }

}
