package fr.masterdapm.ancyen.aardatabasemodel;

import android.app.Activity;
import android.database.Cursor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import fr.masterdapm.ancyen.dao.RideDAO;
import fr.masterdapm.ancyen.dao.UserDAO;
import fr.masterdapm.ancyen.model.Ride;
import fr.masterdapm.ancyen.model.User;

/**
 * Created by cyril on 09/12/17.
 */

public class Facade extends Activity{
    private UserDAO userDAO = new UserDAO(this);
    private RideDAO rideDAO = new RideDAO(this);

    public void addUser(ObjectInputStream ois){
        userDAO.open();
        try {
            User user = (User) ois.readObject();
            userDAO.add(user);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        userDAO.close();
    }

    public void addRide(ObjectInputStream ois){
        rideDAO.open();
        Ride ride = null;
        try {
            ride = (Ride) ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        rideDAO.add(ride);
        rideDAO.close();
    }
    
    public void getRidesWithEmail(ObjectInputStream ois, ObjectOutputStream oos){
        rideDAO.open();
        String email = null;
        try {
            email = (String) ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Ride[] rides = rideDAO.getWithEmail(email);
        for (Ride r:rides
             ) {
            try {
                oos.writeObject(r);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        rideDAO.close();
    }

    public void getUser(ObjectInputStream ois, ObjectOutputStream oos){
        userDAO.open();
        String email = null;
        try {
            email = (String) ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        User user = userDAO.getWithEmail(email);
        try {
        if (user != null) {
                oos.writeObject("OK");
                oos.writeObject(user);
        }
        else {
            oos.writeObject("KO");
        }
        } catch (IOException e) {
            e.printStackTrace();
        }
        userDAO.close();
    }

}
