/*
Copyright (c) 2019, Alexander Joseph Swanson Villares
alexanderjswanson@icloud.com | https://github.com/ajswanny
*/


/* Package */
package Accounter.fxml.calendar.monthly;


/* Imports */
import Accounter.java.Accounter;
import Accounter.java.calendar_gridpane.DayGridPane;
import Accounter.java.calendar_gridpane.MonthGridPane;
import Accounter.java.calendar_gridpane.WeekGridPane;
import Accounter.java.client.Client;
import Accounter.java.client.Corporation;
import Accounter.java.client.Individual;
import Accounter.java.enums.MethodTask;
import Accounter.java.user.User;
import Accounter.java.window.SettingsWindow;
import Accounter.java.window.client_details.ClientDetailsWindow;
import Accounter.java.window.client_details.CorporationDetailsWindow;
import Accounter.java.window.client_details.IndividualDetailsWindow;
import Accounter.java.window.new_client.NewCorporationWindow;
import Accounter.java.window.new_client.NewIndividualWindow;
import Accounter.java.window.new_client_appointment.NewAppointmentWindow;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import org.jetbrains.annotations.NotNull;

import java.net.URL;
import java.time.LocalDate;
import java.util.*;


/**
 * Active: Currently being displayed.
 * Current: Actual value of world.
 */
@SuppressWarnings({"Duplicates", "unused", "JavaDoc"})
public class CalendarViewController implements Initializable {

    /* Class Attributes */
    /**
     * The Map containing the indexes and names of the months of the year.
     */
    private Map<Integer, String> MONTHS_OF_YEAR;

    /**
     * The Map containing the names of weekdays.
     */
    private Map<Integer, String> DAYS_OF_WEEK;

    /**
     * The height of the APP Scene's root container's CENTER Node.
     */
    private final double RCC_HEIGHT = 741.0;

    /**
     * The width of the APP Scene's root container's CENTER Node.
     */
    private final double RCC_WIDTH = 1048.0;

    /**
     * The integer value of the active week of the year. This value defaults to 0.
     */
    private int active_week_value = 0;

    /**
     * The integer value of the current month. Range: 0 to 11.
     */
    private int current_month_value = LocalDate.now().getMonthValue()-1;

    /**
     * The integer value of the active month.
     */
    private int active_month_value = current_month_value;

    /**
     * The integer value of the current year.
     */
    private int current_year_value = LocalDate.now().getYear();

    /**
     * The integer value of the active year.
     */
    private int active_year_value = current_year_value;

    /**
     *
     */
    private Accounter application;

    /**
     * The current User.
     */
    @FXML
    private User current_user;

    /**
     * The Calendar used for the creation of Month/Week/DayGridpanes.
     */
    private Calendar calendar =
            new GregorianCalendar(Calendar.getInstance().get(Calendar.YEAR), Calendar.JANUARY, 1);


    /* Class UI Elements */
    /**
     *
     */
    private Scene scene;

    /**
     *
     */
    @FXML
    public BorderPane root_view;

    @FXML
    AnchorPane root_top_ap;

    @FXML
    AnchorPane root_left_ap;

    @FXML
    AnchorPane root_center_ap;

    /**
     * The main container for the Views and Controls within the root_view's LEFT node.
     */
    @FXML
    VBox root_left_primary_container;

    /**
     * The main container for the Views and Controls within the root_view's CENTER node.
     */
    @FXML
    VBox root_center_primary_container;

    @FXML
    ScrollPane client_list_sp;

    /**
     *
     */
    @FXML
    private AnchorPane calendar_container;

    /**
     * The MenuBar containing the main Menus.
     */
    @FXML
    private MenuBar root_top_mb;

    @FXML
    private Menu accounter_menu;

    @FXML
    private Menu file_menu;

    @FXML
    private Menu new_client_menu;

    @FXML
    private MenuItem settings_menu_item;

    @FXML
    private MenuItem close_menu_item;

    @FXML
    private MenuItem new_individual_menu_item;

    @FXML
    private MenuItem new_corporation_menu_item;

    @FXML
    private Label CLIENTS;

