package com.buoi2.quanlyvanchuyen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.buoi2.quanlyvanchuyen.DAO.CongTrinhDAO;
import com.buoi2.quanlyvanchuyen.DAO.PhieuVanChuyenDAO;
import com.buoi2.quanlyvanchuyen.bean.CongTrinh;
import com.buoi2.quanlyvanchuyen.bean.PhieuVanChuyen;

import java.util.ArrayList;

public class ChiTietCongTrinh extends AppCompatActivity {
    EditText maCongTrinhEdt;
    EditText tenCongTrinhEdt;
    EditText diaChiCongTrinhEdt;
    ImageButton suaChiTietBtn;
    int maCongTrinhIntent;
    CSDLVanChuyen database;
    CongTrinh congTrinh;
    ListView danhSachPhieuVanChuyenLv;
    ArrayList<PhieuVanChuyen> data;
    PhieuVanChuyenAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_cong_trinh);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Chi tiết công trình");
        actionBar.setDisplayHomeAsUpEnabled(true);
        Intent homeIntent = getIntent();
        maCongTrinhIntent = homeIntent.getIntExtra("maCongTrinh",0);
        database = new CSDLVanChuyen(this);
        data = PhieuVanChuyenDAO.danhSachPhieuVanChuyenTheoCongTrinh(database.getReadableDatabase(),maCongTrinhIntent);
        congTrinh = getCongTrinhFromDatabase(database);
        // TODO: 5/3/2021 xử lý load danh sách các pvc 
        setControl();
        setEvent(); // load data trước khi gọi event
    }

    /** lấy công trình thông qua maCongTrinh
     * @param database
     * @return congTrinh
     */
    private CongTrinh getCongTrinhFromDatabase(CSDLVanChuyen database) {
        return CongTrinhDAO.timKiemTheoMaCongTrinh(database.getReadableDatabase(),maCongTrinhIntent);
    }

    private void setEvent() {
        // set data cho các editText hiển thị công trình đang chọn
        maCongTrinhEdt.setText(String.valueOf(congTrinh.getMaCongTrinh()));
        tenCongTrinhEdt.setText(congTrinh.getTenCongTrinh());
        diaChiCongTrinhEdt.setText(congTrinh.getDiaChi());
        // set listview adapter để hiển thị danh sách công trình
        adapter = new PhieuVanChuyenAdapter(this,R.layout.danh_sach_phieu_van_chuyen_custom_listview,data);
        adapter.setDb(database); // set database cho adapter
        danhSachPhieuVanChuyenLv.setAdapter(adapter);
        // set listener cho các btn để thực hiện chức năng
        suaChiTietBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(suaChiTietBtn.getTag().toString().equals("non-edit")) {
                    tenCongTrinhEdt.setEnabled(true);
                    diaChiCongTrinhEdt.setEnabled(true);
                    suaChiTietBtn.setImageResource(R.drawable.done_edit_vector);
                    suaChiTietBtn.setTag("editing");
                }else{
                    tenCongTrinhEdt.setEnabled(false);
                    diaChiCongTrinhEdt.setEnabled(false);
                    suaChiTietBtn.setImageResource(R.drawable.edit_vector);
                    congTrinh.setDiaChi(diaChiCongTrinhEdt.getText().toString().trim());
                    congTrinh.setTenCongTrinh(tenCongTrinhEdt.getText().toString().trim());
                    CongTrinhDAO.capNhatCongTrinh(congTrinh,database.getWritableDatabase());
                    suaChiTietBtn.setTag("non-edit");
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.them_capnhat_timkiem_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                Toast.makeText(this, "quay trở lại", Toast.LENGTH_SHORT).show();
                break;
            case R.id.timKiem_ATB:
                // TODO: 5/3/2021 xử lý tìm kiếm 
                break;
            case R.id.them_ATB:
                // TODO: 5/3/2021 xử lý thêm phiếu 
                break;
            case R.id.lamMoi_ATB:
                // TODO: 5/3/2021 xử lý làm mới danh sách phiếu 
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setControl() {
        maCongTrinhEdt = findViewById(R.id.maCongTrinhEdt_CTCT);
        tenCongTrinhEdt = findViewById(R.id.tenCongTrinhEdt_CTCT);
        diaChiCongTrinhEdt = findViewById(R.id.diaChiCongTrinhEdt_CTCT);
        suaChiTietBtn = findViewById(R.id.suaChiTietCongTrinhBtn_CTCT);
        danhSachPhieuVanChuyenLv = findViewById(R.id.danhSachPhieuVanChuyenLv_CTCT);
    }

}