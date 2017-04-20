package ru.kpfu.itis.notebook.model;

import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.kpfu.itis.notebook.interfaces.AbstractDAO;
import ru.kpfu.itis.notebook.util.HibernateUtil;

/**
 * Created by Максим on 18.04.2017.
 */
public abstract class AbstractModel<T> implements AbstractDAO<T> {
    protected Session session;
    private Transaction transaction;

    @Override
    public void save(T obj) {
        session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        session.persist(obj);
        transaction.commit();
        session.close();
    }

    @Override
    public void update(T obj) {
        session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        session.update(obj);
        transaction.commit();
        session.close();
    }

    @Override
    public void delete(T obj) {
        session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        session.delete(obj);
        transaction.commit();
        session.close();
    }
}
