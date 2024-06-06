/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uas_222212787.Model;

import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 *
 * @author Nazlya
 */
public class Tabel_Model_Mahasiswa extends AbstractTableModel{
    List<Model_Mahasiswa> listMahasiswa;
    
    public Tabel_Model_Mahasiswa(List<Model_Mahasiswa> listMahasiswa) {
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
            case 1 -> "Nama";
            case 2 -> "Jenis Kelamin";
            case 3 -> "Email";
            case 4 -> "Kementerian";
            case 5 -> "Alamat";
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
            case 4 -> listMahasiswa.get(row).getKementerian();
            case 5 -> listMahasiswa.get(row).getAlamat();
            default -> null;
        };
    }
    
}
