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
