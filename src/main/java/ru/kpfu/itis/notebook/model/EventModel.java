package ru.kpfu.itis.notebook.model;

import ru.kpfu.itis.notebook.entity.Event;
import ru.kpfu.itis.notebook.entity.User;
import ru.kpfu.itis.notebook.util.HibernateUtil;

import java.util.List;

public class EventModel extends AbstractModel<Event> {
    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public List<Event> getAll() {
        session = HibernateUtil.getSessionFactory().openSession();
        List<Event> events = session.createQuery(
//                "select e from EventDataSet e", EventDataSet.class)
                "from Event where user = :user", Event.class)
                .setParameter("user", user)
                .list();
        session.close();
        return events;
    }

    public List<Event> find(String name) {
        session = HibernateUtil.getSessionFactory().openSession();
        List<Event> events = session.createQuery(
//                "select e from EventDataSet e", EventDataSet.class)
                "from Event where user = :user and name like :name", Event.class)
                .setParameter("user", user)
                .setParameter("name", '%' + name + '%')
                .list();
        session.close();
        return events;
    }
}