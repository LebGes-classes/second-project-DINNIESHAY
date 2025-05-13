package appControl;

import appControl.visual.services.Services;
import appControl.visual.printer.Printer;
import company.order.Order;
import company.producer.Producer;
import company.product.Product;
import company.storage.Cell;
import company.storage.Storage;
import company.storage.salepoint.SalePoint;
import company.storage.warehouse.Warehouse;
import company.user.buyer.Buyer;
import company.user.worker.Worker;
import database.access.*;
import database.connection.DataBase;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class AppController {

    Printer printer = new Printer();
    ProductsAccess productsAccess = new ProductsAccess();
    StoragesAccess storagesAccess = new StoragesAccess();
    CellsAccess cellsAccess = new CellsAccess();
    ProducersAccess producersAccess = new ProducersAccess();
    SalePointsAccess salePointsAccess = new SalePointsAccess();
    OrdersAccess ordersAccess = new OrdersAccess();
    WorkersAccess workersAccess = new WorkersAccess();
    BuyersAccess buyersAccess = new BuyersAccess();

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
                    printAvailableProducts();
                    break;
                case "2":
                    printSoldProducts();
                    break;
                case "3":
                    addProduct();
                    break;
                case "4":
                    purchaseProduct();
                    break;
                case "5":
                    moveToSalePoint();
                    break;
                case "6":
                    moveToWarehouse();
                    break;
                case "7":
                    moveToOtherSalePoint();
                    break;
                case "B", "b":
                    managing = false;
                    break;
                default:
                    System.out.println("Wrong choice. Try again.\n");
            }
        }
    }

    private void printAvailableProducts() throws SQLException {
        ArrayList<Product> products = productsAccess.getAll("Status = 'Available'");
        if (products.isEmpty()) {
            System.out.println("No products available\n");
        } else {
            for (Product product : products) {
                System.out.println(product);
            }
        }
    }

    private void printSoldProducts() throws SQLException {
        ArrayList<Product> products = productsAccess.getAll("Status = 'Sold'");
        if (products.isEmpty()) {
            System.out.println("No products sold\n");
        } else {
            for (Product product : products) {
                System.out.println(product);
            }
        }
    }

    private void addProduct() throws SQLException {
        Product product = new Product();

        System.out.println("Enter the product name: ");
        String name = Services.getInput();
        product.setName(name);

        System.out.println("Enter price: ");
        double price = Double.parseDouble(Services.getInput());
        product.setPrice(price);

        System.out.println("Enter sell price: ");
        double sellPrice = Double.parseDouble(Services.getInput());
        product.setSellPrice(sellPrice);

        System.out.println("Choose warehouse:");
        printAllWarehouses();
        int warehouseId = Integer.parseInt(Services.getInput());
        product.setStorageId(warehouseId);

        Cell cell = new Cell(warehouseId);
        System.out.println("Enter quantity: ");
        int quantity = Integer.parseInt(Services.getInput());
        cell.setQuantity(quantity);
        cellsAccess.add(cell);
        product.setCellId(cellsAccess.getLastAddedId());

        Producer producer = new Producer();
        System.out.println("Enter producer name: ");
        String producerName = Services.getInput();
        producer.setName(producerName);
        producersAccess.add(producer);
        product.setProducerId(producersAccess.getId("Name = '" + producerName + "'"));

        product.setStatus("Available");

        productsAccess.add(product);
    }

    private void purchaseProduct() throws SQLException {
        System.out.println("Choose product you want to purchase:\n");
        printSoldProducts();
        Product product = productsAccess.getById(Integer.parseInt(Services.getInput()));

        System.out.println("Choose warehouse:");
        printAllWarehouses();
        int warehouseId = Integer.parseInt(Services.getInput());
        product.setStorageId(warehouseId);

        Cell cell = new Cell(warehouseId);
        System.out.println("Enter quantity: ");
        int quantity = Integer.parseInt(Services.getInput());
        cell.setQuantity(quantity);
        cellsAccess.add(cell);
        product.setCellId(cellsAccess.getLastAddedId());

        product.setStatus("Available");

        productsAccess.update(product);
    }

    private void moveToSalePoint() throws SQLException {
        System.out.println("Choose warehouse:\n");
        printAllWarehouses();
        int warehouseId = Integer.parseInt(Services.getInput());

        System.out.println("Choose product that you want to move to sale point:\n");
        ArrayList<Product> products = productsAccess.getAll("Status = 'Available' AND Storage_id = '" + warehouseId + "'");
        if (products.isEmpty()) {
            System.out.println("No product available");
        } else {
            for (Product product : products) {
                System.out.println(product);
            }
        }
        Product product = productsAccess.getById(Integer.parseInt(Services.getInput()));

        System.out.println("Choose sale point:\n");
        printAllSalePoints();
        int salePointId = Integer.parseInt((Services.getInput()));
        product.setStorageId(salePointId);

        Cell cell = new Cell();
        cell.setStorageId(salePointId);
        cellsAccess.add(cell);
        product.setCellId(cell.id);

        productsAccess.update(product);
    }

    private void moveToWarehouse() throws SQLException {
        System.out.println("Choose sale point:\n");
        printAllSalePoints();
        int salePointId = Integer.parseInt(Services.getInput());

        System.out.println("Choose product that you want to move to warehouse:\n");
        ArrayList<Product> products = productsAccess.getAll("Status = 'Available' AND Storage_id = '" + salePointId + "'");
        if (products.isEmpty()) {
            System.out.println("No product available");
        } else {
            for (Product product : products) {
                System.out.println(product);
            }
        }
        Product product = productsAccess.getById(Integer.parseInt(Services.getInput()));

        System.out.println("Choose warehouse:\n");
        printAllWarehouses();
        int warehouseId = Integer.parseInt((Services.getInput()));
        product.setStorageId(warehouseId);

        Cell cell = new Cell();
        cell.setStorageId(warehouseId);
        cellsAccess.add(cell);
        product.setCellId(cell.id);

        productsAccess.update(product);
    }

    private void moveToOtherSalePoint() throws SQLException {
        System.out.println("Choose sale point:\n");
        printAllSalePoints();
        int oldSalePointId = Integer.parseInt(Services.getInput());

        System.out.println("Choose product that you want to move to other sale point:\n");
        ArrayList<Product> products = productsAccess.getAll("Status = 'Available' AND Storage_id = '" + oldSalePointId + "'");
        if (products.isEmpty()) {
            System.out.println("No product available");
        } else {
            for (Product product : products) {
                System.out.println(product);
            }
        }
        Product product = productsAccess.getById(Integer.parseInt(Services.getInput()));

        System.out.println("Choose new sale point:\n");
        printAllSalePoints();
        int newSalePointId = Integer.parseInt((Services.getInput()));
        product.setStorageId(newSalePointId);

        Cell cell = new Cell();
        cell.setStorageId(newSalePointId);
        cellsAccess.add(cell);
        product.setCellId(cell.id);

        productsAccess.update(product);
    }

    private void manageOrders() throws SQLException {
        boolean managing = true;

        while (managing) {
            printer.printOrderManagement();
            String choice = Services.getInput();

            switch (choice) {
                case "1":
                    makeOrder();
                    break;
                case "2":
                    makeReturn();
                    break;
                case "3":
                    printAllOrders();
                    break;
                case "4":
                    printOrdersByBuyer();
                    break;
                case "5":
                    printOrdersByProduct();
                    break;
                case "B", "b":
                    managing = false;
                    break;
                default:
                    System.out.println("Wrong choice. Try again.\n");
            }
        }
    }

    private void makeOrder() throws SQLException {
        Order order = new Order();

        System.out.println("Choose your sale point:\n");
        printAllSalePoints();
        int salePointId = Integer.parseInt(Services.getInput());
        SalePoint salePoint = salePointsAccess.getById(salePointId);

        System.out.println("Choose product to sell:\n");
        ArrayList<Product> products = productsAccess.getAll("Status = 'Available' AND Storage_id = '" + salePointId + "'");
        if (products.isEmpty()) {
            System.out.println("No product available");
        } else {
            for (Product product : products) {
                System.out.println(product);
            }
        }
        int productId = Integer.parseInt(Services.getInput());
        order.setProductId(productId);
        Product product = productsAccess.getById(productId);
        Cell cell = cellsAccess.getById(product.cellId);

        System.out.println("Enter quantity: ");
        int quantity = Integer.parseInt(Services.getInput());
        while (quantity > cell.productQuantity) {
            System.out.println("Wrong quantity! Try again!");
            quantity = Integer.parseInt(Services.getInput());
        }
        if (cell.productQuantity == quantity) {
            product.setCellId(0);
            product.setStatus("Sold");
            cellsAccess.delete(cell.id);
        } else {
            cell.setQuantity(cell.productQuantity - quantity);
        }

        order.setQuantity(quantity);

        double totalPrice = product.sellPrice * quantity;
        order.setTotalPrice(totalPrice);

        Buyer buyer = new Buyer();
        System.out.println("Enter buyer's first name: ");
        String firstName = Services.getInput();
        buyer.setFirstName(firstName);
        System.out.println("Enter buyer's last name: ");
        String lastName = Services.getInput();
        buyer.setLastName(lastName);
        System.out.println("Enter buyer's phone number: ");
        String phoneNumber = Services.getInput();
        buyer.setPhoneNumber(phoneNumber);
        buyersAccess.add(buyer);
        buyer.setId(buyersAccess.getId("First_name = '" + firstName + "' AND Last_name = '" + lastName + "'"));
        order.setBuyerId(buyer.id);

        order.setDate(LocalDateTime.now());

        salePoint.increaseRevenue(product.sellPrice);

        productsAccess.update(product);
        salePointsAccess.update(salePoint);

        ordersAccess.add(order);
    }

    private void makeReturn() throws SQLException {
        System.out.println("Choose order you want to return:\n");
        printAllOrders();
        Order order = ordersAccess.getById(Integer.parseInt(Services.getInput()));
        Product product = productsAccess.getById(order.productId);
        product.setStatus("Available");

        SalePoint salePoint = salePointsAccess.getById(product.storageId);
        salePoint.reduceRevenue(product.sellPrice);

        Cell cell = new Cell(product.storageId);
        int quantity = order.quantity;
        cell.setQuantity(quantity);
        cellsAccess.add(cell);
        product.setCellId(cellsAccess.getLastAddedId());

        productsAccess.update(product);
        salePointsAccess.update(salePoint);

        ordersAccess.delete(order.id);
    }

    private void printAllOrders() throws SQLException {
        ArrayList<Order> orders = ordersAccess.getAll();
        if (orders.isEmpty()) {
            System.out.println("No orders");
        } else {
            for (Order order : orders) {
                System.out.println(order);
            }
        }
    }

    private void printOrdersByBuyer() throws SQLException {
        System.out.println("Choose buyer:\n");
        printAllBuyers();
        int buyerId = Integer.parseInt(Services.getInput());

        ArrayList<Order> orders = ordersAccess.getOrdersByBuyer(buyerId);
        if (orders.isEmpty()) {
            System.out.println("No orders");
        } else {
            for (Order order : orders) {
                System.out.println(order);
            }
        }
    }

    private void printOrdersByProduct() throws SQLException {
        System.out.println("Choose product:\n");
        printAllProducts();
        int productId = Integer.parseInt(Services.getInput());

        ArrayList<Order> orders = ordersAccess.getOrdersByProduct(productId);
        if (orders.isEmpty()) {
            System.out.println("No orders");
        } else {
            for (Order order : orders) {
                System.out.println(order);
            }
        }
    }

    private void printAllProducts() throws SQLException {
        ArrayList<Product> products = productsAccess.getAll();
        if (products.isEmpty()) {
            System.out.println("No products");
        } else {
            for (Product product: products) {
                System.out.println(product);
            }
        }
    }

    private void printAllBuyers() throws SQLException {
        ArrayList<Buyer> buyers = buyersAccess.getAll();
        if (buyers.isEmpty()) {
            System.out.println("No buyers");
        } else {
            for (Buyer buyer: buyers) {
                System.out.println(buyer);
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
                    changeAdmin();
                    break;
                case "2":
                    openNewWarehouse();
                    break;
                case "3":
                    openNewSalePoint();
                    break;
                case "4":
                    closeStorage();
                    break;
                case "5":
                    printAllSalePoints();
                    break;
                case "6":
                    printAllWarehouses();
                    break;
                case "7":
                    printWarehouseProductsInfo();
                    break;
                case "8":
                    printSalePointProductsInfo();
                    break;
                case "B", "b":
                    managing = false;
                    break;
                default:
                    System.out.println("Wrong choice. Try again.\n");
            }
        }
    }

    private void changeAdmin() throws SQLException {
        System.out.println("Choose sale point to change admin:\n");
        printAllSalePoints();
        SalePoint salePoint = salePointsAccess.getById(Integer.parseInt(Services.getInput()));

        System.out.println("Choose new admin:\n");
        printAllWorkers();
        salePoint.setAdminId(Integer.parseInt(Services.getInput()));

        salePointsAccess.update(salePoint);
    }

    private void openNewWarehouse() throws SQLException {
        Warehouse warehouse = new Warehouse();

        System.out.println("Enter address: ");
        warehouse.setStreet(Services.getInput());

        storagesAccess.add(warehouse);
    }

    private void openNewSalePoint() throws SQLException {
        SalePoint salePoint = new SalePoint();

        System.out.println("Enter address: ");
        salePoint.setStreet(Services.getInput());

        storagesAccess.add(salePoint);
        salePoint.setId(storagesAccess.getLastAddedId());
        salePointsAccess.add(salePoint);
    }

    private void closeStorage() throws SQLException {
        System.out.println("Choose storage to close:\n");
        printAllStorages();
        int storageId = Integer.parseInt(Services.getInput());

        storagesAccess.delete(storageId);
        salePointsAccess.delete(storageId);
    }

    private void printAllSalePoints() throws SQLException {
        ArrayList<SalePoint> salePoints = salePointsAccess.getAll();
        if (salePoints.isEmpty()) {
            System.out.println("No sale points");
        } else {
            for (SalePoint salePoint : salePoints) {
                System.out.println(salePoint);
            }
        }
    }

    private void printAllWarehouses() throws SQLException {
        ArrayList<Storage> warehouses = storagesAccess.getAll("Type = 'Warehouse'");
        if (warehouses.isEmpty()) {
            System.out.println("No warehouses");
        } else {
            for (Storage warehouse: warehouses) {
                System.out.println(warehouse);
            }
        }
    }

    private void printAllStorages() throws SQLException {
        ArrayList<Storage> storages = storagesAccess.getAll();
        if (storages.isEmpty()) {
            System.out.println("No storages");
        } else {
            for (Storage storage: storages) {
                System.out.println(storage);
            }
        }
    }

    private void printWarehouseProductsInfo() throws SQLException {
        System.out.println("Choose warehouse:\n");
        printAllWarehouses();
        int warehouseId = Integer.parseInt(Services.getInput());

        ArrayList<Product> products = productsAccess.getAll("Storage_id = " + warehouseId);
        if (products.isEmpty()) {
            System.out.println("No products in warehouse");
        } else {
            for (Product product: products) {
                System.out.println(product);
            }
        }
    }

    private void printSalePointProductsInfo() throws SQLException {
        System.out.println("Choose sale point:\n");
        printAllSalePoints();
        int salePointId = Integer.parseInt(Services.getInput());

        ArrayList<Product> products = productsAccess.getAll("Storage_id = " + salePointId);
        if (products.isEmpty()) {
            System.out.println("No products in sale point");
        } else {
            for (Product product: products) {
                System.out.println(product);
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
                    addWorker();
                    break;
                case "2":
                    fireWorker();
                    break;
                case "3":
                    changeWorkPlace();
                    break;
                case "4":
                    printAllWorkers();
                    break;
                case "B", "b":
                    managing = false;
                    break;
                default:
                    System.out.println("Wrong choice. Try again.");
            }
        }
    }

    private void addWorker() throws SQLException {
        Worker worker = new Worker();

        System.out.println("Enter worker's first name: ");
        worker.setFirstName(Services.getInput());

        System.out.println("Enter worker's last name: ");
        worker.setLastName(Services.getInput());

        System.out.println("Enter worker's phone number:" );
        worker.setPhoneNumber(Services.getInput());

        System.out.println("Choose worker's work place:\n");
        printAllSalePoints();
        worker.setWorkPlaceId(Integer.parseInt(Services.getInput()));

        workersAccess.add(worker);
    }

    private void fireWorker() throws SQLException {
        System.out.println("Choose worker to fire:\n");
        printAllWorkers();
        Worker workerToFire = workersAccess.getById(Integer.parseInt(Services.getInput()));
        workerToFire.setWorkPlaceId(0);

        workersAccess.update(workerToFire);
    }

    private void changeWorkPlace() throws SQLException {
        System.out.println("Choose worker to change his work place:\n");
        printAllWorkers();
        Worker worker = workersAccess.getById(Integer.parseInt(Services.getInput()));

        System.out.println("Choose his new work place:\n");
        printAllSalePoints();
        worker.setWorkPlaceId(Integer.parseInt(Services.getInput()));

        workersAccess.update(worker);
    }

    private void printAllWorkers() throws SQLException {
        ArrayList<Worker> workers = workersAccess.getAll("Work_place_id > 0");
        if (workers.isEmpty()) {
            System.out.println("No workers");
        } else {
            for (Worker worker : workers) {
                System.out.println(worker);
            }
        }
    }

}