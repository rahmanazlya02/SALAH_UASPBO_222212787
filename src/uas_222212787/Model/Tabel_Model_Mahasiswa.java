/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uas_222212787.Model;

import javax.swing.table.AbstractTableModel;
import java.awt.List;

/**
 *
 * @author Nazlya
 */
public class Tabel_Model_Mahasiswa extends AbstractTableModel{
    java.util.List<Model_Mahasiswa> listMahasiswa;
    
    public Tabel_Model_Mahasiswa(java.util.List<Model_Mahasiswa> listMahasiswa) {
        this.listMahasiswa = listMahasiswa;
    }
    @Override
    public int getRowCount() {
        return listMahasiswa.size();
    }

    @Override
    public int getColumnCount() {
        return 6;
    }
    
    @Override
    public String getColumnName(int column) {
        return switch (column) {
            case 0 -> "NIM";
            case 1 -> "NAMA";
            case 2 -> "JENIS KELAMIN";
            case 3 -> "EMAIL";
            case 4 -> "PROVINSI";
            case 5 -> "ALAMAT";
            default -> null;
        };
    }

    @Override
    public Object getValueAt(int row, int column) {
        return switch (column) {
            case 0 -> listMahasiswa.get(row).getNim();
            case 1 -> listMahasiswa.get(row).getNamaMhs();
            case 2 -> listMahasiswa.get(row).getGender();
            case 3 -> listMahasiswa.get(row).getEmail();
            case 4 -> listMahasiswa.get(row).getProvinsi();
            case 5 -> listMahasiswa.get(row).getAlamat();
            default -> null;
        };
    }
    
}
