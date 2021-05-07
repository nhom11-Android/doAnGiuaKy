package com.buoi2.quanlyvanchuyen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.buoi2.quanlyvanchuyen.bean.CongTrinh;

import java.util.ArrayList;

public class InCongTrinh extends AppCompatActivity {
    String maCongTrinh,tenCongTrinh, diaChiCongTrinh;
    ArrayList<String> danhSachPhieuVanChuyen;
    //view mapping
    TextView maCongTrinhTv,tenCongTrinhTv,diaChiCongTrinhTv,tongSoPVCTv,ngayInTv;
    EditText nguoiLapEdt;
    TableLayout bangPvcTbL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_cong_trinh);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("In công trình");
        actionBar.setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        maCongTrinh = intent.getStringExtra(CongTrinh.cotMaCongTrinh);
        tenCongTrinh = intent.getStringExtra(CongTrinh.cotTenCongTrinh);
        diaChiCongTrinh = intent.getStringExtra(CongTrinh.cotDiaChi);
        danhSachPhieuVanChuyen = intent.getStringArrayListExtra("danhSachPhieuVanChuyen");
        setControl();
        setData();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setData() {
        maCongTrinhTv.setText(maCongTrinh);
        tenCongTrinhTv.setText(tenCongTrinh);
        diaChiCongTrinhTv.setText(diaChiCongTrinh);
        tongSoPVCTv.setText(String.valueOf(danhSachPhieuVanChuyen.size()));
        for(int i=0;i<danhSachPhieuVanChuyen.size();i+=2){
            TableRow  tableRow = new TableRow(this);
            TextView maPhieu = new TextView(this);maPhieu.setText(danhSachPhieuVanChuyen.get(i));
            maPhieu.setGravity(Gravity.CENTER);
            maPhieu.setTextColor(Color.BLACK);
            TextView ngay = new TextView(this);ngay.setText(danhSachPhieuVanChuyen.get(i+1));
            ngay.setGravity(Gravity.CENTER);
            ngay.setTextColor(Color.BLACK);
            tableRow.addView(maPhieu);
            tableRow.addView(ngay);
            bangPvcTbL.addView(tableRow);

        }
        int[] ngay = myHelp.MySuperFunc.getDateInInteger();
        ngayInTv.setText(String.format("TPHCM,ngày %d tháng %d năm %d",ngay[0],ngay[1],ngay[2]));
    }

    private void setControl() {
        maCongTrinhTv = findViewById(R.id.maCongTrinhTv_ICT);
        tenCongTrinhTv = findViewById(R.id.tenCongTrinhTv_ICT);
        diaChiCongTrinhTv = findViewById(R.id.diaChiTv_ICT);
        tongSoPVCTv = findViewById(R.id.tongSoPVC_ICT);
        nguoiLapEdt = findViewById(R.id.nguoiLapEdt_ICT);
        bangPvcTbL = findViewById(R.id.tableLayoyt_ICT);
        ngayInTv = findViewById(R.id.ngayInTv_ICT);
    }

    public void kiemTraChuKy(View view) {
        if(nguoiLapEdt.getText().toString().equals("")) {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Cảnh báo");
            alertDialog.setMessage("Bạn chưa đặt chữ ký !");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Đồng ý",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }
    }
}