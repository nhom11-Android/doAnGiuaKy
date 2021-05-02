package com.buoi2.quanlyvanchuyen;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.buoi2.quanlyvanchuyen.bean.ChiTietPhieuVanChuyen;
import com.buoi2.quanlyvanchuyen.bean.CongTrinh;
import com.buoi2.quanlyvanchuyen.bean.PhieuVanChuyen;
import com.buoi2.quanlyvanchuyen.bean.VatTu;

public class CSDLVanChuyen extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "QuanLyVanChuyen.db";

    public CSDLVanChuyen(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String congTrinh = String.format("CREATE TABLE %s(%s integer primary key autoincrement,%s TEXT,%s TEXT)",
                CongTrinh.tenBang,
                CongTrinh.cotMaCongTrinh,
                CongTrinh.cotTenCongTrinh,
                CongTrinh.cotDiaChi);
        String phieuVanChuyen = String.format("CREATE TABLE %s(%s TEXT primary key,%s TEXT,%s TEXT)",
                PhieuVanChuyen.tenBang,
                PhieuVanChuyen.cotMaCongTrinh,
                PhieuVanChuyen.cotMaPhieuVanChuyen,
                PhieuVanChuyen.cotNgayVanChuyen);
        //CTPVC có trường ID để dễ dàng xoá, cập nhật
        String chiTietPhieuVanChuyen = String.format("CREATE TABLE %s(id integer primary key autoincrement not null,%s TEXT,%s TEXT,%s INTEGER,%s INTEGER)",
                ChiTietPhieuVanChuyen.tenBang,
                ChiTietPhieuVanChuyen.cotMaPhieuVanChuyen,
                ChiTietPhieuVanChuyen.cotSoLuong,
                ChiTietPhieuVanChuyen.cotMaVatTu,
                ChiTietPhieuVanChuyen.cotCuLy);
        String vatTu = String.format("CREATE TABLE %s(%s TEXT primary key,%s TEXT,%s TEXT,%s INTEGER)",
                VatTu.tenBang,
                VatTu.cotMaVatTu,
                VatTu.cotTenVatTu,
                VatTu.cotDonViTinh,
                VatTu.cotGia);
        db.execSQL(congTrinh);
        db.execSQL(phieuVanChuyen);
        db.execSQL(chiTietPhieuVanChuyen);
        db.execSQL(vatTu);
        Log.d("1111", "onCreate: khoi tao csdl");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql_upgrade = "DROP TABLE IF EXISTS ";
        db.execSQL(sql_upgrade + VatTu.tenBang);
        db.execSQL(sql_upgrade+PhieuVanChuyen.tenBang);
        db.execSQL(sql_upgrade+CongTrinh.tenBang);
        db.execSQL(sql_upgrade+ChiTietPhieuVanChuyen.tenBang);
        onCreate(db);
    }

}
