package appcontrol.manager;

import appcontrol.visual.services.Services;
import company.producer.Producer;
import company.product.Product;
import company.storage.Cell;
import database.access.*;

import java.sql.SQLException;
import java.util.ArrayList;

public class ProductsManager {

    static ProductsAccess productsAccess = new ProductsAccess();
    static CellsAccess cellsAccess = new CellsAccess();
    static ProducersAccess producersAccess = new ProducersAccess();

    //Показ доступных товаров
    public static void printAvailableProducts() throws SQLException {
        String condition = "Status = 'Available'";
        if (noProducts(condition)) {
            System.out.println("No products available");
        }
        printProducts(condition);

        System.out.println("\nPress any key to go back");
        Services.getInput();
    }

    //Показ купленных товаров
    public static void printSoldProducts() throws SQLException {
        String condition = "Status = 'Sold'";
        if (noProducts(condition)) {
            System.out.println("No products available");
        }
        printProducts(condition);

        System.out.println("\nPress any key to go back");
        Services.getInput();
    }

    //Добавление товара
    public static void addProduct() throws SQLException {
        Product product = new Product();

        //Ввод данных о новом товаре и добавление его в базу данных
        System.out.println("Enter the product name: ");
        String name = Services.getInput();
        product.setName(name);

        System.out.println("Enter price: ");
        double price = Double.parseDouble(Services.getInput());
        product.setPrice(price);

        System.out.println("Enter sell price: ");
        double sellPrice = Double.parseDouble(Services.getInput());
        product.setSellPrice(sellPrice);

        System.out.println("Choose warehouse:\n");
        if (StoragesManager.noWarehouses()) {
            System.out.println("No warehouses");
            System.out.println("\nPress any key to go back");
            Services.getInput();
            return;
        }
        StoragesManager.printWarehouses();
        int warehouseId = Integer.parseInt(Services.getInput());

        Cell cell = new Cell(warehouseId);

        System.out.println("Enter quantity: ");
        int quantity = Integer.parseInt(Services.getInput());
        cell.setQuantity(quantity);

        Producer producer = new Producer();
        System.out.println("Enter producer name: ");
        String producerName = Services.getInput();
        producer.setName(producerName);
        producersAccess.add(producer);
        product.setProducerId(producersAccess.getId("Name = '" + producerName + "'"));

        product.setStatus("Available");

        productsAccess.add(product);
        cell.setProductId(productsAccess.getLastAddedId());
        cellsAccess.add(cell);
    }

    //Закупка товара в склад
    public static void purchaseProduct() throws SQLException {
        //Выбор товара, который нужно закупить
        System.out.println("Choose product you want to purchase:\n");
        if (noProducts()) {
            System.out.println("No products");
            System.out.println("\nPress any key to go back");
            Services.getInput();
            return;
        }
        printProducts();
        Product product = productsAccess.getById(Integer.parseInt(Services.getInput()));

        //Выбор склада, на который привезти товар
        System.out.println("Choose warehouse:\n");
        if (StoragesManager.noWarehouses()) {
            System.out.println("No warehouses");
            System.out.println("\nPress any key to go back");
            Services.getInput();
            return;
        }
        StoragesManager.printWarehouses();
        int warehouseId = Integer.parseInt(Services.getInput());

        System.out.println("Enter quantity: ");
        int quantity = Integer.parseInt(Services.getInput());

        //Если на складе уже есть ячейка с данным товаром, то добавляем закупленное в эту же ячейку
        Cell cell = cellsAccess.getById(cellsAccess.getId("Storage_id = " + warehouseId + " AND Product_id = " + product.id));
        if (cell != null) {
            cell.setQuantity(cell.productQuantity + quantity);
            cellsAccess.update(cell);
        } else {
            //Иначе - создаем новую ячейку
            cell = new Cell(warehouseId);
            cell.setProductId(product.id);
            cell.setQuantity(quantity);
            cellsAccess.add(cell);
        }

        product.setStatus("Available");

        //Обновляем базу данных
        productsAccess.update(product);
    }

