package com.buoi2.quanlyvanchuyen;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class Menu extends AppCompatActivity {
    Button btnXem_CT, btnXem_VT, btnXem_TK, btnThoat_menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        setControl();
        setEvent();
    }

    private void setEvent() {
        btnXem_CT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Menu.this, DanhSachCongTrinh.class);
                startActivity(intent);
            }
        });
        btnXem_VT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Menu.this, DanhSachVatTu.class);
                startActivity(intent);
            }
        });
        btnXem_TK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Menu.this, ThongKe.class);
                startActivity(intent);
            }
        });
        btnThoat_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });
    }

    private void setControl() {
        btnXem_CT= findViewById(R.id.btnXem_CT);
        btnXem_VT= findViewById(R.id.btnXem_VT);
        btnXem_TK= findViewById(R.id.btnXem_TK);
        btnThoat_menu= findViewById(R.id.btnThoat_menu);

        // cài đặt tiêu đề cho action bar
        ActionBar actionBar = (ActionBar) getSupportActionBar();
        actionBar.setTitle("Quản lý vận chuyển");
//        actionBar.setDisplayHomeAsUpEnabled(true);
    }
}