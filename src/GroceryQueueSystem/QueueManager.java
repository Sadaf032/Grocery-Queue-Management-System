package GroceryQueueSystem;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class QueueManager {
    private Queue<Customer> queue;
    private static final String FILE_PATH = "queue_data.txt";

    public QueueManager() {
        queue = new LinkedList<>();
        loadQueueFromFile();
    }

    public void enqueue(Customer c) {
        queue.add(c);
        saveQueueToFile();
    }

    public Customer dequeue() {
        Customer c = queue.poll();
        saveQueueToFile();
        return c;
    }

    public boolean updateCustomer(int id, String name, int items, double bill) {
        for (Customer c : queue) {
            if (c.getId() == id) {
                c.setName(name);
                c.setTotalItems(items);
                c.setTotalBill(bill);
                saveQueueToFile();
                return true;
            }
        }
        return false;
    }

    // *** Newly added method ***
    public Customer getCustomerById(int id) {
        for (Customer c : queue) {
            if (c.getId() == id) {
                return c;
            }
        }
        return null;  // not found
    }

    public List<Customer> getAllCustomers() {
        return List.copyOf(queue);  // returns an immutable copy
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    // --- SAVE TO FILE ---
    private void saveQueueToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Customer c : queue) {
                writer.write(c.getId() + "," + c.getName() + "," + c.getTotalItems() + "," + c.getTotalBill());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("⚠️ Error saving queue: " + e.getMessage());
        }
    }

    // --- LOAD FROM FILE ---
    private void loadQueueFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    int id = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    int items = Integer.parseInt(parts[2]);
                    double bill = Double.parseDouble(parts[3]);
                    queue.add(new Customer(id, name, items, bill));
                }
            }
        } catch (IOException e) {
            System.out.println("⚠️ Error loading queue: " + e.getMessage());
        }
    }
}
