package company.storage;

public class Cell {

    public int id;
    public int storageId;
    public int productQuantity;

    public Cell() {}

    public Cell(int storageId) {
        this.storageId = storageId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStorageId(int storageId) {
        this.storageId = storageId;
    }

    public void setQuantity(int quantity) {
        this.productQuantity = quantity;
    }

    @Override
    public String toString() {
        return "Cell " +
                "id: " + id +
                ", storageId: " + storageId +
                ", quantity: " + productQuantity +
                "\n";
    }
}
