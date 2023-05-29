package controller;

import domain.Reader;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import service.ServiceInterface;
import view.BorrowedBooksView;
import view.ReaderView;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ReaderController extends UnicastRemoteObject {

    @FXML
    public Button availableBooksButton;
    @FXML
    public Button borrowedBooksButton;
    private ServiceInterface server;
    private AvailableBooksController availableBooksController;
    private ReaderView readerView;
    private Reader loggedInReader;

    @FXML
    private Label labelReader;

    public ReaderController() throws RemoteException {
    }

    public void setServer(ServiceInterface server) {
        this.server = server;
    }

    public void setAvailableBooksController(AvailableBooksController availableBooksController) {
        this.availableBooksController = availableBooksController;
    }

    public ReaderView getMainMenuReaderView() {
        return readerView;
    }

    public void setMainMenuReaderView(ReaderView readerView) {
        this.readerView = readerView;
    }

    public void setLoggedInReader(Reader loggedInReader) {
        this.loggedInReader = loggedInReader;
        availableBooksController.setLoggedInReader(loggedInReader);
        labelReader.setText("Logged reader: " + loggedInReader.getName());
    }

    public void showAvailableBooks() {
        availableBooksController.getAvailableBooksView().show();
        availableBooksController.reloadModelAvailableBooks();
        availableBooksController.getAvailableBooksView().setMainMenuReaderController(this);
        readerView.hide();
    }

    public void showBorrowedBooks() {
        try {
            BorrowedBooksView burrowedBooksView = new BorrowedBooksView(server, this, loggedInReader);
            burrowedBooksView.show();
            readerView.hide();
        } catch (IOException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error at showing the burrowed books!");
            alert.show();
        }
    }
}