/*
Copyright (c) 2019, Alexander Joseph Swanson Villares
alexanderjswanson@icloud.com | https://github.com/ajswanny
*/


package Accounter.java.window;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;


public abstract class AccounterWindow extends Application {
    // TODO: OPTIMIZE.


    /* Application UI Elements */
    protected Stage primary_stage = new Stage();
    protected BorderPane root_container = new BorderPane();
    protected Scene primary_scene = new Scene(new Pane());


    @Override
    public void start(Stage primary_stage) {

        // Define the primary Stage resizability.
        primary_stage.setResizable(false);

        // Define the primary Scene.
        primary_scene = new Scene(root_container, 600, 400);

        // Set the Scene.
        primary_stage.setScene(primary_scene);

        // Set the primary_stage title.
        primary_stage.setTitle("Title");

        // Set the stage modality.
        primary_stage.initModality(Modality.APPLICATION_MODAL);

        // Show the stage.
        primary_stage.show();

    }

}
