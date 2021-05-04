package com.buoi2.quanlyvanchuyen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.buoi2.quanlyvanchuyen.DAO.VatTuDAO;
import com.buoi2.quanlyvanchuyen.bean.VatTu;

public class SuaVatTu extends AppCompatActivity {
    TextView tenVatTuTv, donViTinhTv;
    EditText giaVatTuEdt;
    VatTu vatTu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_vat_tu);
        getExtra();
        setActionBar();
        getId();
        setThongTin();
    }

    private void getExtra(){
        vatTu = new VatTu();
        Intent intent = getIntent();
        vatTu.setMaVatTu(intent.getStringExtra("maVatTu"));
        vatTu.setTenVatTu(intent.getStringExtra("tenVatTu"));
        vatTu.setDonViTinh(intent.getStringExtra("donViTinh"));
        vatTu.setGia(intent.getIntExtra("giaVatTu", 0));
    }

    private void getId() {
        tenVatTuTv = findViewById(R.id.tenVatTuTv_SuaVatTu);
        donViTinhTv = findViewById(R.id.donViTinhTv_SuaVatTu);
        giaVatTuEdt = findViewById(R.id.giaVatTuEdt_SuaVatTu);
    }

    private void setThongTin(){
        tenVatTuTv.setText("Tên vật tư: " + vatTu.getTenVatTu());
        donViTinhTv.setText("Đơn vị tính: " + vatTu.getDonViTinh());
        giaVatTuEdt.setText(String.valueOf(vatTu.getGia()));
    }

    private void setActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Sửa Vật Tư");
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:break;
        }
        return super.onOptionsItemSelected(item);
    }


    public void huyCapNhatVatTu(View view) {
        thoatActivity();
    }

    public int xuLyCapNhatVatTu(View view) {
        int giaVatTu = 0;
        String tmp = "";
        tmp = giaVatTuEdt.getText().toString();
        if(tmp.length() == 0){
            Toast.makeText(this, "Lưu ý:\n - Không để trống giá vật tư.", Toast.LENGTH_LONG).show();
            return -1;
        }
        giaVatTu = Integer.parseInt(tmp);
        CSDLVanChuyen database = new CSDLVanChuyen(this);
        VatTu vatTu_capNhat = new VatTu(vatTu.getMaVatTu(), vatTu.getTenVatTu(), vatTu.getDonViTinh(), giaVatTu);
        int kiemTra = VatTuDAO.capNhatVatTu(vatTu_capNhat,database.getReadableDatabase());
        if(kiemTra == -1){
            Toast.makeText(this, "Lỗi! Vui lòng thử lại sau.", Toast.LENGTH_LONG).show();
            return -1;
        }
        else{
            Toast.makeText(this, "Cập nhật thành công.", Toast.LENGTH_LONG).show();
            thoatActivity();
            return 0;
        }
    }

    private void thoatActivity(){
        Intent intent = new Intent(this, DanhSachVatTu.class);
        startActivity(intent);
        finish();
    }
}