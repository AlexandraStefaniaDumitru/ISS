package view;

import controller.BooksCartController;
import domain.Book;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class BooksCartView {

    private final Stage summaryBorrowedBooksStage = new Stage();

    public BooksCartView(AvailableBooksView availableBooksView, List<Book> borrowedBooks) throws IOException {
        FXMLLoader summaryBorrowedBooksViewLoader = new FXMLLoader(getClass().getResource("/views/BooksCart.fxml"));

        Parent summaryBorrowedBooksRoot = summaryBorrowedBooksViewLoader.load();
        summaryBorrowedBooksStage.setTitle("Library");
        summaryBorrowedBooksStage.setScene(new Scene(summaryBorrowedBooksRoot));

        BooksCartController booksCartController = summaryBorrowedBooksViewLoader.getController();
        booksCartController.setSummaryBorrowedBooksView(this);
        booksCartController.setAvailableBooksView(availableBooksView);
        booksCartController.setModelBorrowedBooks(borrowedBooks);
    }

    public void show() {
        summaryBorrowedBooksStage.show();
    }

    public void hide() {
        summaryBorrowedBooksStage.hide();
    }
}