package database.access;

import java.sql.SQLException;
import java.util.ArrayList;

//Интерфейс классов, предоставляющих доступ к данным из базы данных SQL
public interface Access<T> {

    void add(T object) throws SQLException;
    void update(T object) throws SQLException;
    void delete(int id) throws SQLException;
    ArrayList<T> getAll() throws SQLException;
    ArrayList<T> getAll(String condition) throws SQLException;
    T getById(int id) throws SQLException;
    int getLastAddedId() throws SQLException;
    int getLastAddedId(String condition) throws SQLException;
    int getId(String condition) throws SQLException;
}
