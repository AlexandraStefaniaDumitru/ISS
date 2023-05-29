package controller;

import domain.Book;
import domain.Reader;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import service.LibraryException;
import service.Observer;
import service.ServiceInterface;
import view.AvailableBooksView;
import view.BooksCartView;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AvailableBooksController extends UnicastRemoteObject implements Observer, Initializable, Serializable {

    @FXML
    public Button addToCartButton;
    @FXML
    public Button removeFromCartButton;
    @FXML
    public Button borrowBooksButton;
    private ServiceInterface server;
    private AvailableBooksView availableBooksView;
    private final ObservableList<Book> modelAvailableBooks = FXCollections.observableArrayList();
    private Reader loggedInReader;
    private final List<Book> booksAddedToCart = new ArrayList<>();

    @FXML
    private TableView<Book> availableBooksTableView;

    public AvailableBooksController() throws RemoteException {

    }

    public AvailableBooksView getAvailableBooksView() {
        return availableBooksView;
    }

    public void setAvailableBooksView(AvailableBooksView availableBooksView) {
        this.availableBooksView = availableBooksView;
    }

    public void setServer(ServiceInterface server) {
        this.server = server;
    }

    public void setLoggedInReader(Reader loggedInReader) {
        this.loggedInReader = loggedInReader;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        availableBooksTableView.setItems(modelAvailableBooks);
    }

    @Override
    public void update() throws RemoteException {
        Platform.runLater(this::reloadModelAvailableBooks);
    }

    public void reloadModelAvailableBooks() {
        try {
            modelAvailableBooks.setAll(server.getAvailableBooks());
            if (modelAvailableBooks.size() == 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "There isn't any available book!");
                alert.show();
            }
        } catch (LibraryException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Couldn't reload the table!");
            alert.show();
        }
    }

    public void addToCart() {
        Book book = availableBooksTableView.getSelectionModel().getSelectedItem();
        if (book != null) {
            booksAddedToCart.add(book);
            availableBooksTableView.getSelectionModel().clearSelection();
        }
    }

    public void removeFromCart() {
        Book book = availableBooksTableView.getSelectionModel().getSelectedItem();
        if (book != null) {
            booksAddedToCart.remove(book);
            availableBooksTableView.getSelectionModel().clearSelection();
        }
    }

    public void borrowBooks() {
        if (booksAddedToCart.size() > 0) {
            try {
                booksAddedToCart.forEach(book -> {
                    book.setIsAvailable(false);
                    loggedInReader.addBook(book);
                });
                server.borrowBooks(loggedInReader, booksAddedToCart);

                BooksCartView booksCartView = new BooksCartView(availableBooksView, booksAddedToCart);
                booksCartView.show();
                booksAddedToCart.clear();
            } catch (LibraryException exception) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Couldn't borrow the books!");
                alert.show();
            } catch (IOException exception) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Error at showing the confirmation for the borrowed books!");
                alert.show();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "There is no book in the cart!");
            alert.show();
        }
    }
}