package com.buoi2.quanlyvanchuyen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class ThongKeVatTuDoanhThuCao extends AppCompatActivity {
    TableLayout tableLayout;
    TableRow tableRow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke_vat_tu_doanh_thu_cao);
        setActionBar();
        getId();
        getVatTuDoanhThuCao();
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Thống Kê Vật Tư Có Doanh Thu Cao");
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void getId() {
        /**
         *   Ánh xạ các View
         */
        tableLayout= findViewById(R.id.tablelayout_ThongKeVTDTC);
        tableRow = findViewById(R.id.tableRow0_ThongKeVTDTC);
    }

    private TableRow taoTableRow(String maVatTu, String tenVatTu, String donViTinh, int gia, int soLuong ){
        TableRow tableRow = new TableRow(this);
        TextView maVatTuTv = new TextView(this);
        TextView tenVatTuTv = new TextView(this);
        TextView donViTinhTv = new TextView(this);
        TextView giaTv = new TextView(this);
        TextView soLuongTv = new TextView(this);
        TextView tongTv = new TextView(this);
        Long tong = Long.valueOf(gia*soLuong);
        // tạo Textview maVatTuTv
        maVatTuTv.setText(maVatTu);
        maVatTuTv.setGravity(1);
        maVatTuTv.setTextSize(17);
        // tạo Textview tenVatTuTv
        tenVatTuTv.setText(tenVatTu);
        tenVatTuTv.setGravity(1);
        tenVatTuTv.setTextSize(17);
        // tạo Textview donViTinhTv
        donViTinhTv.setText(donViTinh);
        donViTinhTv.setGravity(1);
        donViTinhTv.setTextSize(17);
        // tạo Textview giaTv
        giaTv.setText(String.valueOf(gia));
        giaTv.setGravity(1);
        giaTv.setTextSize(17);
        // tạo Textview soLuongTv
        soLuongTv.setText(String.valueOf(soLuong));
        soLuongTv.setGravity(1);
        soLuongTv.setTextSize(17);
        // tạo Textview tongTv
        tongTv.setText(String.valueOf(tong));
        tongTv.setGravity(1);
        tongTv.setTextSize(17);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            tableRow.setId(View.generateViewId());
        }
        tableRow.addView(maVatTuTv);
        tableRow.addView(tenVatTuTv);
        tableRow.addView(donViTinhTv);
        tableRow.addView(giaTv);
        tableRow.addView(soLuongTv);
        tableRow.addView(tongTv);
        return tableRow;
    }

    private int setTableLayout(String maVatTu, int tongSoLuong){
        CSDLVanChuyen database = new CSDLVanChuyen(this);
        SQLiteDatabase db = database.getReadableDatabase();
        String sql = "SELECT * FROM VATTU WHERE maVatTu='" + maVatTu+"'";
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToNext()){
            TableRow tableRow = taoTableRow(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3), tongSoLuong );
            tableLayout.addView(tableRow);
        }
        return 0;
    }

    private int getVatTuDoanhThuCao(){
        String sql = "SELECT v.maVatTu, v.tenVatTu, v.donViTinh, v.gia, c.soLuong " +
                "FROM VATTU v, CHITIETPVC c " +
                "WHERE v.maVatTu=c.maVatTu "+
                "ORDER BY v.maVatTu";
        CSDLVanChuyen database = new CSDLVanChuyen(this);
        SQLiteDatabase db = database.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        Map<String, Integer> kq = new HashMap<String, Integer>();
        String maVatTu ="";
        int count;
        if(cursor.moveToFirst()){
            do{
                System.out.println("===========================================");
                System.out.println("MAVT: " + cursor.getString(0));
                System.out.println("TenVT: " + cursor.getString(1));
                System.out.println("Gia: " + cursor.getString(3));
                System.out.println("SoLuong: " + cursor.getString(4));
                maVatTu = cursor.getString(0);
                if (!kq.containsKey(maVatTu)){
                    kq.put(maVatTu, cursor.getInt(4));
                }
                else{
                    count = kq.get(maVatTu);
                    kq.remove(maVatTu);
                    kq.put(maVatTu, count  + cursor.getInt(4));
                }
            }while(cursor.moveToNext());
        }
        db.close();

        for (Map.Entry<String, Integer> e : kq.entrySet()){
            System.out.println(e.getKey() + " " + e.getValue());
            setTableLayout(e.getKey(), e.getValue());
        }
        return 0;
    }
}