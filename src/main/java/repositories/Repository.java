package repositories;

import lombok.Getter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;

@Getter
public abstract class Repository<T> implements BaseRepository<T> {
    private final Connection connection;

    public Repository(Connection connection){
        this.connection = connection;
    }

    protected abstract T mapTo(ResultSet rs);

    protected abstract List<T> mapToList(ResultSet rs);
}
