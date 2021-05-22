package com.buoi2.quanlyvanchuyen;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.buoi2.quanlyvanchuyen.DAO.VatTuDAO;
import com.buoi2.quanlyvanchuyen.bean.VatTu;

import java.io.ByteArrayOutputStream;

public class TestOnly extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_only);
        themVatTu();
    }
    public void themVatTu(){
        CSDLVanChuyen database = new CSDLVanChuyen(this);
        String mvt = "vattu";

//        for(int i=0;i<10;i++){
//            VatTu vatTu = new VatTu(mvt+" "+ String.valueOf(i),"ten "+ String.valueOf(i),"donvi "+String.valueOf(i),1000, );
//            VatTuDAO.themVatTu(vatTu,database.getWritableDatabase());
//        }
    }
}