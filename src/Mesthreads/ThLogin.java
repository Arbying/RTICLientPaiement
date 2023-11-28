package Mesthreads;

import Protocoles.*;
import BeansModele.EmployeBean;
import Ctrl.*;
import java.net.Socket;

public class ThLogin extends Thread {
    private String login;
    private String password;
    private EmployeBean employe;

    public ThLogin(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public EmployeBean getEmploye() {
        return employe;
    }

    @Override
    public void run() {
        // Identifiez la socket du contrôleur (singleton)
        Controleur controleur = Singleton.getInstance();
        Socket socket = controleur.getSocket();

        if (socket != null) {
            // Créez la requête de connexion
            String requete = "LOGIN#" + login + "#" + password;

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

                    employe = VESPAP.LOGIN(reponse,login,password);



                }
            }
        }

    }
}
