package View;

import Model.Customer;
import Model.SubscriptionPlan;
import Model.SubscriptionManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class CustomerDashboardView extends JPanel {
    private JPanel customerInfoPanel;
    private JPanel subscribedPlansPanel;
    private JPanel availablePackagesPanel;
    private Customer customer;
    protected App app;

    public CustomerDashboardView(App app, Customer customer) {
        this.app = app;
        this.customer = customer;
        SubscriptionManager subscriptionManager = new SubscriptionManager();
        try {
            System.out.println(subscriptionManager.loadSubscriptionPlans());
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        customerInfoPanel = createCustomerInfoPanel();
        subscribedPlansPanel = createSubscribedPlansPanel(customer.getSubscriptions(customer.getUsername()));
        availablePackagesPanel = createAvailablePackagesPanel((ArrayList<SubscriptionPlan>) subscriptionManager.getSubscriptionPlans());

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setViewportView(createContentPane());

        add(scrollPane, BorderLayout.CENTER);

        setPreferredSize(new Dimension(600, 400));

        setVisible(true);
    }

    private JPanel createContentPane() {
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        contentPane.setBackground(Color.WHITE);

        contentPane.add(customerInfoPanel);
        contentPane.add(subscribedPlansPanel);
        contentPane.add(availablePackagesPanel);

        return contentPane;
    }

    private JPanel createCustomerInfoPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.decode("#98c1d9"));

        JLabel usernameLabel = new JLabel(customer.getUsername());
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 28));
        JLabel emailLabel = new JLabel(customer.getEmail());
        emailLabel.setFont(new Font("Arial", Font.ITALIC, 14));

        JButton logoutButton = new JButton("Logout");
        logoutButton.setBackground(Color.decode("#293241"));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.selectionPane();
            }
        });

        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        Component gap = Box.createRigidArea(new Dimension(20, 50));

        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addComponent(gap)
                        .addGroup(layout.createParallelGroup()
                                .addComponent(usernameLabel)
                                .addComponent(emailLabel))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE,
                                Short.MAX_VALUE)
                        .addComponent(logoutButton)
                        .addComponent(gap));

        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(gap)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(usernameLabel)
                                .addComponent(emailLabel))
                        .addComponent(logoutButton)
                        .addComponent(gap));

        return panel;
    }

    private JPanel createPackagePlanPanel(String title, ArrayList<SubscriptionPlan> plans) {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0)); // Add space above the panel

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);

        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        if (plans == null || plans.isEmpty()) {
            JLabel emptyLabel = new JLabel("None");
            emptyLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            emptyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            // Add spacing before and after the "None" label
            contentPanel.add(Box.createVerticalStrut(20));
            contentPanel.add(emptyLabel);
            contentPanel.add(Box.createVerticalStrut(20));
        } else {
            for (SubscriptionPlan plan : plans) {
                JPanel planPanel = createPlanPanel(plan, title.equals("Subscribed Plans"));

                // Add spacing between each plan
                contentPanel.add(Box.createVerticalStrut(20));
                contentPanel.add(planPanel);
            }
        }

        panel.add(contentPanel, BorderLayout.CENTER);
        panel.add(titleLabel, BorderLayout.NORTH);

        return panel;
    }

    private JPanel createSubscribedPlansPanel(ArrayList<SubscriptionPlan> subscribed) {
        return createPackagePlanPanel("Subscribed Plans", subscribed);
    }

    private JPanel createAvailablePackagesPanel(ArrayList<SubscriptionPlan> available) {
        return createPackagePlanPanel("Available Plans", available);
    }

    private JPanel createPlanPanel(SubscriptionPlan plan, boolean subscribed) {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel planLabel = new JLabel(plan.getName());
        planLabel.setFont(new Font("Arial", Font.BOLD, 14));

        JLabel priceLabel = new JLabel("Price: $" + String.format("%.2f", plan.getPrice()));
        priceLabel.setFont(new Font("Arial", Font.PLAIN, 12));

        JLabel descripLabel = new JLabel(plan.getDescription());
        descripLabel.setFont(new Font("Arial", Font.PLAIN, 12));

        JLabel dataLabel = new JLabel("Data: " + plan.getData() + "GB");
        dataLabel.setFont(new Font("Arial", Font.PLAIN, 12));

        JLabel talkTimeLabel = new JLabel("Talk Time: " + plan.getTalkTime() + " minutes");
        talkTimeLabel.setFont(new Font("Arial", Font.PLAIN, 12));

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.add(planLabel);
        infoPanel.add(priceLabel);
        infoPanel.add(descripLabel);
        infoPanel.add(dataLabel);
        infoPanel.add(talkTimeLabel);

        panel.add(infoPanel, BorderLayout.CENTER);

        JButton actionButton = new JButton(subscribed ? "Cancel Subscription" : "Subscribe");
        actionButton.addActionListener(e -> {
            if (subscribed) {
                app.cancelSubscription(customer, plan);
            } else {
                app.subscribe(customer, plan);
            }
            app.customerDashboard(customer);
        });

        panel.add(actionButton, BorderLayout.SOUTH);

        return panel;
    }

}
