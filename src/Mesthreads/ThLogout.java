package Mesthreads;

import Protocoles.*;
import Ctrl.*;
import java.net.Socket;

public class ThLogout extends Thread {

    @Override
    public void run() {
        // Identifiez la socket du contrôleur (singleton)
        Controleur controleur = Singleton.getInstance();
        Socket socket = controleur.getSocket();

        if (socket != null) {
            // Créez la requête de déconnexion
            String requete = "LOGOUT";

            // Utilisez la fonction SEND du protocole TCP pour envoyer la requête
            int bytesSent = TCP.send(socket, requete.getBytes(), requete.length());

            if (bytesSent > 0) {
                // Le thread se termine après l'envoi de la requête LOGOUT
                System.out.println("Déconnexion réussie");
             //   return bytesSent;
            }
        }

        System.out.println("Échec de la déconnexion");
      //  return 0;
    }
}
