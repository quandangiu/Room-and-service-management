package org.example.test.model;
public class QLSV_P {
    private String masv;
    private String SDT;
    private String ten;
    private String lop;
    private int namsinh;
    private String gioitinh;
    private String quequan;
    private String cutru;

    public QLSV_P() {
    }

    public QLSV_P(String masv,String SDT, String ten, String lop, int namsinh, String gioitinh, String quequan, String cutru) {
        this.masv = masv;
        this.SDT = SDT;
        this.ten = ten;
        this.lop = lop;
        this.namsinh = namsinh;
        this.gioitinh = gioitinh;
        this.quequan = quequan;
        this.cutru = cutru;
    }

    public String getMasv() {
        return masv;
    }

    public void setMasv(String masv) {
        this.masv = masv;
    }
    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getLop() {
        return lop;
    }

    public void setLop(String lop) {
        this.lop = lop;
    }

    public int getNamsinh() {
        return namsinh;
    }

    public void setNamsinh(int namsinh) {
        this.namsinh = namsinh;
    }

    public String getGioitinh() {
        return gioitinh;
    }

    public void setGioitinh(String gioitinh) {
        this.gioitinh = gioitinh;
    }

    public String getQuequan() {
        return quequan;
    }

    public void setQuequan(String quequan) {
        this.quequan = quequan;
    }

    public String getCutru() {
        return cutru;
    }

    public void setCutru(String cutru) {
        this.cutru = cutru;
    }

    @Override
    public String toString() {
        return "QLSV_P [masv=" + masv + ", SDT=" + SDT + ", ten=" + ten + ", lop=" + lop + ", namsinh=" + namsinh + ", gioitinh="
                + gioitinh + ", quequan=" + quequan + ", cutru=" + cutru + "]";
    }
}