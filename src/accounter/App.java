/*
 * Created by Alexander Swanson on 4/15/19 10:18 PM.
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
import accounter.java.models.DayPaneBase;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

public class App extends Application {

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

    private HashMap<Integer, GridPane> currentYearCalendarMonthGridpanes;

    private HashMap<Integer, DayPaneBase> currentYearDayPaneBases;

    public LocalDate variableDate = LocalDate.now();

    private int activeMonthValue = variableDate.getMonthValue();

    private File CLIENTS_SER_FP = new File("res/ser/clients/");

    private File clientsSERs;

    private static App instance;

    private boolean verbose;

    /**
     * Default constructor.
     */
    public App() {
        instance = this;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        debug();
        verbose = false;

        // Setup Filesystem
        File systemDirectory = new File(System.getenv("ProgramFiles") + "/Accounter");
        if (!systemDirectory.exists()) {
            if (new File(System.getenv("ProgramFiles") + "/Accounter").mkdir() && new File(System.getenv("ProgramFiles") + "/Accounter/ser").mkdir()) {
                if (verbose) System.out.println("Created 'Accounter' directory successfully.");
            } else {
                throw new RuntimeException("Could not create 'Accounter' directory.");
            }
        }
        clientsSERs = new File(System.getenv("ProgramFiles") + "Accounter/ser");

        // Load resources
        initClientData();
        initFxmlControllers();
        initAltStages();
        initMonthCalendarGrids();

        // Setup Stage
        primaryStage = new Stage();
        primaryStage.setMinWidth(600);
        primaryStage.setMinHeight(432);
        primaryStage.setScene(calendarController.getScene());

        // Define current month for the Calendar Grid.
        calendarController.setCalendarGridContent(currentYearCalendarMonthGridpanes.get(activeMonthValue));

        primaryStage.show();
        debug();

    }

    @Override
    public void stop() {

        // Store data if it exists.
        try {
            for (Client element : clients) {

                FileOutputStream fOutStream = new FileOutputStream(clientsSERs);
                ObjectOutputStream objectOutStream = new ObjectOutputStream(fOutStream);
                objectOutStream.writeObject(element);

            }
        } catch (IOException e) {
            System.out.println("IOException when serializing Clients.");
        }

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

    /** Defines a new custom JavaFX Stage */
    private Stage initAltStage(@NotNull FXMLController fxmlController, EventHandler<WindowEvent> actionOnCloseRequest) {

        Stage stage = new Stage(StageStyle.UNIFIED);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setScene(fxmlController.getScene());
        stage.setOnCloseRequest(actionOnCloseRequest);
        return stage;

    }

    /** Initializes all Client data for the User. */
    private void initClientData() {
        appointments = new ArrayList<>();
        clients = new ArrayList<>();

        // Load data if available.
        try {

            // Define the stream to access all files in the 'clients' directory.
            DirectoryStream<Path> dir_stream = Files.newDirectoryStream(clientsSERs.toPath());
            FileInputStream fileInputStream;
            ObjectInputStream objectInputStream;

            // Load a Client from each file within the 'clients' directory in 'data'.
            for (Path entry : dir_stream) {

                fileInputStream = new FileInputStream(entry.toString());
                objectInputStream = new ObjectInputStream(fileInputStream);

                Client client = (Client) objectInputStream.readObject();
                clients.add(client);

            }

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Exception at 'initClientData()'.");
        }

        for (Client client : clients) {
            appointments.addAll(client.getAppointments());
        }

    }

    /* GUI Alternation */
    public void requestApplicationClose() {
        Platform.exit();
    }

    public void requestDisplayForWindow(@NotNull ApplicationWindow applicationWindow) {
        switch (applicationWindow) {
            case APPLICATION_SETTINGS:
                applicationSettingsStage.show();
                if (verbose) System.out.println("Showed ApplicationSettings Stage.");
                break;
            case NEW_INDIVIDUAL_DIALOGUE:
                newIndividualDialogueStage.show();
                if (verbose) System.out.println("Showed NewIndividualDialogue Stage.");
                break;
            case NEW_CORPORATION_DIALOGUE:
                newCorporationDialogueStage.show();
                if (verbose) System.out.println("Showed NewCorporationDialogue Stage.");
                break;
            case NEW_APPOINTMENT_DIALOGUE:
                newAppointmentDialogueController.setRespectiveClient(newAppointmentClientFlag);
                newAppointmentDialogueStage.show();
                if (verbose) System.out.println("Showed NewAppointmentDialogue Stage.");
                break;
            case INDIVIDUAL_INFO:
                individualInfoStage.show();
                if (verbose) System.out.println("Showed IndividualInfo Stage.");
                break;
            case CORPORATION_INFO:
                corporationInfoStage.show();
                if (verbose) System.out.println("Showed CorporationInfo Stage.");
                break;
        }
    }

    public void requestCloseForWindow(@NotNull ApplicationWindow applicationWindow) {
        switch (applicationWindow) {
            case APPLICATION_SETTINGS:
                applicationSettingsStage.close();
                if (verbose) System.out.println("Closed ApplicationSettings Stage.");
                break;
            case NEW_INDIVIDUAL_DIALOGUE:
                newIndividualDialogueStage.close();
                if (verbose) System.out.println("Closed NewIndividualDialogue Stage.");
                break;
            case NEW_CORPORATION_DIALOGUE:
                newCorporationDialogueStage.close();
                if (verbose) System.out.println("Closed NewCorporationDialogue Stage.");
                break;
            case NEW_APPOINTMENT_DIALOGUE:
                newAppointmentDialogueStage.close();
                if (verbose) System.out.println("Closed NewAppointmentDialogue Stage.");
                break;
            case INDIVIDUAL_INFO:
                individualInfoStage.close();
                if (verbose) System.out.println("Closed IndividualInfo Stage.");
                break;
            case CORPORATION_INFO:
                corporationInfoStage.close();
                if (verbose) System.out.println("Closed CorporationInfo Stage.");
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
        instance.calendarController.createNewClientInfoButton(c);
    }

    public void createNewCorporation(String name) {
        Corporation c = new Corporation(name);
        clients.add(c);
        instance.calendarController.createNewClientInfoButton(c);
    }

    /** Complete deletion of a Client (UI and data) */
    public static void deleteClient(Client client) {
        instance.clients.remove(client);
        instance.calendarController.removeClientInfoButton(client);

        File clientSerFile = new File(instance.clientsSERs.getPath() + "/" + client.getNid() + ".ser");
        if (!clientSerFile.delete()) {
            throw new RuntimeException("Failed to delete a Client in the filesystem.");
        }
    }

    /* Calendar Grid Creation */
    /** Initializes the GridPanes to represent Months of the Year for the App's GUI. */
    private void initMonthCalendarGrids() {

        // Prep vars
        currentYearCalendarMonthGridpanes = new HashMap<>();
        currentYearDayPaneBases = new HashMap<>(365);
        LocalDate localDate = LocalDate.now();
        LocalDate calendar;
        GridPane gridPane;
        DayPaneBase dayPaneBase;
        ArrayList<AppointmentInfoLabel> appointmentInfoLabels;
        int calDayOfWeekVal;
        int dayOfYear = 1;

        // Initialize a GridPane for each month of the current year.
        for (int m = 1; m < 13; m++) {
            calendar = LocalDate.of(localDate.getYear(), m, 1);
            gridPane = new GridPane();

            // Initialize data for each day of the month m.
            int guiWeekValue = 0;
            for (int d = 1; d <= calendar.getMonth().maxLength(); d++) {

                // Possible error with LocalDate and February
                if (m == 2 && d == 29) { break; }

                // Update day-of-month
                calendar = calendar.withDayOfMonth(d);

                // Define InfoLabels for all Appointments that are scheduled on the date represented by "calendar"
                if (appointments != null) {
                    appointmentInfoLabels = new ArrayList<>();
                    for (Appointment appointment : appointments) {
                        if (appointment.getDate().equals(calendar)) {
                            appointmentInfoLabels.add(new AppointmentInfoLabel(appointment, true));
                        }
                    }
                    // DayGridPane base with AppointmentInfoLabels
                    dayPaneBase = new DayPaneBase(d, appointmentInfoLabels, calendar);

                } else {
                    // DayGridPane base with just day-of-month
                    dayPaneBase = new DayPaneBase(d, calendar);
                }

                // Creation of DayGridPane.
                GridPane.setHgrow(dayPaneBase, Priority.ALWAYS); GridPane.setVgrow(dayPaneBase, Priority.ALWAYS);

                // Placement on Month-GridPane
                calDayOfWeekVal = calendar.getDayOfWeek().getValue();
                if (calDayOfWeekVal == 7) {
                    gridPane.add(dayPaneBase, 0, guiWeekValue);
                } else {
                    gridPane.add(dayPaneBase, calDayOfWeekVal, guiWeekValue);
                }
                if (calDayOfWeekVal % 6 == 0) {
                    guiWeekValue++;
                }

                // Record the DayPaneBase of the current day of the year in calculation.
                currentYearDayPaneBases.put(dayOfYear, dayPaneBase);
                dayOfYear++;

            }

            // Set height constraints for GridPane rows and store the object
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.prefHeightProperty().bind(calendarController.getCalendarGridContainer().prefHeightProperty().divide(6));
            for (int i = 0; i < 6; i++) {
                gridPane.getRowConstraints().add(rowConstraints);
            }
            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.prefWidthProperty().bind(calendarController.getCalendarGridContainer().prefWidthProperty().divide(7));
            for (int i = 0; i < 7; i++) {
                gridPane.getColumnConstraints().add(columnConstraints);
            }
            gridPane.setPadding(new Insets(0, 1, 1, 0));

            currentYearCalendarMonthGridpanes.put(m, gridPane);
        }

    }

    /** Creates a new Appointment, defining its data and updating the GUI. */
    public void createNewAppointment(@NotNull Client client, String name, LocalDate date, LocalTime time, TimePeriod timePeriod) {

        // Update data.
        Appointment appointment = new Appointment(name, date, time, timePeriod);
        client.defineNewAppointment(appointment);
        appointments.add(appointment);

        // Update UI.
        DayPaneBase dayPaneBase = (DayPaneBase) currentYearCalendarMonthGridpanes.get(date.getMonthValue()).getChildren().get(date.getDayOfMonth()-1);
        dayPaneBase.addNewAppointmentInfoLabel(new AppointmentInfoLabel(appointment, false));

    }

    public void deleteAppointment(Appointment appointment) {

        // Update data.
        appointments.remove(appointment);
        for (Client client : clients) {
            client.getAppointments().remove(appointment);
        }

        // Update UI.
        currentYearDayPaneBases.get(appointment.getDate().getDayOfYear()).removeAppointmentInfoLabelOfAppointment(appointment);

    }

    /**
     * Validates and completes a request to change the current Month that is displayed in the Calendar-GridPane
     * Direction: 0 == previous month; 1 == next month.
     */
    public void requestNewMonthToGrid(int direction) {
        if (direction == 0 && (activeMonthValue - 1 != 0)) {
            activeMonthValue -= 1;
            variableDate = variableDate.withMonth(activeMonthValue);
            calendarController.setCalendarGridContent(currentYearCalendarMonthGridpanes.get(activeMonthValue));
            calendarController.updateMonthLabelText(variableDate.getMonth().toString());
        } else if (direction == 1 && (activeMonthValue + 1 != 13)){
            activeMonthValue += 1;
            variableDate = variableDate.withMonth(activeMonthValue);
            calendarController.setCalendarGridContent(currentYearCalendarMonthGridpanes.get(activeMonthValue));
            calendarController.updateMonthLabelText(variableDate.getMonth().toString());
        } else {
            System.out.println("Encountered illegal request.");
        }
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
