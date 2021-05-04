package com.buoi2.quanlyvanchuyen;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.buoi2.quanlyvanchuyen.DAO.VatTuDAO;
import com.buoi2.quanlyvanchuyen.bean.VatTu;

import java.util.ArrayList;

public class ThemVatTu extends AppCompatActivity {
    Button themVatTuBtn;
    EditText tenVatTuEdt, giaVatTuEdt;
    Spinner donViTinhSp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_vat_tu);
        setActionBar();
        getId();
    }

    private void setActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Thêm Vật Tư");
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

    private void getId() {
        themVatTuBtn = findViewById(R.id.btnThem_themVatTu);
        tenVatTuEdt = findViewById(R.id.edtTenVatTu_themVatTu);
        giaVatTuEdt = findViewById(R.id.edtGiaVatTu_themVatTu);
        donViTinhSp = findViewById(R.id.spDonVi_themVatTu);
    }

    public int xuLyThemVatTu(View view) {
        /**
         *   Hàm xử lý thêm vật tư
         *   @return 0 nếu thành công, -1 nếu thất bại
         */
        boolean check = true;
        String msg = "Lưu ý: ";
        String tenVatTu = tenVatTuEdt.getText().toString();
        if(tenVatTu.length() == 0){
            check = false;
            msg += "\n  - Không để trống tên vật tư.";
        }

        String tmp = giaVatTuEdt.getText().toString();
        int giaVatTu = 0;
        if(tmp.length() == 0){
            check = false;
            msg += "\n  - Không để trống giá vật tư.";
        }
        else{
            giaVatTu = Integer.parseInt(tmp);
        }

        String donViTinh = "";
        System.out.println(donViTinhSp.getSelectedItemPosition());
        int pos = donViTinhSp.getSelectedItemPosition();
        if(pos == 0 | pos > 4){
            check = false;
            msg += "\n  - Chọn đơn vị tính.";
        }
        if (!check){
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            return -1;
        }
        donViTinh += donViTinhSp.getItemAtPosition(pos);

        CSDLVanChuyen database = new CSDLVanChuyen(getBaseContext());
        SQLiteDatabase db = database.getReadableDatabase();
        VatTuDAO vatTuDAO = new VatTuDAO();
        ArrayList<VatTu> ds = VatTuDAO.layDanhSachVatTu(db);
        VatTu vatTu = new VatTu(String.valueOf(ds.size()), tenVatTu, donViTinh, giaVatTu);
        int kiemTra = VatTuDAO.themVatTu(vatTu, database.getWritableDatabase());
        if(kiemTra == -1){
            Toast.makeText(this, "Lỗi! Vui lòng thử lại sau.", Toast.LENGTH_LONG).show();
            return -1;
        }
        else{
            thoatActivity();
        }
        return 0;
    }

    public void huyThemVatTu(View view) {
        /**
         *   Hàm xử lý khi nhấn nút Thoát.
         */
        thoatActivity();
    }

    private void thoatActivity(){
        Intent intent = new Intent(this, DanhSachVatTu.class);
        startActivity(intent);
        finish();
    }

}