package services;

import lombok.Getter;

import java.sql.Connection;

@Getter
public abstract class Service<T> implements BaseService<T>{
    private final Connection connection;

    public Service(Connection connection){
        this.connection = connection;
    }
}
