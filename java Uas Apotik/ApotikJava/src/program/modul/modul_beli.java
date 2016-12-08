package program.modul;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import java.awt.*;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.DateFormat;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import javax.swing.JButton;
import com.toedter.calendar.JDateChooser;
import program.database.*;

public class modul_beli extends JInternalFrame {
    private JDateChooser tbDari;
    private JDateChooser tbSampai;
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public modul_beli() throws SQLException {
        this.setTitle("Data Pembelian");
        setBackground(Color.decode("#eeeeee"));

        final Koneksi d = new Koneksi();
        d.connect();

        final quick.dbtable.DBTable dbt = new quick.dbtable.DBTable();
        getContentPane().add(dbt);
        dbt.setConnection(d.connection);

        dbt.setSelectSql("SELECT ID, Tanggal, (SELECT SUM(Harga * (Stock_Sekarang - Stock_Awal)) FROM pembelian_detail pd WHERE pd.ID_Pembelian = p.ID) as `Total` FROM pembelian p ORDER BY Tanggal DESC");
        dbt.createControlPanel();
        dbt.setEditable(false);
        dbt.setControlPanelVisible(false);

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
            dbt.getColumn(0).setMinWidth(120);
            dbt.getColumn(1).setMinWidth(150);
            dbt.getColumn(2).setMinWidth(100);
        } catch (SQLException e) {
            // e.printStackTrace();
        }

        JButton btnCari = new JButton("Cari");
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
                    condition += " AND DATE_FORMAT(Tanggal, '%Y-%m-%d') >= '"
                            + txtDari + "' ";
                }
                if (!txtSampai.equals("")) {
                    condition += " AND DATE_FORMAT(Tanggal, '%Y-%m-%d') <= '"
                            + txtSampai + "' ";
                }

                String SqlQuery = "SELECT ID, Tanggal, (SELECT SUM(Harga * (Stock_Sekarang - Stock_Awal)) FROM pembelian_detail pd WHERE pd.ID_Pembelian = p.ID) as `Total` FROM pembelian p WHERE p.ID <> '' "
                        + condition + " ORDER BY Tanggal DESC";
                System.out.println(SqlQuery);

                dbt.setSelectSql(SqlQuery);

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
        JButton btnLihatDetail = new JButton("Lihat Detail");
        btnLihatDetail.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                int row = dbt.getSelectedRow();

                String v = (String) dbt.getValueAt(row, 0);
                String SqlSelect = "SELECT p.Nama_Produk as `Nama Produk`, (Stock_Sekarang - Stock_Awal) as `Jumlah`, pd.Harga FROM pembelian_detail pd LEFT JOIN Produk p ON p.ID_Produk = pd.ID_Produk WHERE ID_Pembelian = '"
                        + v + "' ORDER BY p.Nama_Produk ";

                JPanel pnl = new JPanel();

                try {
                    quick.dbtable.DBTable dbd = new quick.dbtable.DBTable();
                    getContentPane().add(dbd);
                    dbd.setConnection(d.connection);
                    dbd.setSelectSql(SqlSelect);
                    dbd.setEditable(false);

                    pnl.add(dbd);

                    dbd.refresh();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                JOptionPane.showMessageDialog(null, pnl, "Detail Penjualan", JOptionPane.INFORMATION_MESSAGE);

            }
        });
        panel.add(btnLihatDetail);
    }
}
