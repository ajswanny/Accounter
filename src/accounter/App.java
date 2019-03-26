//  Created by Alexander Swanson on 3/14/19.
//  Email: alexanderjswanson@icloud.com.
//  Copyright © 2019. All rights reserved.


package accounter;

import accounter.controller.CalendarController;
import accounter.controller.FXMLController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.jetbrains.annotations.Contract;

import java.io.IOException;

public class App extends Application {

    /* Fields */
    public enum ApplicationScene {
        SETTINGS_SCENE
    }

    private Stage primaryStage, applicationSettingsStage;

    private Scene applicationSettingsScene;

    // Controllers
    private FXMLController calendarController, applicationSettingsController;

    // Application instance
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
        loadFxmlControllers();
        loadAltStages();

        // Setup Stage
        primaryStage = new Stage();
        primaryStage.setScene(calendarController.getScene());
        primaryStage.show();

    }

    @Override
    public void stop() {

        System.out.println("Closing application.");

    }

    private void loadFxmlControllers() throws IOException {
        calendarController = loadFxmlController("fxml/CalendarController.fxml");
        applicationSettingsController = loadFxmlController("fxml/ApplicationSettingsController.fxml");
    }

    private FXMLController loadFxmlController(String controllerFxmlFilePath) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(controllerFxmlFilePath));
        fxmlLoader.load();
        return fxmlLoader.getController();

    }

    private void loadAltStages() {

        // ApplicationSettings
        applicationSettingsStage = new Stage(StageStyle.UNIFIED);
        applicationSettingsStage.initModality(Modality.APPLICATION_MODAL);
        applicationSettingsStage.setOnCloseRequest(event -> closeApplicationSettings());
        applicationSettingsScene = applicationSettingsController.getScene();
        applicationSettingsStage.setScene(applicationSettingsScene);

    }

    public void requestApplicationClose() {
        Platform.exit();
    }

    public void requestNewSceneToDisplay(ApplicationScene applicationScene) {

        switch (applicationScene) {

            case SETTINGS_SCENE:
                displayApplicationSettings();
                break;
        }

    }

    private void displayApplicationSettings() {
        applicationSettingsStage.show();
        System.out.println("Opening ApplicationSettings Stage.");
    }

    private void closeApplicationSettings() {
        applicationSettingsStage.close();
        System.out.println("Closing ApplicationSettings Stage.");
    }

    /* Getters */
    @Contract(pure = true)
    public static App getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        launch(args);
    }

}
