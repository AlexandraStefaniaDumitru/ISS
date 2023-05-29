package view;

import controller.LibrarianController;
import controller.ManageBooksController;
import domain.Librarian;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import service.ServiceInterface;

import java.io.IOException;

public class LibrarianView {

    private LibrarianController librarianController;
    private Stage mainMenuLibrarianStage = new Stage();

    public LibrarianView(ServiceInterface server, ManageBooksController manageBooksController, Librarian librarian)
            throws IOException {
        FXMLLoader mainMenuLibrarianLoader = new FXMLLoader(getClass().getResource("/views/LibrarianView.fxml"));

        Parent mainMenuLibrarianRoot = mainMenuLibrarianLoader.load();
        mainMenuLibrarianStage.setTitle("Library");
        mainMenuLibrarianStage.setScene(new Scene(mainMenuLibrarianRoot));

        librarianController = mainMenuLibrarianLoader.getController();
        librarianController.setLoggedInLibrarian(librarian);
        librarianController.setServer(server);
        librarianController.setManageBooksController(manageBooksController);
        librarianController.setLibrarianView(this);
    }

    public void show() {
        mainMenuLibrarianStage.show();
    }

    public void hide() {
        mainMenuLibrarianStage.hide();
    }
}