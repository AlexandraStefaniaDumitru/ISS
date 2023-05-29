package domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Book implements Identifiable<Integer> {

    private static final long serialVersionUID = 12334547428888L;

    private int ISBN;
    private String title;
    private String author;
    private Integer publicationYear;
    private boolean isAvailable;
    private Library library;
    private Set<Reader> readers = new HashSet<>(0);

    public Book() {

    }

    public Book(String title, String author, Integer publicationYear, boolean isAvailable, Library library, Set<Reader> readers) {
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.isAvailable = isAvailable;
        this.library = library;
        this.readers = readers;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(Integer publicationYear) {
        this.publicationYear = publicationYear;
    }

    public boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(boolean available) {
        isAvailable = available;
    }

    public Set<Reader> getReaders() {
        return readers;
    }

    public void setReaders(Set<Reader> readers) {
        this.readers = readers;
    }

    public void addReader(Reader reader) {
        readers.add(reader);
    }

    public void removeReader(Reader reader) {
        readers.remove(reader);
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getISBN() {
        return getID();
    }

    public void setISBN(int ISBN) {
        setID(ISBN);
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public Library getLibrary() {
        return library;
    }

    public void setLibrary(Library library) {
        this.library = library;
    }

    @Override
    public Integer getID() {
        return this.ISBN;
    }

    @Override
    public void setID(Integer id) {
        this.ISBN = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return ISBN == book.ISBN && isAvailable == book.isAvailable && Objects.equals(title, book.title) && Objects.equals(publicationYear, book.publicationYear) && Objects.equals(author, book.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ISBN, title, publicationYear, isAvailable, author);
    }

    @Override
    public String toString() {
        return "Book = " + this.title + ", ISBN = " + this.ISBN + ", author = " + this.author + ", publicationYear = " + this.publicationYear + ", isAvailable = " + this.isAvailable + ", library = " + this.library;
    }
}