package assignments.assignment1;

import java.time.LocalDate;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;

public class NotaGenerator {
    private static final Scanner input = new Scanner(System.in);

    /**
     * Method main, program utama berjalan di sini.
     * User akan input pilihan fitur (1 = Generate ID, 2 = Generate Nota, dan 0 = Exit).
     */
    public static void main(String[] args) {
        String pilihan;

        // Loop, stop jika user input 0 sebagai pilihan.
        do {
            printMenu();
            System.out.print("Pilihan : ");
            pilihan = input.nextLine();

            // Selection.
            switch (pilihan) {
                case "1" -> {
                    UserInput(1);
                }
                case "2" -> {
                    UserInput(2);
                }
                default -> {
                    System.out.println("================================");
                    System.out.println("Perintah tidak diketahui, silakan periksa kembali.");
                }
            }
        } while (!pilihan.equals("0"));

        System.out.println("================================");
        System.out.println("Terima kasih telah menggunakan NotaGenerator!");
        input.close();
    }

    /**
     * Method untuk menampilkan menu di NotaGenerator.
     */
    private static void printMenu() {
        System.out.println("Selamat datang di NotaGenerator!");
        System.out.println("==============Menu==============");
        System.out.println("[1] Generate ID");
        System.out.println("[2] Generate Nota");
        System.out.println("[0] Exit");
    }

    /**
     * Method untuk menampilkan paket yang tersedia.
     */
    private static void showPaket() {
        System.out.println("+-------------Paket-------------+");
        System.out.println("| Express | 1 Hari | 12000 / Kg |");
        System.out.println("| Fast    | 2 Hari | 10000 / Kg |");
        System.out.println("| Reguler | 3 Hari |  7000 / Kg |");
        System.out.println("+-------------------------------+");
    }

    /**
     * Method untuk menerima input dan handling input user agar sesuai kriteria.
     * User akan mengisi semua data untuk Generate ID dan Generate Nota di method ini
     */
    public static void UserInput(int pilihan) {
        String nama; String nomorHP;
        System.out.println("Masukkan nama Anda: ");
        nama = input.nextLine();
        System.out.println("Masukkan nomor handphone Anda: ");
        nomorHP = input.nextLine();
        nomorHP = IntegerAndIntervalChecker(nomorHP, "Nomor hp hanya menerima digit");

        if (pilihan == 1)
            System.out.println(generateId(nama, nomorHP));

        else if (pilihan == 2) {
            String paket;
            System.out.println("Masukkan tanggal terima: ");
            String tanggalTerima = input.nextLine(); // Format dd/mm/yyyy (sudah dijamin valid).

            // Loop, stop jika user input "fast"/"express"/"reguler" sebagai paket.
            do {
                System.out.println("Masukkan paket laundry: ");
                paket = (input.nextLine()).toLowerCase();
                if (paket.equals("?"))
                    showPaket();
                else if (!(paket.equals("express") || paket.equals("fast") || paket.equals("reguler")))
                    System.out.printf("Paket %s tidak diketahui\n[ketik ? untuk mencari tahu jenis paket]\n", paket);
                else
                    break; // Input sudah sesuai.
            } while (true);

            System.out.println("Masukkan berat cucian Anda [Kg]: ");
            String beratCucian = input.nextLine();
            beratCucian = IntegerAndIntervalChecker(beratCucian, "Harap masukkan berat cucian Anda dalam bentuk bilangan positif.");
            int berat = Integer.parseInt(beratCucian);
            System.out.println(generateNota(generateId(nama, nomorHP), paket, berat, tanggalTerima));
        }
    }

