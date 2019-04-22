/*
Copyright (c) 2019, Alexander Joseph Swanson Villares
alexanderjswanson@icloud.com | https://github.com/ajswanny
*/


/* Package */
package Accounter.java.calendar_gridpane;


/* Imports */
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.*;


public class WeekGridPane extends GridPane implements Serializable {

    /* Attributes */
    private ArrayList<DayGridPane> daygridpanes;

    /**
     * The amount of empty DayGridPanes
     */
    private int empty_daygridpanes = 0;

    /* Constructors */
    public WeekGridPane(@NotNull ArrayList<DayGridPane> daygridpanes) {

        // Call parent constructor.
        super();

        // Create a reference to the DayGirdPanes.
        this.daygridpanes = new ArrayList<>(daygridpanes);

        // The preferred width and height values for DayGridPanes.
        double PREF_DGP_WIDTH = 151.4285714286;
        double PREF_DGP_HEIGHT = 718;

        for (int i = 0; i < daygridpanes.size(); i++) {

            // Get the DayGridPane for the day of the week "i".
            DayGridPane daygridpane = daygridpanes.get(i);

            // Adjust the DayGridPane's properties.
//            daygridpane.setPrefWidth(PREF_DGP_WIDTH);
//            daygridpane.setPrefHeight(PREF_DGP_HEIGHT);

            // Initialize the ScrollPane to contain the DayGridPane.
            ScrollPane daygridpane_sp = new ScrollPane(daygridpane);

            // Adjust ScrollPane properties.
            daygridpane_sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            daygridpane_sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            daygridpane_sp.getStyleClass().clear();

            // Add the ScrollPane to this WeekGridPane.
            this.add(daygridpane_sp, i, 0);

            // Keep track of amount of empty DayGridPanes
            if (daygridpane.get_date_day() == 0) { empty_daygridpanes++; }

        }

    }


    /* Setters */
    public int get_empty_daygridpanes() {

        return empty_daygridpanes;

    }

}
