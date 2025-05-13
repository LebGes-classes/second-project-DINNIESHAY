package company.storage.warehouse;

import company.storage.Storage;

public class Warehouse extends Storage {

    public Warehouse() {}

    public Warehouse(int id, String street) {
        super(id, street);
    }

    @Override
    public String toString() {
        return "Warehouse " +
                "id: " + id +
                ", street: " + street +
                "\n";
    }
}
