import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.Random;

public class App {
    public static void main(String[] args) {
        new ViewLogin().setVisible(true);

    }
}

class ViewLogin extends JFrame {
    public static String vId;
    public JLabel title = new JLabel();
    public tPanel panel1 = new tPanel(10, true, 0, 0, 1000, 600, cColor.UNGU);
    public tPanel panel2 = new tPanel(10, true, 293, 106, 413, 443, cColor.WHITE);
    public tButton submitButton = new tButton("SUBMIT", 58, 327, 295, 31, cColor.GREEN, cColor.WHITE);

    public ViewLogin() {
        super();
        setSize(1000, 600);
        setLayout(null);
        setUndecorated(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        tTextField id = new tTextField(59, 163, 295, 31);
        tPasswordField password = new tPasswordField(59, 250, 295, 31);
        panel1.add(new tLabel("PERPUSTAKAAN KAMPUS", Color.white, cFonts.TITLE, 343, 46));
        panel2.add(new tLabel("Form Login", cColor.BLACK, cFonts.SUB_TITLE, 137, 49));
        panel2.add(new tLabel("Id", cColor.BLACK, cFonts.SUB_TITLE_H3, 198, 133));
        panel2.add(id);
        panel2.add(new tLabel("Password", cColor.BLACK, cFonts.SUB_TITLE_H3, 167, 220));
        panel2.add(password);
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                char[] rek = password.getPassword();
                vId = id.getText();
                String vPass = new String(rek);
                cLogin vLogin = new cLogin(vId, vPass);
                if (vLogin.isAdmin()) {
                    dispose();
                    new ViewAdmin().setVisible(true);
                } else if (vLogin.isMahasiswa()) {
                    dispose();
                    new ViewMhs().setVisible(true);
                } else {
                    tPopUp.popup(panel2, "Sepertinya username/password salah");
                }
            }
        });
        panel1.add(new tExit(933, panel1));
        panel2.add(submitButton);
        panel1.add(panel2);
        panel1.add(title);
        add(panel1);
    }

    public static String getId() {
        return vId;
    }
}

//
class ViewAdmin extends JFrame {
    public tPanel panel1 = new tPanel(10, true, 0, 62, 178, 538, cColor.UNGU);
    public tPanel panel2 = new tPanel(10, true, 178, 0, 822, 62, cColor.UNGU);
    public tPanel panel3 = new tPanel(10, true, 178, 62, 822, 538, cColor.WHITE90);
    public tLabel perpusAPP = new tLabel("PERPUS APP", cColor.UNGU, cFonts.TITLE_H1, 28, 17);
    public tLabel lTitle = new tLabel("Anggota       ", cColor.WHITE, cFonts.TITLE_H1, 24, 17);
    public tLabel lStatus = new tLabel("Status : Admin", cColor.WHITE, cFonts.EXIT, 30, 456);
    public tPanel pAnggota = new tPanel(10, true, 17, 18, 788, 503, cColor.WHITE);
    public tPanel pBuku = new tPanel(10, true, 17, 18, 788, 503, cColor.WHITE);
    public tPanel pTransaksi = new tPanel(10, true, 17, 18, 788, 505, cColor.WHITE);
    public tPanel pLaporan = new tPanel(10, true, 17, 18, 788, 505, cColor.WHITE);
    public tPanel pHeaderPeminjaman = new tPanel(10, true, 0, 0, 392, 34, cColor.RED);
    public tPanel pHeaderPengembalian = new tPanel(10, true, 396, 0, 392, 34, cColor.RED);
    public tButton bAnggota = new tButton("Anggota", 0, 0, 178, 44, cColor.GREEN, cColor.WHITE);
    public tButton bBuku = new tButton("Buku", 0, 44, 178, 44, cColor.UNGU, cColor.WHITE);
    public tButton bLaporan = new tButton("Laporan", 0, 132, 178, 44, cColor.UNGU, cColor.WHITE);
    public tTextField txfJudulBuku = new tTextField(602, 0, 186, 34);
    public tTextField txfPenerbitBuku = new tTextField(602, 45, 89, 34);
    public tTextField txfStokBuku = new tTextField(699, 45, 89, 34);
    public tButton tambahBuku = new tButton("Tambah", 602, 90, 186, 34, cColor.RED, cColor.WHITE);
    public tTextField txfCariBuku = new tTextField(602, 137, 186, 34);
    public tButton cariBuku = new tButton("Cari", 602, 182, 186, 34, cColor.RED, cColor.WHITE);
    public tTextField txfUJudulBuku = new tTextField(602, 226, 186, 34);
    public tTextField txfUPenerbitBuku = new tTextField(602, 274, 186, 34);
    public tButton ubahBuku = new tButton("Ubah", 602, 319, 186, 34, cColor.RED, cColor.WHITE);
    public tButton hapusBuku = new tButton("Hapus", 602, 366, 186, 34, cColor.RED, cColor.WHITE);
    public tLabel PEMINJAMAN_BUKU = new tLabel("PEMINJAMAN", cColor.WHITE, cFonts.TABLE_HEADER, 157, 6);
    public tLabel PENGEMBALIAN_BUKU = new tLabel("PENGEMBALIAN", cColor.WHITE, cFonts.TABLE_HEADER, 144, 6);
    public tLabel labelNimPeminjam = new tLabel("Masukkan NIM", cColor.BLACK, cFonts.TITLE_TEXT_TRX, 17, 15);
    public tLabel labelNamaPeminjam = new tLabel("Nama Peminjam", cColor.BLACK, cFonts.TITLE_TEXT_TRX, 17, 74);
    public tLabel labelPilhBuku = new tLabel("Pilih Buku yang ingin di pinjam", cColor.BLACK, cFonts.TITLE_TEXT_TRX, 17, 132);
    public tLabel labelTglPinjamPeminjaman = new tLabel("Tanggal Peminjaman", cColor.BLACK, cFonts.TITLE_TEXT_TRX, 17, 190);
    public tLabel labelKetPeminjam = new tLabel("Keterangan", cColor.BLACK, cFonts.TITLE_TEXT_TRX, 17, 248);
    public tLabel labelNimPengembali = new tLabel("Masukkan NIM", cColor.BLACK, cFonts.TITLE_TEXT_TRX, 17, 15);
    public tLabel labelNamaPengembali = new tLabel("Nama Pengembali", cColor.BLACK, cFonts.TITLE_TEXT_TRX, 17, 74);
    public tLabel labelNamaBukuPengembali = new tLabel("Pilih Buku yang di kembalikan", cColor.BLACK, cFonts.TITLE_TEXT_TRX, 17, 132);
    public tLabel labelTglPeminjamPengembalian = new tLabel("Tanggal Pengembalian", cColor.BLACK, cFonts.TITLE_TEXT_TRX, 17, 190);
    public tLabel labelTglKembaliPengembalian = new tLabel("Keterangan", cColor.BLACK, cFonts.TITLE_TEXT_TRX, 17, 248);
    public tTextField fieldNimPeminjam = new tTextField(17, 36, 363, 34);
    public tTextField fieldNamaPeminjam = new tTextField(17, 95, 363, 34);
    public tComboBox fieldPilihBuku = new tComboBox(mBuku.ambilDataBuku(), 17, 153, 363, 34);
    public tTextField fieldNimPengembali = new tTextField(17, 36, 363, 34);
    public tTextField fieldNamaPengembali = new tTextField(17, 95, 363, 34);
    public JTextArea fieldKeteranganPeminjam = new JTextArea();
    public JTextArea fieldKeteranganPengembalian = new JTextArea();
    public cScrollPane sKeteranganPeminjaman = new cScrollPane(fieldKeteranganPeminjam,17, 269, 363, 120);
    public cScrollPane sKeteranganPengembalian = new cScrollPane(fieldKeteranganPengembalian,17, 269, 363, 120);
    public tComboBox fieldBukuDiPinjam = new tComboBox(mPeminjaman.ambilDataBukuYangDipinjam("202211420058"), 17, 153, 363, 34);
    cDate datePeminjaman1 = new cDate("yyyy-MM-dd", 17, 211, 363, 34, cColor.WHITE);
    cDate datePengembalian2 = new cDate("yyyy-MM-dd", 17, 211, 363, 34, cColor.WHITE);
    tButton tTambahPeminjaman = new tButton("Tambah Peminjam", 16, 396, 364, 43, cColor.GREEN, cColor.WHITE);
    tButton tTambahPengembalian = new tButton("Tambah Pengembalian", 16, 396, 364, 43, cColor.GREEN, cColor.WHITE);
    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
    tPanel headerLaporanPengembalian = new tPanel(0, true, 0, 0, 788, 34, cColor.GREEN);
    tLabel labelLaporanPengembalian = new tLabel("LAPORAN PEMINJAMAN", cColor.WHITE, cFonts.SUB_TITLE_H3, 302, 5);
    tPanel headerLaporanPeminjaman = new tPanel(0, true, 0, 253, 788, 34, cColor.GREEN);
    tLabel labelLaporanPeminjaman = new tLabel("LAPORAN PEMINJAMAN", cColor.WHITE, cFonts.SUB_TITLE_H3, 302, 5);
    private final tPanel pPeminjaman = new tPanel(10, true, 0, 42, 392, 463, cColor.WHITE);
    private final tPanel pPengembalian = new tPanel(10, true, 396, 42, 392, 463, cColor.WHITE);
    private final tButton bTranksaksi = new tButton("Tranksaksi", 0, 88, 178, 44, cColor.UNGU, cColor.WHITE);

