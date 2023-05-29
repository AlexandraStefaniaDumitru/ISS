package controller;

import domain.Book;
import domain.Reader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import service.LibraryException;
import service.ServiceInterface;
import view.BorrowedBooksView;

import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ResourceBundle;

public class BorrowedBooksController extends UnicastRemoteObject implements Initializable {

    private ServiceInterface server;
    private BorrowedBooksView borrowedBooksView;
    private Reader loggedInReader;
    private ObservableList<Book> modelBorrowedBooks = FXCollections.observableArrayList();

    @FXML
    private Label borrowedBooksLabel;

    @FXML
    private TableView<Book> borrowedBooksTableView;

    public BorrowedBooksController() throws RemoteException {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
            borrowedBooksTableView.setItems(modelBorrowedBooks);
    }

    public void setServer(ServiceInterface server) {
        this.server = server;
    }

    public void setBorrowedBooksView(BorrowedBooksView borrowedBooksView) {
        this.borrowedBooksView = borrowedBooksView;
    }

    public void setLoggedInReader(Reader loggedInReader) {
        this.loggedInReader = loggedInReader;
    }

    public void showBorrowedBooks() {
        try {
            modelBorrowedBooks.setAll(server.getBorrowedBooksByReader(loggedInReader));
        } catch (LibraryException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error at getting the borrowed books!");
            alert.show();
        }
    }
}