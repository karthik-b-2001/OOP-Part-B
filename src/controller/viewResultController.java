package controller;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import library.results;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class viewResultController implements Initializable {
    @FXML
    private TableView<results> resultsTableView;
    @FXML
    private TableColumn<results, Integer> idColumn;
    @FXML
    private TableColumn<results, Integer> resultColumn;
    @FXML
    private TableColumn<results, String> nameColumn;
    @FXML
    private TableColumn<results, String> statusColumn, courseColumn;
    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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
    public ObservableList<results> getResults() {
        ObservableList<results> resultList = FXCollections.observableArrayList();
        Connection connection = getConnection();
        String query = "SELECT * FROM resultTable ";
        Statement st;
        ResultSet rs;

        try {
            st = connection.createStatement();
            rs = st.executeQuery(query);
            results res;
            while (rs.next()) {
                res = new results(rs.getInt("ID"), rs.getString("Name"), rs.getInt("Result"), rs.getString("Status"), rs.getString("Course"));
                resultList.add(res);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(resultList);
        return resultList;
    }
    public void showResult(){
        ObservableList<results> t = getResults();
        idColumn.setCellValueFactory(new PropertyValueFactory<results,Integer>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<results,String>("name"));
        resultColumn.setCellValueFactory(new PropertyValueFactory<results,Integer>("result"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<results,String>("status"));
        courseColumn.setCellValueFactory(new PropertyValueFactory<results,String>("course"));
        resultsTableView.setItems(t);
    }
    public void back() throws IOException {
        new Main().changeScene("/view/dashboard.fxml");
    }
}
