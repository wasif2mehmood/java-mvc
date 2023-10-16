package Model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CustomerManager {
    private List<Customer> customers;
    private String customersFilePath; // File path for storing customer data

    public CustomerManager() {
        this.customers = new ArrayList<>();
        this.customersFilePath = "./src/Model/customers.txt";
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void loadCustomersFromFile() {
        try {
            File file = new File(customersFilePath);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                String username = parts[0].trim();
                String password = parts[1].trim();
                String email = parts[2].trim();
                Customer customer = new Customer(username, password, email);
                customers.add(customer);
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Customers file not found. Creating new file...");
            saveCustomersToFile();
        }
    }

    public boolean addCustomer(String username, String password, String email) {
        if (isUsernameTaken(username)) {
            System.out.println("Username already exists. Please choose a different username.");
            return false;
        }

        if (isEmailTaken(email)) {
            System.out.println("Email address already exists. Please provide a different email address.");
            return false;
        }
        Customer customer = new Customer(username, password, email);
        customers.add(customer);
        saveCustomersToFile();
        return true;
    }

    public void PrintCustomers() {
        for (Customer customer : customers) {
            System.out.println(customer.getUsername() + " " + customer.getPassword() + " " + customer.getEmail());
        }
    }

    public void removeCustomer(String username) {
        if (isUsernameTaken(username)) {
            Customer customer = getCustomerByUsername(username);
            customers.remove(customer);
            saveCustomersToFile();
        } else {
            System.out.println("Customer not found.");
        }
    }

    public Customer getCustomerByUsername(String username) {
        for (Customer customer : customers) {
            if (customer.getUsername().equals(username)) {
                return customer;
            }
        }
        return null; // Customer not found
    }

    public boolean isUsernameTaken(String username) {
        for (Customer customer : customers) {
            if (customer.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public boolean isEmailTaken(String email) {
        for (Customer customer : customers) {
            if (customer.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    public void saveCustomersToFile() {
        try {
            File file = new File(customersFilePath);
            PrintWriter writer = new PrintWriter(file);

            for (Customer customer : customers) {
                writer.println(customer.getUsername() + "," + customer.getPassword() + "," + customer.getEmail());
            }

            writer.close();
        } catch (IOException e) {
            System.out.println("Error writing to customers file: " + e.getMessage());
        }
    }
}
