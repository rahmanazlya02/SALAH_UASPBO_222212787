/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uas_222212787.Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import uas_222212787.DAO.DAO_mahasiswa;
import uas_222212787.DAO.DAO_nilai;
import uas_222212787.DAOimplement.Impl_Nilai;
import uas_222212787.Model.Model_Nilai;
import uas_222212787.Model.Model_Mahasiswa;
import uas_222212787.Model.Tabel_Model_Nilai;
import uas_222212787.View.NilaiPanel;

/**
 *
 * @author Nazlya
 */
public class Controller_Nilai {
    NilaiPanel frame_nilai;
    Impl_Nilai implement_nilai;
    List<Model_Nilai> listNilai;
    List<Model_Mahasiswa> listMahasiswa;
    Model_Mahasiswa selectedMahasiswa; // menyimpan mahasiswa yang dipilih
    
    public Controller_Nilai(NilaiPanel frame_nilai) {
        this.frame_nilai = frame_nilai;
        implement_nilai = new DAO_nilai();
        listNilai = implement_nilai.getAll();
        
        // Mengambil semua data mahasiswa
        DAO_mahasiswa daoMahasiswa = new DAO_mahasiswa();
        listMahasiswa = daoMahasiswa.getAll();
        
        // Memanggil metode untuk mengisi tabel nilai saat aplikasi dimulai
        isiTableNilai();
        
        frame_nilai.getNilaiTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int selectedRow = frame_nilai.getNilaiTable().getSelectedRow();
                if (selectedRow >= 0 && selectedRow < listNilai.size()) {
                    isiField(selectedRow);
                }
            }
        });
    }
    
    // Fungsi untuk mencetak data ke CSV
    public void printToCSV() {
    JFileChooser fileChooser = new JFileChooser();
    int result = fileChooser.showSaveDialog(frame_nilai);
    if (result == JFileChooser.APPROVE_OPTION) {
        File selectedFile = fileChooser.getSelectedFile();
        String filePath = selectedFile.getAbsolutePath();
        if (!filePath.toLowerCase().endsWith(".csv")) {
            selectedFile = new File(filePath + ".csv");
        }
        try {
            frame_nilai.printToCSV(selectedFile);
        } catch (IOException ex) {
            ex.printStackTrace();
            frame_nilai.showMessage("Error printing to CSV: " + ex.getMessage());
        }
    }
}
    
    public void calculate() {
        try {
            double kinerja = Double.parseDouble(frame_nilai.getKinerjaTextField().getText().trim());
            double kehadiran = Double.parseDouble(frame_nilai.getKehadiranTextField().getText().trim());
            double kreativitas = Double.parseDouble(frame_nilai.getKreativitasTextField().getText().trim());

            double nilaiAkhir = (0.35 * kinerja) + (0.35 * kehadiran) + (0.30 * kreativitas);
            // Menggunakan String.format untuk membulatkan nilai akhir ke 2 angka di belakang koma
            String formattedNilaiAkhir = String.format("%.2f", nilaiAkhir);

            frame_nilai.getNilaiAkhirTextField().setText(formattedNilaiAkhir);

            String hasilKategori;
            if (nilaiAkhir <= 75) {
                hasilKategori = "Cukup Baik";
            } else if (nilaiAkhir <= 85) {
                hasilKategori = "Baik";
            } else {
                hasilKategori = "Sangat Baik";
            }
            frame_nilai.getHasilKatTextField().setText(hasilKategori);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame_nilai, "Format angka tidak valid!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
   
    public void insertOrUpdateNilai() {
        try {
            if (frame_nilai.getNimTextField().getText().trim().isEmpty() ||
                frame_nilai.getNamaTextField().getText().trim().isEmpty() ||
                frame_nilai.getKementerianTextField().getText().trim().isEmpty() ||
                frame_nilai.getKinerjaTextField().getText().trim().isEmpty() ||
                frame_nilai.getKehadiranTextField().getText().trim().isEmpty() ||
                frame_nilai.getKreativitasTextField().getText().trim().isEmpty()) {
                
                JOptionPane.showMessageDialog(frame_nilai, "Semua field harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String nim = frame_nilai.getNimTextField().getText().trim();
            String nama = frame_nilai.getNamaTextField().getText().trim();
            String kementerian = frame_nilai.getKementerianTextField().getText().trim();
            double kinerja = Double.parseDouble(frame_nilai.getKinerjaTextField().getText().trim());
            double kehadiran = Double.parseDouble(frame_nilai.getKehadiranTextField().getText().trim());
            double kreativitas = Double.parseDouble(frame_nilai.getKreativitasTextField().getText().trim());

            double nilaiAkhir = (0.35 * kinerja) + (0.35 * kehadiran) + (0.30 * kreativitas);
            // Menggunakan String.format untuk membulatkan nilai akhir ke 2 angka di belakang koma
            String formattedNilaiAkhir = String.format("%.2f", nilaiAkhir);
            frame_nilai.getNilaiAkhirTextField().setText(formattedNilaiAkhir);
            
            String hasilKat;
            if (nilaiAkhir <= 75) {
                hasilKat = "Cukup Baik";
            } else if (nilaiAkhir <= 85) {
                hasilKat = "Baik";
            } else {
                hasilKat = "Sangat Baik";
            }
            frame_nilai.getHasilKatTextField().setText(hasilKat);

            Model_Nilai nilai = new Model_Nilai();
            nilai.setNim(nim);
            nilai.setNamaMhs(nama);
            nilai.setKementerian(kementerian);
            nilai.setKinerja(kinerja);
            nilai.setKehadiran(kehadiran);
            nilai.setKreativitas(kreativitas);
            nilai.setNilaiAkhir(Double.parseDouble(formattedNilaiAkhir)); // Simpan nilai akhir yang sudah diformat
            nilai.setKatNilai(hasilKat);

            Model_Nilai existingNilai = implement_nilai.getNilaiByNim(nim);
            if (existingNilai != null) {
                implement_nilai.update(nilai);
                JOptionPane.showMessageDialog(frame_nilai, "Data berhasil diperbarui!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            } else {
                implement_nilai.insert(nilai);
                JOptionPane.showMessageDialog(frame_nilai, "Data berhasil disimpan!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            }
            isiTableNilai();
            listNilai = implement_nilai.getAll();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame_nilai, "Format angka tidak valid!", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame_nilai, "Terjadi kesalahan: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void reset() {
        frame_nilai.getNimTextField().setText("");
        frame_nilai.getNamaTextField().setText("");
        frame_nilai.getKementerianTextField().setText("");
        frame_nilai.getKinerjaTextField().setText("");
        frame_nilai.getKehadiranTextField().setText("");
        frame_nilai.getKreativitasTextField().setText("");
        frame_nilai.getNilaiAkhirTextField().setText("");
        frame_nilai.getHasilKatTextField().setText("");
    }

   public void isiTableNilai() {
        DAO_mahasiswa daoMahasiswa = new DAO_mahasiswa();
        List<Model_Mahasiswa> mahasiswaList = daoMahasiswa.getAll();
        
        // Buat objek untuk menyimpan data nilai
        List<Model_Nilai> nilaiList = new ArrayList<>();
        for (Model_Mahasiswa mahasiswa : mahasiswaList) {
            Model_Nilai nilai = new Model_Nilai();
            nilai.setNim(mahasiswa.getNim());
            nilai.setNamaMhs(mahasiswa.getNamaMhs());
            nilai.setKementerian(mahasiswa.getKementerian());
            
            // Ambil nilai dari database berdasarkan nim mahasiswa
            Model_Nilai nilaiDB = implement_nilai.getNilaiByNim(mahasiswa.getNim());
            if (nilaiDB != null) {
                nilai.setKinerja(nilaiDB.getKinerja());
                nilai.setKehadiran(nilaiDB.getKehadiran());
                nilai.setKreativitas(nilaiDB.getKreativitas());
                nilai.setNilaiAkhir(nilaiDB.getNilaiAkhir());
                nilai.setKatNilai(nilaiDB.getKatNilai());
            } else {
            // Jika tidak ada data nilai di database, set nilai default
            nilai.setKinerja(0.0);
            nilai.setKehadiran(0.0);
            nilai.setKreativitas(0.0);
            nilai.setNilaiAkhir(0.0);
            nilai.setKatNilai("N/A");
        }

            // Tambahkan objek nilai ke dalam list
            nilaiList.add(nilai);
        }
        listNilai = nilaiList;
        // Atur data nilai ke dalam tabel menggunakan model tabel nilai
        Tabel_Model_Nilai tmb = new Tabel_Model_Nilai(listNilai, mahasiswaList);
        frame_nilai.getNilaiTable().setModel(tmb);
    }


    public void isiField(int row) {
        if (row >= 0 && row < listNilai.size()) {
            Model_Nilai nilai = listNilai.get(row);
            frame_nilai.getNimTextField().setText(nilai.getNim());
            frame_nilai.getKinerjaTextField().setText(String.valueOf(nilai.getKinerja()));
            frame_nilai.getKehadiranTextField().setText(String.valueOf(nilai.getKehadiran()));
            frame_nilai.getKreativitasTextField().setText(String.valueOf(nilai.getKreativitas()));

            // Cari mahasiswa dengan nim yang sesuai
            for (Model_Mahasiswa mahasiswa : listMahasiswa) {
                if (mahasiswa.getNim().equals(nilai.getNim())) {
                    frame_nilai.getNamaTextField().setText(mahasiswa.getNamaMhs());
                    frame_nilai.getKementerianTextField().setText(mahasiswa.getKementerian());
                    break;
                }
            }
            
            double nilaiAkhir = (0.35 * nilai.getKehadiran()) + (0.35 * nilai.getKinerja()) + (0.30 * nilai.getKreativitas());
            //frame_nilai.getNilaiAkhirTextField().setText(String.valueOf(nilaiAkhir));
            // Menggunakan String.format untuk membulatkan nilai akhir ke 2 angka di belakang koma
            String formattedNilaiAkhir = String.format("%.2f", nilaiAkhir);
            frame_nilai.getNilaiAkhirTextField().setText(formattedNilaiAkhir);

            String hasilKategori;
            if (nilaiAkhir <= 75) {
                hasilKategori = "Cukup Baik";
            } else if (nilaiAkhir <= 85) {
                hasilKategori = "Baik";
            } else {
                hasilKategori = "Sangat Baik";
            } frame_nilai.getHasilKatTextField().setText(hasilKategori);
            
            frame_nilai.getHasilKatTextField().setText(hasilKategori);
        } else {
            JOptionPane.showMessageDialog(frame_nilai, "Baris yang dipilih tidak valid!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void isiTableCariNama() {
        listNilai = implement_nilai.getCariNama(frame_nilai.getTxtCariData().getText());
        DAO_mahasiswa daoMahasiswa = new DAO_mahasiswa();
        List<Model_Mahasiswa> mahasiswaList = daoMahasiswa.getAll();

        // Tambahkan data mahasiswa ke list nilai yang ditemukan
        List<Model_Nilai> nilaiList = new ArrayList<>();
        for (Model_Nilai nilai : listNilai) {
            for (Model_Mahasiswa mahasiswa : mahasiswaList) {
                if (mahasiswa.getNim().equals(nilai.getNim())) {
                    nilai.setNamaMhs(mahasiswa.getNamaMhs());
                    nilai.setKementerian(mahasiswa.getKementerian());
                    break;
                }
            }
            nilaiList.add(nilai);
        }
        Tabel_Model_Nilai tmb = new Tabel_Model_Nilai(nilaiList, mahasiswaList);
        frame_nilai.getNilaiTable().setModel(tmb);
    }

    public void carinama() {
        if (!frame_nilai.getTxtCariData().getText().trim().isEmpty()) {
            isiTableCariNama();
        } else {
            JOptionPane.showMessageDialog(frame_nilai, "Silahkan masukkan nama untuk mencari data!");
        }
    }

}