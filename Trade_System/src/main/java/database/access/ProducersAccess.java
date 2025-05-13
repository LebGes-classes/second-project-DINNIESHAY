package database.access;

import company.producer.Producer;
import database.connection.DataBase;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ProducersAccess implements Access<Producer> {

    public void add(Producer producer) throws SQLException {
        if (getId("Name = '" + producer.name + "'") != 0) {
            return;
        }

        String sql = "INSERT INTO Producers (Name) VALUES (?)";
        try (PreparedStatement pstmt = DataBase.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, producer.name);
            pstmt.executeUpdate();
        }
    }

    public void update(Producer producer) throws SQLException {
        String sql = "UPDATE Producers SET Name = ? WHERE ID = ?";

        try (PreparedStatement pstmt = DataBase.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, producer.name);
            pstmt.setInt(2, producer.id);
            pstmt.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM Producers WHERE ID = " + id;
        try (Statement stmt = DataBase.getConnection().createStatement()) {
            stmt.executeUpdate(sql);
        }
    }

    public ArrayList<Producer> getAll() throws SQLException {
        ArrayList<Producer> producers = new ArrayList<>();
        String sql = "SELECT * FROM Producers";

        try (Statement stmt = DataBase.getConnection().createStatement();
             ResultSet set = stmt.executeQuery(sql)) {
            while (set.next()) {
                Producer producer = new Producer();
                producer.setId(set.getInt("ID"));
                producer.setName(set.getString("Name"));
                producers.add(producer);
            }
        }

        return producers;
    }

    public ArrayList<Producer> getAll(String condition) throws SQLException {
        ArrayList<Producer> producers = new ArrayList<>();
        String sql = "SELECT * FROM Producers WHERE " + condition;

        try (Statement stmt = DataBase.getConnection().createStatement();
             ResultSet set = stmt.executeQuery(sql)) {
            while (set.next()) {
                Producer producer = new Producer();
                producer.setId(set.getInt("ID"));
                producer.setName(set.getString("Name"));
                producers.add(producer);
            }
        }

        return producers;
    }

    public Producer getById(int id) throws SQLException {
        Producer producer = null;
        String sql = "SELECT * FROM Producers WHERE ID = " + id;

        try (Statement stmt = DataBase.getConnection().createStatement();
             ResultSet set = stmt.executeQuery(sql)) {
            if (set.next()) {
                producer = new Producer();
                producer.setId(set.getInt("ID"));
                producer.setName(set.getString("Name"));
            }
        }

        return producer;
    }

    public int getLastAddedId() throws SQLException {
        int lastAddedId = 0;
        String sql = "SELECT MAX(ID) FROM Producers";

        try (Statement stmt = DataBase.getConnection().createStatement();
             ResultSet result = stmt.executeQuery(sql)) {
            if (result.next()) {
                lastAddedId = result.getInt(1);
            }
        }

        return lastAddedId;
    }

    public int getLastAddedId(String condition) throws SQLException {
        int lastAddedId = 0;
        String sql = "SELECT MAX(ID) FROM Producers WHERE " + condition;

        try (Statement stmt = DataBase.getConnection().createStatement();
             ResultSet result = stmt.executeQuery(sql)) {
            if (result.next()) {
                lastAddedId = result.getInt(1);
            }
        }

        return lastAddedId;
    }

    public int getId(String condition) throws SQLException {
        int id = 0;
        String sql = "SELECT ID FROM Producers WHERE " + condition;

        try (Statement stmt = DataBase.getConnection().createStatement();
             ResultSet result = stmt.executeQuery(sql)) {
            if (result.next()) {
                id = result.getInt(1);
            }
        }

        return id;
    }
}
