package com.buoi2.quanlyvanchuyen;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.buoi2.quanlyvanchuyen.DAO.VatTuDAO;
import com.buoi2.quanlyvanchuyen.bean.VatTu;

import java.util.ArrayList;

public class ThongKeCongTrinh extends AppCompatActivity {
    TableLayout tableLayout1, tableLayout2;
    TableRow tableRow1, tableRow2;
    Spinner danhSachTenVatTuSp;
    ArrayList<VatTu> danhSachVatTu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke_cong_trinh);
        setActionBar();
        getId();
    }

    private void setActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Thống Kê Công Trình");
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
        tableLayout1 = findViewById(R.id.tableLayout1_ThongKeCT);
        tableLayout2 = findViewById(R.id.tableLayout2_ThongKeCT);
        tableRow1 = findViewById(R.id.tableRow1_ThongKeCT);
        tableRow2 = findViewById(R.id.tableRow2_ThongKeCT);
        setDanhSachVatTu();
        danhSachTenVatTuSp = findViewById(R.id.danhSachTenVatTuSp_ThongKeCT);
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
        view.setText("Vật tư: ");
        view.setTextSize(17);
        view.setTextColor(R.color.black);
        view.setBackgroundResource(R.drawable.border);
        view.setPadding(5,5,5,5);
        return 0;
    }

    private int setSpinner_tableLayout1(TextView view){
        view.setHeight(TableRow.LayoutParams.MATCH_PARENT);
        view.setWidth(TableRow.LayoutParams.WRAP_CONTENT);
        view.setBackgroundResource(R.drawable.border);
        view.setPadding(5,5,5,5);
        return 0;
    }

    private int setButton_tableLayout1(TextView view){
        view.setText("Thêm");
        view.setHeight(40);
        view.setWidth(80);
        view.setBackgroundColor(0xFF9800);
        view.setPadding(5,5,5,5);
        return 0;
    }

    private TableRow taoTableRow(String maCongTrinh, String tenCongTrinh, String tenVatTu, String donViTinh, String soLuong, String cuLy, String ngayVanChuyen){
        /**
         *   Tạo View TableRow
         *   @param vatTu đối tượng cần tạo TableRow.
         *   @return tr(TableRow) nếu thành công, -1 nếu thất bại
         */
        TableRow tableRow = new TableRow(this);
        TextView maCongTrinhTv = new TextView(this);
        TextView tenCongTrinhTv = new TextView(this);
        TextView tenVatTuTv = new TextView(this);
        TextView donViTinhTv = new TextView(this);
        TextView soLuongTv = new TextView(this);
        TextView cuLyTv = new TextView(this);
        TextView ngayVanChuyenTv = new TextView(this);

        maCongTrinhTv.setText(maCongTrinh);
        maCongTrinhTv.setGravity(1);
        maCongTrinhTv.setTextSize(17);

        tenCongTrinhTv.setText(tenCongTrinh);
        tenCongTrinhTv.setGravity(1);
        tenCongTrinhTv.setTextSize(17);

        tenVatTuTv.setText(tenVatTu);
        tenVatTuTv.setGravity(1);
        tenVatTuTv.setTextSize(17);

        donViTinhTv.setText(donViTinh);
        donViTinhTv.setGravity(1);
        donViTinhTv.setTextSize(17);

        soLuongTv.setText(soLuong);
        soLuongTv.setGravity(1);
        soLuongTv.setTextSize(17);

        cuLyTv.setText(cuLy);
        cuLyTv.setGravity(1);
        cuLyTv.setTextSize(17);

        ngayVanChuyenTv.setText(ngayVanChuyen);
        ngayVanChuyenTv.setGravity(1);
        ngayVanChuyenTv.setTextSize(17);

        tableRow.addView(maCongTrinhTv);
        tableRow.addView(tenCongTrinhTv);
        tableRow.addView(tenVatTuTv);
        tableRow.addView(donViTinhTv);
        tableRow.addView(soLuongTv);
        tableRow.addView(cuLyTv);
        tableRow.addView(ngayVanChuyenTv);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            tableRow.setId(View.generateViewId());
        }
//        tableRow.setClickable(true);
        System.out.println(tableRow.getId());
        return tableRow;
    }

    public int thongKeCT(View view) {
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
            String sql = "SELECT c.maCongTrinh, c.tenCongTrinh, p.maPhieuVanChuyen, ct.soLuong, ct.cuLy, p.ngayVanChuyen "
                    + "FROM PHIEUVANCHUYEN p, CHITIETPVC ct, CONGTRINH c "
                    + "WHERE p.maPhieuVanChuyen=ct.maPhieuVanChuyen AND p.maCongTrinh=c.maCongTrinh AND ct.maVatTu=" + vatTu.getMaVatTu()
                    + " ORDER BY p.maPhieuVanChuyen";
            CSDLVanChuyen database = new CSDLVanChuyen(this);
            SQLiteDatabase db = database.getReadableDatabase();
            Cursor cursor = db.rawQuery(sql, null);
            TableRow tableRow;
            if(cursor.moveToFirst()){
                do{
                    tableRow = taoTableRow(cursor.getString(0), cursor.getString(1), vatTu.getTenVatTu(), vatTu.getDonViTinh(),cursor.getString(3), cursor.getString(4), cursor.getString(5));
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