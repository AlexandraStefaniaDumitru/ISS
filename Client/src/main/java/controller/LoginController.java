package controller;

import domain.Librarian;
import domain.Reader;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import service.LibraryException;
import service.ServiceInterface;
import view.LibrarianView;
import view.LoginView;
import view.ReaderView;
import view.RegisterView;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class LoginController extends UnicastRemoteObject {

    @FXML
    public Button readerLoginButton;
    @FXML
    public Button librarianLoginButtom;
    @FXML
    public Label labelRegister;
    private ServiceInterface server;
    private AvailableBooksController availableBooksController;
    private ManageBooksController manageBooksController;
    private LoginView loginView;

    @FXML
    private TextField usernameTF;

    @FXML
    private TextField passwordTF;

    public LoginController() throws RemoteException {

    }

    public void setAvailableBooksController(AvailableBooksController availableBooksController) {
        this.availableBooksController = availableBooksController;
    }

    public void setManageBooksController(ManageBooksController manageBooksController) {
        this.manageBooksController = manageBooksController;
    }

    public void setServer(ServiceInterface server) {
        this.server = server;
    }

    public LoginView getLoginView() {
        return loginView;
    }

    public void setLoginView(LoginView loginView) {
        this.loginView = loginView;
    }

    public void loginReader() {
        String username = usernameTF.getText();
        String password = passwordTF.getText();
        if (!username.isBlank() && !password.isBlank()) {
            try {
                Reader loggedInReader = server.loginReader(username, password, availableBooksController);
                if (loggedInReader != null) {
                    System.out.println("Successful authentication!");
                    ReaderView readerView = new ReaderView(server, availableBooksController, loggedInReader);
                    readerView.show();

                    loginView.hide();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid credentials!");
                    alert.show();
                }
            } catch (LibraryException | IOException exception) {
                System.err.println("Failed authentication: " + exception);
                Alert alert = new Alert(Alert.AlertType.ERROR, "Failed authentication!");
                alert.show();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter the credentials!");
            alert.show();
        }
    }

    public void loginLibrarian() {
        String username = usernameTF.getText();
        String password = passwordTF.getText();
        if (!username.isBlank() && !password.isBlank()) {
            try {
                Librarian loggedInLibrarian = server.loginLibrarian(username, password, manageBooksController);
                if (loggedInLibrarian != null) {
                    System.out.println("Successful authentication!");
                    LibrarianView librarianView = new LibrarianView(server, manageBooksController, loggedInLibrarian);
                    librarianView.show();

                    loginView.hide();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid credentials!");
                    alert.show();
                }
            } catch (LibraryException | IOException exception) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Failed authentication!");
                alert.show();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter the credentials!");
            alert.show();
        }
    }

    public void registerReader() {
        try {
            RegisterView registerView = new RegisterView(server, this);
            registerView.show();
            loginView.hide();
        } catch (IOException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error at showing the registration form");
            alert.show();
        }
    }
}