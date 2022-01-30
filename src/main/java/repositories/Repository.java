package repositories;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;

public abstract class Repository<T> implements BaseRepository<T> {
    private Connection connection;

    public Repository(Connection connection){
        this.connection = connection;
    }

    protected abstract T mapTo(ResultSet rs);

    protected abstract List<T> mapToList(ResultSet rs);
}
