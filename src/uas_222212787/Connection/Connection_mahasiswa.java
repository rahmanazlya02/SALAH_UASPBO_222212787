/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uas_222212787.Connection;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.*;
import javax.swing.*;
import javax.swing.JOptionPane;

/**
 *
 * @author Nazlya
 */
public class Connection_mahasiswa {
    //Connection conn = null;
    
   
    
    /*public static Connection getConnection() {
        Connection conn = null;
        try {
            // Muat driver JDBC SQLite
            Class.forName("org.sqlite.JDBC");
            // Buat koneksi ke database SQLite
            conn = DriverManager.getConnection("jdbc:sqlite:UAS_222212787.db");
            // Aktifkan dukungan foreign key
            try (Statement stmt = conn.createStatement()) {
                stmt.execute("PRAGMA foreign_keys = ON");
            }
            return conn;
        } catch (ClassNotFoundException | SQLException e) {
            // Tampilkan pesan kesalahan jika terjadi masalah saat membuat koneksi
            JOptionPane.showMessageDialog(null, e);
            return null;
        }
    }*/
    
    public static Connection getConnection() {
        try{
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:UAS_222212787.db");
            //JOptionPane.showMessageDialog(null, "Berhasil Koneksi ke Database");
            
            return conn;
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return null;
        }
    }
}
