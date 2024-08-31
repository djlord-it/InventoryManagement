public class Item {
    private final String name;
    private double price;
    private int quantity;

    public Item(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Item{name='" + name + "', price=" + price + ", quantity=" + quantity + '}';
    }

    public String toCSV() {
        return name + "," + price + "," + quantity;
    }

    public static Item fromCSV(String csv) {
        String[] parts = csv.split(",");
        String name = parts[0];
        double price = Double.parseDouble(parts[1]);
        int quantity = Integer.parseInt(parts[2]);
        return new Item(name, price, quantity);
    }
}
