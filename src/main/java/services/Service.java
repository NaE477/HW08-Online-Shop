package services;

import java.sql.Connection;

public abstract class Service<T> implements BaseService<T>{
    private Connection connection;

    public Service(Connection connection){
        this.connection = connection;
    }
}
