/*
Copyright (c) 2019, Alexander Joseph Swanson Villares
alexanderjswanson@icloud.com | https://github.com/ajswanny
*/


/* Package */
package Accounter.java;


/* Imports */

import Accounter.fxml.calendar.monthly.CalendarViewController;
import Accounter.java.client.Client;
import Accounter.java.user.User;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 *
 */
@SuppressWarnings({"JavaDoc", "FieldCanBeLocal", "MismatchedQueryAndUpdateOfCollection", "Duplicates"})
public class Accounter extends Application {

    /* Class Fields */
    /**
     * The Map containing the indexes and names of the months of the year.
     */
    protected Map<Integer, String> MONTHS_OF_YEAR = new HashMap<>();

    /**
     * The Map containing the names of weekdays.
     */
    protected Map<Integer, String> DAYS_OF_WEEK = new HashMap<>();

    /**
     * The Directory containing Client resource files.
     */
    private File clients_directory = new File("src/Accounter/data/clients");

    /**
     * The official error log File.
     */
    private File error_log = new File("src/Accounter/data/logs/error_log.txt");

    /**
     * The Map containing all Clients.
     */
    public Map<Long, Client> clients = new HashMap<>();

    /**
     *
     */
    private Scene primary_scene;

    /**
     *
     */
    private Pane blank_parent = new Pane();

    /**
     * The current User.
     */
    @FXML
    private User current_user;

    /**
     *
     */
    private FXMLLoader monthly_calendar_fxml_loader;

    /**
     *
     */
    private CalendarViewController calendar_view_controller;

    /**
     *
     */
    private BorderPane monthly_calendar_root_view;


    /* Initializer */
    /**
     * @param args
     */
    public static void main(String[] args) {

        launch(args);

    }


    /* Methods */
    @Override
    public void init() throws Exception {

        // Define the collection of references to the months of the year in the format desirable for presentation in
        // the UI.
        MONTHS_OF_YEAR = deserialize(HashMap.class, "src/Accounter/data/hashmaps/MONTHS_OF_YEAR.ser");
        if (MONTHS_OF_YEAR.isEmpty()) {

            MONTHS_OF_YEAR.put(0, "January");
            MONTHS_OF_YEAR.put(1, "February");
            MONTHS_OF_YEAR.put(2, "March");
            MONTHS_OF_YEAR.put(3, "April");
            MONTHS_OF_YEAR.put(4, "May");
            MONTHS_OF_YEAR.put(5, "June");
            MONTHS_OF_YEAR.put(6, "July");
            MONTHS_OF_YEAR.put(7, "August");
            MONTHS_OF_YEAR.put(8, "September");
            MONTHS_OF_YEAR.put(9, "October");
            MONTHS_OF_YEAR.put(10, "November");
            MONTHS_OF_YEAR.put(11, "December");

        }

        // Define the collection of the names of the days of the week.
        DAYS_OF_WEEK = deserialize(HashMap.class, "src/Accounter/data/hashmaps/DAYS_OF_WEEK.ser");
        if (DAYS_OF_WEEK.isEmpty()) {

            // TODO: Verify numbering.
            DAYS_OF_WEEK.put(0, "Sunday");
            DAYS_OF_WEEK.put(1, "Monday");
            DAYS_OF_WEEK.put(2, "Tuesday");
            DAYS_OF_WEEK.put(3, "Wednesday");
            DAYS_OF_WEEK.put(4, "Thursday");
            DAYS_OF_WEEK.put(5, "Friday");
            DAYS_OF_WEEK.put(6, "Saturday");

        }

        //
        def_client_data();

        // Initialize the main View controllers.
        init_view_controllers();

        // $DEVELOPMENT
        $DEV_SCRATCH_WORK();

    }

    private void init_view_controllers() throws Exception {

        // Initialize the View controller for the monthly calendar.
        monthly_calendar_fxml_loader = new FXMLLoader();
        monthly_calendar_fxml_loader.setLocation(
                getClass().getResource("../fxml/calendar/monthly/monthly_calendar.fxml")
        );
        monthly_calendar_root_view = monthly_calendar_fxml_loader.load();
        calendar_view_controller = monthly_calendar_fxml_loader.getController();
        calendar_view_controller.initialize(this, MONTHS_OF_YEAR, DAYS_OF_WEEK, current_user);

    }

    /**
     * Loads all resources and starts the Accounter application.
     *
     * @param primary_stage The Stage to be initialized.
     * @throws Exception Various Exceptions that are recorded in the official error log.
     */
    @Override
    public void start(Stage primary_stage) {

        // Defines the primary_stage title.
        primary_stage.setTitle("Accounter");

        primary_scene = new Scene(blank_parent);
        primary_stage.setScene(primary_scene);

        primary_scene.setRoot(monthly_calendar_root_view);


        // Shows the Stage.
        primary_stage.show();

    }

    /**
     * Performs necessary operations before application shut-down.
     */
    @Override
    public void stop() {

        // Serialize all application resources.
        serialize_resources();

    }

    /**
     * FOR DEVELOPMENT.
     */
    private void $DEV_SCRATCH_WORK() throws Exception {

        // Define the current user.
        this.current_user = new User("dev_username", "dev_password");

    }

