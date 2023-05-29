package repository;

import domain.Librarian;

public interface LibrarianRepository extends Repository<Integer, Librarian> {

    Librarian findLibrarianByUsernameAndPassword(String username, String password);
}