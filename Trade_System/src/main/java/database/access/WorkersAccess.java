package database.access;

import company.user.worker.Worker;
import database.connection.DataBase;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class WorkersAccess implements Access<Worker> {

    public void add(Worker worker) throws SQLException {
        String sql = "INSERT INTO Workers (First_name, Last_name, Phone_number, Work_place_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = DataBase.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, worker.firstName);
            pstmt.setString(2, worker.lastName);
            pstmt.setString(3, worker.phoneNumber);
            pstmt.setInt(4, worker.workPlaceId);
            pstmt.executeUpdate();
        }
    }

    public void update(Worker worker) throws SQLException {
        String sql = "UPDATE Workers SET First_name = ?, Last_name = ?, Phone_number = ?, Work_place_id = ? WHERE ID = ?";

        try (PreparedStatement pstmt = DataBase.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, worker.firstName);
            pstmt.setString(2, worker.lastName);
            pstmt.setString(3, worker.phoneNumber);
            pstmt.setInt(4, worker.workPlaceId);
            pstmt.setInt(5, worker.id);
            pstmt.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM Workers WHERE ID = " + id;
        try (Statement stmt = DataBase.getConnection().createStatement()) {
            stmt.executeUpdate(sql);
        }
    }

    public ArrayList<Worker> getAll() throws SQLException {
        ArrayList<Worker> workers = new ArrayList<>();
        String sql = "SELECT * FROM Workers";

        try (Statement stmt = DataBase.getConnection().createStatement();
             ResultSet set = stmt.executeQuery(sql)) {
            while (set.next()) {
                Worker worker = new Worker();
                worker.setId(set.getInt("ID"));
                worker.setFirstName(set.getString("First_name"));
                worker.setLastName(set.getString("Last_name"));
                worker.setPhoneNumber(set.getString("Phone_number"));
                worker.setWorkPlaceId(set.getInt("Work_place_id"));
                workers.add(worker);
            }
        }

        return workers;
    }

    public ArrayList<Worker> getAll(String condition) throws SQLException {
        ArrayList<Worker> workers = new ArrayList<>();
        String sql = "SELECT * FROM Workers WHERE " + condition;

        try (Statement stmt = DataBase.getConnection().createStatement();
             ResultSet set = stmt.executeQuery(sql)) {
            while (set.next()) {
                Worker worker = new Worker();
                worker.setId(set.getInt("ID"));
                worker.setFirstName(set.getString("First_name"));
                worker.setLastName(set.getString("Last_name"));
                worker.setPhoneNumber(set.getString("Phone_number"));
                worker.setWorkPlaceId(set.getInt("Work_place_id"));
                workers.add(worker);
            }
        }

        return workers;
    }

    public Worker getById(int id) throws SQLException {
        Worker worker = null;
        String sql = "SELECT * FROM Workers WHERE ID = " + id;

        try (Statement stmt = DataBase.getConnection().createStatement();
             ResultSet set = stmt.executeQuery(sql)) {
            if (set.next()) {
                worker = new Worker();
                worker.setId(set.getInt("ID"));
                worker.setFirstName(set.getString("First_name"));
                worker.setLastName(set.getString("Last_name"));
                worker.setPhoneNumber(set.getString("Phone_number"));
                worker.setWorkPlaceId(set.getInt("Work_place_id"));
            }
        }

        return worker;
    }

    public int getLastAddedId() throws SQLException {
        int lastAddedId = 0;
        String sql = "SELECT MAX(ID) FROM Workers";

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
        String sql = "SELECT MAX(ID) FROM Workers WHERE " + condition;

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
        String sql = "SELECT ID FROM Workers WHERE " + condition;

        try (Statement stmt = DataBase.getConnection().createStatement();
             ResultSet result = stmt.executeQuery(sql)) {
            if (result.next()) {
                id = result.getInt(1);
            }
        }

        return id;
    }
}
