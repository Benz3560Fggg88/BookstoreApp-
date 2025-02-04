package bookstore.ui;

import javax.swing.*;
import java.awt.*;

public class BookDetailsPage extends JFrame {

    public BookDetailsPage(String bookData) {
        String[] parts = bookData.split("\\|");

        setTitle("Book Details");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JLabel titleLabel = new JLabel("Title: " + parts[0]);
        JLabel quantityLabel = new JLabel("Quantity: " + parts[1]);
        JTextArea descriptionArea = new JTextArea(parts[2]);
        descriptionArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(descriptionArea);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> dispose());

        panel.add(titleLabel);
        panel.add(quantityLabel);
        panel.add(scrollPane);
        panel.add(backButton);

        add(panel, BorderLayout.CENTER);
        setVisible(true);
    }
}
