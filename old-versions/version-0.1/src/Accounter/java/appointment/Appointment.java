/*
Copyright (c) 2019, Alexander Joseph Swanson Villares
alexanderjswanson@icloud.com | https://github.com/ajswanny
*/


package Accounter.java.appointment;


import Accounter.java.client.Client;
import Accounter.java.enums.AppointmentTimes;
import Accounter.java.simple_time.Time;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;


/**
 *
 */
public class Appointment implements Serializable {

    /* Class Attributes */
    /**
     *
     */
    private String name;

    /**
     *
     */
    private String about;

    /**
     *
     */
    private Calendar calendar;

    /**
     *
     */
    private Time start_time;

    /**
     *
     */
    private Time end_time;

    /**
     *
     */
    private ArrayList<Client> clients = new ArrayList<>();

    /**
     *
     */
    private AppointmentTimes defined_times;


    /* Constructors */
    /**
     *
     * @param name
     */
    public Appointment(String name) {

        // Call greater constructor.
        this(name, "", new GregorianCalendar(), new Time(), new Time(), new ArrayList<>());

        // Provide the defined times.
        defined_times = AppointmentTimes.NONE;

    }

    /**
     *
     * @param name
     * @param calendar
     */
    public Appointment(String name, Calendar calendar) {

        // Call the greater constructor.
        this(name, "", calendar, new Time(), new Time(), new ArrayList<>());

        // Provide the defined times.
        defined_times = AppointmentTimes.NONE;

    }

    /**
     *
     * @param name
     * @param calendar
     * @param start_time
     */
    public Appointment(String name, Calendar calendar, Time start_time) {

        // Call greater constructor.
        this(name, "", calendar, start_time, start_time, new ArrayList<>());

        // Provide the defined times.
        defined_times = AppointmentTimes.START_TIME;

    }

    /**
     *
     * @param name
     * @param calendar
     * @param start_time
     * @param end_time
     */
    public Appointment(String name, Calendar calendar, Time start_time, Time end_time) {

        // Call greater constructor.
        this(name, "", calendar, start_time, end_time, new ArrayList<>());

        // Provide the defined times.
        defined_times = AppointmentTimes.ALL;

    }

    /**
     *
     * @param name
     * @param calendar
     * @param start_time
     * @param end_time
     * @param client
     */
    public Appointment(String name, Calendar calendar, Time start_time, Time end_time, Client client) {

        // Define data fields.
        this.name = name;
        this.about = about;
        this.calendar = calendar;
        this.start_time = start_time;
        this.end_time = end_time;

        // Provide the defined times.
        defined_times = AppointmentTimes.ALL;

        // Define the list of Clients this Appointment corresponds to.
        clients.add(client);

    }

    /**
     *
     * @param name
     * @param about
     * @param calendar
     * @param start_time
     * @param end_time
     * @param clients
     */
    public Appointment(
            String name, String about,
            Calendar calendar,
            Time start_time, Time end_time,
            ArrayList<Client> clients) {

        // Define data fields.
        this.name = name;
        this.about = about;
        this.calendar = calendar;
        this.start_time = start_time;
        this.end_time = end_time;
        this.clients = clients;

        // Provide the defined times.
        defined_times = AppointmentTimes.ALL;

    }


    /* Getters */
    /**
     *
     * @return
     */
    public String get_name() {

        return name;

    }

    /**
     *
     * @return
     */
    public String get_about() {

        return about;

    }

    /**
     *
     * @return
     */
    public Calendar get_date() {

        return calendar;

    }

    /**
     *
     * @return
     */
    public Time get_start_time() {

        return start_time;

    }

    /**
     *
     * @return
     */
    public Time get_end_time() {

        return end_time;

    }

    /**
     *
     * @return
     */
    public ArrayList<Client> get_clients() {

        return clients;

    }

    /**
     *
     * @return
     */
    public AppointmentTimes get_defined_times() {

        return defined_times;

    }


    /* Setters */
    /**
     *
     * @param name
     */
    public void set_name(String name) {

        this.name = name;

    }

    /**
     *
     * @param about
     */
    public void set_about(String about) {

        this.about = about;

    }

    /**
     *
     * @param calendar
     */
    public void set_date(Calendar calendar) {

        this.calendar = calendar;

    }

    /**
     *
     * @param start_time
     */
    public void set_start_time(Time start_time) {

        this.start_time = start_time;

    }

    /**
     *
     * @param end_time
     */
    public void set_end_time(Time end_time) {

        this.end_time = end_time;

    }

    /**
     *
     * @param defined_time
     */
    private void set_defined_times(AppointmentTimes defined_time) {

        this.defined_times = defined_time;

    }

}
