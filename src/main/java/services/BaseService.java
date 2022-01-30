package services;

import java.util.List;

public interface BaseService <T>{
    T find(Integer id);
    List<T> findAll();
    Integer update(T t);
    Integer delete(Integer id);
}
