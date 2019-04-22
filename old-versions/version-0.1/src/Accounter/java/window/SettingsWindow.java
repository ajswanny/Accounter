/*
Copyright (c) 2019, Alexander Joseph Swanson Villares
alexanderjswanson@icloud.com | https://github.com/ajswanny
*/


/* Package */
package Accounter.java.window;


/* Imports */
import Accounter.java.Accounter;
import Accounter.java.user.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;


/**
 *
 */
public class SettingsWindow extends AccounterWindow {

    /* Class Attributes */
    /**
     *
     */
    private Accounter application;

    /**
     *
     */
    private User current_user;

    /* Class UI Elements */
    /**
     *
     */
    private HBox root_center__default__hbox;

    /**
     *
     */
    private GridPane root_center__profile__gp;

    /**
     *
     */
    private GridPane root_left__gp;


    public SettingsWindow(@NotNull Accounter application) {

        // Define values.
        current_user = application.get_current_user();

        // Start the UI.
        start(this.primary_stage);

    }


    /**
     * Default Constructor to create a new SettingsWindow.
     */
    @Override
    public void start(Stage primary_stage) {

        // Define the root container.
        this.root_container = new BorderPane();

        // Define the GP containing all buttons.
        this.root_left__gp = new GridPane();


        // Define the primary stage.
        this.primary_stage = new Stage();

        // Define the primary stage resizability.
        primary_stage.setResizable(false);


        // Define the root container.
        init_root_container();

        // Define the primary Scene.
        this.primary_scene = new Scene(root_container, 600, 400);


        // Define the Scene stylesheet.
        primary_scene.getStylesheets().add(
                SettingsWindow.class.getResource("../../css/settings_window/main.css").toExternalForm()
        );


        // Set the Scene.
        primary_stage.setScene(primary_scene);

        // Define the title of the window.
        primary_stage.setTitle("SettingsWindow");

        // Set stage modality.
        primary_stage.initModality(Modality.APPLICATION_MODAL);

        // Show the stage.
        primary_stage.show();

    }

    private void init_root_container() {

        // Set the LEFT of the BorderPane.
        init_root_left();
        root_container.setLeft(root_left__gp);

        // Set the CENTER of the BorderPane.
        init_root_center();
        root_container.setCenter(root_center__default__hbox);

    }

    private void init_root_center() {

        // Define the container and its alignment.
        root_center__default__hbox = new HBox();
        root_center__default__hbox.setAlignment(Pos.CENTER);

        // Define the CSS hook.
        root_center__default__hbox.getStyleClass().add("root_center__default__hbox");

        // Define the default label.
        Label accounter = new Label("Accounter version 0.1");
        accounter.setFont(new Font("Arial", 14));
        accounter.setAlignment(Pos.CENTER);

        // Add children to 'root_center__default__hbox'.
        root_center__default__hbox.getChildren().add(accounter);


        // Define the alternative center views.
        init_root_center__PROFILE();

    }

    private void init_root_left() {

        // Define the parameters for the GridPane.
        root_left__gp.setHgap(10);
        // TODO: Determine official vertical gap.
        root_left__gp.setVgap(5);
        // TODO: Determine official Insets.
        root_left__gp.setPadding(new Insets(0, 0, 0, 0));

        // Define the container CSS hook.
        root_left__gp.getStyleClass().add("root_left__gp");

        // $DEVELOPMENT
        root_left__gp.setGridLinesVisible(true);


        // Define the "About" settings button.
        Button about = new Button("About");
        about.setOnAction( action_event -> root_container.setCenter(root_center__default__hbox));
        root_left__gp.add(about, 0, 0, 1, 1);

        Button profile = new Button("Profile");
        profile.setOnAction( action_event -> root_container.setCenter(root_center__profile__gp));
        root_left__gp.add(profile, 0, 1, 1, 1);


        // Define a collection of all the buttons in 'init_root_left'.
        Button[] buttons = new Button[] {about, profile};


        // Apply common customizations to the buttons.
        for (Button button : buttons) {

            // Set the width of every button to the preferred width of the widest button.
            button.setMaxWidth(Double.MAX_VALUE);

        }


    }

    /**
     * Defines the display for user profile information and settings.
     */
    private void init_root_center__PROFILE() throws NullPointerException {

        // Define the container for the settings option and set the alignment.
        this.root_center__profile__gp = new GridPane();
        root_center__profile__gp.setAlignment(Pos.CENTER);
        root_center__profile__gp.setHgap(10);


        /* Define labels indicating the current user. */
        Label who_is_current_user = new Label("Current user:");
        root_center__profile__gp.add(who_is_current_user, 0, 0);

        try {

            Label current_user = new Label(this.current_user.get_username());
            root_center__profile__gp.add(current_user, 1, 0);

        } catch (NullPointerException e) {

            System.out.println("No User registered for application instance.");
            e.printStackTrace();

        }

    }


}
