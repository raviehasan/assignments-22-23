package assignments.assignment3.user.menu;

import assignments.assignment3.nota.Nota;
import assignments.assignment3.nota.NotaManager;
import assignments.assignment3.nota.service.AntarService;
import assignments.assignment3.nota.service.CuciService;
import assignments.assignment3.nota.service.SetrikaService;
import assignments.assignment3.user.Member;
import static assignments.assignment1.NotaGenerator.IntegerAndIntervalChecker;
import static assignments.assignment3.nota.NotaManager.cal;
import static assignments.assignment3.nota.NotaManager.fmt;

public class MemberSystem extends SystemCLI {
    /**
     * Memproses pilihan dari Member yang masuk ke sistem ini sesuai dengan menu specific.
     *
     * @param choice -> pilihan pengguna.
     * @return true jika user log.
     */
    @Override
    protected boolean processChoice(int choice) {
        boolean logout = false;
        switch (choice) {
            // Menerima input detail cucian dari member.
            case 1 -> {
                System.out.println("Masukkan paket laundry: ");
                printPaket();
                String paket;
                String paketLowerCase;

                // Validasi input paket.
                do {
                    paket = in.nextLine();
                    paketLowerCase = paket.toLowerCase();
                    if (paket.equals("?"))
                        printPaket();
                    else if (!(paketLowerCase.equals("express") || paketLowerCase.equals("fast") || paketLowerCase.equals("reguler")))
                        System.out.printf("Paket %s tidak diketahui\n[ketik ? untuk mencari tahu jenis paket]\n", paket);
                    else
                        break;
                } while (true);

                // Validasi input berat cucian.
                System.out.println("Masukkan berat cucian Anda [Kg]: ");
                String beratCucian = in.nextLine();
                beratCucian = IntegerAndIntervalChecker(beratCucian, "Harap masukkan berat cucian Anda dalam bentuk bilangan positif.");
                int berat = Integer.parseInt(beratCucian);
                if (berat < 2) {
                    berat = 2;
                    System.out.println("Cucian kurang dari 2 kg, maka cucian akan dianggap sebagai 2 kg");
                }

                System.out.println("Apakah kamu ingin cucianmu disetrika oleh staff professional kami?");
                System.out.println("Hanya tambah 1000 / kg :0");
                System.out.print("[Ketik x untuk tidak mau]: ");
                String inputSetrika = in.nextLine();
                boolean optionSetrika = !(inputSetrika.equalsIgnoreCase("x")); // Jika false --> ga setrika

                System.out.println("Mau diantar oleh kurir kami? Dijamin aman dan cepat sampai tujuan!");
                System.out.println("Cuma 2000 / 4kg, kemudian 500 / kg");
                System.out.print("[Ketik x untuk tidak mau]: ");
                String inputAntar = in.nextLine();
                boolean optionAntar = !(inputAntar.equalsIgnoreCase("x")); // Jika false --> ga antar

                // Instansiasi nota sesuai laundry service dan tambahkan ke array.
                Nota nota = new Nota(this.loginMember, berat, paket, fmt.format(cal.getTime()));
                nota.addService(new CuciService());
                if (optionSetrika)
                    nota.addService(new SetrikaService());
                if (optionAntar)
                    nota.addService(new AntarService());
                NotaManager.addNota(nota);
                loginMember.addNota(nota);
                System.out.println("Nota berhasil dibuat!");
            }

            // Mencetak semua nota dari member yang bersangkutan.
            case 2 -> {
                int counter = 1;
                int total = loginMember.getNotaList().length;
                for (Nota nota : loginMember.getNotaList()) {
                    System.out.println(nota);
                    if (counter != total)
                        System.out.println();
                    counter++;
                }
            }

            // Logout.
            case 3 -> {
                logout = true;
            }
        }

        return logout;
    }

    /**
     * Displays specific menu untuk Member biasa.
     */
    @Override
    protected void displaySpecificMenu() {
        System.out.println("1. Saya ingin laundry");
        System.out.println("2. Lihat detail nota saya");
        System.out.println("3. Logout");
    }

    /**
     * Menambahkan Member baru ke sistem.
     *
     * @param member -> Member baru yang akan ditambahkan.
     */
    public void addMember(Member member) {
        // TODO
        Member[] newArray = new Member[memberList.length + 1];
        System.arraycopy(memberList, 0, newArray, 0, memberList.length);
        newArray[newArray.length - 1] = member;
        memberList = newArray;
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

}