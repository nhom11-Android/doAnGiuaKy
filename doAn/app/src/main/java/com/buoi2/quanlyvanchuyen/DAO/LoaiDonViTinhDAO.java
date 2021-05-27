package com.buoi2.quanlyvanchuyen.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.buoi2.quanlyvanchuyen.bean.LoaiDonViTinh;

import java.util.ArrayList;

public class LoaiDonViTinhDAO {
    /**
     * @param donViTinh object can them
     * @param db writeable object cua sqlite helper
     * @return 0 neu dung, -1 neu sai
     */
    public static int themDonViTinh(LoaiDonViTinh donViTinh, SQLiteDatabase db){
        try {
            ContentValues values = new ContentValues();
            values.put("tenDonViTinh", donViTinh.getTenDonViTinh());
            db.insert(LoaiDonViTinh.tenBang, null, values);
            db.close();
            Log.d("insert don vi tinh", "themmDonViTinh: ");
            return 0;
        }catch (Exception e){
            return -1;
        }
    }

    public static ArrayList<LoaiDonViTinh> layDanhSachDonViTinh(SQLiteDatabase db){

        String[] projection = { // những cột muốn lấy
                LoaiDonViTinh.cotMaDonViTinh,
                LoaiDonViTinh.cotTenDonViTinh
        } ;
        //selection = null
        Cursor cursor = db.query(
                LoaiDonViTinh.tenBang,
                projection,
                null,
                null,
                null,
                null,
                null
        );
        ArrayList<LoaiDonViTinh> danhSachDonViTinh = new ArrayList<>();
        while (cursor.moveToNext()){
            int mdvt = cursor.getInt(cursor.getColumnIndex(LoaiDonViTinh.cotMaDonViTinh));
            String tdvt = cursor.getString(cursor.getColumnIndex(LoaiDonViTinh.cotTenDonViTinh));
            LoaiDonViTinh temp = new LoaiDonViTinh(tdvt);temp.setMaDonViTinh(mdvt);
            danhSachDonViTinh.add(temp);
        }
        return  danhSachDonViTinh;
    }
}
