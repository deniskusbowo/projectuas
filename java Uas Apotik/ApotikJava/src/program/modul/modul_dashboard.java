package program.modul;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.plaf.InternalFrameUI;

public class modul_dashboard extends JFrame {

    public modul_dashboard() {
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // beri oopsi keluar jika iya
                if (JOptionPane.showConfirmDialog(null, "Anda yakin keluar dari aplikasi ?", "",
                        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    System.exit(1);
                }


            }
        });

        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
        } catch (InstantiationException ex) {
        } catch (IllegalAccessException ex) {
        } catch (UnsupportedLookAndFeelException ex) {
        }
        UIManager.put("defaultFont", new Font("Segoe UI", Font.PLAIN, 13));
        UIManager.put("MenuBar.font", new Font("Segoe UI", Font.PLAIN, 13));
        UIManager.put("Menu.font", new Font("Segoe UI", Font.PLAIN, 13));
        UIManager.put("MenuItem.font", new Font("Segoe UI", Font.PLAIN, 13));

        UIManager.put("MenuBar.background", Color.decode("#2cbda5"));
        UIManager.put("Menu.background", Color.decode("#2cbda5"));
        UIManager.put("MenuItem.background", Color.decode("#2cbda5"));

        UIManager.put("MenuBar.foreground", Color.decode("#ffffff"));
        UIManager.put("Menu.foreground", Color.decode("#ffffff"));
        UIManager.put("MenuItem.foreground", Color.decode("#ffffff"));

        // menubar
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        // menu : inventori
        JMenu mnInventori = new JMenu("Barang");
        menuBar.add(mnInventori);

        JMenuItem mntmBuatBarang = new JMenuItem("Buat Produk");
        mntmBuatBarang.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                try{
                    showFrame(new modul_produk_form(""));
                }catch (SQLException ex) {

                }
            }
        });
        mnInventori.add(mntmBuatBarang);
        JMenuItem mntmProduk = new JMenuItem("Data Produk");
        mntmProduk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                try {
                    // laporan produk, stock, dan harga jualnya
                    showFrame(new modul_produk());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        mnInventori.add(mntmProduk);
        JMenuItem mntmKeluar = new JMenuItem("Keluar");
        mntmKeluar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // beri oopsi keluar jika iya
                if (JOptionPane.showConfirmDialog(null, "Anda yakin keluar dari aplikasi ?", "",
                        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    System.exit(1);
                }
            }
        });
        JSeparator separator_1 = new JSeparator();
        mnInventori.add(separator_1);
        mnInventori.add(mntmKeluar);

        // menu : pembelian
        JMenu mnrPembelian = new JMenu("Pembelian");
        menuBar.add(mnrPembelian);
        JMenuItem mntmP = new JMenuItem("Buat Pembelian");
        mnrPembelian.add(mntmP);
        mntmP.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                modul_beli_form b;
                try {
                    // membuka form beli baru
                    showFrame(new modul_beli_form());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        JMenuItem mntmHistoriPembelian = new JMenuItem("Data Pembelian");
        mnrPembelian.add(mntmHistoriPembelian);
        mntmHistoriPembelian.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                modul_beli b;
                try {
                    // laporan pembelian
                    showFrame(new modul_beli());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        // menu : penjualan
        JMenu mnrPenjualan = new JMenu("Penjualan");
        menuBar.add(mnrPenjualan);
        JMenuItem mntmBuatBaru = new JMenuItem("Buat Penjualan");
        mnrPenjualan.add(mntmBuatBaru);
        mntmBuatBaru.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                modul_jual_form j;
                try {
                    // membuka form jual baru
                    showFrame(new modul_jual_form());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        JMenuItem mntmHistoriPenjualan = new JMenuItem("Data Penjualan");
        mntmHistoriPenjualan.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                modul_jual j;
                try {
                    // laporan penjualan
                    showFrame(new modul_jual());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        mnrPenjualan.add(mntmHistoriPenjualan);

        JMenu mnBantuan = new JMenu("Bantuan");
        menuBar.add(mnBantuan);

        JMenuItem mntmTentang = new JMenuItem("About Program");
        mntmTentang.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showFrame(new modul_tentang());
            }
        });
        mnBantuan.add(mntmTentang);

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setTitle("PROGRAM INVENTORI APOTIK JAVA");
        setMinimumSize(new Dimension(800, 600));
        setLocationRelativeTo(null);
        //setExtendedState(MAXIMIZED_BOTH);

        this.getContentPane().setBackground(Color.decode("#eeeeee"));
    }

    public void showFrame(JInternalFrame jf) {
        jf.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        jf.setClosable(true);
        jf.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        getContentPane().removeAll();
        getContentPane().add(jf, BorderLayout.CENTER);
        jf.setVisible(true);

    }
}
