package appControl.manager;

import appControl.visual.services.Services;
import company.order.Order;
import company.product.Product;
import company.storage.Cell;
import company.storage.salepoint.SalePoint;
import company.user.buyer.Buyer;
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

        System.out.println("Choose product to sell:\n");
        ArrayList<Product> products = productsAccess.getAll("Status = 'Available' AND Storage_id = '" + salePointId + "'");
        if (products.isEmpty()) {
            System.out.println("No product available");
            System.out.println("\nPress any key to go back");
            Services.getInput();
            return;
        } else {
            for (Product product : products) {
                System.out.println(product + ", quantity: " + cellsAccess.getById(product.getCellId()).productQuantity);
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

    public static void makeReturn() throws SQLException {
        System.out.println("Choose order you want to return:\n");
        ArrayList<Order> orders = ordersAccess.getAll();
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
                System.out.print(product);
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
