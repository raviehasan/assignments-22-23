package assignments.assignment3;

import assignments.assignment3.user.Member;
import assignments.assignment3.user.menu.EmployeeSystem;
import assignments.assignment3.user.menu.MemberSystem;
import assignments.assignment3.user.menu.SystemCLI;
import static assignments.assignment1.NotaGenerator.generateId;

public class LoginManager {
    private final EmployeeSystem employeeSystem;
    private final MemberSystem memberSystem;

    /**
     * Method constructor.
     */
    public LoginManager(EmployeeSystem employeeSystem, MemberSystem memberSystem) {
        this.employeeSystem = employeeSystem;
        this.memberSystem = memberSystem;
    }

    /**
     * Method mapping dari ke SystemCLI yang sesuai.
     *
     * @param id -> ID dari user yang akan menggunakan SystemCLI
     * @return SystemCLI object yang sesuai dengan ID, null if  ID tidak ditemukan.
     */
    public SystemCLI getSystem(String id){
        String who; // Untuk cek member/employee.
        if(memberSystem.isMemberExist(id)){
            who = memberSystem.memberOrEmployee(id);
            if (who.equals("member"))
                return memberSystem;
            else
                return employeeSystem;
        }
        return null;
    }

    /**
     * Mendaftarkan member baru dengan informasi yang diberikan.
     *
     * @param nama -> Nama member.
     * @param noHp -> Nomor handphone member.
     * @param password -> Password akun member.
     * @return Member object yang berhasil mendaftar, return null jika gagal mendaftar.
     */
    public Member register(String nama, String noHp, String password) {
        boolean param = memberSystem.isMemberExist(generateId(nama, noHp));

        // Jika belum ada member dengan id yang sama.
        if (!param) {
            Member member = new Member(nama, noHp, password);
            memberSystem.addMember(member);
            return member;

        } else {
            return null;
        }
    }
}