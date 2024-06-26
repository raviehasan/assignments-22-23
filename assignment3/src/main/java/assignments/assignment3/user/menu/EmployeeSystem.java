package assignments.assignment3.user.menu;

import assignments.assignment3.nota.Nota;
import assignments.assignment3.user.Employee;
import assignments.assignment3.user.Member;

import static assignments.assignment3.nota.NotaManager.notaList;

public class EmployeeSystem extends SystemCLI {

    /**
     * Membuat object baru EmployeeSystem dan mendaftarkan Employee pada CuciCuci
     */
    public EmployeeSystem() {
        memberList = new Member[]{
                new Employee("Dek Depe", "akuDDP"),
                new Employee("Depram", "musiktualembut"),
                new Employee("Lita Duo", "gitCommitPush"),
                new Employee("Ivan Hoshimachi", "SuamiSahSuisei"),
        };
    }

    /**
     * Memproses pilihan dari employee yang masuk ke sistem ini sesuai dengan menu specific.
     *
     * @param choice -> pilihan pengguna.
     * @return true jika user log.
     */
    @Override
    protected boolean processChoice(int choice) {
        boolean logout = false;

        switch (choice) {
            // Menyelesaikan 1 laundry service pada setiap nota.
            case 1 -> {
                System.out.println("Stand back! " + loginMember.getNama() + " beginning to nyuci!");
                for (Nota nota : notaList) {
                    System.out.printf("Nota %d : %s\n", nota.getIdNota(), nota.kerjakan());
                }
            }
            // Mencetak status setiap nota.
            case 2 -> {
                for (Nota nota : notaList) {
                    System.out.printf("Nota %d : %s\n", nota.getIdNota(), nota.getNotaStatus());
                }
            }
            // Logout.
            case 3 -> {
                logout = true;
            }
        }

        return logout;
    }

    public void addEmployee(Member[] members){
        Member[] result = new Member[memberList.length + 2];
        System.arraycopy(memberList, 0, result, 0, memberList.length);
        result[result.length - 2] = members[0];
        result[result.length -1] = members[1];
        memberList = result;
    }

    /**
     * Displays specific menu untuk Employee.
     */
    @Override
    protected void displaySpecificMenu() {
        System.out.println("1. It's nyuci time");
        System.out.println("2. Display List Nota");
        System.out.println("3. Logout");
    }
}