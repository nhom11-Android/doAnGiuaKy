package com.buoi2.quanlyvanchuyen;

import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.buoi2.quanlyvanchuyen.DAO.LoaiDonViTinhDAO;
import com.buoi2.quanlyvanchuyen.DAO.VatTuDAO;
import com.buoi2.quanlyvanchuyen.bean.LoaiDonViTinh;
import com.buoi2.quanlyvanchuyen.bean.VatTu;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;

public class ThemVatTu extends AppCompatActivity {
    Button themVatTuBtn;
    EditText tenVatTuEdt, giaVatTuEdt;
    Spinner donViTinhSp;
    ArrayList<LoaiDonViTinh> danhSachDonViTinh;
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.capnhat_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.lamMoi_CNB:
                lamMoi();
                break;
            default:break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getId() {
        themVatTuBtn = findViewById(R.id.btnThem_themVatTu);
        tenVatTuEdt = findViewById(R.id.edtTenVatTu_themVatTu);
        giaVatTuEdt = findViewById(R.id.edtGiaVatTu_themVatTu);
        donViTinhSp = findViewById(R.id.spDonVi_themVatTu);
        setDanhSachDonViTinh();
        donViTinhSp.setAdapter(taoArraySpinner());
    }

    private int lamMoi(){
        try{
            tenVatTuEdt.setText("");
            giaVatTuEdt.setText("");
            setDanhSachDonViTinh();
            donViTinhSp.setAdapter(taoArraySpinner());
            donViTinhSp.setSelection(0);
            return 0;
        }
        catch (Exception e){
            return -1;
        }
    }

    private void setDanhSachDonViTinh(){
        CSDLVanChuyen database = new CSDLVanChuyen(this);
        this.danhSachDonViTinh = LoaiDonViTinhDAO.layDanhSachDonViTinh(database.getReadableDatabase());
    }

    private ArrayAdapter taoArraySpinner(){
        ArrayList<String> arraySpinner = new ArrayList<>();
        arraySpinner.add("---");
        for(LoaiDonViTinh donViTinh : this.danhSachDonViTinh){
            arraySpinner.add(donViTinh.getTenDonViTinh());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }

    public int xuLyThemVatTu(View view) {
        /**
         *   Hàm xử lý thêm vật tư
         *   @return 0 nếu thành công, -1 nếu thất bại
         */
        boolean check = true;
        String msg = "Lưu ý: ";
        //Kiểm tra View tenVatTu
        String tenVatTu = tenVatTuEdt.getText().toString();
        if(tenVatTu.length() == 0){
            check = false;
            msg += "\n  - Không để trống tên vật tư.";
        }
        //Kiểm tra View giaVatTu
        String tmp = giaVatTuEdt.getText().toString();
        int giaVatTu = 0;
        if(tmp.length() == 0){
            check = false;
            msg += "\n  - Không để trống giá vật tư.";
        }
        else{
            giaVatTu = Integer.parseInt(tmp);
        }
        //Kiểm tra donViTinh
        String donViTinh = "";
        System.out.println(donViTinhSp.getSelectedItemPosition());
        int pos = donViTinhSp.getSelectedItemPosition();
        if(pos == 0 | pos >= donViTinhSp.getCount()){
            check = false;
            msg += "\n  - Chọn đơn vị tính.";
        }
        donViTinh += donViTinhSp.getItemAtPosition(pos);
        if (!check){
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            return -1;
        }

        CSDLVanChuyen database = new CSDLVanChuyen(getBaseContext());
        SQLiteDatabase db = database.getReadableDatabase();
        ArrayList<VatTu> ds = VatTuDAO.layDanhSachVatTu(db);
        //Kiểm tra vật tư đã có trong danh sách chưa
        for(VatTu v : ds){
            if(tenVatTu.equals(v.getTenVatTu())){
                Toast.makeText(this, "Vật tư \"" + tenVatTu + "\" đã có.", Toast.LENGTH_LONG).show();
                return -1;

            }
        }
        //Thêm vật tư mới
        VatTu vatTu = new VatTu(String.valueOf(ds.size() + 1), tenVatTu, donViTinh, giaVatTu);
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
        finish();
    }

    public int themLoaiDonViTinh(View view) {
        /**
         *   Gọi Dialog tìm kiếm
         *   @return 0 nếu thành công
         */
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View themDonViTinhDialog = layoutInflater.inflate(R.layout.them_don_vi_tinh_dialog, null);
        MaterialAlertDialogBuilder alertDialogBuilder = new MaterialAlertDialogBuilder(this);
        alertDialogBuilder.setView(themDonViTinhDialog);
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Thêm",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                EditText donViTinhDialogEdt = themDonViTinhDialog.findViewById(R.id.donViTinhEdt_dialog);
                                String str = donViTinhDialogEdt.getText().toString();
                                if(kiemTraThemDonViTinh(str) == 0){
                                    CSDLVanChuyen database = new CSDLVanChuyen(getBaseContext());
                                    int kt = LoaiDonViTinhDAO.themDonViTinh(new LoaiDonViTinh(str), database.getWritableDatabase());
                                    if (kt == -1){
                                        Toast.makeText(getApplicationContext(), "Lỗi! Vui lòng thử lại sau.", Toast.LENGTH_LONG).show();
                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(), "Thêm thành công.", Toast.LENGTH_LONG).show();
                                    }
                                }
                                else{
                                    Toast.makeText(getApplicationContext(), "Loại đơn vị tính đã có", Toast.LENGTH_LONG).show();
                                }

                            }
                        })
                .setNegativeButton("Huỷ", // cài đặt nút huỷ hành đọng
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alertDialog = alertDialogBuilder.create(); // tạo dialog từ dialog builder
        alertDialog.show();//show diaglog
        return 0;
    }

    private int kiemTraThemDonViTinh(String donViTinh){
        for(LoaiDonViTinh d : danhSachDonViTinh){
            if(donViTinh.equals(d.getTenDonViTinh())){
                return -1;
            }
        }
        return 0;
    }
}