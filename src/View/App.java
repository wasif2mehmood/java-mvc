package View;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;

import Model.Customer;
import Model.SubscriptionManager;
import Model.SubscriptionPlan;
import Controller.CustomerController;

public class App extends JFrame {
    LoginSelectionView loginSelectionView;
    AdminLoginView adminLoginView;
    CustomerLoginView customerLoginView;
    CustomerRegistrationView customerRegistrationView;
    CustomerDashboardView customerDashboardView;
    AdminDashboardView adminDashboardView;
    SubscriptionPlanDetailView subscriptionPlanDetailView;
    NewSubscriptionPlanView newSubscriptionPlanView;

    public App() {
        setTitle("DataPLUS - TelcoApp");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setResizable(false);

        setVisible(true);
        selectionPane();
    }

    protected void paneChange(JPanel panel) {
        getContentPane().removeAll();
        getContentPane().add(panel);
        revalidate();
        repaint();
    }

    protected void selectionPane() {
        loginSelectionView = new LoginSelectionView(this);
        paneChange(loginSelectionView);
    }

    protected void customerLogin() {
        customerLoginView = new CustomerLoginView(this);
        paneChange(customerLoginView);
    }

    protected void adminLogin() {
        adminLoginView = new AdminLoginView(this);
        paneChange(adminLoginView);
    }

    protected void customerDashboard(Customer customer) {
        customerDashboardView = new CustomerDashboardView(this, customer);
        paneChange(customerDashboardView);
    }

    protected void adminDashboard() {
        adminDashboardView = new AdminDashboardView(this);
        paneChange(adminDashboardView);
    }

    protected void subscriptionDetail(SubscriptionPlan subscriptionPlan) {
        ArrayList<Customer> subCustomers = getSubscribedCustomers(subscriptionPlan);
        subscriptionPlanDetailView = new SubscriptionPlanDetailView(this, subscriptionPlan, subCustomers);
        paneChange(subscriptionPlanDetailView);
    }

    protected void newPlanDetails() {
        newSubscriptionPlanView = new NewSubscriptionPlanView(this);
        paneChange(newSubscriptionPlanView);
    }

    protected void openRegistrationView() {
        customerRegistrationView = new CustomerRegistrationView(this);
        paneChange(customerRegistrationView);
    }

    protected String[] validateCustomerLogin(String username, String password) {
        return CustomerController.validator(username, password);
    }

