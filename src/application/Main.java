package application;

import javafx.application.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    private static Stage tmp;
    @Override
    public void start(Stage stage) throws Exception {
        tmp = stage;
        Parent parent = (Parent) FXMLLoader.load(getClass().getResource(
                "/view/loginpage.fxml"));
        Scene scene = new Scene(parent);

        stage.setScene(scene);
        stage.setTitle("School Management");
        stage.show();
    }
    public void changeScene(String fxml) throws IOException {
            Parent pane = FXMLLoader.load(getClass().getResource(fxml));
            tmp.getScene().setRoot(pane);
    }
    public static void main(String[] args) {
        launch(args);
    }
}
