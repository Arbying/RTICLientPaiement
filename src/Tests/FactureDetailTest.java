package Tests;

import BeansModele.ArticleBean;
import BeansModele.ClientBean;
import BeansModele.FactureBean;
import BeansModele.FactureDetail;
import BeansModele.VenteBean;
import java.util.ArrayList;
import java.util.List;

public class FactureDetailTest {

    public static void main(String[] args) {
        // Initialisation des objets pour le test
        FactureBean facture = new FactureBean(1, new ClientBean(1, "ClientNom", "mdp123"), 100.0f, true, "2023-01-01");
        List<VenteBean> details = new ArrayList<>();
        details.add(new VenteBean(facture, new ArticleBean(1, "Article1", 10.0f, 5, "image1.jpg"), 2));
        details.add(new VenteBean(facture, new ArticleBean(2, "Article2", 20.0f, 10, "image2.jpg"), 3));

        FactureDetail factureDetail = new FactureDetail(facture, details);

        // Test de la méthode toPrint
        System.out.println("\nTest de la méthode toPrint()");
        String printedFacture = factureDetail.toPrint();
        if (printedFacture != null && !printedFacture.isEmpty()) {
            System.out.println("Résultat de toPrint() : \n" + printedFacture);
        } else {
            System.out.println("Échec : la sortie de toPrint() est vide ou null.");
        }
    }
}
