//  Created by Alexander Swanson on 3/14/19.
//  Email: alexanderjswanson@icloud.com.
//  Copyright © 2019. All rights reserved.


package accounter;

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
        APPLICATION_SETTINGS, NEW_INDIVIDUAL_DIALOGUE
    }

    private Stage primaryStage, applicationSettingsStage, newIndividualDialogueStage, newCorporationDialogueStage;

    private FXMLController calendarController, applicationSettingsController, newIndividualDialogueController;

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
        loadClientData();
        loadFxmlControllers();
        loadAltStages();

        // Setup Stage
        primaryStage = new Stage();
        primaryStage.setScene(calendarController.getScene());
        primaryStage.show();

    }

    @Override
    public void stop() {
        System.out.println("Closing application.");
    }

    private void loadFxmlControllers() throws IOException {

        calendarController = loadFxmlController("fxml/CalendarController.fxml");
        applicationSettingsController = loadFxmlController("fxml/ApplicationSettingsController.fxml");
        newIndividualDialogueController = loadFxmlController("fxml/NewIndividualDialogueController.fxml");

    }

    private FXMLController loadFxmlController(String controllerFxmlFilePath) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(controllerFxmlFilePath));
        fxmlLoader.load();
        return fxmlLoader.getController();

    }

    private void loadAltStages() {

        // ApplicationSettings
        applicationSettingsStage = new Stage(StageStyle.UNIFIED);
        applicationSettingsStage.initModality(Modality.APPLICATION_MODAL);
        applicationSettingsStage.setOnCloseRequest(event -> closeApplicationSettings());
        applicationSettingsStage.setScene(applicationSettingsController.getScene());

        // NewIndividualDialogue
        newIndividualDialogueStage = new Stage(StageStyle.UNIFIED);
        newIndividualDialogueStage.initModality(Modality.WINDOW_MODAL);
        newIndividualDialogueStage.setOnCloseRequest(event -> closeNewIndividualDialogue());
        newIndividualDialogueStage.setScene(newIndividualDialogueController.getScene());

    }

    private void loadClientData() {

        clients = new ArrayList<>();

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

        }

    }

    private void displayApplicationSettings() {
        applicationSettingsStage.show();
        System.out.println("Opening ApplicationSettings Stage.");
    }

    private void closeApplicationSettings() {
        applicationSettingsStage.close();
        System.out.println("Closing ApplicationSettings Stage.");
    }

    private void displayNewIndividualDialogue() {
        newIndividualDialogueStage.show();
        System.out.println("Opening NewIndividualDialogue Stage.");
    }

    private void closeNewIndividualDialogue() {
        newIndividualDialogueStage.close();
        System.out.println("Closing NewIndividualDialogue Stage.");
    }

    public void createNewIndividual(String firstName, String lastName) {
        clients.add(new Individual(firstName, lastName));
    }

    public void createNewCorporation(String name) {
        clients.add(new Corporation(name));
    }

    /* Getters */
    @Contract(pure = true)
    public static App getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        launch(args);
    }

}
