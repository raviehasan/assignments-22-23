package assignments.assignment2;

import java.util.ArrayList;

public class Nota {
    // TODO: tambahkan attributes yang diperlukan untuk class ini
    private int idNota;
    private Member member;
    private String paket;
    private int berat;
    private String tanggalMasuk;
    private int sisaHariPengerjaan;
    private boolean isReady;
    private int hargaPerKg;
    private int hariPengerjaan;
    private static ArrayList<Integer> listIdNota = new ArrayList<>();

    public Nota(Member member, String paket, int berat, String tanggalMasuk) {
        // TODO: buat constructor untuk class ini
        this.member = member;
        this.paket = paket;
        this.berat = berat;
        this.tanggalMasuk = tanggalMasuk;
        this.isReady = false;
        this.idNota = listIdNota.size();
        listIdNota.add(this.idNota);
        this.sisaHariPengerjaan = selectionKarateristikPaket(1, 2, 3);
        this.hariPengerjaan = selectionKarateristikPaket(1, 2, 3);
        this.hargaPerKg = selectionKarateristikPaket(12000, 10000, 7000);
    }

    // TODO: tambahkan methods yang diperlukan untuk class ini
    /**
     * Method untuk selection harga dan waktu untuk paket exprees, fast, dan reguler.
     *
     * @return int sesuai parameter.
     */
    public int selectionKarateristikPaket(int a, int b, int c) {
        switch (this.paket.toLowerCase()) {
            case "express" -> {
                return a;
            }
            case "fast" -> {
                return b;
            }
            case "reguler" -> {
                return c;
            }
            default -> {
                return 0;
            }
        }
    }

    /**
     * Method-method getter.
     */
    public String getTanggalMasuk() {
        return this.tanggalMasuk;
    }

    public int getIdNota() {
        return this.idNota;
    }

    public String getPaket() {
        return this.paket;
    }

    public int getBerat() {
        return this.berat;
    }

    public int getHariPengerjaan() {
        return this.hariPengerjaan;
    }

    public int getHargaPerKg() {
        return this.hargaPerKg;
    }

    public int getSisaHariPengerjaan() {
        return this.sisaHariPengerjaan;
    }

    public boolean getIsReady() {
        return this.isReady;
    }


    /**
     * Method untuk decrement sisa hari pengerjaan untuk fitur next day.
     * Jika sisa hari pengerjaan sudah == 0, maka tidak perlu decrement lagi
     */
    public void decrementSisaHariPengerjaan() {
        if (this.sisaHariPengerjaan > 0)
            this.sisaHariPengerjaan--;
        if (this.sisaHariPengerjaan == 0)
            this.isReady = true;
    }

    /**
     * Method untuk cek apakah sudah dapat ambil cucian.
     * Jika sisa hari pengerjaan == 0, maka stop decrement karena cucian sudah dapat diambil.
     */
    public String isItReady() {
        if (this.sisaHariPengerjaan == 0) {
            return "Sudah dapat diambil!";
        }
        else
            return "Belum bisa diambil :(";
    }

}
