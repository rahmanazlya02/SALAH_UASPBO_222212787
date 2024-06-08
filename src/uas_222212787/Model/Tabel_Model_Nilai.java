/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uas_222212787.Model;

import java.text.DecimalFormat;
import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 *
 * @author Nazlya
 */
public class Tabel_Model_Nilai extends AbstractTableModel{
    List<Model_Nilai> listNilai;
    List<Model_Mahasiswa> listMahasiswa; // Tambahkan list mahasiswa
    
    public Tabel_Model_Nilai(List<Model_Nilai> listNilai, List<Model_Mahasiswa> listMahasiswa) { // Tambahkan list mahasiswa pada konstruktor
        this.listNilai = listNilai;
        this.listMahasiswa = listMahasiswa;
    }
    
    @Override
    public int getRowCount() {
        return listNilai.size();
    }
    
    @Override
    public int getColumnCount() {
        return 8;
    }
    
    @Override
    public String getColumnName(int column) {
        return switch (column) {
            case 0 -> "NIM";
            case 1 -> "Nama";
            case 2 -> "Kementerian";
            case 3 -> "Nilai Kinerja";
            case 4 -> "Nilai Kehadiran";
            case 5 -> "Nilai Kreativitas";
            case 6 -> "Nilai Akhir";
            case 7 -> "Hasil Kategori Penilaian";
            default -> null;
        };
    }

    @Override
    public Object getValueAt(int row, int column) {
        if (column == 0) { // Jika kolom adalah kolom nama
            String nim = listNilai.get(row).getNim(); // Ambil nim dari list nilai
            // Cari mahasiswa dengan nim yang sesuai
            for (Model_Mahasiswa mahasiswa : listMahasiswa) {
                if (mahasiswa.getNim().equals(nim)) {
                    return mahasiswa.getNim(); // Kembalikan nama mahasiswa
                }
            }
        }
        else if (column == 1) { // Jika kolom adalah kolom nama
            String nim = listNilai.get(row).getNim(); // Ambil nim dari list nilai
            // Cari mahasiswa dengan nim yang sesuai
            for (Model_Mahasiswa mahasiswa : listMahasiswa) {
                if (mahasiswa.getNim().equals(nim)) {
                    return mahasiswa.getNamaMhs(); // Kembalikan nama mahasiswa
                }
            }
        } else if (column == 2) { // Jika kolom adalah kolom kementerian
            String nim = listNilai.get(row).getNim(); // Ambil nim dari list nilai
            // Cari mahasiswa dengan nim yang sesuai
            for (Model_Mahasiswa mahasiswa : listMahasiswa) {
                if (mahasiswa.getNim().equals(nim)) {
                    return mahasiswa.getKementerian(); // Kembalikan kementerian mahasiswa
                }
            }
        } else if (column == 6) { // Perubahan disini
            double nilaiAkhir = listNilai.get(row).getNilaiAkhir();
            DecimalFormat df = new DecimalFormat("#.##"); // Format dua angka di belakang koma
            return df.format(nilaiAkhir);
        } else {
            return switch (column) {
                case 3 -> listNilai.get(row).getKinerja();
                case 4 -> listNilai.get(row).getKehadiran();
                case 5 -> listNilai.get(row).getKreativitas();
                case 7 -> listNilai.get(row).getKatNilai();
                default -> null;
            };
        }
        return null;
    }
}