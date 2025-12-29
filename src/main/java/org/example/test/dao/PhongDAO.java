package org.example.test.dao;

import org.example.test.model.Phong;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PhongDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/quanlyphongtro?useSSL=false";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    private int getSoLuongSinhVien(String maPhong) {
        String query = "SELECT COUNT(*) FROM phong_sinhvien WHERE maphong = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, maPhong);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Phong> getAll() {
        List<Phong> list = new ArrayList<>();
        String query = "SELECT * FROM phong";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Phong p = new Phong();
                p.setMaPhong(rs.getString("maphong"));
                p.setTen(rs.getString("ten"));
                p.setLoaiPhong(rs.getString("loaiphong"));
                p.setSoLuong(getSoLuongSinhVien(p.getMaPhong()));
                updateSoLuong(p.getMaPhong(), p.getSoLuong());
                list.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean add(Phong phong) {
        String query = "INSERT INTO phong (maphong, ten, soluong, loaiphong) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, phong.getMaPhong());
            stmt.setString(2, phong.getTen());
            stmt.setInt(3, 0);
            stmt.setString(4, phong.getLoaiPhong());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(Phong phong) {
        String query = "UPDATE phong SET ten = ?, loaiphong = ? WHERE maphong = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, phong.getTen());
            stmt.setString(2, phong.getLoaiPhong());
            stmt.setString(3, phong.getMaPhong());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void updateSoLuong(String maPhong, int soLuong) {
        String query = "UPDATE phong SET soluong = ? WHERE maphong = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, soLuong);
            stmt.setString(2, maPhong);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean delete(String maPhong) {
        String deleteLinkQuery = "DELETE FROM phong_sinhvien WHERE maphong = ?";
        String deletePhongQuery = "DELETE FROM phong WHERE maphong = ?";
        Connection conn = null;
        try {
            conn = getConnection();
            conn.setAutoCommit(false);

            try (PreparedStatement stmtLink = conn.prepareStatement(deleteLinkQuery)) {
                stmtLink.setString(1, maPhong);
                stmtLink.executeUpdate();
            }

            try (PreparedStatement stmtPhong = conn.prepareStatement(deletePhongQuery)) {
                stmtPhong.setString(1, maPhong);
                int rowsAffected = stmtPhong.executeUpdate();
                conn.commit();
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (conn != null) conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Phong> search(String keyword, int offset, int limit) {
        List<Phong> list = new ArrayList<>();
        String query = "SELECT * FROM phong WHERE maphong LIKE ? OR ten LIKE ? OR loaiphong LIKE ? LIMIT ?, ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, "%" + keyword + "%");
            stmt.setString(2, "%" + keyword + "%");
            stmt.setString(3, "%" + keyword + "%");
            stmt.setInt(4, offset);
            stmt.setInt(5, limit);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Phong p = new Phong();
                p.setMaPhong(rs.getString("maphong"));
                p.setTen(rs.getString("ten"));
                p.setLoaiPhong(rs.getString("loaiphong"));
                p.setSoLuong(getSoLuongSinhVien(p.getMaPhong()));
                list.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Phong> search(String keyword) {
        return search(keyword, 0, 25); // Default to first page with 25 results
    }
}