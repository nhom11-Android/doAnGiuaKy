package com.buoi2.quanlyvanchuyen;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;

import com.buoi2.quanlyvanchuyen.DAO.VatTuDAO;
import com.buoi2.quanlyvanchuyen.bean.VatTu;


public class MainActivity extends AppCompatActivity {
    ImageView logoImgView;
    Animation xoay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setControl();
        setEvent();
    }

    private void setEvent() {
        logoImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Runnable r = new Runnable() {
                    @Override
                    public void run(){
                        Intent intent= new Intent(MainActivity.this, Menu.class);
                        startActivity(intent);
                    }
                };
                Handler h = new Handler();
                h.postDelayed(r, 3000);
                xoay= AnimationUtils.loadAnimation(getApplicationContext(), R.anim.xoay);
                logoImgView.startAnimation(xoay);
//                overridePendingTransition(android.R.anim.cycle_interpolator, android.R.anim.cycle_interpolator);
            }
        });
    }

    private void setControl() {
        logoImgView= findViewById(R.id.logoImgView);
        // cài đặt tiêu đề cho action bar
        ActionBar actionBar = (ActionBar) getSupportActionBar();
        actionBar.hide();
//        actionBar.setTitle("Thống Kê");
//        actionBar.setDisplayHomeAsUpEnabled(true);
    }
}