    /**
     *
     */
    @FXML
    private VBox client_details_btns_container;

    /**
     * The Labels that display the names of the days of the week.
     */
    private Map<Integer, Label> day_of_week_lbls = new HashMap<>(7);

    /**
     * The HBox containing the Labels for weekdays.
     */
    @FXML
    private HBox weekday_name_lbl_container = new HBox();

    /**
     * The GridPanes representing each month of the year for the current year.
     */
    private Map<Integer, MonthGridPane> current_year_monthgridpanes = new HashMap<>(12);

    /**
     * The container for the current year's WeekGridPanes; ordered by: Month::Week::WeekGridPane.
     */
    private HashMap<Integer, HashMap<Integer, WeekGridPane>> current_year_weekgridpanes = new HashMap<>(12);

    /**
     * The VBox that contains the Calendar and its controls.
     *
     * FXML Properties: Already contains the containers for the Calendar controls and the weekday Labels.
     */
    private VBox root_center_vb = new VBox();

    /**
     * The HBox that contains all controls for switching the month displayed in the App Scene.
     */
    @FXML
    HBox month_view_controls_container;

    /**
     * The Label indicating which month is being displayed in the App Scene.
     */
    @FXML
    private Label active_month_or_week_label;

    /**
     * The Label indicating what the active year is; that is, the year for which months are being displayed to the user.
     */
    @FXML
    private Label active_year_lbl;

    @FXML
    private Label SUNDAY_LBL;

    @FXML
    private Label MONDAY_LBL;

    @FXML
    private Label TUESDAY_LBL;

    @FXML
    private Label WEDNESDAY_LBL;

    @FXML
    private Label THURSDAY_LBL;

    @FXML
    private Label FRIDAY_LBL;

    @FXML
    private Label SATURDAY_LBL;

    /**
     * The Button allowing for left-switching of the currently displayed month.
     */
    @FXML
    private Button prev_month_btn;

    /**
     * The Button allowing for right-switching of the currently displayed month.
     */
    @FXML
    private Button next_month_btn;

    @FXML
    private ToggleButton weekly_calendar_toggle;

    @FXML
    private ToggleButton monthly_calendar_toggle;

    @FXML
    private Region MONTHS_CONTROL_SEPARATOR_1;

    @FXML
    private Region MONTHS_CONTROL_SEPARATOR_2;

    /**
     * The container for WeekGridPanes; ordered by: Year::Month::Week::WeekGridPane.
     */
    private HashMap<Integer, HashMap<Integer, HashMap<Integer, WeekGridPane>>> weekgridpanes = new HashMap<>();

    /**
     * The Map containing the respective MonthGridPanes of every defined year.
     */
    private Map<Integer, HashMap<Integer, MonthGridPane>> monthgridpanes = new HashMap<>();

    private HashMap<Integer, HashMap<Integer, ArrayList<DayGridPane>>> daygridpanes = new HashMap<>();

    /**
     * The Map containing the Buttons for every Client that open their respective DetailsWindows.
     */
    private Map<Client, Button> client_buttons = new HashMap<>();


    private MonthGridPane x;


    /* Constructor */
    public CalendarViewController() {

    }

    /**
     * Automatically called after the FXML file has been loaded.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void initialize(
            Accounter application,
            Map<Integer, String> MONTHS_OF_YEAR,
            Map<Integer, String> DAYS_OF_WEEK,
            User current_user
    ) {

        // Initialize data.
        init_data(application, MONTHS_OF_YEAR, DAYS_OF_WEEK, current_user);

        // Initialize the UI.
        init_ui();

        $DEV();

    }

    /* Methods */
    private void $DEV() {



        x = monthgridpanes.get(active_year_value).get(0);



    }

    private void init_data(
            Accounter application,
            Map<Integer, String> MONTHS_OF_YEAR,
            Map<Integer, String> DAYS_OF_WEEK,
            User current_user
    ) {

        // Define values.
        this.application = application;
        this.MONTHS_OF_YEAR = MONTHS_OF_YEAR;
        this.DAYS_OF_WEEK = DAYS_OF_WEEK;
        this.current_user = current_user;

    }

