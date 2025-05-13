package database.access;

import company.storage.salepoint.SalePoint;
import database.connection.DataBase;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SalePointsAccess implements Access<SalePoint> {

    private StoragesAccess storagesAccess = new StoragesAccess();

    public void add(SalePoint salePoint) throws SQLException {
        String sql = "INSERT INTO Sale_points (ID, Admin_id, Revenue) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = DataBase.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, salePoint.id);
            pstmt.setInt(2, salePoint.adminId);
            pstmt.setDouble(3, salePoint.revenue);
            pstmt.executeUpdate();
        }
    }

    public void update(SalePoint salePoint) throws SQLException {
        String sql = "UPDATE Sale_points SET Admin_id = ?, Revenue = ? WHERE ID = ?";

        try (PreparedStatement pstmt = DataBase.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, salePoint.adminId);
            pstmt.setDouble(2, salePoint.revenue);
            pstmt.setInt(3, salePoint.id);
            pstmt.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM Sale_points WHERE ID = " + id;
        try (Statement stmt = DataBase.getConnection().createStatement()) {
            stmt.executeUpdate(sql);
        }
    }

    public ArrayList<SalePoint> getAll() throws SQLException {
        ArrayList<SalePoint> salePoints = new ArrayList<>();
        String sql = "SELECT * FROM Sale_points";

        try (Statement stmt = DataBase.getConnection().createStatement();
             ResultSet set = stmt.executeQuery(sql)) {
            while (set.next()) {
                SalePoint salePoint = new SalePoint();
                salePoint.setId(set.getInt("ID"));
                salePoint.setStreet(storagesAccess.getById(salePoint.id).street);
                salePoint.setAdminId(set.getInt("Admin_id"));
                salePoint.setRevenue(set.getDouble("Revenue"));
                salePoints.add(salePoint);
            }
        }

        return salePoints;
    }

    public ArrayList<SalePoint> getAll(String condition) throws SQLException {
        ArrayList<SalePoint> salePoints = new ArrayList<>();
        String sql = "SELECT * FROM Sale_points WHERE " + condition;

        try (Statement stmt = DataBase.getConnection().createStatement();
             ResultSet set = stmt.executeQuery(sql)) {
            while (set.next()) {
                SalePoint salePoint = new SalePoint();
                salePoint.setId(set.getInt("ID"));
                salePoint.setStreet(storagesAccess.getById(salePoint.id).street);
                salePoint.setAdminId(set.getInt("Admin_id"));
                salePoint.setRevenue(set.getDouble("Revenue"));
                salePoints.add(salePoint);
            }
        }

        return salePoints;
    }

    public SalePoint getById(int id) throws SQLException {
        SalePoint salePoint = null;
        String sql = "SELECT * FROM Sale_points WHERE ID = " + id;

        try (Statement stmt = DataBase.getConnection().createStatement();
             ResultSet set = stmt.executeQuery(sql)) {
            if (set.next()) {
                salePoint = new SalePoint();
                salePoint.setId(set.getInt("ID"));
                salePoint.setStreet(storagesAccess.getById(salePoint.id).street);
                salePoint.setAdminId(set.getInt("Admin_id"));
                salePoint.setRevenue(set.getDouble("Revenue"));
            }
        }

        return salePoint;
    }

    public int getLastAddedId() throws SQLException {
        int lastAddedId = 0;
        String sql = "SELECT MAX(ID) FROM Sale_points";

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
        String sql = "SELECT MAX(ID) FROM Sale_points WHERE " + condition;

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
        String sql = "SELECT ID FROM Sale_points WHERE " + condition;

        try (Statement stmt = DataBase.getConnection().createStatement();
             ResultSet result = stmt.executeQuery(sql)) {
            if (result.next()) {
                id = result.getInt(1);
            }
        }

        return id;
    }
}
