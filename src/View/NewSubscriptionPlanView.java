package View;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewSubscriptionPlanView extends JPanel {
    private JTextField nameField;
    private JTextField dataField;
    private JTextField talkTimeField;
    private JTextArea descriptionArea;
    private JButton addButton;
    private JButton cancelButton;
    public NewSubscriptionPlanView(App app) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Create New Subscription Plan");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);

        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(Color.WHITE);
        GroupLayout layout = new GroupLayout(contentPanel);
        contentPanel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        JLabel nameLabel = new JLabel("Name:");
        JLabel dataLabel = new JLabel("Data:");
        JLabel talkTimeLabel = new JLabel("Talk Time:");
        JLabel descriptionLabel = new JLabel("Description:");

        nameField = new JTextField(20);
        dataField = new JTextField(20);
        talkTimeField = new JTextField(20);
        descriptionArea = new JTextArea(5, 20);
        JScrollPane descriptionScrollPane = new JScrollPane(descriptionArea);

        addButton = new JButton("Add");
        addButton.setBackground(Color.decode("#4CAF50"));
        addButton.setForeground(Color.WHITE);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String data = dataField.getText();
                String talkTime = talkTimeField.getText();
                String description = descriptionArea.getText();

                // Perform input validation here
                if (name.isEmpty() || data.isEmpty() || talkTime.isEmpty() || description.isEmpty()) {
                    JOptionPane.showMessageDialog(NewSubscriptionPlanView.this,
                            "Please fill in all the fields.", "Incomplete Fields", JOptionPane.ERROR_MESSAGE);
                } else if (!isNumeric(data) || !isNumeric(talkTime)) {
                    JOptionPane.showMessageDialog(NewSubscriptionPlanView.this,
                            "Data and Talk Time must be numeric values.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                } else {
                    app.addNewSubscriptionPlan(name, Integer.parseInt(data), Integer.parseInt(talkTime), description);
                    
                    nameField.setText("");
                    dataField.setText("");
                    talkTimeField.setText("");
                    descriptionArea.setText("");

                    JOptionPane.showMessageDialog(NewSubscriptionPlanView.this,
                            "New subscription plan added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        cancelButton = new JButton("Cancel");
        cancelButton.setBackground(Color.decode("#F44336"));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.adminDashboard();
            }
        });

        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(nameLabel)
                                .addComponent(dataLabel)
                                .addComponent(talkTimeLabel)
                                .addComponent(descriptionLabel))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(nameField)
                                .addComponent(dataField)
                                .addComponent(talkTimeField)
                                .addComponent(descriptionScrollPane))
        );

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(nameLabel)
                                .addComponent(nameField))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(dataLabel)
                                .addComponent(dataField))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(talkTimeLabel)
                                .addComponent(talkTimeField))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(descriptionLabel)
                                .addComponent(descriptionScrollPane))
        );

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(addButton);
        buttonPanel.add(cancelButton);

        add(titleLabel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }
}
