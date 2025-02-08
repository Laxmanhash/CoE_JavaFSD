import java.io.*;
import java.util.*;
import java.util.concurrent.*;

class Product {
    private final String productID;
    private final String name;
    private int quantity;
    private final Location location;

    public Product(String productID, String name, int quantity, Location location) {
        this.productID = productID;
        this.name = name;
        this.quantity = quantity;
        this.location = location;
    }

    public String getProductID() { return productID; }
    public String getName() { return name; }
    public int getQuantity() { return quantity; }
    public Location getLocation() { return location; }

    public synchronized void adjustStock(int change) throws OutOfStockException {
        if (quantity + change < 0) throw new OutOfStockException("Product " + name + " is out of stock!");
        quantity += change;
    }

    @Override
    public String toString() {
        return productID + " | " + name + " | Qty: " + quantity + " | Location: " + location;
    }
}

class Location {
    private final int aisle;
    private final int shelf;
    private final int bin;

    public Location(int aisle, int shelf, int bin) {
        this.aisle = aisle;
        this.shelf = shelf;
        this.bin = bin;
    }

    @Override
    public String toString() {
        return "Aisle " + aisle + ", Shelf " + shelf + ", Bin " + bin;
    }
}

class Order implements Comparable<Order> {
    private final String orderID;
    private final List<String> productIDs;
    private final Priority priority;

    public enum Priority { STANDARD, EXPEDITED }

    public Order(String orderID, List<String> productIDs, Priority priority) {
        this.orderID = orderID;
        this.productIDs = productIDs;
        this.priority = priority;
    }

    public List<String> getProductIDs() { return productIDs; }
    public Priority getPriority() { return priority; }

    @Override
    public int compareTo(Order o) {
        return this.priority.compareTo(o.priority);
    }

    @Override
    public String toString() {
        return "Order " + orderID + " | Priority: " + priority + " | Products: " + productIDs;
    }
}

class OutOfStockException extends Exception {
    public OutOfStockException(String message) { super(message); }
}

class InvalidLocationException extends Exception {
    public InvalidLocationException(String message) { super(message); }
}

class InventoryManager {
    private final ConcurrentHashMap<String, Product> products = new ConcurrentHashMap<>();
    private final PriorityBlockingQueue<Order> orderQueue = new PriorityBlockingQueue<>();

    public void addProduct(Product product) {
        products.put(product.getProductID(), product);
    }

    public void placeOrder(Order order) {
        orderQueue.add(order);
        System.out.println("Order Placed: " + order);
    }

    public void processOrders() {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        while (!orderQueue.isEmpty()) {
            Order order = orderQueue.poll();
            if (order != null) {
                executor.execute(() -> fulfillOrder(order));
            }
        }
        executor.shutdown();
    }

    private void fulfillOrder(Order order) {
        System.out.println("Processing: " + order);
        for (String productID : order.getProductIDs()) {
            try {
                Product product = products.get(productID);
                if (product == null) throw new OutOfStockException("Product " + productID + " not found!");
                product.adjustStock(-1);
                logOrder(order, product);
            } catch (OutOfStockException e) {
                System.err.println("Order " + order + " failed: " + e.getMessage());
            }
        }
    }

    public void saveInventory() throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("inventory.dat"))) {
            oos.writeObject(new ArrayList<>(products.values()));
        }
    }

    public void loadInventory() throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("inventory.dat"))) {
            List<Product> productList = (List<Product>) ois.readObject();
            for (Product p : productList) products.put(p.getProductID(), p);
        }
    }

    private void logOrder(Order order, Product product) {
        try (FileWriter fw = new FileWriter("order_log.txt", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println("Order: " + order + " | Fulfilled: " + product);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void displayInventory() {
        System.out.println("\nCurrent Inventory:");
        products.values().forEach(System.out::println);
    }
}

public class WarehouseManagementSystem {
    public static void main(String[] args) {
        InventoryManager inventoryManager = new InventoryManager();

        inventoryManager.addProduct(new Product("P001", "Laptop", 10, new Location(1, 2, 3)));
        inventoryManager.addProduct(new Product("P002", "Mouse", 20, new Location(2, 3, 1)));
        inventoryManager.addProduct(new Product("P003", "Keyboard", 15, new Location(3, 1, 4)));

        List<String> order1Items = Arrays.asList("P001", "P002");
        List<String> order2Items = Arrays.asList("P003");
        List<String> order3Items = Arrays.asList("P002", "P003", "P001");

        Order order1 = new Order("O001", order1Items, Order.Priority.STANDARD);
        Order order2 = new Order("O002", order2Items, Order.Priority.EXPEDITED);
        Order order3 = new Order("O003", order3Items, Order.Priority.EXPEDITED);

        inventoryManager.placeOrder(order1);
        inventoryManager.placeOrder(order2);
        inventoryManager.placeOrder(order3);

        inventoryManager.processOrders();

        try {
            inventoryManager.saveInventory();
            inventoryManager.loadInventory();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        inventoryManager.displayInventory();
    }
}
