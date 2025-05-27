package appcontrol.manager;

import appcontrol.visual.services.Services;
import company.product.Product;
import company.storage.Cell;
import company.storage.Storage;
import company.storage.salepoint.SalePoint;
import company.storage.warehouse.Warehouse;
import company.user.worker.Worker;
import database.access.*;

import java.sql.SQLException;
import java.util.ArrayList;

public class StoragesManager {

    static SalePointsAccess salePointsAccess = new SalePointsAccess();
    static StoragesAccess storagesAccess = new StoragesAccess();
    static ProductsAccess productsAccess = new ProductsAccess();
    static WorkersAccess workersAccess = new WorkersAccess();
    static CellsAccess cellsAccess = new CellsAccess();

    //Изменить ответственное лицо пункта продаж
    public static void changeAdmin() throws SQLException {
        //Выбор пункта продаж, для которого нужно сменить администратора
        System.out.println("Choose sale point to change admin:\n");
        if (noSalePoints()) {
            System.out.println("No sale points");
            System.out.println("\nPress any key to go back");
            Services.getInput();
            return;
        }
        printSalePoints();
        int salePointId = Integer.parseInt(Services.getInput());
        SalePoint salePoint = salePointsAccess.getById(salePointId);

        //Выбор нового админа среди сотрудников, не являющихся админами
        String condition = "Status = 'Staff'";
        System.out.println("Choose new admin:\n");
        if (WorkersManager.noWorkers(condition)) {
            System.out.println("No workers");
            System.out.println("\nPress any key to go back");
            Services.getInput();
            return;
        }
        WorkersManager.printWorkers(condition);
        int adminId = Integer.parseInt(Services.getInput());

        //Меняем статус старого админа
        Worker oldAdmin = workersAccess.getById(salePoint.adminId);
        if (oldAdmin != null) {
            oldAdmin.setStatus("Staff");
            workersAccess.update(oldAdmin);
        }
        salePoint.setAdminId(adminId);

        Worker newAdmin = workersAccess.getById(adminId);
        newAdmin.setStatus("Admin");
        workersAccess.update(newAdmin);

        //Обновление базы данных
        salePointsAccess.update(salePoint);
    }

    //Открытие нового склада
    public static void openNewWarehouse() throws SQLException {
        Warehouse warehouse = new Warehouse();

        System.out.println("Enter address: ");
        warehouse.setStreet(Services.getInput());

        //Добавлниие нового склада в базу данных
        storagesAccess.add(warehouse);
    }

    //Открытие нового пункта продаж
    public static void openNewSalePoint() throws SQLException {
        SalePoint salePoint = new SalePoint();

        System.out.println("Enter address: ");
        salePoint.setStreet(Services.getInput());

        //Добавление нового пункта продаж в базу данных
        storagesAccess.add(salePoint);
        salePoint.setId(storagesAccess.getLastAddedId());
        salePointsAccess.add(salePoint);
    }

    //Закрытие склада
    public static void closeStorage() throws SQLException {
        //Выбор склада для закрытия
        System.out.println("Choose storage to close:\n");
        if (noStorages()) {
            System.out.println("No storages");
            System.out.println("\nPress any key to go back");
            Services.getInput();
            return;
        }
        printStorages();
        int storageId = Integer.parseInt(Services.getInput());

        Worker worker = workersAccess.getById(workersAccess.getId("Work_place_id = " + storageId));
        if (worker != null) {
            worker.setWorkPlaceId(0);
            worker.setStatus("Staff");
            workersAccess.update(worker);
        }

        //Обновление базы данных
        storagesAccess.delete(storageId);
        salePointsAccess.delete(storageId);
    }

    //Вывод информации о товарах склада
    public static void printWarehouseProductsInfo() throws SQLException {
        System.out.println("Choose warehouse:\n");
        if (noWarehouses()) {
            System.out.println("No warehouses");
            System.out.println("\nPress any key to go back");
            Services.getInput();
            return;
        }
        printWarehouses();
        int warehouseId = Integer.parseInt(Services.getInput());

        ArrayList<Cell> cells = cellsAccess.getAll("Storage_id = " + warehouseId);
        ArrayList<Product> products = new ArrayList<>();
        for (Cell cell : cells) {
            Product product = productsAccess.getById(cell.productId);
            products.add(product);
        }
        if (products.isEmpty()) {
            System.out.println("No products in warehouse");
        } else {
            for (Product product: products) {
                int quantity = cellsAccess.getQuantityOfProductInStorage(product.id, warehouseId);
                System.out.println(product + ", quantity: " + quantity);
            }
        }

        System.out.println("\nPress any key to go back");
        Services.getInput();
    }

    //Вывод информации о товарах пункта продаж
    public static void printSalePointProductsInfo() throws SQLException {
        //Выбор пункта продаж
        System.out.println("Choose sale point:\n");
        if (noSalePoints()) {
            System.out.println("No sale points");
            System.out.println("\nPress any key to go back");
            Services.getInput();
            return;
        }
        printSalePoints();
        int salePointId = Integer.parseInt(Services.getInput());

        ArrayList<Cell> cells = cellsAccess.getAll("Storage_id = " + salePointId);
        ArrayList<Product> products = new ArrayList<>();
        for (Cell cell : cells) {
            Product product = productsAccess.getById(cell.productId);
            products.add(product);
        }
        if (products.isEmpty()) {
            System.out.println("No products in sale point");
        } else {
            for (Product product: products) {
                int quantity = cellsAccess.getQuantityOfProductInStorage(product.id, salePointId);
                System.out.println(product + ", quantity: " + quantity);
            }
        }

        System.out.println("\nPress any key to go back");
        Services.getInput();
    }

    //Вывод всех хранилищ
    public static void printAllStorages() throws SQLException {
        if (noStorages()) {
            System.out.println("No storages");
        }
        printStorages();

        System.out.println("\nPress any key to go back");
        Services.getInput();
    }

    //Проверка на отсутствие хранилищ
    public static boolean noStorages() throws SQLException {
        ArrayList<Storage> storages = storagesAccess.getAll();
        boolean noStorages = storages.isEmpty();

        return noStorages;
    }

    //Проверка на отсутствие пунктов продаж
    public static boolean noSalePoints() throws SQLException {
        ArrayList<SalePoint> salePoints = salePointsAccess.getAll();
        boolean noSalePoints = salePoints.isEmpty();

        return noSalePoints;
    }

    //Проверка на отсутствие складов
    public static boolean noWarehouses() throws SQLException {
        ArrayList<Storage> warehouses = storagesAccess.getAll("Type = 'Warehouse'");
        boolean noWarehouses = warehouses.isEmpty();

        return noWarehouses;
    }

    //Вывод всех хранилищ с указанием типа
    public static void printStorages() throws SQLException {
        ArrayList<Storage> storages = storagesAccess.getAll();
        for (Storage storage : storages) {
            System.out.print(storage + ", type: " + storagesAccess.getType(storage) + "\n");
        }
    }

    //Вывод всех пунктов продаж
    public static void printSalePoints() throws SQLException {
        ArrayList<SalePoint> salePoints = salePointsAccess.getAll();
        for (SalePoint salePoint : salePoints) {
            System.out.print(salePoint);
        }

    }

    //Вывод всех складов
    public static void printWarehouses() throws SQLException {
        ArrayList<Storage> warehouses = storagesAccess.getAll("Type = 'Warehouse'");
        for (Storage warehouse : warehouses) {
            System.out.println(warehouse);
        }
    }
}
