/*
 * Created by Alexander Swanson on 4/14/19 10:55 PM.
 * Email: alexanderjswanson@icloud.com.
 * Copyright © 2019. All rights reserved.
 */

package accounter.java.client;

import java.util.ArrayList;

public class Individual extends Client {

    /* Fields */
    private String firstName;
    private String lastName;


    /* Constructors */
    public Individual(String firstName, String lastName) {
        this.appointments = new ArrayList<>();
        this.firstName = firstName;
        this.lastName = lastName;
        sid = firstName + " " + lastName;
    }

    /* Getters */
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

}
