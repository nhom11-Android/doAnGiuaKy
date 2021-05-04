package com.buoi2.quanlyvanchuyen.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.NonNull;

import com.buoi2.quanlyvanchuyen.bean.PhieuVanChuyen;
import com.buoi2.quanlyvanchuyen.bean.VatTu;

import java.util.ArrayList;

public class VatTuDAO {
    /**
     * @param vatTu object can them
     * @param db writeable object cua sqlite helper
     * @return 0 neu dung, -1 neu sai
     */
    public static int themVatTu(VatTu vatTu,SQLiteDatabase db){
        try {
            ContentValues values = new ContentValues();
            values.put("maVatTu", vatTu.getMaVatTu());
            values.put("tenVatTu", vatTu.getTenVatTu());
            values.put("donViTinh", vatTu.getDonViTinh());
            values.put("gia", vatTu.getGia());
            db.insert(VatTu.tenBang, null, values);
            db.close();
            Log.d("insert vattu", "themVatTu: ");
            return 0;
        }catch (Exception e){
            return -1;
        }
    }

    /**
     * @param maVatTu maVatTu cua doi tuong can xoa
     * @param db writable object cua sqlite database
     * @return so hang bi anh huong neu dung, -1 neu sai
     */
    public static int xoaVatTu(String maVatTu,SQLiteDatabase db){

        try {
            String selection = VatTu.cotMaVatTu + " LIKE ?";
            String[] selectionArgs = {maVatTu};
            int delete = db.delete(VatTu.tenBang, selection, selectionArgs);
            db.close();
            return delete;
        }catch (Exception e){
            return -1;
        }

    }
    public static int capNhatVatTu(VatTu vatTu,SQLiteDatabase db){
        /**
         *   @param vatTu đối tượng cần sửa, not null
         *   @param db writeable database instance từ lớp sqlhelper
         *   @return sô dòng bị ảnh hưởng nếu thành công, -1 nếu thất bại
         */
        try {
            ContentValues values = new ContentValues();
            values.put(VatTu.cotGia, vatTu.getGia());
            String selection = VatTu.cotMaVatTu + " LIKE ?";
            String[] selectionArgs = {vatTu.getMaVatTu()};
            int count = db.update(
                    VatTu.tenBang,
                    values,
                    selection,
                    selectionArgs);
            db.close();
            return count;
        }catch (Exception e){
            return -1;
        }
    }

    public static ArrayList layDanhSachVatTu(SQLiteDatabase db){
        /**
         *   @param list danh sách vật tư
         *   @param db readable database instance từ lớp sqlhelper
         *   @return list nếu thành công, null nếu thất bại
         */
        try {
            ArrayList<VatTu> ds = new ArrayList<>();
            System.out.println("Lấy Danh sách");
            String query = "select * from VATTU";
            Cursor cursor = db.rawQuery(query, null);
            if(cursor.moveToFirst()){
                do{
                    VatTu vatTu = new VatTu();
                    vatTu.setMaVatTu(cursor.getString(0));
                    vatTu.setTenVatTu(cursor.getString(1));
                    vatTu.setDonViTinh(cursor.getString(2));
                    vatTu.setGia(cursor.getInt(3));
                    ds.add(vatTu);
                }while(cursor.moveToNext());
            }
            db.close();
            return ds;
        }catch (Exception e){
            return null;
        }
    }


}
