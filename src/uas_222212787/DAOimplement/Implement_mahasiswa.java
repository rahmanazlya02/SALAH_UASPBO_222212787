/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package uas_222212787.DAOimplement;
import uas_222212787.Model.Model_Mahasiswa;
import java.util.List;

/**
 *
 * @author Nazlya
 */
public interface Implement_mahasiswa {
    public void insert(Model_Mahasiswa a);
    public void update(Model_Mahasiswa a);
    public void delete(String nim);
    
    public List<Model_Mahasiswa> getAll();
    public List<Model_Mahasiswa> getCariNama(String namaMhs);
     public Model_Mahasiswa getByNim(String nim);  
    
}
