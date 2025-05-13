package company.product;

public class Product {

    public int id;
    public String name;
    public double price;
    public double sellPrice;
    public String status;
    public int storageId;
    public int cellId;
    public int producerId;

    public Product() {}

    public Product(String name, double price, double sellPrice) {
        this.name = name;
        this.price = price;
        this.sellPrice = sellPrice;
    }

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

    public void setStorageId(int storageId) {
        this.storageId = storageId;
    }

    public void setCellId(int cellId) {
        this.cellId = cellId;
    }

    public void setProducerId(int producerId) {
        this.producerId = producerId;
    }

    public int getCellId() {
        return cellId;
    }

    @Override
    public String toString() {
        return "Product " +
                "id: " + id +
                ", name: " + name +
                ", sellPrice: " + sellPrice +
                ", storageId: " + storageId +
                ", cellId: " + cellId +
                ", producerId: " + producerId +
                "\n";
    }
}
