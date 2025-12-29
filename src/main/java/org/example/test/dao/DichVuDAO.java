package org.example.test.dao;

import org.example.test.db.DBConnection;
import org.example.test.model.DichVu;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DichVuDAO {
    public boolean add(DichVu dv) {
        String query = "INSERT INTO dichvu (maDV, tenDV, ngayN, ngayX, soLuong, gia, image_path) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, dv.getMaDV());
            stmt.setString(2, dv.getTenDV());
            stmt.setDate(3, dv.getNgayN() != null ? new java.sql.Date(dv.getNgayN().getTime()) : null);
            stmt.setDate(4, dv.getNgayX() != null ? new java.sql.Date(dv.getNgayX().getTime()) : null);
            stmt.setInt(5, dv.getSoLuong());
            stmt.setDouble(6, dv.getGia());
            stmt.setString(7, dv.getImagePath());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(DichVu dv) {
        String query = "UPDATE dichvu SET tenDV = ?, ngayN = ?, ngayX = ?, soLuong = ?, gia = ?, image_path = ? WHERE maDV = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, dv.getTenDV());
            stmt.setDate(2, dv.getNgayN() != null ? new java.sql.Date(dv.getNgayN().getTime()) : null);
            stmt.setDate(3, dv.getNgayX() != null ? new java.sql.Date(dv.getNgayX().getTime()) : null);
            stmt.setInt(4, dv.getSoLuong());
            stmt.setDouble(5, dv.getGia());
            stmt.setString(6, dv.getImagePath());
            stmt.setString(7, dv.getMaDV());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(String maDV) {
        String query = "DELETE FROM dichvu WHERE maDV = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, maDV);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<DichVu> getAll() {
        List<DichVu> list = new ArrayList<>();
        String query = "SELECT * FROM dichvu";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                DichVu dv = new DichVu();
                dv.setMaDV(rs.getString("maDV"));
                dv.setTenDV(rs.getString("tenDV"));
                dv.setNgayN(rs.getDate("ngayN"));
                dv.setNgayX(rs.getDate("ngayX"));
                dv.setSoLuong(rs.getInt("soLuong"));
                dv.setGia(rs.getDouble("gia"));
                dv.setImagePath(rs.getString("image_path"));
                list.add(dv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<DichVu> search(String keyword) {
        List<DichVu> list = new ArrayList<>();
        String query = "SELECT * FROM dichvu WHERE maDV LIKE ? OR tenDV LIKE ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, "%" + keyword + "%");
            stmt.setString(2, "%" + keyword + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                DichVu dv = new DichVu();
                dv.setMaDV(rs.getString("maDV"));
                dv.setTenDV(rs.getString("tenDV"));
                dv.setNgayN(rs.getDate("ngayN"));
                dv.setNgayX(rs.getDate("ngayX"));
                dv.setSoLuong(rs.getInt("soLuong"));
                dv.setGia(rs.getDouble("gia"));
                dv.setImagePath(rs.getString("image_path"));
                list.add(dv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}