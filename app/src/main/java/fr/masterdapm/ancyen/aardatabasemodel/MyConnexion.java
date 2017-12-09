package fr.masterdapm.ancyen.aardatabasemodel;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by cyril on 09/12/17.
 */

public class MyConnexion {
    private Socket client; //Liaison avec le client
    private Facade facade;

    public MyConnexion(Socket client, Facade facade) {

        this.client = client;
        this.facade = facade;

        new Thread().start();
    }


    public void run() {

        boolean running = true;
        ObjectInputStream ois = null;
        ObjectOutputStream oos = null;
        try {
            ois = new ObjectInputStream(client.getInputStream());
            oos = new ObjectOutputStream(client.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (running) {


            // on switch en fonction du premier string
            String s = null;
            try {
                s = (String) ois.readObject();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            if (s.equals("addUser")) {
                facade.addUser(ois);
            } else if (s.equals("getUser")) {
                facade.getUser(ois, oos);
            } else if (s.equals("addRide")) {
                facade.addRide(ois);
            } else if (s.equals("getRidesWithEmail")) {
                facade.getRidesWithEmail(ois, oos);
            } else if (s.equals("close")) {
                running = false;
            }
        }

        stop();
    }

    public void stop() {


        try {
            System.out.println("Connexion fermée: " + client.getLocalAddress());
            client.close();
        } catch (IOException e) {
            System.out.println("Exception a la fermeture d'une connexion"+e);
        }

    }
}
