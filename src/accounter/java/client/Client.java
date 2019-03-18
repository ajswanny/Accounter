package accounter.java.client;

import java.io.Serializable;

public abstract class Client implements Serializable {

    String sid;

    @Override
    public String toString() {
        return sid;
    }

}
