package assignments.assignment2;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;
import static assignments.assignment1.NotaGenerator.*;


public class MainMenu {
    private static final Scanner input = new Scanner(System.in);
    private static SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
    private static Calendar cal = Calendar.getInstance();
    private static ArrayList<Nota> notaList = new ArrayList<>();
    private static ArrayList<Member> memberList = new ArrayList<>();

    /**
     * Method main, program utama berjalan di sini.
     * Loop, user akan input satu pilihan dari 6 fitur yang tersedia (1/2/3/4/5/6/0).
     * Program akan berhenti jika pilihan = 0.
     */
    public static void main(String[] args) {
        boolean isRunning = true;
        while (isRunning) {
            printMenu();
            System.out.print("Pilihan : ");
            String command = input.nextLine();
            System.out.println("================================");
            switch (command) {
                case "1" -> handleGenerateUser();
                case "2" -> handleGenerateNota();
                case "3" -> handleListNota();
                case "4" -> handleListUser();
                case "5" -> handleAmbilCucian();
                case "6" -> handleNextDay();
                case "0" -> isRunning = false;
                default -> System.out.println("Perintah tidak diketahui, silakan periksa kembali.");
            }
        }
        System.out.println("Terima kasih telah menggunakan NotaGenerator!");
    }

    /**
     * Method untuk validasi input noHP.
     *
     * @return String input noHP baru yang valid.
     */
    public static String inputNoHP() {
        System.out.println("Masukkan nomor handphone Anda: ");
        String noHP = input.nextLine();
        noHP = IntegerAndIntervalChecker(noHP, "Field nomor hp hanya menerima digit");
        return noHP;
    }

    /**
     * Method untuk command generate member.
     */
    private static void handleGenerateUser() {
        // TODO: handle generate user
        System.out.println("Masukkan nama Anda: ");
        String nama = input.nextLine();
        String noHP = inputNoHP();

        // Jika param == false, maka sudah ada member dengan id yang sama.
        boolean param = true;
        for (Member member : memberList) {
            if (member.getId().equals(generateId(nama, noHP))) {
                param = false;
                break;
            }
        }

        // Jika belum ada member dengan id yang sama.
        if (param) {
            Member member = new Member(nama, noHP);
            member.setId(generateId(nama, noHP));
            memberList.add(member);
            System.out.printf("Berhasil membuat member dengan ID %s!\n", member.getId());
        }

        else
            System.out.printf("Member dengan nama %s dan nomor hp %s sudah ada!\n", nama, noHP);
    }

    /**
     * Method untuk command generate nota.
     */
    private static void handleGenerateNota() {
        // TODO: handle ambil cucian
        System.out.println("Masukkan ID member:");
        String idMember = input.nextLine();
        Member memberSekarang = null;

        // Jika param == true, maka id yang diinput user valid (user merupakan seorang member).
        boolean param = false;
        for (Member member : memberList) {
            if (idMember.equals(member.getId())) {
                memberSekarang = member;
                param = true;
                break;
            }
        }

        if (param) {
            String paket;
            String paketLowerCase;

            // Loop akan stop jika user input fast/express/reguler sebagai paket (pengecekan secara case insensitive).
            do {
                System.out.println("Masukkan paket laundry: ");
                paket = input.nextLine();
                paketLowerCase = paket.toLowerCase();
                if (paket.equals("?"))
                    printPaket();
                else if (!(paketLowerCase.equals("express") || paketLowerCase.equals("fast") || paketLowerCase.equals("reguler")))
                    System.out.printf("Paket %s tidak diketahui\n[ketik ? untuk mencari tahu jenis paket]\n", paket);
                else
                    break; // Input sudah sesuai.
            } while (true);

            // Validasi input berat cucian.
            System.out.println("Masukkan berat cucian Anda [Kg]: ");
            String beratCucian = input.nextLine();
            beratCucian = IntegerAndIntervalChecker(beratCucian, "Harap masukkan berat cucian Anda dalam bentuk bilangan positif.");
            int berat = Integer.parseInt(beratCucian);
            if (berat < 2) {
                berat = 2;
                System.out.println("Cucian kurang dari 2 kg, maka cucian akan dianggap sebagai 2 kg");
            }

            Nota nota = new Nota(memberSekarang, paket, berat, fmt.format(cal.getTime()));
            notaList.add(nota);
            memberSekarang.incrementBonusCounter();
            printNota(nota, memberSekarang);
        }
        else
            System.out.printf("Member dengan ID %s tidak ditemukan!\n", idMember);
    }

    /**
     * Method untuk command list nota.
     * Print list nota dengan format:
     * "Terdaftar [Banyak Nota Terdaftar] nota dalam sistem." --> 1x cetak
     * "- [[Id Nota]] Status      	: [Status pengambilan]" --> Sebanyak nota terdaftar saat ini
     */
    private static void handleListNota() {
        // TODO: handle list semua nota pada sistem
        System.out.printf("Terdaftar %d nota dalam sistem.\n", notaList.size());
        for (Nota nota : notaList) {
            System.out.printf("- [%d] Status      \t: %s\n", nota.getIdNota(), nota.isItReady());
        }

    }

