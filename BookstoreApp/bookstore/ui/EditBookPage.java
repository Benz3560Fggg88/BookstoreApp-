package bookstore.ui;

import bookstore.utils.FileUtils;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class EditBookPage extends JFrame {
    private JTextField titleField;
    private JTextField quantityField;
    private JTextArea descriptionArea;
    private JFrame previousPage;
    private String originalTitle;

    public EditBookPage(JFrame previousPage, String title, String quantity, String description) {
        this.previousPage = previousPage;
        this.originalTitle = title;

        setTitle("Edit Book");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 2, 10, 10));

        JLabel titleLabel = new JLabel("Title:");
        JLabel quantityLabel = new JLabel("Quantity:");
        JLabel descriptionLabel = new JLabel("Description:");

        titleField = new JTextField(title);
        quantityField = new JTextField(quantity);
        descriptionArea = new JTextArea(description, 4, 20);

        JButton saveButton = new JButton("Save Changes");
        JButton backButton = new JButton("Back");

        add(titleLabel);
        add(titleField);
        add(quantityLabel);
        add(quantityField);
        add(descriptionLabel);
        add(new JScrollPane(descriptionArea));
        add(saveButton);
        add(backButton);

        saveButton.addActionListener(e -> saveChanges());
        backButton.addActionListener(e -> {
            dispose();
            previousPage.setVisible(true);
        });

        setVisible(true);
    }

    private void saveChanges() {
        String newTitle = titleField.getText();
        String newQuantity = quantityField.getText();
        String newDescription = descriptionArea.getText();

        if (newTitle.isEmpty() || newQuantity.isEmpty() || newDescription.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<String> books = FileUtils.readFile("data/books.txt");
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).startsWith(originalTitle + "|")) {
                books.set(i, newTitle + "|" + newQuantity + "|" + newDescription);
                break;
            }
        }

        FileUtils.writeFile("data/books.txt", books);

        JOptionPane.showMessageDialog(this, "Book updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

        dispose();
        previousPage.setVisible(true);
    }
}
