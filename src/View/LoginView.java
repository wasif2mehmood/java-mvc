package View;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class LoginView extends JPanel {
    protected JTextField usernameField;
    protected JPasswordField passwordField;
    protected JButton loginButton;
    protected JButton cancelButton;
    protected JCheckBox showPasswordCheckBox;
    protected App app;

    public LoginView(App app) {
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

        JLabel passwordLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(passwordLabel, gbc);

        passwordField = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(passwordField, gbc);

        showPasswordCheckBox = new JCheckBox("Show Password");
        showPasswordCheckBox.setBackground(Color.WHITE);
        gbc.gridx = 1;
        gbc.gridy = 2;
        showPasswordCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                int state = e.getStateChange();
                if (state == ItemEvent.SELECTED) {
                    passwordField.setEchoChar((char) 0); // Show password
                } else {
                    passwordField.setEchoChar('\u2022'); // Hide password with bullet character
                }
            }
        });
        add(showPasswordCheckBox, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.WHITE);

        loginButton = new JButton("Login");
        loginButton.setBackground(Color.decode("#ee6c4d"));
        loginButton.setForeground(Color.WHITE);
        buttonPanel.add(loginButton);

        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.selectionPane();
            }
        });
        cancelButton.setBackground(Color.decode("#3d5a80"));
        cancelButton.setForeground(Color.WHITE);
        buttonPanel.add(cancelButton);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = GridBagConstraints.REMAINDER; // Set gridwidth to REMAINDER
        add(buttonPanel, gbc);
    }
}
