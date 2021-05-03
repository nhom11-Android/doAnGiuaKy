package com.buoi2.quanlyvanchuyen.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;

import com.buoi2.quanlyvanchuyen.bean.ChiTietPhieuVanChuyen;
import com.buoi2.quanlyvanchuyen.bean.CongTrinh;

import java.util.ArrayList;

public class CongTrinhDAO {

    public static int themCongTrinh(CongTrinh congTrinh, SQLiteDatabase db){
        /**
        @param congTrinh: đối tượng cần thêm, not null
        @param db: writeable database instance từ lớp sqlhelper
        @return: 0 nếu thành công, -1 nếu thất bại
         */
        try {
            ContentValues values = new ContentValues();
            values.put(CongTrinh.cotTenCongTrinh, congTrinh.getTenCongTrinh());
            values.put(CongTrinh.cotDiaChi, congTrinh.getDiaChi());
            db.insert(CongTrinh.tenBang, null, values);
            db.close();
            return 0;
        } catch (Exception e) {
            return -1;
        }
    }
    public static int xoaCongTrinh(int maCongTrinh, SQLiteDatabase db){
        /**
        @param maCongTrinh: maCongTrinh cần xoá
        @param db: writeable database từ lớp helper
        @return 0 nếu thành công, -1 nếu thất bại
        */
        try {
            String xoaQuery = String.format("DELETE from %s where %s='%s'",
                    CongTrinh.tenBang,
                    CongTrinh.cotMaCongTrinh,
                    String.valueOf(maCongTrinh));
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
    // không được sử dụng
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

    /**
     * @param db database helper readable
     * @return danh sách công trình
     */
    public static ArrayList<CongTrinh> layDanhSachCongTrinh(SQLiteDatabase db){

        String[] projection = { // những cột muốn lấy
                CongTrinh.cotMaCongTrinh,
                CongTrinh.cotTenCongTrinh,
                CongTrinh.cotDiaChi
        } ;
        //selection = null
        Cursor cursor = db.query(
                CongTrinh.tenBang,
                projection,
                null,
                null,
                null,
                null,
                null
                );
        ArrayList<CongTrinh> danhSachCongTrinh = new ArrayList<>();
        while (cursor.moveToNext()){
            int mct = cursor.getInt(cursor.getColumnIndex(CongTrinh.cotMaCongTrinh));
            String tct = cursor.getString(cursor.getColumnIndex(CongTrinh.cotTenCongTrinh));
            String dcct = cursor.getString(cursor.getColumnIndex(CongTrinh.cotDiaChi));
            CongTrinh temp = new CongTrinh(tct,dcct);temp.setMaCongTrinh(mct);
            danhSachCongTrinh.add(temp);
        }
        return  danhSachCongTrinh;
    }

    /**
     * @param db write able database
     * @param maCongTrinh : int
     * @return CongTrinh <Object>
     */
    public static CongTrinh timKiemTheoMaCongTrinh(SQLiteDatabase db, int maCongTrinh){
        CongTrinh temp=null;
        String[] projection = { // những cột muốn lấy
                CongTrinh.cotMaCongTrinh,
                CongTrinh.cotTenCongTrinh,
                CongTrinh.cotDiaChi
        } ;
        String selection = CongTrinh.cotMaCongTrinh+" = ?";
        String[] selectionArgs = {String.valueOf(maCongTrinh)};
        Cursor cursor = db.query(
                CongTrinh.tenBang,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        if (cursor.moveToNext()) {
            int mct = cursor.getInt(cursor.getColumnIndex(CongTrinh.cotMaCongTrinh));
            String tct = cursor.getString(cursor.getColumnIndex(CongTrinh.cotTenCongTrinh));
            String dcct = cursor.getString(cursor.getColumnIndex(CongTrinh.cotDiaChi));
            temp = new CongTrinh(tct, dcct);
            temp.setMaCongTrinh(mct);
        }
        return temp;
    }
}
