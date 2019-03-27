package accounter.java.models;

import accounter.App;
import accounter.java.client.Client;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import org.jetbrains.annotations.NotNull;

public class ClientInfoButton extends Button {

    /* Fields */
    private Client respectiveClient;

    /* Default Constructor */
    public ClientInfoButton(@NotNull Client client) {

        this.setText(client.toString());
        respectiveClient = client;

        MenuItem deleteClient = new MenuItem("Delete");
        MenuItem newAppointment = new MenuItem("New Appointment");

        deleteClient.setOnAction(event -> App.deleteClient(respectiveClient));
        newAppointment.setOnAction(event -> App.getInstance().requestDisplayForNewWindow(App.ApplicationWindow.NEW_APPOINTMENT_DIALOGUE));

        ContextMenu contextMenu = new ContextMenu(deleteClient, newAppointment);
        this.setContextMenu(contextMenu);

    }

    /* Getters */
    public Client getRespectiveClient() {
        return respectiveClient;
    }

}
