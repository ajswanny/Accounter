/*
Copyright (c) 2019, Alexander Joseph Swanson Villares
alexanderjswanson@icloud.com | https://github.com/ajswanny
*/


/* Package */
package Accounter.java.window;


/* Imports */
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;


/**
 *
 */
public class ConfirmationWindow extends AccounterWindow {

    /* Class UI Elements */
    Button decision_confirmation = new Button();


    /* Constructors */
    public ConfirmationWindow() {

        start(primary_stage);

    }


    /* Methods */
    @Override
    public void start(Stage primary_stage) {

        // Define the primary Stage resizability.
        primary_stage.setResizable(false);

        // Define the primary Scene.
        primary_scene = new Scene(root_container, 200, 200);

        // Set the Scene.
        primary_stage.setScene(primary_scene);

        // Set the stage modality.
        primary_stage.initModality(Modality.APPLICATION_MODAL);

        // Show the stage.
        primary_stage.show();

    }

    /**
     *
     * @return
     */
    public boolean confirm_decision() {

        return false;

    }

}
