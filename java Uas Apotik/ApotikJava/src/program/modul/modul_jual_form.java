package program.modul;

import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.PreparedStatement;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;

import javax.swing.JButton;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;

import program.database.*;
import program.*;

public class modul_jual_form extends JInternalFrame {
    protected static final Object[][] Object = null;
    private JTextField tbNo;
    private JTable tblData;
    private JScrollPane scrollPane;
    private JComboBox<String> cmbProduk;

    private Vector<String> ID_Produk = new Vector<String>();
    private Vector<String> Nama_Produk = new Vector<String>();
    private Vector<Object> Jumlah_Produk = new Vector<Object>();
    private Vector<Object> Harga_Jual = new Vector<Object>();

    private Vector<Vector<?>> data_data = new Vector<Vector<?>>();
    private Vector<String> data_cols = new Vector<String>();
    private modul_jual_form me;

    private int Total = 0;
    private JTextField tbHarga;

    public modul_jual_form() throws SQLException {
        me = this;

        setTitle("Jual Baru");
        setBackground(Color.decode("#eeeeee"));
        getContentPane().setLayout(null);

        JLabel lblNoPenjualan = new JLabel("No. Penjualan");
        lblNoPenjualan.setBounds(10, 11, 96, 14);
        getContentPane().add(lblNoPenjualan);

        tbNo = new JTextField();
        tbNo.setBounds(147, 8, 125, 20);
        getContentPane().add(tbNo);
        tbNo.setColumns(10);

        JLabel lblBarang = new JLabel("Produk/ Barang yang diJual");
        lblBarang.setBounds(10, 36, 128, 14);
        getContentPane().add(lblBarang);

        cmbProduk = new JComboBox<String>();
        cmbProduk.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent arg0) {
                int index = cmbProduk.getSelectedIndex();
                int harga = (Integer) Harga_Jual.get(index);

                tbHarga.setText("" + harga);
            }
        });

        cmbProduk.setBounds(147, 33, 285, 20);
        getContentPane().add(cmbProduk);

        data_cols.add("Kode_Barang");
        data_cols.add("Nama_Barang");
        data_cols.add("Stock");
        data_cols.add("Jumlah Barang");
        data_cols.add("Harga Barang");

        redrawTableData(false);
        getContentPane().add(scrollPane);

        tblData = new JTable(new JCustomModel(data_data, data_cols));
        scrollPane.setViewportView(tblData);
        tblData.setPreferredScrollableViewportSize(new Dimension(480, 294));
        tblData.setFillsViewportHeight(true);
        tblData.repaint();

        final JButton btnBatal = new JButton("Tutup");
        btnBatal.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                modul_dashboard d = Start.d;
                d.getContentPane().removeAll();
                d.repaint();
            }
        });

        btnBatal.setBounds(237, 431, 89, 23);
        getContentPane().add(btnBatal);

        JButton btnSimpan = new JButton("Simpan");
        btnSimpan.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Koneksi db = new Koneksi();
                if (validasiUdaOke()) {
                    boolean checker = false;

                    try {
                        db.connect();
                        PreparedStatement ps = db.connection
                                .prepareStatement("INSERT INTO penjualan values (?, NOW(), ?) ");
                        ps.setString(1, tbNo.getText());
                        ps.setString(2, Total + "");
                        ps.executeUpdate();

                        for (int i = 0; i < data_data.size(); i++) {
                            @SuppressWarnings("unchecked")
                            Vector<String> v = (Vector<String>) data_data
                                    .get(i);
                            PreparedStatement ps2 = db.connection
                                    .prepareStatement("INSERT INTO penjualan_detail(ID_Penjualan, ID_Produk, Jumlah, Harga) "
                                            + " VALUES (?, ?, ?, ?) ");

                            int harga = Integer.parseInt(v.get(4));

                            ps2.setString(1, tbNo.getText());
                            ps2.setString(2, v.get(0));
                            ps2.setInt(3, Integer.parseInt(v.get(3)));
                            ps2.setInt(4, harga);

                            ps2.executeUpdate();

                        }
                        checker = true;
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }

                    if (checker) {
                        JOptionPane.showMessageDialog(me,
                                "Entry Transaction Success");
                        btnBatal.doClick();
                    } else {
                        JOptionPane.showMessageDialog(me,
                                "Error Transactions to database");
                    }

                    me.dispose();
                }

            }
        });

        btnSimpan.setBounds(442, 431, 89, 23);
        getContentPane().add(btnSimpan);

        final JSpinner tbJumlah = new JSpinner(new SpinnerNumberModel(1, 1,
                10000, 1));
        tbJumlah.setBounds(385, 59, 47, 20);
        tbJumlah.setValue(1);

        getContentPane().add(tbJumlah);

        JButton btnInsert = new JButton("Tambah");
        btnInsert.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if (cekStock()) {
                    int index = cmbProduk.getSelectedIndex();

                    String kd = ID_Produk.get(index);
                    String value = Nama_Produk.get(index);
                    String qty = tbJumlah.getValue().toString();
                    String stock_sekarang = "" + Jumlah_Produk.get(index);
                    String harga = "" + tbHarga.getText();

                    Vector<String> vv = new Vector<String>();

                    if (!checkData(kd)) {
                        vv.add(kd);
                        vv.add(value);
                        vv.add(stock_sekarang);
                        vv.add(qty);
                        vv.add(harga);

                        data_data.add(vv);
                        redrawTableData(false);
                    }
                }else{
                    JOptionPane.showMessageDialog(me, "Barang tidak ada stock");
                }
            }
        });

        btnInsert.setBounds(147, 82, 96, 23);
        getContentPane().add(btnInsert);

        JButton btnHapus = new JButton("Reset");
        btnHapus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (JOptionPane.showConfirmDialog(null, "Reset ?", "",
                        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    redrawTableData(true);
                }
            }
        });
        btnHapus.setBounds(336, 431, 96, 23);
        getContentPane().add(btnHapus);

        JLabel lblHargaJual = new JLabel("Harga Jual");
        lblHargaJual.setBounds(10, 61, 69, 14);
        getContentPane().add(lblHargaJual);

        tbHarga = new JTextField();
        tbHarga.setEditable(false);
        tbHarga.setHorizontalAlignment(SwingConstants.RIGHT);
        tbHarga.setBounds(147, 58, 150, 20);
        tbHarga.setText("0");
        getContentPane().add(tbHarga);
        tbHarga.setColumns(10);

        JLabel lblJumlah = new JLabel("Jumlah");
        lblJumlah.setBounds(319, 62, 56, 14);
        getContentPane().add(lblJumlah);

        // custom
        Koneksi d = new Koneksi();
        d.connect();
        ResultSet rs = d.statement
                .executeQuery("SELECT ID_Produk, Nama_Produk, "
                        + Koneksi.QUERY_RUMUS_STOCK
                        + ", Harga_Jual FROM produk p ORDER BY Nama_Produk");

        if (!rs.wasNull()) {
            while (rs.next()) {
                String id = rs.getString(1);
                String value = rs.getString(2);
                int qty = rs.getInt(3);
                int harga = rs.getInt(4);

                this.ID_Produk.add(id);
                this.Nama_Produk.add(value);
                this.Jumlah_Produk.add(qty);
                this.Harga_Jual.add(harga);

                cmbProduk.addItem(value);
            }
        }

    }

    private void redrawTableData(boolean reset) {
        if (reset) {
            data_data.clear();
        }

        tblData = new JTable(new CustomModel(data_data, data_cols));

        tblData.setBounds(10, 95, 480, 294);
        tblData.setPreferredScrollableViewportSize(new Dimension(480, 294));
        tblData.setFillsViewportHeight(true);
        tblData.repaint();

        scrollPane = new JScrollPane(tblData);
        scrollPane.setHorizontalScrollBar(new JScrollBar());

        scrollPane.setBounds(10, 142, 521, 278);
        scrollPane.setPreferredSize(new Dimension(480, 294));
        scrollPane.repaint();

        repaint();
    }

    private boolean checkData(String kd) {
        for (int i = 0; i < data_data.size(); i++) {
            @SuppressWarnings("unchecked")
            Vector<String> v = (Vector<String>) data_data.get(i);
            if (v.get(0) == kd) {
                return true;
            }
        }

        return false;
    }

    private boolean validasiUdaOke() {
        if (tbNo.getText().equals("")) {
            JOptionPane
                    .showMessageDialog(me, "No Penjualan Tidak boleh kosong");
            return false;
        } else if (data_data.isEmpty()) {
            JOptionPane.showMessageDialog(me,
                    "Barang penjualan belum dimasukkan");
            return false;
        }

        return true;
    }

    private boolean cekStock() {
        int index = cmbProduk.getSelectedIndex();
        int stock = (Integer) Jumlah_Produk.get(index);
        if (stock > 0)
            return true;

        return false;
    }
}

class JCustomModel extends DefaultTableModel {

    public JCustomModel(Vector<Vector<?>> data, Vector<String> columnNames) {
        super(data, columnNames);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        if (column == 4 || column == 3) {
            return true;
        }
        return false;
    }
}
