package BeansModele;

public class ArticleBean {
    private int id;
    private String intitule;
    private float prix;
    private int stock;
    private String image;

    // Constructeur par défaut
    public ArticleBean() {
    }

    // Constructeur d'initialisation
    public ArticleBean(int id, String intitule, float prix, int stock, String image) {
        this.id = id;
        this.intitule = intitule;
        this.prix = prix;
        this.stock = stock;
        this.image = image;
    }

    // Getters et setters pour les propriétés

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    // Méthode toString pour afficher l'objet
    @Override
    public String toString() {
        return "ArticleBean{" +
                "id=" + id +
                ", intitule='" + intitule + '\'' +
                ", prix=" + prix +
                ", stock=" + stock +
                ", image='" + image + '\'' +
                '}';
    }

    // Méthode pour afficher les titres des colonnes
    public static String toStringTitres() {
        StringBuilder sb = new StringBuilder();

        // Ligne de 50 caractères '*'
        sb.append("*".repeat(50)).append("\n");

        // Ligne avec les titres, séparés par des '|'
        sb.append("|").append(String.format("%-5s", "ID"));
        sb.append("|").append(String.format("%-20s", "Intitule"));
        sb.append("|").append(String.format("%-10s", "Prix"));
        sb.append("|").append(String.format("%-10s", "Stock")).append("|").append("\n");

        // Ligne de 50 caractères '*'
        sb.append("*".repeat(50));

        return sb.toString();
    }

    // Méthode pour afficher les valeurs de l'objet sous forme de ligne
    public String toStringLigne() {
        StringBuilder sb = new StringBuilder();

        // Ajout des données de l'article avec des séparateurs '|'
        sb.append("|").append(String.format("%-5d", id));
        sb.append("|").append(String.format("%-20s", intitule));
        sb.append("|").append(String.format("%-10.2f", prix));
        sb.append("|").append(String.format("%-10d", stock)).append("|").append("\n");

        // Ajout d'une ligne de séparation de 50 caractères '-'
        sb.append("-".repeat(50));

        return sb.toString();
    }
}