    /**
     * Initializes all UI elements.
     */
    private void init_ui() {

        // Initialize the MenuBar.
        init_main_menu_bar();

        // Initialize the BorderPane Left.
        init_root_borderpane_left();

        // Initialize the BorderPane Center.
        init_root_borderpane_center();

        // Set the active MonthGridPane.
        set_active_monthgridpane();

        //
        monthly_calendar_toggle.setSelected(true);

        // Initialize custom styles.
        init_custom_styles();

    }

    private void init_custom_styles() {

        // Clear stylesheets.
//        client_list_sp.getStyleClass().clear();

    }

    /**
     * Initializes the main application MenuBar.
     *
     * FXML Properties:
     *  - All objects already initialized.
     *  - Object hierarchy is already defined.
     */
    private void init_main_menu_bar() {

        // Define the Menus "Accounter", "File", and "New Client".
        accounter_menu.setText("Accounter");
        file_menu.setText("File");
        new_client_menu.setText("New Client");

        // Define the Menu CSS hooks.
        accounter_menu.getStyleClass().add("menu_accounter");
        file_menu.getStyleClass().add("menu_file");


        /* Define the MenuItems for the "Accounter" Menu. */
        // The MenuItem to open a "Settings" window.
        settings_menu_item.setText("Settings");
        settings_menu_item.setOnAction(action_event -> {

            // Instantiate a Settings window.
            new SettingsWindow(application);

        });

        // The MenuItem to close the application.
        close_menu_item.setText("Close");
        close_menu_item.setOnAction(action_event -> {

            // Quit the application.
            Platform.exit();

        });


        /* Define the MenuItems for the "New Client" Menu. */
        // Define the MenuItem to create a new Client of type "Individual".
        new_individual_menu_item.setText("Individual");
        new_individual_menu_item.setOnAction(action_event -> {

            // Initialize a Client-creation window for a Client of type: Individual.
            new NewIndividualWindow(this);

        });

        // Define the MenuItem to create a new Client of type "Corporation".
        new_corporation_menu_item.setText("Corporation");
        new_corporation_menu_item.setOnAction(action_event -> {

            // Initialize a Client-creation window for a Client of type: Corporation.
            new NewCorporationWindow(this);

        });

    }

    private void init_root_borderpane_left() {

        // Update the list display of Client buttons.
        def_client_list(MethodTask.INITIALIZE_DATA);

    }

    /**
     * Setup for the root-BorderPane's center node.
     */
    private void init_root_borderpane_center() {

        // Initialize the Month controls and add them to the RCC.
        init_calendar_controls();

        // Define the collection of DayGridPanes for the current year.
        init_daygridpanes(current_year_value);

        // Define the collection of MonthGridPanes.
        init_monthgridpanes(current_year_value);

        // Define the collection of MonthGridPanes for the current year.
        current_year_monthgridpanes = monthgridpanes.get(current_year_value);

        // Define the collection of WeekGridPanes.
        init_weekgridpanes(current_year_value);

        // Define the collection of WeekGridPanes for the current year.
        current_year_weekgridpanes = weekgridpanes.get(current_year_value);

    }

    /**
     * Define the Buttons that allow users to access and edit details about a Client.
     *
     * @param method_task Whether this method is being called to update or init the list.
     */
    public void def_client_list(MethodTask method_task) {

        if (method_task.equals(MethodTask.UPDATE_DATA)) {

            // Update the Clients data.
            application.def_client_data();

        }

        // Create an iterator for the 'clients' HashMap.
        Iterator iterator = application.clients.entrySet().iterator();

        // Define a temporary container for Client-details Buttons.
        List<Button> buttons = new ArrayList<>();

        // Define the Buttons.
        while (iterator.hasNext()) {

            // Create a reference to the current Client in the level of iteration.
            HashMap.Entry element = (HashMap.Entry) iterator.next();

            // Define a new Client object in order to serialize successfully.
            Client client = (Client) element.getValue();

            // Define the button for the Client.
            Button button = def_client_details_btn(client);

            // Store the Client and Button.
            client_buttons.putIfAbsent(client, button);

            // Append the Client button to the temporary container.
            buttons.add(button);

        }

        // Sort the Buttons in alphabetical order of their text.
        buttons.sort(Comparator.comparing(Labeled::getText));

        // Add the Buttons to their container in the UI.
        client_details_btns_container.getChildren().setAll(buttons);

    }

