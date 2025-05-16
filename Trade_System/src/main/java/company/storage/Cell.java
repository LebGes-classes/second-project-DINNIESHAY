package company.storage;

public class Cell {

    public int id;
    public int storageId;
    public int  productId;
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

    public void setProductId(int productId) {
        this.productId = productId;
    }

    @Override
    public String toString() {
        return "Cell " +
                "id: " + id +
                ", storageId: " + storageId +
                ", productId: " + productId +
                ", productQuantity: " + productQuantity +
                "\n";
    }
}