    /**
     * Defines the Client objects and their resources within the application.
     */
    public void def_client_data() {

        try {

            // Define the stream to access all files in the 'clients' directory.
            Path dir = Paths.get(clients_directory.toURI());
            DirectoryStream<Path> dir_stream = Files.newDirectoryStream(dir);

            // Load a Client from each file within the 'clients' directory in 'data'.
            for (Path entry : dir_stream) {

                // Load a Client and store it in 'clients'.
                Client client = deserialize(Client.class, entry.toString());

                // Add the Client to the Clients HashMap.
                clients.putIfAbsent(client.get_NID(), client);

            }

        } catch (IOException e) {

            // Record the error.
            update_error_log("Could not locate the directory for Client resource files.", e);

        } catch (ClassNotFoundException e) {

            // Record the error.
            update_error_log("Could not define loaded serializations to Clients.", e);

        }

    }

    /**
     * Serializes application resources.
     */
    private void serialize_resources() {

        try {

            /* Serialize Clients */
            // Create an iterator for the 'clients' HashMap.
            Iterator iterator = clients.entrySet().iterator();
            while (iterator.hasNext()) {

                // Create a reference to the current Client in the level of iteration.
                HashMap.Entry element = (HashMap.Entry) iterator.next();

                // Output status.
                System.out.println("Serializing: " + element.getKey());

                // Define a new Client object in order to serialize successfully.
                Client client = (Client) element.getValue();

                // Serialize the Client object.
                client.serialize("src/Accounter/data/clients/" + client.get_NID() + ".ser", false);

                // Remove the Client from the iterator.
                iterator.remove();

            }

            /* Serialize Maps */
            serialize(MONTHS_OF_YEAR, "src/Accounter/data/hashmaps/MONTHS_OF_YEAR.ser");
            serialize(DAYS_OF_WEEK, "src/Accounter/data/hashmaps/DAYS_OF_WEEK.ser");

        } catch (IOException e) {
            update_error_log("Error when serializing Accounter resources in closing sequence.", e);
        }

    }

    /**
     * Serializes an object at the provided file.
     *
     * @param object The object to be serialized.
     * @param file_path The location of the file for serialization.
     */
    private void serialize(Object object, String file_path) {

        try {

            // Define the FileOutputStream.
            FileOutputStream file_out_stream = new FileOutputStream(file_path);

            // Define the ObjectOutputStream.
            ObjectOutputStream object_out_stream = new ObjectOutputStream(file_out_stream);

            // Serialize the object.
            object_out_stream.writeObject(object);

            // Close the streams.
            file_out_stream.close();
            object_out_stream.close();

        } catch (IOException e) {

            // Record the error.
            update_error_log("Error serializing object: " + object.toString(), e);

        }

    }

    /**
     * De-serializes an object at the specified file location.
     *
     * @param obj_type The type of the object to be de-serialized.
     * @param file_path The location of the file containing the object's serialized data.
     * @param <T> Generic type of serialized object.
     * @return The de-serialized object.
     * @throws IOException if the file containing the object's data cannot be located.
     * @throws ClassNotFoundException if the object cannot be converted to its specified type.
     */
    protected  <T> T deserialize(@NotNull Class<T> obj_type, String file_path) throws IOException, ClassNotFoundException {

        try {

            // Create a file input object to open the file specified by 'file_path'.
            FileInputStream file_in_stream = new FileInputStream(file_path);

            // Define the object deserializer.
            ObjectInputStream object_in_stream = new ObjectInputStream(file_in_stream);

            // Return the de-serialized object.
            return obj_type.cast(object_in_stream.readObject());

        } catch (IOException e) {

            // Record the error.
            update_error_log(obj_type.toString() + " resource file not found.", e);
            throw e;

        } catch (ClassNotFoundException e) {

            // Record the error.
            update_error_log(obj_type.toString() + " class not found.", e);
            throw e;

        }

    }

    /**
     * Removes a Client from the Map directory of Clients.
     *
     * @param client The Client to be deleted.
     */
    public void delete_client(Client client) {

        // Remove the Client.
        clients.remove(client.get_NID());

        // Delete the file containing the Client's serialization.
        File client_serial_file = new File("src/Accounter/data/clients/" + client.get_NID() + ".ser");
        client_serial_file.delete();

    }

    /**
     * Prints a desired message to the official error log without a specified Exception.
     */
    public void update_error_log(String message) {

        update_error_log(message, new Exception());

    }

    /**
     * Prints a desired message to the official error log.
     */
    private void update_error_log(String message, @NotNull Exception exception) {

        try {

            // Set up the File Output system.
            FileWriter file_writer = new FileWriter(error_log, true);
            BufferedWriter buffered_writer = new BufferedWriter(file_writer);
            PrintWriter print_writer = new PrintWriter(buffered_writer);

            // Define portions of the error log message.
            String time_stamp = LocalDate.now().toString() + "/" + LocalTime.now().toString();
            String stack_trace =
                    Thread.currentThread().getStackTrace()[2].getMethodName() + "()"
                            + "." +
                            Thread.currentThread().getStackTrace()[1].getMethodName() + "()"
                    ;

            // Output the error message and close the output streams.
            print_writer.println(
                    "|| " + time_stamp + " | " + stack_trace + " | " + exception.toString() + " | " + message + " ||"
            );
            print_writer.close();
            buffered_writer.close();
            file_writer.close();

        } catch (IOException e) { e.printStackTrace(); }

    }


    /* Getters */
    /**
     * Getter for "current_user".
     *
     * @return The active value of "current_user".
     */
    @Contract(pure = true)
    public User get_current_user() {
        return current_user;
    }


}
