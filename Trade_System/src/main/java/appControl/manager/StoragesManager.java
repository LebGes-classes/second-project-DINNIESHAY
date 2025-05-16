package appControl.manager;

import appControl.visual.services.Services;
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

    public static void changeAdmin() throws SQLException {
        System.out.println("Choose sale point to change admin:\n");
        ArrayList<SalePoint> salePoints = salePointsAccess.getAll();
        if (salePoints.isEmpty()) {
            System.out.println("No sale points");
            System.out.println("\nPress any key to go back");
            Services.getInput();
            return;
        } else {
            for (SalePoint salePoint : salePoints) {
                System.out.print(salePoint);
            }
        }
        SalePoint salePoint = salePointsAccess.getById(Integer.parseInt(Services.getInput()));

        System.out.println("Choose new admin:\n");
        ArrayList<Worker> workers = workersAccess.getAll("Status = 'Staff'");
        if (workers.isEmpty()) {
            System.out.println("No workers");
            System.out.println("\nPress any key to go back");
            Services.getInput();
            return;
        } else {
            for (Worker worker : workers) {
                System.out.print(worker);
            }
        }
        int adminId = Integer.parseInt(Services.getInput());

        Worker oldAdmin = workersAccess.getById(salePoint.adminId);
        if (oldAdmin != null) {
            oldAdmin.setStatus("Staff");
            workersAccess.update(oldAdmin);
        }
        salePoint.setAdminId(adminId);

        Worker newAdmin = workersAccess.getById(adminId);
        newAdmin.setStatus("Admin");
        workersAccess.update(newAdmin);

        salePointsAccess.update(salePoint);
    }

    public static void openNewWarehouse() throws SQLException {
        Warehouse warehouse = new Warehouse();

        System.out.println("Enter address: ");
        warehouse.setStreet(Services.getInput());

        storagesAccess.add(warehouse);
    }

    public static void openNewSalePoint() throws SQLException {
        SalePoint salePoint = new SalePoint();

        System.out.println("Enter address: ");
        salePoint.setStreet(Services.getInput());

        storagesAccess.add(salePoint);
        salePoint.setId(storagesAccess.getLastAddedId());
        salePointsAccess.add(salePoint);
    }

    public static void closeStorage() throws SQLException {
        System.out.println("Choose storage to close:\n");
        ArrayList<Storage> storages = storagesAccess.getAll();
        if (storages.isEmpty()) {
            System.out.println("No storages");
            System.out.println("\nPress any key to go back");
            Services.getInput();
            return;
        } else {
            for (Storage storage: storages) {
                System.out.println(storage);
            }
        }
        int storageId = Integer.parseInt(Services.getInput());

        Worker worker = workersAccess.getById(workersAccess.getId("Work_place_id = " + storageId));
        if (worker != null) {
            worker.setWorkPlaceId(0);
            worker.setStatus("Staff");
            workersAccess.update(worker);
        }

        storagesAccess.delete(storageId);
        salePointsAccess.delete(storageId);
    }

    public static void printWarehouseProductsInfo() throws SQLException {
        System.out.println("Choose warehouse:\n");
        ArrayList<Storage> warehouses = storagesAccess.getAll("Type = 'Warehouse'");
        if (warehouses.isEmpty()) {
            System.out.println("No warehouses");
            System.out.println("\nPress any key to go back");
            Services.getInput();
            return;
        } else {
            for (Storage warehouse: warehouses) {
                System.out.println(warehouse);
            }
        }
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

    public static void printSalePointProductsInfo() throws SQLException {
        System.out.println("Choose sale point:\n");
        ArrayList<SalePoint> salePoints = salePointsAccess.getAll();
        if (salePoints.isEmpty()) {
            System.out.println("No sale points");
            System.out.println("\nPress any key to go back");
            Services.getInput();
            return;
        } else {
            for (SalePoint salePoint : salePoints) {
                System.out.print(salePoint);
            }
        }
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

    public static void printAllStorages() throws SQLException {
        ArrayList<Storage> storages = storagesAccess.getAll();
        if (storages.isEmpty()) {
            System.out.println("No storages");
        } else {
            for (Storage storage: storages) {
                System.out.print(storage + ", type: " + storagesAccess.getType(storage) + "\n");
            }
        }
        System.out.println("\nPress any key to go back");
        Services.getInput();
    }
}
