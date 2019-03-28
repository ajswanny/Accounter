/*
 * Created by Alexander Swanson on 3/28/19 7:10 PM.
 * Email: alexanderjswanson@icloud.com.
 * Copyright © 2019. All rights reserved.
 */

package accounter.java.client;

public class Corporation extends Client {

    /* Fields */
    private String name;

    /* Constructors */
    public Corporation(String name) {

        this.name = name;
        sid = name;

    }

    /* Getters */
    public String getName() {
        return name;
    }

}
