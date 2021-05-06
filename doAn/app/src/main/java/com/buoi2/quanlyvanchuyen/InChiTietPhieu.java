package com.buoi2.quanlyvanchuyen;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.buoi2.quanlyvanchuyen.DAO.ChiTietPhieuVanChuyenDAO;
import com.buoi2.quanlyvanchuyen.DAO.CongTrinhDAO;
import com.buoi2.quanlyvanchuyen.DAO.PhieuVanChuyenDAO;
import com.buoi2.quanlyvanchuyen.DAO.VatTuDAO;
import com.buoi2.quanlyvanchuyen.bean.ChiTietPhieuVanChuyen;
import com.buoi2.quanlyvanchuyen.bean.CongTrinh;
import com.buoi2.quanlyvanchuyen.bean.PhieuVanChuyen;
import com.buoi2.quanlyvanchuyen.bean.VatTu;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class InChiTietPhieu extends AppCompatActivity {
    TextView maCongTrinhTv,tenCongTrinhTv,
    diachiTv,maPhieuVanChuyenTv,ngayVanChuyenTv,tongTienTv;
    EditText daiDienEdt,nguoiLapEdt;
    TableLayout danhSachVatTuTbl;
    ImageButton kiemTraInBtn;
    CSDLVanChuyen database = new CSDLVanChuyen(this);
    int maCongTrinh, maPhieuVanChuyen;
    String ngayVanChuyen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_chi_tiet_phieu);
        Intent intent = getIntent();
        maCongTrinh = Integer.parseInt(intent.getStringExtra("maCongTrinh"));
        maPhieuVanChuyen = Integer.parseInt(intent.getStringExtra("maPhieuVanChuyen"));
        ngayVanChuyen = intent.getStringExtra("ngayVanChuyen");
        setControl();
        setData();
    }

    private void setData() {
        CongTrinh congTrinh = CongTrinhDAO.timKiemTheoMaCongTrinh(database.getReadableDatabase(),maCongTrinh);
        ArrayList<ChiTietPhieuVanChuyen> danhSachVatTu = ChiTietPhieuVanChuyenDAO.danhSachVatTuTheoPhieuVanChuyen(maPhieuVanChuyen, database.getReadableDatabase());
        // đưa data len view

        maCongTrinhTv.setText(String.valueOf(congTrinh.getMaCongTrinh()));
        tenCongTrinhTv.setText(String.valueOf(congTrinh.getTenCongTrinh()));
        diachiTv.setText(String.valueOf(congTrinh.getDiaChi()));
        maPhieuVanChuyenTv.setText(String.valueOf(maPhieuVanChuyen));
        ngayVanChuyenTv.setText(String.valueOf(ngayVanChuyen));

        int tongTien = 0;

        //set data cho table
        for(ChiTietPhieuVanChuyen i:danhSachVatTu){
            VatTu vattu = VatTuDAO.layVatTuTheoMaVatTu(i.getMaVatTu(),database.getReadableDatabase());
            TableRow tableRow = new TableRow(this);
            TextView tenVt = new TextView(this);tenVt. 
            tenVt.setText(String.valueOf(vattu.getTenVatTu()));
            TextView donVi = new TextView(this);
            donVi.setText(vattu.getDonViTinh());
            TextView soLuong = new TextView(this);
            soLuong.setText(String.valueOf(i.getSoLuong()));
            TextView giaVc = new TextView(this);
            giaVc.setText(String.valueOf(vattu.getGia()));
            TextView cuLy = new TextView(this);
            cuLy.setText(String.valueOf(i.getCuLy()));
            TextView thanhTien = new TextView(this);
            int tienVcVt = i.getSoLuong()*vattu.getGia()*i.getCuLy();
            tongTien+=tienVcVt;
            thanhTien.setText(String.valueOf(tienVcVt));
            tableRow.addView(tenVt);
            tableRow.addView(donVi);
            tableRow.addView(soLuong);
            tableRow.addView(giaVc);
            tableRow.addView(cuLy);
            tableRow.addView(thanhTien);
            danhSachVatTuTbl.addView(tableRow);
        }
        tongTienTv.setText(String.valueOf(tongTien));

    }

    private void setControl() {
        maCongTrinhTv = findViewById(R.id.maCongTrinhTv_ICTP);
        tenCongTrinhTv = findViewById(R.id.tenCongTrinhTv_ICTP);
        diachiTv = findViewById(R.id.diaChiTv_ICTP);
        maPhieuVanChuyenTv = findViewById(R.id.maPhieu_ICTP);
        ngayVanChuyenTv = findViewById(R.id.ngayVanChuyenTv_ICTP);
        tongTienTv = findViewById(R.id.tongTienTv_ICTP);
        daiDienEdt = findViewById(R.id.daiDienCongTrinhEdt_ICTP);
        nguoiLapEdt = findViewById(R.id.nguoiLapEdt_ICTP);
        danhSachVatTuTbl = findViewById(R.id.bangChiTietVatTuTb_ICTP);
        kiemTraInBtn = findViewById(R.id.kiemTraInBtn_ICTP);
    }

    public void kiemTraChuKy(View view) {
        if(nguoiLapEdt.getText().toString().equals("")){
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Cảnh báo");
            alertDialog.setMessage("Bạn chưa đặt người lập !");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Đồng ý",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }
        if(daiDienEdt.getText().toString().equals("")){
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Cảnh báo");
            alertDialog.setMessage("Bạn chưa đặt người đại diện!");
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