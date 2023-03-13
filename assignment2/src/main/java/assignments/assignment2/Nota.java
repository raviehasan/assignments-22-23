package assignments.assignment2;

import assignments.assignment1.NotaGenerator;

import java.util.ArrayList;
import java.util.Calendar;

import static assignments.assignment1.NotaGenerator.tanggalSelesaiCounter;

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

    public Member getMember() {
        return this.member;
    }

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

    public String isItReady() {
        if (this.sisaHariPengerjaan == 0) {
            return "Sudah dapat diambil!";
        }
        else
            return "Belum bisa diambil :(";
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

    public void decrementSisaHariPengerjaan() {
        if (this.sisaHariPengerjaan > 0)
            this.sisaHariPengerjaan--;
        if (this.sisaHariPengerjaan == 0)
            this.isReady = true;
    }

    public boolean getIsReady() {
        return this.isReady;
    }

    public ArrayList<Integer> getListIdNota() {
        return listIdNota;
    }
}
