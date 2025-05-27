package database.access;

import company.storage.salepoint.SalePoint;
import database.connection.DataBase;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SalePointsAccess implements Access<SalePoint> {

    //Доступ к базе данных пунктов продаж
    private final StoragesAccess storagesAccess = new StoragesAccess();

    //Добавление хранилища в базу данных
    public void add(SalePoint salePoint) throws SQLException {
        String sql = "INSERT INTO Sale_points (ID, Admin_id, Revenue) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = DataBase.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, salePoint.id);
            pstmt.setInt(2, salePoint.adminId);
            pstmt.setDouble(3, salePoint.revenue);
            pstmt.executeUpdate();
        }
    }

    //Обновление данных о пункте продаж
    public void update(SalePoint salePoint) throws SQLException {
        String sql = "UPDATE Sale_points SET Admin_id = ?, Revenue = ? WHERE ID = ?";

        try (PreparedStatement pstmt = DataBase.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, salePoint.adminId);
            pstmt.setDouble(2, salePoint.revenue);
            pstmt.setInt(3, salePoint.id);
            pstmt.executeUpdate();
        }
    }

    //Удаление пункта продаж в базе данных
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM Sale_points WHERE ID = " + id;
        try (Statement stmt = DataBase.getConnection().createStatement()) {
            stmt.executeUpdate(sql);
        }
    }

    //Получение списка всех пунктов продаж из базы данных
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

    ////Получение списка всех пунктов продаж, удовлетворяющих условию, из базы данных
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

    //Получение пункта продаж по его id
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

    //Получение последнего добавленного в базу данных id
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

    //Получение последнего добавленного id пункта продаж, удовлетворяющего условию
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

    //Получение id пункта продаж, удовлетворяющего условию
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
