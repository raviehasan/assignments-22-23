package assignments.assignment4.gui;

import assignments.assignment3.LoginManager;
import assignments.assignment3.user.Member;
import assignments.assignment4.MainFrame;

import javax.swing.*;
import java.awt.*;

import static javax.swing.JOptionPane.showMessageDialog;

public class RegisterGUI extends JPanel {
    public static final String KEY = "REGISTER";
    private JPanel mainPanel;
    private JLabel nameLabel;
    private JTextField nameTextField;
    private JLabel phoneLabel;
    private JTextField phoneTextField;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JButton registerButton;
    private LoginManager loginManager;
    private JButton backButton;

    public RegisterGUI(LoginManager loginManager) {
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

        // Initialize semua komponen yang diperlukan oleh RegisterGUI
        nameLabel = new JLabel("Masukkan nama Anda:", JLabel.LEFT);
        nameTextField = new JTextField();
        phoneLabel = new JLabel("Masukkan nomor handphone Anda:", JLabel.LEFT);
        phoneTextField = new JTextField();
        passwordLabel = new JLabel("Masukkan password Anda:", JLabel.LEFT);
        passwordField = new JPasswordField();
        registerButton = new JButton("Register");
        backButton = new JButton("Kembali");

        c.ipadx = 400; // Perlebar komponen
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0, 0, 20, 0);

        // Row 1
        c.gridx = 1;
        c.gridy = 1;
        mainPanel.add(nameLabel, c);

        // Row 2
        c.gridy = 2;
        mainPanel.add(nameTextField, c);

        // Row 3
        c.gridy = 3;
        mainPanel.add(phoneLabel, c);

        // Row 4
        c.gridy = 4;
        mainPanel.add(phoneTextField, c);

        // Row 5
        c.gridy = 5;
        mainPanel.add(passwordLabel, c);

        // Row 6
        c.gridy = 6;
        mainPanel.add(passwordField, c);

        // Row 7
        d.insets = new Insets(0, 0, 20, 0);
        d.gridx = 1;
        d.gridy = 7;
        mainPanel.add(registerButton, d);

        // Row 8
        d.insets = new Insets(0, 0, 0, 0);
        d.gridy = 8;
        mainPanel.add(backButton, d);

        // Register action listener pada button
        registerButton.addActionListener(e -> handleRegister());
        backButton.addActionListener(e -> handleBack());
    }

    /**
     * Method untuk kembali ke halaman home.
     * Akan dipanggil jika pengguna menekan "backButton"
     * */
    private void handleBack() {
        // Clear fields dan kembali ke HomeGUI
        nameTextField.setText("");
        phoneTextField.setText("");
        passwordField.setText("");
        MainFrame.getInstance().navigateTo(HomeGUI.KEY);
    }

    /**
    * Method untuk mendaftarkan member pada sistem.
    * Akan dipanggil jika pengguna menekan "registerButton"
    * */
    private void handleRegister() {

        // Collect data dari input fields
        String nama = nameTextField.getText();
        String noHp = phoneTextField.getText();
        String password = String.valueOf(passwordField.getPassword());
        Member registeredMember = loginManager.register(nama, noHp, password);

        // Handling error input (else paling bawah adalah jika sudah sesuai)
        if (nama.equals("") || noHp.equals("") || password.equals("")) {
            showMessageDialog(this, "Semua field di atas wajib diisi!",
                    "Empty Field", JOptionPane.ERROR_MESSAGE);

        } else if (!validasiDigit(noHp, "noHp")) {
            showMessageDialog(this, "Nomor handphone harus berisi angka!",
                    "Invalid Phone Number", JOptionPane.ERROR_MESSAGE);
            phoneTextField.setText("");

        } else if (registeredMember == null) {
            showMessageDialog(this, String.format("User dengan nama %s dan nomor hp %s sudah ada!",
                    nama, noHp), "Registration Failed", JOptionPane.ERROR_MESSAGE);
            handleBack(); // Clear fields

        } else {
            JTextField text = new JTextField(String.format("Berhasil membuat user dengan ID %s",
                    registeredMember.getId()));
            text.setEditable(false);
            showMessageDialog(this, text, "Registration Successful", JOptionPane.INFORMATION_MESSAGE);
            handleBack(); // Clear fields
        }
    }

    /**
     * Method untuk validasi apakah noHp dan berat cucian sudah valid
     */
    public static boolean validasiDigit(String number, String cekApa) {
        int batasBawah = 0; int sum = 0; int charVal = 0; char elem; boolean check;

        if (cekApa.equals("berat"))
            batasBawah = 1;

        // Iterasi setiap character pada string number.
        check = true;
        for (int i = 0; i < number.length(); i++) {
            elem = number.charAt(i);
            if (Character.isDigit(elem)) {
                charVal = elem - '0';
                sum += charVal;
            } else {
                check = false;
                break;
            }
        }

        // Belum sesuai kriteria --> user input ulang number
        if (!check || sum < batasBawah || number.equals("")) {
            check = false;
        }

        return check;
    }
}
