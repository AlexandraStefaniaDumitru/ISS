package domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Reader extends User {

    private static final long serialVersionUID = 9994342342L;

    private String CNP;
    private String address;
    private String phoneNumber;
    private Set<Book> books = new HashSet<>(0);

    public Reader() {

    }

    public Reader(String name, String username, String password, String CNP, String address, String phoneNumber) {
        super(name, username, password);
        this.CNP = CNP;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public String getCNP() {
        return CNP;
    }

    public void setCNP(String CNP) {
        this.CNP = CNP;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public void setName(String name) {
        super.setName(name);
    }

    @Override
    public String getUsername() {
        return super.getUsername();
    }

    @Override
    public void setUsername(String username) {
        super.setUsername(username);
    }

    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public void setPassword(String password) {
        super.setPassword(password);
    }

    @Override
    public void setID(Integer id) {
        super.setID(id);
    }

    @Override
    public Integer getID() {
        return super.getID();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Reader reader = (Reader) o;
        return Objects.equals(CNP, reader.CNP) && Objects.equals(address, reader.address) && Objects.equals(phoneNumber, reader.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), CNP, address, phoneNumber);
    }

    @Override
    public String toString() {
        return "Reader = " + this.name + ", username = " + this.username + ", password = " + this.password +
                ", CNP = " + this.CNP + ", address = " + this.address + ", phoneNumber = " + this.phoneNumber;
    }
}