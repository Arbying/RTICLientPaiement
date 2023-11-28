package Mesthreads;

import BeansModele.ClientFactures;
import Protocoles.TCP;
import Ctrl.Controleur;
import Ctrl.Singleton;
import Protocoles.VESPAP;

import java.net.Socket;

public class ThGetFactures extends Thread {
    private String request;
    ClientFactures clientEtSesFactures = null;

    public ThGetFactures(String request) {
        this.request = request;
    }

    public ClientFactures getFactures() {
        return clientEtSesFactures;
    }



    @Override
    public void run() {
        // Identifiez la socket du contrôleur (singleton)
        Controleur controleur = Singleton.getInstance();
        Socket socket = controleur.getSocket();
        System.out.println("Je suis le thread getFactures et client.");

        if (socket != null) {
            try {
                // Créez la requête complète en concaténant "GETFACTURE#" avec la demande
                String fullRequest = "GETFACTURE#" + request;

                // Utilisez la fonction SEND du protocole TCP pour envoyer la requête complète
                int bytesSent = TCP.send(socket, fullRequest.getBytes(), fullRequest.length());

                if (bytesSent > 0) {
                    // Attendez la réponse
                    byte[] responseData = new byte[1500]; // Ajustez la taille du tableau selon vos besoins
                    int bytesRead = TCP.receive(socket, responseData);
                    System.out.println("J'ai envoyé la requête : " + fullRequest);

                    if (bytesRead > 0) {
                        // Convertissez les données de réponse en une chaîne de caractères
                        String response = new String(responseData, 0, bytesRead);

                        // Affichez la réponse à l'écran
                        System.out.println("Réponse du serveur : " + response);


                        clientEtSesFactures = VESPAP.MesFactures(response, request);
                    }
                }
            } catch (Exception e) {
                // Gérez les exceptions ici
                e.printStackTrace();
            }
        }
       // return 0;
    }
}
