package GroceryQueueSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

public class GroceryQueueSystem extends JFrame {
    private final QueueManager manager;
    private final JTextField idField;
    private final JTextField nameField;
    private final JTextField itemsField;
    private final JTextField billField;

    private boolean isEditing = false;           // Track if update is in editing mode
    private int editingCustomerId = -1;          // Store the ID being edited

    // Added Cancel Edit button
    private final JButton cancelEditBtn;

    public GroceryQueueSystem() {
        manager = new QueueManager();
        setTitle("Grocery Queue Management System");
        setSize(900, 550);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // --- Background Image ---
        File bg = new File("Gemini_Generated_Image_zco46izco46izco4.png");
        JLabel bgLabel;
        if (bg.exists()) {
            ImageIcon bgIcon = new ImageIcon(
                    new ImageIcon(bg.getAbsolutePath())
                            .getImage()
                            .getScaledInstance(900, 550, Image.SCALE_SMOOTH)
            );
            bgLabel = new JLabel(bgIcon);
        } else {
            bgLabel = new JLabel();
            bgLabel.setBackground(new Color(240, 240, 240));
            bgLabel.setOpaque(true);
        }
        bgLabel.setLayout(new GridBagLayout());
        setContentPane(bgLabel);

        // --- Title Label ---
        JLabel title = new JLabel(" Grocery Queue Management System");
        title.setFont(new Font("Poppins", Font.BOLD, 28)); // Modern font
        title.setForeground(Color.WHITE);
        title.setHorizontalAlignment(SwingConstants.CENTER);

        // --- Transparent Form Panel ---
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(255, 255, 255, 220));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        formPanel.setPreferredSize(new Dimension(400, 260));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel idLabel = new JLabel("Customer ID:");
        JLabel nameLabel = new JLabel("Customer Name:");
        JLabel itemsLabel = new JLabel("Total Items:");
        JLabel billLabel = new JLabel("Total Bill (Rs):");

        Font labelFont = new Font("Poppins", Font.BOLD, 15);
        idLabel.setFont(labelFont);
        nameLabel.setFont(labelFont);
        itemsLabel.setFont(labelFont);
        billLabel.setFont(labelFont);

        idField = new JTextField(12);
        nameField = new JTextField(12);
        itemsField = new JTextField(12);
        billField = new JTextField(12);

        Font tfFont = new Font("Roboto", Font.PLAIN, 14);
        idField.setFont(tfFont);
        nameField.setFont(tfFont);
        itemsField.setFont(tfFont);
        billField.setFont(tfFont);

        // --- Add Labels & TextFields ---
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(idLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(idField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(nameLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(itemsLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(itemsField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(billLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(billField, gbc);

        // --- Button Panel ---
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        btnPanel.setOpaque(false);

        JButton backBtn = createStyledButton("Back", "go-back.png");
        JButton addBtn = createStyledButton("Add", "image-gallery.png");
        JButton removeBtn = createStyledButton("Delete", "delete.png");
        JButton updateBtn = createStyledButton("Update", "refresh.png");
        cancelEditBtn = createStyledButton("Cancel Edit", "cancel.png");
        cancelEditBtn.setVisible(false);  // initially hidden

        btnPanel.add(backBtn);
        btnPanel.add(addBtn);
        btnPanel.add(removeBtn);
        btnPanel.add(updateBtn);
        btnPanel.add(cancelEditBtn);

        // --- Layout Combination ---
        GridBagConstraints layoutGbc = new GridBagConstraints();
        layoutGbc.gridx = 0;
        layoutGbc.gridy = 0;
        layoutGbc.insets = new Insets(10, 10, 20, 10);
        bgLabel.add(title, layoutGbc);

        layoutGbc.gridy = 1;
        bgLabel.add(formPanel, layoutGbc);

        layoutGbc.gridy = 2;
        bgLabel.add(btnPanel, layoutGbc);

        // --- Button Actions ---
        addBtn.addActionListener(this::enqueueCustomer);
        removeBtn.addActionListener(this::dequeueCustomer);
        updateBtn.addActionListener(this::updateCustomer);
        cancelEditBtn.addActionListener(e -> cancelEditing());

        // 🔙 Back Button Action — Go to Dashboard
        backBtn.addActionListener(e -> {
            dispose();
            new MainDashboard().setVisible(true);
        });
    }

    // --- Create Button Helper ---
    private JButton createStyledButton(String text, String iconPath) {
        ImageIcon icon = loadIcon(iconPath);
        JButton button = (icon != null) ? new JButton(text, icon) : new JButton(text);
        button.setFont(new Font("Montserrat", Font.BOLD, 14));
        button.setBackground(new Color(255, 255, 255, 230));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180), 1));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(120, 35));
        return button;
    }

    private ImageIcon loadIcon(String path) {
        File f = new File(path);
        if (!f.exists()) return null;
        Image img = new ImageIcon(path).getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }

    // --- Queue Operations ---
    private void enqueueCustomer(ActionEvent e) {
        try {
            int id = Integer.parseInt(idField.getText().trim());
            String name = nameField.getText().trim();
            int items = Integer.parseInt(itemsField.getText().trim());
            double bill = Double.parseDouble(billField.getText().trim());

            Customer c = new Customer(id, name, items, bill);
            manager.enqueue(c);
            JOptionPane.showMessageDialog(this, "✅ Customer added!");
            clearFields();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "⚠️ Invalid input. Please check fields.");
        }
    }

    private void dequeueCustomer(ActionEvent e) {
        if (manager.isEmpty()) {
            JOptionPane.showMessageDialog(this, "⚠️ Queue is empty!");
            return;
        }
        Customer c = manager.dequeue();
        JOptionPane.showMessageDialog(this, "🗑️ Removed: " + c);
    }

    private void updateCustomer(ActionEvent e) {
        try {
            if (!isEditing) {
                // First click: load customer data into fields
                int id = Integer.parseInt(idField.getText().trim());
                Customer c = manager.getCustomerById(id);

                if (c != null) {
                    nameField.setText(c.getName());
                    itemsField.setText(String.valueOf(c.getTotalItems()));
                    billField.setText(String.valueOf(c.getTotalBill()));
                    isEditing = true;
                    editingCustomerId = id;

                    idField.setEnabled(false);         // Disable ID editing during update
                    cancelEditBtn.setVisible(true);    // Show cancel edit button

                    JOptionPane.showMessageDialog(this, "Edit the fields and click Update again to save changes.");
                } else {
                    JOptionPane.showMessageDialog(this, "Customer with ID " + id + " not found.");
                }
            } else {
                // Second click: save changes
                String name = nameField.getText().trim();
                int items = Integer.parseInt(itemsField.getText().trim());
                double bill = Double.parseDouble(billField.getText().trim());

                boolean updated = manager.updateCustomer(editingCustomerId, name, items, bill);
                if (updated) {
                    JOptionPane.showMessageDialog(this, "✅ Customer updated!");
                    clearFields();
                    resetEditingState();
                } else {
                    JOptionPane.showMessageDialog(this, "❌ Failed to update customer.");
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "⚠️ Invalid input! Please check the fields.");
        }
    }

    private void cancelEditing() {
        clearFields();
        resetEditingState();
        JOptionPane.showMessageDialog(this, "Editing cancelled.");
    }

    private void resetEditingState() {
        isEditing = false;
        editingCustomerId = -1;
        idField.setEnabled(true);
        cancelEditBtn.setVisible(false);
    }

    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        itemsField.setText("");
        billField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GroceryQueueSystem().setVisible(true));
    }
}
