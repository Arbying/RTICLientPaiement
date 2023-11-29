package BeansModele;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class FactureDetail {
    private FactureBean facture;
    private List<VenteBean> details;

    // Constructeur par défaut
    public FactureDetail() {
    }

    // Constructeur d'initialisation
    public FactureDetail(FactureBean facture, List<VenteBean> details) {
        this.facture = facture;
        this.details = details;
    }

    // Getters et setters
    public FactureBean getFacture() {
        return facture;
    }

    public void setFacture(FactureBean facture) {
        this.facture = facture;
    }

    public List<VenteBean> getDetails() {
        return details;
    }

    public void setDetails(List<VenteBean> details) {
        this.details = details;
    }

    // Méthode toPrint pour afficher une facture formatée
    public String toPrint() {
        StringBuilder sb = new StringBuilder();
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");

        // En-tête
        sb.append("Sebarby légumier\nRue Feronstrée 148\n4000 Liege\n\n");

        // Informations de la facture
        try {
            Date dateFacture = inputFormat.parse(facture.getDate());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateFacture);
            calendar.add(Calendar.DAY_OF_MONTH, 15);
            Date dateEcheance = calendar.getTime();

            // Affichage du nom du client
            String nomClient = facture.getClient() != null ? facture.getClient().getNom() : "Inconnu";
            sb.append("Client: ").append(nomClient).append("\n");

            sb.append("Numéro de facture: ").append(facture.getId()).append("\n");
            sb.append("Date: ").append(outputFormat.format(dateFacture)).append("\n");
            sb.append("Date d'échéance: ").append(outputFormat.format(dateEcheance)).append("\n");
        } catch (ParseException e) {
            sb.append("Erreur de format de date\n");
        }

        sb.append("\nFacture pour ce qui suit :\n");
        sb.append(ArticleBean.toStringTitres()).append("\n");
        for (VenteBean detail : details) {
            sb.append(detail.getArticle().toStringLigne()).append("\n");
        }

        // Calculs financiers et affichage du tableau financier
        float montantHTVA = facture.getMontant() / 1.06f;
        float tva = facture.getMontant() - montantHTVA;
        sb.append(formatTableauFinancier(montantHTVA, tva, facture.getMontant()));

        // Statut de paiement
        if (facture.isPaye()) {
            sb.append("\nCette facture a été payée.\n");
        } else {
            sb.append("\nCette facture n'a pas été payée.\n");
        }

        return sb.toString();
    }

    // Méthode pour formater le tableau financier
    private String formatTableauFinancier(float montantHTVA, float tva, float total) {
        StringBuilder sb = new StringBuilder();

        // Définition de la largeur maximale pour les libellés et les montants
        int largeurLibelle = 15; // Pour "Montant HTVA", "TVA", "Total"
        int largeurMontant = 10; // Pour les montants (max 9999,99)

        // Ajout de chaque ligne dans le tableau
        sb.append(String.format("%-" + largeurLibelle + "s: %-"
                + largeurMontant + ".2f\n", "Montant HTVA", montantHTVA));
        sb.append(String.format("%-" + largeurLibelle + "s: %-"
                + largeurMontant + ".2f\n", "TVA", tva));
        sb.append(String.format("%-" + largeurLibelle + "s: %-"
                + largeurMontant + ".2f\n", "Total", total));

        return sb.toString();
    }
}
