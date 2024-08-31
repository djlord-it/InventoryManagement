import java.io.*;
import java.util.*;
public class Inventory {
    private final List<Item> items = new ArrayList<>();

    public void addItem(Item item) {
        items.add(item);
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    public List<Item> findItemsByName(String name) {
        List<Item> foundItems = new ArrayList<>();
        for (Item item : items) {
            if (item.getName().equalsIgnoreCase(name)) {
                foundItems.add(item);
            }
        }
        return foundItems;
    }

    public List<Item> searchItems(String keyword) {
        List<Item> foundItems = new ArrayList<>();
        for (Item item : items) {
            if (item.getName().toLowerCase().contains(keyword.toLowerCase())) {
                foundItems.add(item);
            }
        }
        return foundItems;
    }

    public void displayItems() {
        if (items.isEmpty()) {
            System.out.println("Inventory is empty.");
        } else {
            for (Item item : items) {
                System.out.println(item);
            }
        }
    }

    public void exportToFile(String filename) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (Item item : items) {
                writer.println(item.toCSV());
            }
        }
    }

    public void importFromFile(String filename) throws IOException {
        items.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Item item = Item.fromCSV(line);
                items.add(item);
            }
        }
    }

    public List<Item> getLowStockItems(int threshold) {
        List<Item> lowStockItems = new ArrayList<>();
        for (Item item : items) {
            if (item.getQuantity() < threshold) {
                lowStockItems.add(item);
            }
        }
        return lowStockItems;
    }
}
