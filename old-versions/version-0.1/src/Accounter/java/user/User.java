/*
Copyright (c) 2019, Alexander Joseph Swanson Villares
alexanderjswanson@icloud.com | https://github.com/ajswanny
*/

package Accounter.java.user;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Objects;


@SuppressWarnings("Duplicates")
public class User implements java.io.Serializable {
    // TODO: OPTIMIZE.

    /* Attributes */
    // The User's username.
    private String username;

    // The User's password.
    private String password;


    /* Constructors */
    public User() {

        // Set default values.
        username = null;
        password = null;

    }

    public User(String username, String password) {

        this.username = username;
        this.password = password;

    }


    public String get_username() {

        return username;

    }


    /* Methods */
    public void serialize(String file_path, Boolean verbose) throws IOException {

        try {

            // Define the File IO object.
            FileOutputStream file_out = new FileOutputStream(file_path);

            // Define the Object IO object.
            ObjectOutputStream object_out = new ObjectOutputStream(file_out);

            // Serialize 'this' to the current defined File object.
            object_out.writeObject(this);

            // Close the File and Object streams.
            file_out.close();
            object_out.close();

            // Output status.
            if (verbose) { System.out.println("Serialized object to: " + file_path); }

        } catch (IOException e) {

            // Catch and IO exception.
            e.printStackTrace();

        }

    }

    public Boolean verify_credentials(String username, String password) {

        // TODO: Enhance this system (implement custom exceptions, implement cryptography, etc.).
        // Test if username and password are correct.
        return (
                (Objects.equals(username, this.username)) & (Objects.equals(password, this.password))
        );

    }

}
