package View;

import Model.SubscriptionPlan;
import Model.Customer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SubscriptionPlanDetailView extends JPanel {
    protected App app;
    private JLabel nameLabel;
    private JLabel priceLabel;
    private JLabel descripLabel;
    private JLabel dataLabel;
    private JLabel talkTimeLabel;
    private JButton backButton;
    private JButton deleteButton;
    private JButton saveButton;
    private JTextArea subscribedCustomersArea;
    private JLabel revenueLabel;

    private ArrayList<Customer> subscribedCustomers;

    public SubscriptionPlanDetailView(App app, SubscriptionPlan subscriptionPlan,
            ArrayList<Customer> subscribedCustomers) {
        this.app = app;
        this.subscribedCustomers = subscribedCustomers;

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JPanel contentPanel = new JPanel();
        GroupLayout layout = new GroupLayout(contentPanel);
        contentPanel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Subscription details
        JLabel nameFieldLabel = createFieldLabel("Name:");
        nameLabel = createEditableLabel(subscriptionPlan.getName());

        JLabel priceFieldLabel = createFieldLabel("Price ($):");
        priceLabel = createEditableLabel(String.format("%.2f", subscriptionPlan.getPrice()));

        JLabel descripFieldLabel = createFieldLabel("Description:");
        descripLabel = createEditableLabel(subscriptionPlan.getDescription());
        JButton editDescripButton = createEditButton("Edit Description", descripLabel);

        JLabel dataFieldLabel = createFieldLabel("Data (GB):");
        dataLabel = createEditableLabel(Integer.toString(subscriptionPlan.getData()));
        JButton editDataButton = createEditButton("Edit Data", dataLabel);

        JLabel talkTimeFieldLabel = createFieldLabel("Talk Time (Minutes):");
        talkTimeLabel = createEditableLabel(Integer.toString(subscriptionPlan.getTalkTime()));
        JButton editTalkTimeButton = createEditButton("Edit Talk Time", talkTimeLabel);

        saveButton = new JButton("Save");
        saveButton.setFont(new Font("Arial", Font.PLAIN, 12));
        saveButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        saveButton.setEnabled(false);
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameLabel.getText();
                String data = dataLabel.getText();
                String talkTime = talkTimeLabel.getText();
                String description = descripLabel.getText();

                if (name.isEmpty() || data.isEmpty() || talkTime.isEmpty() || description.isEmpty()) {
                    JOptionPane.showMessageDialog(SubscriptionPlanDetailView.this,
                            "Please fill in all the fields.", "Incomplete Fields", JOptionPane.ERROR_MESSAGE);
                } else if (!isNumeric(data) || !isNumeric(talkTime)) {
                    JOptionPane.showMessageDialog(SubscriptionPlanDetailView.this,
                            "Data and Talk Time must be numeric values.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                } else {
                    double price=app.updateSubscriptionPlan(subscriptionPlan, name, Integer.parseInt(data),
                            Integer.parseInt(talkTime), description);
                    priceLabel.setText(String.format("%.2f", price));

                    saveButton.setEnabled(false);
                    backButton.setEnabled(true);

                    app.subscriptionDetail(subscriptionPlan);
                }
            }
        });

        backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.PLAIN, 12));
        backButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.adminDashboard();
            }
        });

        deleteButton = new JButton("Delete");
        deleteButton.setFont(new Font("Arial", Font.PLAIN, 12));
        deleteButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle delete button action here
                app.deleteSubscriptionPlan(subscriptionPlan);
                app.adminDashboard();
            }
        });

        JLabel subscribedCustomersLabel = new JLabel("Subscribed Customers:");
        subscribedCustomersArea = new JTextArea(5, 20);
        subscribedCustomersArea.setEditable(false);
        JScrollPane subscribedCustomersScrollPane = new JScrollPane(subscribedCustomersArea);

        updateSubscribedCustomersArea(); // Update the Subscribed Customers area

        revenueLabel = new JLabel(
                "Total Revenue: $" + calculateRevenue(subscribedCustomers, subscriptionPlan.getPrice()));

        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(nameFieldLabel)
                                        .addComponent(priceFieldLabel)
                                        .addComponent(descripFieldLabel)
                                        .addComponent(dataFieldLabel)
                                        .addComponent(talkTimeFieldLabel))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(nameLabel)
                                        .addComponent(priceLabel)
                                        .addComponent(descripLabel)
                                        .addComponent(dataLabel)
                                        .addComponent(talkTimeLabel))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(editDescripButton)
                                        .addComponent(editDataButton)
                                        .addComponent(editTalkTimeButton)))
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(saveButton)
                                .addComponent(backButton)
                                .addComponent(deleteButton))
                        .addComponent(subscribedCustomersLabel)
                        .addComponent(subscribedCustomersScrollPane)
                        .addComponent(revenueLabel));

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(nameFieldLabel)
                                .addComponent(nameLabel))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(priceFieldLabel)
                                .addComponent(priceLabel))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(descripFieldLabel)
                                .addComponent(descripLabel)
                                .addComponent(editDescripButton))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(dataFieldLabel)
                                .addComponent(dataLabel)
                                .addComponent(editDataButton))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(talkTimeFieldLabel)
                                .addComponent(talkTimeLabel)
                                .addComponent(editTalkTimeButton))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(saveButton)
                                .addComponent(backButton)
                                .addComponent(deleteButton))
                        .addComponent(subscribedCustomersLabel)
                        .addComponent(subscribedCustomersScrollPane)
                        .addComponent(revenueLabel));

        add(contentPanel, BorderLayout.CENTER);

        setPreferredSize(new Dimension(600, 400));
        setVisible(true);
    }

    private JLabel createFieldLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setBorder(new EmptyBorder(5, 0, 5, 10));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private JLabel createEditableLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        label.setBorder(new EmptyBorder(5, 0, 5, 0));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private JButton createEditButton(String text, JLabel targetLabel) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 12));
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newValue = JOptionPane.showInputDialog(
                        SubscriptionPlanDetailView.this, "Enter new value:");
                if (newValue != null && !newValue.isEmpty()) {
                    targetLabel.setText(newValue);
                    saveButton.setEnabled(true);
                    backButton.setEnabled(false);
                }
            }
        });
        return button;
    }

    private void updateSubscribedCustomersArea() {
        StringBuilder customersText = new StringBuilder();
        if (subscribedCustomers != null) {
            for (Customer customer : subscribedCustomers) {
                customersText.append(customer.getUsername()).append("\n");
            }
            subscribedCustomersArea.setText(customersText.toString());
        }
    }

    private double calculateRevenue(ArrayList<Customer> customers, double price) {
        return customers == null ? 0 : customers.size() * price;
    }

    private boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }
}
