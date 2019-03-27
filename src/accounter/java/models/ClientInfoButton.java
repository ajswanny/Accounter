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
        deleteClient.setOnAction(event -> App.deleteClient(respectiveClient));

        ContextMenu contextMenu = new ContextMenu(deleteClient);
        this.setContextMenu(contextMenu);

    }

    /* Getters */
    public Client getRespectiveClient() {
        return respectiveClient;
    }

}
