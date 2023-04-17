package assignments.assignment2;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

import static assignments.assignment1.NotaGenerator.*;

public class MainMenu {
    private static final Scanner input = new Scanner(System.in);
    private static SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
    private static Calendar cal = Calendar.getInstance();

    private static Nota[] notaList = new Nota[1000];

    private static Member[] memberList = new Member[1000];

    private static int memberCount = 0;
    private static int notaCount = 0;

    public static void main(String[] args) {

        while (true) {
            printMenu();
            System.out.print("Pilihan : ");
            String command = input.nextLine();
            System.out.println("================================");
            if (command.equals("1")) {
                handleGenerateMember();
                continue;
            }
            if (command.equals("2")) {
                handleGenerateNota();
                continue;
            }
            if (command.equals("3")) {
                handleListNota();
                continue;
            }
            if (command.equals("4")) {
                handleListMember();
                continue;
            }
            if(command.equals("5")){
                handleAmbilCucian();
                continue;
            }
            if (command.equals("6")) {
                handleNextDay();
                continue;
            }
            if (command.equals("0")) {
                break;
            }
            System.out.println("Perintah tidak diketahui, silakan periksa kembali.");
        }
        System.out.println("Terima kasih telah menggunakan NotaGenerator!");
    }

    private static void handleAmbilCucian() {
        System.out.println("Masukan ID nota yang akan diambil: ");
        String idNotaString = input.nextLine();
        while (!isNumeric(idNotaString)){
            System.out.println("ID nota berbentuk angka!");
            idNotaString = input.nextLine();
        }

        Nota nota = findNota(Integer.parseInt(idNotaString));
        if(nota == null){
            System.out.printf("Nota dengan ID %s tidak ditemukan!\n", idNotaString);
            return;
        }
        boolean isSuccess = removeNota(nota);

        if(isSuccess){
            System.out.printf("Nota dengan ID %s berhasil diambil!\n", idNotaString);
            notaCount--;
            return;
        }
        System.out.printf("Nota dengan ID %s gagal diambil!\n", idNotaString);
    }

    private static boolean removeNota(Nota nota) {
        for (int i = 0; i < notaList.length; i++) {
            if(notaList[i] != nota){
                continue;
            }
            if(!nota.isReady()){
                return false;
            }
            notaList[i] = null;
            return true;
        }
        return false;
    }

    private static Nota findNota(int idNota) {
        for (Nota nota:
             notaList) {
            if(nota == null){
                continue;
            }
            if(nota.equals(idNota)){
                return nota;
            }
        }
        return null;
    }

    private static void handleGenerateMember() {
        System.out.println("Masukan nama Anda: ");
        String nama = input.nextLine();
        System.out.println("Masukan nomor handphone Anda: ");
        String noHp = input.nextLine();
        while (!isNumeric(noHp)) {
            System.out.println("Field nomor hp hanya menerima digit.");
            noHp = input.nextLine();
        }
        Member member = new Member(nama, noHp);
        if(findMember(member.getId()) != null){
            System.out.printf("Member dengan nama %s dan nomor hp %s sudah ada!\n", nama, noHp);
            return;
        }
        memberList[memberCount] = member;
        memberCount++;
        System.out.printf("Berhasil membuat member dengan ID %s!\n", member.getId());
    }

    private static void handleGenerateNota() {
        System.out.println("Masukan ID member: ");
        String id = input.nextLine();
        Member member = findMember(id);
        if(member == null){
            System.out.printf("Member dengan ID %s tidak ditemukan!\n", id);
            return;
        }
        String tanggalTerima = fmt.format(cal.getTime());
        String paket = getPaket();
        int berat = getBerat();
        Nota nota = new Nota(member, paket, berat, tanggalTerima);
        boolean isSuccess = placeNota(nota);
        if(isSuccess) {
            notaCount++;
            System.out.println("Berhasil menambahkan nota!");
            System.out.println(nota);
            return;
        }
        System.out.println("Gagal menambahkan nota!");
    }

    private static boolean placeNota(Nota nota) {
        for (int i = 0; i < notaList.length; i++) {
            if(notaList[i] == null){
                notaList[i] = nota;
                return true;
            }
        }
        return false;
    }

    private static void handleNextDay() {
        cal.add(Calendar.DATE, 1);
        System.out.println("Dek Depe tidur hari ini... zzz...");
        for (Nota nota:
             notaList) {
            if(nota == null)
                continue;
            boolean isReady = nota.toNextDay();
            if(isReady)
                System.out.printf("Laundry dengan nota ID %d sudah dapat diambil!\n", nota.getId());
        }
        System.out.println("Selamat pagi dunia!");
        System.out.println("Dek Depe: It's CuciCuci Time.");
    }

    private static void handleListNota() {
        System.out.printf("Terdaftar %d nota dalam sistem.\n", notaCount);
        for (Nota nota:
             notaList) {
            if(nota == null)
                continue;
            System.out.printf("- [%d] %s\n", nota.getId(), nota.getStatus());
        }
    }



    private static void handleListMember() {
        System.out.printf("Terdaftar %d member dalam sistem.\n", memberCount);
        for (Member member :
                memberList) {
            if(member == null)
                return;
            System.out.printf("- %s : %s\n", member.getId(), member.getNama());
        }
    }
    /* Below is helper function */

    private static Member findMember(String id) {
        for (int i = 0; i < memberCount; i++) {
            if(memberList[i] == null)
                return null;
            if(memberList[i].equals(id)){
                return memberList[i];
            }
        }
        return null;
    }

    private static boolean isNumeric(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c))
                return false;
        }
        return true;
    }

    private static void printMenu() {
        System.out.println("\nSelamat datang di CuciCuci!");
        System.out.printf("Sekarang Tanggal: %s\n", fmt.format(cal.getTime()));
        System.out.println("==============Menu==============");
        System.out.println("[1] Generate Member");
        System.out.println("[2] Generate Nota");
        System.out.println("[3] List Nota");
        System.out.println("[4] List Member");
        System.out.println("[5] Ambil Cucian");
        System.out.println("[6] Next Day");
        System.out.println("[0] Exit");
    }

}
