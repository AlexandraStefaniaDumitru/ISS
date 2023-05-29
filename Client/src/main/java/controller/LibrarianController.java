package controller;

import domain.Librarian;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import service.ServiceInterface;
import view.LibrarianView;
import view.ReturnBookView;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class LibrarianController extends UnicastRemoteObject {

    @FXML
    public Button returnButton;
    @FXML
    public Button manageButton;
    private Librarian loggedInLibrarian;
    private ServiceInterface server;
    private ManageBooksController manageBooksController;
    private LibrarianView librarianView;

    @FXML
    private Label labelLibrarian;

    public LibrarianController() throws RemoteException {

    }

    public void setLoggedInLibrarian(Librarian loggedInLibrarian) {
        this.loggedInLibrarian = loggedInLibrarian;
        labelLibrarian.setText("Logged librarian: " + loggedInLibrarian.getName());
    }

    public LibrarianView getLibrarianView() {
        return librarianView;
    }
    public void setManageBooksController(ManageBooksController manageBooksController) {
        this.manageBooksController = manageBooksController;
        this.manageBooksController.getManageBooksView().setLibrarianController(this);
    }

    public void setServer(ServiceInterface server) {
        this.server = server;
    }

    public void setLibrarianView(LibrarianView librarianView) {
        this.librarianView = librarianView;
    }

    public void showAllBooks() {
        manageBooksController.setLoggedInLibrarian(loggedInLibrarian);
        manageBooksController.reloadBooks();
        manageBooksController.getManageBooksView().show();
        librarianView.hide();
    }

    public void returnBook() {
        try {
            new ReturnBookView(server, this);
            librarianView.hide();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error at showing the return book form!");
            alert.show();
        }
    }
}