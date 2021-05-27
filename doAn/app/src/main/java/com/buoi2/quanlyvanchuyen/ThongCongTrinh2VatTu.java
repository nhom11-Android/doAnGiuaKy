package com.buoi2.quanlyvanchuyen;

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
import java.util.HashMap;
import java.util.Map;

public class ThongCongTrinh2VatTu extends AppCompatActivity {
    TableLayout tableLayout1, tableLayout2;
    TableRow tableRow0, tableRow1, tableRow2;
    Spinner danhSachTenVatTu1Sp, danhSachTenVatTu2Sp;
    ArrayList<VatTu> danhSachVatTu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_cong_trinh2_vat_tu);
        setActionBar();
        getId();
    }

    private void setActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Thống Kê Công Trình Theo 2 Loại Vật Tư");
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
                tableLayout1.addView(tableRow0);
                tableLayout1.addView(tableRow1);
                danhSachTenVatTu1Sp.setSelection(0);
                danhSachTenVatTu2Sp.setSelection(0);
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
        tableLayout1 = findViewById(R.id.tableLayout1_ThongKeCT2);
        tableLayout2 = findViewById(R.id.tableLayout2_ThongKeCT2);
        tableRow0 = findViewById(R.id.tableRow0_ThongKeCT2);
        tableRow1 = findViewById(R.id.tableRow1_ThongKeCT2);
        tableRow2 = findViewById(R.id.tableRow2_ThongKeCT2);
        setDanhSachVatTu();
        danhSachTenVatTu1Sp = findViewById(R.id.danhSachTenVatTuSp1_ThongKeCT2);
        danhSachTenVatTu2Sp = findViewById(R.id.danhSachTenVatTuSp2_ThongKeCT2);
        danhSachTenVatTu1Sp.setAdapter(taoArraySpinner());
        danhSachTenVatTu2Sp.setAdapter(taoArraySpinner());
    }

    private void setDanhSachVatTu(){
        /**
         *   khởi tạo danh sách vật tư
         */
        CSDLVanChuyen database = new CSDLVanChuyen(this);
        this.danhSachVatTu = VatTuDAO.danhSachVatTu(database.getReadableDatabase());
    }

    private ArrayAdapter taoArraySpinner(){
        /**
         *   Thêm giá trị cho Spinner
         */
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

    private TableRow taoTableRow(String maCongTrinh, String tenVatTu1, String tenVatTu2){
        /**
         *   Tạo TableRow
         *   @param maCongTrinh tham số của TableRow.
         *   @param tenVatTu1 tham số của TableRow.
         *   @param tenVatTu2 tham số của TableRow.
         *   @return tableRow(TableRow) nếu thành công, null nếu thất bại
         */
        String tenCongTrinh = "";
        CSDLVanChuyen database = new CSDLVanChuyen(this);
        SQLiteDatabase db = database.getReadableDatabase();
        String sql = "SELECT * FROM CONGTRINH WHERE maCongTrinh=" + maCongTrinh;
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToNext()){
            tenCongTrinh = cursor.getString(1);
        }
        else{
            return null;
        }
        TableRow tableRow = new TableRow(this);
        TextView maCongTrinhTv = new TextView(this);
        TextView tenCongTrinhTv = new TextView(this);
        TextView tenVatTu1Tv = new TextView(this);
        TextView tenVatTu2Tv = new TextView(this);
        TextView ngayVanChuyenTv = new TextView(this);
        // tạo Textview maCongTringTv
        maCongTrinhTv.setText(maCongTrinh);
        maCongTrinhTv.setGravity(1);
        maCongTrinhTv.setTextSize(17);
        // tạo Textview tenCongTrinhTv
        tenCongTrinhTv.setText(tenCongTrinh);
        tenCongTrinhTv.setGravity(1);
        tenCongTrinhTv.setTextSize(17);
        // tạo Textview tenVatTu1Tv
        tenVatTu1Tv.setText(tenVatTu1);
        tenVatTu1Tv.setGravity(1);
        tenVatTu1Tv.setTextSize(17);
        // tạo Textview tenVatTu2Tv
        tenVatTu2Tv.setText(tenVatTu2);
        tenVatTu2Tv.setGravity(1);
        tenVatTu2Tv.setTextSize(17);

        tableRow.addView(maCongTrinhTv);
        tableRow.addView(tenCongTrinhTv);
        tableRow.addView(tenVatTu1Tv);
        tableRow.addView(tenVatTu2Tv);
        tableRow.addView(ngayVanChuyenTv);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            tableRow.setId(View.generateViewId());
        }
        return tableRow;
    }

    public int thongKeCT(View view) {
        /**
         *   Xử lý khi nhấn Kết quả
         *   @return 0 nếu thành công, null nếu thất bại
         */
        tableLayout2.removeAllViews();
        tableLayout2.addView(tableRow2);
        String msg = "Lưu ý: ";
        int pos1 = danhSachTenVatTu1Sp.getSelectedItemPosition();
        if(pos1 == 0 | pos1 >= danhSachTenVatTu1Sp.getCount()){
            msg += "\n  - Chọn vật tư cần thống kê.";
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            return -1;
        }
        int pos2 = danhSachTenVatTu2Sp.getSelectedItemPosition();
        if(pos2 == 0 | pos2 >= danhSachTenVatTu2Sp.getCount()){
            msg += "\n  - Chọn vật tư cần thống kê.";
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            return -1;
        }
        VatTu vatTu1 = danhSachVatTu.get(pos1 - 1);
        VatTu vatTu2 = danhSachVatTu.get(pos2 - 1);
        if (vatTu1.getTenVatTu().equals(vatTu2.getTenVatTu())){
            Toast.makeText(this, "Vui lòng chọn 2 loại vật tư khác nhau!", Toast.LENGTH_LONG).show();
        }
        try {
            String sql = "SELECT DISTINCT c.maCongTrinh, c.tenCongTrinh, ct.maVattu "
                    + "FROM PHIEUVANCHUYEN p, CHITIETPVC ct, CONGTRINH c "
                    + "WHERE p.maPhieuVanChuyen=ct.maPhieuVanChuyen AND p.maCongTrinh=c.maCongTrinh AND (ct.maVatTu='" + vatTu1.getMaVatTu() + "' OR ct.maVatTu='" + vatTu2.getMaVatTu() + "') "
                    + "ORDER BY c.maCongTrinh";
            CSDLVanChuyen database = new CSDLVanChuyen(this);
            SQLiteDatabase db = database.getReadableDatabase();
            Cursor cursor = db.rawQuery(sql, null);
            //Chuyển kết quả vào mảng Map<String, Integer>
            //Key = maCongTrinh, value = 0 (false), nếu key đã có sẽ đặt lại value = 1 (true)
            Map<String, Integer> kq = new HashMap<String, Integer>();
            String maCongTrinh = "";
            if(cursor.moveToFirst()){
                do{
                    maCongTrinh = cursor.getString(0);
                    if(kq.containsKey(maCongTrinh)){
                        kq.remove(maCongTrinh);
                        kq.put(maCongTrinh, 1);
                    }
                    else{
                        kq.put(maCongTrinh, 0);
                    }
                }while(cursor.moveToNext());
            }
            db.close();
            // Kiểm tra mảng kq, tạo TableRow và thêm vào bảng
            TableRow tableRow;
            for (Map.Entry<String, Integer> e : kq.entrySet()){
                if(e.getValue() == 1){
                    System.out.println(e.getKey() + " " + e.getValue());
                    tableRow = taoTableRow(e.getKey(), vatTu1.getTenVatTu(), vatTu2.getTenVatTu());
                    tableLayout2.addView(tableRow);
                }
            }
            return 0;
        }
        catch (Exception e){
            return -1;
        }
    }
}