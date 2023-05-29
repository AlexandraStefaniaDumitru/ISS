package controller;

import domain.Book;
import domain.Librarian;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import service.LibraryException;
import service.Observer;
import service.ServiceInterface;
import view.ManageBooksView;

import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ResourceBundle;

public class ManageBooksController extends UnicastRemoteObject implements Observer, Initializable {
    private final ObservableList<Book> modelBooks = FXCollections.observableArrayList();
    private ServiceInterface server;
    private ManageBooksView manageBooksView;
    private Librarian loggedInLibrarian;

    @FXML
    public TableView<Book> tableViewBorrowedBooks;
    @FXML
    public Button removeButton;
    @FXML
    public TextField titleTF;
    @FXML
    public TextField authorTF;
    @FXML
    public TextField publicationYearTF;
    @FXML
    public Button buttonAddBook;


    public ManageBooksController() throws RemoteException {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tableViewBorrowedBooks.setItems(modelBooks);
    }

    public void setServer(ServiceInterface server) {
        this.server = server;
    }

    public void setLoggedInLibrarian(Librarian loggedInLibrarian) {
        this.loggedInLibrarian = loggedInLibrarian;
    }

    public void setManageBooksView(ManageBooksView manageBooksView) {
        this.manageBooksView = manageBooksView;
    }

    public ManageBooksView getManageBooksView() {
        return manageBooksView;
    }

    public void reloadBooks() {
        try {
            modelBooks.setAll(server.getAllBooks());
            if (modelBooks.size() == 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "There are no books!");
                alert.show();
            }
        } catch (LibraryException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Couldn't reload the table!");
            alert.show();
        }
    }

    public void addBook() {
        String title = titleTF.getText();
        String author = authorTF.getText();
        String publicationYearString = publicationYearTF.getText();
        if (!title.isBlank() && !author.isBlank() && !publicationYearString.isBlank()) {
            int publicationYear = Integer.parseInt(publicationYearString);
            try {
                boolean success = server.addBook(loggedInLibrarian, title, author, publicationYear);
                if (!success) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Error at adding the new book!");
                    alert.show();
                }
            } catch (LibraryException exception) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Error at adding the new book!");
                alert.show();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter all fields!");
            alert.show();
        }
    }

    public void removeBook() {
        Book book = tableViewBorrowedBooks.getSelectionModel().getSelectedItem();
        if (book != null) {
            if (book.isAvailable()) {
                try {
                    Book deletedBook = server.removeBook(book);
                    if (deletedBook != null) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION, "The book has been deleted!");
                        alert.show();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "The book couldn't be deleted!");
                        alert.show();
                    }
                } catch (LibraryException exception) {
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "The book is already borrowed!");
                alert.show();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a book to delete!");
            alert.show();
        }
    }

    @Override
    public void update() throws RemoteException {
        Platform.runLater(this::reloadBooks);
    }
}