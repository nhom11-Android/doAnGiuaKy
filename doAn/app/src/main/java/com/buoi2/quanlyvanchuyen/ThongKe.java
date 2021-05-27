package com.buoi2.quanlyvanchuyen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ThongKe extends AppCompatActivity {
    TextView vatTuDungNhieuTv, vatTuDoanhThuCaoTv, congTrinhVatTuTv, congTrinh2LoaiVatTuTv, phieuVanChuyenVatTuTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke);
        setActionBar();
        getId();
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
        actionBar.setTitle("Thống Kê");
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void getId() {
        /**
         *   Ánh xạ các View
         */
        vatTuDungNhieuTv = findViewById(R.id.vatTuDungNhieuTv_ThongKe);
        congTrinhVatTuTv = findViewById(R.id.congTrinhVatTuTv_ThongKe);
        phieuVanChuyenVatTuTv = findViewById(R.id.phieuVanChuyenVatTuTv_ThongKe);
    }

    public void callThongKeVatTuDungNhieu(View view) {
        Intent intent = new Intent(this, ThongKeVatTuDungNhieu.class);
        startActivity(intent);
    }

    public void callThongKeCongTrinh(View view) {
        Intent intent = new Intent(this, ThongKeCongTrinh.class);
        startActivity(intent);
    }

    public void callThongKePhieuVanChuyen(View view) {
        Intent intent = new Intent(this, ThongKePhieuVanChuyen.class);
        startActivity(intent);
    }

    public void callThongKeVatTuDoanhThuCao(View view) {
        Intent intent = new Intent(this, ThongKeVatTuDoanhThuCao.class);
        startActivity(intent);
    }

    public void callThongKeCongTrinh2LoaiVT(View view) {
        Intent intent = new Intent(this, ThongCongTrinh2VatTu.class);
        startActivity(intent);
    }

    public void callDoanhThuTheoThang(View view) {
        Intent intent = new Intent(this, ThongKeDoanhThuTheoThang.class);
        startActivity(intent);
    }
}