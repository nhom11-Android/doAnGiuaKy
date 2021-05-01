package com.buoi2.quanlyvanchuyen;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.buoi2.quanlyvanchuyen.bean.CongTrinh;

import java.util.ArrayList;

public class DanhSachCongTrinh extends AppCompatActivity {
    ListView danhSachCongTrinhLv;
    ArrayList<CongTrinh> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_cong_trinh);
        ActionBar actionBar = (ActionBar) getSupportActionBar();
        actionBar.setTitle("Công trình");
        actionBar.setDisplayHomeAsUpEnabled(true);
        data = loadData();
        setControl();
        setEvent();
    }

    /**
     * load dữ liệu từ database
     *
     * @return arrayList công trình
     */
    private ArrayList<CongTrinh> loadData() {
        // TODO: viết hàm load database COng trinh từ csdl lên
        ArrayList<CongTrinh> danhSachCongTrinh = new ArrayList<>();
        return danhSachCongTrinh;
    }

    private void setEvent() {
        data = new ArrayList<>();
        data.add(new CongTrinh("123", "vinhome", "nguyen xien quận 9"));
        CongTrinhAdapter adapter = new CongTrinhAdapter(this, R.layout.cong_trinh_custom_listview, data);
        danhSachCongTrinhLv.setAdapter(adapter);
    }

    private void setControl() {
        danhSachCongTrinhLv = findViewById(R.id.danhSachCongTrinhLv_DSCT);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.them_capnhat_timkiem_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

        }
        return super.onOptionsItemSelected(item);
    }
}