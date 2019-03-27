//  Created by Alexander Swanson on 3/14/19.
//  Email: alexanderjswanson@icloud.com.
//  Copyright © 2019. All rights reserved.


package accounter;

import accounter.controller.CalendarController;
import accounter.controller.FXMLController;
import accounter.java.client.Client;
import accounter.java.client.Corporation;
import accounter.java.client.Individual;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.jetbrains.annotations.Contract;

import java.io.IOException;
import java.util.ArrayList;

public class App extends Application {

    /* Fields */
    public enum ApplicationWindow {
        APPLICATION_SETTINGS, NEW_INDIVIDUAL_DIALOGUE, NEW_CORPORATION_DIALOGUE
    }

    private Stage primaryStage, applicationSettingsStage, newIndividualDialogueStage, newCorporationDialogueStage;

    private FXMLController
            calendarController,
            applicationSettingsController,
            newIndividualDialogueController,
            newCorporationDialogueController;

    private ArrayList<Client> clients;

    private static App instance;

    /**
     * Default constructor.
     */
    public App() {
        instance = this;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        // Load resources
        initClientData();
        initFxmlControllers();
        initAltStages();

        // Setup Stage
        primaryStage = new Stage();
        primaryStage.setScene(calendarController.getScene());
        primaryStage.show();

    }

    @Override
    public void stop() {
        System.out.println("Closing application.");
    }

    private void initFxmlControllers() throws IOException {

        calendarController = loadFxmlController("fxml/CalendarController.fxml");
        applicationSettingsController = loadFxmlController("fxml/ApplicationSettingsController.fxml");
        newIndividualDialogueController = loadFxmlController("fxml/NewIndividualDialogueController.fxml");
        newCorporationDialogueController = loadFxmlController("fxml/NewCorporationDialogueController.fxml");

    }

    private FXMLController loadFxmlController(String controllerFxmlFilePath) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(controllerFxmlFilePath));
        fxmlLoader.load();
        return fxmlLoader.getController();

    }

    private void initAltStages() {

        // ApplicationSettings
        applicationSettingsStage = new Stage(StageStyle.UNIFIED);
        applicationSettingsStage.initModality(Modality.APPLICATION_MODAL);
        applicationSettingsStage.setOnCloseRequest(event -> closeApplicationSettings());
        applicationSettingsStage.setScene(applicationSettingsController.getScene());
        applicationSettingsStage.setResizable(false);

        // NewIndividualDialogue
        newIndividualDialogueStage = new Stage(StageStyle.UNIFIED);
        newIndividualDialogueStage.initModality(Modality.APPLICATION_MODAL);
        newIndividualDialogueStage.setOnCloseRequest(event -> closeNewIndividualDialogue());
        newIndividualDialogueStage.setScene(newIndividualDialogueController.getScene());
        newIndividualDialogueStage.setResizable(false);

        // NewCorporationDialogue
        newCorporationDialogueStage = new Stage(StageStyle.UNIFIED);
        newCorporationDialogueStage.initModality(Modality.APPLICATION_MODAL);
        newCorporationDialogueStage.setOnCloseRequest(event -> closeNewCorporationDialogue());
        newCorporationDialogueStage.setScene(newCorporationDialogueController.getScene());
        newCorporationDialogueStage.setResizable(false);

    }

    private void initClientData() {

        clients = new ArrayList<>();
        clients.add(new Individual("apple", "martin"));


    }

    public void requestApplicationClose() {
        Platform.exit();
    }

    public void requestDisplayForNewWindow(ApplicationWindow applicationWindow) {

        switch (applicationWindow) {

            case APPLICATION_SETTINGS:
                displayApplicationSettings();
                break;
            case NEW_INDIVIDUAL_DIALOGUE:
                displayNewIndividualDialogue();
                break;
            case NEW_CORPORATION_DIALOGUE:
                displayNewCorporationDialogue();
                break;
        }

    }

    /* ApplicationSettings open & close */
    private void displayApplicationSettings() {
        applicationSettingsStage.show();
        System.out.println("Opening ApplicationSettings Stage.");
    }

    private void closeApplicationSettings() {
        applicationSettingsStage.close();
        System.out.println("Closing ApplicationSettings Stage.");
    }

    /* NewIndividualDialogue open & close */
    private void displayNewIndividualDialogue() {
        newIndividualDialogueStage.show();
        System.out.println("Opening NewIndividualDialogue Stage.");
    }

    private void closeNewIndividualDialogue() {
        newIndividualDialogueStage.close();
        System.out.println("Closing NewIndividualDialogue Stage.");
    }

    /* NewCorporationDialogue open & close */
    private void displayNewCorporationDialogue() {
        newCorporationDialogueStage.show();
        System.out.println("Opening NewCorporationDialogue Stage.");
    }

    private void closeNewCorporationDialogue() {
        newCorporationDialogueStage.close();
        System.out.println("Closing NewCorporationDialogue Stage.");
    }

    /* Client Creation */
    public void createNewIndividual(String firstName, String lastName) {
        Individual c = new Individual(firstName, lastName);
        clients.add(c);
        CalendarController.createNewClientButton(c);
        closeNewIndividualDialogue();
    }

    public void createNewCorporation(String name) {
        clients.add(new Corporation(name));
        closeNewCorporationDialogue();
    }

    public static void deleteClient(Client client) {

        instance.clients.remove(client);

        CalendarController.removeClientInfoButton(client);

    }

    /* Getters */
    @Contract(pure = true)
    public static App getInstance() {
        return instance;
    }

    public ArrayList<Client> getClients() {
        return clients;
    }

    public static void main(String[] args) {
        launch(args);
    }

}
