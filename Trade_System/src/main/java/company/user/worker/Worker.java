package company.user.worker;

import company.user.User;

public class Worker extends User {

    public int workPlaceId;

    public Worker() {}

    public Worker(int id, String firstName, String lastName, String phoneNumber, int workPlaceId) {
        super(id, firstName, lastName, phoneNumber);
        this.workPlaceId = workPlaceId;
    }

    public void setWorkPlaceId(int workPlaceId) {
        this.workPlaceId = workPlaceId;
    }

    @Override
    public String toString() {
        return "Worker " +
                "id: " + id +
                ", firstName: " + firstName +
                ", lastName: " + lastName +
                ", phoneNumber: " + phoneNumber +
                ", workPlaceId: " + workPlaceId +
                "\n";
    }
}