    public ViewAdmin() {
        super();
        final String phJudulBuku = "nama buku";
        final String phPenerbitBuku = "penerbit buku";
        final String phStokBuku = "stok buku";
        final String[] selectedOption = new String[1];
        cTable tGetDataMahasiswa = new cTable(mMahasiswa.getAllMahasiswaTableModel());
        cScrollPane finalGetMhs = new cScrollPane(tGetDataMahasiswa, 0, 0, 788, 503);
        cTable tGetBuku = new cTable(mBuku.ambilDataBuku());
        cScrollPane tesSc = new cScrollPane(tGetBuku, 0, 0, 588, 503);
        cTable ambilLaporanPeminjaman = new cTable(mPeminjaman.getPeminjamanTableModel());
        cScrollPane hasilLaporanPeminjaman = new cScrollPane(ambilLaporanPeminjaman, 0, 37, 787, 216);
        cTable ambilLaporanPengembalian = new cTable(mPeminjaman.getLaporanPengembalian());
        cScrollPane hasilLaporanPengembalian = new cScrollPane(ambilLaporanPengembalian, 0, 286, 787, 221);
        fieldKeteranganPengembalian.setFont(cFonts.TITLE_TEXT_TRX);
        fieldKeteranganPeminjam.setFont(cFonts.TITLE_TEXT_TRX);
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tGetDataMahasiswa.setDefaultRenderer(Object.class, centerRenderer);
        tGetBuku.setDefaultRenderer(Object.class, centerRenderer);
        ambilLaporanPeminjaman.setDefaultRenderer(Object.class, centerRenderer);
        ambilLaporanPengembalian.setDefaultRenderer(Object.class, centerRenderer);
        setSize(1000, 600);
        setLayout(null);
        setUndecorated(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        fieldBukuDiPinjam.removeAllItems();
        pBuku.setVisible(false);
        pLaporan.setVisible(false);
        pTransaksi.setVisible(false);
        lTitle.setHorizontalAlignment(SwingConstants.LEFT);
        txfJudulBuku.setText(phJudulBuku);
        txfPenerbitBuku.setText(phPenerbitBuku);
        txfStokBuku.setText(phStokBuku);
        txfCariBuku.setText(phJudulBuku);
        bAnggota.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lTitle.setText("Anggota");
                bAnggota.setBackground(cColor.GREEN);
                bBuku.setBackground(cColor.UNGU);
                bTranksaksi.setBackground(cColor.UNGU);
                bLaporan.setBackground(cColor.UNGU);
                pAnggota.setVisible(true);
                pBuku.setVisible(false);
                pLaporan.setVisible(false);
                pTransaksi.setVisible(false);
            }
        });
        bBuku.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lTitle.setText("Buku");
                bAnggota.setBackground(cColor.UNGU);
                bBuku.setBackground(cColor.GREEN);
                bTranksaksi.setBackground(cColor.UNGU);
                bLaporan.setBackground(cColor.UNGU);

                pAnggota.setVisible(false);
                pBuku.setVisible(true);
                pLaporan.setVisible(false);
                pTransaksi.setVisible(false);
            }
        });
        bTranksaksi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lTitle.setText("Tranksaksi");
                bAnggota.setBackground(cColor.UNGU);
                bBuku.setBackground(cColor.UNGU);
                bTranksaksi.setBackground(cColor.GREEN);
                bLaporan.setBackground(cColor.UNGU);
                pAnggota.setVisible(false);
                pBuku.setVisible(false);
                pLaporan.setVisible(false);
                pTransaksi.setVisible(true);
            }
        });
        bLaporan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pTransaksi.setVisible(false);
                lTitle.setText("Laporan");
                bAnggota.setBackground(cColor.UNGU);
                bBuku.setBackground(cColor.UNGU);
                bTranksaksi.setBackground(cColor.UNGU);
                bLaporan.setBackground(cColor.GREEN);
                pAnggota.setVisible(false);
                pBuku.setVisible(false);
                pLaporan.setVisible(true);
                ambilLaporanPeminjaman.setModel(mPeminjaman.getPeminjamanTableModel());
                ambilLaporanPengembalian.setModel(mPeminjaman.getLaporanPengembalian());
            }
        });
        cariBuku.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                String keyword = txfCariBuku.getText();
                if (keyword.equals(phJudulBuku) || keyword.isEmpty()) {
                    tPopUp.popup(panel3, "Silahkan isi kolom pencarian terlebih dahulu");
                } else {
                    if (mBuku.cariBuku(keyword).getRowCount() == 0) {
                        tPopUp.popup(panel3, "Sepertinya buku yang anda cari tidak ada.");
                    } else {
                        tGetBuku.setModel(mBuku.cariBuku(keyword));
                    }
                }
            }
        });
        tambahBuku.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String judul = txfJudulBuku.getText();
                String penerbit = txfPenerbitBuku.getText();
                String stok = txfStokBuku.getText();
                if (judul.equals(phJudulBuku) || penerbit.equals(phPenerbitBuku) || stok.equals(phStokBuku)) {
                    tPopUp.popup(panel3, "Harap lengkapi semua bidang.");
                } else {
                    if (!judul.isEmpty() && !penerbit.isEmpty() && !stok.isEmpty()) {
                        try {
                            int finalStok = Integer.parseInt(stok);
                            if (finalStok < 0) {
                                tPopUp.popup(panel3, "Stok tidak boleh kurang dari nol");
                            } else {
                                mBuku.tambahBuku(judul, penerbit, finalStok, panel3);
                                tGetBuku.setModel(mBuku.ambilDataBuku());
                                fieldPilihBuku.refreshComboBox(mBuku.ambilDataBuku());
                                ambilLaporanPeminjaman.setModel(mPeminjaman.getPeminjamanTableModel());
                                ambilLaporanPengembalian.setModel(mPeminjaman.getLaporanPengembalian());
                            }
                        } catch (NumberFormatException ex) {
                            tPopUp.popup(panel3, "Stok harus berupa angka.");
                        }
                    } else {
                        tPopUp.popup(panel3, "Harap lengkapi semua bidang.");
                    }
                }
            }
        });
        tGetBuku.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String[] selectedRowData = tGetBuku.getSelectedRowData();
                if (selectedRowData != null) {
                    txfUJudulBuku.setText(selectedRowData[1]);
                    txfUPenerbitBuku.setText(selectedRowData[2]);
                }
            }
        });
        ubahBuku.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String[] selectedRowData = tGetBuku.getSelectedRowData();
                String judul = txfUJudulBuku.getText();
                String penerbit = txfUPenerbitBuku.getText();
                if (judul.isEmpty() || penerbit.isEmpty()) {
                    tPopUp.popup(panel3, "Kolom wajib di isi!!!");
                } else {
                    mBuku.ubahBuku(selectedRowData[1], selectedRowData[2], judul, penerbit, panel3);
                    tGetBuku.setModel(mBuku.ambilDataBuku());
                    fieldPilihBuku.refreshComboBox(mBuku.ambilDataBuku());
                }
            }
        });
        hapusBuku.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String[] selectedRowData = tGetBuku.getSelectedRowData();
                int value = Integer.parseInt(selectedRowData[0]);
                mBuku.hapusBuku(value, panel3);
                tGetBuku.setModel(mBuku.ambilDataBuku());
                fieldPilihBuku.refreshComboBox(mBuku.ambilDataBuku());
            }
        });

        fieldNamaPengembali.setEditable(false);
        fieldNamaPeminjam.setEditable(false);
        fieldNimPeminjam.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                String nim = fieldNimPeminjam.getText().trim();
                if (!nim.isEmpty()) {
                    mMahasiswa mahasiswa = mMahasiswa.getMahasiswaByNIM(nim);
                    if (mahasiswa != null) {
                        fieldNamaPeminjam.setText(mahasiswa.getNama());
                    } else {
                        fieldNamaPeminjam.setText("Mahasiswa tidak ditemukan");
                    }
                } else {
                    fieldNamaPeminjam.setText("");
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                insertUpdate(e);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });
        fieldNimPengembali.getDocument().addDocumentListener(new DocumentListener() {
            final String nim = fieldNimPengembali.getText().trim();

            @Override
            public void insertUpdate(DocumentEvent e) {
                String nim = fieldNimPengembali.getText().trim();
                if (!nim.isEmpty()) {
                    mMahasiswa mahasiswa = mMahasiswa.cekPeminjaman(nim);
                    if (mahasiswa != null) {
                        fieldNamaPengembali.setText(mahasiswa.getNim());
                        fieldNamaPengembali.setText(mahasiswa.getNama());
                        System.out.println(mahasiswa.getNim());
                        fieldBukuDiPinjam.refreshComboBox(mPeminjaman.ambilDataBukuYangDipinjam(nim));
                    } else {
                        fieldNamaPengembali.setText("Mahasiswa tidak ditemukan");
                    }
                } else {
                    fieldNamaPengembali.setText("");
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                insertUpdate(e);
                fieldBukuDiPinjam.refreshComboBox(mPeminjaman.ambilDataBukuYangDipinjam(nim));
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                fieldBukuDiPinjam.refreshComboBox(mPeminjaman.ambilDataBukuYangDipinjam(nim));
            }
        });

        datePeminjaman1.getDateEditor().addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if ("date".equals(evt.getPropertyName())) {
                    Date tglPeminjamanValue = datePeminjaman1.getDate();
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(tglPeminjamanValue);
                    cal.add(Calendar.DATE, 4);
                    SimpleDateFormat sdf = new SimpleDateFormat("EEE dd - MMMM - yyyy", new Locale("id", "ID"));
                    String formattedDate = sdf.format(cal.getTime());
                    fieldKeteranganPeminjam.setText("Buku wajib dikembalikan pada " + formattedDate);
                }
            }
        });
        datePengembalian2.getDateEditor().addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if ("date".equals(evt.getPropertyName())) {
                    if (!fieldNimPengembali.getText().trim().isEmpty() && fieldNimPengembali.getText().trim() != null && fieldBukuDiPinjam.getSelectedIdBuku() != null) {
                        Date[] getTgl = mPeminjaman.getPerbandinganDate(fieldNimPengembali.getText().trim(), fieldBukuDiPinjam.getSelectedIdBuku());
                        Calendar cal = Calendar.getInstance(), cal2 = Calendar.getInstance();
                        cal.setTime(getTgl[1]);
                        cal.add(Calendar.DATE, 4);
                        Date dateNow = datePengembalian2.getDate();
                        if (dateNow.after(getTgl[0])) {
                            long selisihHari = (dateNow.getTime() - getTgl[0].getTime()) / (1000 * 60 * 60 * 24);
                            if (selisihHari == 0){
                                fieldKeteranganPengembalian.setText("Pengembalian tepat waktu.");
                            } else {
                                fieldKeteranganPengembalian.setText("Pengembalian terlambat! Telat " + selisihHari + " hari.\nAnda harus membayar denda sebesar Rp." + selisihHari*5000 + ".");
                            }
                        } else {
                            fieldKeteranganPengembalian.setText("Pengembalian tepat waktu.");
                        }
                    }
                }
            }
        });
        tTambahPeminjaman.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String ambilNim = fieldNimPeminjam.getText();
                Date ambilTglPeminjaman = datePeminjaman1.getDate();
                Calendar cal = Calendar.getInstance();
                cal.setTime(ambilTglPeminjaman);
                cal.add(Calendar.DAY_OF_MONTH, 4);
                Date ambilTglPengembalian = cal.getTime();
                if (ambilNim.isEmpty() || fieldNamaPeminjam.getText().equals("Mahasiswa tidak ditemukan")) {
                    tPopUp.popup(panel3, "NIM Peminjam Wajib di isi dengan benar.");
                } else {
                    if (ambilTglPeminjaman == null) {
                        tPopUp.popup(panel3, "Tanggal Peminjaman Wajib di Isi.");
                    } else {
                        if (fieldPilihBuku.getSelectedItem() == null) {
                            tPopUp.popup(panel3, "Silahkan pilih buku yang ingin di pinjam.");
                        } else {
                            fieldKeteranganPengembalian.setText(ambilTglPengembalian.toString());
                            mMahasiswa getNimAndNama = mMahasiswa.getMahasiswaByNIM(ambilNim);
                            mPeminjaman.tambahPeminjaman(getNimAndNama.getNim(), fieldPilihBuku.getSelectedIdBuku(), ViewLogin.getId(), ambilTglPeminjaman, ambilTglPengembalian, "dipinjam", panel3);
                            ambilLaporanPeminjaman.setModel(mPeminjaman.getPeminjamanTableModel());
                            ambilLaporanPengembalian.setModel(mPeminjaman.getLaporanPengembalian());
                            tGetBuku.setModel(mBuku.ambilDataBuku());
                        }
                    }
                }
            }
        });
        tTambahPengembalian.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Date tglPengembalian = datePengembalian2.getDate();
                if (fieldBukuDiPinjam.getSelectedItem() == null) {
                    tPopUp.popup(panel3, "Pilih buku yang ingin di kembalikan");
                } else {
                    if (tglPengembalian == null) {
                        tPopUp.popup(panel3, "Tanggal Peminjaman Wajib di Isi.");
                    } else {
                        mPeminjaman.tambahkanPengembalian(fieldNimPengembali.getText().trim(),fieldBukuDiPinjam.getSelectedIdBuku(),tglPengembalian,ViewLogin.getId(),panel3);
                    }
                }
                ambilLaporanPeminjaman.setModel(mPeminjaman.getPeminjamanTableModel());
                ambilLaporanPengembalian.setModel(mPeminjaman.getLaporanPengembalian());
                tGetBuku.setModel(mBuku.ambilDataBuku());
                fieldBukuDiPinjam.refreshComboBox(mPeminjaman.ambilDataBukuYangDipinjam(fieldNimPengembali.getText()));
            }
        });

        headerLaporanPeminjaman.add(labelLaporanPeminjaman);
        headerLaporanPengembalian.add(labelLaporanPengembalian);
        pLaporan.add(headerLaporanPeminjaman);
        pLaporan.add(headerLaporanPengembalian);
        pLaporan.add(hasilLaporanPeminjaman);
        pLaporan.add(hasilLaporanPengembalian);
        tAction.textFieldListenera(txfJudulBuku, phJudulBuku);
        tAction.textFieldListenera(txfPenerbitBuku, phPenerbitBuku);
        tAction.textFieldListenera(txfStokBuku, phStokBuku);
        tAction.textFieldListenera(txfCariBuku, phJudulBuku);
        pHeaderPeminjaman.add(PEMINJAMAN_BUKU);
        pHeaderPengembalian.add(PENGEMBALIAN_BUKU);
        pPeminjaman.add(labelNimPeminjam);
        pPeminjaman.add(labelNamaPeminjam);
        pPeminjaman.add(labelPilhBuku);
        pPeminjaman.add(labelTglPinjamPeminjaman);
        pPeminjaman.add(labelKetPeminjam);
        pPeminjaman.add(datePeminjaman1);
        pPeminjaman.add(tTambahPeminjaman);
        pPengembalian.add(labelNimPengembali);
        pPengembalian.add(labelNamaPengembali);
        pPengembalian.add(labelNamaBukuPengembali);
        pPengembalian.add(labelTglPeminjamPengembalian);
        pPengembalian.add(labelTglKembaliPengembalian);
        pPeminjaman.add(sKeteranganPeminjaman);
        pPengembalian.add(sKeteranganPengembalian);
        pPengembalian.add(datePengembalian2);
        pPengembalian.add(tTambahPengembalian);
        pPeminjaman.add(fieldNimPeminjam);
        pPeminjaman.add(fieldNamaPeminjam);
        pPeminjaman.add(fieldPilihBuku);
        pPengembalian.add(fieldNimPengembali);
        pPengembalian.add(fieldNamaPengembali);
        pPengembalian.add(fieldBukuDiPinjam);
        pTransaksi.add(pHeaderPeminjaman);
        pTransaksi.add(pHeaderPengembalian);
        pTransaksi.add(pPeminjaman);
        pTransaksi.add(pPengembalian);
        pAnggota.add(finalGetMhs);
        pBuku.add(tesSc);
        pBuku.add(txfJudulBuku);
        pBuku.add(txfPenerbitBuku);
        pBuku.add(txfStokBuku);
        pBuku.add(tambahBuku);
        pBuku.add(txfCariBuku);
        pBuku.add(cariBuku);
        pBuku.add(txfUJudulBuku);
        pBuku.add(txfUPenerbitBuku);
        pBuku.add(ubahBuku);
        pBuku.add(hapusBuku);
        panel1.add(new tLogOut(0,494,panel3,this));
        panel2.add(new tExit(755, this));
        add(perpusAPP);
        panel2.add(lTitle);
        panel3.add(pAnggota);
        panel3.add(pBuku);
        panel3.add(pTransaksi);
        panel3.add(pLaporan);
        panel1.add(bAnggota);
        panel1.add(bBuku);
        panel1.add(bTranksaksi);
        panel1.add(bLaporan);
        panel1.add(lStatus);
        add(panel1);
        add(panel2);
        add(panel3);
    }
}

