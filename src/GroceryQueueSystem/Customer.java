package GroceryQueueSystem;

public class Customer {
    private int id;
    private String name;
    private int totalItems;
    private double totalBill;

    public Customer(int id, String name, int totalItems, double totalBill) {
        this.id = id;
        this.name = name;
        this.totalItems = totalItems;
        this.totalBill = totalBill;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getTotalItems() { return totalItems; }
    public double getTotalBill() { return totalBill; }

    public void setName(String name) { this.name = name; }
    public void setTotalItems(int totalItems) { this.totalItems = totalItems; }
    public void setTotalBill(double totalBill) { this.totalBill = totalBill; }

    @Override
    public String toString() {
        return id + " - " + name + " | Items: " + totalItems + " | Bill: Rs." + totalBill;
    }
}

