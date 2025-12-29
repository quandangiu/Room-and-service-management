package org.example.test.model;



public class TaiKhoan {
    private String taiKhoan;
    private String email;
    private String matKhau;
    private String xacNhan;

    public TaiKhoan() {}

    public TaiKhoan(String taiKhoan, String email, String matKhau, String xacNhan) {
        this.taiKhoan = taiKhoan;
        this.email = email;
        this.matKhau = matKhau;
        this.xacNhan = xacNhan;
    }

    // Getters and Setters
    public String getTaiKhoan() { return taiKhoan; }
    public void setTaiKhoan(String taiKhoan) { this.taiKhoan = taiKhoan; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getMatKhau() { return matKhau; }
    public void setMatKhau(String matKhau) { this.matKhau = matKhau; }
    public String getXacNhan() { return xacNhan; }
    public void setXacNhan(String xacNhan) { this.xacNhan = xacNhan; }
}