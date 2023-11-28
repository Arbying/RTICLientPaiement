package BeansModele;

import java.util.Vector;

public class ClientFactures {
    private ClientBean client;
    private Vector<FactureBean> factures;

    public ClientFactures() {
        // Constructeur par défaut
    }

    public ClientFactures(ClientBean client, Vector<FactureBean> factures) {
        this.client = client;
        this.factures = factures;
    }

    public ClientBean getClient() {
        return client;
    }

    public void setClient(ClientBean client) {
        this.client = client;
    }

    public Vector<FactureBean> getFactures() {
        return factures;
    }

    public void setFactures(Vector<FactureBean> factures) {
        this.factures = factures;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ClientFactures{");
        sb.append("client=");
        sb.append(client != null ? client.toString() : "null");
        sb.append(", factures=[");

        if (factures != null) {
            for (FactureBean facture : factures) {
                sb.append(facture.toString());
                sb.append(", ");
            }
            // Supprime la virgule en trop à la fin
            sb.delete(sb.length() - 2, sb.length());
        }

        sb.append("]}");

        return sb.toString();
    }
}
