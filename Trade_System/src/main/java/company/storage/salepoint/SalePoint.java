package company.storage.salepoint;

import company.storage.Storage;

public class SalePoint extends Storage {

    public int adminId;
    public double revenue;

    public SalePoint() {}

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }

    public void increaseRevenue(double money) {
        this.revenue = revenue + money;
    }

    public void reduceRevenue(double money) {
        this.revenue = revenue - money;
    }

    @Override
    public String toString() {
        return "SalePoint " +
                "id: " + id +
                ", street: " + street +
                ", adminId: " + adminId +
                "\n";
    }
}
