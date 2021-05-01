package com.buoi2.quanlyvanchuyen;

import android.os.Bundle;
import android.view.Menu;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class DanhSachCongTrinh extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_cong_trinh);
//        ActionBar actionBar = (ActionBar) findViewById(R.id.)
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.danh_sach_cong_trinh_menu,menu);
        return true;
    }
}