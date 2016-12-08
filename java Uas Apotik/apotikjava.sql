-- phpMyAdmin SQL Dump
-- version 4.3.11
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: 08 Des 2016 pada 14.55
-- Versi Server: 5.6.24
-- PHP Version: 5.5.24

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `apotikjava`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `pembelian`
--

CREATE TABLE IF NOT EXISTS `pembelian` (
  `ID` varchar(100) NOT NULL,
  `Tanggal` datetime DEFAULT NULL,
  `Total` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `pembelian`
--

INSERT INTO `pembelian` (`ID`, `Tanggal`, `Total`) VALUES
('PO/002', '2013-12-18 12:14:39', 0),
('PO/001', '2013-12-18 10:37:15', 0),
('PO00001', '2016-12-06 14:34:42', 0);

-- --------------------------------------------------------

--
-- Struktur dari tabel `pembelian_detail`
--

CREATE TABLE IF NOT EXISTS `pembelian_detail` (
  `ID` int(11) NOT NULL,
  `ID_Pembelian` varchar(100) DEFAULT NULL,
  `ID_Produk` varchar(100) DEFAULT NULL,
  `Stock_Awal` int(11) DEFAULT NULL,
  `Stock_Sekarang` int(11) DEFAULT NULL,
  `Harga` int(11) DEFAULT NULL,
  `Tanggal_Kadaluarasa` datetime DEFAULT NULL
) ENGINE=MyISAM AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `pembelian_detail`
--

INSERT INTO `pembelian_detail` (`ID`, `ID_Pembelian`, `ID_Produk`, `Stock_Awal`, `Stock_Sekarang`, `Harga`, `Tanggal_Kadaluarasa`) VALUES
(6, 'PO/001', '24', 0, 35, 1000, '2017-06-06 00:00:00'),
(5, 'PO/001', '4', 0, 25, 5000, '2017-06-06 00:00:00'),
(4, 'PO/001', '2', 0, 10, 7000, '2015-12-16 00:00:00'),
(7, 'PO/001', '21', 0, 45, 5000, '2017-06-06 00:00:00'),
(8, 'PO/001', '26', 0, 45, 5000, '2014-01-02 00:00:00'),
(9, 'PO/001', '19', 0, 20, 15000, '2013-12-31 00:00:00'),
(10, 'PO/002', '4', 25, 30, 10000, '2013-12-31 00:00:00'),
(11, 'PO00001', '36', 150, 165, 14000, '2016-12-06 00:00:00');

-- --------------------------------------------------------

--
-- Struktur dari tabel `penjualan`
--

CREATE TABLE IF NOT EXISTS `penjualan` (
  `ID` varchar(100) NOT NULL,
  `Tanggal` datetime DEFAULT NULL,
  `Total` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `penjualan`
--

INSERT INTO `penjualan` (`ID`, `Tanggal`, `Total`) VALUES
('SO001', '2013-12-18 14:30:21', 0);

-- --------------------------------------------------------

--
-- Struktur dari tabel `penjualan_detail`
--

CREATE TABLE IF NOT EXISTS `penjualan_detail` (
  `ID` int(11) NOT NULL,
  `ID_Penjualan` varchar(100) DEFAULT NULL,
  `ID_Produk` varchar(100) DEFAULT NULL,
  `Jumlah` int(11) DEFAULT NULL,
  `Harga` int(11) DEFAULT NULL
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `penjualan_detail`
--

INSERT INTO `penjualan_detail` (`ID`, `ID_Penjualan`, `ID_Produk`, `Jumlah`, `Harga`) VALUES
(1, 'SO001', '2', 1, 88000),
(2, 'SO001', '21', 4, 71000);

-- --------------------------------------------------------

--
-- Struktur dari tabel `produk`
--

CREATE TABLE IF NOT EXISTS `produk` (
  `ID_Produk` int(11) NOT NULL,
  `Nama_Produk` varchar(250) DEFAULT NULL,
  `Stock` int(11) DEFAULT NULL,
  `Harga_Jual` int(11) DEFAULT NULL
) ENGINE=MyISAM AUTO_INCREMENT=38 DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `produk`
--

INSERT INTO `produk` (`ID_Produk`, `Nama_Produk`, `Stock`, `Harga_Jual`) VALUES
(1, 'Amoksisilin sirup kering  125 mg/5 ml botol 60 ml', 0, 18000),
(2, 'Amoksisilin kapsul 500 mg Ktk @ 120 kap', 0, 88000),
(3, 'Antasida DOEN tablet Btl @ 1000 tab', 0, 95000),
(4, 'Antalgin tablet 500 mg Btl @ 1000 tab', 0, 34000),
(5, 'Deksametason inj. 5 mg/ml-2ml Ktk @ 100 amp', 0, 69000),
(6, 'Dektrometorfan Sirup 10 mg/5ml Btl @ 60 ml', 0, 20000),
(7, 'Dektrometorfan tablet 15 mg Btl @ 1000 tab', 0, 38000),
(8, 'Difenhidramin HCL Inj 10 mg/ml, 1 ml Ktk @ 100 amp', 0, 13000),
(9, 'Gliseril Guaiacolat tablet 100 mg Btl @ 1000 tab', 0, 66000),
(10, 'Glukosa larutan infus 5 % steril Btl 500 ml', 0, 45000),
(11, 'Ibuprofen tablet 200 mg Btl @ 100 tab', 0, 14000),
(12, 'Kloramfenikol kapsul 250 mg Btl @ 250 kaps', 0, 38000),
(13, 'Kotrimoksazol 480 mg Btl @ 100 tab', 0, 25000),
(14, 'Klorfeniramini Maleat tablet 4 mg Btl @ 1000 tab', 0, 50000),
(15, 'Natrium Klorida infus 0.9 % steril Btl 500 ml', 0, 11000),
(16, 'Parasetamol tablet 500  mg Btl @ 1000 tab', 0, 100000),
(17, 'Ringer Laktat infus steril Btl @ 500 ml', 0, 30000),
(18, 'Oralit Btl @ 1000 kaps', 0, 104000),
(19, 'Vitamin B kompleks Btl @ 1000 kaps', 0, 12000),
(20, 'Retinol 200000 IU Btl/30 tab', 0, 52000),
(21, 'Tablet Tambah Darah Bks / 30 tab', 0, 71000),
(22, 'Multivitamin syr btl', 0, 61000),
(23, 'Garam Oralit Ktk / 100 bks', 0, 42000),
(24, 'Kotrimoksazol 120 mg Btl / 100 tab', 0, 97000),
(25, 'Kotrimoksazol susp. Btl 60 ml', 0, 62000),
(26, 'Kloroquin Tablet Btl / 1000 tab', 0, 97000),
(27, 'OAT Kat 1 Pkt', 0, 92000),
(28, 'OAT Kat 2 Pkt', 0, 34000),
(29, 'OAT Kat 3 Pkt', 0, 22000),
(30, 'OAT Kat sisipan Pkt', 0, 21000),
(31, 'OAT Kat Anak Pkt', 0, 26000),
(32, 'Pyrantel 125 mg tablet Kotak @ 60 tablet', 0, 55000),
(33, 'Salep 2-4 Pot', 0, 23000),
(34, 'Infus set dewasa Kantong', 0, 20000),
(35, 'Infus set anak Kantong', 0, 101000),
(36, 'sample obT', 0, 10000);

-- --------------------------------------------------------

--
-- Struktur dari tabel `retur`
--

CREATE TABLE IF NOT EXISTS `retur` (
  `ID_Retur` varchar(100) NOT NULL,
  `ID_Produk` varchar(100) DEFAULT NULL,
  `ID_Pembelian` varchar(100) DEFAULT NULL,
  `Jumlah` int(11) DEFAULT NULL,
  `Tanggal` datetime DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `pembelian`
--
ALTER TABLE `pembelian`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `pembelian_detail`
--
ALTER TABLE `pembelian_detail`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `penjualan`
--
ALTER TABLE `penjualan`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `penjualan_detail`
--
ALTER TABLE `penjualan_detail`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `produk`
--
ALTER TABLE `produk`
  ADD PRIMARY KEY (`ID_Produk`);

--
-- Indexes for table `retur`
--
ALTER TABLE `retur`
  ADD PRIMARY KEY (`ID_Retur`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `pembelian_detail`
--
ALTER TABLE `pembelian_detail`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=12;
--
-- AUTO_INCREMENT for table `penjualan_detail`
--
ALTER TABLE `penjualan_detail`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `produk`
--
ALTER TABLE `produk`
  MODIFY `ID_Produk` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=38;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
