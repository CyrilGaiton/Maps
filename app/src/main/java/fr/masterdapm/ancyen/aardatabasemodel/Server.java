package fr.masterdapm.ancyen.aardatabasemodel;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by cyril on 09/12/17.
 */

public class Server {
    private static int port = 5700;
    private static Facade facade = new Facade();


    public static void main(String[] args) {

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
