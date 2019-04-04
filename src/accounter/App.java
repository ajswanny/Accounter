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
import accounter.java.models.AppointmentInfoLabel;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
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
import java.util.HashMap;

public class App extends Application {

    /* TODO
        Calendar
//        Time implementation for Appointment
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

    private ArrayList<Appointment> appointments;

    private HashMap<Integer, GridPane> currentYearCalendarGrids;

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
        initMonthCalendarGrids();
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
        createNewIndividual("Beck", "Martin");
        createNewCorporation("Microsoft");

        calendarController.setCalendarGridContent(currentYearCalendarGrids.get(2));
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
        applicationSettingsStage = initAltStage(applicationSettingsController, f->requestCloseForWindow(ApplicationWindow.APPLICATION_SETTINGS));

        // NewIndividualDialogue
        newIndividualDialogueStage = initAltStage(newIndividualDialogueController, f->requestCloseForWindow(ApplicationWindow.NEW_INDIVIDUAL_DIALOGUE));

        // NewCorporationDialogue
        newCorporationDialogueStage = initAltStage(newCorporationDialogueController, f->requestCloseForWindow(ApplicationWindow.NEW_CORPORATION_DIALOGUE));

        // NewAppointmentDialogue
        newAppointmentDialogueStage = initAltStage(newAppointmentDialogueController, f->requestCloseForWindow(ApplicationWindow.NEW_APPOINTMENT_DIALOGUE));

        // IndividualInfo
        individualInfoStage = initAltStage(individualInfoController, f->requestCloseForWindow(ApplicationWindow.INDIVIDUAL_INFO));

        // CorporationInfo
        corporationInfoStage = initAltStage(corporationInfoController, f->requestCloseForWindow(ApplicationWindow.CORPORATION_INFO));

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

    /* Calendar Grid Creation */
    private void initMonthCalendarGrids() {

        currentYearCalendarGrids = new HashMap<>();
        LocalDate localDate = LocalDate.now();
        LocalDate calendar;
        GridPane gridPane;
        AnchorPane anchorPane;
        Label label;
        VBox vBox;
        ArrayList<AppointmentInfoLabel> appointmentInfoLabels;
        int calDayOfWeekVal;

        for (int m = 1; m < 13; m++) {

            calendar = LocalDate.of(localDate.getYear(), m, 1);
            gridPane = new GridPane();

            int guiWeekValue = 0;
            for (int d = 1; d <= calendar.getMonth().maxLength(); d++) {

                // Possible error with LocalDate and February
                if (m == 2 && d == 29) { break; }

                label = new Label(String.valueOf(d));
                AnchorPane.setLeftAnchor(label, 5.0); AnchorPane.setTopAnchor(label, 5.0);
                vBox = new VBox();
                AnchorPane.setLeftAnchor(vBox, 0.0); AnchorPane.setBottomAnchor(vBox, 0.0); AnchorPane.setRightAnchor(vBox, 0.0);
                calendar = calendar.withDayOfMonth(d);

                // Define InfoLabels for all Appointments that are scheduled on the date represented by "calendar"
                if (appointments != null) {
                    appointmentInfoLabels = new ArrayList<>();
                    for (Appointment appointment : appointments) {
                        if (appointment.getDate().equals(calendar)) {
                            appointmentInfoLabels.add(new AppointmentInfoLabel(appointment));
                        }
                    }
                    vBox.getChildren().addAll(appointmentInfoLabels);
                }

                // Placement on GridPane
                calDayOfWeekVal = calendar.getDayOfWeek().getValue();
                anchorPane = new AnchorPane(label, vBox);
                if (calDayOfWeekVal == 7) {
                    gridPane.add(anchorPane, 0, guiWeekValue);
                } else {
                    gridPane.add(anchorPane, calDayOfWeekVal, guiWeekValue);
                }
                if (calDayOfWeekVal % 6 == 0) {
                    guiWeekValue++;
                }
            }

            currentYearCalendarGrids.put(m, gridPane);

        }

    }

    public void createNewAppointment(@NotNull Client client, String name, LocalDate date, LocalTime time, TimePeriod timePeriod) {
        Appointment appointment = new Appointment(name, date, time, timePeriod);
        client.defineNewAppointment(appointment);
        appointments.add(appointment);
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
