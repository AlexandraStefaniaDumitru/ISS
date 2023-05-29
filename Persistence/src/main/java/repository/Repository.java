package repository;

public interface Repository<ID, T> {

    void save(T entity);

    T delete(ID id);

    void modify(T entity);

    T findOne(ID id);

    Iterable<T> findAll();
}