class ViewMhs extends ViewAdmin {
    tLabel title = new tLabel("Laporan Peminjaman          ", cColor.WHITE, cFonts.TITLE_H1, 24, 17);
    tPanel pCekPeminjaman = new tPanel(10, true, 17, 18, 788, 505, cColor.GREEN);
    tPanel pCekPengembalian = new tPanel(10, true, 17, 18, 788, 505, cColor.UNGU);
    tButton tCekPeminjaman = new tButton("Peminjaman", 0, 0, 178, 44, cColor.GREEN, cColor.WHITE);
    tButton tCekPengembalian = new tButton("Pengembalian", 0, 44, 178, 44, cColor.UNGU, cColor.WHITE);
    cTable tabelCekPeminjaman = new cTable(mPeminjaman.getLaporanPeminjamanMhs(ViewLogin.getId()));
    cScrollPane tCPeminjaman = new cScrollPane(tabelCekPeminjaman, 0, 0, 788, 505);
    cTable tabelCekPenegmbalian = new cTable(mPeminjaman.getLaporanPengembalianMhs(ViewLogin.getId()));
    cScrollPane tCPengembalian = new cScrollPane(tabelCekPenegmbalian, 0, 0, 788, 505);
    DefaultTableCellRenderer centerTable = new DefaultTableCellRenderer();

