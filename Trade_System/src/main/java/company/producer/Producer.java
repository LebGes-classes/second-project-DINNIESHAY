package company.producer;

public class Producer {

    public int id;
    public String name;

    public Producer() {}

    public Producer(int id, String name) {
        this.name = name;
        this.id = id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Producer " +
                "id: " + id +
                ", name: " + name +
                "\n";
    }
}
