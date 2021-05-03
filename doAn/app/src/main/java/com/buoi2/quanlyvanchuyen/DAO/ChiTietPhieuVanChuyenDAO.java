package com.buoi2.quanlyvanchuyen.DAO;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;

import com.buoi2.quanlyvanchuyen.bean.ChiTietPhieuVanChuyen;

public class ChiTietPhieuVanChuyenDAO {
//    private String nulll;

    public static int themCTPVC(ChiTietPhieuVanChuyen chiTietPhieuVanChuyen, SQLiteDatabase db) {
        /*
        para 1: đối tượng phiếu cần thêm
        para 2: writeable database từ lớp helper
        @return: 0 nếu thành công, -1 nếu thất bại
        */
        try {
            ContentValues values = new ContentValues();
//            values.put(ChiTietPhieuVanChuyen.cotMaPhieuVanChuyen, chiTietPhieuVanChuyen.getMaPhieuVanChuyen()); cot nay auto
            values.put(ChiTietPhieuVanChuyen.cotMaVatTu, chiTietPhieuVanChuyen.getMaVatTu());
            values.put(ChiTietPhieuVanChuyen.cotSoLuong, chiTietPhieuVanChuyen.getSoLuong());
            values.put(ChiTietPhieuVanChuyen.cotSoLuong, chiTietPhieuVanChuyen.getCuLy());
            db.insert(ChiTietPhieuVanChuyen.tenBang, null, values);
            db.close();
            return 0;
        } catch (Exception e) {
            return -1;
        }
    }

    public static int xoaCTPVCById(int id, SQLiteDatabase db) {
        /*
        para 1: id đối tượng phiếu cần xoá
        para 2: writeable database từ lớp helper
        @return: 0 nếu thành công, -1 nếu thất bại
        */
        try {
            String xoaQuery = "DELETE from " + ChiTietPhieuVanChuyen.tenBang + " where id=" + id;
            db.execSQL(xoaQuery);
            return 0;
        } catch (Exception e) {
            return -1;
        }
    }

    public static int capNhatCTPVC(ChiTietPhieuVanChuyen chiTietPhieuVanChuyen, SQLiteDatabase db) {
         /*
        para 1: đối tượng phiếu cần cập nhật
        para 2: writeable database từ lớp helper
        @return: 0 nếu thành công, -1 nếu thất bại
        */
        try {
            String capNhatQuery = "UPDATE " +
                    ChiTietPhieuVanChuyen.tenBang +
                    " set " +
                    ChiTietPhieuVanChuyen.cotMaPhieuVanChuyen +" = '" + chiTietPhieuVanChuyen.getMaPhieuVanChuyen() + "'," +
                    ChiTietPhieuVanChuyen.cotMaVatTu + " = '" + chiTietPhieuVanChuyen.getMaVatTu() + "'," +
                    ChiTietPhieuVanChuyen.cotSoLuong+ " = " + chiTietPhieuVanChuyen.getSoLuong() + "," +
                    ChiTietPhieuVanChuyen.cotCuLy + " = " + chiTietPhieuVanChuyen.getCuLy() +
                    "where id = " + chiTietPhieuVanChuyen.getId();
            db.execSQL(capNhatQuery);
            return 0;
        } catch (Exception e) {
            return -1;
        }
    }
}
