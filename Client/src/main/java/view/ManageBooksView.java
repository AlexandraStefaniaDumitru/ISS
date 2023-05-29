package view;

import controller.LibrarianController;
import controller.ManageBooksController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import service.ServiceInterface;

import java.io.IOException;

public class ManageBooksView {


    private ManageBooksController manageBooksController;
    private LibrarianController librarianController;
    private Stage manageBooksStage = new Stage();


    public ManageBooksView(ServiceInterface server) throws IOException {
        FXMLLoader manageBooksViewLoader = new FXMLLoader(getClass().getResource("/views/ManageBooksView.fxml"));

        Parent manageBooksRoot = manageBooksViewLoader.load();
        manageBooksStage.setTitle("Library");
        manageBooksStage.setScene(new Scene(manageBooksRoot));
        manageBooksStage.setOnCloseRequest(event -> {
            hide();
            librarianController.getLibrarianView().show();
        });
        manageBooksController = manageBooksViewLoader.getController();
        manageBooksController.setServer(server);
        manageBooksController.setManageBooksView(this);
    }

    public void show() {
        manageBooksStage.show();
    }
    public void hide() {
        manageBooksStage.hide();
    }
    public ManageBooksController getManageBooksController() {
        return manageBooksController;
    }

    public void setLibrarianController(LibrarianController librarianController) {
        this.librarianController = librarianController;
    }
}