    //Передвижение товара из склада в пункт продаж
    public static void moveToSalePoint() throws SQLException {
        //Выбор склада, из которого нужно перевести товар
        System.out.println("Choose warehouse:\n");
        if (StoragesManager.noWarehouses()) {
            System.out.println("No warehouses");
            System.out.println("\nPress any key to go back");
            Services.getInput();
            return;
        }
        StoragesManager.printWarehouses();
        int warehouseId = Integer.parseInt(Services.getInput());

        //Выбор товара для перемещения
        System.out.println("Choose product that you want to move to sale point:\n");
        ArrayList<Cell> cells = cellsAccess.getAll("Storage_id = " + warehouseId);
        ArrayList<Product> products = new ArrayList<>();
        for (Cell cell : cells) {
            Product product = productsAccess.getById(cell.productId);
            products.add(product);
        }
        if (products.isEmpty()) {
            System.out.println("No product available");
            System.out.println("\nPress any key to go back");
            Services.getInput();
            return;
        } else {
            for (Product product : products) {
                System.out.println(product);
            }
        }
        Product product = productsAccess.getById(Integer.parseInt(Services.getInput()));

        //Уменьшаем количество товара в его старой ячейке
        Cell oldCell = cellsAccess.getById(cellsAccess.getId("Product_id = " + product.id + " AND Storage_id = " + warehouseId));
        System.out.println("Available: " + oldCell.productQuantity);
        //Ввод количества, которое нужно перевести
        System.out.println("Enter quantity: ");
        int quantity = Integer.parseInt(Services.getInput());
        while (quantity > oldCell.productQuantity) {
            System.out.println("Wrong quantity! Try again!");
            quantity = Integer.parseInt(Services.getInput());
        }
        oldCell.setQuantity(oldCell.productQuantity - quantity);
        if (oldCell.productQuantity == 0) {
            cellsAccess.delete(oldCell.id);
        }
        //Обновление старой ячейки в базе данных
        cellsAccess.update(oldCell);

        //Выбир пункта продаж, в который нужно перевести товар
        System.out.println("Choose sale point:\n");
        if (StoragesManager.noSalePoints()) {
            System.out.println("No sale points");
            System.out.println("\nPress any key to go back");
            Services.getInput();
            return;
        }
        StoragesManager.printSalePoints();
        int salePointId = Integer.parseInt((Services.getInput()));

        //Если ячейка с этим товаром уже существовала, то добавляем товар в неё же
        Cell newCell = cellsAccess.getById(cellsAccess.getId("Storage_id = " + salePointId + " AND Product_id = " + product.id));
        if (newCell != null) {
            newCell.setQuantity(newCell.productQuantity + quantity);
            cellsAccess.update(newCell);
        } else {
            //Иначе - создаем новую ячейку
            newCell = new Cell(salePointId);
            newCell.setQuantity(quantity);
            newCell.setProductId(product.id);
            cellsAccess.add(newCell);
        }
    }

    //Передвижение товара из пункта продаж в склад
    public static void moveToWarehouse() throws SQLException {
        //Выбор пункта продаж, из которого нужно перевести товар
        System.out.println("Choose sale point:\n");
        if (StoragesManager.noSalePoints()) {
            System.out.println("No sale points");
            System.out.println("\nPress any key to go back");
            Services.getInput();
            return;
        }
        StoragesManager.printSalePoints();
        int salePointId = Integer.parseInt(Services.getInput());

        //Выбор товара для перемещения
        System.out.println("Choose product that you want to move to sale point:\n");
        ArrayList<Cell> cells = cellsAccess.getAll("Storage_id = " + salePointId);
        ArrayList<Product> products = new ArrayList<>();
        for (Cell cell : cells) {
            Product product = productsAccess.getById(cell.productId);
            products.add(product);
        }
        if (products.isEmpty()) {
            System.out.println("No product available");
            System.out.println("\nPress any key to go back");
            Services.getInput();
            return;
        } else {
            for (Product product : products) {
                System.out.println(product);
            }
        }
        Product product = productsAccess.getById(Integer.parseInt(Services.getInput()));

        //Уменьшаем количество товара в его старой ячейке
        Cell oldCell = cellsAccess.getById(cellsAccess.getId("Product_id = " + product.id + " AND Storage_id = " + salePointId));
        System.out.println("Available: " + oldCell.productQuantity);
        System.out.println("Enter quantity: ");
        int quantity = Integer.parseInt(Services.getInput());
        while (quantity > oldCell.productQuantity) {
            System.out.println("Wrong quantity! Try again!");
            quantity = Integer.parseInt(Services.getInput());
        }
        oldCell.setQuantity(oldCell.productQuantity - quantity);
        if (oldCell.productQuantity == 0) {
            cellsAccess.delete(oldCell.id);
        }
        cellsAccess.update(oldCell);

        //Выбор склада, в который нужно перевести товар
        System.out.println("Choose warehouse:\n");
        if (StoragesManager.noWarehouses()) {
            System.out.println("No warehouses");
            System.out.println("\nPress any key to go back");
            Services.getInput();
            return;
        }
        StoragesManager.printWarehouses();
        int warehouseId = Integer.parseInt((Services.getInput()));

        //Если ячейка с этим товаром уже существовала, то добавляем товар в неё же
        Cell newCell = cellsAccess.getById(cellsAccess.getId("Storage_id = " + warehouseId + " AND Product_id = " + product.id));
        if (newCell != null) {
            newCell.setQuantity(newCell.productQuantity + quantity);
            cellsAccess.update(newCell);
        } else {
            //Иначе - создаем новую ячейку
            newCell = new Cell(warehouseId);
            newCell.setQuantity(quantity);
            newCell.setProductId(product.id);
            cellsAccess.add(newCell);
        }
    }

