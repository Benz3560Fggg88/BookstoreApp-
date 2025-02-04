package bookstore.ui;

import bookstore.utils.FileUtils;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AddBookPage extends JFrame {
    private JTextField titleField;
    private JTextField quantityField;
    private JTextArea descriptionArea;
    private JFrame previousPage;

    public AddBookPage(JFrame previousPage) {
        this.previousPage = previousPage;
        setTitle("Add Book");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 2, 10, 10));

        JLabel titleLabel = new JLabel("Title:");
        JLabel quantityLabel = new JLabel("Quantity:");
        JLabel descriptionLabel = new JLabel("Description:");

        titleField = new JTextField();
        quantityField = new JTextField();
        descriptionArea = new JTextArea(4, 20);

        JButton addButton = new JButton("Add Book");
        JButton backButton = new JButton("Back");

        add(titleLabel);
        add(titleField);
        add(quantityLabel);
        add(quantityField);
        add(descriptionLabel);
        add(new JScrollPane(descriptionArea));
        add(addButton);
        add(backButton);

        addButton.addActionListener(e -> addBook());
        backButton.addActionListener(e -> {
            dispose();
            previousPage.setVisible(true);
        });

        setVisible(true);
    }

    private void addBook() {
        String title = titleField.getText();
        String quantity = quantityField.getText();
        String description = descriptionArea.getText();

        if (title.isEmpty() || quantity.isEmpty() || description.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<String> books = FileUtils.readFile("data/books.txt");
        books.add(title + "|" + quantity + "|" + description);
        FileUtils.writeFile("data/books.txt", books);

        JOptionPane.showMessageDialog(this, "Book added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

        dispose();
        previousPage.setVisible(true);
    }
}
