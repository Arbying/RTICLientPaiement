package Ctrl;

public class Singleton {
    private static Controleur instance;

    public static synchronized Controleur getInstance() {
        if (instance == null) {
            instance = new Controleur();
        }
        return instance;
    }
}
