package program;

import program.modul.*;

public class Start {
    public static modul_dashboard d = new modul_dashboard();

    // aplikasi berjalan awalnya dari sini
    public static void main(String args[]) {
        // tampilkan dashboard
        d.setLocationRelativeTo(null);
        d.setVisible(true);
    }
}
