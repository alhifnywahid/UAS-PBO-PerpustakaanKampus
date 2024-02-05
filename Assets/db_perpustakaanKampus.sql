/*
SQLyog Ultimate v12.4.3 (64 bit)
MySQL - 10.4.32-MariaDB : Database - db_kampus
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`db_kampus` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;

USE `db_kampus`;

/*Table structure for table `buku` */

DROP TABLE IF EXISTS `buku`;

CREATE TABLE `buku` (
  `idBuku` int(12) NOT NULL AUTO_INCREMENT,
  `judulBuku` varchar(255) DEFAULT NULL,
  `penerbitBuku` varchar(255) DEFAULT NULL,
  `jumlahBuku` int(11) DEFAULT NULL,
  PRIMARY KEY (`idBuku`)
) ENGINE=InnoDB AUTO_INCREMENT=126 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `buku` */

insert  into `buku`(`idBuku`,`judulBuku`,`penerbitBuku`,`jumlahBuku`) values 
(117,'Metode Penelitian Kuantitatif','Alfabeta',15),
(118,'Metode Penelitian Kebijakan : Pendekatan Kuantitatif, Kualitatif, Kombinasi, R&D dan Penelitian Evaluasi','Alfabeta',20),
(119,'Panduan Lengkap : Membuat Desain Bangunan Ruko dan Perkantoran dengan AutoCad dan Google SketchUp','ANDI',8),
(120,'Kultur Jaringan : Teori dan Praktek','ANDI',12),
(121,'Struktur Beton Bertulang Tahan Gempa','ANDI',5),
(122,'Pengantar Kepemimpinan : Teori dan Praktek','ANDI',18),
(123,'Inovasi Pembelajaran Berbasis Life Skill dan Entrepreneurship','Ar-Ruzz Media',7),
(124,'Manajemen Layanan Khusus di Sekolah','Bumi Aksara',9),
(125,'Piawai Menembus Jurnal Terakreditasi','Bumi Aksara',11);

/*Table structure for table `laporan` */

DROP TABLE IF EXISTS `laporan`;

CREATE TABLE `laporan` (
  `id` varchar(20) NOT NULL,
  `nim` bigint(20) DEFAULT NULL,
  `nama` varchar(255) DEFAULT NULL,
  `judulBuku` varchar(255) DEFAULT NULL,
  `tanggal` date DEFAULT NULL,
  `petugas` varchar(255) DEFAULT NULL,
  `statusTransaksi` enum('peminjaman','pengembalian') DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `laporan` */

/*Table structure for table `mahasiswa` */

DROP TABLE IF EXISTS `mahasiswa`;

CREATE TABLE `mahasiswa` (
  `nim` bigint(12) NOT NULL,
  `nama` varchar(100) NOT NULL,
  `jenis_kelamin` enum('Laki - Laki','Perempuan') NOT NULL,
  `password` varchar(10) NOT NULL,
  `no_hp` varchar(12) NOT NULL,
  PRIMARY KEY (`nim`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `mahasiswa` */

insert  into `mahasiswa`(`nim`,`nama`,`jenis_kelamin`,`password`,`no_hp`) values 
(202211420000,'Amira Syafira','Perempuan','123456','085655005050'),
(202211420052,'Siti Sofiah','Perempuan','123456','085911245567'),
(202211420058,'Alhifny Wahid','Laki - Laki','123456','085655005555'),
(202211420065,'Muhammad Farhan','Laki - Laki','123456','085655002222'),
(202211420067,'Bondan Tiur Mahendra','Laki - Laki','123456','085655004444'),
(202211420083,'Moch Arif Samsul Rizal','Laki - Laki','123456','085655003333');

/*Table structure for table `petugas` */

DROP TABLE IF EXISTS `petugas`;

CREATE TABLE `petugas` (
  `nip` bigint(18) NOT NULL,
  `nama` varchar(100) NOT NULL,
  `no_tlp` varchar(12) NOT NULL,
  `password` varchar(12) NOT NULL,
  PRIMARY KEY (`nip`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `petugas` */

insert  into `petugas`(`nip`,`nama`,`no_tlp`,`password`) values 
(200009062022011001,'Abd Saifuddin','087877120433','123456789'),
(200009062022021001,'Siti Sofiah','087877120422','123456789'),
(200009062022031001,'Irna Safira','087877120444','123456789');

/*Table structure for table `transaksi_peminjaman_pengembalian` */

DROP TABLE IF EXISTS `transaksi_peminjaman_pengembalian`;

CREATE TABLE `transaksi_peminjaman_pengembalian` (
  `idTransaksi` int(11) NOT NULL AUTO_INCREMENT,
  `nim` bigint(20) DEFAULT NULL,
  `idBuku` int(20) DEFAULT NULL,
  `nip` bigint(20) DEFAULT NULL,
  `idPeminjaman` varchar(20) DEFAULT NULL,
  `idPengembalian` varchar(20) DEFAULT NULL,
  `tglPinjam` date DEFAULT NULL,
  `tglPengembalian` date DEFAULT NULL,
  `status` enum('dipinjam','dikembalikan') DEFAULT NULL,
  PRIMARY KEY (`idTransaksi`),
  KEY `nim` (`nim`),
  KEY `nip` (`nip`),
  KEY `idBuku` (`idBuku`),
  CONSTRAINT `transaksi_peminjaman_pengembalian_ibfk_1` FOREIGN KEY (`nim`) REFERENCES `mahasiswa` (`nim`),
  CONSTRAINT `transaksi_peminjaman_pengembalian_ibfk_3` FOREIGN KEY (`nip`) REFERENCES `petugas` (`nip`),
  CONSTRAINT `transaksi_peminjaman_pengembalian_ibfk_4` FOREIGN KEY (`idBuku`) REFERENCES `buku` (`idBuku`)
) ENGINE=InnoDB AUTO_INCREMENT=70 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `transaksi_peminjaman_pengembalian` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
