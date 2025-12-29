package org.example.test.gui;


import org.example.test.dao.TaiKhoanDAO;
import org.example.test.gui.MainFrame;
import org.example.test.gui.RegisterFrame;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private JTextField txtTaiKhoan;
    private JPasswordField txtMatKhau;
    private TaiKhoanDAO taiKhoanDAO;

    public LoginFrame() {
        taiKhoanDAO = new TaiKhoanDAO();
        initUI();
    }

    private void initUI() {
        // Sử dụng System Look and Feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        setTitle("Đăng Nhập - Quản Lý Phòng Trọ");
        setSize(450, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel chính với BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        mainPanel.setBackground(Color.WHITE);

        // Panel phía Bắc (tiêu đề)
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBackground(Color.WHITE);
        JLabel lblTitle = new JLabel("Đăng Nhập", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitle.setForeground(new Color(33, 150, 243));
        titlePanel.add(lblTitle);
        mainPanel.add(titlePanel, BorderLayout.NORTH);

        // Panel phía Trung tâm (form đăng nhập)
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        // Panel tài khoản với GridBagLayout để canh lề tốt hơn
        JPanel taiKhoanPanel = new JPanel(new GridBagLayout());
        taiKhoanPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();

        // Label tài khoản
        JLabel lblTaiKhoan = new JLabel("Tài khoản:");
        lblTaiKhoan.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 10, 10);
        taiKhoanPanel.add(lblTaiKhoan, gbc);

        // TextField tài khoản
        txtTaiKhoan = new JTextField(20);
        txtTaiKhoan.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtTaiKhoan.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        taiKhoanPanel.add(txtTaiKhoan, gbc);

        formPanel.add(taiKhoanPanel);
        formPanel.add(Box.createVerticalStrut(15));

        // Panel mật khẩu
        JPanel matKhauPanel = new JPanel(new GridBagLayout());
        matKhauPanel.setBackground(Color.WHITE);

        // Label mật khẩu
        JLabel lblMatKhau = new JLabel("Mật khẩu:");
        lblMatKhau.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        matKhauPanel.add(lblMatKhau, gbc);

        // TextField mật khẩu
        txtMatKhau = new JPasswordField(20);
        txtMatKhau.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtMatKhau.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        matKhauPanel.add(txtMatKhau, gbc);

        formPanel.add(matKhauPanel);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Panel phía Nam (các nút)
        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 0, 15));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));

        // Nút đăng nhập - lớn và nổi bật
        JButton btnLogin = new JButton("ĐĂNG NHẬP");
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnLogin.setForeground(Color.black);
        btnLogin.setBackground(new Color(33, 150, 243));
        btnLogin.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
        btnLogin.setFocusPainted(false);
        btnLogin.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
        btnLogin.addActionListener(e -> login());

        // Nút đăng ký - rõ ràng nhưng ít nổi bật hơn
        JButton btnRegister = new JButton("ĐĂNG KÝ");
        btnRegister.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnRegister.setForeground(Color.black);
        btnRegister.setBackground(new Color(52, 103, 83));
        btnRegister.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
        btnRegister.setFocusPainted(false);
        btnRegister.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRegister.addActionListener(e -> new RegisterFrame().setVisible(true));

        buttonPanel.add(btnLogin);
        buttonPanel.add(btnRegister);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel);
    }

    private void login() {
        String taiKhoan = txtTaiKhoan.getText();
        String matKhau = new String(txtMatKhau.getPassword());
        if (taiKhoan.isEmpty() || matKhau.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tài khoản và mật khẩu!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (taiKhoanDAO.login(taiKhoan, matKhau)) {
            JOptionPane.showMessageDialog(this, "Đăng nhập thành công!");
            dispose();
            new MainFrame().setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Tài khoản hoặc mật khẩu sai!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}