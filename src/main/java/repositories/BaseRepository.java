package repositories;

import java.util.List;

public interface BaseRepository<T> {
    Integer insert(T t);
    T read(Integer id);
    List<T> readAll();
    Integer update(T t);
    Integer delete(T t);
    Integer delete(Integer id);
}
