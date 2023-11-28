package Ctrl;

import GUI.VuePrincipale;
import Protocoles.TCP;
import BeansModele.*;
import Mesthreads.*;

import java.awt.*;
import java.net.Socket;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Vector;


public class Controleur {
    private VuePrincipale vue;
    private VuePrincipale.InvoiceTableCellRenderer renderer;

    private Socket socket; // Ajoutez une référence à la socket ici

    public Controleur() {
        // Constructeur sans paramètre

        // Établir la connexion avec le serveur

    }

    public void setVue(VuePrincipale vue) {
        this.vue = vue;
        renderer = vue.new InvoiceTableCellRenderer();

        vue.addButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtenez le composant source de l'événement (le bouton cliqué)
                Object source = e.getSource();

                if (source == vue.loginButton) {
                    socket = TCP.createClientSocket("192.168.100.2", 55407);
                    if (socket != null) {
                        System.out.println("Connection établie");
                    } else {
                        System.out.println("Connection non établie ");
                    }
                    // Code à exécuter lorsque le bouton "Connexion" est cliqué
                    String login = vue.getLoginFieldText();
                    String password = vue.getPasswordFieldText();

                    // Logique de connexion ici
                    System.out.println("Login: " + login);
                    System.out.println("Mot de passe: " + password);
                    EmployeBean employeConnecte = null;

                    // Créer et lancer le thread ThLogin
                    ThLogin thLogin = new ThLogin(login, password);
                    thLogin.start();

                    try {
                        // Attendre que le thread ThLogin se termine
                        thLogin.join();
                        // Récupérer l'employé connecté à partir du thread ThLogin
                        employeConnecte = thLogin.getEmploye();
                        thLogin.interrupt();
                        System.out.println(employeConnecte);

                        if (employeConnecte.getIdEmploye() == 1)
                        {
                            System.out.println("Login réussi");
                            vue.enableAfterLogin();
                        }
                        else
                        {
                            System.out.println("Login echec");
                        }
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
                else if (source == vue.selectClientButton)
                {
                    String numclient = vue.getclientNumberField();
                    System.out.println("Num client : " + numclient);
                    ClientFactures facturesAtraiter = null;
                    ThGetFactures thGetFactures = new ThGetFactures(numclient);
                    thGetFactures.start();
                    try {
                        thGetFactures.join();
                        // Une fois le thread terminé, obtenez les résultats de la variable clientEtSesFactures
                        facturesAtraiter = thGetFactures.getFactures();
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                    System.out.println("J'ai recu (controleur) : " + facturesAtraiter.toString());

                    if(facturesAtraiter == null)
                    {
                        System.out.println("Pas de factures à traiter");
                    }
                    else {
                        vue.enableAfterClientSelection();
                        vue.setClientNameFieldText(facturesAtraiter.getClient().getNom());
                        vue.fillTableWithInvoices(facturesAtraiter.getFactures());
                        vue.setNomCarte("");
                    }


                }
                else if (source == vue.actionButtons[0]) {
                    String fac = vue.getNumFacFieldText();
                    String nom = vue.getNameFieldText();
                    String carte = vue.getCardNumberFieldText();
                    ThPaiement thPaiement = new ThPaiement(fac, nom, carte);
                    thPaiement.start();

                    try {
                        // Attendez que le thread ThPaiement se termine
                        thPaiement.join();

                        // Récupérez le statut du paiement en utilisant le getter
                        int estPaye = thPaiement.getEstPaye();

                        // Utilisez la valeur estPaye comme bon vous semble
                        if (estPaye == 1) {
                            System.out.println("Paiement réussi");
                            String numclient = vue.getclientNumberField();
                            ClientFactures facturesAtraiter = null;
                            ThGetFactures thGetFactures2 = new ThGetFactures(numclient);
                            thGetFactures2.start();
                            try {
                                thGetFactures2.join();
                                // Une fois le thread terminé, obtenez les résultats de la variable clientEtSesFactures
                                facturesAtraiter = thGetFactures2.getFactures();
                            } catch (InterruptedException e1) {
                                e1.printStackTrace();
                            }
                            System.out.println("J'ai recu (controleur) : " + facturesAtraiter.toString());

                            if(facturesAtraiter == null)
                            {
                                System.out.println("Pas de factures à traiter");
                            }
                            else {
                                vue.enableAfterClientSelection();
                                vue.setClientNameFieldText(facturesAtraiter.getClient().getNom());
                                vue.fillTableWithInvoices(facturesAtraiter.getFactures());
                                vue.setCardNumberField("");
                                vue.setNumFacFieldText("");
                            }


                        } else {
                            System.out.println("Statut de paiement inconnu");
                        }
                    } catch (InterruptedException e2) {
                        e2.printStackTrace();
                    }

                    System.out.println("Bouton 1 cliqué : AAAA");
                } else if (source == vue.actionButtons[1]) {
                    System.out.println("Bouton 2 cliqué : sdfsdc");
                } else if (source == vue.actionButtons[2]) {
                    System.out.println("Bouton 3 cliqué : sdvc");
                } else if (source == vue.actionButtons[3]) {
                    System.out.println("Bouton 4 cliqué : fzsd<vd");
                } else if (source == vue.actionButtons[4]) {
                    System.out.println("Bouton 5 cliqué : qzcsvd");
                } else if (source == vue.actionButtons[5]) {
                    ThLogout thLogout = new ThLogout();
                    thLogout.start();
                    vue.setClientNameFieldText(null);
                    Vector<FactureBean> vecvide = new Vector<FactureBean>();
                    vue.fillTableWithInvoices(vecvide);
                    vue.disableAllExceptLogin();
                    vue.setLoginField("");
                    vue.setPasswordField("");
                    vue.setClientNumberField("");
                    vue.setNomCarte("");
                    vue.setCardNumberField("");
                }
                else
                {
                    System.out.println("J'ai recu un truc que je connais pas ");
                }


            }

        });

    }

    public Socket getSocket() {
        return socket;
    }

}
