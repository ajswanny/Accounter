/*
 * Created by Alexander Swanson on 3/28/19 7:10 PM.
 * Email: alexanderjswanson@icloud.com.
 * Copyright © 2019. All rights reserved.
 */

package accounter.controller;

import accounter.App;
import accounter.java.client.Client;
import accounter.java.models.ClientInfoButton;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CalendarController extends FXMLController {

    private ArrayList<ClientInfoButton> clientButtons;

    private static CalendarController calendarControllerInstance;

    @FXML
    private MenuItem accounterSettings;

    @FXML
    private MenuItem accounterQuit;

    @FXML
    private MenuItem newIndividual;

    @FXML
    private MenuItem newCorporation;

    @FXML
    private VBox clientButtonsContainer;

    public CalendarController() {
        System.out.println("Initialized instance of CalendarController.");
        calendarControllerInstance = this;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        super.initialize(location, resources);

        defineMenuItemActions();

        initClientInfoButtons();

    }

    private void defineMenuItemActions() {

        accounterSettings.setOnAction(event -> instance.requestDisplayForNewWindow(App.ApplicationWindow.APPLICATION_SETTINGS));
        accounterQuit.setOnAction(event -> instance.requestApplicationClose());
        newIndividual.setOnAction(event -> instance.requestDisplayForNewWindow(App.ApplicationWindow.NEW_INDIVIDUAL_DIALOGUE));
        newCorporation.setOnAction(event -> instance.requestDisplayForNewWindow(App.ApplicationWindow.NEW_CORPORATION_DIALOGUE));

    }

    /** Initializes all ClientInfoButtons and their necessary resources */
    private void initClientInfoButtons() {

        clientButtons = new ArrayList<>();

        for (Client client : instance.getClients()) {
            clientButtons.add(new ClientInfoButton(client));
        }
        clientButtonsContainer.getChildren().addAll(clientButtons);

    }

    /** Creates a new ClientInfoButton for the GUI */
    public void createNewClientButton(Client client) {

        ClientInfoButton button = new ClientInfoButton(client);
        clientButtons.add(button);
        clientButtonsContainer.getChildren().add(button);

    }

    /** Removes a specified ClientInfoButton from the GUI */
    public void removeClientInfoButton(Client respectiveClient) {

        ClientInfoButton button = clientButtons.stream().filter(clientInfoButton ->
                respectiveClient.equals(clientInfoButton.getRespectiveClient())).findFirst().orElse(null);
        clientButtons.remove(button);
        clientButtonsContainer.getChildren().remove(button);

    }

}
