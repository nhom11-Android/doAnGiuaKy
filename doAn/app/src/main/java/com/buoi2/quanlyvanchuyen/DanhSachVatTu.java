package com.buoi2.quanlyvanchuyen;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.buoi2.quanlyvanchuyen.DAO.VatTuDAO;
import com.buoi2.quanlyvanchuyen.bean.VatTu;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;


public class DanhSachVatTu extends AppCompatActivity {
    TableLayout tableLayout;
    TableRow tr, tr0;
    TextView maVatTu, tenVatTu, donViTinh, gia;
    int chonHang = 0; //  chonHang id của hàng đã chọn trong table.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_vat_tu);
        setActionBar();
        getId();
        getDanhSachVatTu();
        Toast.makeText(this, "DSVT", Toast.LENGTH_SHORT).show();
    }

    private void getId() {
        /**
         *   Ánh xạ các View
         */
        tableLayout = findViewById(R.id.tablelayout_DSVT);
        tr0 = findViewById(R.id.tableRow0_DSVT);
    }

    private void setActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Danh Sách Vật Tư");
        actionBar.setDisplayHomeAsUpEnabled(true);
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
            case R.id.timKiem_ATB:
                Toast.makeText(this, "search", Toast.LENGTH_SHORT).show();
                callTimKiemDialog();
                break;

            case R.id.them_ATB:
                Intent intent = new Intent(this, ThemVatTu.class);
                startActivity(intent);
                finish();
                break;

            case R.id.lamMoi_ATB:
                Toast.makeText(this, "Refresh", Toast.LENGTH_SHORT).show();
                getDanhSachVatTu();
                break;
            default:break;
        }
        return super.onOptionsItemSelected(item);
    }

    private TableRow taoTableRow(VatTu vatTu){
        /**
         *   Tạo View TableRow
         *   @param vatTu đối tượng cần tạo TableRow.
         *   @return tr(TableRow) nếu thành công, -1 nếu thất bại
         */
        tr = new TableRow(this);
        maVatTu = new TextView(this);
        tenVatTu = new TextView(this);
        donViTinh = new TextView(this);
        gia = new TextView(this);

        maVatTu.setText(vatTu.getMaVatTu());
        maVatTu.setGravity(1);
        maVatTu.setTextSize(17);
        tenVatTu.setText(vatTu.getTenVatTu());
        tenVatTu.setGravity(1);
        tenVatTu.setTextSize(17);
        donViTinh.setText(vatTu.getDonViTinh());
        donViTinh.setGravity(1);
        donViTinh.setTextSize(17);
        gia.setText(String.valueOf(vatTu.getGia()));
        gia.setGravity(1);
        gia.setTextSize(17);

        tr.addView(maVatTu);
        tr.addView(tenVatTu);
        tr.addView(donViTinh);
        tr.addView(gia);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            tr.setId(View.generateViewId());
        }
        tr.setClickable(true);
        tr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chonHang = view.getId();
                System.out.println("Chọn row: " + chonHang);
            }
        });
        System.out.println(tr.getId());
        return tr;
    }

    private int getDanhSachVatTu(){
        /**
         *   Lấy danh sách vật tư
         *   @return 0 nếu thành công, -1 nếu thất bại
         */
        tableLayout.removeAllViews();
        tableLayout.addView(tr0);
        System.out.println("DSVT");
        CSDLVanChuyen database = new CSDLVanChuyen(this);
        ArrayList<VatTu> list = VatTuDAO.layDanhSachVatTu(database.getReadableDatabase());
        System.out.println(list.size());
        if(list != null) {
            for (VatTu v : list) {
                System.out.println("/n=========================================== ");
                System.out.println(v.getMaVatTu());
                System.out.println(v.getTenVatTu());
                System.out.println(v.getDonViTinh());
                System.out.println(v.getGia());

                tr = taoTableRow(v);
                tableLayout.addView(tr);
            }
        }
        else{
            System.out.println("Danh sách rỗng");
            return -1;
        }
        return 0;
    }

    public int xuLyXoaVatTu(View view) {
        /**
         *   Hàm xử lý khi nhấn nút Xoá. Hiển thị Dialog hỏi người dùng khi xoá vật tư
         *   @return 0 nếu thành công, -1 nếu chưa chọn vật tư
         */
        if (chonHang == 0){
            Toast.makeText(this, "Chưa chọn vật tư.", Toast.LENGTH_LONG).show();
            return -1;
        }

        TableRow tr1 = findViewById(chonHang);
        TextView maVatTuTv = (TextView) tr1.getChildAt(0);
        String maVatTu = maVatTuTv.getText().toString();
        TextView tenVatTuTv = (TextView) tr1.getChildAt(1);
        String tenVatTu = maVatTuTv.getText().toString();

        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View canhBaoDialog = layoutInflater.inflate(R.layout.canh_bao_dialog, null);
        MaterialAlertDialogBuilder alertDialogBuilder = new MaterialAlertDialogBuilder(this);
        alertDialogBuilder.setView(canhBaoDialog);
        TextView tenCanhBaoTv = canhBaoDialog.findViewById(R.id.tenCanhBaoTv_dialog);
        TextView canhBaoTv = canhBaoDialog.findViewById(R.id.canhBaoTv_dialog);
        tenCanhBaoTv.setText("Xoá?");
        canhBaoTv.setText("Bạn muốn xoá vật tư " + tenVatTu + "?");
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Xoá",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                xoaVatTu(maVatTu);
                                chonHang = 0;
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

    public int xoaVatTu(String maVatTu) {
        /**
         *   Hàm xử lý khi nhấn nút Xoá.
         *   @param chonHang id của hàng đã chọn trong table.
         *   @return 0 nếu thành công, -1 nếu thất bại
         */
        CSDLVanChuyen database = new CSDLVanChuyen(this);
        int kiemTra = VatTuDAO.xoaVatTu(maVatTu, database.getWritableDatabase());
        if(kiemTra == - 1){
            Toast.makeText(DanhSachVatTu.this, "Lỗi! Vui lòng thử lại sau.", Toast.LENGTH_LONG).show();
            return -1;
        }
        else{
            Toast.makeText(DanhSachVatTu.this, "Xoá thành công!", Toast.LENGTH_LONG).show();
        }
        getDanhSachVatTu();
        return 0;
    }

    public int suaVatTu(View view) {
        /**
         *   Hàm xử lý khi nhấn nút Sửa.
         *   @param chonHang id của hàng đã chọn trong table.
         *   @return 0 nếu thành công, -1 nếu thất bại
         */
        System.out.println("Sửa vật tư: " + chonHang);
        if (chonHang != 0){
            TableRow tr1 = findViewById(chonHang);
            VatTu vatTu = new VatTu();
            TextView tmp = (TextView) tr1.getChildAt(0);
            String maVatTu = tmp.getText().toString();
            tmp = (TextView) tr1.getChildAt(1);
            String tenVatTu = tmp.getText().toString();
            tmp = (TextView) tr1.getChildAt(2);
            String donViTinh = tmp.getText().toString();
            tmp = (TextView) tr1.getChildAt(3);
            int giaVatTu = Integer.parseInt(tmp.getText().toString());

            callActivitySuaVatTu(maVatTu, tenVatTu, donViTinh, giaVatTu);
            getDanhSachVatTu();
            return 0;
        }
        else{
            Toast.makeText(this, "Chưa chọn vật tư.", Toast.LENGTH_LONG).show();
            return -1;
        }
    }

    private int callActivitySuaVatTu(String maVatTu, String tenVatTu, String donViTinh, int giaVatTu){
        /**
         *   gọi activity SuaVatTu
         *   @param maVatTu
         *   @param tenVatTu
         *   @param donViTinh
         *   @param giaVatTu
         *   @return 0 nếu thành công, -1 nếu thất bại
         */
        try {
            Intent intent = new Intent(this, SuaVatTu.class);
            intent.putExtra("maVatTu", maVatTu);
            intent.putExtra("tenVatTu", tenVatTu);
            intent.putExtra("donViTinh", donViTinh);
            intent.putExtra("giaVatTu", giaVatTu);
            startActivity(intent);
            finish();
            return 0;
        }
        catch (Exception e) {
            Toast.makeText(this, "Lỗi! Vui lòng thử lại sau.", Toast.LENGTH_SHORT).show();
            return -1;
        }
    }

    private int callTimKiemDialog(){
        /**
         *   Gọi Dialog tìm kiếm
         *   @return 0 nếu thành công
         */
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View timKiemDialog = layoutInflater.inflate(R.layout.tim_kiem_dialog, null);
        MaterialAlertDialogBuilder alertDialogBuilder = new MaterialAlertDialogBuilder(this);
        alertDialogBuilder.setView(timKiemDialog);
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Tìm kiếm",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                EditText timKiemDialogEdt = timKiemDialog.findViewById(R.id.timKiemEdt_dialog);
                                String str = timKiemDialogEdt.getText().toString();
                                System.out.println("Tìm kiếm: " + str);
                                timKiemVatTu(str);
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

    private int timKiemVatTu(String str){
        /**
         *   Tìm kiếm vật tư
         *   @param str chuỗi cần tìm kiếm
         *   @return 0 nếu thành công, -1 nếu thất bại
         */
        int count = tableLayout.getChildCount();
        int i = 1;
        TableRow tr1;
        try {
            while (i < count){
                tr1 = (TableRow) tableLayout.getChildAt(i);
                TextView tmp = (TextView) tr1.getChildAt(1);
                String tenVatTu = tmp.getText().toString();
                System.out.println("So sánh với: " + tenVatTu);
                if (!tenVatTu.contains(str)) {
                    tableLayout.removeViewAt(i);
                }
                else{
                    i++;
                }
            }
            return 0;
        }
        catch(Exception e){
            return -1;
        }
    }
}