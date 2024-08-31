import java.io.*;
import java.util.*;
public class UserInterface {
    private final Scanner scanner;
    private final Inventory inventory;

    public UserInterface(Scanner scanner, Inventory inventory) {
        this.scanner = scanner;
        this.inventory = inventory;
    }

    public void start() {
        while (true) {
            showMenu();
            int choice = getUserChoice();
            if (choice == 9) {
                System.out.println("Exiting the system. Goodbye!");
                break;
            }
            handleUserChoice(choice);
        }
        scanner.close();
    }

    private void showMenu() {
        System.out.println("\nInventory Management System");
        System.out.println("1. Add a new item");
        System.out.println("2. Remove an item");
        System.out.println("3. Update an item");
        System.out.println("4. View inventory");
        System.out.println("5. Search for items");
        System.out.println("6. Export inventory to file");
        System.out.println("7. Import inventory from file");
        System.out.println("8. Check low-stock items");
        System.out.println("9. Exit");
        System.out.print("Choose an option: ");
    }

    private int getUserChoice() {
        while (true) {
            try {
                int choice = scanner.nextInt();
                if (choice >= 1 && choice <= 9) {
                    return choice;
                } else {
                    System.out.print("Invalid choice. Please choose between 1 and 9: ");
                }
            } catch (InputMismatchException e) {
                System.out.print("Invalid input. Please enter a number between 1 and 9: ");
                scanner.next(); // clear invalid input
            }
        }
    }

    private void handleUserChoice(int choice) {
        scanner.nextLine(); // clear the buffer
        switch (choice) {
            case 1:
                addItem();
                break;
            case 2:
                removeItem();
                break;
            case 3:
                updateItem();
                break;
            case 4:
                viewInventory();
                break;
            case 5:
                searchItems();
                break;
            case 6:
                exportInventory();
                break;
            case 7:
                importInventory();
                break;
            case 8:
                checkLowStockItems();
                break;
            default:
                break;
        }
    }

    private void addItem() {
        Item newItem = createNewItem();
        inventory.addItem(newItem);
    }

    private Item createNewItem() {
        System.out.print("Enter item name: ");
        String name = scanner.nextLine();
        double price = promptForDouble("Enter item price: ");
        int quantity = promptForInt("Enter item quantity: ");
        return new Item(name, price, quantity);
    }

    private void removeItem() {
        System.out.print("Enter the name of the item to remove: ");
        String name = scanner.nextLine();
        List<Item> foundItems = inventory.findItemsByName(name);
        if (foundItems.isEmpty()) {
            System.out.println("Item '" + name + "' not found.");
        } else if (foundItems.size() == 1) {
            inventory.removeItem(foundItems.get(0));
            System.out.println("Item '" + name + "' removed successfully.");
        } else {
            System.out.println("Multiple items found:");
            for (int i = 0; i < foundItems.size(); i++) {
                System.out.println((i + 1) + ". " + foundItems.get(i));
            }
            int itemIndex = promptForInt("Enter the number of the item to remove: ") - 1;
            if (itemIndex >= 0 && itemIndex < foundItems.size()) {
                inventory.removeItem(foundItems.get(itemIndex));
                System.out.println("Item '" + foundItems.get(itemIndex).getName() + "' removed successfully.");
            } else {
                System.out.println("Invalid choice. No item removed.");
            }
        }
    }

