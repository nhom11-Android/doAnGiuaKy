package com.buoi2.quanlyvanchuyen;

import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.buoi2.quanlyvanchuyen.DAO.CongTrinhDAO;
import com.buoi2.quanlyvanchuyen.bean.CongTrinh;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.dialog.MaterialDialogs;

import java.util.ArrayList;

public class DanhSachCongTrinh extends AppCompatActivity {
    ListView danhSachCongTrinhLv;
    ArrayList<CongTrinh> data;
    CSDLVanChuyen database;
    private CongTrinhAdapter adapter; // lưu trữ biến cục bộ adapter

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_cong_trinh);
        setControl();
        setEvent();
    }

    /**
     * load dữ liệu từ database
     *
     * @return arrayList công trình
     */
    private ArrayList<CongTrinh> loadData() {
        ArrayList<CongTrinh> danhSachCongTrinh = CongTrinhDAO.layDanhSachCongTrinh(database.getReadableDatabase());
        return danhSachCongTrinh;
    }

    private void setEvent() {
        // set adapter lên listview
        CongTrinhAdapter adapter = new CongTrinhAdapter(this, R.layout.cong_trinh_custom_listview, data);
        this.adapter = adapter;
        danhSachCongTrinhLv.setAdapter(adapter);
    }

    private void setControl() {
        danhSachCongTrinhLv = findViewById(R.id.danhSachCongTrinhLv_DSCT);
        // cài đặt tiêu đề cho action bar
        ActionBar actionBar = (ActionBar) getSupportActionBar();
        actionBar.setTitle("Công trình");
        actionBar.setDisplayHomeAsUpEnabled(true);
        // khai báo csdl
        database = new CSDLVanChuyen(this);
        data = loadData();// load dữ liệu lên arraylist
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.them_capnhat_timkiem_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //todo: xử lý home
                Toast.makeText(this, "quay trở lại", Toast.LENGTH_SHORT).show();
                break;
            case R.id.timKiem_ATB:
                // TODO: xử lý tìm kiếm
                Toast.makeText(this, "tìm kiếm", Toast.LENGTH_SHORT).show();
                break;
            case R.id.them_ATB:
                themCongTrinh(); // xử lý thêm công trình
                Toast.makeText(this, "Thêm công trình thành công ! Bấm Làm mới nếu chưa xuất hiện trên màn hình !", Toast.LENGTH_SHORT).show();
                break;
            case R.id.lamMoi_ATB:
                lamMoiDanhSach();
                Toast.makeText(this, "Làm mới thành công !", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Xử lý thêm công trình
     *  - gọi dialog nhập dữ liệu
     *  - add dữ liệu vào database
     *  - thông báo lên listview đã có thay đổi dữ liệu
     * @return -1 nếu không thành công, 0 nếu thành công
     */
    private int themCongTrinh() {
        try {
            LayoutInflater layoutInflater = LayoutInflater.from(this);
            View themCongTrinhDialog = layoutInflater.inflate(R.layout.them_cong_trinh_dialog, null);
            MaterialAlertDialogBuilder alertDialogBuilder = new MaterialAlertDialogBuilder(this);
            alertDialogBuilder.setView(themCongTrinhDialog);
            EditText tenCongTrinhDialog = (EditText) themCongTrinhDialog.findViewById(R.id.tenCongTrinh_TCTdialog);
            EditText diaChiCongTrinhDialog = (EditText) themCongTrinhDialog.findViewById(R.id.diaChiCongTrinh_TCTdialog);
            EditText maCongTrinhDialog = (EditText) themCongTrinhDialog.findViewById(R.id.maCongTrinh_TCTdialog);
            maCongTrinhDialog.setText("Tự động");
            maCongTrinhDialog.setEnabled(false);
            alertDialogBuilder
                    .setCancelable(false)
                    .setPositiveButton("Thêm",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    SQLiteDatabase db = database.getWritableDatabase();
                                    String ten = tenCongTrinhDialog.getText().toString().trim();
                                    String dc = diaChiCongTrinhDialog.getText().toString().trim();
                                    CongTrinh temp = new CongTrinh(ten, dc);
                                    CongTrinhDAO.themCongTrinh(temp, db);
                                    data.add(temp); //thêm user mới vào listview
                                    adapter.notifyDataSetChanged(); // thôg báo thay đổi dữ liệu
//                                Toast.makeText(DanhSachCongTrinh.this, "tên: "+tenCongTrinhDialog.getText() +"\bđịa chỉ: "+diaChiCongTrinhDialog.getText(), Toast.LENGTH_SHORT).show();

                                }
                            })
                    .setNegativeButton("Huỷ thêm",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
            return 0;
        }catch (Exception e){
            return -1;
        }
    }

    /**
     * làm mới danh sách
     *  - load lại database từ csdl lên listview
     */
    private void lamMoiDanhSach() {
        data = loadData();
        adapter.notifyDataSetChanged();
    }
}