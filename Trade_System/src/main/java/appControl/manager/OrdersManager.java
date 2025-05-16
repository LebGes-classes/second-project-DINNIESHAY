package appControl.manager;

import appControl.visual.services.Services;
import company.order.Order;
import company.product.Product;
import company.storage.Cell;
import company.storage.salepoint.SalePoint;
import company.user.buyer.Buyer;
import company.user.worker.Worker;
import database.access.*;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class OrdersManager {

    static SalePointsAccess salePointsAccess = new SalePointsAccess();
    static ProductsAccess productsAccess = new ProductsAccess();
    static CellsAccess cellsAccess = new CellsAccess();
    static BuyersAccess buyersAccess = new BuyersAccess();
    static OrdersAccess ordersAccess = new OrdersAccess();
    static WorkersAccess workersAccess = new WorkersAccess();

    public static void makeOrder() throws SQLException {
        Order order = new Order();

        System.out.println("Choose your sale point:\n");
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
        SalePoint salePoint = salePointsAccess.getById(salePointId);
        order.setSalePointId(salePointId);

        System.out.println("Choose product to sell:\n");
        ArrayList<Cell> cells = cellsAccess.getAll("Storage_id = " + salePointId);
        ArrayList<Product> products = new ArrayList<>();
        for (Cell cell : cells) {
            Product product = productsAccess.getById(cell.productId);
            if (product.status.equals("Available")) {
                products.add(product);
            }
        }
        if (products.isEmpty()) {
            System.out.println("No product available");
            System.out.println("\nPress any key to go back");
            Services.getInput();
            return;
        } else {
            for (Product product : products) {
                int quantity = cellsAccess.getQuantityOfProductInStorage(product.id, salePointId);
                System.out.println(product + ", quantity: " + quantity);
            }
        }
        Product product = productsAccess.getById(Integer.parseInt(Services.getInput()));
        order.setProductId(product.id);

        System.out.println("Choose employee:");
        ArrayList<Worker> workers = workersAccess.getAll("Status = 'Staff' OR Status = 'Admin'");
        if (workers.isEmpty()) {
            System.out.println("No workers");
            return;
        } else {
            for (Worker worker : workers) {
                System.out.print(worker);
            }
        }
        int workerId = Integer.parseInt(Services.getInput());
        order.setWorkerId(workerId);

        Cell cell = cellsAccess.getById(cellsAccess.getId("Product_id = " + product.id + " AND Storage_id = " + salePointId));
        System.out.println("Available: " + cell.productQuantity);
        System.out.println("Enter quantity: ");
        int quantity = Integer.parseInt(Services.getInput());
        while (quantity > cell.productQuantity) {
            System.out.println("Wrong quantity! Try again!");
            quantity = Integer.parseInt(Services.getInput());
        }
        cell.setQuantity(cell.productQuantity - quantity);
        if (cell.productQuantity == 0) {
            cellsAccess.delete(cell.id);
        }
        cellsAccess.update(cell);

        order.setQuantity(quantity);

        double totalPrice = product.sellPrice * quantity;
        order.setTotalPrice(totalPrice);
        salePoint.increaseRevenue(totalPrice);

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
        order.setStatus("Received");

        productsAccess.update(product);
        salePointsAccess.update(salePoint);

        ordersAccess.add(order);
    }

    public static void makeReturn() throws SQLException {
        System.out.println("Choose order you want to return:\n");
        ArrayList<Order> orders = ordersAccess.getAll("Status = 'Received'");
        if (orders.isEmpty()) {
            System.out.println("No orders");
            System.out.println("\nPress any key to go back");
            Services.getInput();
            return;

        } else {
            for (Order order : orders) {
                System.out.print(order);
            }
        }
        Order order = ordersAccess.getById(Integer.parseInt(Services.getInput()));

        Product product = productsAccess.getById(order.productId);
        product.setStatus("Available");

        SalePoint salePoint = salePointsAccess.getById(order.salePointId);
        salePoint.reduceRevenue(order.totalPrice);

        Cell newCell = cellsAccess.getById(cellsAccess.getId("Storage_id = " + order.salePointId + " AND Product_id = " + product.id));
        if (newCell != null) {
            newCell.setQuantity(newCell.productQuantity + order.quantity);
            cellsAccess.update(newCell);
        } else {
            newCell = new Cell(order.salePointId);
            newCell.setQuantity(order.quantity);
            newCell.setProductId(product.id);
            cellsAccess.add(newCell);
        }

        productsAccess.update(product);
        salePointsAccess.update(salePoint);

        order.setStatus("Returned");
        ordersAccess.update(order);
    }

    public static void printAllOrders() throws SQLException {
        ArrayList<Order> orders = ordersAccess.getAll();
        if (orders.isEmpty()) {
            System.out.println("No orders");
        } else {
            for (Order order : orders) {
                System.out.print(order);
            }
        }

        System.out.println("\nPress any key to go back");
        Services.getInput();
    }

    public static void printOrdersByBuyer() throws SQLException {
        System.out.println("Choose buyer:\n");
        ArrayList<Buyer> buyers = buyersAccess.getAll();
        if (buyers.isEmpty()) {
            System.out.println("No buyers");
            System.out.println("\nPress any key to go back");
            Services.getInput();
            return;
        } else {
            for (Buyer buyer: buyers) {
                System.out.print(buyer);
            }
        }
        int buyerId = Integer.parseInt(Services.getInput());

        ArrayList<Order> orders = ordersAccess.getOrdersByBuyer(buyerId);
        if (orders.isEmpty()) {
            System.out.println("No orders");
        } else {
            for (Order order : orders) {
                System.out.print(order);
            }
        }

        System.out.println("\nPress any key to go back");
        Services.getInput();
    }

    public static void printOrdersByProduct() throws SQLException {
        System.out.println("Choose product:\n");
        ArrayList<Product> products = productsAccess.getAll();
        if (products.isEmpty()) {
            System.out.println("No products");
            System.out.println("\nPress any key to go back");
            Services.getInput();
            return;
        } else {
            for (Product product: products) {
                System.out.println(product);
            }
        }
        int productId = Integer.parseInt(Services.getInput());

        ArrayList<Order> orders = ordersAccess.getOrdersByProduct(productId);
        if (orders.isEmpty()) {
            System.out.println("No orders");
        } else {
            for (Order order : orders) {
                System.out.print(order);
            }
        }

        System.out.println("\nPress any key to go back");
        Services.getInput();
    }
}
