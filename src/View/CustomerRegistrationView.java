package View;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class CustomerRegistrationView extends JPanel {
    private JTextField usernameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JButton registerButton;
    private JButton cancelButton;
    private JCheckBox showPasswordCheckBox;
    App app;

    public CustomerRegistrationView(App app) {
        this.app = app;
        
        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel usernameLabel = new JLabel("Username:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(usernameLabel, gbc);

        usernameField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(usernameField, gbc);

        JLabel emailLabel = new JLabel("Email:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(emailLabel, gbc);

        emailField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(emailField, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(passwordLabel, gbc);

        passwordField = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(passwordField, gbc);

        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(confirmPasswordLabel, gbc);

        confirmPasswordField = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.gridy = 3;
        add(confirmPasswordField, gbc);

        showPasswordCheckBox = new JCheckBox("Show Password");
        showPasswordCheckBox.setBackground(Color.WHITE);
        gbc.gridx = 1;
        gbc.gridy = 4;
        showPasswordCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                int state = e.getStateChange();
                if (state == ItemEvent.SELECTED) {
                    passwordField.setEchoChar((char) 0); // Show password
                    confirmPasswordField.setEchoChar((char) 0);
                } else {
                    passwordField.setEchoChar('\u2022'); // Hide password with bullet character
                    confirmPasswordField.setEchoChar('\u2022');
                }
            }
        });
        add(showPasswordCheckBox, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.WHITE);

        registerButton = new JButton("Register");
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String email = emailField.getText();
                String password = String.valueOf(passwordField.getPassword());
                String confirmPassword = String.valueOf(confirmPasswordField.getPassword());

                boolean isValid = validateRegistration(username, email, password, confirmPassword);

                if (isValid) {
                    app.registerNewUser(username, email, password);
                    app.selectionPane();
                }
            }
        });
        buttonPanel.add(registerButton);

        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.selectionPane();
            }
        });
        buttonPanel.add(cancelButton);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = GridBagConstraints.REMAINDER; // Set gridwidth to REMAINDER
        add(buttonPanel, gbc);
    }

    private boolean validateRegistration(String username, String email, String password, String confirmPassword) {
        // Perform validation checks here
        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(CustomerRegistrationView.this,
                    "All fields are required",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(CustomerRegistrationView.this,
                    "Passwords do not match",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                    
            return false;
            
        } else{ 
            return !app.userAlreadyExists(username, email, password);}
    }
}
