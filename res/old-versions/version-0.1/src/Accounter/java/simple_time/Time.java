/*
Copyright (c) 2019, Alexander Joseph Swanson Villares
alexanderjswanson@icloud.com | https://github.com/ajswanny
*/

/* Package */
package Accounter.java.simple_time;


/* Imports */
import java.io.Serializable;


/**
 * A simple class to record a specific instance of time.
 */
public class Time implements Serializable, Comparable<Time> {


    /* Class Attributes */
    /**
     *
     */
    private int hour;

    /**
     *
     */
    private int minute;

    /**
     *
     */
    private Period time_period;

    /**
     *
     */
    public enum Period {
        AM,
        PM
    }


    /* Enums */
    /**
     *
     */
    public Time() {

        //
        hour = 8;
        minute = 0;
        time_period = Period.AM;

    }


    /* Constructors */
    /**
     *
     * @param hour
     * @param minute
     * @param time_period
     */
    public Time(int hour, int minute, Period time_period) {

        //
        this.hour = hour;
        this.minute = minute;
        this.time_period = time_period;

    }


    /* Methods */
    /**
     *
     * @return
     */
    @Override
    public String toString() {

        return String.valueOf(hour) + ":" + format_time(minute) + " " + time_period.toString();

    }

    /**
     *
     * @param time
     * @return
     */
    private static String format_time(String time) {

        // Determine if the String is of length 1. If it is, add a '0' to the beginning.
        if (time.length() == 1) {

            return "0" + time;

        } else {

            return time;

        }

    }

    /**
     *
     * @param time
     * @return
     */
    private static String format_time(int time) {

        // Convert the integer to a String.
        String value = String.valueOf(time);

        // Format the time String.
        return format_time(value);

    }

    /**
     * Compares this Time to another Time.
     * @param other
     * @return
     */
    @Override
    public int compareTo(Time other) {

        int comparison = Integer.compare(hour, other.hour);

        if (comparison == 0) {
            comparison = Integer.compare(minute, other.minute);
        }

        return comparison;

    }


    /* Getters */
    public int get_hour() {

        return hour;

    }

    public int get_minute() {

        return minute;

    }

    public Period get_time_period() {

        return time_period;

    }


    /* Setters */
    public void set_hour(int hour) {

        this.hour = hour;

    }

    public void set_minute(int minute) {

        this.minute = minute;

    }

    public void set_time_period(Period time_period) {

        this.time_period = time_period;

    }


}
