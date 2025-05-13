package company.user;

public class User {

    public int id;
    public String firstName;
    public String lastName;
    public String phoneNumber;

    public User() {}

    public User(int id, String firstName, String lastName, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "User " +
                "id: " + id +
                ", firstName: " + firstName +
                ", lastName: " + lastName +
                ", phoneNumber: " + phoneNumber +
                "\n";
    }
}
