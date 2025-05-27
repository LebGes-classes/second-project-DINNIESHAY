package database.access;

import company.storage.Storage;
import company.storage.salepoint.SalePoint;
import database.connection.DataBase;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class StoragesAccess implements Access<Storage> {

    //Добавление хранилища в базу данных
    public void add(Storage storage) throws SQLException {
        String sql = "INSERT INTO Storages (Type, Street) VALUES (?, ?)";
        try (PreparedStatement pstmt = DataBase.getConnection().prepareStatement(sql)) {
            if (storage instanceof SalePoint) {
                pstmt.setString(1, "Sale_point");
            } else {
                pstmt.setString(1, "Warehouse");
            }
            pstmt.setString(2, storage.street);
            pstmt.executeUpdate();
        }
    }

    //Обновление данных о хранилище в базе данных
    public void update(Storage storage) throws SQLException {
        String sql = "UPDATE Storages SET Type = ?, Street = ? WHERE ID = ?";

        try (PreparedStatement pstmt = DataBase.getConnection().prepareStatement(sql)) {
            if (storage instanceof SalePoint) {
                pstmt.setString(1, "Sale_point");
            } else {
                pstmt.setString(1, "Warehouse");
            }
            pstmt.setString(2, storage.street);
            pstmt.setInt(3, storage.id);
            pstmt.executeUpdate();
        }
    }

    //Удаление хранилища из базы данных
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM Storages WHERE ID = " + id;
        try (Statement stmt = DataBase.getConnection().createStatement()) {
            stmt.executeUpdate(sql);
        }
    }

    //Получение списка всех хранилищ из базы данных
    public ArrayList<Storage> getAll() throws SQLException {
        ArrayList<Storage> storages = new ArrayList<>();
        String sql = "SELECT * FROM Storages";

        try (Statement stmt = DataBase.getConnection().createStatement();
             ResultSet set = stmt.executeQuery(sql)) {
            while (set.next()) {
                Storage storage = new Storage();
                storage.setId(set.getInt("ID"));
                storage.setStreet(set.getString("Street"));
                storages.add(storage);
            }
        }

        return storages;
    }

    //Получение списка всех хранилищ, удовлетворяющих условию
    public ArrayList<Storage> getAll(String condition) throws SQLException {
        ArrayList<Storage> storages = new ArrayList<>();
        String sql = "SELECT * FROM Storages WHERE " + condition;

        try (Statement stmt = DataBase.getConnection().createStatement();
             ResultSet set = stmt.executeQuery(sql)) {
            while (set.next()) {
                Storage storage = new Storage();
                storage.setId(set.getInt("ID"));
                storage.setStreet(set.getString("Street"));
                storages.add(storage);
            }
        }

        return storages;
    }

    //Получение хранилища по его id
    public Storage getById(int id) throws SQLException {
        Storage storage = null;
        String sql = "SELECT * FROM Storages WHERE ID = " + id;

        try (Statement stmt = DataBase.getConnection().createStatement();
             ResultSet set = stmt.executeQuery(sql)) {
            if (set.next()) {
                storage = new Storage();
                storage.setId(set.getInt("ID"));
                storage.setStreet(set.getString("Street"));
            }
        }

        return storage;
    }

    //Получение последнего добавленного id хранилища
    public int getLastAddedId() throws SQLException {
        int lastAddedId = 0;
        String sql = "SELECT MAX(ID) FROM Storages";

        try (Statement stmt = DataBase.getConnection().createStatement();
             ResultSet result = stmt.executeQuery(sql)) {
            if (result.next()) {
                lastAddedId = result.getInt(1);
            }
        }

        return lastAddedId;
    }

    //Получение последнего добавленного id
    public int getLastAddedId(String condition) throws SQLException {
        int lastAddedId = 0;
        String sql = "SELECT MAX(ID) FROM Storages WHERE " + condition;

        try (Statement stmt = DataBase.getConnection().createStatement();
             ResultSet result = stmt.executeQuery(sql)) {
            if (result.next()) {
                lastAddedId = result.getInt(1);
            }
        }

        return lastAddedId;
    }

    //Получение id хранилища, удовлетворяющего условию
    public int getId(String condition) throws SQLException {
        int id = 0;
        String sql = "SELECT ID FROM Storages WHERE " + condition;

        try (Statement stmt = DataBase.getConnection().createStatement();
             ResultSet result = stmt.executeQuery(sql)) {
            if (result.next()) {
                id = result.getInt(1);
            }
        }

        return id;
    }

    //Получение типа хранилища
    public String getType(Storage storage) throws SQLException {
        String type = null;
        String sql = "SELECT Type FROM Storages WHERE ID = " + storage.id;

        try (Statement stmt = DataBase.getConnection().createStatement();
             ResultSet set = stmt.executeQuery(sql)) {
            if (set.next()) {
                type = set.getString("Type");
            }
        }

        return type;
    }
}
