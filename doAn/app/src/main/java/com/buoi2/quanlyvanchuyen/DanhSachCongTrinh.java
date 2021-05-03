package com.buoi2.quanlyvanchuyen;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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
        adapter.setDb(database); //set database cho adapter
        danhSachCongTrinhLv.setAdapter(adapter);
        danhSachCongTrinhLv.setClickable(true);
        //set onclick listener cho listview 
        // TODO: 5/3/2021 kiểm tra lại có cần cái listener này hay không?
        danhSachCongTrinhLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CongTrinh congTrinh = data.get(position);
                Toast.makeText(DanhSachCongTrinh.this, "Bạn chọn "+ congTrinh.getTenCongTrinh(), Toast.LENGTH_SHORT).show();
            }
        });
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
                timKiemCongTrinh();
                break;
            case R.id.them_ATB:
                themCongTrinh() ;  // xử lý thêm công trình
                break;
            case R.id.lamMoi_ATB:
                lamMoiDanhSach();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private int timKiemCongTrinh() {
        try {
            LayoutInflater layoutInflater = LayoutInflater.from(this);
            View timKiemCongTrinhDialog = layoutInflater.inflate(R.layout.tim_kiem_dialog, null); // tìm dialog view layout từ inflater
            MaterialAlertDialogBuilder alertDialogBuilder = new MaterialAlertDialogBuilder(this); // tạo dialog builder : lớp hỗ trợ xây dựng dialog
            alertDialogBuilder.setView(timKiemCongTrinhDialog); // set view tìm được cho dialog
            EditText timKiemCongTrinhEdt = (EditText) timKiemCongTrinhDialog.findViewById(R.id.timKiemEdt_dialog); // lấy control các trường đã tạo trên dialog
            alertDialogBuilder
                    .setCancelable(false)
                    .setPositiveButton("Tìm", // cài đặt nút đồng ý hành động
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    SQLiteDatabase db = database.getWritableDatabase();
                                    String tenCongTrinhcantim = timKiemCongTrinhEdt.getText().toString().trim();
                                    if(tenCongTrinhcantim.isEmpty()==false) {
                                        //todo: xử lý tìm kiếm, có cần chuẩn hoá input tên công trình hay không?
                                        for (int i = 0; i < data.size(); i++) {
                                            if (data.get(i).getTenCongTrinh().equals(tenCongTrinhcantim) == false) {
                                                System.out.println(tenCongTrinhcantim);
                                                System.out.println(data.get(i).getTenCongTrinh());
                                                data.remove(i);//remove những đối tượng không thoả tìm kiếm
                                                adapter.notifyDataSetChanged(); // thôg báo thay đổi dữ liệu
                                                Toast.makeText(DanhSachCongTrinh.this, "Tìm kiếm thành công !", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                        System.out.println("data size: " + data.size());
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
            alertDialog.show();//show diaglo
            return 0;
        } catch (Exception e) {
            return -1;
        }
    }

    /**
     * Xử lý thêm công trình
     * - gọi dialog nhập dữ liệu
     * - add dữ liệu vào database
     * - thông báo lên listview đã có thay đổi dữ liệu
     *
     * @return -1 nếu không thành công, 0 nếu thành công
     */
    private int themCongTrinh() {
        try {
            LayoutInflater layoutInflater = LayoutInflater.from(this);
            View themCongTrinhDialog = layoutInflater.inflate(R.layout.them_cong_trinh_dialog, null); // tìm dialog view layout từ inflater
            MaterialAlertDialogBuilder alertDialogBuilder = new MaterialAlertDialogBuilder(this); // tạo dialog builder : lớp hỗ trợ xây dựng dialog
            alertDialogBuilder.setView(themCongTrinhDialog); // set view tìm được cho dialog
            EditText tenCongTrinhEdt = (EditText) themCongTrinhDialog.findViewById(R.id.tenCongTrinh_TCTdialog); // lấy control các trường đã tạo trên dialog
            EditText diaChiCongTrinhEdt = (EditText) themCongTrinhDialog.findViewById(R.id.diaChiCongTrinh_TCTdialog);
            EditText maCongTrinhEdt = (EditText) themCongTrinhDialog.findViewById(R.id.maCongTrinh_TCTdialog);
            maCongTrinhEdt.setText("Tự động"); // tuỳ chỉnh dialog
            maCongTrinhEdt.setEnabled(false);
            alertDialogBuilder
                    .setCancelable(false)
                    .setPositiveButton("Thêm", // cài đặt nút đồng ý hành động
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    SQLiteDatabase db = database.getWritableDatabase();
                                    String ten = tenCongTrinhEdt.getText().toString().trim();
                                    String dc = diaChiCongTrinhEdt.getText().toString().trim();
                                    if(ten.isEmpty() || dc.isEmpty()){
                                        Toast.makeText(DanhSachCongTrinh.this, "Nhập đầy đủ thông tin để tạo mới!", Toast.LENGTH_SHORT).show();
                                        dialog.cancel();
                                    }
                                    else{
                                        CongTrinh temp = new CongTrinh(ten, dc);
                                        if(CongTrinhDAO.themCongTrinh(temp, db)==0) { // thêm vào csdl thành công
                                            data.add(temp); //thêm user mới vào listview
                                            adapter.notifyDataSetChanged(); // thôg báo thay đổi dữ liệu
                                            Toast.makeText(getApplicationContext(), "Thêm công trình thành công ! Bấm Làm mới nếu chưa xuất hiện trên màn hình !", Toast.LENGTH_SHORT).show();
                                        }
                                        else Toast.makeText(getApplicationContext(), "Thêm công trình không thành công! Báo cáo lỗi cho nhà sản xuất để giải quyết!", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            })
                    .setNegativeButton("Huỷ thêm", // cài đặt nút huỷ hành đọng
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });

            AlertDialog alertDialog = alertDialogBuilder.create(); // tạo dialog từ dialog builder
            alertDialog.show();//show diaglo
            return 0;
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Thêm công trình không thành công !", Toast.LENGTH_SHORT).show();
            return -1;
        }
    }

    /**
     * làm mới danh sách
     * - load lại database từ csdl lên listview
     *
     * @return 0 nếu thành công, -1 nếu thất bại
     */
    private int lamMoiDanhSach() {
        try {
            data = loadData();
            adapter.data = data;
            adapter.notifyDataSetChanged();
            return 0;
        } catch (Exception e) {
            return -1;
        }
    }
}