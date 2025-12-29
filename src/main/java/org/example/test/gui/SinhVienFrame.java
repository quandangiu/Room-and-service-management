package org.example.test.gui;



import org.example.test.dao.QLSV_P_DAO;
import org.example.test.model.QLSV_P;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class SinhVienFrame extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private QLSV_P_DAO qlsvDAO;
    private Color primaryColor = new Color(41, 128, 185);
    private Color secondaryColor = new Color(52, 152, 219);
    private Font textFont = new Font("Segoe UI", Font.PLAIN, 13);
    private Font headerFont = new Font("Segoe UI", Font.BOLD, 14);
    private String tenPhong;
    private String loaiPhong;

    public SinhVienFrame(String maPhong, String tenPhong, String loaiPhong) {
        this.tenPhong = tenPhong;
        this.loaiPhong = loaiPhong;
        qlsvDAO = new QLSV_P_DAO();
        initUI(maPhong);
        loadData(maPhong);
    }

    private void initUI(String maPhong) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        setTitle("Danh Sách Sinh Viên - Phòng " + maPhong);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(15, 15));

        // Title Panel
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(primaryColor);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JLabel titleLabel = new JLabel("DANH SÁCH SINH VIÊN", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel, BorderLayout.CENTER);

        add(titlePanel, BorderLayout.NORTH);

        // Info Panel
        JPanel infoPanel = new JPanel(new GridLayout(3, 2, 10, 5));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        infoPanel.add(new JLabel("Mã Phòng:"));
        infoPanel.add(new JLabel(maPhong));
        infoPanel.add(new JLabel("Tên Phòng:"));
        infoPanel.add(new JLabel(tenPhong));
        infoPanel.add(new JLabel("Loại Phòng:"));
        infoPanel.add(new JLabel(loaiPhong));
        add(infoPanel, BorderLayout.WEST);

        // Table
        String[] columns = {"Mã SV", "Tên SV", "Tên Phòng", "Loại Phòng"};
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

        JTableHeader header = table.getTableHeader();
        header.setFont(headerFont);
        header.setBackground(primaryColor);
        header.setForeground(Color.WHITE);
        header.setBorder(null);
        header.setPreferredSize(new Dimension(header.getWidth(), 40));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(primaryColor, 1));
        add(scrollPane, BorderLayout.CENTER);
    }

    private void loadData(String maPhong) {
        tableModel.setRowCount(0);
        List<QLSV_P> list = qlsvDAO.getByMaPhong(maPhong);
        if (list != null) {
            for (QLSV_P sv : list) {
                tableModel.addRow(new Object[]{
                        sv.getMasv(),
                        sv.getTen(),
                        tenPhong,
                        loaiPhong
                });
            }
        }
        if (list.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không có sinh viên nào trong phòng này!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}