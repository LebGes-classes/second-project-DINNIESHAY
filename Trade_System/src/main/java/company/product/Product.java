package company.product;

public class Product {

    public int id;
    public String name;
    public double price;
    public double sellPrice;
    public String status;
    public int producerId;

    public Product() {}

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setSellPrice(double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setProducerId(int producerId) {
        this.producerId = producerId;
    }

    @Override
    public String toString() {
        return "Product " +
                "id: " + id +
                ", name: " + name +
                ", sellPrice: " + sellPrice;
    }
}
