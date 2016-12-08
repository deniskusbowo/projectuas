package program.modul;


import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

import javax.swing.JInternalFrame;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import program.database.*;
import com.toedter.calendar.JDateChooser;

public class modul_check_produk extends JInternalFrame {
    private JDateChooser tbDari;
    private JDateChooser tbSampai;
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private String ConditionEx = " AND CONCAT(p.ID, pd.ID_Produk) NOT IN (SELECT CONCAT(rr.ID_Pembelian, rr.ID_Produk) FROM retur rr) ";
    private JButton btnCari;

    public modul_check_produk() throws SQLException {
        this.setTitle("Check Produk");
        setBackground(Color.decode("#eeeeee"));
        Koneksi d = new Koneksi();
        d.connect();

        final quick.dbtable.DBTable dbt = new quick.dbtable.DBTable();
        getContentPane().add(dbt);
        dbt.setConnection(d.connection);
        String SQLQuery = "SELECT p.ID as `ID Pembelian`, Tanggal as `Tanggal Beli`, pro.ID_Produk, pro.Nama_Produk, Harga, (Stock_Sekarang-Stock_Awal) as `Jumlah`, pd.Tanggal_Kadaluarasa as `Tanggal Kadaluarsa` FROM pembelian p LEFT JOIN pembelian_detail pd ON p.ID = pd.ID_Pembelian LEFT JOIN produk pro ON pro.ID_Produk = pd.ID_Produk "
                + " WHERE p.ID <> '' " + ConditionEx + " ORDER BY Tanggal DESC";

        dbt.setSelectSql(SQLQuery);
        dbt.createControlPanel();
        dbt.setEditable(false);

        JPanel panel = new JPanel();
        FlowLayout flowLayout = (FlowLayout) panel.getLayout();
        flowLayout.setAlignment(FlowLayout.LEFT);
        dbt.add(panel, BorderLayout.NORTH);

        JLabel lblDari = new JLabel("Dari");
        lblDari.setHorizontalAlignment(SwingConstants.RIGHT);
        lblDari.setFont(new Font("Arial", Font.BOLD, 12));
        panel.add(lblDari);

        try {
            dbt.refresh();
            dbt.getColumn(1).setMinWidth(150);
            dbt.getColumn(2).setMinWidth(100);
        } catch (SQLException e) {
            // e.printStackTrace();
        }

        btnCari = new JButton("Cari");
        btnCari.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                String txtDari = "";
                if (tbDari.getDate() != null)
                    txtDari += dateFormat.format(tbDari.getDate());

                String txtSampai = "";
                if (tbSampai.getDate() != null)
                    txtSampai += dateFormat.format(tbSampai.getDate());

                String condition = "";

                if (!txtDari.equals("")) {
                    condition += " AND DATE_FORMAT(pd.Tanggal_Kadaluarasa, '%Y-%m-%d') >= '"
                            + txtDari + "' ";
                }
                if (!txtSampai.equals("")) {
                    condition += " AND DATE_FORMAT(pd.Tanggal_Kadaluarasa, '%Y-%m-%d') <= '"
                            + txtSampai + "' ";
                }

                String SQLQuery = "SELECT p.ID as `ID Pembelian`, Tanggal as `Tanggal Beli`, pro.ID_Produk, pro.Nama_Produk, Harga, (Stock_Sekarang-Stock_Awal) as `Jumlah`, pd.Tanggal_Kadaluarasa as `Tanggal Kadaluarsa` FROM pembelian p LEFT JOIN pembelian_detail pd ON p.ID = pd.ID_Pembelian LEFT JOIN produk pro ON pro.ID_Produk = pd.ID_Produk "
                        + " WHERE p.ID <> '' "
                        + ConditionEx
                        + condition
                        + " ORDER BY Tanggal DESC";
                System.out.println(SQLQuery);

                dbt.setSelectSql(SQLQuery);

                try {
                    dbt.refresh();
                    dbt.getColumn(1).setMinWidth(150);
                    dbt.getColumn(2).setMinWidth(100);
                } catch (SQLException e) {
// e.printStackTrace();
                }

            }
        });

        tbDari = new JDateChooser();
        panel.add(tbDari);

        JLabel lblSampai = new JLabel("Sampai");
        lblSampai.setHorizontalAlignment(SwingConstants.RIGHT);
        lblSampai.setFont(new Font("Arial", Font.BOLD, 12));
        panel.add(lblSampai);

        tbSampai = new JDateChooser();
        panel.add(tbSampai);

        panel.add(btnCari);

        JButton btnReturTerpilih = new JButton("Retur Terpilih");
        btnReturTerpilih.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                int row = dbt.getSelectedRow();

                String ID = "";
                String IDProduk = "" + dbt.getValueAt(row, 2);
                String IDPembelian = "" + dbt.getValueAt(row, 0);
                String Jumlah = "" + dbt.getValueAt(row, 5);

                ID = JOptionPane.showInputDialog("Masukkan No. Retur : ");
                if (!ID.equals("")) {
                    try {
                        Koneksi db = new Koneksi();
                        db.connect();

                        PreparedStatement ps = db.connection
                                .prepareStatement("INSERT INTO retur values (?, ?, ?, ?, NOW()) ");

                        ps.setString(1, "" + ID);
                        ps.setString(2, "" + IDProduk);
                        ps.setString(3, "" + IDPembelian);
                        ps.setString(4, "" + Jumlah);

                        ps.executeUpdate();
                        JOptionPane.showMessageDialog(null,
                                "Retur pembelian berhasil di buat "
                                        + IDPembelian);
                        btnCari.doClick();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        panel.add(btnReturTerpilih);
    }
}
