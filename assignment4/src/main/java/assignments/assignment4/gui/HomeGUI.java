package assignments.assignment4.gui;

import assignments.assignment3.nota.NotaManager;
import assignments.assignment4.MainFrame;

import javax.swing.*;
import java.awt.*;

import static assignments.assignment3.nota.NotaManager.cal;
import static assignments.assignment3.nota.NotaManager.fmt;
import static javax.swing.JOptionPane.showMessageDialog;

public class HomeGUI extends JPanel {
    public static final String KEY = "HOME";
    private JLabel titleLabel;
    private JLabel dateLabel;
    private JPanel mainPanel;
    private JButton loginButton;
    private JButton registerButton;
    private JButton toNextDayButton;

    /**
     * Constructor
     */
    public HomeGUI(){
        super(new BorderLayout()); // Setup layout, Feel free to make any changes

        // Set up main panel, Feel free to make any changes
        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        initGUI();

        add(mainPanel, BorderLayout.CENTER);

        this.setVisible(true);
    }

    /**
     * Method untuk menginisialisasi GUI.
     * Selama funsionalitas sesuai dengan soal, tidak apa apa tidak 100% sama.
     * Be creative and have fun!
     * */
    private void initGUI() {
        GridBagConstraints c = new GridBagConstraints();

        // Initialize komponen-komponen yang diperlukan
        titleLabel = new JLabel("Selamat Datang di CuciCuci System!", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        loginButton = new JButton("Login");
        registerButton = new JButton("Register");
        toNextDayButton = new JButton("Next Day");
        dateLabel = new JLabel("Hari ini: " + fmt.format(cal.getTime()), JLabel.CENTER);

        c.insets = new Insets(0, 0, 50, 0);

        // Row 1
        c.gridx = 1;
        c.gridy = 0;
        mainPanel.add(titleLabel, c);

        // Row 2
        c.insets = new Insets(0, 0, 75, 0);
        c.gridy = 1;
        mainPanel.add(loginButton, c);

        // Row 3
        c.gridy = 2;
        mainPanel.add(registerButton, c);

        // Row 4
        c.gridy = 3;
        mainPanel.add(toNextDayButton, c);

        // Row 5
        c.insets = new Insets(0, 0, 0, 0);
        c.gridy = 4;
        mainPanel.add(dateLabel, c);

        // Register action listener pada semua button di HomeGUI
        loginButton.addActionListener(e -> handleToLogin());
        registerButton.addActionListener(e -> handleToRegister());
        toNextDayButton.addActionListener(e -> handleNextDay());
    }

    /**
     * Method untuk pergi ke halaman register.
     * Akan dipanggil jika pengguna menekan "registerButton"
     * */
    private static void handleToRegister() {
        MainFrame.getInstance().navigateTo(RegisterGUI.KEY);
    }

    /**
     * Method untuk pergi ke halaman login.
     * Akan dipanggil jika pengguna menekan "loginButton"
     * */
    private static void handleToLogin() {
        MainFrame.getInstance().navigateTo(LoginGUI.KEY);
    }

    /**
     * Method untuk skip hari.
     * Akan dipanggil jika pengguna menekan "toNextDayButton"
     * */
    private void handleNextDay() {
        // Handle nextday pada setiap nota melalui static method NotaManager.toNextDay()
        NotaManager.toNextDay();
        dateLabel.setText("Hari ini: " + fmt.format(cal.getTime()));
        showMessageDialog(this, "Kamu tidur hari ini... zzz...",
                "This is Prince Paul's Bubble Party's ability!", JOptionPane.INFORMATION_MESSAGE);
    }
}
