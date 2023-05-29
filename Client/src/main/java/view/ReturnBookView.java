package view;

import controller.LibrarianController;
import controller.ReturnBookController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import service.ServiceInterface;

import java.io.IOException;

public class ReturnBookView {
    private static final String PATH_RETURN_BOOK_VIEW = "/views/ReturnBookView.fxml";

    private ReturnBookController returnBookController;
    private Stage returnBookStage = new Stage();

    public ReturnBookView(ServiceInterface serviceInterface, LibrarianController librarianController) throws IOException {

        FXMLLoader returnBookViewLoader = new FXMLLoader(getClass().getResource(PATH_RETURN_BOOK_VIEW));
        Parent returnBookRoot = returnBookViewLoader.load();
        returnBookStage.setTitle("BookTerra");
        returnBookStage.setScene(new Scene(returnBookRoot));
        returnBookStage.setOnCloseRequest(event -> {
            hide();
            librarianController.getLibrarianView().show();
        });

        returnBookController = returnBookViewLoader.getController();
        returnBookController.setServer(serviceInterface);
        returnBookController.setReturnBookView(this);
        show();
    }

    public ReturnBookController getReturnBookController() {
        return returnBookController;
    }

    public void show() {
        returnBookStage.show();
    }

    public void hide() {
        returnBookStage.hide();
    }
}