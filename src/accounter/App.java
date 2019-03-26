//  Created by Alexander Swanson on 3/14/19.
//  Email: alexanderjswanson@icloud.com.
//  Copyright © 2019. All rights reserved.


package accounter;

import accounter.controller.CalendarController;
import accounter.controller.FXMLController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    /* Fields */
    private Stage primaryStage;

    // Controllers
    private CalendarController calendarController;

    @Override
    public void start(Stage primaryStage) throws Exception {

        // Load resources
        loadFxmlControllers();

        // Setup Stage
        primaryStage = new Stage();
        primaryStage.setScene(calendarController.getScene());
        primaryStage.show();

    }

    private void loadFxmlControllers() throws IOException  {
        calendarController = (CalendarController) loadFxmlController("fxml/CalendarController.fxml");
    }

    @SuppressWarnings("SameParameterValue")
    private FXMLController loadFxmlController(String controllerFxmlFilePath) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(controllerFxmlFilePath));
        fxmlLoader.load();
        return fxmlLoader.getController();

    }

    public static void main(String[] args) {
        launch(args);
    }

}
