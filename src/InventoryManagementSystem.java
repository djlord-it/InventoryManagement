import java.io.*;
import java.util.*;

public class InventoryManagementSystem {

    public static void main(String[] args) {
        Inventory inventory = new Inventory();
        UserInterface ui = new UserInterface(new Scanner(System.in), inventory);
        ui.start();
    }
}
