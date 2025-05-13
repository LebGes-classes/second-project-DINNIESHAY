package company.user.buyer;

import company.user.User;

public class Buyer extends User {

    public Buyer() {}

    public Buyer(int id, String firstName, String lastName, String phoneNumber) {
        super(id, firstName, lastName, phoneNumber);
    }

    @Override
    public String toString() {
        return "Buyer " +
                "id: " + id +
                ", firstName: " + firstName +
                ", lastName: " + lastName +
                ", phoneNumber: " + phoneNumber +
                "\n";
    }
}
