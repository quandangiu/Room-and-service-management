package org.example.test.model;

public class Phong {
    private String maPhong;
    private String ten;
    private int soLuong;
    private String loaiPhong;

    public Phong() {
    }

    public Phong(String maPhong, String ten, int soLuong, String loaiPhong) {
        this.maPhong = maPhong;
        this.ten = ten;
        this.soLuong = soLuong;
        this.loaiPhong = loaiPhong;
    }

    public String getMaPhong() {
        return maPhong;
    }

    public void setMaPhong(String maPhong) {
        this.maPhong = maPhong;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getLoaiPhong() {
        return loaiPhong;
    }

    public void setLoaiPhong(String loaiPhong) {
        this.loaiPhong = loaiPhong;
    }

    @Override
    public String toString() {
        return "Phong [maPhong=" + maPhong + ", ten=" + ten + ", soLuong=" + soLuong + ", loaiPhong=" + loaiPhong + "]";
    }
}