    /**
     * Defines the Button that open a Client's DetailsWindow.
     *
     * @param client The Client whose DetailsWindow-Button will be defined.
     * @return The defined Button.
     */
    private Button def_client_details_btn(@NotNull Client client) {

        // Create the new Button.
        Button button = new Button(client.get_identifier());

        // Define the Button.
        button.setOnAction( action_event -> {

            try {

                // Define action: open the client's appropriate details window.
                switch (client.get_client_type()) {

                    case Generic:

                        // Open a window to display Client details.
                        ClientDetailsWindow window = new ClientDetailsWindow(client, this);
                        window.start_ui();
                        break;

                    case Corporation:

                        // Open a window to display Client details, casting the Client to a Corporation.
                        new CorporationDetailsWindow((Corporation) client, this);
                        break;

                    case Individual:

                        // Open a window to display Client details, casting the Client to an Individual.
                        new IndividualDetailsWindow((Individual) client, this);
                        break;

                }

            } catch (java.lang.ClassCastException exception) {

                // Output status.
                application.update_error_log("Unable to cast Client objects to different extended types at " +
                        "'Accounter/src/Accounter/java/Accounter.init_root_borderpane_left'.");
                exception.printStackTrace();

            }

        });

        // Define the ContextMenu for the Client button.
        ContextMenu context_menu = new ContextMenu();

        // Define the MenuItem for creation of a new Appointment.
        MenuItem cm__new_appointment = new MenuItem("New Appointment");
        cm__new_appointment.setOnAction( action_event -> {
            new NewAppointmentWindow(this, client, true);
        });

        // Define the MenuItem for deletion of a Client.
        MenuItem cm__delete_client = new MenuItem("Delete");
        cm__delete_client.setOnAction(event -> delete_client(client));

        // Add the MenuItem to the ContextMenu.
        context_menu.getItems().addAll(cm__new_appointment, cm__delete_client);

        // Define the Client Button's ContextMenu.
        button.setContextMenu(context_menu);


        return button;

    }

    /**
     *
     */
    private void delete_client(Client client) {

        // Call the parent function to delete the Client.
        application.delete_client(client);

        // Update the UI Client list.
        def_client_list(MethodTask.UPDATE_DATA);

        // Update the data and UI related to Clients.
        update_root_center();

    }

    /**
     * Initializes the controls for month-switching.
     * test
     *
     * Note: The oldest year possible is 2018; this is the year in which the application was created.
     */
    private void init_calendar_controls() {

        // Set the text for the Label indicating which month is currently being displayed.
        active_month_or_week_label.setText(MONTHS_OF_YEAR.get(active_month_value));

        // Set the text of the Label indicating the current year.
        active_year_lbl.setText(String.valueOf(active_year_value));

        // Define the action for the last month/week-switch control.
        prev_month_btn.setOnAction(event -> change_calendar_backwards());

        // Define the action for the next month/week-switch control.
        next_month_btn.setOnAction(event -> change_calendar_forwards());

        // Define the action for the week-calendar ToggleButton.
        weekly_calendar_toggle.setOnAction(event -> {

            // Ensure that the month-toggle does not show selection.
            monthly_calendar_toggle.setSelected(false);

            // Set this selector's toggle to true.
            weekly_calendar_toggle.setSelected(true);

            // Set the current WeekGridPane to be displayed.
            set_active_weekgridpane();

        });

        // Define the action for the month-calendar ToggleButton.
        monthly_calendar_toggle.setOnAction(event -> {

            // Ensure that the week-toggle does not show selection.
            weekly_calendar_toggle.setSelected(false);

            // Set this selector's toggle to true.
            monthly_calendar_toggle.setSelected(true);

            // Set the active MonthGridPane to be displayed.
            set_active_monthgridpane();

        });

    }

