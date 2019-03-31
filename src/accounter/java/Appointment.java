/*
 * Created by Alexander Swanson on 3/28/19 7:11 PM.
 * Email: alexanderjswanson@icloud.com.
 * Copyright © 2019. All rights reserved.
 */

package accounter.java;

import accounter.App;
import org.jetbrains.annotations.Contract;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public class Appointment implements Serializable {

    /* Fields */
    private String name;
    private LocalDate date;
    private LocalTime time;
    private App.TimePeriod timePeriod;

    /* Constructors */
    @Contract(pure = true)
    public Appointment(String name) {
        this.name = name;
    }

    @Contract(pure = true)
    public Appointment(String name, LocalDate date, LocalTime time, App.TimePeriod timePeriod) {
        this.name = name; this.date = date; this.time = time; this.timePeriod = timePeriod;
    }

    /* Methods */
    @Override
    public String toString() {
        String s = name;
        if (date != null) { s += " " + date.toString(); }
        if (time != null) { s += " " + time.toString(); if (timePeriod != null) {s += " " + timePeriod.toString(); } }
        return s;
    }

    /* Getters */
    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    /* Setters */
    public void setDate(LocalDate date) {
        this.date = date;
    }


}
