package com.buoi2.quanlyvanchuyen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.buoi2.quanlyvanchuyen.DAO.ChiTietPhieuVanChuyenDAO;
import com.buoi2.quanlyvanchuyen.DAO.PhieuVanChuyenDAO;
import com.buoi2.quanlyvanchuyen.DAO.VatTuDAO;
import com.buoi2.quanlyvanchuyen.bean.ChiTietPhieuVanChuyen;
import com.buoi2.quanlyvanchuyen.bean.PhieuVanChuyen;
import com.buoi2.quanlyvanchuyen.bean.VatTu;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Arrays;

public class ThongKeDoanhThuTheoThang extends AppCompatActivity {
    CSDLVanChuyen csdlVanChuyen = new CSDLVanChuyen(this);
    TableLayout bangDoanhThuTheoThang;
    ArrayList<Integer> doanhThuTheoThang = new ArrayList<>();

//    public ThongKeDoanhThuTheoThang() {
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke_doanh_thu_theo_thang);
        setActionBar();
        setControl();
        int[] doanhthutheothang = doanhThuTheoThang();
        for(int i=1;i<=12;i++){
            bangDoanhThuTheoThang.addView(taoTableRow(i,doanhthutheothang[i]));
            doanhThuTheoThang.add(doanhthutheothang[i]);
        }
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
        actionBar.setTitle("Thống kê doanh thu theo tháng");
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void setControl() {
        bangDoanhThuTheoThang = findViewById(R.id.tablelayout_TKDTTT);
    }

    public TableRow taoTableRow(int thang, int doanhthu){
        TableRow tbr = new TableRow(this);
        TextView thangCol = new TextView(this);
        TextView doanhthuCol = new TextView(this);
        thangCol.setText(String.valueOf(thang));
        thangCol.setGravity(Gravity.CENTER);
        doanhthuCol.setText(String.valueOf(doanhthu));
        doanhthuCol.setGravity(Gravity.CENTER);
        tbr.addView(thangCol);
        tbr.addView(doanhthuCol);
        return tbr;
    }

    public int[] doanhThuTheoThang(){
        ArrayList<String> doanhThu = new ArrayList<>();
        ArrayList<String> danhSachPvc = PhieuVanChuyenDAO.getAllPVC(csdlVanChuyen.getReadableDatabase());
        int[] doanhThuCuaThang = new int[13];// 12 thang (1-12)
        Arrays.fill(doanhThuCuaThang,0);
        for(int i=0;i< danhSachPvc.size();i+=2){
            int month = Integer.parseInt(danhSachPvc.get(i+1).split("/")[1]);
            int mp = Integer.parseInt(danhSachPvc.get(i));
            //tính doanh thu của phiếu
            int doanhThuCuaPhieu = tinhDoanhThuTheoMaPhieu(mp);
            doanhThuCuaThang[month] += doanhThuCuaPhieu;
            System.out.println("phieu: "+mp+" "+ doanhThuCuaPhieu);
        }
        return doanhThuCuaThang;
    }

    private int tinhDoanhThuTheoMaPhieu(int maPhieuVanChuyen) {
        ArrayList<ChiTietPhieuVanChuyen> danhSachVatTu = ChiTietPhieuVanChuyenDAO.danhSachVatTuTheoPhieuVanChuyen(maPhieuVanChuyen, csdlVanChuyen.getReadableDatabase());
        int tongTien = 0;

        //set data cho table
        for(ChiTietPhieuVanChuyen i:danhSachVatTu){
            VatTu vattu = VatTuDAO.layVatTuTheoMaVatTu(i.getMaVatTu(),csdlVanChuyen.getReadableDatabase());
            int tienVcVt = i.getSoLuong()*vattu.getGia()*i.getCuLy();
            tongTien+=tienVcVt;
        }
        return tongTien;
    }


    public void veDoThiDoanhThuThang(View view) {
        Intent intent = new Intent(this,MPLineChart.class);
        intent.putIntegerArrayListExtra("doanhThuThang",doanhThuTheoThang);
        startActivity(intent);
    }
}