    private void change_calendar_backwards() {

        // Define switching for MonthGridPanes.
        if (monthly_calendar_toggle.isSelected()) {

            // Set active_month_value
            set_active_month_value(active_month_value - 1);

            // Switch the MonthGridPane
            switch_monthgridpane(active_month_value);

        // Define switching for WeekGridPanes.
        } else {

            // Set new active_week_value to previous week
            set_active_week_value(active_week_value - 1);

            // Switch the weekgridpane
            switch_weekgridpane(active_week_value);

        }

    }

    private void change_calendar_forwards() {


        // Define switching for MonthGridPanes.
        if (monthly_calendar_toggle.isSelected()) {

            // New active_month_value
            set_active_month_value(active_month_value + 1);

            // Set new monthgridpane
            switch_monthgridpane(active_month_value);

        // Define switching for WeekGridPanes.
        } else {

            // New active_week_value
            set_active_week_value(active_week_value + 1);

            // Set new weekgridpane
            switch_weekgridpane(active_week_value);

        }


    }

    /**
     *
     * @param year_value
     *
     * Functionality: verified.
     */
    private void init_daygridpanes(int year_value) {

        // Define the HashMap of Months-DayGridPanes for the given year-value.
        daygridpanes.putIfAbsent(year_value, new HashMap<>(12));

        // Define the DayGridPanes for every month of the given year-value.
        for (int i = 0; i < 12; i++) {

            // Set the appropriate Calendar values for processing.
            calendar.set(Calendar.YEAR, year_value);
            calendar.set(Calendar.MONTH, i);
            calendar.set(Calendar.DAY_OF_MONTH, 1);

            // Define the value for the maximum days of the current month being processed.
            int max_days_of_current_month = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

            // Define the DayGridPanes for the current month in the loop.
            init_daygridpanes(year_value, i, max_days_of_current_month);

        }

        // Reset the Calendar's appropriate values.
        calendar.set(Calendar.YEAR, current_year_value);
        calendar.set(Calendar.MONTH, current_month_value);

    }

    /**
     * Defines the collection of DayGridPanes for a given month of a given year. The algorithm, in addition, generates
     * blank DayGridPanes in order to fill the spaces in the UI where days of a previous or next month would be.
     *
     * Day-of-week ordering:
     * Day-of-month ordering: 1 to Max.
     *
     * @param year_value The year the DayGridPanes correspond to.
     * @param month_value The month the DayGridPanes correspond to.
     * @param maximum_days_of_month The maximum days of the month the DayGridPanes correspond to.
     *
     * Functionality: verified.
     */
    private void init_daygridpanes(int year_value, int month_value, int maximum_days_of_month) {

        // Define the collection for new DayGridPanes.
        ArrayList<DayGridPane> dgps = new ArrayList<>(42);

        // Define a reference to the first and last weekdays of the given month.
        int first_weekday_in_month = calendar.get(Calendar.DAY_OF_WEEK);
        calendar.set(Calendar.DAY_OF_MONTH, maximum_days_of_month);
        int last_weekday_in_month = calendar.get(Calendar.DAY_OF_WEEK);

        // Define empty DayGridPanes for days of the beginning of the calendar not occupied by the given month.
        for (int i = 1; i < first_weekday_in_month; i++) { dgps.add(new DayGridPane()); }

        // Define a new DayGridPane for every day of the given month.
        for (int i = 1; i <= maximum_days_of_month; i++) {
            dgps.add(
                    new DayGridPane(
                        this,
                        calendar.get(Calendar.YEAR),
                        month_value,
                        i,
                        new ArrayList<>(application.clients.values()
                    )
            ));
        }

        // Define empty DayGridPanes for days of the the end of the calendar not occupied by the given month.
        for (int i = last_weekday_in_month; dgps.size() < 42; i++) { dgps.add(new DayGridPane()); }

        // Archive the collection of DayGridPanes in their respective container.
        daygridpanes.get(year_value).putIfAbsent(month_value, dgps);

    }

