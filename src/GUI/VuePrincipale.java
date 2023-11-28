package GUI;

import BeansModele.*;
import Ctrl.Controleur;
import Ctrl.Singleton;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class VuePrincipale extends JFrame {

    // Composants de l'interface utilisateur
    private JTextField loginField;
    private JPasswordField passwordField;
    public JButton loginButton;
    private JTextField clientNumberField;
    private JTextField clientNameField;
    public JButton selectClientButton;
    private JTable invoiceTable;
    private JTextField numFacField;
    private JTextField nameField;
    private JTextField cardNumberField;
    public JButton[] actionButtons = new JButton[6]; // Tableau de boutons pour le bas
    private DefaultTableModel tableModel;

    // Référence au contrôleur
    private Controleur controleur;

    // Constructeur
    public VuePrincipale() {
        setTitle("Gestion des Paiements Client");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 700); // Taille par défaut de la fenêtre
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setLocationRelativeTo(null);

        // Initialisation des composants de l'interface utilisateur
        initLoginPanel();
        initClientSearchPanel();
        initTablePanel();
        initBottomPanel();

        // Récupérer l'instance du contrôleur via le singleton
        controleur = Singleton.getInstance();
        controleur.setVue(this);

        // Désactiver les éléments sauf Login, Mot de passe et Connexion au lancement
        disableAllExceptLogin();

        setVisible(true); // Rendre la fenêtre visible
    }

    // Initialiser le panneau de connexion
    private void initLoginPanel() {
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        loginField = new JTextField(10);
        passwordField = new JPasswordField(10);
        loginButton = new JButton("Connexion");

        loginPanel.add(new JLabel("Login:"));
        loginPanel.add(loginField);
        loginPanel.add(new JLabel("Mot de Passe:"));
        loginPanel.add(passwordField);
        loginPanel.add(loginButton);

        loginPanel.setMaximumSize(new Dimension(500, loginPanel.getPreferredSize().height));
        add(loginPanel);
    }

    // Initialiser le panneau de recherche de client
    private void initClientSearchPanel() {
        JPanel clientSearchPanel = new JPanel();
        clientSearchPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        clientNumberField = new JTextField(15);
        clientNameField = new JTextField(20);
        clientNameField.setEditable(false); // Le nom est affiché, non édité
        selectClientButton = new JButton("Sélectionner Client");

        clientSearchPanel.add(new JLabel("NumClient:"));
        clientSearchPanel.add(clientNumberField);
        clientSearchPanel.add(new JLabel("Nom Client:"));
        clientSearchPanel.add(clientNameField);
        clientSearchPanel.add(selectClientButton);

        add(clientSearchPanel);
    }

    // Initialiser le panneau du tableau
    private void initTablePanel() {
        String[] columnNames = {"NumFacture", "Date", "Montant", "StatusPaiement"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Rendre les cellules non éditables
            }
        };

        invoiceTable = new JTable(tableModel);
        invoiceTable.setDefaultRenderer(Object.class, new InvoiceTableCellRenderer());
        JScrollPane scrollPane = new JScrollPane(invoiceTable);

        add(scrollPane);
    }

    // Initialiser le panneau inférieur avec les champs de texte et les boutons
    private void initBottomPanel() {
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        numFacField = new JTextField(10);
        nameField = new JTextField(10);
        cardNumberField = new JTextField(10);

        bottomPanel.add(new JLabel("NumFac:"));
        bottomPanel.add(numFacField);
        bottomPanel.add(new JLabel("Nom:"));
        bottomPanel.add(nameField);
        bottomPanel.add(new JLabel("Numéro Carte:"));
        bottomPanel.add(cardNumberField);

        for (int i = 0; i < actionButtons.length; i++) {
            if (i == 0) {
                actionButtons[i] = new JButton("Paiement");

            } else if (i == 5) {
                actionButtons[i] = new JButton("Fermer session");
            } else {
                actionButtons[i] = new JButton("Bouton " + (i + 1));

            }
            bottomPanel.add(actionButtons[i]);
        }

        add(bottomPanel);
    }

    // Classe interne pour le rendu personnalisé des cellules du tableau
    public class InvoiceTableCellRenderer extends JLabel implements TableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            if (value != null) {
                setText(value.toString());
            }

            setOpaque(true); // Pour que la couleur de fond soit visible

            String status = table.getModel().getValueAt(row, 3).toString();
            if ("Pas payée".equals(status)) {
                setBackground(Color.PINK);
            } else if ("Payée".equals(status)) {
                setBackground(Color.GREEN);
            } else {
                setBackground(Color.WHITE);
            }

            if (isSelected) {
                setBackground(getBackground().darker());
            }

            return this;
        }
    }

    public void fillTableWithInvoices(List<FactureBean> invoices) {
        DefaultTableModel model = (DefaultTableModel) invoiceTable.getModel();
        model.setRowCount(0); // Clear the existing table data

        for (FactureBean invoice : invoices) {
            // Add a row to the table for each invoice
            Object[] rowData = {
                    invoice.getId(),
                    invoice.getDate(),
                    invoice.getMontant(),
                    invoice.isPaye() ? "Payée" : "Pas payée"
            };
            model.addRow(rowData);
        }
    }



    // Méthode pour désactiver tous les éléments sauf Login, Mot de passe et Connexion
    public void disableAllExceptLogin() {
        clientNumberField.setEnabled(false);
        clientNameField.setEnabled(false);
        selectClientButton.setEnabled(false);
        numFacField.setEnabled(false); // Griser le champ NumFac
        nameField.setEnabled(false);   // Griser le champ Nom
        cardNumberField.setEnabled(false); // Griser le champ Numéro Carte
        for (JButton button : actionButtons) {
            button.setEnabled(false);
        }
        loginField.setEnabled(true);
        passwordField.setEnabled(true);
        loginButton.setEnabled(true);
    }

    // Méthode pour activer NumClient, Sélectionner Client et le bouton numéro 6
    public void enableAfterLogin() {
        clientNumberField.setEnabled(true);
        selectClientButton.setEnabled(true);
        actionButtons[5].setEnabled(true);
        loginField.setEnabled(false);
        passwordField.setEnabled(false);
        loginButton.setEnabled(false);
    }

    // Méthode pour activer le reste des éléments
    public void enableAfterClientSelection() {
        for (JButton button : actionButtons) {
            button.setEnabled(true);
        }
        numFacField.setEnabled(true);
        nameField.setEnabled(true);
        cardNumberField.setEnabled(true);
    }


    // GET / SET / ACTIONS
    public String getLoginFieldText() {
        return loginField.getText();
    }

    public String getPasswordFieldText() {
        return new String(passwordField.getPassword());
    }

    public JButton getLoginButton() {
        return loginButton;
    }
    public void addButtonListener(ActionListener listener) {
        loginButton.addActionListener(listener);
        selectClientButton.addActionListener(listener);
        for (JButton button : actionButtons) {
            button.addActionListener(listener);
        }
    }


    public String getclientNumberField() {
        return clientNumberField.getText();
    }


    public void setClientNameFieldText(String text) {
        clientNameField.setText(text);
    }
    // Getter pour le champ NumFac
    public String getNumFacFieldText() {
        return numFacField.getText();
    }

    // Getter pour le champ Nom
    public String getNameFieldText() {
        return nameField.getText();
    }

    // Getter pour le champ Numéro Carte
    public String getCardNumberFieldText() {
        return cardNumberField.getText();
    }

    // Setter pour le champ NumFac
    public void setNumFacFieldText(String text) {
        numFacField.setText(text);
    }
    public void setLoginField(String text) {
        loginField.setText(text);
    }
    public void setPasswordField(String text) {
        passwordField.setText(text);
    }
    public void setClientNumberField(String text) {
        clientNumberField.setText(text);
    }
    public void setNomCarte(String text) {
        nameField.setText(text);
    }

    public void setCardNumberField(String text) {
        cardNumberField.setText(text);
    }

    // Getter pour le bouton 1
    public JButton getActionButton1() {
        return actionButtons[0];
    }

    // Getter pour le bouton 2
    public JButton getActionButton2() {
        return actionButtons[1];
    }

    // Getter pour le bouton 3
    public JButton getActionButton3() {
        return actionButtons[2];
    }

    // Getter pour le bouton 4
    public JButton getActionButton4() {
        return actionButtons[3];
    }

    // Getter pour le bouton 5
    public JButton getActionButton5() {
        return actionButtons[4];
    }

    // Getter pour le bouton 6
    public JButton getActionButton6() {
        return actionButtons[5];
    }



    // Méthode principale pour lancer l'interface
    public static void main(String[] args) {
        SwingUtilities.invokeLater(VuePrincipale::new);
    }
}
