/*
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
import Accounter.java.client.Individual;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCode;


/**
 * An extension of the Label class that implements user-editing of the Label's text directly from the UI.
 */
public abstract class EditableLabel extends Label {

    /* Class Fields */
    /**
     * The reference to the controller for the Calendar View.
     */
    protected CalendarViewController calendar_view_controller;

    /**
     * The Client that this EditableLabel can edit fields for.
     */
    protected Client client;

    /**
     * The ClientField that can be edited by this EditableLabel.
     */
    protected ClientField client_field;

    /**
     * A record of the text set the to Label.
     */
    protected String text_memento = "";


    /* Class UI Elements */
    /**
     * The field for editing of the Label's text.
     */
    protected TextInputControl field;


    /* Constructors */
    /**
     * The default constructor for EditableLabel.
     */
    public EditableLabel() {
    }

    /**
     * Initializes an EditableLabel with an initial String for the text.
     * @param lbl_text The String to use as the Label's text.
     */
    EditableLabel(
            String lbl_text,
            Client client,
            ClientField client_field,
            CalendarViewController calendar_view_controller)
    {

        // Call the parent constructor.
        super(lbl_text);

        // Define the Client.
        this.client = client;

        // Define the ClientField to edit.
        this.client_field = client_field;

        // Define the reference to the Accounter application.
        this.calendar_view_controller = calendar_view_controller;

    }


    /* Methods */
    /**
     * Performs editing of the text-editing field.
     */
    void customize_field() {

        // Define the action to allow for text editing: allow for text-editing if the user clicks on the label twice in
        // a rapid succession.
        this.setOnMouseClicked(event -> {

            if (event.getClickCount() == 2) {
                field.setText(text_memento = this.getText());
                this.setGraphic(field);
                this.setText("");
                field.requestFocus();
            }

        });

        // Define the listener for user-clicking outside the object.
        field.focusedProperty().addListener((prop, o, n) -> {
            if (!n) {
                to_label();
            }
        });

        // Set the action for accepting the Label's text.
        field.setOnKeyReleased(event -> {

            if (event.getCode().equals(KeyCode.ENTER)) {
                to_label();
            } else if (event.getCode().equals(KeyCode.ESCAPE)) {
                field.setText(text_memento);
                to_label();
            }

        });

    }

    /**
     * Forces the object-state to a Label.
     */
    private void to_label() {

        // The reference to the new text.
        String user_input = field.getText();

        // Set the graphic to 'null' and set the text of the Label.
        this.setGraphic(null);
        this.setText(user_input);

        // Update the Client field.
        client.set_field(client_field, user_input);
        switch (client_field) {

            case IDENTIFIER:
                calendar_view_controller.update_client_btn_txt(client, user_input);
                break;

            case FIRST_NAME:
                calendar_view_controller.update_client_btn_txt(
                        client, user_input + " " + ((Individual) client).get_last_name()
                );
                break;

            case LAST_NAME:
                calendar_view_controller.update_client_btn_txt(
                        client, ((Individual) client).get_first_name() + " " + user_input
                );
                break;

        }

    }

}