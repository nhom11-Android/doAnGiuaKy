package com.buoi2.quanlyvanchuyen.bean;

public class LoaiDonViTinh {
    public static final String tenBang = "LOAIDONVITINH";
    public static final String cotMaDonViTinh = "maDonViTinh";
    public static final String cotTenDonViTinh = "tenDonViTinh";
    int maDonViTinh;
    String tenDonViTinh;

    public LoaiDonViTinh() {
        this.tenDonViTinh = "";
    }

    public LoaiDonViTinh(String tenDonViTinh) {
        this.tenDonViTinh = tenDonViTinh;
    }

    public int getMaDonViTinh() {
        return maDonViTinh;
    }

    public void setMaDonViTinh(int maDonViTinh) {
        this.maDonViTinh = maDonViTinh;
    }

    public String getTenDonViTinh() {
        return tenDonViTinh;
    }

    public void setTenDonViTinh(String tenDonViTinh) {
        this.tenDonViTinh = tenDonViTinh;
    }
}
