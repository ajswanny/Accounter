/*
 * Created by Alexander Swanson on 3/28/19 7:11 PM.
 * Email: alexanderjswanson@icloud.com.
 * Copyright © 2019. All rights reserved.
 */

package accounter.java;

import accounter.java.client.Client;
import org.jetbrains.annotations.Contract;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public class Appointment implements Serializable {

    /* Fields */
    private String name;
    private Client attendee;
    private LocalDate date;
    private LocalTime time;

    /* Constructors */
    @Contract(pure = true)
    public Appointment(String name) {
        this.name = name;
    }

    @Contract(pure = true)
    public Appointment(String name, Client attendee) {
        this(name);
        this.attendee = attendee;
    }

    /* Methods */
    @Override
    public String toString() {
        String s = name;
        if (date != null) { s += " " + date.toString(); }
        if (time != null) { s += " " + time.toString(); }
        return s;
    }

    /* Getters */
    public String getName() {
        return name;
    }

    /* Setters */
    public void setAttendee(Client attendee) {
        this.attendee = attendee;
    }


}
