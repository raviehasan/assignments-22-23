package assignments.assignment2;

import assignments.assignment1.NotaGenerator;

public class Nota {

    private int idNota;
    private Member member;
    private String paket;
    private int berat;
    private String tanggalTerima;
    private int sisaHariPengerjaan;
    static public int totalNota;

    private boolean isReady = false;

    public Nota(Member member, String paket, int berat, String tanggalTerima) {
        this.idNota = totalNota;
        this.member = member;
        this.paket = paket;
        this.berat = berat;
        this.tanggalTerima = tanggalTerima;
        this.sisaHariPengerjaan = NotaGenerator.toHariPaket(paket);
        totalNota++;
        member.addBonusCounter(1);
    }
    public int getId() {
        return idNota;
    }

    public boolean isReady() {
        return isReady;
    }

    public boolean toNextDay(){
        sisaHariPengerjaan--;
        if(sisaHariPengerjaan <= 0){
            isReady = true;
        }
        return isReady;
    }

    public String getStatus() {
        String message = isReady ? "Sudah dapat diambil!" : "Belum bisa diambil :(";
        return "Status          : " + message;
    }

    @Override
    public String toString() {
        return String.format("[ID Nota = %d]%n", idNota) + NotaGenerator.generateNota(member.getId(), paket, berat, tanggalTerima, member.isDiscount()) + "\n"
                + getStatus();
    }
    public boolean equals(int idNota) {
        return idNota == this.idNota;
    }

    public boolean equals(Nota nota) {
        return equals(nota.getId());
    }
}
