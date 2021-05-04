package com.buoi2.quanlyvanchuyen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.buoi2.quanlyvanchuyen.DAO.VatTuDAO;
import com.buoi2.quanlyvanchuyen.bean.VatTu;

import java.util.ArrayList;

public class ThongKePhieuVanChuyen extends AppCompatActivity {
    TableLayout tableLayout1, tableLayout2;
    TableRow tableRow1, tableRow2;
    Spinner danhSachTenVatTuSp;
    ArrayList<VatTu> danhSachVatTu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke_phieu_van_chuyen);
        setActionBar();
        getId();
    }

    private void setActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Thống Kê Phiếu Vận Chuyển");
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.capnhat_bar,menu);
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
                Toast.makeText(this, "Làm mới", Toast.LENGTH_SHORT).show();
                tableLayout1.removeAllViews();
                tableLayout1.addView(tableRow1);
                danhSachTenVatTuSp.setSelection(0);
                tableLayout2.removeAllViews();
                tableLayout2.addView(tableRow2);
                break;
            default:break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getId() {
        /**
         *   Ánh xạ các View
         */
        tableLayout1 = findViewById(R.id.tableLayout1_ThongKePVC);
        tableLayout2 = findViewById(R.id.tableLayout2_ThongKePVC);
        tableRow1 = findViewById(R.id.tableRow1_ThongKePVC);
        tableRow2 = findViewById(R.id.tableRow2_ThongKePVC);
        setDanhSachVatTu();
        danhSachTenVatTuSp = findViewById(R.id.danhSachTenVatTuSp_ThongKePVC);
        danhSachTenVatTuSp.setAdapter(taoArraySpinner());
    }

    private void setDanhSachVatTu(){
        CSDLVanChuyen database = new CSDLVanChuyen(this);
        this.danhSachVatTu = VatTuDAO.layDanhSachVatTu(database.getReadableDatabase());
    }

    private ArrayAdapter taoArraySpinner(){
        ArrayList<String> arraySpinner = new ArrayList<>();
        arraySpinner.add("---");
        for(VatTu vatTu : this.danhSachVatTu){
            arraySpinner.add(vatTu.getTenVatTu());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }

    @SuppressLint("ResourceAsColor")
    private int setTextView_tableLayout2(TextView view){
        view.setTextSize(17);
        view.setTextColor(R.color.black);
        view.setBackgroundColor(0xECE8E8);
        view.setPadding(5,5,5,5);
        return 0;
    }

    @SuppressLint("ResourceAsColor")
    private int setTextView_tableLayout1(TextView view){
        view.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
        view.setText("Vật tư:");
        view.setTextSize(17);
        view.setTextColor(R.color.black);
        view.setBackgroundResource(R.drawable.border);
        view.setPadding(5,5,5,5);
        return 0;
    }

    private int setSpinner_tableLayout1(Spinner view){
        view.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.MATCH_PARENT));
        view.setBackgroundResource(R.drawable.border);
        view.setPadding(5,5,5,5);
        ArrayAdapter<String> adapter = taoArraySpinner();
        view.setAdapter(adapter);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            view.setId(View.generateViewId());
        }
        return 0;
    }

    private int setButton_tableLayout1(Button view){
        view.setText("Thêm");
        view.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setWidth(80);
        view.setPadding(5,5,5,5);
        return 0;
    }

    private TableRow taoTableRow(String maPhieuVanChuyen, String ngay, String tenVatTu){
        /**
         *   Tạo View TableRow
         *   @param vatTu đối tượng cần tạo TableRow.
         *   @return tr(TableRow) nếu thành công, -1 nếu thất bại
         */
        TableRow tableRow = new TableRow(this);
        TextView maPhieuVanChuyenTv = new TextView(this);
        TextView ngayTv = new TextView(this);
        TextView tenVatTuTv = new TextView(this);

        maPhieuVanChuyenTv.setText(maPhieuVanChuyen);
        maPhieuVanChuyenTv.setGravity(1);
        maPhieuVanChuyenTv.setTextSize(17);
        ngayTv.setText(ngay);
        ngayTv.setGravity(1);
        ngayTv.setTextSize(17);
        tenVatTuTv.setText(tenVatTu);
        tenVatTuTv.setGravity(1);
        tenVatTuTv.setTextSize(17);


        tableRow.addView(maPhieuVanChuyenTv);
        tableRow.addView(ngayTv);
        tableRow.addView(tenVatTuTv);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            tableRow.setId(View.generateViewId());
        }
//        tableRow.setClickable(true);
        System.out.println(tableRow.getId());
        return tableRow;
    }

    public void themVatTuTimKiem(View view) {
        TextView textView = new TextView(this);
        Spinner spinner = new Spinner(this);
        Button button = new Button(this);

        setTextView_tableLayout1(textView);
        setSpinner_tableLayout1(spinner);
        setButton_tableLayout1(button);

        TableRow tableRow = new TableRow(this);
        tableRow.addView(textView);
        tableRow.addView(spinner);
        tableRow.addView(button);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            tableRow.setId(View.generateViewId());
        }
        tableLayout1.addView(tableRow);
    }

    public int thongKePVC(View view) {
        tableLayout2.removeAllViews();
        tableLayout2.addView(tableRow2);
        String msg = "Lưu ý: ";
        int pos = danhSachTenVatTuSp.getSelectedItemPosition();
        if(pos == 0 | pos >= danhSachTenVatTuSp.getCount()){
            msg += "\n  - Chọn vật tư cần thống kê.";
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            return -1;
        }
        VatTu vatTu = danhSachVatTu.get(pos - 1);
        try {
            String sql = "SELECT p.maPhieuVanChuyen, p.ngayVanChuyen, c.maVatTu "
                    + "FROM PHIEUVANCHUYEN p, CHITIETPVC c "
                    + "WHERE p.maPhieuVanChuyen=c.maPhieuVanChuyen AND c.maVatTu=" + vatTu.getMaVatTu()
                    + " ORDER BY p.maPhieuVanChuyen";
            CSDLVanChuyen database = new CSDLVanChuyen(this);
            SQLiteDatabase db = database.getReadableDatabase();
            Cursor cursor = db.rawQuery(sql, null);
            TableRow tableRow;
            if(cursor.moveToFirst()){
                do{
                    tableRow = taoTableRow(cursor.getString(0), cursor.getString(1), vatTu.getTenVatTu());
                    tableLayout2.addView(tableRow);
                }while(cursor.moveToNext());
            }
            db.close();
            return 0;
        }
        catch (Exception e){
            return -1;
        }
    }
}