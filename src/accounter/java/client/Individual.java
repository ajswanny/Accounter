/*
 * Created by Alexander Swanson on 3/28/19 7:11 PM.
 * Email: alexanderjswanson@icloud.com.
 * Copyright © 2019. All rights reserved.
 */

package accounter.java.client;

public class Individual extends Client {

    /* Fields */
    private String firstName;
    private String lastName;


    /* Constructors */
    public Individual(String firstName, String lastName) {

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
