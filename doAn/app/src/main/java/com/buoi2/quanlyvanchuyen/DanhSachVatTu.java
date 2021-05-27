package com.buoi2.quanlyvanchuyen;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.buoi2.quanlyvanchuyen.DAO.CongTrinhDAO;
import com.buoi2.quanlyvanchuyen.DAO.VatTuDAO;
import com.buoi2.quanlyvanchuyen.bean.CongTrinh;
import com.buoi2.quanlyvanchuyen.bean.VatTu;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;


public class DanhSachVatTu extends AppCompatActivity {
    ListView danhSachVatTuLv;
    ArrayList<VatTu> data;
    private VatTuAdapter adapter;
    CSDLVanChuyen database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_vat_tu);
        setControl();
        setEvent();

    }

    private void setControl() {
        danhSachVatTuLv = findViewById(R.id.danhSachVatTuLv_DSVT);
        // cài đặt tiêu đề cho action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Danh Sách Vật Tư");
        actionBar.setDisplayHomeAsUpEnabled(true);
        // khai báo csdl
        database = new CSDLVanChuyen(this);
        data = loadData();// load dữ liệu lên arraylist
    }

    private void setEvent() {
        // set adapter lên listview
        VatTuAdapter adapter = new VatTuAdapter(this, R.layout.cong_trinh_custom_listview, data);
        this.adapter = adapter;
        adapter.setDb(database); //set database cho adapter
        danhSachVatTuLv.setAdapter(adapter);
        danhSachVatTuLv.setClickable(true);
        //set onclick listener cho listview
        danhSachVatTuLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                VatTu vatTu = data.get(position);
                Toast.makeText(DanhSachVatTu.this, "Bạn chọn " + vatTu.getTenVatTu(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.them_capnhat_timkiem_bar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.timKiem_ATB:
                Toast.makeText(this, "Tìm kiếm", Toast.LENGTH_SHORT).show();
                timKiemVatTu();
                break;
            case R.id.them_ATB:
                Intent intent = new Intent(this, ThemVatTu.class);
                startActivity(intent);
                break;

            case R.id.lamMoi_ATB:
                Toast.makeText(this, "Làm mới", Toast.LENGTH_SHORT).show();
                lamMoiDanhSach();
                break;
            default:break;
        }
        return super.onOptionsItemSelected(item);
    }

    private ArrayList<VatTu> loadData() {
        /**
         * load dữ liệu từ database
         *
         * @return arrayList công trình
         */
        ArrayList<VatTu> danhSachVatTu = VatTuDAO.danhSachVatTu(database.getReadableDatabase());
        return danhSachVatTu;
    }

    private int lamMoiDanhSach() {
        /**
         * làm mới danh sách
         * - load lại database từ csdl lên listview
         *
         * @return 0 nếu thành công, -1 nếu thất bại
         */
        try {
            data = loadData();
            adapter.data = data;
            adapter.notifyDataSetChanged();
            return 0;
        } catch (Exception e) {
            return -1;
        }
    }

    private int timKiemVatTu() {
        /**
         *   Tìm kiếm vật tư
         *   @return 0 nếu thành công, -1 nếu thất bại
         */
        try {
            LayoutInflater layoutInflater = LayoutInflater.from(this);
            View timKiemVatTuDialog = layoutInflater.inflate(R.layout.tim_kiem_dialog, null); // tìm dialog view layout từ inflater
            MaterialAlertDialogBuilder alertDialogBuilder = new MaterialAlertDialogBuilder(this); // tạo dialog builder : lớp hỗ trợ xây dựng dialog
            alertDialogBuilder.setView(timKiemVatTuDialog); // set view tìm được cho dialog
            EditText timKiemVatTuEdt = (EditText) timKiemVatTuDialog.findViewById(R.id.timKiemEdt_dialog); // lấy control các trường đã tạo trên dialog
            alertDialogBuilder
                    .setCancelable(false)
                    .setPositiveButton("Tìm", // cài đặt nút đồng ý hành động
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    SQLiteDatabase db = database.getWritableDatabase();
                                    String tenCongTrinhcantim = timKiemVatTuEdt.getText().toString().trim().toUpperCase();
                                    if (tenCongTrinhcantim.isEmpty() == false) {
                                        //todo: xử lý tìm kiếm, có cần chuẩn hoá input tên công trình hay không?
                                        for (int i = 0; i < data.size(); i++) {
                                            if (data.get(i).getTenVatTu().toUpperCase().equals(tenCongTrinhcantim) == false) {
                                                System.out.println(tenCongTrinhcantim);
                                                System.out.println(data.get(i).getTenVatTu());
                                                data.remove(i);//remove những đối tượng không thoả tìm kiếm
                                                i--;
                                                adapter.notifyDataSetChanged(); // thôg báo thay đổi dữ liệu
                                                Toast.makeText(DanhSachVatTu.this, "Tìm kiếm thành công !", Toast.LENGTH_SHORT).show();
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
}