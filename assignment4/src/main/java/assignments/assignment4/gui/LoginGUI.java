package assignments.assignment4.gui;

import assignments.assignment3.LoginManager;
import assignments.assignment3.user.Member;
import assignments.assignment3.user.menu.SystemCLI;
import assignments.assignment4.MainFrame;
import assignments.assignment4.gui.member.employee.EmployeeSystemGUI;
import assignments.assignment4.gui.member.member.MemberSystemGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.JOptionPane.*;

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
        // TODO
        GridBagConstraints c = new GridBagConstraints();
        GridBagConstraints d = new GridBagConstraints();

        idLabel = new JLabel("Masukkan ID Anda:", JLabel.LEFT);
        idTextField = new JTextField();
        passwordLabel = new JLabel("Masukkan password Anda:", JLabel.LEFT);
        passwordField = new JPasswordField();
        loginButton = new JButton("Login");
        backButton = new JButton("Kembali");

        c.ipadx = 400;
        c.fill = GridBagConstraints.HORIZONTAL;

        c.insets = new Insets(0, 0, 40, 0);
        c.gridx = 1;
        c.gridy = 1;
        mainPanel.add(idLabel, c);

        c.gridy = 2;
        mainPanel.add(idTextField, c);

        c.gridy = 3;
        mainPanel.add(passwordLabel, c);

        c.gridy = 4;
        mainPanel.add(passwordField, c);

        d.insets = new Insets(0, 0, 40, 0);
        d.gridx = 1;
        d.gridy = 5;
        mainPanel.add(loginButton, d);

        d.insets = new Insets(0, 0, 0, 0);
        d.gridy = 6;
        mainPanel.add(backButton, d);

        loginButton.addActionListener(e -> {
            handleLogin();
        });

        backButton.addActionListener(e -> {
            handleBack();
        });
    }

    /**
     * Method untuk kembali ke halaman home.
     * Akan dipanggil jika pengguna menekan "backButton"
     * */
    private void handleBack() {
        idTextField.setText("");
        passwordField.setText("");
        MainFrame.getInstance().navigateTo(HomeGUI.KEY);
    }

    /**
     * Method untuk login pada sistem.
     * Akan dipanggil jika pengguna menekan "loginButton"
     * */
    private void handleLogin() {
        // TODO
        String id = idTextField.getText();
        String pass = String.valueOf(passwordField.getPassword());
        boolean param = MainFrame.getInstance().login(id, pass);
        if (param) {
            idTextField.setText("");
            passwordField.setText("");
        } else {
            JTextField text = new JTextField("ID atau password invalid.");
            text.setEditable(false);
            showMessageDialog(this, text, "Invalid ID or Password", JOptionPane.ERROR_MESSAGE);
        }
    }
}
