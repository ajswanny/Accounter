/*
 * Created by Alexander Swanson on 4/9/19 11:40 AM.
 * Email: alexanderjswanson@icloud.com.
 * Copyright © 2019. All rights reserved.
 */

package accounter.controller;

import accounter.App;
import accounter.java.client.Client;
import accounter.java.models.ClientInfoButton;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CalendarController extends FXMLController {

    private ArrayList<ClientInfoButton> clientButtons;

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

    @FXML
    private Button previousMonth;

    @FXML
    private Button nextMonth;

    @FXML
    private Label currentMonth;

    @FXML
    private Label currentYear;

    @FXML
    private AnchorPane calendarGridContainer;

    public CalendarController() {
        System.out.println("Initialized instance of CalendarController.");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        super.initialize(location, resources);


        // Define the Menu
        accounterSettings.setOnAction(event -> instance.requestDisplayForWindow(App.ApplicationWindow.APPLICATION_SETTINGS));
        accounterQuit.setOnAction(event -> instance.requestApplicationClose());
        newIndividual.setOnAction(event -> instance.requestDisplayForWindow(App.ApplicationWindow.NEW_INDIVIDUAL_DIALOGUE));
        newCorporation.setOnAction(event -> instance.requestDisplayForWindow(App.ApplicationWindow.NEW_CORPORATION_DIALOGUE));

        // Init ClientInfoButtons
        clientButtons = new ArrayList<>();
        for (Client client : instance.getClients()) {
            clientButtons.add(new ClientInfoButton(client));
        }
        clientButtonsContainer.getChildren().addAll(clientButtons);

        // Init month navigation buttons
        previousMonth.setOnAction(event -> instance.requestNewMonthToGrid(0));
        nextMonth.setOnAction(event -> instance.requestNewMonthToGrid(1));

        // Month and Year text
        currentYear.setText(String.valueOf(instance.variableDate.getYear()));
        currentMonth.setText(instance.variableDate.getMonth().toString());

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

    /**
     * Sets the current Calendar-Grid content.
     * Currently not working properly due to a possible JavaFX bug; setting the content by overriding the children of a
     * default GridPane.
     */
    public void setCalendarGridContent(GridPane calendarGrid) {


        AnchorPane.setTopAnchor(calendarGrid, 0d);
        AnchorPane.setLeftAnchor(calendarGrid, 0d);
        AnchorPane.setBottomAnchor(calendarGrid, 0d);
        AnchorPane.setRightAnchor(calendarGrid, 0d);

        calendarGridContainer.getChildren().setAll(calendarGrid);

        // TODO: Implement cleaning of UI.
    }

    public void updateMonthLabelText(String newMonthName) {
        currentMonth.setText(newMonthName);
    }

    /* Getters */
    public AnchorPane getCalendarGridContainer() {
        return calendarGridContainer;
    }
}