    public ViewMhs() {
        panel1.removeAll();
        panel1.revalidate();
        panel1.repaint();
        panel2.removeAll();
        panel2.revalidate();
        panel2.repaint();
        panel3.removeAll();
        panel3.revalidate();
        panel3.repaint();
        lStatus.setText("Status : Mahasiswa");
        lStatus.setFont(cFonts.LSTATUS);
        title.setHorizontalAlignment(title.LEFT);
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        tabelCekPeminjaman.setDefaultRenderer(Object.class, centerRenderer);
        tabelCekPenegmbalian.setDefaultRenderer(Object.class, centerRenderer);
        tCekPeminjaman.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                title.setText("Laporan Peminjaman          ");
                tCekPeminjaman.setBackground(cColor.GREEN);
                tCekPengembalian.setBackground(cColor.UNGU);
                pCekPeminjaman.setVisible(true);
                pCekPengembalian.setVisible(false);
            }
        });
        tCekPengembalian.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                title.setText("Laporan Pengembalian");
                tCekPeminjaman.setBackground(cColor.UNGU);
                tCekPengembalian.setBackground(cColor.GREEN);
                pCekPeminjaman.setVisible(false);
                pCekPengembalian.setVisible(true);
            }
        });
        pCekPeminjaman.add(tCPeminjaman);
        pCekPengembalian.add(tCPengembalian);
        panel2.add(title);
        panel2.add(new tExit(755, this));
        panel3.add(pCekPeminjaman);
        panel3.add(pCekPengembalian);
        panel1.add(new tLogOut(0,494,panel3,this));
        panel1.add(lStatus);
        panel1.add(tCekPengembalian);
        panel1.add(tCekPeminjaman);
    }
}

class DbConn {
    private static Connection conn;

