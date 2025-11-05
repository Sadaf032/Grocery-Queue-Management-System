package GroceryQueueSystem;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.awt.event.ActionEvent;
import java.util.List;

public class MainDashboard extends JFrame {
    private final QueueManager manager;

    public MainDashboard() {
        manager = new QueueManager();
        setTitle("Grocery Management Dashboard");
        setSize(900, 550);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // --- Background Image ---
        File bg = new File("Gemini_Generated_Image_vl6xz7vl6xz7vl6x (1).png");
        JLabel bgLabel;

        if (bg.exists()) {
            ImageIcon bgIcon = new ImageIcon(
                    new ImageIcon(bg.getPath()).getImage().getScaledInstance(900, 550, Image.SCALE_SMOOTH)
            );
            bgLabel = new JLabel(bgIcon);
        } else {
            bgLabel = new JLabel();
            bgLabel.setBackground(new Color(240, 240, 240));
            bgLabel.setOpaque(true);
        }

        bgLabel.setLayout(new BorderLayout());
        setContentPane(bgLabel);


        // --- Bottom Panel Container ---
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 30, 30, 30)); // bottom padding

        // --- Left Buttons Panel (with padding) ---
        JPanel queueButtonsPanel = new JPanel();
        queueButtonsPanel.setLayout(new BoxLayout(queueButtonsPanel, BoxLayout.Y_AXIS));
        queueButtonsPanel.setOpaque(false);

        JButton manageQueueBtn = createStyledButton("Manage Queue", "profile (1).png");
        JButton viewQueueBtn = createStyledButton("View Queue", "view.png");

        queueButtonsPanel.add(manageQueueBtn);
        queueButtonsPanel.add(Box.createVerticalStrut(15));
        queueButtonsPanel.add(viewQueueBtn);

        // Wrap left buttons panel inside a FlowLayout with left alignment and 30px horizontal gap for padding
        JPanel leftWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 30, 0));
        leftWrapper.setOpaque(false);
        leftWrapper.add(queueButtonsPanel);

        // --- Right Buttons Panel (with padding) ---
        JPanel rightButtonsPanel = new JPanel();
        rightButtonsPanel.setLayout(new BoxLayout(rightButtonsPanel, BoxLayout.Y_AXIS));
        rightButtonsPanel.setOpaque(false);

        JButton aboutBtn = createStyledButton("About", "info.png");
        JButton exitBtn = createStyledButton("Exit", "switch.png");

        rightButtonsPanel.add(aboutBtn);
        rightButtonsPanel.add(Box.createVerticalStrut(15));
        rightButtonsPanel.add(exitBtn);

        // Wrap right buttons panel inside a FlowLayout with right alignment and 30px horizontal gap for padding
        JPanel rightWrapper = new JPanel(new FlowLayout(FlowLayout.RIGHT, 30, 0));
        rightWrapper.setOpaque(false);
        rightWrapper.add(rightButtonsPanel);

        // Add wrapped panels to bottomPanel
        bottomPanel.add(leftWrapper, BorderLayout.WEST);
        bottomPanel.add(rightWrapper, BorderLayout.EAST);

        // Add bottomPanel to the main container
        bgLabel.add(bottomPanel, BorderLayout.SOUTH);

        // --- Button Actions ---
        manageQueueBtn.addActionListener(this::openQueueSystem);
        viewQueueBtn.addActionListener(this::openQueueView);
        aboutBtn.addActionListener(_ ->
                JOptionPane.showMessageDialog(
                        this,
                        "📦 Grocery Queue System\nDeveloped by Sadaf\nJava + Swing GUI Project",
                        "About",
                        JOptionPane.INFORMATION_MESSAGE
                )
        );
        exitBtn.addActionListener(_ -> {
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to exit?",
                    "Exit Confirmation",
                    JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
    }

    // --- Styled Button Method ---
    private JButton createStyledButton(String text, String iconPath) {
        ImageIcon icon = loadIcon(iconPath);
        JButton button = (icon != null) ? new JButton(text, icon) : new JButton(text);

        button.setFont(new Font("Poppins", Font.BOLD, 14));
        button.setForeground(Color.BLACK);
        button.setBackground(Color.WHITE);
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setHorizontalTextPosition(SwingConstants.RIGHT);
        button.setIconTextGap(10);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        return button;
    }

    private void openQueueSystem(ActionEvent e) {
        GroceryQueueSystem queueSystemWindow = new GroceryQueueSystem();
        queueSystemWindow.setVisible(true);
        // this.dispose();  // Optional: Close dashboard if needed
    }

    private void openQueueView(ActionEvent e) {
        List<Customer> customers = manager.getAllCustomers();

        if (customers == null || customers.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "🕓 The queue is currently empty.",
                    "Queue Status",
                    JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }

        JTextArea textArea = new JTextArea(12, 40);
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));

        StringBuilder sb = new StringBuilder();
        sb.append("📋 Current Queue:\n---------------------------\n");

        for (Customer c : customers) {
            sb.append("ID: ").append(c.getId())
                    .append(" | Name: ").append(c.getName())
                    .append(" | Items: ").append(c.getTotalItems())
                    .append(" | Bill: ₹").append(c.getTotalBill())
                    .append("\n");
        }

        textArea.setText(sb.toString());

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(500, 300));

        JOptionPane.showMessageDialog(
                this,
                scrollPane,
                "Queue Details",
                JOptionPane.PLAIN_MESSAGE
        );
    }

    private ImageIcon loadIcon(String path) {
        File f = new File(path);
        if (!f.exists()) return null;

        Image img = new ImageIcon(path).getImage().getScaledInstance(28, 28, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }

    // --- Main method ---
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainDashboard dashboard = new MainDashboard();
            dashboard.setVisible(true);
        });
    }
}
