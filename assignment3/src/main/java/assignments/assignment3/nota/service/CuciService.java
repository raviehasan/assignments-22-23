package assignments.assignment3.nota.service;

public class CuciService implements LaundryService{
    private boolean isDone = false;

    /**
     * Method untuk menyelesaikan cuci service.
     */
    @Override
    public String doWork() {
        if (!isDone)
            isDone = true;
        return "Sedang mencuci...";
    }

    /**
     * Method untuk mengecek apakah cuci service telah selesai.
     */
    @Override
    public boolean isDone() {
        return isDone;
    }

    /**
     * Method untuk menghitung harga cuci service.
     * Harga selalu 0 (sesuai test case)
     */
    @Override
    public long getHarga(int berat) {
        return 0;
    }

    /**
     * Method untuk mengetahui bahwa ini merupakan cuci service.
     */
    @Override
    public String getServiceName() {
        return "Cuci";
    }
}