    private DbConn() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/db_kampus";
            String user = "root";
            String password = "";
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        tambahDataKeLaporan();
    }

    public static Connection getInstance() {
        if (conn == null) {
            new DbConn();
        }
        return conn;
    }

    public static void tambahDataKeLaporan() {
        try {
            String sql = "INSERT INTO laporan (id, nim, nama, judulBuku, tanggal, petugas, statusTransaksi) " +
                    "SELECT " +
                    "    CASE " +
                    "        WHEN t.status = 'dipinjam' THEN t.idPeminjaman " +
                    "        WHEN t.status = 'dikembalikan' THEN t.idPengembalian " +
                    "    END AS id, " +
                    "    t.nim, " +
                    "    m.nama, " +
                    "    b.judulBuku, " +
                    "    CASE " +
                    "        WHEN t.status = 'dipinjam' THEN t.tglPinjam " +
                    "        WHEN t.status = 'dikembalikan' THEN t.tglPengembalian " +
                    "    END AS tanggal, " +
                    "    p.nama AS petugas, " +
                    "    CASE " +
                    "        WHEN t.status = 'dipinjam' THEN 'peminjaman' " +
                    "        WHEN t.status = 'dikembalikan' THEN 'pengembalian' " +
                    "    END AS statusTransaksi " +
                    "FROM " +
                    "    transaksi_peminjaman_pengembalian t " +
                    "    JOIN mahasiswa m ON t.nim = m.nim " +
                    "    JOIN buku b ON t.idBuku = b.idBuku " +
                    "    JOIN petugas p ON t.nip = p.nip " +
                    "    LEFT JOIN laporan l ON " +
                    "        (t.status = 'dipinjam' AND l.id = t.idPeminjaman) OR " +
                    "        (t.status = 'dikembalikan' AND l.id = t.idPengembalian) " +
                    "WHERE " +
                    "    l.id IS NULL";
            Statement stmt = conn.createStatement();
            int rowsAffected = stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


class cLogin {
    private boolean sAdmin;
    private boolean sMhs;
    private String nip;

    public cLogin(String username, String password) {
        try {
            Connection conn = DbConn.getInstance();
            Statement stmt = conn.createStatement();
            String queryMahasiswa = "SELECT * FROM mahasiswa WHERE nim = '" + username + "' AND password = '" + password + "'";
            ResultSet rsMahasiswa = stmt.executeQuery(queryMahasiswa);
            if (rsMahasiswa.next()) {
                sMhs = true;
            } else {
                String queryPetugas = "SELECT * FROM petugas WHERE nip = '" + username + "' AND password = '" + password + "'";
                ResultSet rsPetugas = stmt.executeQuery(queryPetugas);
                if (rsPetugas.next()) {
                    sAdmin = true;
                    nip = rsPetugas.getString("nip");
                } else {
                    // Username atau password salah
                }
                rsPetugas.close();
            }
            rsMahasiswa.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isAdmin() {
        return sAdmin;
    }

    public boolean isMahasiswa() {
        return sMhs;
    }

    public String getNIP() {
        return nip;
    }
}
class mPeminjaman {
    public static void tambahPeminjaman(String nim, String idBuku, String nip, Date tglPinjam, Date tglPengembalian, String status, java.awt.Container parentComponent) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = DbConn.getInstance();
            String checkQuery = "SELECT COUNT(*) FROM transaksi_peminjaman_pengembalian WHERE nim = ? AND idBuku = ? AND STATUS = 'dipinjam'";
            stmt = conn.prepareStatement(checkQuery);
            stmt.setString(1, nim);
            stmt.setString(2, idBuku);
//            stmt.setDate(3, new java.sql.Date(tglPinjam.getTime()));
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                if (count > 0) {
                    tPopUp.popup(parentComponent, "Peminjaman dengan nim, dan judul buku tersebut sudah ada di database.");
                    return;
                }
            }
            String insertQuery = "INSERT INTO transaksi_peminjaman_pengembalian (nim, idBuku, nip, idPeminjaman, tglPinjam, tglPengembalian, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(insertQuery);
            stmt.setString(1, nim);
            stmt.setString(2, idBuku);
            stmt.setString(3, nip);
            stmt.setString(4, tAction.generatedId('B'));
            stmt.setDate(5, new java.sql.Date(tglPinjam.getTime()));
            stmt.setDate(6, new java.sql.Date(tglPengembalian.getTime()));
            stmt.setString(7, status);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                String restockQuery = "UPDATE buku SET jumlahBuku = jumlahBuku - 1 WHERE idBuku = " + idBuku + ";";
                stmt = conn.prepareStatement(restockQuery);
                int booksRestocked = stmt.executeUpdate();
                if (booksRestocked > 0) {
                    tPopUp.popup(parentComponent, "Peminjaman berhasil dicatat.");
                } else {
                    tPopUp.popup(parentComponent, "Gagal merestorasi jumlah buku.");
                }
                tPopUp.popup(parentComponent, "Peminjaman berhasil ditambahkan ke database.");
            } else {
                tPopUp.popup(parentComponent, "Gagal menambahkan peminjaman ke database.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static DefaultTableModel ambilDataBukuYangDipinjam(String nim) {
        Connection conn = null;
        PreparedStatement stmt = null;
        DefaultTableModel tm = null;

        try {
            conn = DbConn.getInstance();

            String[] dataHeader = {"ID Buku", "Judul Buku", "Penerbit", "Jumlah"};
            tm = new DefaultTableModel(null, dataHeader);
            String query = "SELECT buku.idBuku, buku.judulBuku, buku.penerbitBuku, buku.jumlahBuku " +
                    "FROM buku " +
                    "INNER JOIN transaksi_peminjaman_pengembalian " +
                    "ON buku.idBuku = transaksi_peminjaman_pengembalian.idBuku " +
                    "WHERE transaksi_peminjaman_pengembalian.nim = ? AND transaksi_peminjaman_pengembalian.status = 'dipinjam'";

            stmt = conn.prepareStatement(query);
            stmt.setString(1, nim);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Object[] rowData = {rs.getInt("idBuku"), rs.getString("judulBuku"),
                        rs.getString("penerbitBuku"), rs.getInt("jumlahBuku")};
                tm.addRow(rowData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return tm;
    }

    public static void tambahkanPengembalian(String nim, String idBuku, Date tglPengembalian, String nip, java.awt.Container parentComponent) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = DbConn.getInstance();
            String checkIdTransaksiQuery = "SELECT idTransaksi, idPeminjaman, tglPengembalian FROM transaksi_peminjaman_pengembalian WHERE nim = ? AND idBuku = ? AND STATUS = 'dipinjam'";
            stmt = conn.prepareStatement(checkIdTransaksiQuery);
            stmt.setString(1, nim);
            stmt.setString(2, idBuku);
            rs = stmt.executeQuery();
            if (rs.next()) {
                String idTransaksi = rs.getString("idTransaksi");
                String idPeminjaman = rs.getString("idPeminjaman");
                Date tglPengmbalian = rs.getDate("tglPengembalian");
                String idPengembalian = "R" + idPeminjaman.substring(1);
                String updateQuery = "UPDATE transaksi_peminjaman_pengembalian SET status = 'dikembalikan', idPengembalian = ?, tglPengembalian = ?, nip = ? WHERE idTransaksi = ?";
                stmt = conn.prepareStatement(updateQuery);
                stmt.setString(1, idPengembalian);
                stmt.setDate(2, new java.sql.Date(tglPengembalian.getTime()));
                stmt.setString(3, nip);
                stmt.setString(4, idTransaksi);
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    String checkLateQuery = "SELECT tglPinjam FROM transaksi_peminjaman_pengembalian WHERE idTransaksi = ?";
                    stmt = conn.prepareStatement(checkLateQuery);
                    stmt.setString(1, idTransaksi);
                    rs = stmt.executeQuery();
                    if (rs.next()) {
                        Date tglPeminjaman = rs.getDate("tglPinjam");
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(tglPeminjaman);
                        cal.add(Calendar.DATE, 4);
                        Date tglHarusKembali = tglPengmbalian;

                        // Bandingkan tanggal pengembalian dengan tanggal yang diharapkan untuk mengembalikan buku
                        if (tglPengembalian.after(tglHarusKembali)) {
                            long selisihHari = (tglPengembalian.getTime() - tglHarusKembali.getTime()) / (1000 * 60 * 60 * 24);
                            tPopUp.popup(parentComponent, "Pengembalian terlambat! Telat " + selisihHari + " hari.");
                        } else {
                            tPopUp.popup(parentComponent, "Pengembalian tepat waktu.");
                        }
                    }
                    String restockQuery = "UPDATE buku SET jumlahBuku = jumlahBuku + 1 WHERE idBuku IN (SELECT idBuku FROM transaksi_peminjaman_pengembalian WHERE idTransaksi = ?)";
                    stmt = conn.prepareStatement(restockQuery);
                    stmt.setString(1, idTransaksi);
                    int booksRestocked = stmt.executeUpdate();
                    if (booksRestocked > 0) {
                        tPopUp.popup(parentComponent, "Pengembalian berhasil dicatat.");
                    } else {
                        tPopUp.popup(parentComponent, "Gagal merestorasi jumlah buku.");
                    }
                } else {
                    tPopUp.popup(parentComponent, "Gagal menambahkan pengembalian.");
                }
            } else {
                tPopUp.popup(parentComponent, "Data transaksi tidak ditemukan.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
    public static Date[] getPerbandinganDate(String nim, String idBuku) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Date[] tglPengembalian = new Date[2]; // Inisialisasi array dengan dua elemen
        try {
            conn = DbConn.getInstance();
            String checkIdTransaksiQuery = "SELECT tglPengembalian, tglPinjam FROM transaksi_peminjaman_pengembalian WHERE nim = ? AND idBuku = ? AND STATUS = 'dipinjam'";
            stmt = conn.prepareStatement(checkIdTransaksiQuery);
            stmt.setString(1, nim);
            stmt.setString(2, idBuku);
            rs = stmt.executeQuery();
            if (rs.next()) {
                tglPengembalian[0] = rs.getDate("tglPengembalian");
                tglPengembalian[1] = rs.getDate("tglPinjam");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return tglPengembalian;
    }




    public static DefaultTableModel getPeminjamanTableModel() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("NIM");
        model.addColumn("NAMA");
        model.addColumn("JUDUL BUKU");
        model.addColumn("TANGGAL");
        model.addColumn("PETUGAS");
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            DbConn.tambahDataKeLaporan();
            conn = DbConn.getInstance();
            String sql = "SELECT id, nim, nama, judulBuku, tanggal, petugas, statusTransaksi " +
                    "FROM laporan " +
                    "WHERE statusTransaksi = 'peminjaman'";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                String id = rs.getString("id");
                String nim = rs.getString("nim");
                String nama = rs.getString("nama");
                String judulBuku = rs.getString("judulBuku");
                String tanggal = rs.getString("tanggal");
                String petugas = rs.getString("petugas");
                String statusTransaksi = rs.getString("statusTransaksi");
                model.addRow(new String[]{id, nim, nama, judulBuku, tanggal, petugas});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return model;
    }

    public static DefaultTableModel getLaporanPengembalian() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("NIM");
        model.addColumn("NAMA");
        model.addColumn("JUDUL BUKU");
        model.addColumn("TANGGAL");
        model.addColumn("PETUGAS");
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            DbConn.tambahDataKeLaporan();
            conn = DbConn.getInstance();
            String sql = "SELECT id, nim, nama, judulBuku, tanggal, petugas, statusTransaksi " +
                    "FROM laporan " +
                    "WHERE statusTransaksi = 'pengembalian'";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                String id = rs.getString("id");
                String nim = rs.getString("nim");
                String nama = rs.getString("nama");
                String judulBuku = rs.getString("judulBuku");
                String tanggal = rs.getString("tanggal");
                String petugas = rs.getString("petugas");
                String statusTransaksi = rs.getString("statusTransaksi");
                model.addRow(new String[]{id, nim, nama, judulBuku, tanggal, petugas});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return model;
    }

    public static DefaultTableModel getLaporanPeminjamanMhs(String nim) {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("NIM");
        model.addColumn("NAMA");
        model.addColumn("JUDUL BUKU");
        model.addColumn("TANGGAL");
        model.addColumn("PETUGAS");
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = DbConn.getInstance();
            String query = "SELECT id, nim, nama, judulBuku, tanggal, petugas, statusTransaksi " +
                    "FROM laporan " +
                    "WHERE statusTransaksi = 'peminjaman' AND nim = ?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, nim);
            rs = stmt.executeQuery();
            while (rs.next()) {
                String id = rs.getString("id");
                long nimResult = rs.getLong("nim");
                String nama = rs.getString("nama");
                String judulBuku = rs.getString("judulBuku");
                Date tanggal = rs.getDate("tanggal");
                String petugas = rs.getString("petugas");
                String statusTransaksi = rs.getString("statusTransaksi");
                model.addRow(new Object[]{id, nimResult, nama, judulBuku, tanggal, petugas});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return model;
    }

    public static DefaultTableModel getLaporanPengembalianMhs(String nim) {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("NIM");
        model.addColumn("NAMA");
        model.addColumn("JUDUL BUKU");
        model.addColumn("TANGGAL");
        model.addColumn("PETUGAS");
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = DbConn.getInstance();
            String query = "SELECT id, nim, nama, judulBuku, tanggal, petugas " +
                    "FROM laporan " +
                    "WHERE statusTransaksi = 'pengembalian' AND nim = ?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, nim);
            rs = stmt.executeQuery();
            while (rs.next()) {
                String id = rs.getString("id");
                long nimResult = rs.getLong("nim");
                String nama = rs.getString("nama");
                String judulBuku = rs.getString("judulBuku");
                Date tanggal = rs.getDate("tanggal");
                String petugas = rs.getString("petugas");
                model.addRow(new Object[]{id, nimResult, nama, judulBuku, tanggal, petugas});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return model;
    }
}

class mBuku {
    private String id;
    private String judul;
    private String penulis;
    private int jumlah;

    public mBuku(String id, String judul) {
        this.id = id;
        this.judul = judul;
    }

    public static DefaultTableModel ambilDataBuku() {
        Connection conn = null;
        Statement statement = null;
        DefaultTableModel tm = null;

        try {
            conn = DbConn.getInstance();
            statement = conn.createStatement();

            String[] dataHeader = {"ID Buku", "Judul Buku", "Penerbit", "Jumlah"};
            tm = new DefaultTableModel(null, dataHeader);

            String query = "SELECT * FROM buku";
            ResultSet resultData = statement.executeQuery(query);

            while (resultData.next()) {
                Object[] rowData = {resultData.getInt("idBuku"), resultData.getString("judulBuku"),
                        resultData.getString("penerbitBuku"), resultData.getInt("jumlahBuku")};
                tm.addRow(rowData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) statement.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return tm;
    }

    public static DefaultTableModel cariBuku(String namaBuku) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        DefaultTableModel tm = null;
        try {
            conn = DbConn.getInstance();
            String query = "SELECT * FROM buku WHERE judulBuku LIKE ?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, "%" + namaBuku + "%");
            rs = stmt.executeQuery();
            String[] dataHeader = {"ID Buku", "Judul Buku", "Penerbit", "Jumlah"};
            tm = new DefaultTableModel(null, dataHeader);
            while (rs.next()) {
                Object[] rowData = {rs.getString("idBuku"), rs.getString("judulBuku"),
                        rs.getString("penerbitBuku"), rs.getString("jumlahBuku")};
                tm.addRow(rowData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return tm;
    }

    public static void tambahBuku(String judul, String penerbit, int jumlah, java.awt.Container parentComponent) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = DbConn.getInstance();
            String checkQuery = "SELECT COUNT(*) FROM buku WHERE judulBuku = ? AND penerbitBuku = ?";
            stmt = conn.prepareStatement(checkQuery);
            stmt.setString(1, judul);
            stmt.setString(2, penerbit);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                if (count > 0) {
                    tPopUp.popup(parentComponent, "Buku dengan nama dan penerbit tersebut sudah ada di database.");
                    return;
                }
            }
            String insertQuery = "INSERT INTO buku (judulBuku, penerbitBuku, jumlahBuku) VALUES (?, ?, ?)";
            stmt = conn.prepareStatement(insertQuery);
            stmt.setString(1, judul);
            stmt.setString(2, penerbit);
            stmt.setInt(3, jumlah);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                tPopUp.popup(parentComponent, "Buku berhasil ditambahkan ke database.");
            } else {
                tPopUp.popup(parentComponent, "Gagal menambahkan buku ke database.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void ubahBuku(String judul, String penerbit, String judulBaru, String penerbitBaru, java.awt.Container parentComponent) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = DbConn.getInstance();
            String checkQuery = "SELECT COUNT(*) FROM buku WHERE judulBuku = ? AND penerbitBuku = ?";
            stmt = conn.prepareStatement(checkQuery);
            stmt.setString(1, judul);
            stmt.setString(2, penerbit);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                if (count == 0) {
                    tPopUp.popup(parentComponent, "Buku dengan judul dan penerbit tersebut tidak ditemukan di database.");
                    return;
                }
            }
            String updateQuery = "UPDATE buku SET judulBuku = ?, penerbitBuku = ? WHERE judulBuku = ? AND penerbitBuku = ?";
            stmt = conn.prepareStatement(updateQuery);
            stmt.setString(1, judulBaru);
            stmt.setString(2, penerbitBaru);
            stmt.setString(3, judul);
            stmt.setString(4, penerbit);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                tPopUp.popup(parentComponent, "Perubahan pada buku berhasil disimpan.");
            } else {
                tPopUp.popup(parentComponent, "Gagal menyimpan perubahan pada buku.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void hapusBuku(int idBuku, java.awt.Container parentComponent) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = DbConn.getInstance();
            String checkQuery = "SELECT COUNT(*) FROM buku WHERE idBuku = ?";
            stmt = conn.prepareStatement(checkQuery);
            stmt.setInt(1, idBuku);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                if (count == 0) {
                    tPopUp.popup(parentComponent, "Buku dengan ID tersebut tidak ditemukan di database.");
                    return;
                }
            }
            String deleteQuery = "DELETE FROM buku WHERE idBuku = ?";
            stmt = conn.prepareStatement(deleteQuery);
            stmt.setInt(1, idBuku);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                tPopUp.popup(parentComponent, "Buku berhasil dihapus dari database.");
            } else {
                tPopUp.popup(parentComponent, "Gagal menghapus buku dari database.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void getData(String id, String buku, String penulis, int jumlah) {
        this.id = id;
        this.judul = judul;
        this.penulis = penulis;
        this.jumlah = jumlah;
    }

    public String getId() {
        return id;
    }

    public String getJudul() {
        return judul;
    }

    public String getPenulis() {
        return penulis;
    }

    public int getJumlah() {
        return jumlah;
    }
}

class mMahasiswa {
    private String nim;
    private String nama;
    private String noHp;
    private String jenisKelamin;

    public mMahasiswa(String nim, String nama) {
        this.nim = nim;
        this.nama = nama;
    }

    public static DefaultTableModel getAllMahasiswaTableModel() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("NIM");
        model.addColumn("Nama");
        model.addColumn("No. HP");
        model.addColumn("Jenis Kelamin");
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = DbConn.getInstance();
            String query = "SELECT nim, nama, no_hp, jenis_kelamin FROM mahasiswa";
            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();
            while (rs.next()) {
                String nim = rs.getString("nim");
                String nama = rs.getString("nama");
                String noHp = rs.getString("no_hp");
                String jenisKelamin = rs.getString("jenis_kelamin");
                model.addRow(new String[]{nim, nama, noHp, jenisKelamin});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return model;
    }

    public static mMahasiswa getMahasiswaByNIM(String nim) {
        mMahasiswa mahasiswa = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = DbConn.getInstance();
            String query = "SELECT nim, nama FROM mahasiswa WHERE nim = ?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, nim);
            rs = stmt.executeQuery();
            if (rs.next()) {
                String nama = rs.getString("nama");
                mahasiswa = new mMahasiswa(nim, nama);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return mahasiswa;
    }

    public static mMahasiswa cekPeminjaman(String nim) {
        mMahasiswa mahasiswa = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = DbConn.getInstance();
            String query = "SELECT * FROM transaksi_peminjaman_pengembalian WHERE nim = ?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, nim);
            rs = stmt.executeQuery();
            if (rs.next()) {
                String namaMahasiswa = null;
                String queryNama = "SELECT nama FROM mahasiswa WHERE nim = ?";
                PreparedStatement stmtNama = conn.prepareStatement(queryNama);
                stmtNama.setString(1, nim);
                ResultSet rsNama = stmtNama.executeQuery();
                if (rsNama.next()) {
                    namaMahasiswa = rsNama.getString("nama");
                }
                mahasiswa = new mMahasiswa(nim, namaMahasiswa);
                rsNama.close();
                stmtNama.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return mahasiswa;
    }

    public void setData(String nim, String nama, String noHp, String jenisKelamin) {
        this.nim = nim;
        this.nama = nama;
        this.noHp = noHp;
        this.jenisKelamin = jenisKelamin;
    }

    public String getNim() {
        return nim;
    }

    public String getNama() {
        return nama;
    }

    public String getNoHp() {
        return noHp;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }
}

class mPetugas {
    private String nip;
    private String name;
    private String noHp;
    private String password;

    public mPetugas(String nip, String name, String noHp, String password) {
        this.nip = nip;
        this.name = name;
        this.noHp = noHp;
        this.password = password;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNoHp() {
        return noHp;
    }

    public void setNoHp(String noHp) {
        this.noHp = noHp;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

class tExit extends JLabel {
    public tExit(int x, Container component) {
        super("Keluar");
        setBounds(x, 15, 52, 24);
        setFont(cFonts.EXIT);
        setForeground(cColor.WHITE);
        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            }

            public void mouseExited(java.awt.event.MouseEvent e) {
                setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
            }

            public void mouseClicked(java.awt.event.MouseEvent e) {
                int pilihan = JOptionPane.showConfirmDialog(component, "Apakah anda yakin ingin keluar?", "Pemberitahuan", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (pilihan == 0) {
                    System.exit(0);
                }
            }
        });
    }
}
class tLogOut extends JButton {
    public tLogOut(int x, int y, Container component, JFrame frame) {
        super("LogOut");
        setBounds(x, 15, 52, 24);
        setFont(cFonts.EXIT);
        setForeground(cColor.WHITE);
        setLocation(x, y);
        setSize(178, 44);
        setBackground(cColor.GREEN);
        setBorderPainted(false);
        setForeground(cColor.WHITE);
        setUI(new BasicButtonUI());
        setHorizontalTextPosition(SwingConstants.CENTER);
        setHorizontalAlignment(SwingConstants.CENTER);
        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            }

            public void mouseExited(java.awt.event.MouseEvent e) {
                setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
            }
        });
        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            }

            public void mouseExited(java.awt.event.MouseEvent e) {
                setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
            }

            public void mouseClicked(java.awt.event.MouseEvent e) {
                int pilihan = JOptionPane.showConfirmDialog(component, "Apakah anda yakin ingin LogOut?", "Pemberitahuan", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (pilihan == 0) {
                    new ViewLogin().setVisible(true);
                    System.out.println(ViewLogin.getId());
                    frame.dispose();
                }
            }
        });
    }
}

class tPanel extends JPanel {
    private final int radius;
    private final boolean useLayout;

    public tPanel(int radius, boolean useLayout, int x, int y, int width, int height, java.awt.Color color) {
        super();
        this.radius = radius;
        this.useLayout = useLayout;
        if (useLayout) {
            setLayout(null);
        }
        setBounds(x, y, width, height);
        setBackground(color);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics = (Graphics2D) g.create();
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setColor(getBackground());
        graphics.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
        graphics.dispose();
    }
}

class tLabel extends JLabel {
    public tLabel(String text, java.awt.Color color, java.awt.Font font, int x, int y) {
        super(text);
        setFont(font);
        setLocation(x, y);
        setSize(getPreferredSize());
        setForeground(color);
        setHorizontalAlignment(this.CENTER);
        setVerticalAlignment(this.CENTER);
    }
}

class tTextField extends JTextField {
    public tTextField(int x, int y, int width, int height) {
        super();
        setLocation(x, y);
        setSize(width, height);
        setHorizontalAlignment(this.CENTER);
    }
}

class tPasswordField extends JPasswordField {
    public tPasswordField(int x, int y, int width, int height) {
        super();
        setLocation(x, y);
        setSize(width, height);
        setHorizontalAlignment(this.CENTER);
    }
}

class tButton extends JButton {
    public tButton(String text, int x, int y, int width, int height, Color bgColor, Color textColor) {
        super(text);
        setLocation(x, y);
        setSize(width, height);
        setBackground(bgColor);
        setBorderPainted(false);
        setForeground(textColor);
        setUI(new BasicButtonUI());
        setHorizontalTextPosition(SwingConstants.CENTER);
        setHorizontalAlignment(SwingConstants.CENTER);
        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            }

            public void mouseExited(java.awt.event.MouseEvent e) {
                setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
            }
        });
    }
}

class tPopUp {
    public static void popup(Container parentComponent, String Teks) {
        JOptionPane.showMessageDialog(parentComponent, Teks, "PEMBERITAHUAN", JOptionPane.PLAIN_MESSAGE);
    }
}

class tAction {
    public static void textFieldListenera(JTextField textField, String teks) {
        textField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(teks)) {
                    textField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setText(teks);
                }
            }
        });
    }

    public static String generatedId(char first) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        sb.append(first);
        for (int i = 0; i < 9; i++) {
            char c = characters.charAt(random.nextInt(characters.length()));
            sb.append(c);
        }
        return sb.toString();
    }
}

