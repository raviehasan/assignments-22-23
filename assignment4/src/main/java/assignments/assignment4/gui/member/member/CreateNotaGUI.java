package assignments.assignment4.gui.member.member;

import assignments.assignment3.nota.Nota;
import assignments.assignment3.nota.NotaManager;
import assignments.assignment3.nota.service.AntarService;
import assignments.assignment3.nota.service.CuciService;
import assignments.assignment3.nota.service.SetrikaService;
import assignments.assignment3.user.Member;
import assignments.assignment4.MainFrame;
import assignments.assignment4.gui.RegisterGUI;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static javax.swing.JOptionPane.showMessageDialog;

public class CreateNotaGUI extends JPanel {
    public static final String KEY = "CREATE_NOTA";
    private JLabel paketLabel;
    private JComboBox<String> paketComboBox;
    private JButton showPaketButton;
    private JLabel beratLabel;
    private JTextField beratTextField;
    private JCheckBox setrikaCheckBox;
    private JCheckBox antarCheckBox;
    private JButton createNotaButton;
    private JButton backButton;
    private final SimpleDateFormat fmt;
    private final Calendar cal;
    private final MemberSystemGUI memberSystemGUI;
    private JPanel mainPanel;
    private final String[] listPaket = {"Express", "Fast", "Reguler"};

    /**
     * Constructor
     * */
    public CreateNotaGUI(MemberSystemGUI memberSystemGUI) {
        super(new BorderLayout()); // Setup layout, Feel free to make any changes
        this.memberSystemGUI = memberSystemGUI;
        this.fmt = NotaManager.fmt;
        this.cal = NotaManager.cal;

        // Set up main panel, Feel free to make any changes
        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Set up main panel, Feel free to make any changes
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

        // Initialize komponen yang diperlukan
        paketLabel = new JLabel("Paket Laundry:");
        paketComboBox = new JComboBox<>(listPaket);
        paketComboBox.setSelectedItem("Express"); // Set "Express" sebagai default agar tidak error
        showPaketButton = new JButton("Show Paket");
        beratLabel = new JLabel("Berat Cucian [Kg]:");
        beratTextField = new JTextField();
        setrikaCheckBox = new JCheckBox("Tambah Setrika Service (1000/kg)");
        antarCheckBox = new JCheckBox("Tambah Antar Service (2000/4kg pertama, kemudian 500/kg)");
        createNotaButton = new JButton("Buat Nota");
        backButton = new JButton("Kembali");

        // Set layout
        c.insets = new Insets(5,5,5,5);
        c.fill = GridBagConstraints.HORIZONTAL;

        // Row 1
        c.gridx = 1;
        c.gridy = 1;
        mainPanel.add(paketLabel, c);
        c.gridx = 2;
        mainPanel.add(paketComboBox, c);
        c.gridx = 3;
        mainPanel.add(showPaketButton, c);

        // Row 2
        c.gridx = 1;
        c.gridy = 2;
        mainPanel.add(beratLabel, c);
        c.gridx = 2;
        mainPanel.add(beratTextField, c);

        // Row 3
        c.gridx = 1;
        c.gridy = 3;
        mainPanel.add(setrikaCheckBox, c);

        // Row 4
        c.gridy = 4;
        mainPanel.add(antarCheckBox, c);

        // Row 5
        c.gridwidth = 3;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridy = 5;
        mainPanel.add(createNotaButton, c);

        // Row 6
        c.gridy = 6;
        mainPanel.add(backButton, c);

        // Register action listener pada 3 button di CreateNotaGUI
        showPaketButton.addActionListener(e -> showPaket());
        createNotaButton.addActionListener(e -> createNota());
        backButton.addActionListener(e -> handleBack());
    }

    /**
     * Menampilkan list paket pada user.
     * Akan dipanggil jika pengguna menekan "showPaketButton"
     * */
    private void showPaket() {
        String paketInfo = """
                        <html><pre>
                        +-------------Paket-------------+
                        | Express | 1 Hari | 12000 / Kg |
                        | Fast    | 2 Hari | 10000 / Kg |
                        | Reguler | 3 Hari |  7000 / Kg |
                        +-------------------------------+
                        </pre></html>
                        """;

        JLabel label = new JLabel(paketInfo);
        label.setFont(new Font("monospaced", Font.PLAIN, 12));
        JOptionPane.showMessageDialog(this, label, "Paket Information", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Method untuk melakukan pengecekan input user dan mendaftarkan nota yang sudah valid pada sistem.
     * Akan dipanggil jika pengguna menekan "createNotaButton"
     * */
    private void createNota() {
        // Collect data dari input user
        Member loggedInMember = memberSystemGUI.getLoggedInMember();
        String paket = (String) paketComboBox.getSelectedItem();
        String beratCek = beratTextField.getText();
        boolean param = RegisterGUI.validasiDigit(beratCek, "berat"); // jika input berat valid --> param = true

        // Jika berat sudah sesuai (digit dan >= 1)
        if (param) {
            int berat = Integer.parseInt(beratCek);
            if (berat < 2) {
                berat = 2;
                showMessageDialog(this, "Cucian kurang dari 2kg, maka cucian akan dianggap sebagai 2kg",
                        "Info", JOptionPane.INFORMATION_MESSAGE);
            }

            // Initialize objek nota dan add laundry service yang dipilih member pada checkbox
            Nota nota = new Nota(loggedInMember, berat, paket, fmt.format(cal.getTime()));
            nota.addService(new CuciService());
            if (setrikaCheckBox.isSelected())
                nota.addService(new SetrikaService());
            if (antarCheckBox.isSelected())
                nota.addService(new AntarService());

            // Add objek nota ke sistem
            NotaManager.addNota(nota);
            loggedInMember.addNota(nota);
            showMessageDialog(this, "Nota berhasil dibuat!", "Success", JOptionPane.INFORMATION_MESSAGE);

            // clear fields
            paketComboBox.setSelectedItem("Express");
            beratTextField.setText("");
            setrikaCheckBox.setSelected(false);
            antarCheckBox.setSelected(false);

        } else {
            showMessageDialog(this, "Berat Cucian harus angka",
                    "Error", JOptionPane.ERROR_MESSAGE);
            beratTextField.setText("");
        }
    }

    /**
     * Method untuk kembali ke halaman home.
     * Akan dipanggil jika pengguna menekan "backButton"
     * */
    private void handleBack() {
        // Set all input handler back to default value
        paketComboBox.setSelectedItem("Express");
        beratTextField.setText("");
        setrikaCheckBox.setSelected(false);
        antarCheckBox.setSelected(false);

        MainFrame.getInstance().navigateTo(MemberSystemGUI.KEY);
    }
}
