package util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateSessionFactory {

    private static SessionFactory instance = null;

    private HibernateSessionFactory() {

    }

    public static SessionFactory getInstance() {
        if (instance == null) {
            StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
            try {
                instance = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            } catch (Exception exception) {
                exception.printStackTrace();
                System.err.println("Error at building the SessionFactory: " + exception);
                StandardServiceRegistryBuilder.destroy(registry);
            }
        }
        return instance;
    }

}
