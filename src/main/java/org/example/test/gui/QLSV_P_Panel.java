package org.example.test.gui;

import org.example.test.dao.QLSV_P_DAO;
import org.example.test.dao.PhongDAO;
import org.example.test.db.DBConnection;
import org.example.test.model.Phong;
import org.example.test.model.QLSV_P;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class QLSV_P_Panel extends JPanel implements PhongChangeListener {
    private JTextField txtMasv, txtSDT, txtTen, txtLop, txtNamSinh, txtGioiTinh, txtQueQuan, txtCuTru, txtTimKiem;
    private JComboBox<String> comboPhong;
    private JTable table;
    private DefaultTableModel tableModel;
    private QLSV_P_DAO qlsvDAO;
    private PhongDAO phongDAO;
    private Color primaryColor = new Color(41, 128, 185);
    private Color secondaryColor = new Color(52, 152, 219);
    private Color accentColor = new Color(231, 76, 60);
    private Color successColor = new Color(46, 204, 113);
    private Color warningColor = new Color(241, 196, 15);
    private Color backgroundColor = new Color(63, 115, 128);
    private Font labelFont = new Font("Segoe UI", Font.BOLD, 13);
    private Font textFont = new Font("Segoe UI", Font.PLAIN, 13);
    private Font headerFont = new Font("Segoe UI", Font.BOLD, 14);
    private Font buttonFont = new Font("Segoe UI", Font.BOLD, 14);

    public QLSV_P_Panel() {
        qlsvDAO = new QLSV_P_DAO();
        phongDAO = new PhongDAO();
        initUI();
        loadData();
        loadPhongComboBox();
    }

    public void reloadData() {
        loadData();
        loadPhongComboBox();
        clearForm();
    }

    private void initUI() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setBackground(backgroundColor);

        // Title Panel
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(primaryColor);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JLabel titleLabel = new JLabel("QUẢN LÝ SINH VIÊN", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel, BorderLayout.CENTER);

        add(titlePanel, BorderLayout.NORTH);

        // Main Content Panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(backgroundColor);

        // Form Panel (Left) - Now Scrollable
        JPanel formPanel = createFormPanel();
        JScrollPane formScrollPane = new JScrollPane(formPanel);
        formScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        formScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        formScrollPane.setBorder(null); // Remove default border to maintain design
        formScrollPane.setPreferredSize(new Dimension(300, 400)); // Match original preferred size
        mainPanel.add(formScrollPane, BorderLayout.WEST);

        // Table Panel (Center/Right)
        JPanel tablePanel = createTablePanel();
        mainPanel.add(tablePanel, BorderLayout.CENTER);

        add(mainPanel, BorderLayout.CENTER);

        // Bottom Button Panel
        JPanel bottomPanel = createButtonPanel();
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(primaryColor, 1, true),
                        "Thông Tin Sinh Viên",
                        TitledBorder.DEFAULT_JUSTIFICATION,
                        TitledBorder.DEFAULT_POSITION,
                        headerFont,
                        primaryColor),
                BorderFactory.createEmptyBorder(15, 10, 15, 10)));
        formPanel.setBackground(Color.WHITE);
        // Remove preferred size to allow dynamic height for scrolling
        // formPanel.setPreferredSize(new Dimension(300, 400));

        // Mã Sinh Viên
        JPanel panelMasv = createInputRow("Mã SV:", txtMasv = createTextField());
        formPanel.add(panelMasv);
        formPanel.add(Box.createVerticalStrut(15));

        // Tên Sinh Viên
        JPanel panelTen = createInputRow("Tên SV:", txtTen = createTextField());
        formPanel.add(panelTen);
        formPanel.add(Box.createVerticalStrut(15));

        // Số Điện Thoại
        JPanel panelSDT = createInputRow("SĐT:", txtSDT = createTextField());
        formPanel.add(panelSDT);
        formPanel.add(Box.createVerticalStrut(15));

        // Lớp
        JPanel panelLop = createInputRow("Lớp:", txtLop = createTextField());
        formPanel.add(panelLop);
        formPanel.add(Box.createVerticalStrut(15));

        // Năm Sinh
        JPanel panelNamSinh = createInputRow("Năm Sinh:", txtNamSinh = createTextField());
        formPanel.add(panelNamSinh);
        formPanel.add(Box.createVerticalStrut(15));

        // Giới Tính
        JPanel panelGioiTinh = createInputRow("Giới Tính:", txtGioiTinh = createTextField());
        formPanel.add(panelGioiTinh);
        formPanel.add(Box.createVerticalStrut(15));

        // Quê Quán
        JPanel panelQueQuan = createInputRow("Quê Quán:", txtQueQuan = createTextField());
        formPanel.add(panelQueQuan);
        formPanel.add(Box.createVerticalStrut(15));

        // Cư Trú
        JPanel panelCuTru = createInputRow("Cư Trú:", txtCuTru = createTextField());
        formPanel.add(panelCuTru);
        formPanel.add(Box.createVerticalStrut(15));

        // Phòng
        JPanel panelPhong = new JPanel();
        panelPhong.setLayout(new BoxLayout(panelPhong, BoxLayout.Y_AXIS));
        panelPhong.setBackground(Color.WHITE);
        panelPhong.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel lblPhong = new JLabel("Phòng:");
        lblPhong.setFont(labelFont);
        lblPhong.setForeground(primaryColor);
        panelPhong.add(lblPhong);
        panelPhong.add(Box.createVerticalStrut(5));
        comboPhong = new JComboBox<>();
        comboPhong.setFont(textFont);
        comboPhong.setMaximumSize(new Dimension(Integer.MAX_VALUE, comboPhong.getPreferredSize().height + 16));
        panelPhong.add(comboPhong);
        formPanel.add(panelPhong);
        formPanel.add(Box.createVerticalStrut(15));

        // Clear button
        JButton btnClear = new JButton("Làm Mới");
        btnClear.setFont(buttonFont);
        btnClear.setBackground(warningColor);
        btnClear.setForeground(Color.WHITE);
        btnClear.setFocusPainted(false);
        btnClear.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        btnClear.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnClear.addActionListener(e -> clearForm());

        JPanel clearPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        clearPanel.setBackground(Color.WHITE);
        clearPanel.add(btnClear);
        formPanel.add(clearPanel);
        formPanel.add(Box.createVerticalStrut(15));

        // Add glue to push content to top
        formPanel.add(Box.createVerticalGlue());

        return formPanel;
    }

    private JPanel createInputRow(String labelText, JTextField textField) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel label = new JLabel(labelText);
        label.setFont(labelFont);
        label.setForeground(primaryColor);

        panel.add(label);
        panel.add(Box.createVerticalStrut(5));
        panel.add(textField);

        return panel;
    }

    private JTextField createTextField() {
        JTextField field = new JTextField();
        field.setFont(textFont);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, field.getPreferredSize().height + 16));
        return field;
    }

    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout(0, 10));
        tablePanel.setBackground(backgroundColor);

        // Search Panel
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.X_AXIS));
        searchPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        searchPanel.setBackground(backgroundColor);

        txtTimKiem = new JTextField();
        txtTimKiem.setFont(textFont);
        txtTimKiem.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(170, 166, 166), 1, true),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));

        JButton btnTimKiem = new JButton("Tìm Kiếm");
        btnTimKiem.setFont(buttonFont);
        btnTimKiem.setBackground(warningColor);
        btnTimKiem.setForeground(Color.WHITE);
        btnTimKiem.setFocusPainted(false);
        btnTimKiem.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        btnTimKiem.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnTimKiem.addActionListener(e -> timKiem());

        searchPanel.add(txtTimKiem);
        searchPanel.add(Box.createHorizontalStrut(10));
        searchPanel.add(btnTimKiem);

        tablePanel.add(searchPanel, BorderLayout.NORTH);

        // Table
        String[] columns = {"Mã SV", "Tên SV", "SĐT", "Lớp", "Năm Sinh", "Giới Tính", "Quê Quán", "Cư Trú", "Phòng"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setFont(textFont);
        table.setRowHeight(30);
        table.setShowGrid(true);
        table.setGridColor(new Color(230, 230, 230));
        table.setSelectionBackground(secondaryColor);
        table.setSelectionForeground(Color.WHITE);

        // Customize table header
        JTableHeader header = table.getTableHeader();
        header.setFont(headerFont);
        header.setBackground(primaryColor);
        header.setForeground(Color.WHITE);
        header.setBorder(null);
        header.setPreferredSize(new Dimension(header.getWidth(), 40));

        // Center align cell contents
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        table.getSelectionModel().addListSelectionListener(e -> hienThiChiTiet());

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(primaryColor, 1));

        tablePanel.add(scrollPane, BorderLayout.CENTER);

        return tablePanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(backgroundColor);
        buttonPanel.setBorder(new EmptyBorder(10, 0, 0, 0));

        JButton btnThem = createActionButton("Thêm", primaryColor, e -> themSinhVien());
        JButton btnSua = createActionButton("Sửa", successColor, e -> suaSinhVien());
        JButton btnXoa = createActionButton("Xóa", accentColor, e -> xoaSinhVien());

        buttonPanel.add(btnThem);
        buttonPanel.add(btnSua);
        buttonPanel.add(btnXoa);

        return buttonPanel;
    }

    private JButton createActionButton(String text, Color bgColor, java.awt.event.ActionListener actionListener) {
        JButton button = new JButton(text);
        button.setFont(buttonFont);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addActionListener(actionListener);

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(bgColor.darker());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });

        return button;
    }

    private void loadData() {
        tableModel.setRowCount(0);
        List<QLSV_P> list = qlsvDAO.getAll();
        for (QLSV_P sv : list) {
            String maPhong = getMaPhongByMasv(sv.getMasv());
            tableModel.addRow(new Object[]{
                    sv.getMasv(),
                    sv.getTen(),
                    sv.getSDT(),
                    sv.getLop(),
                    sv.getNamsinh(),
                    sv.getGioitinh(),
                    sv.getQuequan(),
                    sv.getCutru(),
                    maPhong != null ? maPhong : "Chưa gán"
            });
        }
    }

    public void loadPhongComboBox() {
        comboPhong.removeAllItems();
        comboPhong.addItem("Chưa gán");
        List<Phong> phongList = phongDAO.getAll();
        for (Phong p : phongList) {
            comboPhong.addItem(p.getMaPhong());
        }
    }

    private String getMaPhongByMasv(String masv) {
        String query = "SELECT maphong FROM phong_sinhvien WHERE masv = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, masv);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("maphong");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void themSinhVien() {
        if (!validateInput()) return;

        QLSV_P sv = new QLSV_P();
        sv.setMasv(txtMasv.getText().trim());
        sv.setSDT(txtSDT.getText().trim());
        sv.setTen(txtTen.getText().trim());
        sv.setLop(txtLop.getText().trim());
        try {
            sv.setNamsinh(Integer.parseInt(txtNamSinh.getText().trim()));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Năm sinh phải là số!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            txtNamSinh.requestFocus();
            return;
        }
        sv.setGioitinh(txtGioiTinh.getText().trim());
        sv.setQuequan(txtQueQuan.getText().trim());
        sv.setCutru(txtCuTru.getText().trim());

        String insertQuery = "INSERT INTO qlsv_p (masv, SDT, ten, lop, namsinh, gioitinh, quequan, cutru) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(insertQuery)) {
            stmt.setString(1, sv.getMasv());
            stmt.setString(2, sv.getSDT());
            stmt.setString(3, sv.getTen());
            stmt.setString(4, sv.getLop());
            stmt.setInt(5, sv.getNamsinh());
            stmt.setString(6, sv.getGioitinh());
            stmt.setString(7, sv.getQuequan());
            stmt.setString(8, sv.getCutru());
            stmt.executeUpdate();

            String selectedPhong = (String) comboPhong.getSelectedItem();
            if (selectedPhong != null && !selectedPhong.equals("Chưa gán")) {
                String linkQuery = "INSERT INTO phong_sinhvien (maphong, masv) VALUES (?, ?)";
                try (PreparedStatement linkStmt = conn.prepareStatement(linkQuery)) {
                    linkStmt.setString(1, selectedPhong);
                    linkStmt.setString(2, sv.getMasv());
                    linkStmt.executeUpdate();
                }
            }

            JOptionPane.showMessageDialog(this, "Thêm sinh viên thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            loadData();
            clearForm();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi thêm sinh viên: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void suaSinhVien() {
        if (!validateInput()) return;

        QLSV_P sv = new QLSV_P();
        sv.setMasv(txtMasv.getText().trim());
        sv.setSDT(txtSDT.getText().trim());
        sv.setTen(txtTen.getText().trim());
        sv.setLop(txtLop.getText().trim());
        try {
            sv.setNamsinh(Integer.parseInt(txtNamSinh.getText().trim()));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Năm sinh phải là số!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            txtNamSinh.requestFocus();
            return;
        }
        sv.setGioitinh(txtGioiTinh.getText().trim());
        sv.setQuequan(txtQueQuan.getText().trim());
        sv.setCutru(txtCuTru.getText().trim());

        String updateQuery = "UPDATE qlsv_p SET SDT = ?, ten = ?, lop = ?, namsinh = ?, gioitinh = ?, quequan = ?, cutru = ? WHERE masv = ?";
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            try (PreparedStatement stmt = conn.prepareStatement(updateQuery)) {
                stmt.setString(1, sv.getSDT());
                stmt.setString(2, sv.getTen());
                stmt.setString(3, sv.getLop());
                stmt.setInt(4, sv.getNamsinh());
                stmt.setString(5, sv.getGioitinh());
                stmt.setString(6, sv.getQuequan());
                stmt.setString(7, sv.getCutru());
                stmt.setString(8, sv.getMasv());
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected == 0) {
                    JOptionPane.showMessageDialog(this, "Không tìm thấy sinh viên với mã " + sv.getMasv() + "!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    conn.rollback();
                    return;
                }
            }

            String selectedPhong = (String) comboPhong.getSelectedItem();
            String deleteLinkQuery = "DELETE FROM phong_sinhvien WHERE masv = ?";
            try (PreparedStatement deleteStmt = conn.prepareStatement(deleteLinkQuery)) {
                deleteStmt.setString(1, sv.getMasv());
                deleteStmt.executeUpdate();
            }

            if (selectedPhong != null && !selectedPhong.equals("Chưa gán")) {
                String insertLinkQuery = "INSERT INTO phong_sinhvien (maphong, masv) VALUES (?, ?)";
                try (PreparedStatement insertStmt = conn.prepareStatement(insertLinkQuery)) {
                    insertStmt.setString(1, selectedPhong);
                    insertStmt.setString(2, sv.getMasv());
                    insertStmt.executeUpdate();
                }
            }

            conn.commit();
            JOptionPane.showMessageDialog(this, "Sửa sinh viên thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            loadData();
            clearForm();
        } catch (SQLException e) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi sửa sinh viên: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (conn != null) conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void xoaSinhVien() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            String masv = table.getValueAt(row, 0).toString();

            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Bạn có chắc chắn muốn xóa sinh viên " + masv + "?",
                    "Xác nhận xóa",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );

            if (confirm == JOptionPane.YES_OPTION) {
                Connection conn = null;
                try {
                    conn = DBConnection.getConnection();
                    conn.setAutoCommit(false);

                    String deleteLinkQuery = "DELETE FROM phong_sinhvien WHERE masv = ?";
                    try (PreparedStatement deleteStmt = conn.prepareStatement(deleteLinkQuery)) {
                        deleteStmt.setString(1, masv);
                        deleteStmt.executeUpdate();
                    }

                    String deleteQuery = "DELETE FROM qlsv_p WHERE masv = ?";
                    try (PreparedStatement stmt = conn.prepareStatement(deleteQuery)) {
                        stmt.setString(1, masv);
                        int rowsAffected = stmt.executeUpdate();
                        if (rowsAffected == 0) {
                            JOptionPane.showMessageDialog(this, "Không tìm thấy sinh viên với mã " + masv + "!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                            conn.rollback();
                            return;
                        }
                    }

                    conn.commit();
                    JOptionPane.showMessageDialog(this, "Xóa sinh viên thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    loadData();
                    clearForm();
                } catch (SQLException e) {
                    try {
                        if (conn != null) conn.rollback();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Lỗi khi xóa sinh viên: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                } finally {
                    try {
                        if (conn != null) conn.setAutoCommit(true);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sinh viên để xóa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void timKiem() {
        String keyword = txtTimKiem.getText().trim();
        if (keyword.isEmpty()) {
            loadData();
            return;
        }

        tableModel.setRowCount(0);

        // Kiểm tra xem từ khóa có phải là mã phòng không
        boolean isMaPhong = false;
        List<Phong> phongList = phongDAO.getAll();
        for (Phong p : phongList) {
            if (p.getMaPhong().equalsIgnoreCase(keyword)) {
                isMaPhong = true;
                break;
            }
        }

        if (isMaPhong) {
            // Tìm kiếm theo mã phòng
            String query = "SELECT sv.* FROM qlsv_p sv " +
                    "JOIN phong_sinhvien ps ON sv.masv = ps.masv " +
                    "WHERE ps.maphong = ?";
            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, keyword);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    QLSV_P sv = new QLSV_P();
                    sv.setMasv(rs.getString("masv"));
                    sv.setSDT(rs.getString("SDT"));
                    sv.setTen(rs.getString("ten"));
                    sv.setLop(rs.getString("lop"));
                    sv.setNamsinh(rs.getInt("namsinh"));
                    sv.setGioitinh(rs.getString("gioitinh"));
                    sv.setQuequan(rs.getString("quequan"));
                    sv.setCutru(rs.getString("cutru"));
                    String maPhong = getMaPhongByMasv(sv.getMasv());
                    tableModel.addRow(new Object[]{
                            sv.getMasv(),
                            sv.getTen(),
                            sv.getSDT(),
                            sv.getLop(),
                            sv.getNamsinh(),
                            sv.getGioitinh(),
                            sv.getQuequan(),
                            sv.getCutru(),
                            maPhong != null ? maPhong : "Chưa gán"
                    });
                }
                if (tableModel.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(this, "Không tìm thấy sinh viên nào trong phòng " + keyword + "!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Lỗi khi tìm kiếm theo mã phòng: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            // Tìm kiếm theo tất cả các trường
            String query = "SELECT DISTINCT sv.* FROM qlsv_p sv " +
                    "LEFT JOIN phong_sinhvien ps ON sv.masv = ps.masv " +
                    "WHERE sv.masv LIKE ? OR sv.SDT LIKE ? OR sv.ten LIKE ? OR sv.lop LIKE ? " +
                    "OR CAST(sv.namsinh AS CHAR) LIKE ? OR sv.gioitinh LIKE ? " +
                    "OR sv.quequan LIKE ? OR sv.cutru LIKE ? OR ps.maphong LIKE ?";
            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query)) {
                String likePattern = "%" + keyword + "%";
                for (int i = 1; i <= 9; i++) {
                    stmt.setString(i, likePattern);
                }
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    QLSV_P sv = new QLSV_P();
                    sv.setMasv(rs.getString("masv"));
                    sv.setSDT(rs.getString("SDT"));
                    sv.setTen(rs.getString("ten"));
                    sv.setLop(rs.getString("lop"));
                    sv.setNamsinh(rs.getInt("namsinh"));
                    sv.setGioitinh(rs.getString("gioitinh"));
                    sv.setQuequan(rs.getString("quequan"));
                    sv.setCutru(rs.getString("cutru"));
                    String maPhong = getMaPhongByMasv(sv.getMasv());
                    tableModel.addRow(new Object[]{
                            sv.getMasv(),
                            sv.getTen(),
                            sv.getSDT(),
                            sv.getLop(),
                            sv.getNamsinh(),
                            sv.getGioitinh(),
                            sv.getQuequan(),
                            sv.getCutru(),
                            maPhong != null ? maPhong : "Chưa gán"
                    });
                }
                if (tableModel.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(this, "Không tìm thấy sinh viên nào!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Lỗi khi tìm kiếm: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void hienThiChiTiet() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            txtMasv.setText(table.getValueAt(row, 0).toString());
            txtTen.setText(table.getValueAt(row, 1).toString());
            txtSDT.setText(table.getValueAt(row, 2).toString());
            txtLop.setText(table.getValueAt(row, 3).toString());
            txtNamSinh.setText(table.getValueAt(row, 4).toString());
            txtGioiTinh.setText(table.getValueAt(row, 5).toString());
            txtQueQuan.setText(table.getValueAt(row, 6).toString());
            txtCuTru.setText(table.getValueAt(row, 7).toString());
            String maPhong = table.getValueAt(row, 8).toString();
            comboPhong.setSelectedItem(maPhong.equals("Chưa gán") ? "Chưa gán" : maPhong);
        }
    }

    private void clearForm() {
        txtMasv.setText("");
        txtTen.setText("");
        txtSDT.setText("");
        txtLop.setText("");
        txtNamSinh.setText("");
        txtGioiTinh.setText("");
        txtQueQuan.setText("");
        txtCuTru.setText("");
        comboPhong.setSelectedItem("Chưa gán");
        table.clearSelection();
    }

    private boolean validateInput() {
        if (txtMasv.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã sinh viên!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            txtMasv.requestFocus();
            return false;
        }

        if (txtTen.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên sinh viên!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            txtTen.requestFocus();
            return false;
        }

        if (txtSDT.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số điện thoại!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            txtSDT.requestFocus();
            return false;
        }

        // Kiểm tra định dạng SĐT
        if (!txtSDT.getText().trim().matches("\\d{10,15}")) {
            JOptionPane.showMessageDialog(this, "SĐT phải là số và có độ dài từ 10 đến 15 chữ số!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            txtSDT.requestFocus();
            return false;
        }

        if (txtLop.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập lớp!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            txtLop.requestFocus();
            return false;
        }

        if (txtNamSinh.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập năm sinh!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            txtNamSinh.requestFocus();
            return false;
        }

        try {
            int namSinh = Integer.parseInt(txtNamSinh.getText().trim());
            if (namSinh < 1900 || namSinh > 2025) {
                JOptionPane.showMessageDialog(this, "Năm sinh phải nằm trong khoảng 1900-2025!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                txtNamSinh.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Năm sinh phải là số!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            txtNamSinh.requestFocus();
            return false;
        }

        if (txtGioiTinh.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập giới tính!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            txtGioiTinh.requestFocus();
            return false;
        }

        return true;
    }

    @Override
    public void onPhongListChanged() {
        loadPhongComboBox();
    }
}