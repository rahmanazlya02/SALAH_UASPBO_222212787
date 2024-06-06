/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uas_222212787.DAO;
import uas_222212787.Connection.Connection_mahasiswa;
import uas_222212787.DAOimplement.Impl_Nilai;
import uas_222212787.Model.Model_Nilai;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Nazlya
 */
public class DAO_nilai implements Impl_Nilai {
    Connection conn;
   
    final String insert = "INSERT INTO nilai (nim, namaMhs, kementerian, kinerja, kehadiran, kreativitas, nilaiAkhir, katNilai) "
            + "VALUES (?,?,?,?,?,?,?,?);";
    final String update = "UPDATE nilai SET namaMhs=?, kementerian=?, kinerja=?, kehadiran=?, kreativitas=?, nilaiAkhir=?, katNilai=? "
         + "WHERE nim=?;";
    final String select = "SELECT m.nim, m.namaMhs, m.kementerian, "
            + "n.kinerja, n.kehadiran, n.kreativitas, n.nilaiAkhir, n.katNilai "
            + "FROM mahasiswa m JOIN nilai n ON m.nim = n.nim;";
    final String carinama = "SELECT n.*, m.namaMhs, m.kementerian FROM nilai n JOIN mahasiswa m ON n.nim = m.nim WHERE m.namaMhs LIKE ?;";
    public DAO_nilai() {
        conn = Connection_mahasiswa.getConnection();
    }
    
    @Override
    public void insert(Model_Nilai n) {
        try (PreparedStatement stmt = conn.prepareStatement(insert)) {
            stmt.setString(1, n.getNim());
            stmt.setString(2, n.getNamaMhs());
            stmt.setString(3, n.getKementerian());
            stmt.setDouble(4, n.getKinerja());
            stmt.setDouble(5, n.getKehadiran());
            stmt.setDouble(6, n.getKreativitas());
            stmt.setDouble(7, n.getNilaiAkhir());
            stmt.setString(8, n.getKatNilai());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    @Override
    public void update(Model_Nilai n) {
    try (PreparedStatement stmt = conn.prepareStatement(update)) {
            stmt.setString(1, n.getNamaMhs());
            stmt.setString(2, n.getKementerian());
            stmt.setDouble(3, n.getKinerja());
            stmt.setDouble(4, n.getKehadiran());
            stmt.setDouble(5, n.getKreativitas());
            stmt.setDouble(6, n.getNilaiAkhir());
            stmt.setString(7, n.getKatNilai());
            stmt.setString(8, n.getNim());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    @Override
    public List<Model_Nilai> getAll() {
    List<Model_Nilai> list = new ArrayList<>();
    try (Statement statement = conn.createStatement();
             ResultSet rs = statement.executeQuery("SELECT * FROM nilai")) {
            while (rs.next()) {
                Model_Nilai nilai = new Model_Nilai();
                nilai.setNim(rs.getString("nim"));
                nilai.setNamaMhs(rs.getString("namaMhs"));
                nilai.setKementerian(rs.getString("kementerian"));
                nilai.setKinerja(rs.getDouble("kinerja"));
                nilai.setKehadiran(rs.getDouble("kehadiran"));
                nilai.setKreativitas(rs.getDouble("kreativitas"));
                nilai.setNilaiAkhir(rs.getDouble("nilaiAkhir"));
                nilai.setKatNilai(rs.getString("katNilai"));
                list.add(nilai);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    return list;
}

    
    @Override
    public List<Model_Nilai> getCariNama(String namaMhs) {
        List<Model_Nilai> listNilai = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(carinama)) {
            stmt.setString(1, "%" + namaMhs + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Model_Nilai n = new Model_Nilai();
                    n.setNim(rs.getString("nim"));
                    n.setNamaMhs(rs.getString("namaMhs"));
                    n.setKementerian(rs.getString("kementerian"));
                    n.setKinerja(rs.getDouble("kinerja"));
                    n.setKehadiran(rs.getDouble("kehadiran"));
                    n.setKreativitas(rs.getDouble("kreativitas"));
                    n.setNilaiAkhir(rs.getDouble("nilaiAkhir"));
                    n.setKatNilai(rs.getString("katNilai"));
                    listNilai.add(n);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAO_nilai.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listNilai;
    }
    
     @Override
    public Model_Nilai getNilaiByNim(String nim) {
        Model_Nilai nilai = null;
        String query = "SELECT m.nim, m.namaMhs, m.kementerian, n.kinerja, n.kehadiran, n.kreativitas, n.nilaiAkhir, n.katNilai "
                     + "FROM mahasiswa m JOIN nilai n ON m.nim = n.nim WHERE n.nim = ?;";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, nim);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    nilai = new Model_Nilai();
                    nilai.setNim(rs.getString("nim"));
                    nilai.setNamaMhs(rs.getString("namaMhs"));
                    nilai.setKementerian(rs.getString("kementerian"));
                    nilai.setKinerja(rs.getDouble("kinerja"));
                    nilai.setKehadiran(rs.getDouble("kehadiran"));
                    nilai.setKreativitas(rs.getDouble("kreativitas"));
                    nilai.setNilaiAkhir(rs.getDouble("nilaiAkhir"));
                    nilai.setKatNilai(rs.getString("katNilai"));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return nilai;
    }

    @Override
    public boolean isNilaiExist(String nim) {
        boolean exist = false;
        try (PreparedStatement statement = conn.prepareStatement("SELECT COUNT(*) FROM nilai WHERE nim = ?")) {
            statement.setString(1, nim);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    exist = true;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return exist;
    }
    
    @Override
    public void deleteByNim(String nim) {
    try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM nilai WHERE nim = ?")) {
        stmt.setString(1, nim);
        stmt.executeUpdate();
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
}

}
