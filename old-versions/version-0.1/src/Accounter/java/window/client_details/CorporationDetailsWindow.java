/*
Copyright (c) 2019, Alexander Joseph Swanson Villares
alexanderjswanson@icloud.com | https://github.com/ajswanny
*/


/* Package */
package Accounter.java.window.client_details;


/* Imports */
import Accounter.fxml.calendar.monthly.CalendarViewController;
import Accounter.java.client.Corporation;


/**
 *
 */
public class CorporationDetailsWindow extends ClientDetailsWindow {

    /* Constructors */
    /**
     * Initializes a CorporationDetailsWindow with the provided Corporation.
     *
     * @param client The Corporation whose DetailsWindow will be initialized.
     * @param calendar_view_controller The reference to the Calendar View controller.
     */
    public CorporationDetailsWindow (Corporation client, CalendarViewController calendar_view_controller) {

        // Initialize the parent class with provided Client:Corporation.
        super(client, calendar_view_controller);

        // Define the current Client.
        this.client = client;

        // Output status.
        System.out.println("Opened the CorporationDetailsWindow for client: " + client.get_identifier());

        // Define the Primary Scene stylesheet.
        this.primary_scene.getStylesheets().add(
                CorporationDetailsWindow.class.getResource(
                        "../../../css/client_details_window/corporation_window.css").toExternalForm()
        );

        // Start the UI.
        start(this.primary_stage);

    }

}