    //Передвижение товара из одного пункта продаж в другой
    public static void moveToOtherSalePoint() throws SQLException {
        //Выбор пункта продаж, из которого нужно перевести товар
        System.out.println("Choose sale point:\n");
        if (StoragesManager.noSalePoints()) {
            System.out.println("No sale points");
            System.out.println("\nPress any key to go back");
            Services.getInput();
            return;
        }
        StoragesManager.printSalePoints();
        int oldSalePointId = Integer.parseInt(Services.getInput());

        //Выбор товара, который нужно перевести
        System.out.println("Choose product that you want to move to sale point:\n");
        ArrayList<Cell> cells = cellsAccess.getAll("Storage_id = " + oldSalePointId);
        ArrayList<Product> products = new ArrayList<>();
        for (Cell cell : cells) {
            Product product = productsAccess.getById(cell.productId);
            products.add(product);
        }
        if (products.isEmpty()) {
            System.out.println("No product available");
            System.out.println("\nPress any key to go back");
            Services.getInput();
            return;
        } else {
            for (Product product : products) {
                System.out.println(product);
            }
        }
        Product product = productsAccess.getById(Integer.parseInt(Services.getInput()));

        //Выбор второго пункта продаж, в который нужно перевести товар
        System.out.println("Choose sale point:\n");
        if (StoragesManager.noSalePoints()) {
            System.out.println("No sale points");
            System.out.println("\nPress any key to go back");
            Services.getInput();
            return;
        }
        StoragesManager.printSalePoints();
        int salePointId = Integer.parseInt((Services.getInput()));

        //Уменьшаем количество товара в его старой ячейке
        Cell oldCell = cellsAccess.getById(cellsAccess.getId("Product_id = " + product.id + " AND Storage_id = " + oldSalePointId));
        System.out.println("Available: " + oldCell.productQuantity);
        System.out.println("Enter quantity: ");
        int quantity = Integer.parseInt(Services.getInput());
        while (quantity > oldCell.productQuantity) {
            System.out.println("Wrong quantity! Try again!");
            quantity = Integer.parseInt(Services.getInput());
        }
        oldCell.setQuantity(oldCell.productQuantity - quantity);
        if (oldCell.productQuantity == 0) {
            cellsAccess.delete(oldCell.id);
        }
        cellsAccess.update(oldCell);

        //Если ячейка с этим товаром уже существовала, то добавляем товар в неё же
        Cell newCell = cellsAccess.getById(cellsAccess.getId("Storage_id = " + salePointId + " AND Product_id = " + product.id));
        if (newCell != null) {
            newCell.setQuantity(newCell.productQuantity + quantity);
            cellsAccess.update(newCell);
        } else {
            //Иначе - создаем новую ячейку
            newCell = new Cell(salePointId);
            newCell.setQuantity(quantity);
            newCell.setProductId(product.id);
            cellsAccess.add(newCell);
        }
    }

    //Проверка на отсутствие товаров
    public static boolean noProducts() throws SQLException {
        ArrayList<Product> products = productsAccess.getAll();
        boolean noProducts = products.isEmpty();

        return noProducts;
    }

    //Проверка на отсутствие товаров, удовлетворяющих некоторому условию
    public static boolean noProducts(String condition) throws SQLException {
        ArrayList<Product> products = productsAccess.getAll(condition);
        boolean noProducts = products.isEmpty();

        return noProducts;
    }

    //Вывод товаров, удовлетворяяющих некоторому условию
    public static void printProducts(String condition) throws SQLException {
        ArrayList<Product> products = productsAccess.getAll(condition);
        for (Product product : products) {
            int quantity = cellsAccess.getTotalQuantityOfProduct(product.id);
            System.out.println(product + ", quantity: " + quantity);
        }
    }

    //Вывод всех товаров
    public static void printProducts() throws SQLException {
        ArrayList<Product> products = productsAccess.getAll();
        for (Product product : products) {
            int quantity = cellsAccess.getTotalQuantityOfProduct(product.id);
            System.out.println(product + ", quantity: " + quantity);
        }
    }
}
