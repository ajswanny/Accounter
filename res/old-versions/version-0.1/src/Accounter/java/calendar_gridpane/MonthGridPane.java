/*
Copyright (c) 2019, Alexander Joseph Swanson Villares
alexanderjswanson@icloud.com | https://github.com/ajswanny
*/


/* Package */
package Accounter.java.calendar_gridpane;


/* Imports */
import Accounter.fxml.calendar.monthly.CalendarViewController;
import Accounter.java.client.Client;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.*;
import java.util.List;


public class MonthGridPane extends GridPane implements Serializable {


    /* Class Attributes */
    /**
     * The integer value of the month this MonthGridPane represents.
     */
    private int month_value;

    /**
     * List containing all DayGridPanes for this MonthGridPane.
     */
    private List<DayGridPane> daygridpanes;

    /* Class UI Elements */
    /**
     * The HBoxes that contain DayGridPanes for every week of the current month_value.
     */
    private HBox rc__w1__hb = new HBox();
    private HBox rc__w2__hb = new HBox();
    private HBox rc__w3__hb = new HBox();
    private HBox rc__w4__hb = new HBox();
    private HBox rc__w5__hb = new HBox();
    private HBox rc__w6__hb = new HBox();

    /**
     * The container for all Week-HBoxes.
     */
    private HBox[] rc__week__hbs = {rc__w1__hb, rc__w2__hb, rc__w3__hb, rc__w4__hb, rc__w5__hb, rc__w6__hb};


    /* Constructor */
    public MonthGridPane(int month_value, ArrayList<DayGridPane> daygridpanes) {

        // Call the parent constructor to init the GridPane.
        super();

        // Define and initialize given class attributes.
        this.month_value = month_value;
        this.daygridpanes = daygridpanes;

        // Define the HBoxes to describe the weeks of the current month_value.
        def_week_hboxes();

        // $DEVELOPMENT
        $DEV_SCRATCH_WORK();

    }


    /* Methods */
    /**
     * FOR DEVELOPMENT.
     */
    private void $DEV_SCRATCH_WORK() {

    }

    /**
     * Defines the HBoxes to describe the weeks of the current month_value, containing the DayGridPanes respective to the week
     * each represents.
     */
    private void def_week_hboxes() {

        // The preferred width value.
        double PREF_DGP_WIDTH = 151.4285714286;

        // The preferred height value.
        double PREF_DGP_HEIGHT = 119.6666666667;

        // Define integer for tracking of week and day.
        int weekday_counter = 7;
        int weekday_limiter = 1;
        int row_index = 0;

        // Initialize the DayGridPanes for every week of the current month_value.
        for (HBox hbox : rc__week__hbs) {

            // Define the DayGridPanes for every day of the current week.
            for (int i = weekday_counter - 7; weekday_limiter < 8 && i < 42; i++) {

                // Fetch the appropriate DayGridPane and set its preferred dimensions.
                DayGridPane dgp = daygridpanes.get(i);
                dgp.setPrefWidth(PREF_DGP_WIDTH);
                dgp.setPrefHeight(PREF_DGP_HEIGHT);

                // Define the ScrollPane to contain the DayGridPane and add it to the HBox.
                ScrollPane dgp_sp = new ScrollPane(dgp);
                dgp_sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
                dgp_sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
                dgp_sp.getStyleClass().clear();

                hbox.getChildren().add(dgp_sp);

                //
                weekday_limiter++;

            }

            // Add the HBox containing the days of every week to this GridPane.
            this.add(hbox, 0, row_index);

            // Increment the weekday tracker to begin process for the next week of the month_value.
            weekday_counter += 7;
            weekday_limiter = 1;
            row_index++;

        }

    }


    /* Getters */
    /**
     * Gets this MonthGridPane's month-value.
     *
     * @return The variable: "month_value".
     */
    public int get_month_value() {

        return month_value;

    }

}