    protected List<SubscriptionPlan> getAdminSubscriptionPlans() {
        SubscriptionManager subscriptionManager = new SubscriptionManager();
        try {
            subscriptionManager.loadSubscriptionPlans();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return subscriptionManager.getSubscriptionPlans();
    }

    protected ArrayList<Customer> getSubscribedCustomers(SubscriptionPlan subscriptionPlan) {

        ArrayList<Customer> subscribedCustomers = new ArrayList<Customer>();
        ArrayList<String> subscribedCustomersUsername = new ArrayList<String>();
        try (BufferedReader reader = new BufferedReader(new FileReader("src\\Model\\subscribed.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String savedUsername = parts[0].trim();
                String savedPlanName = parts[1].trim();
                System.out.println(subscriptionPlan.getName());
                if (savedPlanName.equals(subscriptionPlan.getName())) {
                    String name = savedUsername;
                    subscribedCustomersUsername.add(name);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading subscribed.txt file: " + e.getMessage());
            return null;
        }

        for (String username : subscribedCustomersUsername) {
            try (BufferedReader reader = new BufferedReader(new FileReader("src\\Model\\customers.txt"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    String savedUsername = parts[0].trim();
                    String savedPassword = parts[1].trim();
                    String savedEmail = parts[2].trim();
                    if (savedUsername.equals(username)) {
                        Customer customer = new Customer(savedUsername, savedPassword, savedEmail);
                        subscribedCustomers.add(customer);
                    }
                }
            } catch (IOException e) {
                System.out.println("Error reading customers.txt file: " + e.getMessage());
                return null;
            }
        }

        return subscribedCustomers;
    }

    protected Customer loadCustomer(String[] customerDetails) {
        // Code to return customer object from DB

        // create a customer object
        Customer customer = new Customer(customerDetails[0], customerDetails[1], customerDetails[2]);

        return customer;
    }

    protected void registerNewUser(String name, String email, String password) {
        try {
            File file = new File("src\\Model\\customers.txt");
            PrintWriter pw = new PrintWriter(new FileWriter(file, true));
            pw.println(name + "," + password + "," + email);
            pw.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    protected boolean userAlreadyExists(String name, String email, String password) {
        // Check if user already exists, if true new user will not be create
        // When the customer cancels a subscription for a given plan
        List<String> lines = new ArrayList<>();

        // Read the file and collect all lines except the one to be removed
        try (BufferedReader reader = new BufferedReader(new FileReader("src\\Model\\customers.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String Username = parts[0].trim();
                String Email = parts[2].trim();
                if (Username.equals(name) && Email.equals(email)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading subscribed.txt file: " + e.getMessage());
            return false;
        }

        return false;
    }

    protected void cancelSubscription(Customer customer, SubscriptionPlan plan) {
        // When the customer cancels a subscription for a given plan
        List<String> lines = new ArrayList<>();
        int n=0;

        // Read the file and collect all lines except the one to be removed
        try (BufferedReader reader = new BufferedReader(new FileReader("src\\Model\\subscribed.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String savedUsername = parts[0].trim();
                String savedPlanName = parts[1].trim();
                if (savedUsername.equals(customer.getUsername()) && savedPlanName.equals(plan.getName())) {
                    if(n>0){
                        lines.add(line);
                    }
                    n++;
                    continue;
                }
                else{
                    lines.add(line);
                }
                
            }
        } catch (IOException e) {
            System.out.println("Error reading subscribed.txt file: " + e.getMessage());
            return;
        }

        // Write the updated contents back to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src\\Model\\subscribed.txt"))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing to subscribed.txt file: " + e.getMessage());
        }
    }

    protected void subscribe(Customer customer, SubscriptionPlan plan) {
        // open src\Model\subscribed.txt and add the customer and plan to the file
        try {
            File file = new File("src\\Model\\subscribed.txt");
            PrintWriter pw = new PrintWriter(new FileWriter(file, true));
            pw.println(customer.getUsername() + "," + plan.getName());
            pw.close();
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    protected void addNewSubscriptionPlan(String name, int data, int talkTime, String description) {
        // When the admin adds a new subscription plan
        // create a random number between 1 and 1000000
        int random = (int) (Math.random() * 1000000 + 1);
        // create a subscription object
        SubscriptionPlan subscriptionPlan = new SubscriptionPlan(random, name, description, data, talkTime);
        subscriptionPlan.setPricesBasedOnDataAndTalkTime();
        try {
            File file = new File("src\\Model\\subscriptionPlans.txt");
            PrintWriter pw = new PrintWriter(new FileWriter(file, true));
            pw.println(random + "," + name + ","+ description + "," + data + "," + talkTime + "," 
                    + subscriptionPlan.getPrice());
            pw.close();
        } catch (Exception e) {
            System.out.println(e);

        }
    }

    protected void deleteSubscriptionPlan(SubscriptionPlan subscriptionPlan) {
        // When the admin deletes a subscription plan

        List<String> lines = new ArrayList<>();

        // Read the file and collect all lines except the one to be removed

        try (BufferedReader reader = new BufferedReader(new FileReader("src\\Model\\subscriptionPlans.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String savedName = parts[1].trim();
                System.out.println(savedName+"kkkk");
                System.out.println(subscriptionPlan.getName()+"jjjjj");
                if (!savedName.equals(subscriptionPlan.getName())) {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading subscriptionPlans.txt file: " + e.getMessage());
            return;
        }

        // Write the updated contents back to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src\\Model\\subscriptionPlans.txt"))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing to subscriptionPlans.txt file: " + e.getMessage());
        }
    }

    protected double updateSubscriptionPlan(SubscriptionPlan subscriptionPlan, String name, int data, int talkTime,
            String description) {
        // When admin updates a Subscription Plan
        String Name = subscriptionPlan.getName();
        subscriptionPlan.setName(name);
        subscriptionPlan.setData(data);
        subscriptionPlan.setTalkTime(talkTime);
        subscriptionPlan.setDescription(description);
        subscriptionPlan.setPricesBasedOnDataAndTalkTime();

        List<String> lines = new ArrayList<>();

        // Read the file and collect all lines except the one to be removed

        try (BufferedReader reader = new BufferedReader(new FileReader("src\\Model\\subscriptionPlans.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String savedName = parts[1].trim();
                if (!savedName.equals(Name)) {
                    lines.add(line);
                } else {
                    lines.add(parts[0].trim() + "," + name + "," + description + "," + data + "," + talkTime + ","
                            + subscriptionPlan.getPrice());
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading subscriptionPlans.txt file: " + e.getMessage());
            return subscriptionPlan.getPrice();
        }

        // Write the updated contents back to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src\\Model\\subscriptionPlans.txt"))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing to subscriptionPlans.txt file: " + e.getMessage());
        }

        return subscriptionPlan.getPrice();
    }
}