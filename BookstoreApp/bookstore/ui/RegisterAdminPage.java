package bookstore.ui;

import bookstore.utils.FileUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class RegisterAdminPage extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JFrame previousPage; // ✅ สำหรับเก็บหน้าที่เรียกมา

    // ✅ Constructor
    public RegisterAdminPage(JFrame previousPage) {
        this.previousPage = previousPage; // เก็บหน้าก่อนหน้าเพื่อนำกลับไป

        setTitle("📋 Register Admin");
        setSize(500, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 🌟 Header
        JLabel headerLabel = new JLabel("Create New Admin Account", JLabel.CENTER);
        headerLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        headerLabel.setForeground(new Color(0, 102, 204));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));

        // 📋 Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 10, 50));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 10, 15, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        confirmPasswordField = new JPasswordField(20);

        // Add fields with labels
        addField(formPanel, gbc, "Username:", usernameField, 0);
        addField(formPanel, gbc, "Password:", passwordField, 1);
        addField(formPanel, gbc, "Confirm Password:", confirmPasswordField, 2);

        // 🔘 Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton registerButton = createButton("Create Account", new Color(0, 153, 76));
        JButton backButton = createButton("Back", new Color(204, 0, 0));

        buttonPanel.add(registerButton);
        buttonPanel.add(backButton);

        // 🗂️ Add components to frame
        add(headerLabel, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Event Handlers
        registerButton.addActionListener(this::registerAdmin);
        backButton.addActionListener(e -> handleBackButton());

        getContentPane().setBackground(Color.WHITE);
        setVisible(true);
    }

    // 🔖 Method to Add Label and Field
    private void addField(JPanel panel, GridBagConstraints gbc, String label, JTextField field, int y) {
        gbc.gridx = 0;
        gbc.gridy = y;
        JLabel jLabel = new JLabel(label);
        jLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        panel.add(jLabel, gbc);

        gbc.gridx = 1;
        field.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        field.setPreferredSize(new Dimension(300, 35));
        panel.add(field, gbc);
    }

    // 🔘 Create Styled Button
    private JButton createButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(150, 45));
        return button;
    }

    // ✅ การทำงานของปุ่ม Back
    private void handleBackButton() {
        dispose();
        if (previousPage != null) {
            previousPage.setVisible(true);  // ✅ กลับไปหน้า Admin Dashboard
        } else {
            new LoginPage();               // ✅ กลับไปหน้า Login ถ้าไม่มีหน้า previousPage
        }
    }

    // ✅ Register Admin Logic
    private void registerAdmin(ActionEvent e) {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<String> users = FileUtils.readFile("data/users.txt");
        for (String user : users) {
            if (user.split("\\|")[1].equals(username)) {
                JOptionPane.showMessageDialog(this, "Username already exists.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        users.add("admin|" + username + "|" + password);
        FileUtils.writeFile("data/users.txt", users);

        JOptionPane.showMessageDialog(this, "Admin account created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

        handleBackButton();  // ✅ กลับไปหน้าเดิมหลังจากสมัครเสร็จ
    }
}
