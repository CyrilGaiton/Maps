package fr.masterdapm.ancyen.model;

import java.io.Serializable;

/**
 * Created by cyril on 25/11/17.
 */

public class User implements Serializable{
    private final int id;
    private String email;
    private String password;
    private String lastName;
    private String firstName;

    public User(int id, String email, String password, String lastname, String firstname) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.lastName = lastname;
        this.firstName = firstname;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }
}
