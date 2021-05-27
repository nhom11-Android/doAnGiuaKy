package com.buoi2.quanlyvanchuyen;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;


public class Menu extends AppCompatActivity {
    Button btnXem_CT, btnXem_VT, btnXem_TK, btnThoat_menu;
    Dialog thoat_dialog;
    Button thoatBtn_yes, thoatBtn_no;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        setControl();
        setExitDialog();
        setEvent();
    }

    private void setExitDialog(){
        thoat_dialog= new Dialog(this);
        thoat_dialog.setContentView(R.layout.thoat_dialog);
        thoat_dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        thoat_dialog.setCancelable(false);
        thoatBtn_yes =thoat_dialog.findViewById(R.id.thoatBtn_yes);
        thoatBtn_no =thoat_dialog.findViewById(R.id.thoatBtn_no);

        thoatBtn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Menu.this.finishAffinity();
                System.exit(0);
            }
        });

        thoatBtn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thoat_dialog.dismiss();
            }
        });
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
                thoat_dialog.show();
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
        actionBar.hide();
//        actionBar.setDisplayHomeAsUpEnabled(true);
    }
}