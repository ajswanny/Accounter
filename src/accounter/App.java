//  Created by Alexander Swanson on 3/14/19.
//  Email: alexanderjswanson@icloud.com.
//  Copyright © 2019. All rights reserved.


package accounter;

import accounter.controller.CalendarController;
import accounter.controller.FXMLController;
import accounter.java.Appointment;
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
        APPLICATION_SETTINGS, NEW_INDIVIDUAL_DIALOGUE, NEW_CORPORATION_DIALOGUE, NEW_APPOINTMENT_DIALOGUE,
        INDIVIDUAL_INFO
    }

    private Stage applicationSettingsStage;
    private Stage newIndividualDialogueStage;
    private Stage newCorporationDialogueStage;
    private Stage newAppointmentDialogueStage;
    private Stage individualInfoStage;

    private FXMLController
        calendarController,
        applicationSettingsController,
        newIndividualDialogueController,
        newCorporationDialogueController,
        newAppointmentDialogueController,
        individualInfoController,
        corporationInfoController
    ;


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
        newAppointmentDialogueController = loadFxmlController("fxml/NewAppointmentDialogueController.fxml");
        individualInfoController = loadFxmlController("fxml/client_info/IndividualInfoController.fxml");

    }

    private FXMLController loadFxmlController(String controllerFxmlFilePath) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(controllerFxmlFilePath));
        fxmlLoader.load();
        return fxmlLoader.getController();

    }

    @SuppressWarnings("Duplicates")
    private void initAltStages() {

        // ApplicationSettings
        applicationSettingsStage = new Stage(StageStyle.UNIFIED);
        applicationSettingsStage.initModality(Modality.APPLICATION_MODAL);
        applicationSettingsStage.setResizable(false);
        applicationSettingsStage.setOnCloseRequest(event -> closeApplicationSettings());
        applicationSettingsStage.setScene(applicationSettingsController.getScene());

        // NewIndividualDialogue
        newIndividualDialogueStage = new Stage(StageStyle.UNIFIED);
        newIndividualDialogueStage.initModality(Modality.APPLICATION_MODAL);
        newIndividualDialogueStage.setResizable(false);
        newIndividualDialogueStage.setOnCloseRequest(event -> closeNewIndividualDialogue());
        newIndividualDialogueStage.setScene(newIndividualDialogueController.getScene());

        // NewCorporationDialogue
        newCorporationDialogueStage = new Stage(StageStyle.UNIFIED);
        newCorporationDialogueStage.initModality(Modality.APPLICATION_MODAL);
        newCorporationDialogueStage.setResizable(false);
        newCorporationDialogueStage.setOnCloseRequest(event -> closeNewCorporationDialogue());
        newCorporationDialogueStage.setScene(newCorporationDialogueController.getScene());

        // NewAppointmentDialogue
        newAppointmentDialogueStage = new Stage(StageStyle.UNIFIED);
        newAppointmentDialogueStage.initModality(Modality.APPLICATION_MODAL);
        newAppointmentDialogueStage.setResizable(false);
        newAppointmentDialogueStage.setOnCloseRequest(event -> closeNewAppointmentDialogue());
        newAppointmentDialogueStage.setScene(newAppointmentDialogueController.getScene());

        // IndividualInfo
        individualInfoStage = new Stage(StageStyle.UNIFIED);
        individualInfoStage.initModality(Modality.APPLICATION_MODAL);
        individualInfoStage.setResizable(false);
        individualInfoStage.setOnCloseRequest(event -> closeIndividualInfo());
        individualInfoStage.setScene(individualInfoController.getScene());

    }

    private void initClientData() {

        clients = new ArrayList<>();
        clients.add(new Individual("apple", "martin"));


    }


    /* GUI Alternation */
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
            case NEW_APPOINTMENT_DIALOGUE:
                displayNewAppointmentDialogue();
                break;
            case INDIVIDUAL_INFO:
                displayIndividualInfo();
                break;

        }

    }

    /* ApplicationSettings open & close */
    private void displayApplicationSettings() {
        applicationSettingsStage.show();
        System.out.println("Showed ApplicationSettings Stage.");
    }

    private void closeApplicationSettings() {
        applicationSettingsStage.close();
        System.out.println("Closed ApplicationSettings Stage.");
    }

    /* NewIndividualDialogue open & close */
    private void displayNewIndividualDialogue() {
        newIndividualDialogueStage.show();
        System.out.println("Showed NewIndividualDialogue Stage.");
    }

    private void closeNewIndividualDialogue() {
        newIndividualDialogueStage.close();
        System.out.println("Closed NewIndividualDialogue Stage.");
    }

    /* NewCorporationDialogue open & close */
    private void displayNewCorporationDialogue() {
        newCorporationDialogueStage.show();
        System.out.println("Showed NewCorporationDialogue Stage.");
    }

    private void closeNewCorporationDialogue() {
        newCorporationDialogueStage.close();
        System.out.println("Closed NewCorporationDialogue Stage.");
    }

    /* NewAppointmentDialogue open & close */
    private void displayNewAppointmentDialogue() {
        newAppointmentDialogueStage.show();
        System.out.println("Showed NewAppointmentDialogue Stage.");
    }

    private void closeNewAppointmentDialogue() {
        newAppointmentDialogueStage.close();
        System.out.println("Closed NewAppointmentDialogue Stage.");
    }

    /* IndividualInfo open & close */
    private void displayIndividualInfo() {
        individualInfoStage.show();
        System.out.println("Showed IndividualInfo Stage.");
    }

    private void closeIndividualInfo() {
        individualInfoStage.close();
        System.out.println("Closed IndividualInfo Stage.");
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

    /** Complete deletion of a Client (UI and data) */
    public static void deleteClient(Client client) {
        instance.clients.remove(client);
        CalendarController.removeClientInfoButton(client);
    }

    public void createNewAppointment(Client client, Appointment appointment) {
        client.defineNewAppointment(appointment);
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
