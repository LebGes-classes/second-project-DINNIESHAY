package appControl;

import appControl.manager.*;
import appControl.visual.services.Services;
import appControl.visual.printer.Printer;
import database.connection.DataBase;

import java.sql.SQLException;

public class AppController {

    Printer printer = new Printer();

    public void openApp() throws SQLException {
        DataBase.getConnection();
        try {
            DataBase.createTables();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        boolean isRunning = true;

        while(isRunning) {
            printer.printMenu();
            String choice = Services.getInput();

            switch (choice) {
                case "1":
                    manageProducts();
                    break;
                case "2":
                    manageOrders();
                    break;
                case "3":
                    manageStorages();
                    break;
                case "4":
                    manageWorkers();
                    break;
                case "5":
                    showAccounting();
                    break;
                case "E", "e":
                    isRunning = false;
                    break;
                default:
                    System.out.println("Wrong choice. Try again.\n");
            }
        }
        System.out.println("Exiting the program...\n");
    }

    private void manageProducts() throws SQLException {
        boolean managing = true;

        while (managing) {
            printer.printProductManagement();
            String choice = Services.getInput();

            switch (choice) {
                case "1":
                    ProductsManager.printAvailableProducts();
                    break;
                case "2":
                    ProductsManager.printSoldProducts();
                    break;
                case "3":
                    ProductsManager.addProduct();
                    break;
                case "4":
                    ProductsManager.purchaseProduct();
                    break;
                case "5":
                    ProductsManager.moveToSalePoint();
                    break;
                case "6":
                    ProductsManager.moveToWarehouse();
                    break;
                case "7":
                    ProductsManager.moveToOtherSalePoint();
                    break;
                case "B", "b":
                    managing = false;
                    break;
                default:
                    System.out.println("Wrong choice. Try again.\n");
            }
        }
    }

    private void manageOrders() throws SQLException {
        boolean managing = true;

        while (managing) {
            printer.printOrderManagement();
            String choice = Services.getInput();

            switch (choice) {
                case "1":
                    OrdersManager.makeOrder();
                    break;
                case "2":
                    OrdersManager.makeReturn();
                    break;
                case "3":
                    OrdersManager.printAllOrders();
                    break;
                case "4":
                    OrdersManager.printOrdersByBuyer();
                    break;
                case "5":
                    OrdersManager.printOrdersByProduct();
                    break;
                case "B", "b":
                    managing = false;
                    break;
                default:
                    System.out.println("Wrong choice. Try again.\n");
            }
        }
    }

    private void manageStorages() throws SQLException {
        boolean managing = true;

        while (managing) {
            printer.printStorageManagement();
            String choice = Services.getInput();

            switch (choice) {
                case "1":
                    StoragesManager.changeAdmin();
                    break;
                case "2":
                    StoragesManager.openNewWarehouse();
                    break;
                case "3":
                    StoragesManager.openNewSalePoint();
                    break;
                case "4":
                    StoragesManager.closeStorage();
                    break;
                case "5":
                    StoragesManager.printAllStorages();
                    break;
                case "6":
                    StoragesManager.printWarehouseProductsInfo();
                    break;
                case "7":
                    StoragesManager.printSalePointProductsInfo();
                    break;
                case "B", "b":
                    managing = false;
                    break;
                default:
                    System.out.println("Wrong choice. Try again.\n");
            }
        }
    }

    private void manageWorkers() throws SQLException {
        boolean managing = true;

        while (managing) {
            printer.printWorkerManagement();
            String choice = Services.getInput();

            switch (choice) {
                case "1":
                    WorkersManager.addWorker();
                    break;
                case "2":
                    WorkersManager.fireWorker();
                    break;
                case "3":
                    WorkersManager.changeWorkPlace();
                    break;
                case "4":
                    WorkersManager.printAllWorkers();
                    break;
                case "B", "b":
                    managing = false;
                    break;
                default:
                    System.out.println("Wrong choice. Try again.\n");
            }
        }
    }

    private  void showAccounting() throws SQLException {
        boolean managing = true;

        while (managing) {
            printer.printAccounting();
            String choice = Services.getInput();

            switch (choice) {
                case "1":
                    AccountingManager.printSalePointRevenue();
                    break;
                case "2":
                    AccountingManager.printCompanyRevenue();
                    break;
                case "B", "b":
                    managing = false;
                    break;
                default:
                    System.out.println("Wrong choice. Try again.\n");
            }
        }
    }
}