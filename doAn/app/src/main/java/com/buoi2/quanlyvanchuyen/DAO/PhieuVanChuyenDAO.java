package com.buoi2.quanlyvanchuyen.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;

import com.buoi2.quanlyvanchuyen.bean.CongTrinh;
import com.buoi2.quanlyvanchuyen.bean.PhieuVanChuyen;

import java.util.ArrayList;

public class PhieuVanChuyenDAO {
    public static int themPhieuVanChuyen(PhieuVanChuyen phieuVanChuyen, SQLiteDatabase db){
        /**
        @param phieuVanChuyen đối tượng cần thêm, not null
        @param db writeable database instance từ lớp sqlhelper
        @return: 0 nếu thành công, -1 nếu thất bại
         */
        try {
            ContentValues values = new ContentValues();
            values.put("ngayVanChuyen", phieuVanChuyen.getNgayVanChuyen());
            values.put("maCongTrinh", phieuVanChuyen.getMaCongTrinh());
            db.insert(PhieuVanChuyen.tenBang, null, values);
            db.close();
            return 0;
        } catch (Exception e) {
            return -1;
        }
    }
    public static int xoaPhieuVanChuyen(int maPhieuVanChuyen,SQLiteDatabase db){
        /**
        @param maPhieuVanChuyen: mã phiếu cần xoá
        @param db: writeable database instance từ lớp sqlhelper
        @return: số dòng bị ảnh hưởng nếu thành công, -1 nếu thất bại
         */
        try {
            ChiTietPhieuVanChuyenDAO.xoaCTPVCByMaPhieuVanChuyen(maPhieuVanChuyen,db);// xoá các chi tiết phiếu của mã phiếu này
            String selection = PhieuVanChuyen.cotMaPhieuVanChuyen + " LIKE ?";
            String[] selectionArgs = {String.valueOf(maPhieuVanChuyen)};
            int delete = db.delete(PhieuVanChuyen.tenBang, selection, selectionArgs);
            db.close();
            return delete;
        }catch (Exception e){
            return -1;
        }
    }


    public static int capNhatPhieuVanChuyen(PhieuVanChuyen phieuVanChuyen, SQLiteDatabase db){
        /**
         *   @param phieuVanChuyen đối tượng cần sửa, not null
         *   @param db writeable database instance từ lớp sqlhelper
         *   @return sô dòng bị ảnh hưởng nếu thành công, -1 nếu thất bại
         */
        try {
            ContentValues values = new ContentValues();
            values.put(PhieuVanChuyen.cotMaCongTrinh, phieuVanChuyen.getMaCongTrinh());
            values.put(PhieuVanChuyen.cotNgayVanChuyen, phieuVanChuyen.getNgayVanChuyen());
            String selection = PhieuVanChuyen.cotMaPhieuVanChuyen + " LIKE ?";
            String[] selectionArgs = {String.valueOf(phieuVanChuyen.getMaPhieuVanChuyen())};
            int count = db.update(
                    PhieuVanChuyen.tenBang,
                    values,
                    selection,
                    selectionArgs);
            db.close();
            return count;
        }catch (Exception e){
            return -1;
        }
    }
    public static ArrayList<PhieuVanChuyen> danhSachPhieuVanChuyenTheoCongTrinh(SQLiteDatabase db,int maCongTrinh){
        ArrayList<PhieuVanChuyen> phieuVanChuyens = new ArrayList<>();
        String[] projection = { // những cột muốn lấy
                PhieuVanChuyen.cotMaPhieuVanChuyen,
                PhieuVanChuyen.cotNgayVanChuyen
        } ;
        String selection = PhieuVanChuyen.cotMaCongTrinh+" = ?";
        String[] selectionArgs = {String.valueOf(maCongTrinh)};
        Cursor cursor = db.query(
                PhieuVanChuyen.tenBang,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        while (cursor.moveToNext()) {
            //lấy dữ liệu từ cursor
            int mp = cursor.getInt(cursor.getColumnIndex(PhieuVanChuyen.cotMaPhieuVanChuyen));
            String nvc = cursor.getString(cursor.getColumnIndex(PhieuVanChuyen.cotNgayVanChuyen));
            // tạo 1 object của PhieuVC, sau đó gán maPhieuVC , vì không phải là tạo object mới trong database
            PhieuVanChuyen temp = new PhieuVanChuyen(nvc,maCongTrinh);
            temp.setMaPhieuVanChuyen(mp);
            phieuVanChuyens.add(temp);
        }
        return phieuVanChuyens;
    };
    public static ArrayList<String> getAllPVC(SQLiteDatabase db){
        String[] projection = { // những cột muốn lấy
                PhieuVanChuyen.cotMaPhieuVanChuyen,
                PhieuVanChuyen.cotNgayVanChuyen
        } ;

        Cursor cursor = db.query(
                PhieuVanChuyen.tenBang,
                projection,
                null,
                null,
                null,
                null,
                null
        );
        ArrayList<String> pvc = new ArrayList<>();
        while (cursor.moveToNext()) {
            //lấy dữ liệu từ cursor
            int mp = cursor.getInt(cursor.getColumnIndex(PhieuVanChuyen.cotMaPhieuVanChuyen));
            String nvc = cursor.getString(cursor.getColumnIndex(PhieuVanChuyen.cotNgayVanChuyen));
            pvc.add(String.valueOf(mp));
            pvc.add(nvc);
        }
        return pvc;
    };
}
