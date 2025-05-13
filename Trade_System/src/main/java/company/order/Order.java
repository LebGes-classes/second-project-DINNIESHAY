package company.order;

import java.time.LocalDateTime;

public class Order {

    public int id;
    public int buyerId;
    public int productId;
    public int quantity;
    public double totalPrice;
    public LocalDateTime date;
    public int workerId;

    public Order() {}

    public Order(int buyerId, int productId, int quantity, double totalPrice, int workerId) {
        this.buyerId = buyerId;
        this.productId = productId;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.date = LocalDateTime.now();
        this.workerId = workerId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBuyerId(int buyerId) {
        this.buyerId = buyerId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setWorkerId(int workerId) {
        this.workerId = workerId;
    }

    @Override
    public String toString() {
        return "Order " +
                "id: " + id +
                ", buyerId: " + buyerId +
                ", productId: " + productId +
                ", quantity: " + quantity +
                ", totalPrice: " + totalPrice +
                ", date: " + date +
                "\n";
    }
}
