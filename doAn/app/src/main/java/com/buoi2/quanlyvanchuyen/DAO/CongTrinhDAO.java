package com.buoi2.quanlyvanchuyen.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;

import com.buoi2.quanlyvanchuyen.bean.ChiTietPhieuVanChuyen;
import com.buoi2.quanlyvanchuyen.bean.CongTrinh;

public class CongTrinhDAO {

    public static int themCongTrinh(CongTrinh congTrinh, SQLiteDatabase db){
        /**
        @param congTrinh: đối tượng cần thêm, not null
        @param db: writeable database instance từ lớp sqlhelper
        @return: 0 nếu thành công, -1 nếu thất bại
         */
        try {
            ContentValues values = new ContentValues();
            values.put(CongTrinh.cotMaCongTrinh, congTrinh.getMaCongTrinh());
            values.put(CongTrinh.cotTenCongTrinh, congTrinh.getTenCongTrinh());
            values.put(CongTrinh.cotDiaChi, congTrinh.getDiaChi());
            db.insert(CongTrinh.tenBang, null, values);
            db.close();
            return 0;
        } catch (Exception e) {
            return -1;
        }
    }
    public static int xoaCongTrinh(String maCongTrinh, SQLiteDatabase db){
        /**
        @param maCongTrinh: maCongTrinh cần xoá
        @param db: writeable database từ lớp helper
        @return 0 nếu thành công, -1 nếu thất bại
        */
        try {
            String xoaQuery = String.format("DELETE from %s where %s='%s'",
                    CongTrinh.tenBang,
                    CongTrinh.cotMaCongTrinh,
                    maCongTrinh);
            db.execSQL(xoaQuery);
            db.close();
            return 0;
        } catch (Exception e) {
            return -1;
        }
    }
    public static int capNhatCongTrinh(CongTrinh congTrinh, SQLiteDatabase db){
        /**
        @param congTrinh: đối tượng công trình cần cập nhật
        @param db: writeable database từ lớp helper
        @return: 0 nếu thành công, -1 nếu thất bại
        */
        try {
            String capNhatQuery = String.format("UPDATE %s set %s = '%s',%s = '%s' where %s = '%s'",
                    CongTrinh.tenBang,
                    CongTrinh.cotTenCongTrinh,
                    congTrinh.getTenCongTrinh(),
                    CongTrinh.cotDiaChi,
                    congTrinh.getDiaChi(),
                    CongTrinh.cotMaCongTrinh,
                    congTrinh.getMaCongTrinh());
            db.execSQL(capNhatQuery);
            db.close();
            return 0;
        } catch (Exception e) {
            return -1;
        }
    }

    public static boolean kiemTraTonTaiCongTrinh(String maCongTrinh,SQLiteDatabase db){
//        String kiemTraQuery = "SELECT * FROM "+ CongTrinh.tenBang + " where maCongTrinh= '" + maCongTrinh +"'";
        String[] projection = {
                "maCongTrinh"
        };
        String selection = "maCongTrinh = ?";
        String[] selectionArgs = {maCongTrinh};
        Cursor truyVanCongTrinh = db.query(
                CongTrinh.tenBang,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        db.close();
        if(truyVanCongTrinh.moveToNext()) return true;
        else return false;
    }
}
