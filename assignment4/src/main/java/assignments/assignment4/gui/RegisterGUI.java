package assignments.assignment4.gui;

import assignments.assignment3.LoginManager;
import assignments.assignment3.user.Member;
import assignments.assignment4.MainFrame;
import assignments.assignment4.gui.member.member.MemberSystemGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.JOptionPane.*;

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
        // TODO
        GridBagConstraints c = new GridBagConstraints();
        GridBagConstraints d = new GridBagConstraints();

        nameLabel = new JLabel("Masukkan nama Anda:", JLabel.LEFT);
        nameTextField = new JTextField();
        phoneLabel = new JLabel("Masukkan nomor handphone Anda:", JLabel.LEFT);
        phoneTextField = new JTextField();
        passwordLabel = new JLabel("Masukkan password Anda:", JLabel.LEFT);
        passwordField = new JPasswordField();
        registerButton = new JButton("Register");
        backButton = new JButton("Kembali");

        c.ipadx = 400;
        c.fill = GridBagConstraints.HORIZONTAL;

        c.insets = new Insets(0, 0, 20, 0);
        c.gridx = 1;
        c.gridy = 1;
        mainPanel.add(nameLabel, c);

        c.gridy = 2;
        mainPanel.add(nameTextField, c);

        c.gridy = 3;
        mainPanel.add(phoneLabel, c);

        c.gridy = 4;
        mainPanel.add(phoneTextField, c);

        c.gridy = 5;
        mainPanel.add(passwordLabel, c);

        c.gridy = 6;
        mainPanel.add(passwordField, c);

        d.insets = new Insets(0, 0, 20, 0);
        d.gridx = 1;
        d.gridy = 7;
        mainPanel.add(registerButton, d);

        d.insets = new Insets(0, 0, 0, 0);
        d.gridy = 8;
        mainPanel.add(backButton, d);

        registerButton.addActionListener(e -> {
            handleRegister();
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
        // TODO: clear fieldsnya
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
        // TODO: save datanya
        String nama = nameTextField.getText();
        String noHp = phoneTextField.getText();
        String password = String.valueOf(passwordField.getPassword());
        Member registeredMember = loginManager.register(nama, noHp, password);
        boolean param = registeredMember != null; // registeredMember == null --> param = false

        if (nama.equals("") || noHp.equals("") || password.equals("")) {
            JTextField text = new JTextField("Semua field di atas wajib diisi!");
            text.setEditable(false);
            showMessageDialog(this, text, "Empty Field", JOptionPane.ERROR_MESSAGE);

        } else if (!validasiDigit(noHp, "noHp")) {
            JTextField text = new JTextField("Nomor handphone harus berisi angka!");
            text.setEditable(false);
            showMessageDialog(this, text,"Invalid Phone Number", JOptionPane.ERROR_MESSAGE);
            noHp = "";
            phoneTextField.setText("");

        } else if (registeredMember == null) {
            JTextField text = new JTextField(String.format("User dengan nama %s dan nomor hp %s sudah ada!",
                    nama, noHp));
            text.setEditable(false);
            showMessageDialog(this, text, "Registration Failed", JOptionPane.ERROR_MESSAGE);
            handleBack(); // clear fields

        } else {
            JTextField text = new JTextField(String.format("Berhasil membuat user dengan ID %s",
                    registeredMember.getId()));
            text.setEditable(false);
            showMessageDialog(this, text, "Registration Successful", JOptionPane.INFORMATION_MESSAGE);
            handleBack(); // clear fields
        }
    }

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
            }
            else {
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
