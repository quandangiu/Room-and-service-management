package org.example.test.gui;

import org.example.test.dao.PhongDAO;
import org.example.test.model.Phong;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class PhongPanel extends JPanel {
    private JTextField txtMaPhong, txtTen, txtSoLuong, txtLoaiPhong, txtTimKiem;
    private JTable table;
    private DefaultTableModel tableModel;
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
    private List<PhongChangeListener> listeners = new ArrayList<>();

    public PhongPanel() {
        phongDAO = new PhongDAO();
        initUI();
        loadData();
    }

    // Thêm listener
    public void addPhongChangeListener(PhongChangeListener listener) {
        listeners.add(listener);
    }

    // Thông báo cho các listener khi danh sách phòng thay đổi
    private void firePhongListChanged() {
        for (PhongChangeListener listener : listeners) {
            listener.onPhongListChanged();
        }
    }

    // Làm mới dữ liệu khi chuyển tab
    public void reloadData() {
        loadData(); // Tải lại dữ liệu bảng
        clearForm(); // Làm sạch form
        firePhongListChanged(); // Thông báo cho các listener
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

        JLabel titleLabel = new JLabel("QUẢN LÝ PHÒNG ", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel, BorderLayout.CENTER);

        add(titlePanel, BorderLayout.NORTH);

        // Main Content Panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(backgroundColor);

        // Form Panel (Left)
        JPanel formPanel = createFormPanel();
        mainPanel.add(formPanel, BorderLayout.WEST);

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
                        "Thông Tin Phòng",
                        TitledBorder.DEFAULT_JUSTIFICATION,
                        TitledBorder.DEFAULT_POSITION,
                        headerFont,
                        primaryColor),
                BorderFactory.createEmptyBorder(15, 10, 15, 10)));
        formPanel.setBackground(Color.WHITE);
        formPanel.setPreferredSize(new Dimension(300, 400));

        // Mã Phòng
        JPanel panelMaPhong = createInputRow("Mã Phòng:", txtMaPhong = createTextField());
        formPanel.add(panelMaPhong);
        formPanel.add(Box.createVerticalStrut(15));

        // Tên Phòng
        JPanel panelTen = createInputRow("Tên Phòng:", txtTen = createTextField());
        formPanel.add(panelTen);
        formPanel.add(Box.createVerticalStrut(15));

        // Số Lượng Sinh Viên
        JPanel panelSoLuong = createInputRow("Số Lượng SV:", txtSoLuong = createTextField());
        txtSoLuong.setEditable(false); // Không cho chỉnh sửa
        formPanel.add(panelSoLuong);
        formPanel.add(Box.createVerticalStrut(15));

        // Loại Phòng
        JPanel panelLoaiPhong = createInputRow("Loại Phòng:", txtLoaiPhong = createTextField());
        formPanel.add(panelLoaiPhong);
        formPanel.add(Box.createVerticalStrut(15));

        // Add some glue to push everything to the top
        formPanel.add(Box.createVerticalGlue());

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
        String[] columns = {"Mã Phòng", "Tên Phòng", "Số Lượng SV", "Loại Phòng"};
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

        // Add listener for row selection
        table.getSelectionModel().addListSelectionListener(e -> hienThiChiTiet());

        // Add mouse listener for double-click action
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    hienThiChiTiet();
                }
                // click 2 cái đến xem danh sách sinh viên trong phòng
                if (e.getClickCount() == 2) {
                    int row = table.rowAtPoint(e.getPoint());
                    if (row >= 0) {
                        String maPhong = table.getValueAt(row, 0).toString();
                        String tenPhong = table.getValueAt(row, 1).toString();
                        String loaiPhong = table.getValueAt(row, 3).toString();
                        new SinhVienFrame(maPhong, tenPhong, loaiPhong).setVisible(true);
                    }
                }
            }
        });

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

        JButton btnThem = createActionButton("Thêm", primaryColor, e -> themPhong());
        JButton btnSua = createActionButton("Sửa", successColor, e -> suaPhong());
        JButton btnXoa = createActionButton("Xóa", accentColor, e -> xoaPhong());

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

        // Add hover effect
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
        List<Phong> list = phongDAO.getAll();
        for (Phong p : list) {
            tableModel.addRow(new Object[]{
                    p.getMaPhong(),
                    p.getTen(),
                    p.getSoLuong(),
                    p.getLoaiPhong()
            });
        }
    }

    private void themPhong() {
        if (!validateInput()) return;

        Phong phong = new Phong();
        phong.setMaPhong(txtMaPhong.getText());
        phong.setTen(txtTen.getText());
        phong.setSoLuong(0); // Số lượng sẽ được tính tự động
        phong.setLoaiPhong(txtLoaiPhong.getText());

        if (phongDAO.add(phong)) {
            JOptionPane.showMessageDialog(this, "Thêm phòng thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            loadData();
            clearForm();
            firePhongListChanged(); // Thông báo sau khi thêm
        } else {
            JOptionPane.showMessageDialog(this, "Lỗi khi thêm phòng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void suaPhong() {
        if (!validateInput()) return;

        Phong phong = new Phong();
        phong.setMaPhong(txtMaPhong.getText());
        phong.setTen(txtTen.getText());
        phong.setSoLuong(0); // Số lượng sẽ được tính tự động
        phong.setLoaiPhong(txtLoaiPhong.getText());

        if (phongDAO.update(phong)) {
            JOptionPane.showMessageDialog(this, "Sửa phòng thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            loadData();
            clearForm();
            firePhongListChanged(); // Thông báo sau khi sửa
        } else {
            JOptionPane.showMessageDialog(this, "Lỗi khi sửa phòng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void xoaPhong() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            String maPhong = table.getValueAt(row, 0).toString();

            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Bạn có chắc chắn muốn xóa phòng " + maPhong + "?",
                    "Xác nhận xóa",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );

            if (confirm == JOptionPane.YES_OPTION) {
                if (phongDAO.delete(maPhong)) {
                    JOptionPane.showMessageDialog(this, "Xóa phòng thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    loadData();
                    clearForm();
                    firePhongListChanged(); // Thông báo sau khi xóa
                } else {
                    JOptionPane.showMessageDialog(this, "Lỗi khi xóa phòng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn phòng để xóa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void timKiem() {
        String keyword = txtTimKiem.getText().trim();
        if (keyword.isEmpty()) {
            loadData();
            return;
        }

        tableModel.setRowCount(0);
        List<Phong> list = phongDAO.search(keyword);

        if (list.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy kết quả phù hợp!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        for (Phong p : list) {
            tableModel.addRow(new Object[]{
                    p.getMaPhong(),
                    p.getTen(),
                    p.getSoLuong(),
                    p.getLoaiPhong()
            });
        }
    }

    private void hienThiChiTiet() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            txtMaPhong.setText(table.getValueAt(row, 0).toString());
            txtTen.setText(table.getValueAt(row, 1).toString());
            txtSoLuong.setText(table.getValueAt(row, 2).toString());
            txtLoaiPhong.setText(table.getValueAt(row, 3).toString());
        }
    }

    private void clearForm() {
        txtMaPhong.setText("");
        txtTen.setText("");
        txtSoLuong.setText("");
        txtLoaiPhong.setText("");
        table.clearSelection();
    }

    private boolean validateInput() {
        if (txtMaPhong.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã phòng!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            txtMaPhong.requestFocus();
            return false;
        }

        if (txtTen.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên phòng!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            txtTen.requestFocus();
            return false;
        }

        if (txtLoaiPhong.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập loại phòng!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            txtLoaiPhong.requestFocus();
            return false;
        }

        return true;
    }
}