package controller;

import domain.Reader;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import service.LibraryException;
import service.ServiceInterface;
import view.RegisterView;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RegisterController extends UnicastRemoteObject {

    @FXML
    public Button registerButton;
    private RegisterView registerView;
    private ServiceInterface server;
    private LoginController loginController;

    @FXML
    private TextField nameTF;
    @FXML
    private TextField cnpTF;
    @FXML
    private TextField emailTF;
    @FXML
    private TextField phoneTF;
    @FXML
    private TextField usernameTF;
    @FXML
    private TextField passwordTF;

    public RegisterController() throws RemoteException {

    }

    public void setRegisterView(RegisterView registerView) {
        this.registerView = registerView;
    }

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

    public void setServer(ServiceInterface server) {
        this.server = server;
    }

    public void confirmRegistration() {
        String fullName = nameTF.getText();
        String CNP = cnpTF.getText();
        String email = emailTF.getText();
        String phoneNumber = phoneTF.getText();
        String username = usernameTF.getText();
        String password = passwordTF.getText();
        if (fullName.isEmpty() || CNP.isEmpty() || email.isEmpty() || phoneNumber.isEmpty() || username.isEmpty() || password.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please fill all fields!");
            alert.show();
        } else {
            Reader reader = new Reader(fullName, username, password, CNP, email, phoneNumber);
            try {
                server.registerNewReader(reader);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Registered successfully!");
                alert.show();

                loginController.getLoginView().show();
                registerView.hide();
            } catch (LibraryException exception) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "We couldn't register you! Please try again!");
                alert.show();
            }
        }
    }
}