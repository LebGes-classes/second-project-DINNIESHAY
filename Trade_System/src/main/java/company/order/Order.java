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
    public int salePointId;
    public String status;

    public Order() {}

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

    public void setSalePointId(int salePointId) {
        this.salePointId = salePointId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order " +
                "id: " + id +
                ", status: " + status +
                ", buyerId: " + buyerId +
                ", productId: " + productId +
                ", product quantity: " + quantity +
                ", total price: " + totalPrice +
                ", date: " + date +
                ", workerId: " + workerId +
                ", salePointId: " + salePointId +
                "\n";
    }
}
