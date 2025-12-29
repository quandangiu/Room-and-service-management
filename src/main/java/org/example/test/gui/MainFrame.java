package org.example.test.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainFrame extends JFrame {
    public MainFrame() {
        // Sử dụng System Look and Feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        setTitle("Quản Lý Phòng Trọ");
        setSize(900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Tạo JTabbedPane để chứa các panel
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tabbedPane.setBackground(Color.WHITE);

        // Tạo các panel
        PhongPanel phongPanel = new PhongPanel();
        QLSV_P_Panel qlsvPanel = new QLSV_P_Panel();
        DichVuPanel dichVuPanel = new DichVuPanel();

        // Đăng ký QLSV_P_Panel làm listener cho PhongPanel
        phongPanel.addPhongChangeListener(qlsvPanel);

        // Thêm các panel vào tab
        tabbedPane.addTab("Quản Lý Phòng", phongPanel);
        tabbedPane.addTab("Quản Lý Sinh Viên", qlsvPanel);
        tabbedPane.addTab("Quản Lý Dịch Vụ", dichVuPanel);

        // Thêm ChangeListener để reload dữ liệu khi chuyển tab
        tabbedPane.addChangeListener(e -> {
            Component selectedComponent = tabbedPane.getSelectedComponent();
            if (selectedComponent instanceof PhongPanel) {
                ((PhongPanel) selectedComponent).reloadData();
            }

            else if (selectedComponent instanceof QLSV_P_Panel) {
                ((QLSV_P_Panel) selectedComponent).reloadData();
            }
//            else if (selectedComponent instanceof DichVuPanel) {
//                ((DichVuPanel) selectedComponent).reloadData();
//            }
        });

        // Panel chính với viền
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.add(tabbedPane, BorderLayout.CENTER);

        add(mainPanel, BorderLayout.CENTER);

        // Thanh tiêu đề
        JLabel lblTitle = new JLabel("Hệ Thống Quản Lý Kí Túc Xá", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitle.setForeground(new Color(33, 150, 243));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(lblTitle, BorderLayout.NORTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}