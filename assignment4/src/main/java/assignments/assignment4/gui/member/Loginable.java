package assignments.assignment4.gui.member;

/**
 * Interface ini akan diimplements oleh 3 class, yakni
 * EmpployeeSystemGUI, MemberSystemGUI, dan AbstractMemberGUI
 */
public interface Loginable {

    /**
     * Di bawah ini adalah method-method yang harus dibuat implementasinya
     * oleh class yang implements Loginable
     */
    boolean login(String id, String password);
    void logout();
    String getPageName();

}
