package controller;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;


import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class courseController implements Initializable {
    @FXML
    private TextField username;
    @FXML
    private MenuItem mathmenu,sciencemenu,englishmenu,pemenu;
    @FXML
    private Label plabel;
    private int flag = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){ }
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
    public void addCourse(){
        String un = username.getText();
        String co ;
        switch (flag) {
            case 1 -> { co = getMath();  }
            case 2 -> { co = getScience();  }
            case 3 -> { co = getEnglish();  }
            case 4-> { co = getPE();  }
            default -> {co = null;}
        }
        System.out.println(co+" "+un);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                executeQuery("insert into courses values('"+co+"','"+un+"')");
            }
        });
        t.start();
        plabel.setText("Added: "+co);
    }
    public String getMath(){
        flag = 1;
        plabel.setText("Selected: "+mathmenu.getText());
        return mathmenu.getText();
    }
    public String getScience(){
        flag=2;
        plabel.setText("Selected: "+sciencemenu.getText());
        return sciencemenu.getText();
    }
    public String getEnglish(){
        flag=3;
        plabel.setText("Selected: "+englishmenu.getText());
        return englishmenu.getText();
    }
    public String getPE(){
        flag=4;
        plabel.setText("Selected: "+pemenu.getText());
        return pemenu.getText();
    }
    public void back() throws IOException {
        new Main().changeScene("/view/dashboard.fxml");
    }

}
