package repository;

import domain.Reader;

public interface ReaderRepository extends Repository<Integer, Reader> {

    Reader findReaderByUsernameAndPassword(String username, String password);

    Reader findReaderByUsername(String username);
}