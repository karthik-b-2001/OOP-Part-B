package controller;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import javafx.scene.control.cell.PropertyValueFactory;
import library.course;
public class courseViewController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {  }

    @FXML
    private TableView courseTable;
    @FXML
    private TableColumn<course, String> namecol;
    @FXML
    private TableColumn<course, String> usernamecol;

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

    public ObservableList<course> getCourse() {
        ObservableList<course> courseList = FXCollections.observableArrayList();
        Connection connection = getConnection();
        String query = "SELECT * FROM Courses";
        Statement st;
        ResultSet rs;
        try {
            st = connection.createStatement();
            rs = st.executeQuery(query);
            course res;
            while(rs.next()){
                res = new course(rs.getString("Name"),rs.getString("Username"));
                courseList.add(res);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(courseList);
        return courseList;
    }

    public void showCourse(){
        ObservableList<course> t = getCourse();
        namecol.setCellValueFactory(new PropertyValueFactory<course,String>("name"));
        usernamecol.setCellValueFactory(new PropertyValueFactory<course,String>("username"));
        courseTable.setItems(t);
    }
    public void back() throws IOException {
        new Main().changeScene("/view/dashboard.fxml");
    }
}
