package repository;

import domain.Library;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import util.HibernateSessionFactory;

import java.util.ArrayList;
import java.util.List;

public class LibraryRepositoryDB implements LibraryRepository {

    private static final Logger LOGGER = LogManager.getLogger(LibraryRepositoryDB.class);
    private static final SessionFactory SESSION_FACTORY = HibernateSessionFactory.getInstance();

    public LibraryRepositoryDB() {
        LOGGER.info("Initializing LibraryRepositoryDB");
    }

    @Override
    public void save(Library entity) {

    }

    @Override
    public Library delete(Integer integer) {
        return null;
    }

    @Override
    public void modify(Library entity) {

    }

    @Override
    public Library findOne(Integer integer) {
        try (Session session = SESSION_FACTORY.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                return session.get(Library.class, integer);
            } catch (RuntimeException exception) {
                if (transaction != null) {
                    transaction.rollback();
                }
            }
        }
        return null;
    }

    @Override
    public Iterable<Library> findAll() {
        List<Library> libraries = new ArrayList<>();
        try (Session session = SESSION_FACTORY.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                libraries = session.createQuery("SELECT l FROM Library l", Library.class).list();
                transaction.commit();
            } catch (RuntimeException exception) {
                if (transaction != null) {
                    transaction.rollback();
                }
            }
        }
        return libraries;
    }
}