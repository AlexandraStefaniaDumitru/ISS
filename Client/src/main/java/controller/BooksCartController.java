package controller;

import domain.Book;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import view.AvailableBooksView;
import view.BooksCartView;

import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.ResourceBundle;

public class BooksCartController extends UnicastRemoteObject implements Initializable {

    @FXML
    public Button confirmBorrowButton;
    private BooksCartView booksCartView;
    private AvailableBooksView availableBooksView;
    private ObservableList<Book> modelBorrowedBooks = FXCollections.observableArrayList();

    @FXML
    private TableView<Book> borrowedBooksCartTableView;

    public BooksCartController() throws RemoteException {
    }

    public void setSummaryBorrowedBooksView(BooksCartView booksCartView) {
        this.booksCartView = booksCartView;
    }

    public void setAvailableBooksView(AvailableBooksView availableBooksView) {
        this.availableBooksView = availableBooksView;
    }

    public void setModelBorrowedBooks(List<Book> burrowedBooks) {
        modelBorrowedBooks.setAll(burrowedBooks);
    }

    public void confirmBorrow() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION,
                "Books successfully borrowed!");
        alert.show();
        booksCartView.hide();
        availableBooksView.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        borrowedBooksCartTableView.setItems(modelBorrowedBooks);
    }
}