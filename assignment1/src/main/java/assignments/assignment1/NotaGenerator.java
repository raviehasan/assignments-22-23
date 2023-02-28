package assignments.assignment1;

import java.time.LocalDate;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;

public class NotaGenerator {
    private static final Scanner input = new Scanner(System.in);

    /**
     * Method main, program utama kalian berjalan disini.
     */
    public static void main(String[] args) {
        // TODO: Implement interface menu utama

        // Infinite loop hingga user input 0 sebagai pilihan.
        String pilihan;
        String nama;
        String nomorHP;
        do {
            printMenu();
            System.out.print("Pilihan : ");
            pilihan = input.nextLine();

            // Selection
            switch (pilihan) {

                case "0":
                    break;

                // Fitur Generate ID
                case "1":
                    System.out.println("Masukkan nama Anda: ");
                    nama = input.nextLine(); // namanya masih pake semua
                    System.out.println("Masukkan nomor handphone Anda: ");
                    nomorHP = input.nextLine();
                    System.out.println(generateId(nama, nomorHP));
                    break;

                // Fitur Generate Nota
                case "2":
                    System.out.println("Masukkan nama Anda: ");
                    nama = input.nextLine(); // namanya masih pake semua
                    System.out.println("Masukkan nomor handphone Anda: ");
                    nomorHP = input.nextLine();
                    nomorHP = integerChecker(nomorHP, "Nomor hp hanya menerima digit");
                    System.out.println("Masukkan tanggal terima: ");
                    String tanggalTerima = input.nextLine();

                    String paket;
                    do {
                        System.out.println("Masukkan paket laundry: ");
                        paket = (input.nextLine()).toLowerCase();
                        if (paket.equals("?"))
                            showPaket();
                        else if (!(paket.equals("express") || paket.equals("fast") || paket.equals("reguler")))
                            System.out.printf("Paket %s tidak diketahui\n[ketik ? untuk mencari tahu jenis paket]\n", paket);
                        else
                            break;
                    } while (true);

                    System.out.println("Masukkan berat cucian Anda [Kg]: ");
                    String beratCucian = input.nextLine(); // harus set biar positif terus
                    beratCucian = integerChecker(beratCucian, "Berat cucian hanya menerima bilangan bulat positif");
                    int berat = Integer.parseInt(beratCucian);
                    System.out.println(generateNota(generateId(nama, nomorHP), paket, berat, tanggalTerima));
                    break;

                default:
                    System.out.println("================================");
                    System.out.println("Perintah tidak diketahui, silakan periksa kembali.");
                    break;

            }
        } while (!pilihan.equals("0"));

        // Jika keluar dari loop
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
     * Method untuk menampilkan paket.
     */
    private static void showPaket() {
        System.out.println("+-------------Paket-------------+");
        System.out.println("| Express | 1 Hari | 12000 / Kg |");
        System.out.println("| Fast    | 2 Hari | 10000 / Kg |");
        System.out.println("| Reguler | 3 Hari |  7000 / Kg |");
        System.out.println("+-------------------------------+");
    }

    /**
     * Method untuk membuat ID dari nama dan nomor handphone.
     * Parameter dan return type dari method ini tidak boleh diganti agar tidak mengganggu testing
     *
     * @return String ID anggota dengan format [NAMADEPAN]-[nomorHP]-[2digitChecksum]
     */
    public static String generateId(String nama, String nomorHP){
        // TODO: Implement generate ID sesuai soal.
        nomorHP = integerChecker(nomorHP, "Nomor hp hanya menerima digit");
        if (nama.contains(" "))
            nama = nama.substring(0, (nama.indexOf(" ")));
        String idAwal = nama.toUpperCase() + "-" + nomorHP;
        int checkSum = 0;
        String checkedSum;

        // Checksum
        for (int i = 0; i < idAwal.length(); i++) {
            char element = idAwal.charAt(i);
            if (Character.isLetter(element))
                checkSum += element - 'A' + 1; // Karena A mulai dari 1
            else if (Character.isDigit(element))
                checkSum += element - '0'; // Karena 0 mulai dari 1
            else if (element == '-')
                checkSum += 7;
        }

        if (checkSum < 10)
            checkedSum = "0" + checkSum;
        else if (checkSum > 99)
            checkedSum = (Integer.toString(checkSum)).substring(0,2);
        else
            checkedSum = Integer.toString(checkSum);

        return idAwal + "-" + checkedSum;
    }

    /**
     *
     * Method untuk membuat Nota.
     * Parameter dan return type dari method ini tidak boleh diganti agar tidak mengganggu testing.
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
        // TODO: Implement generate nota sesuai soal.
        String notaLaundry = "";
        int hargaPerKg = 12000; // Default untuk paket express
        int hariPengerjaan = 1; // Default untuk paket express
        switch (paket) {
            case "fast" -> {
                hargaPerKg = 10000;
                hariPengerjaan = 2;
            }
            case "reguler" -> {
                hargaPerKg = 7000;
                hariPengerjaan = 3;
            }
            default -> {
            }
        }
        if (berat < 2) {
            berat = 2;
            System.out.println("Cucian kurang dari 2 kg, maka cucian akan dianggap sebagai 2 kg");
        }
        System.out.println("Nota Laundry");
        return  String.format("ID    : %s", id) +
                String.format("\nPaket : %s", paket) +
                "\nHarga :" +
                String.format("\n%d kg x %d = %d", berat, hargaPerKg, (berat * hargaPerKg)) +
                String.format("\nTanggal Terima  : %s",tanggalTerima) +
                String.format("\nTanggal Selesai : %s", tanggalSelesaiCounter(tanggalTerima, hariPengerjaan));
    }

    public static String tanggalSelesaiCounter(String tanggalTerima, int hariPengerjaan) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate tanggalAwal = LocalDate.parse(tanggalTerima, formatter);
        LocalDate tanggalAkhir = tanggalAwal.plusDays(hariPengerjaan);
        return tanggalAkhir.format(formatter);
    }

    public static String integerChecker(String number, String message) {
        while (!isNumeric(number)) {
            System.out.println(message);
            number = input.nextLine();
        }
        return number;
    }

    public static boolean isNumeric(String nomorHP) {
        boolean checker = true;
        try {
            Long.parseLong(nomorHP);
        }
        catch (NumberFormatException e) {
            checker = false;
        }
        return checker;
    }
}
