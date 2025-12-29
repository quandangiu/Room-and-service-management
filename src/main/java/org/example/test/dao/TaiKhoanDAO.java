package org.example.test.dao;


import org.example.test.db.DBConnection;
import org.example.test.model.TaiKhoan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TaiKhoanDAO {
    public boolean login(String taiKhoan, String matKhau) {
        String query = "SELECT * FROM taikhoan WHERE taikhoan = ? AND matkhau = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, taiKhoan);
            stmt.setString(2, matKhau);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean register(TaiKhoan tk) {
        String query = "INSERT INTO taikhoan (taikhoan, email, matkhau, xacnhan) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, tk.getTaiKhoan());
            stmt.setString(2, tk.getEmail());
            stmt.setString(3, tk.getMatKhau());
            stmt.setString(4, tk.getXacNhan());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}