/*
 * Created by Alexander Swanson on 3/28/19 7:09 PM.
 * Email: alexanderjswanson@icloud.com.
 * Copyright © 2019. All rights reserved.
 */

package accounter.controller;

import java.net.URL;
import java.util.ResourceBundle;

public class ApplicationSettingsController extends FXMLController {

    public ApplicationSettingsController() {
        System.out.println("Initialized instance of ApplicationSettingsController.");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initCoreResources();
    }

}
