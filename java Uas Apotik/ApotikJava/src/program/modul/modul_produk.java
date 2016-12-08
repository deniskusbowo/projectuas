package program.modul;

import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import program.*;

import program.database.*;
import quick.dbtable.Column;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

public class modul_produk extends JInternalFrame {
    private JTextField tbCari;

    public modul_produk() throws SQLException {
        this.setTitle("Produk");
        setBackground(Color.decode("#eeeeee"));
        // terima data dari database
        Koneksi d = new Koneksi();
        d.connect();

        final quick.dbtable.DBTable dbt = new quick.dbtable.DBTable();
        dbt.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                if (arg0.getClickCount() == 2) {
                    int row = dbt.getSelectedRow();
                    String pid = "" + dbt.getValueAt(row, 0);

                    modul_produk_form pb;
                    // membuka form buat barang baru
                    try {
                        pb = new modul_produk_form(pid);
                        Start.d.getContentPane().removeAll();
                        Start.d.getContentPane().add(pb,
                                BorderLayout.CENTER);
                        pb.setVisible(true);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        getContentPane().add(dbt);
        dbt.setConnection(d.connection);

        dbt.setSelectSql("SELECT ID_Produk AS `Kode Produk`, Nama_Produk AS `Nama Produk`, ((SELECT IFNULL(SUM(Stock_Sekarang),0) FROM pembelian_detail pb WHERE pb.ID_Produk = p.ID_Produk) - "
                + "(SELECT IFNULL(SUM(Jumlah),0) FROM penjualan_detail pj WHERE pj.ID_Produk = p.ID_Produk)) as `Stock`, Harga_Jual AS `Harga Jual` "
                + "FROM Produk p "
                + "WHERE Nama_Produk LIKE '%%' "
                + "ORDER BY Nama_Produk");
        dbt.createControlPanel();
        dbt.setEditable(false);
        dbt.setControlPanelVisible(false);

        JPanel panel = new JPanel();
        FlowLayout flowLayout = (FlowLayout) panel.getLayout();
        flowLayout.setAlignment(FlowLayout.LEFT);
        dbt.add(panel, BorderLayout.NORTH);

        JLabel lblCariProduk = new JLabel("Cari Produk");
        lblCariProduk.setHorizontalAlignment(SwingConstants.RIGHT);
        lblCariProduk.setFont(new Font("Arial", Font.BOLD, 12));
        panel.add(lblCariProduk);

        try {
            dbt.refresh();
            dbt.getColumn(0).setMinWidth(50);
            dbt.getColumn(1).setMinWidth(380);
            dbt.getColumn(2).setMinWidth(100);
            dbt.getColumn(3).setMinWidth(100);
        } catch (SQLException e) {
            // e.printStackTrace();
        }

        JButton btnCari = new JButton("Cari");
        btnCari.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                String txt = tbCari.getText();
                dbt.setSelectSql("SELECT ID_Produk AS `ID`, Nama_Produk AS `Nama Produk`, "
                        + Koneksi.QUERY_RUMUS_STOCK
                        + ", Harga_Jual AS `Harga Jual` "
                        + "FROM Produk p "
                        + "WHERE Nama_Produk LIKE '%"
                        + txt
                        + "%' "
                        + "ORDER BY Nama_Produk");

                try {
                    dbt.refresh();
                    dbt.getColumn(1).setMinWidth(300);
                    dbt.getColumn(2).setMinWidth(100);
                    dbt.getColumn(3).setMinWidth(100);
                } catch (SQLException e) {
                    // e.printStackTrace();
                }

            }
        });
        tbCari = new JTextField();
        panel.add(tbCari);
        tbCari.setColumns(10);
        panel.add(btnCari);

        JButton btnPrint = new JButton("Print");
        btnPrint.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dbt.print();
            }
        });
        panel.add(btnPrint);

        JButton btnExport = new JButton("Ekspor");
        btnExport.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser jfc = new JFileChooser();
                jfc.setAcceptAllFileFilterUsed(false);
                jfc.showSaveDialog(null);

                String p = "";

                if (jfc.getSelectedFile() != null)
                    p = jfc.getSelectedFile().getAbsolutePath();

                if (p != null && p != "") {
                    try {
                        d.writeCSVfile(dbt, p);
                        JOptionPane.showMessageDialog(null,
                                "Ekspor file berhasil");
                    } catch (ClassNotFoundException e1) {
                        e1.printStackTrace();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        panel.add(btnExport);


        JButton btnDelete = new JButton("Hapus Terpilih");
        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int row = dbt.getSelectedRow();
                String pid = "" + dbt.getValueAt(row, 0);

                // beri oopsi hapus / tidak
                if (JOptionPane.showConfirmDialog(null, "Anda yakin hapus terpilih ?" + pid, "",
                        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    d.runQuery("DELETE FROM Produk WHERE ID_Produk = " + pid);
                    btnCari.doClick();

                }
            }
        });
        panel.add(btnDelete);
    }
}
