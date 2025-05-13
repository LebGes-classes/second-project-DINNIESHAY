package appControl;

import database.connection.DataBase;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        AppController controller = new AppController();
        controller.openApp();
    }
}