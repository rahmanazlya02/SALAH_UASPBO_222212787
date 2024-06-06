/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uas_222212787.DAO;
import uas_222212787.Connection.Connection_mahasiswa;
import uas_222212787.DAOimplement.Implement_mahasiswa;
import uas_222212787.Model.Model_Mahasiswa;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Nazlya
 */
public class DAO_mahasiswa implements Implement_mahasiswa{
    
    Connection conn;
    final String insert = "INSERT INTO mahasiswa (nim, namaMhs, gender, email, kementerian, alamat) "
            + "VALUES (?,?,?,?,?,?);";
    final String update = "UPDATE mahasiswa SET nim=?, namaMhs=?, gender=?, email=?, kementerian=?, alamat=? "
            + "WHERE nim=?;";
    final String delete = "DELETE FROM mahasiswa WHERE nim=?;";
    final String select = "SELECT * FROM mahasiswa;";
    final String carinama = "SELECT * FROM mahasiswa WHERE namaMhs like ?;";
    final String selectByNim = "SELECT * FROM mahasiswa WHERE nim=?;";  // Query baru untuk getByNim

    
    public DAO_mahasiswa() {
        conn = Connection_mahasiswa.getConnection();
    }
    
    @Override
    public void insert(Model_Mahasiswa a) {
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement(insert);
            stmt.setString(1, a.getNim());
            stmt.setString(2, a.getNamaMhs());
            stmt.setString(3, a.getGender());
            stmt.setString(4, a.getEmail());
            stmt.setString(5, a.getKementerian());
            stmt.setString(6, a.getAlamat());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try{
                stmt.close();
            } catch(SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
    
@Override
public void update(Model_Mahasiswa a) {
    PreparedStatement stmt = null;
    try {
        stmt = conn.prepareStatement(update);
        stmt.setString(1, a.getNim());
        stmt.setString(2, a.getNamaMhs());
        stmt.setString(3, a.getGender());
        stmt.setString(4, a.getEmail());
        stmt.setString(5, a.getKementerian());
        stmt.setString(6, a.getAlamat());
        stmt.setString(7, a.getNim()); // Set nim baru
        stmt.executeUpdate();
    } catch (SQLException ex) {
        ex.printStackTrace();
    } finally {
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch(SQLException ex) {
            ex.printStackTrace();
        }
    }
}
    
@Override
public void delete(String nim) {
    PreparedStatement stmt = null;
    try {
        stmt = conn.prepareStatement(delete);
        stmt.setString(1, nim); // Set nim sebagai parameter
        stmt.executeUpdate();
    } catch (SQLException ex) {
        ex.printStackTrace();
    } finally {
        try{
            if (stmt != null) {
                stmt.close();
            }
        } catch(SQLException ex) {
            ex.printStackTrace();
        }
    }
}
    
    @Override
    public java.util.List<Model_Mahasiswa> getAll() {
        java.util.List<Model_Mahasiswa> listMhs = null;
        try {
            listMhs = new ArrayList<Model_Mahasiswa>();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(select);
            while (rs.next()) {
                Model_Mahasiswa a = new Model_Mahasiswa();
                a.setNim(rs.getString("nim"));
                a.setNamaMhs(rs.getString("namaMhs"));
                a.setGender(rs.getString("gender"));
                a.setEmail(rs.getString("email"));
                a.setKementerian(rs.getString("kementerian"));
                a.setAlamat(rs.getString("alamat"));
                listMhs.add(a); // tambahkan objek ke daftar
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAO_mahasiswa.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return listMhs;
    }
    
    @Override
    public java.util.List<Model_Mahasiswa> getCariNama(String namaMhs) {
        java.util.List<Model_Mahasiswa> listMhs = null;
        try {
            listMhs = new ArrayList<Model_Mahasiswa>();
            PreparedStatement stmt = conn.prepareStatement(carinama);
            stmt.setString(1, "%" + namaMhs + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Model_Mahasiswa a = new Model_Mahasiswa();
                a.setNim(rs.getString("nim"));
                a.setNamaMhs(rs.getString("namaMhs"));
                a.setGender(rs.getString("gender"));
                a.setEmail(rs.getString("email"));
                a.setKementerian(rs.getString("kementerian"));
                a.setAlamat(rs.getString("alamat"));
                listMhs.add(a);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAO_mahasiswa.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return listMhs;
    }
    
    @Override
    public Model_Mahasiswa getByNim(String nim) {  // Metode baru untuk getByNim
        Model_Mahasiswa mahasiswa = null;
        try {
            PreparedStatement stmt = conn.prepareStatement(selectByNim);
            stmt.setString(1, nim);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                mahasiswa = new Model_Mahasiswa();
                mahasiswa.setNim(rs.getString("nim"));
                mahasiswa.setNamaMhs(rs.getString("namaMhs"));
                mahasiswa.setGender(rs.getString("gender"));
                mahasiswa.setEmail(rs.getString("email"));
                mahasiswa.setKementerian(rs.getString("kementerian"));
                mahasiswa.setAlamat(rs.getString("alamat"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAO_mahasiswa.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mahasiswa;
    }
    
}
