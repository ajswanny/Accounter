/*
Copyright (c) 2019, Alexander Joseph Swanson Villares
alexanderjswanson@icloud.com | https://github.com/ajswanny

References:
    https://stackoverflow.com/users/8076227/autumn-wind
*/


/* Package */
package Accounter.java.customs.label;


/* Imports */
import Accounter.fxml.calendar.monthly.CalendarViewController;
import Accounter.java.client.Client;
import Accounter.java.client.ClientField;
import javafx.scene.control.TextField;


/**
 * An extension of the Label class that implements user-editing of the Label's text directly from the UI.
 * Extends EditableLabel to use a TextField as the field for text-editing.
 */
public class TFEditableLabel extends EditableLabel {

    /* Constructors */
    public TFEditableLabel(
            String lbl_text,
            Client client,
            ClientField client_field,
            CalendarViewController calendar_view_controller
    ) {

        // Call the parent constructor.
        super(lbl_text, client, client_field, calendar_view_controller);

        // Define the new type of the text-editing field.
        field = new TextField();

        // Perform customizations for the TextField.
        customize_field();

    }

}