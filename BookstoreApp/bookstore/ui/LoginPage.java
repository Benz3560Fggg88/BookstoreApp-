package bookstore.ui;

import bookstore.model.AccountManager;

import javax.swing.*;
import java.awt.*;

public class LoginPage extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginPage() {
        setTitle("📚 Bookstore App - Login");
        setSize(500, 450);  
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // จัดให้อยู่ตรงกลางหน้าจอ
        setLayout(new BorderLayout());

        // 🌈 Header
        JLabel headerLabel = new JLabel("Welcome to Bookstore App", JLabel.CENTER);
        headerLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        headerLabel.setForeground(new Color(0, 102, 204));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0)); // ✅ ลดระยะห่างด้านล่าง

        // 📝 Form Panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50)); // ✅ ลดขอบเพื่อให้ปุ่มชิดขึ้น

        usernameField = new JTextField();
        passwordField = new JPasswordField();

        formPanel.add(createLabeledField("Username:", usernameField));
        formPanel.add(Box.createVerticalStrut(10)); // ✅ ลดระยะห่างระหว่างช่องกรอก
        formPanel.add(createLabeledField("Password:", passwordField));
        formPanel.add(Box.createVerticalStrut(20)); // ✅ ลดระยะห่างระหว่างช่องกรอกและปุ่ม

        // 🔘 Button Panel (ย้ายขึ้น)
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 100, 5, 100)); // ✅ ลดขอบเพื่อให้ปุ่มขึ้นสูง

        JButton loginButton = createButton("Login", new Color(0, 153, 76));
        JButton registerButton = createButton("Register User", new Color(0, 102, 204));
        JButton exitButton = createButton("Exit", new Color(204, 0, 0));

        // ✅ จัดเรียงปุ่มและเพิ่มระยะห่างเล็กน้อย
        buttonPanel.add(loginButton);
        buttonPanel.add(Box.createVerticalStrut(8));
        buttonPanel.add(registerButton);
        buttonPanel.add(Box.createVerticalStrut(8));
        buttonPanel.add(exitButton);

        // 🎯 Event Handlers
        loginButton.addActionListener(e -> login());
        registerButton.addActionListener(e -> {
            dispose();
            new RegisterPage();
        });
        exitButton.addActionListener(e -> System.exit(0));

        // 🎨 Adding components to Frame
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.add(formPanel);
        centerPanel.add(buttonPanel); // ✅ รวม Form Panel และ Button Panel ไว้ด้วยกัน

        add(headerLabel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);

        getContentPane().setBackground(Color.WHITE);
        setVisible(true);
    }

    // 🔖 Method สำหรับเพิ่ม Label + Field
    private JPanel createLabeledField(String label, JTextField textField) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel jLabel = new JLabel(label);
        jLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        jLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        textField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35)); // ✅ ปรับความสูงของช่องกรอก
        textField.setFont(new Font("SansSerif", Font.PLAIN, 16));

        panel.add(jLabel);
        panel.add(Box.createVerticalStrut(5)); // ✅ ลดระยะห่างระหว่าง Label และ Field
        panel.add(textField);

        return panel;
    }

    // 🔘 Create Styled Button
    private JButton createButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("SansSerif", Font.BOLD, 18)); // ✅ เพิ่มขนาดตัวอักษรในปุ่ม
        button.setAlignmentX(Component.CENTER_ALIGNMENT); // ✅ จัดปุ่มให้อยู่ตรงกลาง
        button.setMaximumSize(new Dimension(250, 45));    // ✅ เพิ่มขนาดปุ่มให้ใหญ่ขึ้น
        return button;
    }

    // ✅ Login Validation
    private void login() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String role = AccountManager.login(username, password);
        if (role.equals("admin")) {
            dispose();
            new AdminDashboard();
        } else if (role.equals("user")) {
            dispose();
            new UserDashboard();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
