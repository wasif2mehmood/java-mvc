package View;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomerLoginView extends LoginView {
    public CustomerLoginView(App app) {
        super(app); 

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = String.valueOf(passwordField.getPassword());

                if (app.validateCustomerLogin(username, password)!=null) {
                    app.customerDashboard(app.loadCustomer(app.validateCustomerLogin(username, password)));
                } else {
                    JOptionPane.showMessageDialog(CustomerLoginView.this,
                            "Incorrect username or password",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.openRegistrationView();
            }
        });

        JPanel buttonPanel = (JPanel) loginButton.getParent();
        buttonPanel.add(registerButton);
    }
}
