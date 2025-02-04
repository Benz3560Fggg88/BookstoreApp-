package bookstore.ui;

import bookstore.utils.FileUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class BookManagePage extends JFrame {
    private JTable bookTable;
    private DefaultTableModel tableModel;

    public BookManagePage() {
        setTitle("📚 Book Management");
        setSize(700, 500);  
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 
        setLayout(new BorderLayout(10, 10)); 

        // 🏷️ Header
        JLabel headerLabel = new JLabel("Book Management", JLabel.CENTER);
        headerLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        headerLabel.setForeground(new Color(0, 102, 204));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0)); 

        // 📋 Table Setup
        String[] columns = {"Title", "Quantity", "Description"};
        tableModel = new DefaultTableModel(columns, 0);
        bookTable = new JTable(tableModel);
        bookTable.setRowHeight(30);
        bookTable.setFont(new Font("SansSerif", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(bookTable);

        loadBooks();

        // 🔘 Button Panel (ขยับขึ้นมาใกล้ตาราง)
        JPanel buttonPanel = new JPanel(new GridLayout(1, 5, 10, 0));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); 

        JButton addButton = createButton("Add Book", new Color(0, 153, 76));
        JButton deleteButton = createButton("Delete", new Color(204, 0, 0));
        JButton editButton = createButton("Edit Book", new Color(255, 153, 0));
        JButton detailsButton = createButton("Details", new Color(0, 102, 204));
        JButton backButton = createButton("Back", new Color(128, 128, 128));

        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(editButton);
        buttonPanel.add(detailsButton);
        buttonPanel.add(backButton);

        //  Event Handlers
        addButton.addActionListener(e -> {
            dispose();
            new AddBookPage(this);
        });

        deleteButton.addActionListener(this::deleteBook);
        editButton.addActionListener(this::editBook);
        detailsButton.addActionListener(this::viewBookDetails);

        backButton.addActionListener(e -> {
            dispose();
            new AdminDashboard();
        });

        // 🎯 Layout Adjustment (ย้ายปุ่มขึ้น)
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        centerPanel.add(buttonPanel, BorderLayout.SOUTH); // ✅ ปุ่มใกล้ตารางขึ้น

        add(headerLabel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);

        getContentPane().setBackground(Color.WHITE);
        setVisible(true);
    }

    // 🔘 Button Styling
    private JButton createButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(120, 40));  // ✅ ปรับขนาดปุ่ม
        return button;
    }

    // 📥 Load Books from File
    private void loadBooks() {
        List<String> books = FileUtils.readFile("data/books.txt");
        tableModel.setRowCount(0); 
        for (String book : books) {
            String[] bookData = book.split("\\|");
            if (bookData.length == 3) {
                tableModel.addRow(new Object[]{bookData[0], bookData[1], bookData[2]});
            }
        }
    }

    // ❌ Delete Book
    private void deleteBook(ActionEvent e) {
        int selectedRow = bookTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a book to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this book?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            String bookTitle = (String) tableModel.getValueAt(selectedRow, 0);
            List<String> books = FileUtils.readFile("data/books.txt");
            books.removeIf(book -> book.startsWith(bookTitle + "|"));
            FileUtils.writeFile("data/books.txt", books);

            loadBooks();
            JOptionPane.showMessageDialog(this, "Book deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // ✏️ Edit Book
    private void editBook(ActionEvent e) {
        int selectedRow = bookTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a book to edit.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String title = (String) tableModel.getValueAt(selectedRow, 0);
        String quantity = (String) tableModel.getValueAt(selectedRow, 1);
        String description = (String) tableModel.getValueAt(selectedRow, 2);

        dispose();
        new EditBookPage(this, title, quantity, description);
    }

    // 👀 View Book Details
    private void viewBookDetails(ActionEvent e) {
        int selectedRow = bookTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a book to view details.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String title = (String) tableModel.getValueAt(selectedRow, 0);
        String quantity = (String) tableModel.getValueAt(selectedRow, 1);
        String description = (String) tableModel.getValueAt(selectedRow, 2);

        JOptionPane.showMessageDialog(this,
                "📚 Title: " + title + "\n" +
                        "📦 Quantity: " + quantity + "\n" +
                        "📝 Description: " + description,
                "Book Details",
                JOptionPane.INFORMATION_MESSAGE);
    }
}
