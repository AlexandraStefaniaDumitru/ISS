import repository.*;

public class Main {

    public static void main(String[] args) {
        BookRepository bookRepository = new BookRepositoryDB();
        LibraryRepository libraryRepository = new LibraryRepositoryDB();
        LibrarianRepository librarianRepository = new LibrarianRepositoryDB();
        ReaderRepository readerRepository = new ReaderRepositoryDB();
    }
}