class cTable extends JTable {
    public cTable(javax.swing.table.TableModel dm) {
        super(dm);
        setFont(cFonts.TABLE_DATA);
        getTableHeader().setFont(cFonts.TABLE_HEADER);
        getTableHeader().setBackground(cColor.RED);
        getTableHeader().setForeground(cColor.WHITE);
        getTableHeader().setBorder(null);
        setBorder(null);
        setRowHeight(30);
        getTableHeader().setReorderingAllowed(false);
        setShowVerticalLines(false);
        setShowHorizontalLines(false);
        setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        setDragEnabled(true);
        setSelectionBackground(cColor.RED);
        setSelectionForeground(cColor.WHITE);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    public String[] getSelectedRowData() {
        Container parent = getParent();
        if (parent instanceof JViewport viewport) {
            if (viewport.getParent() instanceof JScrollPane scrollPane) {
                JTable table = (JTable) scrollPane.getViewport().getView();
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    int columnCount = table.getColumnCount();
                    String[] rowData = new String[columnCount];
                    for (int i = 0; i < columnCount; i++) {
                        rowData[i] = table.getValueAt(selectedRow, i).toString();
                    }
                    return rowData;
                }
            }
        }
        return null;
    }
}

class cScrollPane extends JScrollPane {
    public cScrollPane(java.awt.Component view, int x, int y, int width, int height) {
        super(view);
        setBounds(x, y, width, height);
        getViewport().setBackground(cColor.WHITE);
        setBorder(new javax.swing.border.LineBorder(cColor.BLACK, 1));
    }
}

