package com.buoi2.quanlyvanchuyen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.buoi2.quanlyvanchuyen.DAO.ChiTietPhieuVanChuyenDAO;
import com.buoi2.quanlyvanchuyen.DAO.VatTuDAO;
import com.buoi2.quanlyvanchuyen.bean.ChiTietPhieuVanChuyen;
import com.buoi2.quanlyvanchuyen.bean.VatTu;

import java.util.ArrayList;

public class ThemVatTuPhieuVanChuyen extends AppCompatActivity {
    ArrayList<VatTu> danhSachVatTu;
    EditText soLuongEdt;
    EditText cuLyEdt;
    Spinner danhSachVatTuSpn;
    Button themBtn;
    int maPhieuVanChuyen;

    CSDLVanChuyen database = new CSDLVanChuyen(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_vat_tu_phieu_van_chuyen);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Thêm vật tư vào phiếu");
        actionBar.setDisplayHomeAsUpEnabled(true);
        //lay intent
        maPhieuVanChuyen = Integer.parseInt(getIntent().getStringExtra("maPhieuVanChuyen"));
        setControl();
        setEvent();
    }

    private void setEvent() {
        danhSachVatTu = VatTuDAO.danhSachVatTu(database.getReadableDatabase());
        ArrayAdapter<VatTu> adapter = new ArrayAdapter<VatTu>(this, android.R.layout.simple_spinner_dropdown_item, danhSachVatTu);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        danhSachVatTuSpn.setAdapter(adapter);
    }

    private void setControl() {
        soLuongEdt = findViewById(R.id.soLuongEdt_TVTCTP);
        cuLyEdt = findViewById(R.id.cuLyEdt_TVTCTP);
        danhSachVatTuSpn = findViewById(R.id.danhSachVatTuSpn_TVTCTP);
        themBtn = findViewById(R.id.themBtn_TVTCTP);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    public void themVatTuVaoPhieu(View view) {
        String s = soLuongEdt.getText().toString();
        String s1 = cuLyEdt.getText().toString();
        System.out.println("so luong: " + s);
        System.out.println("so luong: " + s1);
        if(s.isEmpty() || s1.isEmpty()){
            Toast.makeText(this, "Hãy nhập đầy đủ thông tin !", Toast.LENGTH_SHORT).show();
        }
        else{
            int soLuong = Integer.parseInt(s);
            int cuLy = Integer.parseInt(s1);
            VatTu vatTuThem = (VatTu) danhSachVatTuSpn.getItemAtPosition(danhSachVatTuSpn.getSelectedItemPosition());
            ChiTietPhieuVanChuyen chiTietPhieuVanChuyen = new ChiTietPhieuVanChuyen(maPhieuVanChuyen,vatTuThem.getMaVatTu(),soLuong,cuLy);

            System.out.println(cuLy);
            if(ChiTietPhieuVanChuyenDAO.themCTPVC(chiTietPhieuVanChuyen,database.getWritableDatabase())==0){
                Toast.makeText(this, "Thêm thành công !", Toast.LENGTH_SHORT).show();
            };

        }

        //clear các trường để nhập tiếp
        danhSachVatTuSpn.setSelection(0);
        soLuongEdt.setText("");
        cuLyEdt.setText("");
    }
}