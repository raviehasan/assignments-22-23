package assignments.assignment4.gui;

import assignments.assignment3.LoginManager;
import assignments.assignment3.nota.NotaManager;
import assignments.assignment3.user.Member;
import assignments.assignment3.user.menu.EmployeeSystem;
import assignments.assignment3.user.menu.MemberSystem;
import assignments.assignment4.MainFrame;

import javax.accessibility.AccessibleIcon;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.function.ToDoubleBiFunction;

import static assignments.assignment3.nota.NotaManager.*;
import static javax.swing.JOptionPane.*;

public class HomeGUI extends JPanel {
    public static final String KEY = "HOME";
    private JLabel titleLabel;
    private JLabel dateLabel;
    private JPanel mainPanel;
    private JButton loginButton;
    private JButton registerButton;
    private JButton toNextDayButton;

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

        titleLabel = new JLabel("Selamat Datang di CuciCuci System!", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        loginButton = new JButton("Login");
        registerButton = new JButton("Register");
        toNextDayButton = new JButton("Next Day");
        dateLabel = new JLabel("Hari ini: " + fmt.format(cal.getTime()), JLabel.CENTER);

        c.insets = new Insets(0, 0, 50, 0);
        c.gridx = 1;
        c.gridy = 0;
        mainPanel.add(titleLabel, c);

        c.insets = new Insets(0, 0, 75, 0);
        c.gridy = 1;
        mainPanel.add(loginButton, c);

        c.gridy = 2;
        mainPanel.add(registerButton, c);

        c.gridy = 3;
        mainPanel.add(toNextDayButton, c);

        c.insets = new Insets(0, 0, 0, 0);
        c.gridy = 4;
        mainPanel.add(dateLabel, c);

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
        NotaManager.toNextDay();
        dateLabel.setText("Hari ini: " + fmt.format(cal.getTime()));

        JTextField text = new JTextField("Kamu tidur hari ini... zzz...");
        text.setEditable(false);
        showMessageDialog(this, text,
                "This is Prince Paul's Bubble Party's ability!", JOptionPane.INFORMATION_MESSAGE);
        // TODO : handle nextdaynyaa nota2nya--------------------------
    }
}
