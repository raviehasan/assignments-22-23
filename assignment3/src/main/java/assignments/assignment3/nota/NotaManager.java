package assignments.assignment3.nota;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class NotaManager {
    public static SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
    public static Calendar cal = Calendar.getInstance();
    public static Nota[] notaList = new Nota[0];

    /**
     * Skips ke hari berikutnya dan update semua entri nota yang sesuai.
     */
    public static void toNextDay(){
        //TODO: implement skip hari
        cal.add(Calendar.DATE, 1); // Increment cal hari ini.
        for (Nota nota : notaList) {
            if (nota.getNotaStatus().equals("Belum selesai."))
                nota.toNextDay();
        }
    }

    /**
     * Menambahkan nota baru ke NotaList.
     *
     * @param nota Nota object untuk ditambahkan.
     */
    public static void addNota(Nota nota){
        //TODO: implement add nota
        Nota[] newArray = new Nota[notaList.length + 1];
        System.arraycopy(notaList, 0, newArray, 0, notaList.length);
        newArray[newArray.length - 1] = nota;
        notaList = newArray;
    }
}
