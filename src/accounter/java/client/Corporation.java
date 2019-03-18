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
