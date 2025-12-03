package GroceryQueueSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GroceryLogin extends JFrame {

    public GroceryLogin() {
        setTitle("Grocery Management System");
        // Adjusted frame size
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // ===== Background Panel (Image 2) =====
        JPanel bgPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                // Ensure this path/filename is correct for your background image
                ImageIcon img = new ImageIcon("image0001.png"); // <-- background image

                int imgW = img.getIconWidth();
                int imgH = img.getIconHeight();
                int panelW = getWidth();
                int panelH = getHeight();

                // Centering image without stretching (Fill the panel by scaling)
                double scaleX = (double) panelW / imgW;
                double scaleY = (double) panelH / imgH;
                double scale = Math.max(scaleX, scaleY);

                int drawW = (int) (imgW * scale);
                int drawH = (int) (imgH * scale);
                int x = (panelW - drawW) / 2;
                int y = (panelH - drawH) / 2;

                g.drawImage(img.getImage(), x, y, drawW, drawH, this);
            }
        };
        bgPanel.setLayout(null);

        // ===== GLASS CARD (Login Box) =====
        JPanel loginBox = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(new Color(0, 0, 0, 60)); // shadow
                g2.fillRoundRect(5, 5, getWidth() - 10, getHeight() - 10, 25, 25);

                g2.setColor(new Color(255, 255, 255, 200)); // glass
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);

                super.paintComponent(g);
            }
        };

        loginBox.setOpaque(false);
        loginBox.setLayout(null);

        int cardWidth = 350;
        int cardHeight = 300; // Adjusted height for a more compact look

        // --- CENTER POSITION CALCULATION ---
        int frameWidth = 800; // Match setSize
        int frameHeight = 600; // Match setSize

        // Calculate centered X and Y
        int centerX = (frameWidth - cardWidth) / 2;
        int centerY = (frameHeight - cardHeight) / 2;

        loginBox.setBounds(
                centerX, // X (Centered)
                centerY, // Y (Centered)
                cardWidth,
                cardHeight
        );

        // ===== TITLE =====
        JLabel title = new JLabel("🛒 GROCERY MANAGEMENT SYSTEM", SwingConstants.CENTER);
        title.setBounds(10, 20, 330, 28);
        title.setFont(new Font("Segoe UI Emoji", Font.BOLD, 16));
        title.setForeground(new Color(40, 40, 40));

        // ===== USERNAME =====
        JLabel userLabel = new JLabel("USERNAME");
        userLabel.setBounds(30, 70, 200, 18); // Adjusted Y
        userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        JTextField username = new JTextField();
        username.setBounds(30, 90, 290, 38); // Adjusted Y
        username.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        username.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));

        // ===== PASSWORD =====
        JLabel passLabel = new JLabel("PASSWORD");
        passLabel.setBounds(30, 140, 200, 18); // Adjusted Y
        passLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        JPasswordField password = new JPasswordField();
        password.setBounds(30, 160, 290, 38); // Adjusted Y
        password.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        password.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));

        // ===== LOGIN BUTTON =====
        JButton loginBtn = new JButton("LOGIN");
        loginBtn.setBounds(30, 220, 290, 45); // Adjusted Y
        loginBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        loginBtn.setBackground(new Color(24, 133, 61));
        loginBtn.setForeground(new Color(24, 133,61));
        loginBtn.setFocusPainted(false);
        loginBtn.setBorder(BorderFactory.createEmptyBorder());
        // ... inside GroceryLogin constructor

        loginBtn.addActionListener(e -> {
            String user = username.getText();
            String pass = new String(password.getPassword());

            if (user.equals("admin") && pass.equals("123")) {
                // 1. Show success message (Optional)
                JOptionPane.showMessageDialog(null, "Login Successful!");

                // 2. Open the MainDashboard
                MainDashboard dashboard = new MainDashboard();
                dashboard.setVisible(true);

                // 3. Close the current Login window
                dispose();

            } else {
                JOptionPane.showMessageDialog(null, "Invalid Username or Password!");
            }
        });

      // ... rest of GroceryLogin constructor

        // ADD TO CARD
        loginBox.add(title);
        loginBox.add(userLabel);
        loginBox.add(username);
        loginBox.add(passLabel);
        loginBox.add(password);
        loginBox.add(loginBtn);

        // ADD TO BACKGROUND
        bgPanel.add(loginBox);
        setContentPane(bgPanel);
        setVisible(true);
    }

    public static void main(String[] args) {
        // Set system look and feel for a modern appearance
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        new GroceryLogin();
    }
}