    /**
     * Method untuk command list member.
     * Print setiap member yang terdaftar saat ini dengan format:
     * "- [ID Member] : [Nama Lengkap Member]" --> Sebanyak member terdaftar saat ini.
     */
    private static void handleListUser() {
        // TODO: handle list semua user pada sistem
        System.out.printf("Terdaftar %d member dalam sistem.\n", memberList.size());
        for (Member member : memberList) {
            System.out.printf("- %s : %s\n", member.getId(), member.getNama());
        }
    }

    /**
     * Method untuk command ambil cucian.
     */
    private static void handleAmbilCucian() {
        // TODO: handle ambil cucian
        boolean param = false;
        Nota notaSekarang = null;
        System.out.println("Masukan ID nota yang akan diambil:");
        String akanDiambil = input.nextLine();

        // Loop hingga id nota yang diinput user valid.
        while (!isNumeric(akanDiambil)) {
            System.out.println("ID nota berbentuk angka!");
            akanDiambil = input.nextLine();
        }

        int cek = Integer.parseInt(akanDiambil);
        for (Nota nota : notaList) {
            if (nota.getIdNota() == cek) { // Untuk idNota: 1 == 00001, 0 == 00000, etc. (dianggap sama).
                param = true;
                notaSekarang = nota;
                break;
            }
        }

        // Jika id nota ada di list nota, yang diprint pada %s adalah String input dari user.
        if (param) {
            if (notaSekarang.getIsReady()) {
                System.out.printf("Nota dengan ID %s berhasil diambil!\n", akanDiambil);
                notaList.remove(notaSekarang);
            }
            else
                System.out.printf("Nota dengan ID %s gagal diambil!\n", akanDiambil);
        }
        else
            System.out.printf("Nota dengan ID %s tidak ditemukan!\n", akanDiambil);
    }

    /**
     * Method untuk command next day.
     */
    private static void handleNextDay() {
        // TODO: handle ganti hari
        System.out.println("Dek Depe tidur hari ini... zzz...");
        cal.add(Calendar.DATE, 1); // Increment cal hari ini.
        for (Nota nota : notaList) {
            nota.decrementSisaHariPengerjaan();
            if (nota.getSisaHariPengerjaan() == 0)
                System.out.printf("Laundry dengan nota ID %d sudah dapat diambil!\n", nota.getIdNota());
        }
        System.out.println("Selamat pagi dunia!");
        System.out.println("Dek Depe: It's CuciCuci Time.");
    }

    /**
     * Method untuk menampilkan menu yang tersedia.
     */
    private static void printMenu() {
        System.out.println("\nSelamat datang di CuciCuci!");
        System.out.printf("Sekarang Tanggal: %s\n", fmt.format(cal.getTime()));
        System.out.println("==============Menu==============");
        System.out.println("[1] Generate User");
        System.out.println("[2] Generate Nota");
        System.out.println("[3] List Nota");
        System.out.println("[4] List User");
        System.out.println("[5] Ambil Cucian");
        System.out.println("[6] Next Day");
        System.out.println("[0] Exit");
    }

    /**
     * Method untuk menampilkan paket yang tersedia.
     */
    public static void printPaket() {
        System.out.println("+-------------Paket-------------+");
        System.out.println("| Express | 1 Hari | 12000 / Kg |");
        System.out.println("| Fast    | 2 Hari | 10000 / Kg |");
        System.out.println("| Reguler | 3 Hari |  7000 / Kg |");
        System.out.println("+-------------------------------+");
    }

    /**
     * Method untuk cek apakah seluruh character pada suatu String merupakan digit.
     *
     * @return boolean true jika semua char nya digit, otherwise false.
     */
    private static boolean isNumeric(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c))
                return false;
        }
        return true;
    }

    /**
     * Method untuk print detail nota.
     */
    private static void printNota(Nota nota, Member member) {
        int year = Integer.parseInt(nota.getTanggalMasuk().substring(6));
        int month = Integer.parseInt(nota.getTanggalMasuk().substring(3, 5)) - 1;
        int date = Integer.parseInt(nota.getTanggalMasuk().substring(0, 2));
        cal.set(year, month, date);
        cal.add(Calendar.DATE, nota.getHariPengerjaan()); // Count tanggal selesai.

        System.out.println("Berhasil menambahkan nota!");
        System.out.printf("[ID Nota = %d]\n", nota.getIdNota());
        System.out.printf("ID\t: %s\n", member.getId());
        System.out.printf("Paket : %s\n", nota.getPaket());
        System.out.println("Harga :");
        int harga = nota.getBerat() * nota.getHargaPerKg();

        // Diskon 50% pada setiap occurence generate nota ke-kelipatan 3 member tersebut.
        if (member.getBonusCounter() == 3) {
            member.setBonusCounter(0);
            System.out.printf("%d kg x %d = %d ", nota.getBerat(), nota.getHargaPerKg(), harga / 2);
            System.out.println("(Discount member 50%!!!)");
        }
        else
            System.out.printf("%d kg x %d = %d\n", nota.getBerat(), nota.getHargaPerKg(), harga);

        System.out.printf("Tanggal Terima  : %s\n", nota.getTanggalMasuk());
        System.out.printf("Tanggal Selesai : %s\n", fmt.format(cal.getTime()));
        System.out.printf("Status      \t: %s\n", nota.isItReady());
    }

}