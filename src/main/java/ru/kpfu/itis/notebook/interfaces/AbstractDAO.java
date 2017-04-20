package ru.kpfu.itis.notebook.interfaces;

import java.util.List;

public interface AbstractDAO<T> {
    void save(T obj);
    void update(T obj);
    void delete(T obj);
    List<T> getAll();
//    List<T> find(String key);
}
