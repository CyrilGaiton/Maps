package fr.masterdapm.ancyen.aardatabasemodel;

import android.app.Activity;

import java.io.IOException;
import java.io.ObjectInputStream;

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
        try {
            Ride ride = (Ride) ois.readObject();
            rideDAO.add(ride);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        rideDAO.close();
    }
}
