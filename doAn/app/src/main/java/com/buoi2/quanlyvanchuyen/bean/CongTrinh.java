package com.buoi2.quanlyvanchuyen.bean;

public class CongTrinh {
    public static final String tenBang = "CONGTRINH";
    public static final String cotMaCongTrinh = "maCongTrinh";
    public static final String cotTenCongTrinh = "tenCongTrinh";
    public static final String cotDiaChi = "diaChi";
    String maCongTrinh;
    String tenCongTrinh;
    String diaChi;

    public CongTrinh(String tenCongTrinh, String diaChi) {
        this.tenCongTrinh = tenCongTrinh;
        this.diaChi = diaChi;
    }

    public void setMaCongTrinh(String maCongTrinh) { // được sử dụng cho TH lấy dữ liệu xuống
        this.maCongTrinh = maCongTrinh;
    }

    public String getMaCongTrinh() {
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
