package View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminLoginView extends LoginView {
    public AdminLoginView(App app) {
        super(app); 
        
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = String.valueOf(passwordField.getPassword());

                if (username.equals("admin") && password.equals("admin")) {
                    app.adminDashboard();
                } else {
                    JOptionPane.showMessageDialog(AdminLoginView.this,
                            "Incorrect username or password",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}