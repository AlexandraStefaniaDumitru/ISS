package view;

import controller.AvailableBooksController;
import controller.LoginController;
import controller.ManageBooksController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import service.ServiceInterface;

import java.io.IOException;

public class LoginView {

    private Stage loginViewStage;

    public LoginView(Stage primaryStage, ServiceInterface server, AvailableBooksController availableBooksController,
                     ManageBooksController manageBooksController) throws IOException {
        FXMLLoader loginViewLoader = new FXMLLoader(getClass().getResource("/views/LoginView.fxml"));

        Parent loginViewRoot = loginViewLoader.load();
        loginViewStage = primaryStage;
        primaryStage.setTitle("Library");
        primaryStage.setScene(new Scene(loginViewRoot));
        loginViewStage = primaryStage;

        LoginController loginViewController = loginViewLoader.getController();
        loginViewController.setLoginView(this);
        loginViewController.setServer(server);
        loginViewController.setAvailableBooksController(availableBooksController);
        loginViewController.setManageBooksController(manageBooksController);

        primaryStage.show();
    }

    public void show() {
        loginViewStage.show();
    }

    public void hide() {
        loginViewStage.hide();
    }
}