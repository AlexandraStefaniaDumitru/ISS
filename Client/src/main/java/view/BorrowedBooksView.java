package view;

import controller.BorrowedBooksController;
import controller.ReaderController;
import domain.Reader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import service.ServiceInterface;

import java.io.IOException;

public class BorrowedBooksView {

    private BorrowedBooksController borrowedBooksController;
    private Stage borrowedBooksStage = new Stage();

    public BorrowedBooksView(ServiceInterface server, ReaderController readerController, Reader loggedInReader) throws IOException {
        FXMLLoader burrowedBooksViewLoader = new FXMLLoader(getClass().getResource("/views/BorrowedBooksView.fxml"));

        Parent burrowedBooksRoot = burrowedBooksViewLoader.load();
        borrowedBooksController = burrowedBooksViewLoader.getController();
        borrowedBooksStage.setScene(new Scene(burrowedBooksRoot));
        borrowedBooksStage.setTitle("Library");
        borrowedBooksStage.setOnCloseRequest((event) -> {
            hide();
            readerController.getMainMenuReaderView().show();
        });

        borrowedBooksController.setServer(server);
        borrowedBooksController.setBorrowedBooksView(this);
        borrowedBooksController.setLoggedInReader(loggedInReader);
        borrowedBooksController.showBorrowedBooks();
    }

    public void show() {
        borrowedBooksStage.show();
    }

    public void hide() {
        borrowedBooksStage.hide();
    }
}