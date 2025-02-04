package bookstore.ui;

import javax.swing.*;
import java.awt.*;

public class AdminDashboard extends JFrame {
    public AdminDashboard() {
        setTitle("📊 Admin Dashboard");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // จัดให้อยู่ตรงกลาง
        setLayout(new BorderLayout());

        // 🌟 Header
        JLabel headerLabel = new JLabel("Admin Dashboard", JLabel.CENTER);
        headerLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        headerLabel.setForeground(new Color(0, 102, 204));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        // 🔘 Button Panel
        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 15, 15)); // 4 ปุ่ม 1 คอลัมน์
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 20, 50)); // Padding รอบปุ่ม

        // ✅ ปุ่มต่าง ๆ พร้อมสีสันที่แตกต่าง
        JButton registerUserButton = createButton("Register User", new Color(0, 102, 204)); // น้ำเงิน
        JButton registerAdminButton = createButton("Register Admin", new Color(0, 153, 76));   // เขียว
        JButton bookManageButton = createButton("Book Manage", new Color(255, 153, 0));       // ส้ม
        JButton logoutButton = createButton("Logout", new Color(204, 0, 0));                  // แดง

        // 📦 เพิ่มปุ่มลงใน Panel
        buttonPanel.add(registerUserButton);
        buttonPanel.add(registerAdminButton);
        buttonPanel.add(bookManageButton);
        buttonPanel.add(logoutButton);

        // 🚀 Event Handlers
        registerUserButton.addActionListener(e -> {
            setVisible(false);               // ซ่อนหน้า AdminDashboard
            new RegisterPage(this);          // ✅ ส่งหน้า Dashboard ไปยัง RegisterPage
        });

        registerAdminButton.addActionListener(e -> {
            setVisible(false);               // ซ่อนหน้า AdminDashboard
            new RegisterAdminPage(this);     // ✅ ส่งหน้า Dashboard ไปยัง RegisterAdminPage
        });

        bookManageButton.addActionListener(e -> {
            dispose();                      // ปิดหน้า AdminDashboard
            new BookManagePage();            // ✅ เปิดหน้า Book Management
        });

        logoutButton.addActionListener(e -> {
            dispose();                      // ปิดหน้า AdminDashboard
            new LoginPage();                 // ✅ กลับไปหน้า Login
        });

        // 🎯 เพิ่ม Header และ Button Panel ลงใน Frame
        add(headerLabel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);

        getContentPane().setBackground(Color.WHITE); //  สีพื้นหลังขาว
        setVisible(true);
    }

    // 🔘 ฟังก์ชันสำหรับสร้างปุ่มที่มีสไตล์
    private JButton createButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("SansSerif", Font.BOLD, 16));
        button.setPreferredSize(new Dimension(200, 50));  // ✅ ขนาดปุ่มใหญ่ขึ้น
        return button;
    }
}