    /**
     * Defines the collection of MonthGridPanes for the months of a given year using the DayGridPanes from the master
     * data structure: {@link #daygridpanes}.
     *
     * @param year_value The value of the year for which to define MonthGridPanes.
     *
     * Functionality: verified.
     */
    private void init_monthgridpanes(int year_value) {

        // Define HashMap for MonthGridPanes of the given year.
        HashMap<Integer, MonthGridPane> mgps = new HashMap<>(12);

        // Create a MonthGridPane for the twelve months of the given year-value.
        for (int i = 0; i < 12; i++) { mgps.put(i, new MonthGridPane(i, daygridpanes.get(year_value).get(i))); }

        // Add the new MonthGridPanes to to their main collection.
        monthgridpanes.put(year_value, mgps);

    }

    private void init_weekgridpanes(int year_value) {

        // Define a temporary container for the WeekGridPanes of the given year-value.
        HashMap<Integer, HashMap<Integer, WeekGridPane>> new_years_weekgridpanes = new HashMap<>(12);

        // Define a reference to the DayGridPanes of the given year.
        HashMap<Integer, ArrayList<DayGridPane>> years_daygridpanes = daygridpanes.get(year_value);

        // Define a container for the DayGridPanes of any given week.
        ArrayList<DayGridPane> weeks_daygridpanes = new ArrayList<>();

        // The integer tracking the last day for the week being processed.
        int weekday_limiter = 7;

        // Define the WeekGridPanes for every month in the given year.
        for (int i = 0; i < 12; i++) {

            // Initialize the WeekGridPane container for the month-value: i.
            new_years_weekgridpanes.put(i, new HashMap<>());

            // Initialize a WeekGridPane for every week of the month "i".
            for (int j = 0; j < 6; j++) {

                // Define the collection of DayGridPanes for every week "j".
                for (int k = weekday_limiter - 7; k < weekday_limiter && k < 42; k++) {

                    // Fetch the DayGridPane for the current day of the current week of the current month being
                    // processed.
                    weeks_daygridpanes.add(years_daygridpanes.get(i).get(k));

                }


                new_years_weekgridpanes.get(i).put(j, new WeekGridPane(weeks_daygridpanes));

                // Increment the limiter for the DayGridPane-fetch loop.
                weekday_limiter += 7;

                // Clear the collection of DayGridPanes.
                weeks_daygridpanes.clear();

            }

            // Store the current month-value's WeekGridPanes.
            weekgridpanes.put(year_value, new_years_weekgridpanes);

            // Reset
            weekday_limiter = 7;

        }

    }

    /**
     * Switches the active MonthGirdPane.
     *
     * @param month_value The value of the month for the new MonthGridPane to be active.
     */
    private void switch_monthgridpane(int month_value) {

        // Set the text of the new active-month-value UI Label.
        active_month_or_week_label.setText(MONTHS_OF_YEAR.get(active_month_value));

        // Update the UI: set the new active MonthGridPane.
        set_active_monthgridpane();

    }

    private void switch_weekgridpane(int week_value, int direction) {



    }

    /**
     * Switches the active WeekGridPane.
     *
     * @param week_value The value of the week for the new WeekGridPane to be active.
     */
    private void switch_weekgridpane(int week_value) {

        // Set the text of the new active-week-value UI Label.
        active_month_or_week_label.setText(MONTHS_OF_YEAR.get(active_month_value));

        // Update the UI: set the new active WeekGridPane.
        set_active_weekgridpane(active_year_value, active_month_value, active_week_value);

    }

    /**
     * Sets the node for the root_container CENTER.
     */
    private void set_active_monthgridpane() {

        // Call the parent-method with the redefined active month and year values.
        set_active_monthgridpane(active_year_value, active_month_value);

    }

