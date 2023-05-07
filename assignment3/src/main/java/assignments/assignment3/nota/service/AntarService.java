package assignments.assignment3.nota.service;

public class AntarService implements LaundryService{
    private boolean isDone = false;

    /**
     * Method untuk menyelesaikan antar service.
     */
    @Override
    public String doWork() {
        if (!isDone)
            isDone = true;
        return "Sedang mengantar...";
    }

    /**
     * Method untuk mengecek apakah antar service telah selesai.
     */
    @Override
    public boolean isDone() {
        return isDone;
    }

    /**
     * Method untuk menghitung harga antar service sesuai berat cucian.
     * Biayanya 500/kg
     * Jika harga < 2000 --> Dianggap 2000
     */
    @Override
    public long getHarga(int berat) {
        if ((berat*500L) < 2000)
            return 2000L; // Karena minimalnya 2000
        else
            return berat * 500L;
    }

    /**
     * Method untuk mengetahui bahwa ini merupakan antar service.
     */
    @Override
    public String getServiceName() {
        return "Antar";
    }
}
