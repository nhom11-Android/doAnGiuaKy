package com.buoi2.quanlyvanchuyen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.buoi2.quanlyvanchuyen.DAO.ChiTietPhieuVanChuyenDAO;
import com.buoi2.quanlyvanchuyen.DAO.CongTrinhDAO;
import com.buoi2.quanlyvanchuyen.DAO.PhieuVanChuyenDAO;
import com.buoi2.quanlyvanchuyen.DAO.VatTuDAO;
import com.buoi2.quanlyvanchuyen.bean.ChiTietPhieuVanChuyen;
import com.buoi2.quanlyvanchuyen.bean.CongTrinh;
import com.buoi2.quanlyvanchuyen.bean.PhieuVanChuyen;
import com.buoi2.quanlyvanchuyen.bean.VatTu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ThongKeVatTuDungNhieu extends AppCompatActivity {
    TableLayout tableLayout;
    TableRow tableRow0, tableRow1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke_vat_tu_dung_nhieu);
        setActionBar();
        getId();
        getVatTuDungNhieu();
        CSDLVanChuyen database = new CSDLVanChuyen(this);
//        CongTrinhDAO.themCongTrinh(new CongTrinh("EEEE", "1234"),database.getWritableDatabase());
//        CongTrinhDAO.themCongTrinh(new CongTrinh("BBBB", "2345"),database.getWritableDatabase());
//        CongTrinhDAO.themCongTrinh(new CongTrinh("CCCC", "3456"),database.getWritableDatabase());
//        CongTrinhDAO.themCongTrinh(new CongTrinh("DDDD", "4567"),database.getWritableDatabase());

//        ArrayList<CongTrinh> ds = CongTrinhDAO.layDanhSachCongTrinh(database.getReadableDatabase());
//        for(CongTrinh c : ds){
//            System.out.println("=========================================================");
//            System.out.println("MACT: " + c.getMaCongTrinh());
//            System.out.println("TENCT: " + c.getTenCongTrinh());
//            System.out.println("DC: " + c.getDiaChi());
//        }

//        PhieuVanChuyenDAO.themPhieuVanChuyen(new PhieuVanChuyen("01/05/2021", 1), database.getWritableDatabase());
//        PhieuVanChuyenDAO.themPhieuVanChuyen(new PhieuVanChuyen("02/05/2021", 3), database.getWritableDatabase());
//        PhieuVanChuyenDAO.themPhieuVanChuyen(new PhieuVanChuyen("03/05/2021", 1), database.getWritableDatabase());
//        PhieuVanChuyenDAO.themPhieuVanChuyen(new PhieuVanChuyen("03/05/2021", 2), database.getWritableDatabase());

        ArrayList<PhieuVanChuyen> dsp1 = PhieuVanChuyenDAO.danhSachPhieuVanChuyenTheoCongTrinh(database.getReadableDatabase(), 1);
        ArrayList<PhieuVanChuyen> dsp2 = PhieuVanChuyenDAO.danhSachPhieuVanChuyenTheoCongTrinh(database.getReadableDatabase(), 2);
        ArrayList<PhieuVanChuyen> dsp3 = PhieuVanChuyenDAO.danhSachPhieuVanChuyenTheoCongTrinh(database.getReadableDatabase(), 3);
//        for(PhieuVanChuyen p : dsp1){
//            System.out.println("=========================================================");
//            System.out.println("MAPVC: " + p.getMaPhieuVanChuyen());
//            System.out.println("MACT: " + p.getMaCongTrinh());
//            System.out.println("NGAY: " + p.getNgayVanChuyen());
//        }
//        for(PhieuVanChuyen p : dsp2){
//            System.out.println("=========================================================");
//            System.out.println("MAPVC: " + p.getMaPhieuVanChuyen());
//            System.out.println("MACT: " + p.getMaCongTrinh());
//            System.out.println("NGAY: " + p.getNgayVanChuyen());
//        }
//        for(PhieuVanChuyen p : dsp3){
//            System.out.println("=========================================================");
//            System.out.println("MAPVC: " + p.getMaPhieuVanChuyen());
//            System.out.println("MACT: " + p.getMaCongTrinh());
//            System.out.println("NGAY: " + p.getNgayVanChuyen());
//        }

