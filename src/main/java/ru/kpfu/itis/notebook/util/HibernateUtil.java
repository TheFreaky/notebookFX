package ru.kpfu.itis.notebook.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 * Created by Максим on 14.04.2017.
 */
public class HibernateUtil {
    private static final SessionFactory sessionFactory;

    static {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        sessionFactory = new MetadataSources(registry)
                .buildMetadata()
                .buildSessionFactory();
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void close() {
        sessionFactory.close();
    }
}
