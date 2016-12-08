package program.modul;

import javax.swing.JInternalFrame;
import java.awt.Font;
import java.awt.SystemColor;

import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import program.database.*;

public class modul_tentang extends JInternalFrame{
    public modul_tentang() {
        setTitle("Tentang ApotikJava");
        setBackground(Color.decode("#eeeeee"));

        getContentPane().setLayout(null);

        JLabel lblabout= new JLabel("Program ApotikJava");
        lblabout.setBounds(10, 11, 212, 40);
        lblabout.setFont(new Font("Arial", Font.BOLD, 20));
        getContentPane().add(lblabout);

        JPanel panel = new JPanel();
        panel.setBackground(Color.BLACK);
        panel.setBounds(10, 65, 421, 2);
        getContentPane().add(panel);

        JInternalFrame internalFrame = new JInternalFrame("New JInternalFrame");
        panel.add(internalFrame);

        JTextPane txtpnAuthorByBla = new JTextPane();
        txtpnAuthorByBla.setEditable(false);
        txtpnAuthorByBla.setFont(new Font("Arial", Font.PLAIN, 12));
        txtpnAuthorByBla.setText(Koneksi.AUTHOR);
        txtpnAuthorByBla.setBackground(SystemColor.control);
        txtpnAuthorByBla.setBounds(10, 78, 421, 176);
        getContentPane().add(txtpnAuthorByBla);
        internalFrame.setVisible(true);
    }
}
