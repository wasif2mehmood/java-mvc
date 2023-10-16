package Model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Customer {
    private String username;
    private String password;
    private String email;
    private ArrayList<SubscriptionPlan> subscriptionPlans;
    // Add additional attributes as needed

    public Customer(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    // Getters and setters for the attributes

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

   public ArrayList<SubscriptionPlan> getSubscriptions(String username) {
        ArrayList<SubscriptionPlan> userSubscriptions = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("src\\Model\\subscribed.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String savedUsername = parts[0].trim();
                String planName = parts[1].trim();

                if (savedUsername.equals(username)) {
                    SubscriptionPlan subscriptionPlan = findSubscriptionPlan(planName);
                    if (subscriptionPlan != null) {
                        userSubscriptions.add(subscriptionPlan);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading subscription.txt file: " + e.getMessage());
        }

        return userSubscriptions;
    }

   private SubscriptionPlan findSubscriptionPlan(String planName) {
        try (BufferedReader reader = new BufferedReader(new FileReader("src\\Model\\subscriptionplans.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int current_id = Integer.parseInt(parts[0].trim());
                String name = parts[1].trim();
                String description = parts[2].trim();
                int data = Integer.parseInt(parts[3].trim());
                int talkTime = Integer.parseInt(parts[4].trim());
                double price = Double.parseDouble(parts[5].trim());

                if (name.equals(planName)) {
                    return new SubscriptionPlan(current_id, name, description, data, talkTime, price);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading subscriptionplans.txt file: " + e.getMessage());
        }

        return null;
    }


    public void setSubscriptions(ArrayList<SubscriptionPlan> subscriptionPlans) {
        this.subscriptionPlans = subscriptionPlans;
    }

    
    // Other methods as needed
}
