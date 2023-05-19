package assignments.assignment4.gui.member.member;

import assignments.assignment3.nota.Nota;
import assignments.assignment3.user.Member;
import assignments.assignment3.user.menu.SystemCLI;
import assignments.assignment4.MainFrame;
import assignments.assignment4.gui.member.AbstractMemberGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static javax.swing.JOptionPane.*;

public class MemberSystemGUI extends AbstractMemberGUI {
    public static final String KEY = "MEMBER";

    /**
     * Constructor
     */
    public MemberSystemGUI(SystemCLI systemCLI) {
        super(systemCLI);
    }

    /**
     * Method untuk return KEY dari MemberSystemGUI
     */
    @Override
    public String getPageName(){
        return KEY;
    }

    /**
     * Method untuk return objek membner yang sedang login pada sistem saat ini
     */
    public Member getLoggedInMember(){
        return loggedInMember;
    }

    /**
     * Method ini mensupply buttons yang sesuai dengan requirements MemberSystem.
     * Button yang disediakan method ini BELUM memiliki ActionListener.
     *
     * @return Array of JButton, berisi button yang sudah stylize namun belum ada ActionListener.
     * */
    @Override
    protected JButton[] createButtons() {
        // Initialize dan add button pada Array
        return new JButton[]{
                new JButton("Saya ingin laundry"),
                new JButton("Lihat detail nota saya"),
        };
    }

    /**
     * Method ini mensupply ActionListener korespondensi dengan button yang dibuat createButtons()
     * sesuai dengan requirements MemberSystem.
     *
     * @return Array of ActionListener.
     * */
    @Override
    protected ActionListener[] createActionListeners() {
        return new ActionListener[]{
                e -> createNota(),
                e -> showDetailNota(),
        };
    }

    /**
     * Menampilkan detail Nota milik loggedInMember.
     * Akan dipanggil jika pengguna menekan button pertama pada createButtons
     * */
    private void showDetailNota() {
        String status = "";

        // Iterasi setiap nota yang dipesan oleh member yang sedang login saat ini
        for (Nota nota : loggedInMember.getNotaList()) {
            status += nota + "\n\n\n"; // Space antar nota 2 enter (sesuai test case)
        }

        // Jika member tidak pernah pesan nota
        if (status.equals("")) {
            JTextArea text = new JTextArea("Belum pernah laundry di CuciCuci, hiks :'(");
            text.setEditable(false);
            JScrollPane scroll = new JScrollPane(text);
            scroll.setPreferredSize(new Dimension(400, 300));
            showMessageDialog(this, scroll, "Detail Nota", INFORMATION_MESSAGE);
        }

        // Jika ada setidaknya 1 nota
        else {
            JTextArea text = new JTextArea(status);
            text.setEditable(false);
            JScrollPane scroll = new JScrollPane(text);
            scroll.setPreferredSize(new Dimension(400, 300));
            showMessageDialog(this, scroll, "Detail Nota", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Pergi ke halaman CreateNotaGUI.
     * Akan dipanggil jika pengguna menekan button kedua pada createButtons
     * */
    private void createNota() {
        MainFrame.getInstance().navigateTo(CreateNotaGUI.KEY);
    }

}
