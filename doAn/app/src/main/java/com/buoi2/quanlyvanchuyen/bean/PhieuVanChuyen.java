package com.buoi2.quanlyvanchuyen.bean;

public class PhieuVanChuyen {
    public static final String tenBang = "PHIEUVANCHUYEN";
    public static final String cotMaPhieuVanChuyen = "maPhieuVanChuyen";
    public static final String cotNgayVanChuyen = "ngayVanChuyen";
    public static final String cotMaCongTrinh = "maCongTrinh";

    String maPhieuVanChuyen;
    String ngayVanChuyen;
    String maCongTrinh;

    // TODO: check ma pvc
    public PhieuVanChuyen(String maPhieuVanChuyen, String ngayVanChuyen, String maCongTrinh) {
        this.maPhieuVanChuyen = maPhieuVanChuyen;
        this.ngayVanChuyen = ngayVanChuyen;
        this.maCongTrinh = maCongTrinh;
    }

    public String getMaPhieuVanChuyen() {
        return maPhieuVanChuyen;
    }

    public void setMaPhieuVanChuyen(String maPhieuVanChuyen) {
        this.maPhieuVanChuyen = maPhieuVanChuyen;
    }

    public String getNgayVanChuyen() {
        return ngayVanChuyen;
    }

    public void setNgayVanChuyen(String ngayVanChuyen) {
        this.ngayVanChuyen = ngayVanChuyen;
    }

    public String getMaCongTrinh() {
        return maCongTrinh;
    }

    public void setMaCongTrinh(String maCongTrinh) {
        this.maCongTrinh = maCongTrinh;
    }
}
