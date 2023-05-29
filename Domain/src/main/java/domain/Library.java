package domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Library implements Identifiable<Integer> {

    private static final long serialVersionUID = 1247836234234L;

    private int ID;
    private String address;
    private String name;
    private Set<Book> books = new HashSet<>(0);
    private Set<Librarian> librarians = new HashSet<>(0);

    public Library() {

    }

    public Library(String address, String name, Set<Book> books, Set<Librarian> librarians) {
        this.address = address;
        this.name = name;
        this.books = books;
        this.librarians = librarians;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void removeBook(Book book) {
        books.remove(book);
    }

    public Set<Librarian> getLibrarians() {
        return librarians;
    }

    public void setLibrarians(Set<Librarian> librarians) {
        this.librarians = librarians;
    }

    public void addLibrarian(Librarian librarian) {
        librarians.add(librarian);
    }

    public void removeLibrarian(Librarian librarian) {
        librarians.remove(librarian);
    }

    @Override
    public void setID(Integer id) {
        this.ID = id;
    }

    @Override
    public Integer getID() {
        return this.ID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Library library = (Library) o;
        return ID == library.ID && Objects.equals(address, library.address) && Objects.equals(name, library.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, address, name);
    }

    @Override
    public String toString() {
        return getAddress();
//        return "Library: ID = " + getID() + ", address = " + this.address + ", phone number = " + this.phoneNumber +
//                ", number of books = " + books.size() + ", number of librarians = " + librarians.size();
    }
}