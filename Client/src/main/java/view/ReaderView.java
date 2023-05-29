package view;

import controller.AvailableBooksController;
import controller.ReaderController;
import domain.Reader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import service.ServiceInterface;

import java.io.IOException;

public class ReaderView {

    private ReaderController readerController;
    private Stage mainMenuReaderStage = new Stage();

    public ReaderView(ServiceInterface server, AvailableBooksController availableBooksController, Reader loggedInReader)
            throws IOException {
        FXMLLoader mainMenuReaderLoader = new FXMLLoader(getClass().getResource("/views/ReaderView.fxml"));

        Parent mainMenuReaderRoot = mainMenuReaderLoader.load();
        mainMenuReaderStage.setScene(new Scene(mainMenuReaderRoot));
        mainMenuReaderStage.setTitle("Library");

        readerController = mainMenuReaderLoader.getController();
        readerController.setMainMenuReaderView(this);
        readerController.setAvailableBooksController(availableBooksController);
        readerController.setServer(server);
        readerController.setLoggedInReader(loggedInReader);
    }

    public void show() {
        mainMenuReaderStage.show();
    }

    public void hide() {
        mainMenuReaderStage.hide();
    }
}