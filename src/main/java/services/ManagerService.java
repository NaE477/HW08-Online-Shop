package services;

import repositories.ManagerRepository;
import users.Manager;

import java.sql.Connection;
import java.util.List;

public class ManagerService extends Service<Manager>{
    ManagerRepository mr;
    public ManagerService(Connection connection) {
        super(connection);
        mr = new ManagerRepository(super.getConnection());
    }

    public Integer signUp(Manager manager){
        return mr.insert(manager);
    }
    @Override
    public Manager find(Integer id) {
        return mr.read(id);
    }
    public Manager findByUsername(String username){
        return mr.read(username);
    }
    public Manager findByEmail(String email){
        return mr.readByEmail(email);
    }

    @Override
    public List<Manager> findAll() {
        return mr.readAll();
    }

    @Override
    public Integer update(Manager manager) {
        return mr.update(manager);
    }

    @Override
    public Integer delete(Integer id) {
        return mr.delete(id);
    }
}
