package assignments.assignment2;

import assignments.assignment1.NotaGenerator;

public class Member {
    private String nama;
    private String noHp;
    private String id;
    private int bonusCounter;

    public Member(String nama, String noHp) {
        this.nama = nama;
        this.noHp = noHp;
        this.id = NotaGenerator.generateId(nama, noHp);
    }

    public String getNama() {
        return nama;
    }

    public String getNoHp() {
        return noHp;
    }

    public String getId() {
        return id;
    }

    public void addBonusCounter(int i) {
        bonusCounter += i;
    }

    public boolean isDiscount(){
        if(bonusCounter == 3){
            bonusCounter = 0;
            return true;
        }
        return false;
    }

    public boolean equals(String otherId) {
        return otherId.equals(this.id);
    }
}
