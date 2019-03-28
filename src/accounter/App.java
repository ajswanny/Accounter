/*
 * Created by Alexander Swanson on 3/28/19 7:09 PM.
 * Email: alexanderjswanson@icloud.com.
 * Copyright © 2019. All rights reserved.
 */

package accounter;

import accounter.controller.CalendarController;
import accounter.controller.FXMLController;
import accounter.java.Appointment;
import accounter.java.client.Client;
import accounter.java.client.Corporation;
import accounter.java.client.Individual;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;

public class App extends Application {

    /* Fields */
    public enum ApplicationWindow {
        APPLICATION_SETTINGS, NEW_INDIVIDUAL_DIALOGUE, NEW_CORPORATION_DIALOGUE, NEW_APPOINTMENT_DIALOGUE,
        INDIVIDUAL_INFO, CORPORATION_INFO
    }

    private Stage applicationSettingsStage;
    private Stage newIndividualDialogueStage;
    private Stage newCorporationDialogueStage;
    private Stage newAppointmentDialogueStage;
    private Stage individualInfoStage;
    private Stage corporationInfoStage;

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
        corporationInfoController = loadFxmlController("fxml/client_info/CorporationInfoController.fxml");

    }

    private FXMLController loadFxmlController(String controllerFxmlFilePath) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(controllerFxmlFilePath));
        fxmlLoader.load();
        return fxmlLoader.getController();
    }

    private void initAltStages() {

        // ApplicationSettings
        applicationSettingsStage = initAltStage(applicationSettingsController, f->closeApplicationSettings());

        // NewIndividualDialogue
        newIndividualDialogueStage = initAltStage(newIndividualDialogueController, f->closeNewIndividualDialogue());

        // NewCorporationDialogue
        newCorporationDialogueStage = initAltStage(newCorporationDialogueController, f->closeNewCorporationDialogue());

        // NewAppointmentDialogue
        newAppointmentDialogueStage = initAltStage(newAppointmentDialogueController, f->closeNewAppointmentDialogue());

        // IndividualInfo
        individualInfoStage = initAltStage(individualInfoController, f->closeIndividualInfo());

        // CorporationInfo
        corporationInfoStage = initAltStage(corporationInfoController, f->closeCorporationInfo());

    }

    private Stage initAltStage(@NotNull FXMLController fxmlController, EventHandler<WindowEvent> actionOnCloseRequest) {
        Stage stage = new Stage(StageStyle.UNIFIED);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setScene(fxmlController.getScene());
        stage.setOnCloseRequest(actionOnCloseRequest);
        return stage;
    }

    private void initClientData() {
        clients = new ArrayList<>();
        clients.add(new Individual("Beck", "Martin"));
        clients.add(new Corporation("Microsoft"));
    }


    /* GUI Alternation */
    public void requestApplicationClose() {
        Platform.exit();
    }

    public void requestDisplayForNewWindow(@NotNull ApplicationWindow applicationWindow) {
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
            case CORPORATION_INFO:
                displayCorporationInfo();
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

    /* CorporationInfo open & close */
    private void displayCorporationInfo() {
        corporationInfoStage.show();
        System.out.println("Opened CorporationInfo Stage.");
    }

    private void closeCorporationInfo() {
        corporationInfoStage.close();
        System.out.println("Closed CorporationInfo Stage");
    }

    /* Client Creation */
    public void createNewIndividual(String firstName, String lastName) {
        Individual c = new Individual(firstName, lastName);
        clients.add(c);
        CalendarController.createNewClientButton(c);
        closeNewIndividualDialogue();
    }

    public void createNewCorporation(String name) {
        Corporation c = new Corporation(name);
        clients.add(c);
        CalendarController.createNewClientButton(c);
        closeNewCorporationDialogue();
    }

    /** Complete deletion of a Client (UI and data) */
    public static void deleteClient(Client client) {
        instance.clients.remove(client);
        CalendarController.removeClientInfoButton(client);
    }

    @SuppressWarnings("unused")
    public void createNewAppointment(@NotNull Client client, Appointment appointment) {
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
