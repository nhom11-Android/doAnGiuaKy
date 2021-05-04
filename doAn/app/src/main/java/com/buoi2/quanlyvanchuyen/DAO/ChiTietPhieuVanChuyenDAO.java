package com.buoi2.quanlyvanchuyen.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;

import com.buoi2.quanlyvanchuyen.bean.ChiTietPhieuVanChuyen;
import com.buoi2.quanlyvanchuyen.bean.PhieuVanChuyen;

import java.util.ArrayList;

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
            values.put(ChiTietPhieuVanChuyen.cotMaPhieuVanChuyen, chiTietPhieuVanChuyen.getMaPhieuVanChuyen());
            values.put(ChiTietPhieuVanChuyen.cotMaVatTu, chiTietPhieuVanChuyen.getMaVatTu());
            values.put(ChiTietPhieuVanChuyen.cotSoLuong, chiTietPhieuVanChuyen.getSoLuong());
            values.put(ChiTietPhieuVanChuyen.cotCuLy, chiTietPhieuVanChuyen.getCuLy());
            db.insert(ChiTietPhieuVanChuyen.tenBang, null, values);
            db.close();
            return 0;
        } catch (Exception e) {
            return -1;
        }
    }
    /**
    @para id id đối tượng phiếu cần xoá
    @para db writeable database từ lớp helper
    @return 0 nếu thành công, -1 nếu thất bại
    */
    public static int xoaCTPVCById(int id, SQLiteDatabase db) {

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

    public static ArrayList<ChiTietPhieuVanChuyen> danhSachVatTuTheoPhieuVanChuyen(int maPhieuVanChuyen, SQLiteDatabase db){
        ArrayList<ChiTietPhieuVanChuyen> danhSachVatTuTheoPVC = new ArrayList<>();
        String selection = ChiTietPhieuVanChuyen.cotMaPhieuVanChuyen+" = ?";
        String[] selectionArgs = {String.valueOf(maPhieuVanChuyen)};
        Cursor cursor = db.query(
                ChiTietPhieuVanChuyen.tenBang,
                null, // lấy hết các cột
                selection,
                selectionArgs, // lấy theo mã phiếu vận chuyển
                null,
                null,
                null
        );
        while (cursor.moveToNext()) {
            //lấy dữ liệu từ cursor
            int mpvc = cursor.getInt(cursor.getColumnIndex(ChiTietPhieuVanChuyen.cotMaPhieuVanChuyen));
            String mvt = cursor.getString(cursor.getColumnIndex(ChiTietPhieuVanChuyen.cotMaVatTu));
            int culy = cursor.getInt(cursor.getColumnIndex(ChiTietPhieuVanChuyen.cotCuLy));
            int soluong = cursor.getInt(cursor.getColumnIndex(ChiTietPhieuVanChuyen.cotSoLuong));
            int id =  cursor.getInt(cursor.getColumnIndex(ChiTietPhieuVanChuyen.cotId));
            // tạo 1 object của PhieuVC, sau đó gán maPhieuVC , vì không phải là tạo object mới trong database
            ChiTietPhieuVanChuyen temp = new ChiTietPhieuVanChuyen(mpvc,mvt,soluong,culy);
            temp.setId(id);
            danhSachVatTuTheoPVC.add(temp);
        }
        return danhSachVatTuTheoPVC;
    };
}
