import domain.Book;
import domain.Librarian;
import domain.Reader;
import repository.BookRepository;
import repository.LibrarianRepository;
import repository.LibraryRepository;
import repository.ReaderRepository;
import service.LibraryException;
import service.Observer;
import service.ServiceInterface;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Service implements ServiceInterface {

    private static final int NUMBER_OF_THREADS = 5;
    private final Map<Integer, Observer> connectedClients = new ConcurrentHashMap<>();
    private ReaderRepository readerRepository;
    private BookRepository bookRepository;
    private LibrarianRepository librarianRepository;
    private LibraryRepository libraryRepository;

    public Service() {

    }

    public Service(ReaderRepository readerRepository, BookRepository bookRepository, LibrarianRepository librarianRepository, LibraryRepository libraryRepository) {
        this.readerRepository = readerRepository;
        this.bookRepository = bookRepository;
        this.librarianRepository = librarianRepository;
        this.libraryRepository = libraryRepository;
    }

    @Override
    public synchronized Reader loginReader(String username, String password, Observer observerClient) throws LibraryException {
        Reader reader = readerRepository.findReaderByUsernameAndPassword(username, password);
        if (reader != null) {
            if (connectedClients.get(reader.getID()) != null) {
                throw new LibraryException("The user is already logged in!");
            }
            connectedClients.put(reader.getID(), observerClient);
        }
        return reader;
    }

    @Override
    public Librarian loginLibrarian(String username, String password, Observer observerClient) throws LibraryException {
        Librarian librarian = librarianRepository.findLibrarianByUsernameAndPassword(username, password);
        if (librarian != null) {
            if (connectedClients.get(librarian.getID()) != null) {
                throw new LibraryException("The librarian is already logged in!");
            }
            connectedClients.put(librarian.getID(), observerClient);
        }
        return librarian;
    }

    @Override
    public void registerNewReader(Reader reader) {
        readerRepository.save(reader);
    }

    @Override
    public List<Book> getAllBooks() {
        List<Book> bookRegister = new ArrayList<>();
        bookRepository.findAll().forEach(book -> {
            System.out.println(book);
            bookRegister.add(book);
        });
        return bookRegister;
    }

    @Override
    public List<Book> getAvailableBooks() {
        List<Book> books = new ArrayList<>();
        bookRepository.findAllByIsAvailable(true).forEach(books::add);
        books.forEach(System.out::println);
        return books;
    }


    @Override
    public List<Book> getBorrowedBooksByReader(Reader reader) {
        List<Book> borrowedBooks = new ArrayList<>(reader.getBooks());
        borrowedBooks.forEach(System.out::println);
        return borrowedBooks;
    }

    @Override
    public void borrowBooks(Reader reader, List<Book> booksToBorrow) {
        booksToBorrow.forEach(System.out::println);
        booksToBorrow.forEach(book -> {
            book.setIsAvailable(false);
        });
        booksToBorrow.forEach(reader::addBook);
        readerRepository.modify(reader);
        notifyClientsBorrowedBooks();
    }


    @Override
    public boolean addBook(Librarian librarian, String title, String author, Integer publicationYear) {
        Book book = new Book(title, author, publicationYear, true, librarian.getLibrary(), null);
        bookRepository.save(book);
        notifyClientsBorrowedBooks();
        return true;
    }

    @Override
    public Book removeBook(Book book) {
        Book deletedBook = bookRepository.delete(book.getID());
        notifyClientsBorrowedBooks();
        return deletedBook;
    }

    @Override
    public boolean returnBook(String username, int ISBN) {
        Reader reader = readerRepository.findReaderByUsername(username);
        if (reader == null) {
            return false;
        }
        Book book = bookRepository.findOne(ISBN);
        if (book == null) {
            return false;
        }
        reader.removeBook(book);
        book.setIsAvailable(true);
        bookRepository.modify(book);
        readerRepository.modify(reader);
        notifyClientsBorrowedBooks();
        return true;
    }

    private void notifyClientsBorrowedBooks() {
        ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
        connectedClients.forEach((id, client) -> {
            if (client != null) {
                System.out.println("Notifying " + id);
                try {
                    client.update();
                } catch (LibraryException | RemoteException exception) {
                    System.err.println("Error at updating the client for borrowed books: " + exception);
                }
            }
        });
        executorService.shutdown();
    }
}