class cDate extends JDateChooser {
    public cDate(String format, int x, int y, int width, int height, Color color) {
        setDateFormatString(format);
        setBounds(x, y, width, height);
        setForeground(color);
    }
}

class tComboBox extends JComboBox<String> {
    private DefaultTableModel bookDataModel;
    private String selectedIdBuku;

    public tComboBox(DefaultTableModel model, int x, int y, int width, int height) {
        setBounds(x, y, width, height);
        this.bookDataModel = model;
        addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent event) {
                if (event.getStateChange() == ItemEvent.SELECTED) {
                    String selectedItem = (String) event.getItem();
                    String[] splitSelectedItem = selectedItem.split(" - ");
                    selectedIdBuku = splitSelectedItem[0];
                }
            }
        });
        refreshComboBox(model);
    }

    public void refreshComboBox(DefaultTableModel test) {
        removeAllItems();
        for (int i = 0; i < test.getRowCount(); i++) {
            String idBuku = test.getValueAt(i, 0).toString();
            String namaBuku = (String) test.getValueAt(i, 1);
            String penerbit = (String) test.getValueAt(i, 2);
            addItem(idBuku + " - " + namaBuku + " - " + penerbit);
        }
        this.bookDataModel = test;
    }

    public String getSelectedIdBuku() {
        return selectedIdBuku;
    }
}

