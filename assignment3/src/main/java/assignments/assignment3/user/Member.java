package assignments.assignment3.user;

import assignments.assignment3.nota.Nota;
import static assignments.assignment1.NotaGenerator.generateId;

public class Member {
    protected String id;

    protected String password;
    protected String nama;
    protected Nota[] notaList;

    public Member(String nama, String id, String password) {
        this.nama = nama;
        this.password = password;
        if (this instanceof Employee)
            this.id = id;
        else
            this.id = generateId(nama, id);
        this.notaList = new Nota[0];
    }

    /**
     * Method otentikasi member dengan ID dan password yang diberikan.
     *
     * @param id -> ID anggota yang akan diautentikasi.
     * @param password -> password anggota untuk mengautentikasi.
     * @return true jika ID dan password sesuai dengan instance member, false jika tidak.
     */
    public boolean login(String id, String password) {
        return id.equals(this.id) && authenticate(password);
    }

    /**
     * Menambahkan nota baru ke NotaList instance member.
     *
     * @param nota Nota object untuk ditambahkan.
     */
    public void addNota(Nota nota){
        Nota[] result = new Nota[notaList.length + 1];
        for(int i = 0; i < notaList.length; i++) {
            result[i] = notaList[i];
        }
        result[result.length - 1] = nota;
        notaList = result;
    }

    /**
     * Method otentikasi member dengan password yang diberikan.
     *
     * @param password -> sandi password anggota untuk mengautentikasi.
     * @return true jika ID dan password sesuai dengan instance member, false jika tidak.
     */
    protected boolean authenticate(String password) {
        return password.equals(this.password);
    }

    // Dibawah ini adalah getter

    public String getNama() {
        return nama;
    }

    public String getId() {
        return id;
    }

    public Nota[] getNotaList() {
        return notaList;
    }
}