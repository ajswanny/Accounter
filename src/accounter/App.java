/*
 * Created by Alexander Swanson on 3/28/19 7:09 PM.
 * Email: alexanderjswanson@icloud.com.
 * Copyright © 2019. All rights reserved.
 */

package accounter;

import accounter.controller.CalendarController;
import accounter.controller.FXMLController;
import accounter.controller.NewAppointmentDialogueController;
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
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class App extends Application {

    /* TODO
        Time implementation for Appointment
//        Date implementation for Appointment
     */

    /* Fields */
    public enum ApplicationWindow {
        APPLICATION_SETTINGS, NEW_INDIVIDUAL_DIALOGUE, NEW_CORPORATION_DIALOGUE, NEW_APPOINTMENT_DIALOGUE,
        INDIVIDUAL_INFO, CORPORATION_INFO
    }

    public enum TimePeriod {
        AM, PM
    }

    private Stage applicationSettingsStage;
    private Stage newIndividualDialogueStage;
    private Stage newCorporationDialogueStage;
    private Stage newAppointmentDialogueStage;
    private Stage individualInfoStage;
    private Stage corporationInfoStage;

    private CalendarController calendarController;
    private FXMLController applicationSettingsController;
    private FXMLController newIndividualDialogueController;
    private FXMLController newCorporationDialogueController;
    private NewAppointmentDialogueController newAppointmentDialogueController;
    private FXMLController individualInfoController;
    private FXMLController corporationInfoController;

    private Client newAppointmentClientFlag;

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

        debug();

    }

    @Override
    public void stop() {
        System.out.println("Closing application.");
    }

    private void debug() {

    }

    private void initFxmlControllers() throws IOException {

        calendarController = (CalendarController) loadFxmlController("fxml/CalendarController.fxml");
        applicationSettingsController = loadFxmlController("fxml/ApplicationSettingsController.fxml");
        newIndividualDialogueController = loadFxmlController("fxml/NewIndividualDialogueController.fxml");
        newCorporationDialogueController = loadFxmlController("fxml/NewCorporationDialogueController.fxml");
        newAppointmentDialogueController = (NewAppointmentDialogueController) loadFxmlController("fxml/NewAppointmentDialogueController.fxml");
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
        applicationSettingsStage = initAltStage(applicationSettingsController, f-> requestCloseForWindow(ApplicationWindow.APPLICATION_SETTINGS));

        // NewIndividualDialogue
        newIndividualDialogueStage = initAltStage(newIndividualDialogueController, f-> requestCloseForWindow(ApplicationWindow.NEW_INDIVIDUAL_DIALOGUE));

        // NewCorporationDialogue
        newCorporationDialogueStage = initAltStage(newCorporationDialogueController, f-> requestCloseForWindow(ApplicationWindow.NEW_CORPORATION_DIALOGUE));

        // NewAppointmentDialogue
        newAppointmentDialogueStage = initAltStage(newAppointmentDialogueController, f-> requestCloseForWindow(ApplicationWindow.NEW_APPOINTMENT_DIALOGUE));

        // IndividualInfo
        individualInfoStage = initAltStage(individualInfoController, f-> requestCloseForWindow(ApplicationWindow.INDIVIDUAL_INFO));

        // CorporationInfo
        corporationInfoStage = initAltStage(corporationInfoController, f-> requestCloseForWindow(ApplicationWindow.CORPORATION_INFO));

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

    public void requestDisplayForWindow(@NotNull ApplicationWindow applicationWindow) {
        switch (applicationWindow) {
            case APPLICATION_SETTINGS:
                applicationSettingsStage.show();
                System.out.println("Showed ApplicationSettings Stage.");
                break;
            case NEW_INDIVIDUAL_DIALOGUE:
                newIndividualDialogueStage.show();
                System.out.println("Showed NewIndividualDialogue Stage.");
                break;
            case NEW_CORPORATION_DIALOGUE:
                newCorporationDialogueStage.show();
                System.out.println("Showed NewCorporationDialogue Stage.");
                break;
            case NEW_APPOINTMENT_DIALOGUE:
                newAppointmentDialogueController.setRespectiveClient(newAppointmentClientFlag);
                newAppointmentDialogueStage.show();
                System.out.println("Showed NewAppointmentDialogue Stage.");
                break;
            case INDIVIDUAL_INFO:
                individualInfoStage.show();
                System.out.println("Showed IndividualInfo Stage.");
                break;
            case CORPORATION_INFO:
                corporationInfoStage.show();
                System.out.println("Showed CorporationInfo Stage.");
                break;
        }
    }

    public void requestCloseForWindow(@NotNull ApplicationWindow applicationWindow) {
        switch (applicationWindow) {
            case APPLICATION_SETTINGS:
                applicationSettingsStage.close();
                System.out.println("Closed ApplicationSettings Stage.");
                break;
            case NEW_INDIVIDUAL_DIALOGUE:
                newIndividualDialogueStage.close();
                System.out.println("Closed NewIndividualDialogue Stage.");
                break;
            case NEW_CORPORATION_DIALOGUE:
                newCorporationDialogueStage.close();
                System.out.println("Closed NewCorporationDialogue Stage.");
                break;
            case NEW_APPOINTMENT_DIALOGUE:
                newAppointmentDialogueStage.close();
                System.out.println("Closed NewAppointmentDialogue Stage.");
                break;
            case INDIVIDUAL_INFO:
                individualInfoStage.close();
                System.out.println("Closed IndividualInfo Stage.");
                break;
            case CORPORATION_INFO:
                corporationInfoStage.close();
                System.out.println("Closed CorporationInfo Stage.");
                break;
        }
    }

    public static void updateNewAppointmentClientFlag(Client client) {
        instance.newAppointmentClientFlag = client;
    }

    /* Client Creation */
    public void createNewIndividual(String firstName, String lastName) {
        Individual c = new Individual(firstName, lastName);
        clients.add(c);
        instance.calendarController.createNewClientButton(c);
    }

    public void createNewCorporation(String name) {
        Corporation c = new Corporation(name);
        clients.add(c);
        instance.calendarController.createNewClientButton(c);
    }

    /** Complete deletion of a Client (UI and data) */
    public static void deleteClient(Client client) {
        instance.clients.remove(client);
        instance.calendarController.removeClientInfoButton(client);
    }

    @SuppressWarnings("unused")
    public void createNewAppointment(@NotNull Client client, String name, LocalDate date, LocalTime time, TimePeriod timePeriod) {
        client.defineNewAppointment(new Appointment(name, date, time, timePeriod));
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
