package repository;

import domain.Reader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import util.HibernateSessionFactory;

import java.util.ArrayList;
import java.util.List;

public class ReaderRepositoryDB implements ReaderRepository {

    private static final Logger LOGGER = LogManager.getLogger(ReaderRepositoryDB.class);
    private static final SessionFactory SESSION_FACTORY = HibernateSessionFactory.getInstance();

    public ReaderRepositoryDB() {
        LOGGER.info("Initializing ReaderRepositoryDB");
    }

    @Override
    public Reader findReaderByUsernameAndPassword(String username, String password) {
        Reader reader = null;
        try (Session session = SESSION_FACTORY.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                reader = (Reader) session.createQuery("from Reader r where r.username = ?1 and r.password = ?2")
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
        return reader;
    }
    @Override
    public Reader findReaderByUsername(String username) {
        Reader reader = null;
        try (Session session = SESSION_FACTORY.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                reader = (Reader) session.createQuery("from Reader r where r.username = ?1")
                        .setParameter(1, username)
                        .setMaxResults(1)
                        .uniqueResult();

            } catch (RuntimeException exception) {
                if (transaction != null) {
                    transaction.rollback();
                }
            }
        }
        return reader;
    }
    @Override
    public void save(Reader entity) {
        LOGGER.traceEntry("Saving {}", entity);
        try (Session session = SESSION_FACTORY.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                session.save(entity);
                transaction.commit();
            } catch (RuntimeException exception) {
                if (transaction != null) {
                    transaction.rollback();
                }
            }
        }
    }

    @Override
    public Reader delete(Integer integer) {
        Reader reader = null;
        try (Session session = SESSION_FACTORY.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                reader = (Reader) session.createQuery("SELECT r FROM Reader r WHERE r.ID = ?1")
                        .setParameter(1, integer)
                        .setMaxResults(1)
                        .uniqueResult();
                session.delete(reader);
                transaction.commit();
            } catch (RuntimeException exception) {
                if (transaction != null) {
                    transaction.rollback();
                }
            }
        }
        return reader;
    }

    @Override
    public void modify(Reader entity) {
        try (Session session = SESSION_FACTORY.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                session.update(entity);
                transaction.commit();
            } catch (RuntimeException exception) {
                if (transaction != null) {
                    transaction.rollback();
                }
            }
        }
    }

    @Override
    public Reader findOne(Integer integer) {
        LOGGER.traceEntry("{ID = {}", integer);
        Reader reader = null;
        try (Session session = SESSION_FACTORY.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                reader = session.get(Reader.class, integer);
                transaction.commit();
            } catch (RuntimeException exception) {
                if (transaction != null) {
                    transaction.rollback();
                }
            }
        }
        LOGGER.traceExit("Value = ", reader);
        return reader;
    }

    @Override
    public Iterable<Reader> findAll() {
        LOGGER.traceEntry();
        List<Reader> readers = new ArrayList<>();
        try (Session session = SESSION_FACTORY.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                readers = session.createQuery("SELECT r FROM Reader r", Reader.class)
                        .list();
                transaction.commit();
            } catch (RuntimeException exception) {
                if (transaction != null) {
                    transaction.rollback();
                }
            }
        }
        LOGGER.traceExit();
        return readers;
    }
}