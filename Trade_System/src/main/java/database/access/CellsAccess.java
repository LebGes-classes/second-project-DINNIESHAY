package database.access;

import company.storage.Cell;
import database.connection.DataBase;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CellsAccess implements Access<Cell> {

    //Добавление ячейки в базу данных с помощью SQL запроса
    public void add(Cell cell) throws SQLException {
        String sql = "INSERT INTO Cells (Storage_id, Product_id, Product_quantity) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = DataBase.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, cell.storageId);
            pstmt.setInt(2, cell.productId);
            pstmt.setInt(3, cell.productQuantity);
            pstmt.executeUpdate();
        }
    }

    //Обновление ячейки в базе данных с помощью SQL запроса
    public void update(Cell cell) throws SQLException {
        String sql = "UPDATE Cells SET Storage_id = ?, Product_id = ?, Product_quantity = ? WHERE ID = ?";

        try (PreparedStatement pstmt = DataBase.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, cell.storageId);
            pstmt.setInt(2, cell.productId);
            pstmt.setInt(3, cell.productQuantity);
            pstmt.setInt(4, cell.id);
            pstmt.executeUpdate();
        }
    }

    //Удаление ячейки в базе данных с помощью SQL запроса
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM Cells WHERE ID = " + id;
        try (Statement stmt = DataBase.getConnection().createStatement()) {
            stmt.executeUpdate(sql);
        }
    }

    //Получение списка всех ячеек из базы данных
    public ArrayList<Cell> getAll() throws SQLException {
        ArrayList<Cell> cells = new ArrayList<>();
        String sql = "SELECT * FROM Cells";

        try (Statement stmt = DataBase.getConnection().createStatement();
             ResultSet set = stmt.executeQuery(sql)) {
            while (set.next()) {
                Cell cell = new Cell();
                cell.setId(set.getInt("ID"));
                cell.setStorageId(set.getInt("Storage_id"));
                cell.setProductId(set.getInt("Product_id"));
                cell.setQuantity(set.getInt("Product_quantity"));
                cells.add(cell);
            }
        }

        return cells;
    }

    //Получение списка всех ячеек, удовлетворяющих условию, из базы данных
    public ArrayList<Cell> getAll(String condition) throws SQLException {
        ArrayList<Cell> cells = new ArrayList<>();
        String sql = "SELECT * FROM Cells WHERE " + condition;

        try (Statement stmt = DataBase.getConnection().createStatement();
             ResultSet set = stmt.executeQuery(sql)) {
            while (set.next()) {
                Cell cell = new Cell();
                cell.setId(set.getInt("ID"));
                cell.setStorageId(set.getInt("Storage_id"));
                cell.setProductId(set.getInt("Product_id"));
                cell.setQuantity(set.getInt("Product_quantity"));
                cells.add(cell);
            }
        }

        return cells;
    }

    //Получение ячейки из базы данных по id
    public Cell getById(int id) throws SQLException {
        Cell cell = null;
        String sql = "SELECT * FROM Cells WHERE ID = " + id;

        try (Statement stmt = DataBase.getConnection().createStatement();
             ResultSet set = stmt.executeQuery(sql)) {
            if (set.next()) {
                cell = new Cell();
                cell.setId(set.getInt("ID"));
                cell.setStorageId(set.getInt("Storage_id"));
                cell.setProductId(set.getInt("Product_id"));
                cell.setQuantity(set.getInt("Product_quantity"));
            }
        }

        return cell;
    }

    //Получение последнего добавленного id ячейки в базе данных
    public int getLastAddedId() throws SQLException {
        int lastAddedId = 0;
        String sql = "SELECT MAX(ID) FROM Cells";

        try (Statement stmt = DataBase.getConnection().createStatement();
             ResultSet result = stmt.executeQuery(sql)) {
            if (result.next()) {
                lastAddedId = result.getInt(1);
            }
        }

        return lastAddedId;
    }

    //Получение последнего добавленного id ячейки, удовлетворяющей условию, в базе данных
    public int getLastAddedId(String condition) throws SQLException {
        int lastAddedId = 0;
        String sql = "SELECT MAX(ID) FROM Cells WHERE " + condition;

        try (Statement stmt = DataBase.getConnection().createStatement();
             ResultSet result = stmt.executeQuery(sql)) {
            if (result.next()) {
                lastAddedId = result.getInt(1);
            }
        }

        return lastAddedId;
    }

    //Получение id ячейки по условию
    public int getId(String condition) throws SQLException {
        int id = 0;
        String sql = "SELECT ID FROM Cells WHERE " + condition;

        try (Statement stmt = DataBase.getConnection().createStatement();
             ResultSet result = stmt.executeQuery(sql)) {
            if (result.next()) {
                id = result.getInt(1);
            }
        }

        return id;
    }

    //Получение количества товара в ячейке
    public int getQuantityOfProductInStorage(int productId, int storageId) throws SQLException {
        int quantity = 0;

        ArrayList<Cell> cells = getAll("Storage_id = " + storageId + " AND Product_id = " + productId);
        Cell cell = cells.getFirst();
        quantity = cell.productQuantity;

        return quantity;
    }

    //Получение общего количества товара во всех складах
    public int getTotalQuantityOfProduct(int productId) throws SQLException {
        int quantity = 0;

        ArrayList<Cell> cells = getAll("Product_id = " + productId);
        for (Cell cell : cells) {
            quantity += cell.productQuantity;
        }

        return quantity;
    }
}
