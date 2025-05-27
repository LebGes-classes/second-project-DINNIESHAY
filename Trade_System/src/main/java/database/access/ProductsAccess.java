package database.access;

import company.product.Product;
import database.connection.DataBase;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ProductsAccess implements Access<Product> {

    //Добавление товара в базу данных
    public void add(Product product) throws SQLException {
        String sql = "INSERT INTO Products " +
                "(Name, Price, Sell_price, " +
                "Status, Producer_id) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = DataBase.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, product.name);
            pstmt.setDouble(2, product.price);
            pstmt.setDouble(3, product.sellPrice);
            pstmt.setString(4, product.status);
            pstmt.setInt(5, product.producerId);
            pstmt.executeUpdate();
        }
    }

    //Обновление данных о товаре в базе данных
    public void update(Product product) throws SQLException {
        String sql = "UPDATE Products SET Name = ?, Price = ?, Sell_price = ?, Status = ?, Producer_id = ? WHERE ID = ?";

        try (PreparedStatement pstmt = DataBase.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, product.name);
            pstmt.setDouble(2, product.price);
            pstmt.setDouble(3, product.sellPrice);
            pstmt.setString(4, product.status);
            pstmt.setInt(5, product.producerId);
            pstmt.setInt(6, product.id);
            pstmt.executeUpdate();
        }
    }

    //Удаление товара из базы данных
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM Products WHERE ID = " + id;
        try (Statement stmt = DataBase.getConnection().createStatement()) {
            stmt.executeUpdate(sql);
        }
    }

    //Получение списка товаров из базы данных
    public ArrayList<Product> getAll() throws SQLException {
        ArrayList<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM Products";

        try (Statement stmt = DataBase.getConnection().createStatement();
             ResultSet set = stmt.executeQuery(sql)) {
            while (set.next()) {
                Product product = new Product();
                product.setId(set.getInt("ID"));
                product.setName(set.getString("Name"));
                product.setPrice(set.getDouble("Price"));
                product.setSellPrice(set.getDouble("Sell_price"));
                product.setStatus(set.getString("Status"));
                product.setProducerId(set.getInt("Producer_id"));
                products.add(product);
            }
        }

        return products;
    }

    //Получение списка товаров из базы данных, удовлетворяющих условию
    public ArrayList<Product> getAll(String condition) throws SQLException {
        ArrayList<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM Products WHERE " + condition;

        try (Statement stmt = DataBase.getConnection().createStatement();
             ResultSet set = stmt.executeQuery(sql)) {
            while (set.next()) {
                Product product = new Product();
                product.setId(set.getInt("ID"));
                product.setName(set.getString("Name"));
                product.setPrice(set.getDouble("Price"));
                product.setSellPrice(set.getDouble("Sell_price"));
                product.setStatus(set.getString("Status"));
                product.setProducerId(set.getInt("Producer_id"));
                products.add(product);
            }
        }

        return products;
    }

    //Получение товара по его id
    public Product getById(int id) throws SQLException {
        Product product = null;
        String sql = "SELECT * FROM Products WHERE ID = " + id;

        try (Statement stmt = DataBase.getConnection().createStatement();
             ResultSet set = stmt.executeQuery(sql)) {
            if (set.next()) {
                product = new Product();
                product.setId(set.getInt("ID"));
                product.setName(set.getString("Name"));
                product.setPrice(set.getDouble("Price"));
                product.setSellPrice(set.getDouble("Sell_price"));
                product.setStatus(set.getString("Status"));
                product.setProducerId(set.getInt("Producer_id"));
            }
        }

        return product;
    }

    //Получение последнего добавленного id
    public int getLastAddedId() throws SQLException {
        int lastAddedId = 0;
        String sql = "SELECT MAX(ID) FROM Products";

        try (Statement stmt = DataBase.getConnection().createStatement();
             ResultSet result = stmt.executeQuery(sql)) {
            if (result.next()) {
                lastAddedId = result.getInt(1);
            }
        }

        return lastAddedId;
    }

    //Получение последнего добавленного id товара, удовлетворяющего условию
    public int getLastAddedId(String condition) throws SQLException {
        int lastAddedId = 0;
        String sql = "SELECT MAX(ID) FROM Products WHERE " + condition;

        try (Statement stmt = DataBase.getConnection().createStatement();
             ResultSet result = stmt.executeQuery(sql)) {
            if (result.next()) {
                lastAddedId = result.getInt(1);
            }
        }

        return lastAddedId;
    }

    //Получение id товара, удовлетворяющего условию
    public int getId(String condition) throws SQLException {
        int id = 0;
        String sql = "SELECT ID FROM Products WHERE " + condition;

        try (Statement stmt = DataBase.getConnection().createStatement();
             ResultSet result = stmt.executeQuery(sql)) {
            if (result.next()) {
                id = result.getInt(1);
            }
        }

        return id;
    }
}