//        ChiTietPhieuVanChuyenDAO.themCTPVC(new ChiTietPhieuVanChuyen(1,"0", 2, 5), database.getWritableDatabase());
//        ChiTietPhieuVanChuyenDAO.themCTPVC(new ChiTietPhieuVanChuyen(1,"1", 2, 5), database.getWritableDatabase());
//        ChiTietPhieuVanChuyenDAO.themCTPVC(new ChiTietPhieuVanChuyen(1,"2", 2, 5), database.getWritableDatabase());
//        ChiTietPhieuVanChuyenDAO.themCTPVC(new ChiTietPhieuVanChuyen(2,"3", 2, 5), database.getWritableDatabase());
//        ChiTietPhieuVanChuyenDAO.themCTPVC(new ChiTietPhieuVanChuyen(2,"0", 2, 5), database.getWritableDatabase());
//        ChiTietPhieuVanChuyenDAO.themCTPVC(new ChiTietPhieuVanChuyen(3,"2", 2, 5), database.getWritableDatabase());
//        ChiTietPhieuVanChuyenDAO.themCTPVC(new ChiTietPhieuVanChuyen(3,"0", 2, 5), database.getWritableDatabase());
//        ChiTietPhieuVanChuyenDAO.themCTPVC(new ChiTietPhieuVanChuyen(4,"0", 2, 5), database.getWritableDatabase());
//        ChiTietPhieuVanChuyenDAO.themCTPVC(new ChiTietPhieuVanChuyen(4,"3", 2, 5), database.getWritableDatabase());

    }

    private void setActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Thống Kê Vật Tư Dùng Nhiều");
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.capnhat_bar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.lamMoi_CNB:
                tableLayout.removeViewAt(1);
                getVatTuDungNhieu();
                Toast.makeText(this, "Làm mới", Toast.LENGTH_SHORT).show();
                break;
            default:break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getId() {
        /**
         *   Ánh xạ các View
         */
        tableLayout = findViewById(R.id.tablelayout_ThongKeVTDN);
        tableRow0 = findViewById(R.id.tableRow0_ThongKeVTDN);
    }

    private TableRow taoTableRow(String maVatTu, String tenVatTu, int soCongTrinh){
        TableRow tableRow = new TableRow(this);
        TextView maVatTuTv = new TextView(this);
        TextView tenVatTuTv = new TextView(this);
        TextView soCongTrinhTv = new TextView(this);
        // tạo Textview maVatTuTv
        maVatTuTv.setText(maVatTu);
        maVatTuTv.setGravity(1);
        maVatTuTv.setTextSize(17);
        // tạo Textview tenVatTuTv
        tenVatTuTv.setText(tenVatTu);
        tenVatTuTv.setGravity(1);
        tenVatTuTv.setTextSize(17);
        // tạo Textview soCongTrinhTv
        soCongTrinhTv.setText(String.valueOf(soCongTrinh));
        soCongTrinhTv.setGravity(1);
        soCongTrinhTv.setTextSize(17);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            tableRow.setId(View.generateViewId());
        }
        tableRow.addView(maVatTuTv);
        tableRow.addView(tenVatTuTv);
        tableRow.addView(soCongTrinhTv);
        return tableRow;
    }

    private int setTableLayout(String maVatTu, int soCongTrinh){
        CSDLVanChuyen database = new CSDLVanChuyen(this);
        SQLiteDatabase db = database.getReadableDatabase();
        String sql = "SELECT * FROM VATTU WHERE maVatTu=" + maVatTu;
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToNext()){
            TableRow tableRow = taoTableRow(cursor.getString(0), cursor.getString(1), soCongTrinh);
            tableLayout.addView(tableRow);
        }
        return 0;
    }

    private int getVatTuDungNhieu(){
        String sql = "SELECT DISTINCT v.maVatTu, v.tenVatTu, c.maCongTrinh " +
                "FROM VATTU v, CONGTRINH c, PHIEUVANCHUYEN p, CHITIETPVC ct " +
                "WHERE v.maVatTu=ct.maVatTu AND ct.maPhieuVanChuyen=p.maPhieuVanChuyen "+
                "AND p.maCongTrinh=c.maCongTrinh ORDER BY v.maVatTu";
        CSDLVanChuyen database = new CSDLVanChuyen(this);
        SQLiteDatabase db = database.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        Map<String, Integer> kq = new HashMap<String, Integer>();
        String tmp ="";
        int count;
        if(cursor.moveToFirst()){
            do{
                tmp = cursor.getString(0);
                if (!kq.containsKey(tmp)){
                    kq.put(tmp, 1);
                }
                else{
                    count = kq.get(tmp);
                    kq.remove(tmp);
                    kq.put(tmp, count  +1);
                }
            }while(cursor.moveToNext());
        }
        db.close();
        String maVatTu_max = "";
        count = -1;
        for (Map.Entry<String, Integer> e : kq.entrySet()){
            if(e.getValue() > count){
                count = e.getValue();
                maVatTu_max = e.getKey();
            }
//            System.out.println(e.getKey() + " " + e.getValue());
        }
        setTableLayout(maVatTu_max, count);
        return 0;
    }
}