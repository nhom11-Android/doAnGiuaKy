package com.buoi2.quanlyvanchuyen.bean;

public class ChiTietPhieuVanChuyen {
    public static final String tenBang = "CHITIETPVC";
    public static final String cotMaPhieuVanChuyen = "maPhieuVanChuyen";
    public static final String cotMaVatTu = "maVatTu";
    public static final String cotSoLuong = "soLuong";
    public static final String cotCuLy = "cuLy";
    int id;
    String maPhieuVanChuyen;
    String maVatTu;
    int soLuong;
    int cuLy;

    // TODO: check ma pvc, mavt
    public ChiTietPhieuVanChuyen(String maPhieuVanChuyen, String maVatTu, int soLuong, int cuLy) {
        this.maPhieuVanChuyen = maPhieuVanChuyen;
        this.maVatTu = maVatTu;
        this.soLuong = soLuong;
        this.cuLy = cuLy;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaPhieuVanChuyen() {
        return maPhieuVanChuyen;
    }

    public void setMaPhieuVanChuyen(String maPhieuVanChuyen) {
        this.maPhieuVanChuyen = maPhieuVanChuyen;
    }

    public String getMaVatTu() {
        return maVatTu;
    }

    public void setMaVatTu(String maVatTu) {
        this.maVatTu = maVatTu;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getCuLy() {
        return cuLy;
    }

    public void setCuLy(int cuLy) {
        this.cuLy = cuLy;
    }
}
