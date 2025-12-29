package org.example.test.dao;


import org.example.test.db.DBConnection;
import org.example.test.model.QLSV_P;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QLSV_P_DAO {
    public QLSV_P getByMasv(String masv) {
        String query = "SELECT * FROM qlsv_p WHERE masv = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, masv);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                QLSV_P sv = new QLSV_P();
                sv.setMasv(rs.getString("masv"));
                sv.setTen(rs.getString("ten"));
                sv.setSDT(rs.getString("SDT"));
                sv.setLop(rs.getString("lop"));
                sv.setNamsinh(rs.getInt("namsinh"));
                sv.setGioitinh(rs.getString("gioitinh"));
                sv.setQuequan(rs.getString("quequan"));
                sv.setCutru(rs.getString("cutru"));
                return sv;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<QLSV_P> getAll() {
        List<QLSV_P> list = new ArrayList<>();
        String query = "SELECT * FROM qlsv_p";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                QLSV_P sv = new QLSV_P();
                sv.setMasv(rs.getString("masv"));
                sv.setTen(rs.getString("ten"));
                sv.setSDT(rs.getString("SDT"));
                sv.setLop(rs.getString("lop"));
                sv.setNamsinh(rs.getInt("namsinh"));
                sv.setGioitinh(rs.getString("gioitinh"));
                sv.setQuequan(rs.getString("quequan"));
                sv.setCutru(rs.getString("cutru"));
                list.add(sv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<QLSV_P> getByMaPhong(String maPhong) {
        List<QLSV_P> list = new ArrayList<>();
        // kết nối 2 bảng thông qua masv có chung mã phòng
        String query = "SELECT sv.* FROM qlsv_p sv " +
                "JOIN phong_sinhvien ps ON sv.masv = ps.masv " +
                "WHERE ps.maphong = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, maPhong);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                QLSV_P sv = new QLSV_P();
                sv.setMasv(rs.getString("masv"));
                sv.setTen(rs.getString("ten"));
                sv.setSDT(rs.getString("SDT"));
                sv.setLop(rs.getString("lop"));
                sv.setNamsinh(rs.getInt("namsinh"));
                sv.setGioitinh(rs.getString("gioitinh"));
                sv.setQuequan(rs.getString("quequan"));
                sv.setCutru(rs.getString("cutru"));
                list.add(sv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}