class cColor {
    public static final Color UNGU = new Color(90, 46, 160);
    public static final Color RED = new Color(255, 64, 129);
    public static final Color WHITE = new Color(255, 255, 255);
    public static final Color WHITE90 = new Color(255, 255, 255, 90);
    public static final Color WHITE_GREY = new Color(241, 241, 241);
    public static final Color BLACK = new Color(0, 0, 0);
    public static final Color BLACK_GREY = new Color(48, 48, 48);
    public static final Color GREY = new Color(118, 118, 118);
    public static final Color GREEN = new Color(45, 204, 147);
}

class cFonts {
    public static final Font TITLE_H1 = new Font("Poppins Bold", Font.BOLD, 20);
    public static final Font TITLE = new Font("Poppins SemiBold", Font.BOLD, 25);
    public static final Font EXIT = new Font("Poppins Bold", Font.BOLD, 16);
    public static final Font SUB_TITLE = new Font("Poppins Bold", Font.BOLD, 25);
    public static final Font TITLE_TEXT_TRX = new Font("Poppins Bold", Font.BOLD, 12);
    public static final Font SUB_TITLE_H3 = new Font("Poppins Bold", Font.BOLD, 16);
    public static final Font TABLE_HEADER = new Font("Poppins Bold", Font.BOLD, 14);
    public static final Font TABLE_DATA = new Font("Poppins Bold", Font.PLAIN, 12);
    public static final Font LSTATUS = new Font("Poppins Bold", Font.BOLD, 12);
}

