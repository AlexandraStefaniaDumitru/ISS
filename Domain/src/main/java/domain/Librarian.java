package domain;

import java.util.Objects;

public class Librarian extends User {

    private static final long serialVersionUID = 12315472343L;

    private Library library;

    public Library getLibrary() {
        return library;
    }

    public void setLibrary(Library library) {
        this.library = library;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Librarian librarian = (Librarian) o;
        return Objects.equals(library, librarian.library);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), library);
    }

    @Override
    public String toString() {
        return "Librarian = " + this.name + ", username = " + this.username + ", password = " + this.password +
                ", library = " + this.library;

    }
}