package com.buoi2.quanlyvanchuyen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.buoi2.quanlyvanchuyen.DAO.VatTuDAO;
import com.buoi2.quanlyvanchuyen.bean.VatTu;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CSDLVanChuyen database = new CSDLVanChuyen(this);
        VatTuDAO.themVatTu(new VatTu("101","Gach",4000),database.getWritableDatabase());
    }

}