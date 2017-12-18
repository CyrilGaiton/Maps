package fr.masterdapm.ancyen.aardatabasemodel;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by cyril on 09/12/17.
 */

public class Server extends Activity {
    private int port = 5700;
    private Facade facade = new Facade(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ServerSocket serveur;

        try {
            serveur = new ServerSocket(port);
            System.out.println("fr.masterdapm.ancyen.aardatabasemodel.Server lancé sur le port: " + port);
            while (true) {
                Socket client = serveur.accept();
                System.out.println("Connexion acceptée: " + client.getRemoteSocketAddress());
                new MyConnexion(client, facade);
            }
        } catch (IOException e) {

            System.out.println("Erreur a la creation d'un objet socket : " + e.getMessage());
            System.exit(1);
        }


    }
}