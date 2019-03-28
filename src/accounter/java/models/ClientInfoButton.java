package accounter.java.models;

import accounter.App;
import accounter.controller.NewAppointmentDialogueController;
import accounter.controller.client_info.IndividualInfoController;
import accounter.java.client.Client;
import accounter.java.client.Individual;
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
        NewAppointmentDialogueController.setRespectiveClient(respectiveClient);

        ContextMenu contextMenu = new ContextMenu(deleteClient, newAppointment);
        this.setContextMenu(contextMenu);

        this.setOnAction(event -> {
            if (client instanceof Individual) {
                IndividualInfoController.setRespectiveClientData((Individual) client);

                App.getInstance().requestDisplayForNewWindow(App.ApplicationWindow.INDIVIDUAL_INFO);
            }
        });

    }

    /* Getters */
    public Client getRespectiveClient() {
        return respectiveClient;
    }

}
