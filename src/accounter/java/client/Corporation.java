/*
 * Created by Alexander Swanson on 4/14/19 10:55 PM.
 * Email: alexanderjswanson@icloud.com.
 * Copyright © 2019. All rights reserved.
 */

package accounter.java.client;

import java.util.ArrayList;

public class Corporation extends Client {

    /* Fields */
    private String name;

    /* Constructors */
    public Corporation(String name) {
        this.appointments = new ArrayList<>();
        this.name = name;
        sid = name;
    }

    /* Getters */
    public String getName() {
        return name;
    }

}
