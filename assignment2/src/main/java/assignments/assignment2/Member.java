package assignments.assignment2;

import assignments.assignment1.NotaGenerator;

import java.util.ArrayList;

public class Member {
    // TODO: tambahkan attributes yang diperlukan untuk class ini
    private String nama;
    private String noHP;
    private String id;
    private int bonusCounter;

    public Member(String nama, String noHp) {
        // TODO: buat constructor untuk class ini
        this.nama = nama;
        this.noHP = noHp;
        this.bonusCounter = 0;
    }

    // TODO: tambahkan methods yang diperlukan untuk class ini
    public void incrementBonusCounter() {
        this.bonusCounter++;
    }

    public int getBonusCounter() {
        return this.bonusCounter;
    }

    public void setBonusCounter(int bonusCounter) {
        this.bonusCounter = bonusCounter;
    }

    public String getNama() {
        return this.nama;
    }

    public String getNoHP() {
        return this.noHP;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
