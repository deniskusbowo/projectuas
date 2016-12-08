package program.modul;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import javax.swing.JTextField;
import javax.swing.JButton;

import program.database.*;

public class modul_produk_form extends JInternalFrame {
    private JTextField tbNamaProduk;
    private JTextField tbHarga;
    private JButton btnBuat;
    private String PID = "";
    private modul_produk_form me;

    public modul_produk_form(String IID) throws SQLException {
        me = this;
        setTitle("Produk");
        setBackground(Color.decode("#eeeeee"));
        getContentPane().setLayout(null);

        btnBuat = new JButton("Buat");
        PID = IID;

        JLabel lblNamaProduk = new JLabel("Nama Produk");
        lblNamaProduk.setBounds(10, 14, 89, 14);
        getContentPane().add(lblNamaProduk);

        JLabel lblHargaJual = new JLabel("Harga Jual");
        lblHargaJual.setBounds(10, 42, 89, 14);
        getContentPane().add(lblHargaJual);

        tbNamaProduk = new JTextField();
        tbNamaProduk.setBounds(109, 11, 216, 20);
        getContentPane().add(tbNamaProduk);
        tbNamaProduk.setColumns(10);

        tbHarga = new JTextField();
        tbHarga.setBounds(109, 39, 117, 20);
        getContentPane().add(tbHarga);
        tbHarga.setColumns(10);

        btnBuat.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Koneksi db = new Koneksi();

                if (PID.equals("")) {
                    try {
                        db.connect();
                        PreparedStatement ps = db.connection
                                .prepareStatement("INSERT INTO produk(Nama_Produk, Stock, Harga_Jual) values (?, '0', ?) ");
                        ps.setString(1, tbNamaProduk.getText());
                        ps.setString(2, tbHarga.getText());

                        ps.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Produk "
                                + tbNamaProduk.getText()
                                + " berhasil telah dibuat ");
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                } else {
                    try {
                        db.connect();
                        PreparedStatement ps = db.connection
                                .prepareStatement("UPDATE produk SET Nama_Produk = ?, Harga_Jual = ? WHERE ID_Produk = ?");
                        ps.setString(1, tbNamaProduk.getText());
                        ps.setString(2, tbHarga.getText());
                        ps.setString(3, PID);

                        ps.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Produk "
                                + tbNamaProduk.getText()
                                + " berhasil telah diubah");
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }

                    me.dispose();
                }
            }
        });
        btnBuat.setBounds(236, 38, 89, 23);
        getContentPane().add(btnBuat);

        if (IID == "") {
            this.setTitle("Buat Produk");
            btnBuat.setText("Buat");
        } else {

            Koneksi d = new Koneksi();
            d.connect();
            String SQLQuery = "SELECT Nama_Produk, Harga_Jual FROM produk WHERE ID_Produk = '"
                    + PID + "' ";

            System.out.println(SQLQuery);
            ResultSet rs = d.statement.executeQuery(SQLQuery);

            if (!rs.wasNull()) {
                while (rs.next()) {
                    tbNamaProduk.setText(rs.getString(1));
                    tbHarga.setText(rs.getString(2));

                    this.setTitle("Ubah Produk");
                    btnBuat.setText("Ubah");
                }
            }
        }
    }
}
