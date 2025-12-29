package org.example.test.gui;

import org.example.test.dao.TaiKhoanDAO;
import org.example.test.model.TaiKhoan;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterFrame extends JFrame {
    private JTextField taiKhoanField;
    private JTextField emailField;
    private JPasswordField matKhauField;
    private JPasswordField xacNhanField;
    private JButton registerButton;
    private JButton backButton;
    private TaiKhoanDAO taiKhoanDAO;

    public RegisterFrame() {
        taiKhoanDAO = new TaiKhoanDAO();
        initUI();
    }

    private void initUI() {
        // Use System Look and Feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        setTitle("Đăng Ký - Quản Lý Phòng Trọ");
        setSize(450, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        mainPanel.setBackground(Color.WHITE);

        // Title panel
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBackground(Color.WHITE);
        JLabel lblTitle = new JLabel("Đăng Ký", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitle.setForeground(new Color(33, 150, 243));
        titlePanel.add(lblTitle);
        mainPanel.add(titlePanel, BorderLayout.NORTH);

        // Form panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        // Username panel
        JPanel taiKhoanPanel = new JPanel(new GridBagLayout());
        taiKhoanPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 10, 10);

        JLabel lblTaiKhoan = new JLabel("Tài khoản:");
        lblTaiKhoan.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        taiKhoanPanel.add(lblTaiKhoan, gbc);

        taiKhoanField = new JTextField(20);
        taiKhoanField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        taiKhoanField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        taiKhoanPanel.add(taiKhoanField, gbc);

        formPanel.add(taiKhoanPanel);
        formPanel.add(Box.createVerticalStrut(15));

        // Email panel
        JPanel emailPanel = new JPanel(new GridBagLayout());
        emailPanel.setBackground(Color.WHITE);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        emailPanel.add(lblEmail, gbc);

        emailField = new JTextField(20);
        emailField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        emailField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        emailPanel.add(emailField, gbc);

        formPanel.add(emailPanel);
        formPanel.add(Box.createVerticalStrut(15));

        // Password panel
        JPanel matKhauPanel = new JPanel(new GridBagLayout());
        matKhauPanel.setBackground(Color.WHITE);

        JLabel lblMatKhau = new JLabel("Mật khẩu:");
        lblMatKhau.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        matKhauPanel.add(lblMatKhau, gbc);

        matKhauField = new JPasswordField(20);
        matKhauField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        matKhauField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        matKhauPanel.add(matKhauField, gbc);

        formPanel.add(matKhauPanel);
        formPanel.add(Box.createVerticalStrut(15));

        // Confirm Password panel
        JPanel xacNhanPanel = new JPanel(new GridBagLayout());
        xacNhanPanel.setBackground(Color.WHITE);

        JLabel lblXacNhan = new JLabel("Xác nhận mật khẩu:");
        lblXacNhan.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        xacNhanPanel.add(lblXacNhan, gbc);

        xacNhanField = new JPasswordField(20);
        xacNhanField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        xacNhanField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        xacNhanPanel.add(xacNhanField, gbc);

        formPanel.add(xacNhanPanel);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 0, 15));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));

        // Register button
        registerButton = new JButton("ĐĂNG KÝ");
        registerButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        registerButton.setForeground(Color.BLACK);
        registerButton.setBackground(new Color(33, 150, 243));
        registerButton.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
        registerButton.setFocusPainted(false);
        registerButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        registerButton.addActionListener(e -> handleRegister());

        // Back button
        backButton = new JButton("QUAY LẠI");
        backButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        backButton.setForeground(Color.BLACK);
        backButton.setBackground(new Color(52, 103, 83));
        backButton.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
        backButton.setFocusPainted(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });

        buttonPanel.add(registerButton);
        buttonPanel.add(backButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel);
    }

    private void handleRegister() {
        String taiKhoan = taiKhoanField.getText().trim();
        String email = emailField.getText().trim();
        String matKhau = new String(matKhauField.getPassword()).trim();
        String xacNhan = new String(xacNhanField.getPassword()).trim();

        // Validation
        if (taiKhoan.isEmpty() || email.isEmpty() || matKhau.isEmpty() || xacNhan.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ tất cả các trường!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!matKhau.equals(xacNhan)) {
            JOptionPane.showMessageDialog(this, "Mật khẩu xác nhận không khớp!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            JOptionPane.showMessageDialog(this, "Định dạng email không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Create TaiKhoan object
        TaiKhoan tk = new TaiKhoan();
        tk.setTaiKhoan(taiKhoan);
        tk.setEmail(email);
        tk.setMatKhau(matKhau);
        tk.setXacNhan(xacNhan);

        // Register
        if (taiKhoanDAO.register(tk)) {
            JOptionPane.showMessageDialog(this, "Đăng ký thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            new LoginFrame().setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Đăng ký thất bại. Tài khoản có thể đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RegisterFrame().setVisible(true));
    }
}