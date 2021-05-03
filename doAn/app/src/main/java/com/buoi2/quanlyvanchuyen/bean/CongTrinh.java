package com.buoi2.quanlyvanchuyen.bean;

public class CongTrinh {
    public static final String tenBang = "CONGTRINH";
    public static final String cotMaCongTrinh = "maCongTrinh";
    public static final String cotTenCongTrinh = "tenCongTrinh";
    public static final String cotDiaChi = "diaChi";
    int maCongTrinh;
    String tenCongTrinh;
    String diaChi;

    public CongTrinh(String tenCongTrinh, String diaChi) {
        this.tenCongTrinh = tenCongTrinh;
        this.diaChi = diaChi;
    }

    /**
     * được sử dụng cho TH lấy dữ liệu xuống
     * @param maCongTrinh
     */
    public void setMaCongTrinh(int maCongTrinh) {
        this.maCongTrinh = maCongTrinh;
    }

    public int getMaCongTrinh() {
        return maCongTrinh;
    }

    public String getTenCongTrinh() {
        return tenCongTrinh;
    }

    public void setTenCongTrinh(String tenCongTrinh) {
        this.tenCongTrinh = tenCongTrinh;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }
}
