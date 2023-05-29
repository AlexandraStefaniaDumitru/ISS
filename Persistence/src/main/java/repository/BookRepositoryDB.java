package repository;

import domain.Book;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import util.HibernateSessionFactory;

import java.util.ArrayList;
import java.util.List;

public class BookRepositoryDB implements BookRepository {

    private static final Logger LOGGER = LogManager.getLogger(BookRepositoryDB.class);
    private static final SessionFactory SESSION_FACTORY = HibernateSessionFactory.getInstance();

    public BookRepositoryDB() {
        LOGGER.info("Initializing BookRepositoryDB");
    }

    @Override
    public void save(Book entity) {
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
    public Book delete(Integer integer) {
        Book book = null;
        try (Session session = SESSION_FACTORY.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                String queryString = "SELECT b FROM Book b WHERE b.ISBN = ?1";
                book = (Book) session.createQuery(queryString)
                        .setParameter(1, integer)
                        .setMaxResults(1)
                        .uniqueResult();
                session.delete(book);
                transaction.commit();
            } catch (RuntimeException exception) {
                if (transaction != null) {
                    transaction.rollback();
                }
            }
        }
        return book;
    }

    @Override
    public void modify(Book entity) {
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
    public Book findOne(Integer integer) {
        Book book = null;
        try (Session session = SESSION_FACTORY.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                book = session.get(Book.class, integer);
            } catch (RuntimeException exception) {
                if (transaction != null) {
                    transaction.rollback();
                }
            }
        }
        return book;
    }

    @Override
    public Iterable<Book> findAll() {
        List<Book> books = new ArrayList<>();
        try (Session session = SESSION_FACTORY.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                books = session.createQuery("SELECT b FROM Book b", Book.class).list();
                transaction.commit();
            } catch (RuntimeException exception) {
                if (transaction != null) {
                    transaction.rollback();
                }
            }
        }
        return books;
    }

    @Override
    public Iterable<Book> findAllByIsAvailable(boolean isAvailable) {
        List<Book> booksFilteredByIsAvailable = new ArrayList<>();
        try (Session session = SESSION_FACTORY.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                booksFilteredByIsAvailable = session.createQuery("FROM Book b WHERE b.isAvailable = ?1", Book.class)
                        .setParameter(1, isAvailable)
                        .list();
                transaction.commit();
            } catch (RuntimeException exception) {
                if (transaction != null) {
                    transaction.rollback();
                }
            }
        }
        return booksFilteredByIsAvailable;
    }

}