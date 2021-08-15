package controller;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import library.login;

public class loginController implements Initializable {
    @FXML
    private TextField username;
    @FXML
    private TextArea prompt;
    @FXML
    private PasswordField password;

    public void executeQuery(String query) {
        Connection conn = getConnection();
        Statement st;
        try {
            st = conn.createStatement();
            st.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:database.db");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void signupButton() {
        String un = username.getText();
        System.out.println(un);
        if(un.matches("[A-Za-z0-9]{0,10}")) {
            String query = "insert into Login values('" + un + "','" + password.getText() + "')";
            executeQuery(query);
            prompt.setText("User Added");
        }
        else
            prompt.setText("Unable to add user");
    }
    public void signinButton() throws  IOException {
        login t;
        String un = username.getText();
        if (un.matches("[A-Za-z0-9]{0,10}")) {
            t = new login(un,
                    password.getText());
            ObservableList<login> l;
            l = getLogin();
            boolean flag = false;
            for (library.login login : l) {
                if (login.getUsername().equals(t.getUsername()) && login.getPassword().equals(t.getPassword())) {
                    flag = true;
                    break;
                }
            }
            if (flag) {
                System.out.println("Inside If");
                new Main().changeScene("/view/dashboard.fxml");
            } else {
                prompt.setText("please add user");
            }

        }
        else
            prompt.setText("Give valid username");
    }

    public ObservableList<login> getLogin() {
        ObservableList<login> loginList = FXCollections.observableArrayList();
        Connection connection = getConnection();
        String query = "SELECT * FROM Login";
        Statement st;
        ResultSet rs;
        try {
            st = connection.createStatement();
            rs = st.executeQuery(query);
            login l;
            while (rs.next()) {
                System.out.println(rs.getString("username"));
                l = new login(rs.getString("username"), rs.getString("password"));
                loginList.add(l);
                System.out.println(loginList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return loginList;
    }
}
