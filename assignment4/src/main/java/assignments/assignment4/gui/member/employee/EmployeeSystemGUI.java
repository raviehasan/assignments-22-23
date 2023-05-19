package assignments.assignment4.gui.member.employee;

import assignments.assignment3.nota.Nota;
import assignments.assignment3.user.menu.SystemCLI;
import assignments.assignment4.gui.member.AbstractMemberGUI;

import javax.swing.*;
import java.awt.event.ActionListener;

import static assignments.assignment3.nota.NotaManager.notaList;
import static javax.swing.JOptionPane.showMessageDialog;

public class EmployeeSystemGUI extends AbstractMemberGUI {
    public static final String KEY = "EMPLOYEE";

    /**
     * Constructor
     * */
    public EmployeeSystemGUI(SystemCLI systemCLI) {
        super(systemCLI);
    }

    /**
     * Method untuk return page key
     * */
    @Override
    public String getPageName(){
        return KEY;
    }

    /**
     * Method ini mensupply buttons yang sesuai dengan requirements Employee.
     * Button yang disediakan method ini BELUM memiliki ActionListener.
     *
     * @return Array of JButton, berisi button yang sudah stylize namun belum ada ActionListener.
     * */
    @Override
    protected JButton[] createButtons() {
        // TODO
        return new JButton[]{
                new JButton("It's nyuci time"),
                new JButton("Display List Nota"),
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
                e -> cuci(),
                e -> displayNota(),
        };
    }

    /**
     * Menampilkan semua Nota yang ada pada sistem.
     * Akan dipanggil jika pengguna menekan button pertama pada createButtons
     * */
    private void displayNota() {
        String status = "";
        for (Nota nota : notaList) {
            status += String.format("Nota %d : %s\n", nota.getIdNota(), nota.getNotaStatus());
        }

        // Jika tidak terdaftar nota sama sekali pada sistem
        if (status.equals(""))
            showMessageDialog(this, "Belum ada nota", "List Nota", JOptionPane.ERROR_MESSAGE);

        else
            showMessageDialog(this, status, "List Nota", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Menampilkan dan melakukan action mencuci.
     * Akan dipanggil jika pengguna menekan button kedua pada createButtons
     * */
    private void cuci() {
        String status = "";
        showMessageDialog(this, String.format("Stand back! %s beginning to nyuci",
                loggedInMember.getNama()), "Nyuci Time", JOptionPane.INFORMATION_MESSAGE);

        // Iterasi setiap nota dan selesaikan 1 laundry service secara sequential
        for (Nota nota : notaList) {
            status += String.format("Nota %d : %s\n", nota.getIdNota(), nota.kerjakan());
        }

        // Jika tidak terdaftar nota sama sekali pada sistem
        if (status.equals(""))
            showMessageDialog(this, "nothing to cuci here", "Nyuci Results", JOptionPane.ERROR_MESSAGE);

        else
            showMessageDialog(this, status, "List Nota", JOptionPane.INFORMATION_MESSAGE);
    }

}
