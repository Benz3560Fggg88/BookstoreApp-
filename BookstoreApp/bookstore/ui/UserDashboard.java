package bookstore.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class UserDashboard extends JFrame {
    private JTable bookTable;
    private DefaultTableModel tableModel;

    public UserDashboard() {
        setTitle("📚 User Dashboard");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // จัดให้อยู่ตรงกลาง
        setLayout(new BorderLayout());

        // 🌟 Header Panel สำหรับจัดวางข้อความ User Dashboard และ Book List
        JPanel headerPanel = new JPanel(new GridLayout(2, 1));
        headerPanel.setBackground(Color.WHITE);

        // 🏷️ Header: User Dashboard
        JLabel dashboardLabel = new JLabel("User Dashboard", JLabel.CENTER);
        dashboardLabel.setFont(new Font("SansSerif", Font.BOLD, 26));
        dashboardLabel.setForeground(new Color(0, 102, 204));  // สีฟ้า
        dashboardLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 5, 0));

        // 🏷️ Header: Book List
        JLabel bookListLabel = new JLabel("Book List", JLabel.CENTER);
        bookListLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        bookListLabel.setForeground(new Color(0, 153, 76));  // สีเขียวอ่อน
        bookListLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 10, 0));

        // ✅ เพิ่ม Header ทั้งสองลงใน Panel
        headerPanel.add(dashboardLabel);
        headerPanel.add(bookListLabel);

        // 📋 Table Setup
        String[] columns = {"Title", "Quantity", "Description"};
        tableModel = new DefaultTableModel(columns, 0);
        bookTable = new JTable(tableModel);
        bookTable.setRowHeight(30);
        JScrollPane scrollPane = new JScrollPane(bookTable);

        // 🔄 Load Book Data from books.txt
        loadBooksFromFile();

        // 🔘 Button Panel
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JButton viewDetailsButton = createButton("View Details", new Color(0, 102, 204));
        JButton purchaseButton = createButton("Purchase", new Color(255, 153, 0));
        JButton logoutButton = createButton("Logout", new Color(204, 0, 0));

        buttonPanel.add(viewDetailsButton);
        buttonPanel.add(purchaseButton);
        buttonPanel.add(logoutButton);

        // 🎯 Event Handlers
        viewDetailsButton.addActionListener(e -> viewDetails());
        purchaseButton.addActionListener(e -> purchaseBook());
        logoutButton.addActionListener(e -> {
            dispose();
            new LoginPage();
        });

        // 📦 Add Components to Frame
        add(headerPanel, BorderLayout.NORTH);   // ✅ เพิ่ม Header Panel
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    // 📥 Load Books from books.txt
    private void loadBooksFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("data/books.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 3) {
                    String title = parts[0];
                    int quantity = Integer.parseInt(parts[1]);
                    String description = parts[2];
                    tableModel.addRow(new Object[]{title, quantity, description});
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading books from file.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // 👀 View Book Details
    private void viewDetails() {
        int selectedRow = bookTable.getSelectedRow();
        if (selectedRow != -1) {
            String title = (String) tableModel.getValueAt(selectedRow, 0);
            int quantity = (int) tableModel.getValueAt(selectedRow, 1);
            String description = (String) tableModel.getValueAt(selectedRow, 2);

            JOptionPane.showMessageDialog(this,
                    "📚 Title: " + title + "\n" +
                            "📦 Quantity: " + quantity + "\n" +
                            "📝 Description: " + description,
                    "Book Details", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a book to view details.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    // 🛒 Purchase Book
    private void purchaseBook() {
        int selectedRow = bookTable.getSelectedRow();
        if (selectedRow != -1) {
            String title = (String) tableModel.getValueAt(selectedRow, 0);
            int quantity = (int) tableModel.getValueAt(selectedRow, 1);

            String input = JOptionPane.showInputDialog(this, "Enter quantity to purchase:");
            if (input != null && !input.isEmpty()) {
                try {
                    int purchaseQty = Integer.parseInt(input);
                    if (purchaseQty > 0 && purchaseQty <= quantity) {
                        tableModel.setValueAt(quantity - purchaseQty, selectedRow, 1);
                        JOptionPane.showMessageDialog(this, "Purchase successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        saveBooksToFile();
                    } else {
                        JOptionPane.showMessageDialog(this, "Invalid quantity.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a book to purchase.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    // 💾 Save Updated Book Data to books.txt
    private void saveBooksToFile() {
        try (java.io.PrintWriter writer = new java.io.PrintWriter("data/books.txt")) {
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                String title = (String) tableModel.getValueAt(i, 0);
                int quantity = (int) tableModel.getValueAt(i, 1);
                String description = (String) tableModel.getValueAt(i, 2);
                writer.println(title + "|" + quantity + "|" + description);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving books to file.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // 🔘 Create Button with Custom Style
    private JButton createButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("SansSerif", Font.BOLD, 16));
        return button;
    }
}
