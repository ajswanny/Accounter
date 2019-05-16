/*
 * Created by Alexander Swanson on 4/15/19 10:18 PM.
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
import javafx.scene.control.ScrollPane;
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
    public VBox clientButtonsContainer;

    @FXML
    public ScrollPane buttonsScrollpane;

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

        buttonsScrollpane.getStylesheets().clear();

        // Define the Menu
        accounterSettings.setOnAction(event -> instance.requestDisplayForWindow(App.ApplicationWindow.APPLICATION_SETTINGS));
        accounterQuit.setOnAction(event -> instance.requestApplicationClose());
        newIndividual.setOnAction(event -> instance.requestDisplayForWindow(App.ApplicationWindow.NEW_INDIVIDUAL_DIALOGUE));
        newCorporation.setOnAction(event -> instance.requestDisplayForWindow(App.ApplicationWindow.NEW_CORPORATION_DIALOGUE));

        // Init ClientInfoButtons
        clientButtons = new ArrayList<>();
        for (Client client : instance.getClients()) {
            ClientInfoButton button = new ClientInfoButton(client);
            button.setMaxWidth(Double.MAX_VALUE);
            clientButtons.add(button);
        }
        clientButtonsContainer.getChildren().addAll(clientButtons);

        // Init month navigation buttons
        previousMonth.setOnAction(event -> instance.requestNewMonthToGrid(0));
        nextMonth.setOnAction(event -> instance.requestNewMonthToGrid(1));

        // Month and Year text
        currentYear.setText(String.valueOf(instance.variableDate.getYear()));
        String monthName = instance.variableDate.getMonth().name();
        monthName = monthName.substring(0, 1).toUpperCase() + monthName.substring(1).toLowerCase();
        currentMonth.setText(monthName);

    }

    /** Creates a new ClientInfoButton for the GUI */
    public void createNewClientInfoButton(Client client) {

        ClientInfoButton button = new ClientInfoButton(client);
        button.setMaxWidth(Double.MAX_VALUE);
        button.prefWidthProperty().bind(clientButtonsContainer.prefWidthProperty());
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
    }

    public void updateMonthLabelText(String newMonthName) {
        String monthName = newMonthName;
        monthName = monthName.substring(0, 1).toUpperCase() + monthName.substring(1).toLowerCase();
        currentMonth.setText(monthName);
    }

    /* Getters */
    public AnchorPane getCalendarGridContainer() {
        return calendarGridContainer;
    }
}
