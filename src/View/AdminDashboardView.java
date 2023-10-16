package View;

import Model.SubscriptionPlan;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AdminDashboardView extends JPanel {
    private JPanel adminInfoPanel;
    private JPanel subscriptionPlansPanel;
    private JButton addPlanButton;
    protected App app;

    public AdminDashboardView(App app) {
        this.app = app;

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        adminInfoPanel = createAdminInfoPanel();
        subscriptionPlansPanel = createSubscriptionPlansPanel();
        addPlanButton = createAddPlanButton();

        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        contentPane.setBackground(Color.WHITE);
        contentPane.add(subscriptionPlansPanel, BorderLayout.CENTER);
        contentPane.add(addPlanButton, BorderLayout.SOUTH);

        JScrollPane scrollPane = new JScrollPane(contentPane);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        add(adminInfoPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        setPreferredSize(new Dimension(600, 400));
        setVisible(true);
    }

    private JPanel createAdminInfoPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.decode("#98c1d9"));

        JLabel nameLabel = new JLabel("Admin Account");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 28));
        JLabel emailLabel = new JLabel("admin@dataplus.com");
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

        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup()
                                .addComponent(nameLabel)
                                .addComponent(emailLabel))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(logoutButton)
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(nameLabel)
                                .addComponent(emailLabel))
                        .addComponent(logoutButton)
        );

        return panel;
    }

    private JPanel createSubscriptionPlansPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Subscription Plans");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);

        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1.0;
        constraints.insets = new Insets(10, 10, 10, 10);

        List<SubscriptionPlan> plans = app.getAdminSubscriptionPlans();

        if (plans == null || plans.isEmpty()) {
            JLabel emptyLabel = new JLabel("No subscription plans available");
            emptyLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            emptyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            contentPanel.add(emptyLabel, constraints);
        } else {
            int columnCount = 2;
            int currentColumn = 0;
            int currentRow = 0;

            for (SubscriptionPlan plan : plans) {
                JPanel planPanel = createPlanPanel(plan);
                constraints.gridx = currentColumn;
                constraints.gridy = currentRow;
                contentPanel.add(planPanel, constraints);

                currentColumn++;
                if (currentColumn == columnCount) {
                    currentColumn = 0;
                    currentRow++;
                }
            }
        }

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(contentPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createPlanPanel(SubscriptionPlan plan) {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel planLabel = new JLabel(plan.getName());
        planLabel.setFont(new Font("Arial", Font.BOLD, 14));

        JLabel priceLabel = new JLabel("Price: $" + String.format("%.2f", plan.getPrice()));
        priceLabel.setFont(new Font("Arial", Font.PLAIN, 12));

        JLabel descripLabel = new JLabel("<html>" + plan.getDescription() + "</html>");
        descripLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        descripLabel.setMaximumSize(new Dimension(150, Integer.MAX_VALUE));

        JLabel dataLabel = new JLabel("Data: " + plan.getData() + "GB");
        dataLabel.setFont(new Font("Arial", Font.PLAIN, 12));

        JLabel talkTimeLabel = new JLabel("Talk Time: " + plan.getTalkTime() + " minutes");
        talkTimeLabel.setFont(new Font("Arial", Font.PLAIN, 12));

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.add(planLabel);
        infoPanel.add(priceLabel);
        infoPanel.add(dataLabel);
        infoPanel.add(talkTimeLabel);
        infoPanel.add(descripLabel);

        panel.add(infoPanel, BorderLayout.CENTER);

        JButton detailsButton = new JButton("More Details");
        detailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.subscriptionDetail(plan);
            }
        });

        panel.add(detailsButton, BorderLayout.SOUTH);

        return panel;
    }

    private JButton createAddPlanButton() {
        JButton button = new JButton("Add Plan");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.newPlanDetails();
            }
        });
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBackground(Color.decode("#ee6c4d"));
        button.setForeground(Color.WHITE);

        return button;
    }
}
