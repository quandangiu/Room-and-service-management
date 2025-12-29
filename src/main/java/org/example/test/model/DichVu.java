package org.example.test.model;

import java.util.Date;

/**
 * Represents a service (DichVu) entity with details such as service code, name, import/export dates,
 * quantity, price, and image path.
 */
public class DichVu {
    private String maDV;
    private String tenDV;
    private Date ngayN;
    private Date ngayX;
    private int soLuong;
    private double gia;
    private String imagePath;

    /**
     * Default constructor for creating an empty DichVu object.
     */
    public DichVu() {}

    /**
     * Constructor for creating a DichVu object with all fields.
     *
     * @param maDV      Service code
     * @param tenDV     Service name
     * @param ngayN     Import date
     * @param ngayX     Export date
     * @param soLuong   Quantity
     * @param gia       Price
     * @param imagePath Path to the service image
     */
    public DichVu(String maDV, String tenDV, Date ngayN, Date ngayX, int soLuong, double gia, String imagePath) {
        this.maDV = maDV;
        this.tenDV = tenDV;
        this.ngayN = ngayN;
        this.ngayX = ngayX;
        this.soLuong = soLuong;
        this.gia = gia;
        this.imagePath = imagePath;
    }

    // Getters and Setters with validation

    /**
     * Gets the service code.
     *
     * @return Service code
     */
    public String getMaDV() {
        return maDV;
    }

    /**
     * Sets the service code.
     *
     * @param maDV Service code, cannot be null or empty
     * @throws IllegalArgumentException if maDV is null or empty
     */
    public void setMaDV(String maDV) {
        if (maDV == null || maDV.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã dịch vụ không được để trống");
        }
        this.maDV = maDV;
    }

    /**
     * Gets the service name.
     *
     * @return Service name
     */
    public String getTenDV() {
        return tenDV;
    }

    /**
     * Sets the service name.
     *
     * @param tenDV Service name, cannot be null or empty
     * @throws IllegalArgumentException if tenDV is null or empty
     */
    public void setTenDV(String tenDV) {
        if (tenDV == null || tenDV.trim().isEmpty()) {
            throw new IllegalArgumentException("Tên dịch vụ không được để trống");
        }
        this.tenDV = tenDV;
    }

    /**
     * Gets the import date.
     *
     * @return Import date
     */
    public Date getNgayN() {
        return ngayN;
    }

    /**
     * Sets the import date.
     *
     * @param ngayN Import date
     */
    public void setNgayN(Date ngayN) {
        this.ngayN = ngayN;
    }

    /**
     * Gets the export date.
     *
     * @return Export date
     */
    public Date getNgayX() {
        return ngayX;
    }

    /**
     * Sets the export date.
     *
     * @param ngayX Export date
     */
    public void setNgayX(Date ngayX) {
        this.ngayX = ngayX;
    }

    /**
     * Gets the quantity.
     *
     * @return Quantity
     */
    public int getSoLuong() {
        return soLuong;
    }

    /**
     * Sets the quantity.
     *
     * @param soLuong Quantity, must be non-negative
     * @throws IllegalArgumentException if soLuong is negative
     */
    public void setSoLuong(int soLuong) {
        if (soLuong < 0) {
            throw new IllegalArgumentException("Số lượng không được âm");
        }
        this.soLuong = soLuong;
    }

    /**
     * Gets the price.
     *
     * @return Price
     */
    public double getGia() {
        return gia;
    }

    /**
     * Sets the price.
     *
     * @param gia Price, must be non-negative
     * @throws IllegalArgumentException if gia is negative
     */
    public void setGia(double gia) {
        if (gia < 0) {
            throw new IllegalArgumentException("Giá không được âm");
        }
        this.gia = gia;
    }

    /**
     * Gets the image path.
     *
     * @return Image path
     */
    public String getImagePath() {
        return imagePath;
    }

    /**
     * Sets the image path.
     *
     * @param imagePath Path to the service image
     */
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    /**
     * Returns a string representation of the DichVu object.
     *
     * @return String representation
     */
    @Override
    public String toString() {
        return "DichVu{" +
                "maDV='" + maDV + '\'' +
                ", tenDV='" + tenDV + '\'' +
                ", ngayN=" + ngayN +
                ", ngayX=" + ngayX +
                ", soLuong=" + soLuong +
                ", gia=" + gia +
                ", imagePath='" + imagePath + '\'' +
                '}';
    }
}