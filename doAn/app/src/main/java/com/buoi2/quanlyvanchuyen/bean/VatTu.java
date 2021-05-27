package com.buoi2.quanlyvanchuyen.bean;

import androidx.annotation.NonNull;

public class VatTu {
    public static final String tenBang = "VATTU";
    public static final String cotMaVatTu = "maVatTu";
    public static final String cotTenVatTu = "tenVatTu";
    public static final String cotGia = "gia";
    public static final String cotDonViTinh = "donViTinh";
    public static final String cotAnh = "anh";
    String maVatTu;
    String tenVatTu;
    int gia;
    String donViTinh;
    byte[] anh;

    public VatTu(){
        this.maVatTu = "";
        this.tenVatTu = "";
        this.donViTinh = "";
        this.gia = 0;
        this.anh = null;
    }

    public VatTu(String maVatTu, String tenVatTu, String donViTinh, int gia, byte[] anh) {
        this.maVatTu = maVatTu;
        this.tenVatTu = tenVatTu;
        this.donViTinh = donViTinh;
        this.gia = gia;
        this.anh = anh;
    }

    public String getMaVatTu() {
        return maVatTu;
    }

    public void setMaVatTu(String maVatTu) {
        this.maVatTu = maVatTu;
    }

    public String getTenVatTu() {
        return tenVatTu;
    }

    public void setTenVatTu(String tenVatTu) {
        this.tenVatTu = tenVatTu;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public String getDonViTinh() {
        return donViTinh;
    }

    public void setDonViTinh(String donViTinh) {
        this.donViTinh = donViTinh;
    }

    public byte[] getAnh() {
        return anh;
    }

    public void setAnh(byte[] anh) {
        this.anh = anh;
    }

    @NonNull
    @Override
    public String toString() {
        return "(" + this.maVatTu  + ") " +this.getTenVatTu() ;
    }
}
