package service;

import domain.Book;
import domain.Librarian;
import domain.Reader;

import java.util.List;

public interface ServiceInterface {

    Reader loginReader(String username, String password, Observer observerClient) throws LibraryException;

    Librarian loginLibrarian(String username, String password, Observer observerClient) throws LibraryException;

    void registerNewReader(Reader reader) throws LibraryException;

    List<Book> getAllBooks() throws LibraryException;

    List<Book> getAvailableBooks() throws LibraryException;

    List<Book> getBorrowedBooksByReader(Reader reader) throws LibraryException;

    void borrowBooks(Reader reader, List<Book> booksToBorrow) throws LibraryException;

    boolean addBook(Librarian librarian, String title, String author, Integer publicationYear) throws LibraryException;

    Book removeBook(Book book) throws LibraryException;

    boolean returnBook(String username, int ISBN);
}