package com.buoi2.quanlyvanchuyen.bean;

public class PhieuVanChuyen {
    public static final String tenBang = "PHIEUVANCHUYEN";
    public static final String cotMaPhieuVanChuyen = "maPhieuVanChuyen";
    public static final String cotNgayVanChuyen = "ngayVanChuyen";
    public static final String cotMaCongTrinh = "maCongTrinh";

    int maPhieuVanChuyen; // tạo tự động
    String ngayVanChuyen;
    int maCongTrinh;

    // TODO: check ma pvc
    public PhieuVanChuyen(String ngayVanChuyen, int maCongTrinh) {
        this.ngayVanChuyen = ngayVanChuyen;
        this.maCongTrinh = maCongTrinh;
    }

    public int getMaPhieuVanChuyen() {
        return maPhieuVanChuyen;
    }

    public void setMaPhieuVanChuyen(int maPhieuVanChuyen) {
        this.maPhieuVanChuyen = maPhieuVanChuyen;
    }

    public String getNgayVanChuyen() {
        return ngayVanChuyen;
    }

    public void setNgayVanChuyen(String ngayVanChuyen) {
        this.ngayVanChuyen = ngayVanChuyen;
    }

    public int getMaCongTrinh() {
        return maCongTrinh;
    }

    public void setMaCongTrinh(int maCongTrinh) {
        this.maCongTrinh = maCongTrinh;
    }
}
