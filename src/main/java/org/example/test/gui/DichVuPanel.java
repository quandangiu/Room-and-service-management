package org.example.test.gui;

import org.example.test.dao.DichVuDAO;
import org.example.test.model.DichVu;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.List;

public class DichVuPanel extends JPanel {
    private JTextField txtMaDV, txtTenDV, txtSoLuong, txtGia, txtTimKiem;
    private JSpinner spNgayN, spNgayX;
    private JTable table;
    private DefaultTableModel tableModel;
    private DichVuDAO dichVuDAO;
    private JButton btnUploadImage;
    private JLabel lblImage;
    private String selectedImagePath;
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

    public DichVuPanel() {
        dichVuDAO = new DichVuDAO();
        initUI();
        loadData();
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

        JLabel titleLabel = new JLabel("QUẢN LÝ DỊCH VỤ VinMart", SwingConstants.CENTER);
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
                        "Thông Tin Dịch Vụ",
                        TitledBorder.DEFAULT_JUSTIFICATION,
                        TitledBorder.DEFAULT_POSITION,
                        headerFont,
                        primaryColor),
                BorderFactory.createEmptyBorder(15, 10, 15, 10)));
        formPanel.setBackground(Color.WHITE);
        // Remove preferred size to allow dynamic height for scrolling
        // formPanel.setPreferredSize(new Dimension(300, 400));

        // Mã Dịch Vụ
        JPanel panelMaDV = createInputRow("Mã DV:", txtMaDV = createTextField());
        formPanel.add(panelMaDV);
        formPanel.add(Box.createVerticalStrut(15));

        // Tên Dịch Vụ
        JPanel panelTenDV = createInputRow("Tên DV:", txtTenDV = createTextField());
        formPanel.add(panelTenDV);
        formPanel.add(Box.createVerticalStrut(15));

        // Ngày Nhập
        JPanel panelNgayN = new JPanel();
        panelNgayN.setLayout(new BoxLayout(panelNgayN, BoxLayout.Y_AXIS));
        panelNgayN.setBackground(Color.WHITE);
        panelNgayN.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel lblNgayN = new JLabel("Ngày Nhập:");
        lblNgayN.setFont(labelFont);
        lblNgayN.setForeground(primaryColor);
        panelNgayN.add(lblNgayN);
        panelNgayN.add(Box.createVerticalStrut(5));
        spNgayN = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor editorN = new JSpinner.DateEditor(spNgayN, "yyyy-MM-dd");
        spNgayN.setEditor(editorN);
        spNgayN.setFont(textFont);
        spNgayN.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        spNgayN.setMaximumSize(new Dimension(Integer.MAX_VALUE, spNgayN.getPreferredSize().height + 16));
        panelNgayN.add(spNgayN);
        formPanel.add(panelNgayN);
        formPanel.add(Box.createVerticalStrut(15));

        // Ngày Xuất
        JPanel panelNgayX = new JPanel();
        panelNgayX.setLayout(new BoxLayout(panelNgayX, BoxLayout.Y_AXIS));
        panelNgayX.setBackground(Color.WHITE);
        panelNgayX.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel lblNgayX = new JLabel("Ngày Xuất:");
        lblNgayX.setFont(labelFont);
        lblNgayX.setForeground(primaryColor);
        panelNgayX.add(lblNgayX);
        panelNgayX.add(Box.createVerticalStrut(5));
        spNgayX = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor editorX = new JSpinner.DateEditor(spNgayX, "yyyy-MM-dd");
        spNgayX.setEditor(editorX);
        spNgayX.setFont(textFont);
        spNgayX.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        spNgayX.setMaximumSize(new Dimension(Integer.MAX_VALUE, spNgayX.getPreferredSize().height + 16));
        panelNgayX.add(spNgayX);
        formPanel.add(panelNgayX);
        formPanel.add(Box.createVerticalStrut(15));

        // Số Lượng
        JPanel panelSoLuong = createInputRow("Số Lượng:", txtSoLuong = createTextField());
        formPanel.add(panelSoLuong);
        formPanel.add(Box.createVerticalStrut(15));

        // Giá
        JPanel panelGia = createInputRow("Giá:", txtGia = createTextField());
        formPanel.add(panelGia);
        formPanel.add(Box.createVerticalStrut(15));

        // Image Upload
        JPanel panelImage = new JPanel();
        panelImage.setLayout(new BoxLayout(panelImage, BoxLayout.Y_AXIS));
        panelImage.setBackground(Color.WHITE);
        panelImage.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblImageTitle = new JLabel("Hình Ảnh:");
        lblImageTitle.setFont(labelFont);
        lblImageTitle.setForeground(primaryColor);
        panelImage.add(lblImageTitle);
        panelImage.add(Box.createVerticalStrut(5));

        lblImage = new JLabel();
        lblImage.setPreferredSize(new Dimension(100, 100));
        lblImage.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1));
        lblImage.setHorizontalAlignment(SwingConstants.CENTER);
        panelImage.add(lblImage);
        panelImage.add(Box.createVerticalStrut(5));

        btnUploadImage = new JButton("Tải Ảnh Lên");
        btnUploadImage.setFont(buttonFont);
        btnUploadImage.setBackground(secondaryColor);
        btnUploadImage.setForeground(Color.black);
        btnUploadImage.setFocusPainted(false);
        btnUploadImage.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        btnUploadImage.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnUploadImage.addActionListener(e -> uploadImage());
        panelImage.add(btnUploadImage);

        formPanel.add(panelImage);
        formPanel.add(Box.createVerticalStrut(15));

        // Clear button
        JButton btnClear = new JButton("Làm Mới");
        btnClear.setFont(buttonFont);
        btnClear.setBackground(warningColor);
        btnClear.setForeground(Color.black);
        btnClear.setFocusPainted(false);
        btnClear.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        btnClear.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnClear.addActionListener(e -> clearForm());

        JPanel clearPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        clearPanel.setBackground(Color.WHITE);
        clearPanel.add(btnClear);
        formPanel.add(clearPanel);

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
        String[] columns = {"Mã DV", "Tên DV", "Ngày Nhập", "Ngày Xuất", "Số Lượng", "Giá", "Hình Ảnh"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 6) return ImageIcon.class; // Image column
                return super.getColumnClass(columnIndex);
            }
        };

        table = new JTable(tableModel);
        table.setFont(textFont);
        table.setRowHeight(100); // Increased row height to 100 pixels for larger images
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

        // Custom renderer for image column
        table.getColumnModel().getColumn(6).setCellRenderer(new ImageRenderer());

        // Increase width of the "Hình Ảnh" column
        table.getColumnModel().getColumn(6).setPreferredWidth(120); // Increased width to 120 pixels

        // Center align cell contents for non-image columns
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            if (i != 6) { // Skip image column
                table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }
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

        JButton btnThem = createActionButton("Thêm", primaryColor, e -> themDichVu());
        JButton btnSua = createActionButton("Sửa", successColor, e -> suaDichVu());
        JButton btnXoa = createActionButton("Xóa", accentColor, e -> xoaDichVu());

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

    private void uploadImage() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Image files", "jpg", "png", "jpeg"));
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            selectedImagePath = selectedFile.getAbsolutePath();
            ImageIcon imageIcon = new ImageIcon(selectedImagePath);
            Image scaledImage = imageIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            lblImage.setIcon(new ImageIcon(scaledImage));
        }
    }

    private void loadData() {
        tableModel.setRowCount(0);
        List<DichVu> list = dichVuDAO.getAll();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (DichVu dv : list) {
            Object[] row = new Object[]{
                    dv.getMaDV(),
                    dv.getTenDV(),
                    dv.getNgayN() != null ? sdf.format(dv.getNgayN()) : "",
                    dv.getNgayX() != null ? sdf.format(dv.getNgayX()) : "",
                    dv.getSoLuong(),
                    dv.getGia(),
                    dv.getImagePath() != null ? new ImageIcon(new ImageIcon(dv.getImagePath()).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)) : null
            };
            tableModel.addRow(row);
        }
    }

    private void themDichVu() {
        if (!validateInput()) return;

        DichVu dv = new DichVu();
        dv.setMaDV(txtMaDV.getText());
        dv.setTenDV(txtTenDV.getText());
        dv.setNgayN((java.util.Date) spNgayN.getValue());
        dv.setNgayX((java.util.Date) spNgayX.getValue());
        dv.setImagePath(selectedImagePath);
        try {
            dv.setSoLuong(Integer.parseInt(txtSoLuong.getText()));
            dv.setGia(Double.parseDouble(txtGia.getText()));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Số lượng và giá phải là số!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (dichVuDAO.add(dv)) {
            JOptionPane.showMessageDialog(this, "Thêm dịch vụ thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            loadData();
            clearForm();
        } else {
            JOptionPane.showMessageDialog(this, "Lỗi khi thêm dịch vụ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void suaDichVu() {
        if (!validateInput()) return;

        DichVu dv = new DichVu();
        dv.setMaDV(txtMaDV.getText());
        dv.setTenDV(txtTenDV.getText());
        dv.setNgayN((java.util.Date) spNgayN.getValue());
        dv.setNgayX((java.util.Date) spNgayX.getValue());
        dv.setImagePath(selectedImagePath);
        try {
            dv.setSoLuong(Integer.parseInt(txtSoLuong.getText()));
            dv.setGia(Double.parseDouble(txtGia.getText()));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Số lượng và giá phải là số!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (dichVuDAO.update(dv)) {
            JOptionPane.showMessageDialog(this, "Sửa dịch vụ thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            loadData();
            clearForm();
        } else {
            JOptionPane.showMessageDialog(this, "Lỗi khi sửa dịch vụ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void xoaDichVu() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            String maDV = table.getValueAt(row, 0).toString();
            int confirm = JOptionPane.showConfirmDialog(this, "Xóa dịch vụ " + maDV + "?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                if (dichVuDAO.delete(maDV)) {
                    JOptionPane.showMessageDialog(this, "Xóa dịch vụ thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    loadData();
                    clearForm();
                } else {
                    JOptionPane.showMessageDialog(this, "Lỗi khi xóa dịch vụ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dịch vụ để xóa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void timKiem() {
        String keyword = txtTimKiem.getText().trim();
        tableModel.setRowCount(0);
        List<DichVu> list = dichVuDAO.search(keyword);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (DichVu dv : list) {
            Object[] row = new Object[]{
                    dv.getMaDV(),
                    dv.getTenDV(),
                    dv.getNgayN() != null ? sdf.format(dv.getNgayN()) : "",
                    dv.getNgayX() != null ? sdf.format(dv.getNgayX()) : "",
                    dv.getSoLuong(),
                    dv.getGia(),
                    dv.getImagePath() != null ? new ImageIcon(new ImageIcon(dv.getImagePath()).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)) : null
            };
            tableModel.addRow(row);
        }
        if (tableModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy dịch vụ nào!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void hienThiChiTiet() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            txtMaDV.setText(table.getValueAt(row, 0).toString());
            txtTenDV.setText(table.getValueAt(row, 1).toString());
            try {
                String ngayN = table.getValueAt(row, 2).toString();
                String ngayX = table.getValueAt(row, 3).toString();
                spNgayN.setValue(ngayN.isEmpty() ? new java.util.Date() : new SimpleDateFormat("yyyy-MM-dd").parse(ngayN));
                spNgayX.setValue(ngayX.isEmpty() ? new java.util.Date() : new SimpleDateFormat("yyyy-MM-dd").parse(ngayX));
            } catch (Exception e) {
                spNgayN.setValue(new java.util.Date());
                spNgayX.setValue(new java.util.Date());
            }
            txtSoLuong.setText(table.getValueAt(row, 4).toString());
            txtGia.setText(table.getValueAt(row, 5).toString());
            ImageIcon imageIcon = (ImageIcon) table.getValueAt(row, 6);
            if (imageIcon != null) {
                lblImage.setIcon(imageIcon);
                selectedImagePath = imageIcon.getDescription(); // Store the original path if needed
            } else {
                lblImage.setIcon(null);
                selectedImagePath = null;
            }
        }
    }

    private void clearForm() {
        txtMaDV.setText("");
        txtTenDV.setText("");
        spNgayN.setValue(new java.util.Date());
        spNgayX.setValue(new java.util.Date());
        txtSoLuong.setText("");
        txtGia.setText("");
        lblImage.setIcon(null);
        selectedImagePath = null;
        table.clearSelection();
    }

    private boolean validateInput() {
        if (txtMaDV.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã dịch vụ!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            txtMaDV.requestFocus();
            return false;
        }

        if (txtTenDV.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên dịch vụ!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            txtTenDV.requestFocus();
            return false;
        }

        if (txtSoLuong.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số lượng!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            txtSoLuong.requestFocus();
            return false;
        }

        if (txtGia.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập giá!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            txtGia.requestFocus();
            return false;
        }

        return true;
    }

    // Custom renderer for image column
    private static class ImageRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JLabel label = new JLabel();
            if (value instanceof ImageIcon) {
                ImageIcon icon = (ImageIcon) value;
                Image scaledImage = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                label.setIcon(new ImageIcon(scaledImage));
            } else {
                label.setText("");
            }
            label.setHorizontalAlignment(JLabel.CENTER);
            return label;
        }
    }
}