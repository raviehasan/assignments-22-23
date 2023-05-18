package assignments.assignment3.nota;

import assignments.assignment3.nota.service.LaundryService;
import assignments.assignment3.user.Member;
import java.util.Calendar;
import static assignments.assignment3.nota.NotaManager.cal;
import static assignments.assignment3.nota.NotaManager.fmt;

public class Nota {
    private Member member;
    private String paket;
    private LaundryService[] services;
    private long baseHarga;
    private int sisaHariPengerjaan;
    private int berat;
    private int idNota;
    private String tanggalMasuk;
    private boolean isDone;
    public static int totalNota = 0;
    private String notaStatus = "Belum selesai";
    private int telat;

    /**
     * Method counstructor.
     */
    public Nota(Member member, int berat, String paket, String tanggal) {
        this.member = member;
        this.berat = berat;
        this.paket = paket;
        this.tanggalMasuk = tanggal;
        this.idNota = totalNota++;
        this.services = new LaundryService[0];
        this.telat = 0;
        this.sisaHariPengerjaan = this.getHariPengerjaan();
    }

    /**
     * Method untuk menambahkan suatu service ke nota yang bersangkutan.
     */
    public void addService(LaundryService service){
        LaundryService[] newArray = new LaundryService[services.length + 1];
        System.arraycopy(services, 0, newArray, 0, services.length);
        newArray[newArray.length - 1] = service;
        services = newArray;
    }

    /**
     * Method untuk mengerjakan service uyang ada pada suatu nota secara berurutan.
     * Urutannya: Cuci --> Setrika --> Antar (Tidak selalu lengkap 3 service)
     */
    public String kerjakan(){
        // TODO
        for (LaundryService service : this.services) {
            if (!service.isDone()) {
                return service.doWork();
            }
        }
        return "Sudah selesai.";
    }

    /**
     * Method untuk handle skip hari.
     */
    public void toNextDay() {
        --this.sisaHariPengerjaan;
        if ((this.sisaHariPengerjaan < 0) && (this.getNotaStatus().equals("Belum selesai.")))
            ++this.telat; // Menghitung cucian terlambat berapa hari.
    }

    /**
     * Method untuk menghitung harga akhir suatu nota (sebelum kompensasi).
     */
    public long calculateHarga(){
        long harga = 0;
        for (LaundryService service : this.services) {
            if (service.getServiceName().equals("Cuci"))
                harga += this.getBaseHarga() * this.berat; // Karena harga pada class CuciService selalu 0.
            else
                harga += service.getHarga(berat);
        }
        if (harga < 0) // Harga tidak boleh negatif.
            return 0;
        return harga;
    }

    /**
     * Method untuk mengecek status suatu nota (sudah/belum selesai).
     */
    public String getNotaStatus(){
        for (LaundryService service : this.services) {
            if (!service.isDone()) // Jika ada service yang belum selesai.
                return "Belum selesai.";
        }
        return "Sudah selesai.";
    }

    /**
     * Method untuk print detail suatu nota.
     * Terdapat komputasi kompensasi telat (jika memang telat) di dalam method ini.
     */
    @Override
    public String toString(){
        // Handle tanggal masuk cucian dengan calendar.
        int year = Integer.parseInt(this.tanggalMasuk.substring(6));
        int month = Integer.parseInt(this.tanggalMasuk.substring(3, 5)) - 1;
        int date = Integer.parseInt(this.tanggalMasuk.substring(0, 2));
        cal.set(year, month, date);
        cal.add(Calendar.DATE, this.getHariPengerjaan()); // Count tanggal selesai.

        String output = "";
        output += String.format("[ID Nota = %d]\n", this.idNota);
        output += String.format("ID    : %s\n", member.getId());
        output += String.format("Paket : %s\n", this.paket);
        output += "Harga :\n";
        output += String.format("%d kg x %d = %d\n", this.berat, this.getBaseHarga(), this.getBaseHarga() * this.berat);
        output += String.format("tanggal terima  : %s\n", this.tanggalMasuk);
        output += String.format("tanggal selesai : %s\n", fmt.format(cal.getTime()));
        output += "--- SERVICE LIST ---\n";

        for (LaundryService service : this.services) {
            output += String.format("-%s @ Rp.%d\n", service.getServiceName(), service.getHarga(this.berat));
        }

        // Handling jika ada kompensasi karena telat (-2000/hari).
        if (this.telat > 0) {
            long setelahKompensasi = this.calculateHarga() - (this.telat * 2000L);
            output += String.format("Harga Akhir: %d Ada kompensasi keterlambatan %d * 2000 hari", setelahKompensasi, this.telat);
        } else
            output += String.format("Harga Akhir: %d", this.calculateHarga());

        return output;
    }

     // Di bawah ini adalah getter

    public long getBaseHarga() {
        switch (this.paket.toLowerCase()) {
            case "express" -> {
                return 12000;
            }
            case "fast" -> {
                return 10000;
            }
            case "reguler" -> {
                return 7000;
            }
            default -> {
                return 0;
            }
        }
    }

    public int getHariPengerjaan() {
        switch (this.paket.toLowerCase()) {
            case "express" -> {
                return 1;
            }
            case "fast" -> {
                return 2;
            }
            case "reguler" -> {
                return 3;
            }
            default -> {
                return 0;
            }
        }
    }

    public String getPaket() {
        return paket;
    }

    public int getBerat() {
        return berat;
    }

    public String getTanggal() {
        return tanggalMasuk;
    }

    public int getSisaHariPengerjaan(){
        return sisaHariPengerjaan;
    }
    public boolean isDone() {
        return isDone;
    }
    public int getIdNota() {
        return this.idNota;
    }
    public LaundryService[] getServices(){
        return this.services;
    }
}
