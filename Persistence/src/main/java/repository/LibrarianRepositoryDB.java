package repository;

import domain.Librarian;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import util.HibernateSessionFactory;

import java.util.ArrayList;
import java.util.List;

public class LibrarianRepositoryDB implements LibrarianRepository {

    private static final Logger LOGGER = LogManager.getLogger(LibrarianRepositoryDB.class);
    private static final SessionFactory SESSION_FACTORY = HibernateSessionFactory.getInstance();

    public LibrarianRepositoryDB() {
        LOGGER.info("Initializing LibrarianRepositoryDB");
    }

    @Override
    public void save(Librarian entity) {

    }

    @Override
    public Librarian delete(Integer integer) {
        return null;
    }

    @Override
    public void modify(Librarian entity) {

    }

    @Override
    public Librarian findOne(Integer integer) {
        try (Session session = SESSION_FACTORY.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                return session.get(Librarian.class, integer);
            } catch (RuntimeException exception) {
                if (transaction != null) {
                    transaction.rollback();
                }
            }
        }
        return null;
    }

    @Override
    public Iterable<Librarian> findAll() {
        List<Librarian> librarians = new ArrayList<>();
        try (Session session = SESSION_FACTORY.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                librarians = session.createQuery("SELECT l FROM Librarian l", Librarian.class).list();

                transaction.commit();
            } catch (RuntimeException exception) {
                if (transaction != null) {
                    transaction.rollback();
                }
            }
        }
        return librarians;
    }

    @Override
    public Librarian findLibrarianByUsernameAndPassword(String username, String password) {
        try (Session session = SESSION_FACTORY.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                return (Librarian) session.createQuery("FROM Librarian l WHERE l.username = ?1 AND l.password = ?2")
                        .setParameter(1, username)
                        .setParameter(2, password)
                        .setMaxResults(1)
                        .uniqueResult();
            } catch (RuntimeException exception) {
                if (transaction != null) {
                    transaction.rollback();
                }
            }
        }
        return null;
    }
}