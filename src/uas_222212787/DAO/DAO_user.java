/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uas_222212787.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import uas_222212787.DAOimplement.Impl_User;
import uas_222212787.Model.User;
import uas_222212787.Connection.Connection_mahasiswa;

/**
 *
 * @author Nazlya
 */
public class DAO_user implements Impl_User {
    @Override
    public User getUser(String username) {
        User user = null;
        try (Connection connection = Connection_mahasiswa.getConnection()) {
            String query = "SELECT * FROM user WHERE username=?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, username);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        user = new User();
                        user.setUsername(resultSet.getString("username"));
                        user.setPassword(resultSet.getString("password"));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

@Override
public void insert(User user) {
    try (Connection connection = Connection_mahasiswa.getConnection()) {
        String query = "INSERT INTO user (username, password) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, user.getUsername());
            String Password = user.getPassword(); // Menggunakan HashUtil.hashPassword()
            statement.setString(2, Password); // Menyimpan kata sandi yang sudah dienkripsi
            statement.executeUpdate();
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

}
