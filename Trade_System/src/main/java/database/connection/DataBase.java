package database.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBase {

    static Connection connection = null;

    //Получение соединения с базой данных SQL
    public static Connection getConnection() {

        if (connection != null) {
            return connection;
        }

        String url = "jdbc:sqlite:example.db";

        try {
            connection = DriverManager.getConnection(url);
            //При успешном соединении создаются таблицы, если они ещё не существуют
            createTables();
        } catch (SQLException e) {
            //При неуспешном соединении выводится сообщение об ошибке
            System.out.println("Connection error: " + e.getMessage());
        }

        return connection;
    }

    //Создание таблиц в базе данных, если они еще не существуют
    public static void createTables() throws SQLException {
        createWorkersTable();
        createStoragesTable();
        createCellsTable();
        createSalePointsTable();
        createOrdersTable();
        createProductsTable();
        createBuyersTable();
        createProducersTable();
    }

    //Создание таблицы Sale_points
    private static void createSalePointsTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS Sale_points (" +
                "ID INTEGER," +
                "Admin_id INTEGER," +
                "Revenue REAL," +
                "FOREIGN KEY (ID) REFERENCES Storages(ID)," +
                "FOREIGN KEY (Admin_id) REFERENCES Workers(ID)" +
                ");";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        }
    }

    //Создание таблицы Storages
    private static void createStoragesTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS Storages (" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Type TEXT," +
                "Street TEXT" +
                ");";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        }
    }

    //Создание таблицы Cells
    private static void createCellsTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS Cells (" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Storage_id INTEGER," +
                "Product_id INTEGER," +
                "Product_quantity INTEGER," +
                "FOREIGN KEY (Storage_id) REFERENCES Storages(ID)," +
                "FOREIGN KEY (Product_id) REFERENCES Products(ID)" +
                ");";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        }
    }

    //Создание таблицы Orders
    private static void createOrdersTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS Orders (" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Status TEXT," +
                "Buyer_id INTEGER," +
                "Product_id INTEGER," +
                "Quantity INTEGER," +
                "Total_price REAL," +
                "Date TIMESTAMP," +
                "Worker_id INTEGER," +
                "Sale_point_id INTEGER," +
                "FOREIGN KEY (Buyer_id) REFERENCES Buyers(ID)," +
                "FOREIGN KEY (Product_id) REFERENCES Products(ID)," +
                "FOREIGN KEY (Worker_id) REFERENCES Workers(ID)," +
                "FOREIGN KEY (Sale_point_id) REFERENCES Sale_points(ID)" +
                ");";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        }
    }

    //Создание таблицы Products
    private static void createProductsTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS Products (" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Name TEXT," +
                "Price REAL," +
                "Sell_price REAL," +
                "Status TEXT," +
                "Producer_id INTEGER," +
                "FOREIGN KEY (Producer_id) REFERENCES Producers(ID)" +
                ");";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        }
    }

    //Создание таблицы Workers
    private static void createWorkersTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS Workers (" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "First_name TEXT," +
                "Last_name TEXT," +
                "Phone_number TEXT," +
                "Work_place_id INTEGER," +
                "Status TEXT," +
                "FOREIGN KEY (Work_place_id) REFERENCES Sale_points(ID)" +
                ");";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        }
    }

    //Создание таблицы Buyers
    private static void createBuyersTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS Buyers (" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "First_name TEXT," +
                "Last_name TEXT," +
                "Phone_number TEXT" +
                ");";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        }
    }

    //Создание таблицы Producers
    private static void createProducersTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS Producers (" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Name TEXT" +
                ");";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        }
    }
}