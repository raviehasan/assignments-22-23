package assignments.assignment4.gui;

import assignments.assignment3.LoginManager;
import assignments.assignment4.MainFrame;

import javax.swing.*;
import java.awt.*;

import static javax.swing.JOptionPane.showMessageDialog;

public class LoginGUI extends JPanel {
    public static final String KEY = "LOGIN";
    private JPanel mainPanel;
    private JLabel idLabel;
    private JTextField idTextField;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton backButton;
    private LoginManager loginManager;

    public LoginGUI(LoginManager loginManager) {
        super(new BorderLayout()); // Setup layout, Feel free to make any changes
        this.loginManager = loginManager;

        // Set up main panel, Feel free to make any changes
        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        initGUI();

        add(mainPanel, BorderLayout.CENTER);
    }

    /**
     * Method untuk menginisialisasi GUI.
     * Selama funsionalitas sesuai dengan soal, tidak apa apa tidak 100% sama.
     * Be creative and have fun!
     * */
    private void initGUI() {
        GridBagConstraints c = new GridBagConstraints();
        GridBagConstraints d = new GridBagConstraints();

        // Initialize komponen-komponen yang diperlukan oleh LoginGUI
        idLabel = new JLabel("Masukkan ID Anda:", JLabel.LEFT);
        idTextField = new JTextField();
        passwordLabel = new JLabel("Masukkan password Anda:", JLabel.LEFT);
        passwordField = new JPasswordField();
        loginButton = new JButton("Login");
        backButton = new JButton("Kembali");

        c.ipadx = 400; // Perlebar width
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0, 0, 40, 0);

        // Row 1
        c.gridx = 1;
        c.gridy = 1;
        mainPanel.add(idLabel, c);

        // Row 2
        c.gridy = 2;
        mainPanel.add(idTextField, c);

        // Row 3
        c.gridy = 3;
        mainPanel.add(passwordLabel, c);

        // Row 4
        c.gridy = 4;
        mainPanel.add(passwordField, c);

        // Row 5
        d.insets = new Insets(0, 0, 40, 0);
        d.gridx = 1;
        d.gridy = 5;
        mainPanel.add(loginButton, d);

        // Row 6
        d.insets = new Insets(0, 0, 0, 0);
        d.gridy = 6;
        mainPanel.add(backButton, d);

        // Register action listener
        loginButton.addActionListener(e -> handleLogin());
        backButton.addActionListener(e -> handleBack());
    }

    /**
     * Method untuk kembali ke halaman home.
     * Akan dipanggil jika pengguna menekan "backButton"
     * */
    private void handleBack() {
        // Clear fields dan kembali ke HomeGUI
        idTextField.setText("");
        passwordField.setText("");
        MainFrame.getInstance().navigateTo(HomeGUI.KEY);
    }

    /**
     * Method untuk login pada sistem.
     * Akan dipanggil jika pengguna menekan "loginButton"
     * */
    private void handleLogin() {
        // Collect data dari input fields
        String id = idTextField.getText();
        String pass = String.valueOf(passwordField.getPassword());

        if (!MainFrame.getInstance().login(id, pass))
            showMessageDialog(this, "ID atau password invalid.",
                    "Invalid ID or Password", JOptionPane.ERROR_MESSAGE);

        // Clear fields (berhasil/gagal login tetap clear)
        idTextField.setText("");
        passwordField.setText("");
    }
}
