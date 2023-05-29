package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import service.ServiceInterface;
import view.ReturnBookView;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ReturnBookController extends UnicastRemoteObject {

    private ReturnBookView returnBookView;
    private ServiceInterface server;
    @FXML
    public Button returnButton;
    @FXML
    public TextField usernameTF;
    @FXML
    public TextField isbnTF;

    public ReturnBookController() throws RemoteException {
    }
    public void setReturnBookView(ReturnBookView returnBookView) {
        this.returnBookView = returnBookView;
    }

    public void setServer(ServiceInterface server) {
        this.server = server;
    }

    public void returnBook() {
        String readerUsername = usernameTF.getText();
        String ISBNString = isbnTF.getText();
        if (!readerUsername.isBlank() && !ISBNString.isBlank()) {
            int ISBN = Integer.parseInt(ISBNString);
            boolean result = server.returnBook(readerUsername, ISBN);
            if (result) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "The book has been returned successfully!");
                alert.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Error at returning the book!");
                alert.show();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please introduce all the username and the ISBN!");
            alert.show();
        }
    }
}