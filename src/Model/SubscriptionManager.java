package Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class SubscriptionManager {
    static int id = 1;
    String SubscriptionsFilePath;
    List<SubscriptionPlan> subscriptionPlans;
    HashMap<String, Double> PricePlan;

    public SubscriptionManager() {
        this.SubscriptionsFilePath = "./src/Model/subscriptionplans.txt";
        subscriptionPlans = new ArrayList<>();
    }

    public List<SubscriptionPlan> getSubscriptionPlans() {
        return subscriptionPlans;
    }

    public List<SubscriptionPlan> loadSubscriptionPlans() throws FileNotFoundException {
        File file = new File(SubscriptionsFilePath);
        Scanner sc = new Scanner(file);
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] parts = line.split(",");
            int current_id = Integer.parseInt(parts[0].trim());
            String name = parts[1].trim();
            String description = parts[2].trim();
            int data = Integer.parseInt(parts[3].trim());
            int talkTime = Integer.parseInt(parts[4].trim());
            double price = Double.parseDouble(parts[5].trim());
            SubscriptionPlan subscriptionPlan = new SubscriptionPlan(current_id, name, description, data, talkTime,
                    price);
            subscriptionPlans.add(subscriptionPlan);
            increaceID();

        }
        sc.close();
        return subscriptionPlans;
    }

    public void increaceID() {
        id++;
    }

    public void setId(int mid) {
        id = mid;
    }

    public boolean isPlanAlreadyAvailable(String planName) {
        if (subscriptionPlans.isEmpty()) {
            return false;
        }
        for (SubscriptionPlan subscriptionPlan : subscriptionPlans) {
            if (subscriptionPlan.getName().equals(planName)) {
                return true;
            }
        }
        return false;
    }

    public boolean addSubscriptionPlan(String name, String description, int data, int talkTime) {
        if (isPlanAlreadyAvailable(name)) {
            System.out.println("Subscription plan already exists. Please choose a different plan name." + name);
            return false;
        }
        SubscriptionPlan subscriptionPlan = new SubscriptionPlan(id, name, description, data, talkTime);
        subscriptionPlans.add(subscriptionPlan);
        increaceID();
        saveSubscriptionPlansToFile();
        return true;
    }

    public boolean removeSubscriptionPlan(String name) {
        if (isPlanAlreadyAvailable(name)) {
            for (SubscriptionPlan subscriptionPlan : subscriptionPlans) {
                if (subscriptionPlan.getName().equals(name)) {
                    subscriptionPlans.remove(subscriptionPlan);
                    return true;
                }
            }
        } else {
            return false;
        }
        return false;
    }

    public void PrintSubscriptionPlans() {
        for (SubscriptionPlan subscriptionPlan : subscriptionPlans) {
            System.out.println(subscriptionPlan.getId() + " " + subscriptionPlan.getName() + " "
                    + subscriptionPlan.getDescription() + " " + subscriptionPlan.getData() + " "
                    + subscriptionPlan.getTalkTime() + " " + subscriptionPlan.getPrice());
        }
    }

    public void saveSubscriptionPlansToFile() {
        try {
            File file = new File(SubscriptionsFilePath);
            PrintWriter writer = new PrintWriter(file);

            for (SubscriptionPlan subscriptionPlan : subscriptionPlans) {
                writer.println(subscriptionPlan.getId() + "," + subscriptionPlan.getName() + ","
                        + subscriptionPlan.getDescription() + "," + subscriptionPlan.getData() + ","
                        + subscriptionPlan.getTalkTime() + "," + subscriptionPlan.getPrice());
            }

            writer.close();
        } catch (IOException e) {
            System.out.println("Error writing to customers file: " + e.getMessage());
        }

    }



    // Existing code...


}