    private void updateItem() {
        System.out.print("Enter the name of the item to update: ");
        String name = scanner.nextLine();
        List<Item> foundItems = inventory.findItemsByName(name);
        if (foundItems.isEmpty()) {
            System.out.println("Item '" + name + "' not found.");
        } else if (foundItems.size() == 1) {
            Item itemToUpdate = foundItems.get(0);
            System.out.println("Current item details:");
            System.out.println(itemToUpdate);
            System.out.print("Enter new price (leave blank to keep current): ");
            String newPriceStr = scanner.nextLine();
            if (!newPriceStr.isEmpty()) {
                double newPrice = promptForDouble("Enter the new price: ");
                itemToUpdate.setPrice(newPrice);
            }
            System.out.print("Enter new quantity (leave blank to keep current): ");
            String newQuantityStr = scanner.nextLine();
            if (!newQuantityStr.isEmpty()) {
                int newQuantity = promptForInt("Enter the new quantity: ");
                itemToUpdate.setQuantity(newQuantity);
            }
            System.out.println("Item updated successfully.");
        } else {
            System.out.println("Multiple items found:");
            for (int i = 0; i < foundItems.size(); i++) {
                System.out.println((i + 1) + ". " + foundItems.get(i));
            }
            int itemIndex = promptForInt("Enter the number of the item to update: ") - 1;
            if (itemIndex >= 0 && itemIndex < foundItems.size()) {
                Item itemToUpdate = foundItems.get(itemIndex);
                System.out.print("Enter new price (leave blank to keep current): ");
                String newPriceStr = scanner.nextLine();
                if (!newPriceStr.isEmpty()) {
                    double newPrice = promptForDouble("Enter the new price: ");
                    itemToUpdate.setPrice(newPrice);
                }
                System.out.print("Enter new quantity (leave blank to keep current): ");
                String newQuantityStr = scanner.nextLine();
                if (!newQuantityStr.isEmpty()) {
                    int newQuantity = promptForInt("Enter the new quantity: ");
                    itemToUpdate.setQuantity(newQuantity);
                }
                System.out.println("Item updated successfully.");
            } else {
                System.out.println("Invalid choice. No item updated.");
            }
        }
    }

    private void viewInventory() {
        inventory.displayItems();
    }

    private void searchItems() {
        System.out.print("Enter the search keyword: ");
        String keyword = scanner.nextLine();
        List<Item> foundItems = inventory.searchItems(keyword);
        if (foundItems.isEmpty()) {
            System.out.println("No items found matching the keyword '" + keyword + "'.");
        } else {
            System.out.println("Search results:");
            for (Item item : foundItems) {
                System.out.println(item);
            }
        }
    }

    private void exportInventory() {
        System.out.print("Enter the filename to save the inventory: ");
        String filename = scanner.nextLine();
        try {
            inventory.exportToFile(filename);
            System.out.println("Inventory successfully exported to " + filename);
        } catch (IOException e) {
            System.out.println("Error exporting inventory: " + e.getMessage());
        }
    }

    private void importInventory() {
        System.out.print("Enter the filename to import the inventory from: ");
        String filename = scanner.nextLine();
        try {
            inventory.importFromFile(filename);
            System.out.println("Inventory successfully imported from " + filename);
        } catch (IOException e) {
            System.out.println("Error importing inventory: " + e.getMessage());
        }
    }

    private void checkLowStockItems() {
        int lowStockThreshold = promptForInt("Enter the low-stock threshold: ");
        List<Item> lowStockItems = inventory.getLowStockItems(lowStockThreshold);
        if (lowStockItems.isEmpty()) {
            System.out.println("No items are currently low in stock.");
        } else {
            System.out.println("Low-stock items:");
            for (Item item : lowStockItems) {
                System.out.println(item);
            }
        }
    }

    private double promptForDouble(String message) {
        System.out.print(message);
        while (true) {
            try {
                double value = scanner.nextDouble();
                if (value < 0) {
                    System.out.print("Value cannot be negative. Please enter a positive value: ");
                } else {
                    return value;
                }
            } catch (InputMismatchException e) {
                System.out.print("Invalid input. Please enter a valid number: ");
                scanner.next(); // clear invalid input
            } finally {

            }
        }
    }

    private int promptForInt(String message) {
        System.out.print(message);
        while (true) {
            try {
                int value = scanner.nextInt();
                if (value < 0) {
                    System.out.print("Value cannot be negative. Please enter a positive value: ");
                } else {
                    return value;
                }
            } catch (InputMismatchException e) {
                System.out.print("Invalid input. Please enter a valid integer: ");
                scanner.next(); // clear invalid input
            }
        }
    }
}
