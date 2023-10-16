package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginSelectionView extends JPanel {
    private JButton adminLoginButton;
    private JButton userLoginButton;
    protected App app;

    public LoginSelectionView(App app) {
        this.app = app;

        setLayout(new BorderLayout());

        adminLoginButton = new JButton("Admin Login");
        adminLoginButton.setBackground(Color.decode("#98c1d9"));
        adminLoginButton.setForeground(Color.BLACK);
        adminLoginButton.setFont(adminLoginButton.getFont().deriveFont(30f));

        userLoginButton = new JButton("User Login");
        userLoginButton.setBackground(Color.decode("#ee6c4d"));
        userLoginButton.setForeground(Color.WHITE);
        userLoginButton.setFont(userLoginButton.getFont().deriveFont(30f));

        adminLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openAdminLoginWindow();
            }
        });

        userLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openUserLoginWindow();
            }
        });

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 0, 0));
        buttonPanel.setOpaque(false);

        buttonPanel.add(adminLoginButton);
        buttonPanel.add(userLoginButton);

        add(buttonPanel, BorderLayout.CENTER);
    }

    private void openAdminLoginWindow() {
        app.adminLogin();
    }

    private void openUserLoginWindow() {
        app.customerLogin();
    }
}