    /**
     * Sets the active MonthGridPane. This is the MonthGridPane that is displayed to the user in the application.
     *
     * @param year_value The value of the year of the month to display.
     * @param month_value The value of the month to display.
     */
    private void set_active_monthgridpane(int year_value, int month_value) {

        if (year_value == current_year_value) {

            // Update the UI with the new MonthGridPane.
            update_calendar_container(current_year_monthgridpanes.get(month_value));

        } else {

            try {

                // Set the MonthGridPane with the provided parameters.
                update_calendar_container(monthgridpanes.get(year_value).get(month_value));

            // Define a new collection of MonthGridPanes for the 'year_value' and attempt the MonthGridPane update.
            } catch (NullPointerException exception) {

                // Initialize the DayGridPanes for the new year.
                init_daygridpanes(year_value);

                // Initialize the MonthGridPanes for the new year.
                init_monthgridpanes(year_value);

                // Update the UI.
                set_active_monthgridpane(year_value, month_value);

            }

        }

        // Update the UI with the active-year-value.
        active_year_lbl.setText(String.valueOf(active_year_value));

    }

    /**
     * Sets the active WeekGridPane as the first week of the active Month.
     */
    private void set_active_weekgridpane() {

        // Set the Calendar to the first week of the active year and month.
        set_active_weekgridpane(active_year_value, active_month_value, 0);

    }

    /**
     * Sets the active WeekGridPane given the year, month, and week-of-month.
     * @param year_value The year of the WeekGridPane.
     * @param month_value The month of the WeekGridPane.
     * @param week_value The week of the WeekGridPane.
     */
    private void set_active_weekgridpane(int year_value, int month_value, int week_value) {

        // Get the weekgridpane
        WeekGridPane weekgridpane = weekgridpanes.get(year_value).get(month_value).get(week_value);

        if (weekgridpane.get_empty_daygridpanes() == 7) {

        } else {

            // Set the Calendar's content to the active year and month's respective WeekGridPane.
            update_calendar_container(weekgridpane);

        }

    }

    /**
     * Updates the Calendar container with the given Node.
     *
     * @param object The Node to set as the Calendar container's
     */
    private void update_calendar_container(Node object) {

        calendar_container.getChildren().setAll(object);

    }

    /**
     * Used to update the root_container CENTER node if any relevant values have changed.
     */
    public void update_root_center() {

        // Update the Client data with new Appointment.
        application.def_client_data();

        // Update the MonthGridPanes with the refactored Clients.
        init_monthgridpanes(active_year_value);

        // Redefine the object in-place for the current MonthGridPane.
        set_active_monthgridpane();

    }

    /**
     * Updates the text of a Client's respective button for access of details.
     *
     * @param client The Client whose respective button will be updated.
     * @param text The new text for the button.
     */
    public void update_client_btn_txt(Client client, String text) {

        // Perform the update.
        client_buttons.get(client).setText(text);

    }


    /* Setters */
    /**
     * Sets a new value for "active_year_value".
     *
     * @param value The new year-value.
     * @throws IllegalArgumentException Thrown if the user attempts to access a year previous to the current year.
     */
    private void set_active_year_value(int value) throws IllegalArgumentException {

        // Ensure that the value is within bounds of current-year - ...
        if (value < LocalDate.now().getYear()) {

            // Year is not valid (not allowed)
            throw new IllegalArgumentException();

        } else {

            // Set new value
            active_year_value = value;

        }

    }

    /**
     * Sets a new value for "active_month_value".
     *
     * @param value The new month-value.
     */
    private void set_active_month_value(int value) {

        // Attempt switch to previous year.
        if (value < 0) {

            // Set active-year-value
            set_active_year_value(active_year_value - 1);

            active_month_value = 11;

        } else if (value > 11) {

            // Set active-year-value
            set_active_year_value(active_year_value + 1);

            active_month_value = 0;

        } else {

            active_month_value = value;

        }

    }

    /**
     * Sets a new value for "active_week_value".
     *
     * @param value The new week-value.
     */
    private void set_active_week_value(int value) {

        if (value < 0) {

            // Set month to previous
            set_active_month_value(active_month_value - 1);

            // Last week of previous month
            active_week_value = 5;

        } else if (value > 5) {

            // Set month to following
            set_active_month_value(active_month_value + 1);

            // First week of next month
            active_week_value = 0;

        } else {

            // Set new week value
            active_week_value = value;

        }

    }

}
