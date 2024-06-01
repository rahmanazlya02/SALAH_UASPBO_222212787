/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uas_222212787.Controller;

import javax.swing.*;
import uas_222212787.DAO.DAO_user;
import uas_222212787.DAOimplement.Impl_User;
import uas_222212787.Model.User;
import uas_222212787.View.LoginPanel;
import uas_222212787.View.MainFrame;
import uas_222212787.View.homePanel;

/**
 *
 * @author Nazlya
 */
public class Controller_User {
    private LoginPanel loginView;
    private MainFrame mainFrame;
    private DAO_user dao_user;

    public Controller_User(LoginPanel loginView, MainFrame mainFrame) {
        this.loginView = loginView;
        this.mainFrame = mainFrame;
        this.dao_user = new DAO_user();
    }

    public void login() {
        String username = loginView.getUsername();
        String password = loginView.getPassword();
        User user = dao_user.getUser(username);
        if (user != null && user.getPassword().equals(password)) {
            loginView.showLoginSuccess();
        } else {
            JOptionPane.showMessageDialog(loginView, "Invalid username or password!");
        }
    }
}
