package view;

import controller.AvailableBooksController;
import controller.ReaderController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import service.ServiceInterface;

import java.io.IOException;

public class AvailableBooksView {

    private AvailableBooksController availableBooksController;
    private ReaderController readerController;
    private Stage availableBooksStage = new Stage();

    public AvailableBooksView(ServiceInterface server) throws IOException {
        FXMLLoader availableBooksViewLoader = new FXMLLoader(getClass().getResource("/views/AvailableBooksView.fxml"));

        Parent availableBooksRoot = availableBooksViewLoader.load();
        availableBooksStage.setTitle("Library");
        availableBooksStage.setScene(new Scene(availableBooksRoot));

        availableBooksStage.setOnCloseRequest((event) -> {
            hide();
            readerController.getMainMenuReaderView().show();
        });

        availableBooksController = availableBooksViewLoader.getController();
        availableBooksController.setAvailableBooksView(this);
        availableBooksController.setServer(server);
    }

    public void setMainMenuReaderController(ReaderController readerController) {
        this.readerController = readerController;
    }

    public AvailableBooksController getAvailableBooksController() {
        return availableBooksController;
    }

    public void show()  {
        availableBooksStage.show();
    }

    public void hide() {
        availableBooksStage.hide();
    }
}