package assignments.assignment2;
import assignments.assignment1.NotaGenerator;
import org.w3c.dom.ls.LSOutput;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

import static assignments.assignment1.NotaGenerator.*;

public class MainMenu {
    private static final Scanner input = new Scanner(System.in);
    private static SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
    private static Calendar cal = Calendar.getInstance();
    private static ArrayList<Nota> notaList = new ArrayList<>();
    private static ArrayList<Member> memberList = new ArrayList<>();

    public static void main(String[] args) {
        boolean isRunning = true;
        while (isRunning) {
            printMenu();
            System.out.print("Pilihan : ");
            String command = input.nextLine();
            System.out.println("================================");
            switch (command){
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



    public static String inputNoHP() {
        System.out.println("Masukkan nomor handphone Anda: ");
        String noHP = input.nextLine();
        noHP = IntegerAndIntervalChecker(noHP, "Field nomor hp hanya menerima digit");
        return noHP;
    }

    private static void handleGenerateUser() {
        // TODO: handle generate user
        System.out.println("Masukkan nama Anda: ");
        String nama = input.nextLine();
        String noHP = inputNoHP();
        boolean param = true;
        for (Member member : memberList) {
            if (member.getId().equals(generateId(nama, noHP))) {
                param = false; // jika param = false, maka id nya udah ada
                break;
            }
        }
        if (param) {
            Member member = new Member(nama, noHP);
            member.setId(generateId(nama, noHP));
            memberList.add(member); // add ke list
            System.out.printf("Berhasil membuat member dengan ID %s!\n", member.getId());
        }
        else
            System.out.printf("Member dengan nama %s dan nomor hp %s sudah ada!\n", nama, noHP);
    }

    private static void handleGenerateNota() {
        // TODO: handle ambil cucian
        System.out.println("Masukkan ID member:");
        String idMember = input.nextLine();
        Member memberSekarang = null;
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
            // Loop, stop jika user input "fast"/"express"/"reguler" sebagai paket.
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

    private static void handleListNota() {
        // TODO: handle list semua nota pada sistem
        System.out.printf("Terdaftar %d nota dalam sistem.\n", notaList.size());
        for (Nota nota : notaList) {
            System.out.printf("- [%d] Status      \t: %s\n", nota.getIdNota(), nota.isItReady());
        }

    }

    private static void handleListUser() {
        // TODO: handle list semua user pada sistem
        System.out.printf("Terdaftar %d member dalam sistem.\n", memberList.size());
        for (Member member : memberList) {
            System.out.printf("- %s : %s\n", member.getId(), member.getNama());
        }
    }

    private static void handleAmbilCucian() {
        // TODO: handle ambil cucian
        boolean param = false;
        Nota notaSekarang = null;
        System.out.println("Masukan ID nota yang akan diambil:");
        String akanDiambil = input.nextLine();
        while (!isNumeric(akanDiambil)) {
            System.out.println("ID nota berbentuk angka!");
            akanDiambil = input.nextLine();
        }
        for (Nota nota : notaList) {
            if (String.valueOf(nota.getIdNota()).equals(akanDiambil)) {
                param = true;
                notaSekarang = nota;
                break;
            }
        }
        if (param) {
            if (notaSekarang.getIsReady()) {
                System.out.printf("Nota dengan ID %d berhasil diambil!\n", notaSekarang.getIdNota());
                notaList.remove(notaSekarang);
//                Member memberSekarang = notaSekarang.getMember();
//                memberSekarang.incrementBonusCounter();
//                if (memberSekarang.getBonusCounter() == 3) {
//                    // HANDLE DISKON 50%
//                }
            }
            else
                System.out.printf("Nota dengan ID %d gagal diambil!\n", notaSekarang.getIdNota());
        }
        else
            System.out.printf("Nota dengan ID %s tidak ditemukan!\n", akanDiambil);
    }

    private static void handleNextDay() {
        // TODO: handle ganti hari
        // String today = fmt.format(cal.getTime());
        System.out.println("Dek Depe tidur hari ini... zzz...");
        cal.add(Calendar.DATE, 1);
        for (Nota nota : notaList) {
            nota.decrementSisaHariPengerjaan();
            if (nota.getSisaHariPengerjaan() == 0)
                System.out.printf("Laundry dengan nota ID %d sudah dapat diambil!\n", nota.getIdNota());
        }
        System.out.println("Selamat pagi dunia!");
        System.out.println("Dek Depe: It's CuciCuci Time.");
    }

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

    private static boolean isNumeric(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c))
                return false;
        }
        return true;
    }

    private static void printNota(Nota nota, Member member) {
        int year = Integer.parseInt(nota.getTanggalMasuk().substring(6));
        int month = Integer.parseInt(nota.getTanggalMasuk().substring(3, 5)) - 1;
        int date = Integer.parseInt(nota.getTanggalMasuk().substring(0, 2));
        cal.set(year, month, date);
        cal.add(Calendar.DATE, nota.getHariPengerjaan());

        System.out.println("Berhasil menambahkan nota!");
        System.out.printf("[ID Nota = %d]\n", nota.getIdNota());
        System.out.printf("ID\t: %s\n", member.getId());
        System.out.printf("Paket : %s%s\n", nota.getPaket().substring(0,1).toUpperCase(), nota.getPaket().substring(1));
        System.out.println("Harga :");

        int harga = nota.getBerat() * nota.getHargaPerKg();
        if (member.getBonusCounter() == 3) {
            member.setBonusCounter(0); // Reset
            System.out.printf("%d kg x %d = %d\n", nota.getBerat(), nota.getHargaPerKg(), harga / 2);
            System.out.println("(Discount member 50%!!!)");
        }
        else
            System.out.printf("%d kg x %d = %d\n", nota.getBerat(), nota.getHargaPerKg(), harga);

        System.out.printf("Tanggal Terima  : %s\n", nota.getTanggalMasuk());
        System.out.printf("Tanggal Selesai : %s\n", fmt.format(cal.getTime()));
        System.out.printf("Status      \t: %s\n", nota.isItReady());
    }

}