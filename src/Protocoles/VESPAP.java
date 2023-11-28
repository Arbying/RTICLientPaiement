package Protocoles;

import BeansModele.*;

import java.sql.SQLException;
import java.util.Vector;


public class VESPAP {

    public static EmployeBean LOGIN(String reponse, String login, String password) {
        EmployeBean employe = new EmployeBean();

        if (reponse.equals("LOGIN2#OK")) {
            // Créer un employé avec les informations fournies
            employe.setIdEmploye(1);
            employe.setNom(login);
            employe.setMdp(password);
            employe.setActif("Oui");
        } else {
            // Créer un employé vide
            employe.setIdEmploye(0);
            employe.setNom("");
            employe.setMdp("");
            employe.setActif("");
        }
        return employe;
    }


    public static ClientFactures MesFactures(String reponse, String idClient) {
        // Vérifier si la réponse est une erreur
        if (reponse.startsWith("ERREUR")) {
            // Retourner un objet ClientFactures vide
            return new ClientFactures(null, new Vector<>());
        }

        // Extraction du nom du client depuis la réponse
        String[] elements = reponse.split("#");
        String nomClient = elements[elements.length - 1];

        // Création de l'objet ClientBean avec l'ID et le nom
        ClientBean client = new ClientBean(Integer.parseInt(idClient), nomClient, null);

        // Extraction du nombre de factures depuis la réponse
        int nombreFactures = Integer.parseInt(elements[1]);

        // Création et remplissage du vecteur de FactureBean
        Vector<FactureBean> factures = new Vector<>();
        int currentIndex = 2; // Index de départ pour les détails des factures

        for (int i = 0; i < nombreFactures; i++) {
            int idFacture = Integer.parseInt(elements[currentIndex]);
            float montant = Float.parseFloat(elements[currentIndex + 1]); // Changement ici
            boolean paye = Integer.parseInt(elements[currentIndex + 2]) == 1; // Changement ici
            String date = elements[currentIndex + 3]; // Changement ici

            FactureBean facture = new FactureBean(idFacture, null, montant, paye, date);
            factures.add(facture);

            currentIndex += 4; // Changement ici
        }

        // Création de l'objet ClientFactures
        return new ClientFactures(client, factures);
    }

    public static int statusPaiement(String reponse) {
        if (reponse.endsWith("OK")) {
            return 1;
        } else {
            return 0;
        }
    }


}



