/*
 * Created by Alexander Swanson on 3/28/19 7:10 PM.
 * Email: alexanderjswanson@icloud.com.
 * Copyright © 2019. All rights reserved.
 */

package accounter.controller;

import accounter.App;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public abstract class FXMLController implements Initializable {

    @FXML
    public AnchorPane root;

    Scene scene;

    protected App instance;

    protected void initCoreResources() {
        instance = App.getInstance();
        scene = new Scene(root);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initCoreResources();
    }

    public Scene getScene() {
        return scene;
    }

}
