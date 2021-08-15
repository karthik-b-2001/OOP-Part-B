package controller;

import application.Main;

import java.io.IOException;

public class dashboardController {
    public void goToresult() throws IOException {
        new Main().changeScene("/view/result.fxml");
    }
    public void goTocourse() throws IOException{
        new Main().changeScene("/view/coursepage.fxml");
    }
    public void goToviewRes() throws IOException{
        new Main().changeScene("/view/viewResult.fxml");
    }
    public void goToviewCou() throws IOException{
        new Main().changeScene("/view/viewCourses.fxml");
    }
    public void Logout() throws IOException{
        new Main().changeScene("/view/loginpage.fxml");
    }
}



