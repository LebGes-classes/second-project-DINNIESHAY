package database.access;

import company.order.Order;
import database.connection.DataBase;

import java.sql.*;
import java.util.ArrayList;

public class OrdersAccess implements Access<Order> {

    //Добавление заказа в базу данных с помощью SQL запроса
    public void add(Order order) throws SQLException {
        String sql = "INSERT INTO Orders (Status, Buyer_id, Product_id, Quantity, Total_price, Date, Worker_id, Sale_point_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = DataBase.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, order.status);
            pstmt.setInt(2, order.buyerId);
            pstmt.setInt(3, order.productId);
            pstmt.setInt(4, order.quantity);
            pstmt.setDouble(5, order.totalPrice);
            pstmt.setString(6, order.date);
            pstmt.setInt(7, order.workerId);
            pstmt.setInt(8, order.salePointId);
            pstmt.executeUpdate();
        }
    }

    //Обновление данных о заказе с помощью SQL запроса
    public void update(Order order) throws SQLException {
        String sql = "UPDATE Orders SET Status = ?, Buyer_id = ?, Product_id = ?, Quantity = ?, Total_price = ?, Date = ?, Worker_id = ?, Sale_point_id = ? WHERE ID = ?";

        try (PreparedStatement pstmt = DataBase.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, order.status);
            pstmt.setInt(2, order.buyerId);
            pstmt.setInt(3, order.productId);
            pstmt.setInt(4, order.quantity);
            pstmt.setDouble(5, order.totalPrice);
            pstmt.setString(6, order.date);
            pstmt.setInt(7, order.workerId);
            pstmt.setInt(8, order.salePointId);
            pstmt.setInt(9, order.id);
            pstmt.executeUpdate();
        }
    }

    //Удаление заказа
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM Orders WHERE ID = " + id;
        try (Statement stmt = DataBase.getConnection().createStatement()) {
            stmt.executeUpdate(sql);
        }
    }

    //Получение списка всех заказов из базы данных
    public ArrayList<Order> getAll() throws SQLException {
        ArrayList<Order> orders = new ArrayList<>();

        String sql = "SELECT * FROM Orders";

        try (Statement stmt = DataBase.getConnection().createStatement();
             ResultSet set = stmt.executeQuery(sql)) {
            while (set.next()) {
                Order order = new Order();
                order.setId(set.getInt("ID"));
                order.setStatus(set.getString("Status"));
                order.setBuyerId(set.getInt("Buyer_id"));
                order.setProductId(set.getInt("Product_id"));
                order.setQuantity(set.getInt("Quantity"));
                order.setTotalPrice(set.getDouble("Total_price"));
                order.setDate(set.getString("Date"));
                order.setWorkerId(set.getInt("Worker_id"));
                order.setSalePointId(set.getInt("Sale_point_id"));
                orders.add(order);
            }
        }

        return orders;
    }

    //Получение списка всех заказов, удовлетворяющих условию, из базы данных
    public ArrayList<Order> getAll(String condition) throws SQLException {
        ArrayList<Order> orders = new ArrayList<>();

        String sql = "SELECT * FROM Orders WHERE " + condition;

        try (Statement stmt = DataBase.getConnection().createStatement();
             ResultSet set = stmt.executeQuery(sql)) {
            while (set.next()) {
                Order order = new Order();
                order.setId(set.getInt("ID"));
                order.setStatus(set.getString("Status"));
                order.setBuyerId(set.getInt("Buyer_id"));
                order.setProductId(set.getInt("Product_id"));
                order.setQuantity(set.getInt("Quantity"));
                order.setTotalPrice(set.getDouble("Total_price"));
                order.setDate(set.getString("Date"));
                order.setWorkerId(set.getInt("Worker_id"));
                order.setSalePointId(set.getInt("Sale_point_id"));
                orders.add(order);
            }
        }

        return orders;
    }

    //Получение заказа по его id
    public Order getById(int id) throws SQLException {
        Order order = null;

        String sql = "SELECT * FROM Orders WHERE ID = " + id;

        try (Statement stmt = DataBase.getConnection().createStatement();
             ResultSet set = stmt.executeQuery(sql)) {
            if (set.next()) {
                order = new Order();
                order.setId(set.getInt("ID"));
                order.setStatus(set.getString("Status"));
                order.setBuyerId(set.getInt("Buyer_id"));
                order.setProductId(set.getInt("Product_id"));
                order.setQuantity(set.getInt("Quantity"));
                order.setTotalPrice(set.getDouble("Total_price"));
                order.setDate(set.getString("Date"));
                order.setWorkerId(set.getInt("Worker_id"));
                order.setSalePointId(set.getInt("Sale_point_id"));
            }
        }

        return order;
    }

    //Получение списка заказов одного покупателя
    public ArrayList<Order> getOrdersByBuyer(int buyerId) throws SQLException {
        ArrayList<Order> orders = new ArrayList<>();

        String sql = "SELECT * FROM Orders WHERE Buyer_id = " + buyerId;

        try (Statement stmt = DataBase.getConnection().createStatement();
             ResultSet set = stmt.executeQuery(sql)) {
            while (set.next()) {
                Order order = new Order();
                order.setId(set.getInt("ID"));
                order.setStatus(set.getString("Status"));
                order.setBuyerId(set.getInt("Buyer_id"));
                order.setProductId(set.getInt("Product_id"));
                order.setQuantity(set.getInt("Quantity"));
                order.setTotalPrice(set.getDouble("Total_price"));
                order.setDate(set.getString("Date"));
                order.setWorkerId(set.getInt("Worker_id"));
                order.setSalePointId(set.getInt("Sale_point_id"));
                orders.add(order);
            }
        }

        return orders;
    }

    //Получение списка заказов одного продукта
    public ArrayList<Order> getOrdersByProduct(int productId) throws SQLException {
        ArrayList<Order> orders = new ArrayList<>();

        String sql = "SELECT * FROM Orders WHERE Product_id = " + productId;

        try (Statement stmt = DataBase.getConnection().createStatement();
             ResultSet set = stmt.executeQuery(sql)) {
            while (set.next()) {
                Order order = new Order();
                order.setId(set.getInt("ID"));
                order.setStatus(set.getString("Status"));
                order.setBuyerId(set.getInt("Buyer_id"));
                order.setProductId(set.getInt("Product_id"));
                order.setQuantity(set.getInt("Quantity"));
                order.setTotalPrice(set.getDouble("Total_price"));
                order.setDate(set.getString("Date"));
                order.setWorkerId(set.getInt("Worker_id"));
                order.setSalePointId(set.getInt("Sale_point_id"));
                orders.add(order);
            }
        }

        return orders;
    }

    //Получение последнего добавленного в базу данных id
    public int getLastAddedId() throws SQLException {
        int lastAddedId = 0;
        String sql = "SELECT MAX(ID) FROM Orders";

        try (Statement stmt = DataBase.getConnection().createStatement();
             ResultSet result = stmt.executeQuery(sql)) {
            if (result.next()) {
                lastAddedId = result.getInt(1);
            }
        }

        return lastAddedId;
    }

    //Получение последнего добавленного в базу данных id заказа, удовлетворяющего условию
    public int getLastAddedId(String condition) throws SQLException {
        int lastAddedId = 0;
        String sql = "SELECT MAX(ID) FROM Orders WHERE " + condition;

        try (Statement stmt = DataBase.getConnection().createStatement();
             ResultSet result = stmt.executeQuery(sql)) {
            if (result.next()) {
                lastAddedId = result.getInt(1);
            }
        }

        return lastAddedId;
    }

    //Получение id заказа, удовлетворяющего условию
    public int getId(String condition) throws SQLException {
        int id = 0;
        String sql = "SELECT ID FROM Orders WHERE " + condition;

        try (Statement stmt = DataBase.getConnection().createStatement();
             ResultSet result = stmt.executeQuery(sql)) {
            if (result.next()) {
                id = result.getInt(1);
            }
        }

        return id;
    }
}
