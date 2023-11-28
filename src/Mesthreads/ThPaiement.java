package Mesthreads;

import Protocoles.*;
import Ctrl.*;
import java.net.Socket;

public class ThPaiement extends Thread {
    private String fac;
    private String nom;
    private String carte;
    private int estPaye;

    public ThPaiement(String fac, String nom, String carte) {
        this.fac = fac;
        this.nom = nom;
        this.carte = carte;
    }

    public int getEstPaye() {
        return estPaye;
    }

    @Override
    public void run() {
        // Identifiez la socket du contrôleur (singleton)
        Controleur controleur = Singleton.getInstance();
        Socket socket = controleur.getSocket();

        if (socket != null) {
            // Créez la requête de paiement
            String requete = "PAYFAC#" + fac + "#" + nom + "#" + carte;

            // Utilisez la fonction SEND du protocole TCP pour envoyer la requête
            int bytesSent = TCP.send(socket, requete.getBytes(), requete.length());

            if (bytesSent > 0) {
                // Attendez la réponse
                byte[] reponseData = new byte[1500]; // Ajustez la taille du tableau selon vos besoins
                int bytesRead = TCP.receive(socket, reponseData);

                if (bytesRead > 0) {
                    // Convertissez les données de réponse en une chaîne de caractères
                    String reponse = new String(reponseData, 0, bytesRead);

                    // Affichez la réponse à l'écran
                    System.out.println("Réponse du serveur : " + reponse);
                    estPaye = VESPAP.statusPaiement(reponse);
                }
            }
        }

        // Le thread se termine, retourne 1

    }
}
