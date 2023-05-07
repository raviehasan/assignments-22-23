package assignments.assignment3.nota.service;

public class SetrikaService implements LaundryService{
    private boolean isDone = false;

    /**
     * Method untuk menyelesaikan setrika service.
     */
    @Override
    public String doWork() {
        // TODO
        if (!isDone)
            isDone = true;
        return "Sedang menyetrika...";
    }

    /**
     * Method untuk mengecek apakah setrika service telah selesai.
     */
    @Override
    public boolean isDone() {
        // TODO
        return isDone;
    }

    /**
     * Method untuk menghitung harga antar service sesuai berat cucian.
     * Biayanya 1000/kg
     */
    @Override
    public long getHarga(int berat) {
        return berat * 1000L;
    }

    /**
     * Method untuk mengetahui bahwa ini merupakan setrika service.
     */
    @Override
    public String getServiceName() {
        return "Setrika";
    }
}
