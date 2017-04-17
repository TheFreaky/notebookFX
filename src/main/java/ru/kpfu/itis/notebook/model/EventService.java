package ru.kpfu.itis.notebook.model;

import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.kpfu.itis.notebook.entity.Event;
import ru.kpfu.itis.notebook.interfaces.EventDAO;
import ru.kpfu.itis.notebook.util.HibernateUtil;

import java.util.List;

public class EventService implements EventDAO {
    private Session session;
    private Transaction transaction;

    public EventService() {
    }

    @Override
    public void save(Event event) {
        session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        session.persist(event);
        transaction.commit();
        session.close();
    }

    @Override
    public void update(Event event) {
        session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        session.update(event);
        transaction.commit();
        session.close();
    }

    @Override
    public void delete(Event event) {
        session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        session.delete(event);
        transaction.commit();
        session.close();
    }

    @Override
    public List<Event> getAll() {
        session = HibernateUtil.getSessionFactory().openSession();
        List<Event> events = session.createQuery(
//                "select e from EventDataSet e", EventDataSet.class)
                "from Event", Event.class)
                .list();
        session.close();
        return events;
    }

    @Override
    public List<Event> find(String name) {
        session = HibernateUtil.getSessionFactory().openSession();
        List<Event> events = session.createQuery(
//                "select e from EventDataSet e", EventDataSet.class)
                "from Event where name like :name", Event.class)
                .setParameter("name", '%' + name + '%')
                .list();
        session.close();
        return events;
    }
}