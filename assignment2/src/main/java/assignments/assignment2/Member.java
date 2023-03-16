package assignments.assignment2;

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

    /**
     * Method untuk increment bonus counter saat seorang member generate nota.
     */
    public void incrementBonusCounter() {
        this.bonusCounter++;
    }

    /**
     * Kumpulan method getter dan setter.
     */
    public String getNama() {
        return this.nama;
    }

    public int getBonusCounter() {
        return this.bonusCounter;
    }

    public void setBonusCounter(int bonusCounter) {
        this.bonusCounter = bonusCounter;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
