import Model.Customer;
import Model.CustomerManager;
import Model.SubscriptionManager;
import Model.SubscriptionPlan;

public class test {
    public static void main(String[] args) throws Exception {
        CustomerManager customerManager = new CustomerManager();
        customerManager.loadCustomersFromFile();
        Customer customer = new Customer("johndoe", "password", "johnny@gmail.com");
        customerManager.PrintCustomers();
        customerManager.addCustomer(customer.getUsername(), customer.getPassword(), customer.getEmail());
        customerManager.addCustomer("raphael", "password", "prime@promo.com");
        customerManager.PrintCustomers();
        // SubscriptionManager subscriptionManager = new SubscriptionManager();
        // SubscriptionPlan plan = new SubscriptionPlan(1, "Plan 1", "Plan 1
        // description", 150, 200);
        // SubscriptionPlan plan2 = new SubscriptionPlan(2, "Plan 2", "Plan 2
        // description", 1250, 1250);
        // subscriptionManager.loadSubscriptionPlans();
        // subscriptionManager.removeSubscriptionPlan("Plan 1");
        // subscriptionManager.addSubscriptionPlan(plan.getName(),
        // plan.getDescription(), plan.getData(),
        // plan.getTalkTime());
        // subscriptionManager.addSubscriptionPlan(plan2.getName(),
        // plan2.getDescription(), plan2.getData(),
        // plan2.getTalkTime());

        // subscriptionManager.PrintSubscriptionPlans();
    }
}