    /**
     * Method untuk membuat ID dari nama dan nomor handphone.
     *
     * @return String ID anggota dengan format [NAMADEPAN]-[nomorHP]-[2digitChecksum]
     */
    public static String generateId(String nama, String nomorHP) {
        int checkSum = 0; String checkedSum;

        // Jika nama > 1 kata --> ambil nama depan user.
        if (nama.contains(" "))
            nama = nama.substring(0, (nama.indexOf(" ")));
        String idAwal = nama.toUpperCase() + "-" + nomorHP;

        // Checksum, iterasi seluruh character pada String [NAMADEPAN]-[nomorHP]
        for (int i = 0; i < idAwal.length(); i++) {
            char element = idAwal.charAt(i);
            if (Character.isLetter(element))
                checkSum += element - 'A' + 1;
            else if (Character.isDigit(element))
                checkSum += element - '0';
            else if (element == '-')
                checkSum += 7;
        }

        // Handling jika hasil checksum tidak tepat 2 digit.
        if (checkSum < 10)
            checkedSum = "0" + checkSum;
        else if (checkSum > 99)
            checkedSum = (Integer.toString(checkSum)).substring(0,2); // Ambil 2 digit pertama, truncate sisanya.
        else
            checkedSum = Integer.toString(checkSum);

        return idAwal + "-" + checkedSum; // [NAMADEPAN]-[nomorHP]-[2digitChecksum]
    }

    /**
     * Method untuk membuat Nota.
     *
     * @return string nota dengan format di bawah:
     *         <p>ID    : [id]
     *         <p>Paket : [paket]
     *         <p>Harga :
     *         <p>[berat] kg x [hargaPaketPerKg] = [totalHarga]
     *         <p>Tanggal Terima  : [tanggalTerima]
     *         <p>Tanggal Selesai : [tanggalTerima + LamaHariPaket]
     */
    public static String generateNota(String id, String paket, int berat, String tanggalTerima){
        // Assign awal sebagai paket express.
        int hargaPerKg = 12000;
        int hariPengerjaan = 1;

        // Selection, jika bukan paket fast atau reguler --> paket express.
        switch (paket) {
            case "fast" -> {
                hargaPerKg = 10000;
                hariPengerjaan = 2;
            }
            case "reguler" -> {
                hargaPerKg = 7000;
                hariPengerjaan = 3;
            }
        }

        // Nota (print sebagian dan return sisanya).
        if (berat < 2) {
            berat = 2;
            System.out.println("Cucian kurang dari 2 kg, maka cucian akan dianggap sebagai 2 kg");
        }
        System.out.println("Nota Laundry");
        return  String.format("ID    : %s\n", id) +
                String.format("Paket : %s\n", paket) +
                "Harga :\n" +
                String.format("%d kg x %d = %d\n", berat, hargaPerKg, (berat * hargaPerKg)) +
                String.format("Tanggal Terima  : %s\n",tanggalTerima) +
                String.format("Tanggal Selesai : %s", tanggalSelesaiCounter(tanggalTerima, hariPengerjaan));
    }

    /**
     * Method untuk menghitung tanggal pesanan akan selesai.
     *
     * @return String tanggalAkhir (tanggal pesanan akan selesai dengan format [dd/mm/yyyy])
     */
    public static String tanggalSelesaiCounter(String tanggalTerima, int hariPengerjaan) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate tanggalAwal = LocalDate.parse(tanggalTerima, formatter);
        LocalDate tanggalAkhir = tanggalAwal.plusDays(hariPengerjaan);
        return tanggalAkhir.format(formatter);
    }

    /**
     * Method untuk minta user input ulang suatu "number" jika belum sesuai kriteria
     * Kriteria nomorHP --> Bilangan bulat yang >= 0 dan tidak ada spasi
     * Kriteria beratCucian --> Bilangan bulat yang >= 1 dan tidak ada spasi
     *
     * @return String "number" terakhir yang diinput user (sudah sesuai kriteria variabel)
     */
    public static String IntegerAndIntervalChecker(String number, String message) {
        int batasBawah = 0; int sum = 0; int charVal = 0; char elem; boolean check;

        // Jika cek beratCucian (minimal 1, karena bilangan bulat positif).
        if (message.equals("Harap masukkan berat cucian Anda dalam bentuk bilangan positif."))
            batasBawah = 1;

        // Iterasi setiap character pada string number.
        do {
            check = true;
            for (int i = 0; i < number.length(); i++) {
                elem = number.charAt(i);
                if (Character.isDigit(elem)) {
                    charVal = elem - '0';
                    sum += charVal;
                }
                else {
                    check = false;
                    break;
                }
            }
            // Belum sesuai kriteria --> user input ulang number
            if (!check || sum < batasBawah || number.equals("")) {
                System.out.println(message);
                number = input.nextLine();
            }
        } while(!check);

        return number;
    }
}