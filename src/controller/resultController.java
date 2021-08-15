package controller;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import library.results;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
interface lambda{
    int returnAvg(int s,int n);
}
public class resultController implements Initializable {
    @FXML
    private TextField idField, nameField, resultField, courseField, statusField, prompt;
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
    private PasswordField pwd;
    @FXML
    private Label average;
    private final String[] pd = {"Karthik", "admin"};

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showResult();
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

    public boolean check_access() {
        boolean flag = false;
        for (String s : pd)
            if (pwd.getText().equals(s)) {
                flag = true;
                break;
            }

        if (flag)
            prompt.setText("Accepted");
        else
            prompt.setText("Wrong Details");
        return flag;
    }

    public void addButton() {
                if (!(idField.getText().isEmpty() && nameField.getText().isEmpty() && resultField.getText().isEmpty() && statusField.getText().isEmpty()) && check_access()) {
                    executeQuery("insert into resultTable values(" + idField.getText() + ",'" + nameField.getText() + "'," + resultField.getText() + ",'" + statusField.getText() + "','" + courseField.getText() + "')");
                    showResult();
                }
    }

    public ObservableList<results> getResults() {
            ObservableList<results> resultList = FXCollections.observableArrayList();
            Connection connection = getConnection();
            String query = "SELECT * FROM resultTable ";
            Statement st;
            ResultSet rs;
            int sum = 0;
            try {
                st = connection.createStatement();
                rs = st.executeQuery(query);
                results res;
                while (rs.next()) {
                    res = new results(rs.getInt("ID"), rs.getString("Name"), rs.getInt("Result"), rs.getString("Status"), rs.getString("Course"));
                    resultList.add(res);
                    sum+=res.getResult();
                }
                lambda p = (s,n)->{
                    try {
                        return s / n;
                    }catch (ArithmeticException e){
                        average.setText("Average is 0");
                    }
                    return 0;
                };
                if(p.returnAvg(sum,resultList.size())!=0)
                    average.setText("Average: "+p.returnAvg(sum,resultList.size())/1.0);

            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(resultList);
            return resultList;
    }

    public void updateButton() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                String query = "UPDATE resultTable SET Name='" + nameField.getText() + "',Result=" + resultField.getText() + ",Status='" + statusField.getText() + "',Course='" + courseField.getText() + "' WHERE ID=" + idField.getText() + "";
                executeQuery(query);
                showResult();
            }
        });
        t.start();
    }
    public void deleteButton() {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    String query = "DELETE FROM resultTable WHERE ID=" + idField.getText() + "";
                    executeQuery(query);
                    showResult();
                }
            });
            t.start();
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
