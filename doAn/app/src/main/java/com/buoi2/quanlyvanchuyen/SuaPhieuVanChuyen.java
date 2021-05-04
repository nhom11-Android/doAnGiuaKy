package com.buoi2.quanlyvanchuyen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.buoi2.quanlyvanchuyen.DAO.ChiTietPhieuVanChuyenDAO;
import com.buoi2.quanlyvanchuyen.DAO.VatTuDAO;
import com.buoi2.quanlyvanchuyen.bean.ChiTietPhieuVanChuyen;
import com.buoi2.quanlyvanchuyen.bean.PhieuVanChuyen;
import com.buoi2.quanlyvanchuyen.bean.VatTu;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;

public class SuaPhieuVanChuyen extends AppCompatActivity {
    PhieuVanChuyen phieuVanChuyen;
    TextView maCongTrinhTv;
    TextView maPhieuVanChuyenTv;
    TextView ngayVanChuyenTv;
    ListView danhSachVatTuLv;
    ImageButton themVatTuBtn;
    ArrayList<ChiTietPhieuVanChuyen> danhSachChiTiet;
    CSDLVanChuyen database = new CSDLVanChuyen(this);
    ArrayList<VatTu> danhSachVatTu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_phieu_van_chuyen);
        Intent pvcIntent = getIntent();
        int mp = Integer.parseInt(pvcIntent.getStringExtra("maPhieuVanChuyen"));
        int mct = Integer.parseInt(pvcIntent.getStringExtra("maCongTrinh"));
        String ngay = pvcIntent.getStringExtra("ngayVanChuyen");
        phieuVanChuyen = new PhieuVanChuyen(ngay, mct);
        phieuVanChuyen.setMaPhieuVanChuyen(mp);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Chi tiết phiếu vận chuyển");
        actionBar.setDisplayHomeAsUpEnabled(true);
        setControl();
        setEvent();
    }

    private void setControl() {
        maCongTrinhTv = findViewById(R.id.maCongTrinhTv_SPVC);
        maPhieuVanChuyenTv = findViewById(R.id.maPhieuVanChuyenTv_SPVC);
        ngayVanChuyenTv = findViewById(R.id.ngayVanChuyenTv_SPVC);
        danhSachVatTuLv = findViewById(R.id.danhSachVatTuChiTietPhieuLv_SPVC);
        themVatTuBtn = findViewById(R.id.themVatTu_SPVC);
    }

    private void setEvent() {
//        Log.d(TAG, "setEvent: "+phieuVanChuyen.getNgayVanChuyen());
        maPhieuVanChuyenTv.setText(String.valueOf(phieuVanChuyen.getMaPhieuVanChuyen()));
        maCongTrinhTv.setText(String.valueOf(phieuVanChuyen.getMaCongTrinh()));
        ngayVanChuyenTv.setText(phieuVanChuyen.getNgayVanChuyen());
//        danhSachChiTiet
        danhSachChiTiet = ChiTietPhieuVanChuyenDAO.danhSachVatTuTheoPhieuVanChuyen(phieuVanChuyen.getMaPhieuVanChuyen(), database.getReadableDatabase());
        // danh sach vat tu
        danhSachVatTu = VatTuDAO.danhSachVatTu(database.getReadableDatabase());
        // set listenner cho nút thêm vật tư
        themVatTuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // xử lý thêm vật tư = dialog

            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
//                Toast.makeText(this, "quay trở lại", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    public void themVatTuVaoPhieu(int maPhieuVanChuyen) {
        try {
            LayoutInflater layoutInflater = LayoutInflater.from(this);
            View themVatTuCTP = layoutInflater.inflate(R.layout.them_vat_tu_chi_tiet_phieu_dialog, null); // tìm dialog view layout từ inflater
            MaterialAlertDialogBuilder alertDialogBuilder = new MaterialAlertDialogBuilder(this); // tạo dialog builder : lớp hỗ trợ xây dựng dialog
            alertDialogBuilder.setView(themVatTuCTP); // set view tìm được cho dialog
            // lấy control các trường đã tạo trên dialog
            Spinner danhSachVatTuSpn = themVatTuCTP.findViewById(R.id.danhSachVatTuSpn_TVTCTPdialog);
            // set data cho spinner
            ArrayAdapter<VatTu> adapter = new ArrayAdapter<VatTu>(this, android.R.layout.simple_spinner_dropdown_item,danhSachVatTu);
            adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
            danhSachVatTuSpn.setAdapter(adapter);
            //set event cho spinner
            VatTu daChon;
            danhSachVatTuSpn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    daChon = (VatTu) parent.getAdapter().getItem(position);

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            EditText soLuongEdt = themVatTuCTP.findViewById(R.id.soLuongEdt_TVTCTPdialog);
            EditText cuLyEdt = themVatTuCTP.findViewById(R.id.cuLyEdt_TVTCTPdialog);

            alertDialogBuilder
                    .setCancelable(false)
                    .setPositiveButton("Tìm", // cài đặt nút đồng ý hành động
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    SQLiteDatabase db = database.getWritableDatabase();
                                    String culy = cuLyEdt.getText().toString().trim();
                                    String soluong = soLuongEdt.getText().toString().trim();
                                    VatTu chon = danhSachVatTuSpn.get
                                }
                            })
                    .setNegativeButton("Huỷ", // cài đặt nút huỷ hành động
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });

            AlertDialog alertDialog = alertDialogBuilder.create(); // tạo dialog từ dialog builder
            alertDialog.show();//show diaglo
            return 0;
        } catch (Exception e) {
            return -1;
        }
    }

    ;
}