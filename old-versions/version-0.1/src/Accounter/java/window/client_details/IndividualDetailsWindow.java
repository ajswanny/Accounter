/*
Copyright (c) 2019, Alexander Joseph Swanson Villares
alexanderjswanson@icloud.com | https://github.com/ajswanny
*/


/* Package */
package Accounter.java.window.client_details;


/* Imports */
import Accounter.fxml.calendar.monthly.CalendarViewController;
import Accounter.java.client.ClientField;
import Accounter.java.client.Individual;
import Accounter.java.customs.label.TFEditableLabel;
import javafx.scene.control.Label;


/**
 *
 */
public class IndividualDetailsWindow extends ClientDetailsWindow {

    /* Class Fields */
    /**
     * The reference to the Client for this DetailsWindow.
     */
    private Individual client;


    /* Constructors */
    /**
     * Initializes an IndividualDetailsWindow for the provided Individual.
     *
     * @param client The Individual to whose DetailsWindow will be initialized.
     * @param calendar_view_controller The reference to the Calendar View controller.
     */
    public IndividualDetailsWindow(Individual client, CalendarViewController calendar_view_controller) {

        // Initialize parent class with provided Client:Individual.
        super(client, calendar_view_controller);

        // Define the current Client.
        this.client = client;

        // Output status.
        System.out.println("Opened the IndividualDetailsWindow for client: " + client.get_identifier());

        // Define the Primary Scene stylesheet.
        primary_scene.getStylesheets().add(
                IndividualDetailsWindow.class.getResource("../../../css/client_details_window/individual_window.css").toExternalForm()
        );

        // Start the UI.
        start(this.primary_stage);

    }


    /* Methods */
    /**
     * This method overrides its parent to allow for the display of a Client:Individual's first and last names.
     */
    @Override
    protected void init_root_center_1() {

        // Define the Labels for the Client field titles.
        Label first_name_label = new Label("First name: ");
        Label last_name_label = new Label("Last name: ");
        Label age_label = new Label("Age: ");

        // Add the labels to the root_center GridPane.
        rcc1__gp.add(first_name_label, 0, 0);
        rcc1__gp.add(last_name_label, 2, 0);
        rcc1__gp.add(age_label, 4, 0);


        // Define the Labels for the Client fields.
        Label first_name = new TFEditableLabel(client.get_first_name(), client, ClientField.FIRST_NAME, calendar_view_controller);
        Label last_name = new TFEditableLabel(client.get_last_name(), client, ClientField.LAST_NAME, calendar_view_controller);
        Label age = new TFEditableLabel(Integer.toString(client.get_age()), client, ClientField.AGE, calendar_view_controller);

        // Add the labels to the root_center GridPane.
        rcc1__gp.add(first_name, 1, 0);
        rcc1__gp.add(last_name, 3, 0);
        rcc1__gp.add(age, 5, 0);

    }

}
