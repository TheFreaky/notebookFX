package ru.kpfu.itis.notebook.model;

import ru.kpfu.itis.notebook.entity.User;
import ru.kpfu.itis.notebook.util.HibernateUtil;

import java.util.List;

public class UserModel extends AbstractModel<User> {
    @Override
    public List<User> getAll() {
        session = HibernateUtil.getSessionFactory().openSession();
        List<User> users = session.createQuery(
                "from User", User.class)
                .list();
        session.close();
        return users;
    }

    public User getUser(String login, String password) {
        session = HibernateUtil.getSessionFactory().openSession();
        User user = session.createQuery(
                "from User where login = :login and password = :password", User.class)
                .setParameter("login", login)
                .setParameter("password", password)
                .uniqueResult();
        session.close();
        return user;
    }
}
