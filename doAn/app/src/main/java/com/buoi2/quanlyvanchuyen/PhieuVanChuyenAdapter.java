package com.buoi2.quanlyvanchuyen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.buoi2.quanlyvanchuyen.bean.CongTrinh;
import com.buoi2.quanlyvanchuyen.bean.PhieuVanChuyen;

import java.util.ArrayList;

public class PhieuVanChuyenAdapter extends ArrayAdapter<PhieuVanChuyen> {
    Context parentContext;
    int resource;
    ArrayList<PhieuVanChuyen> data;
    CSDLVanChuyen db;

    public void setDb(CSDLVanChuyen db) {
        this.db = db;
    }

    public PhieuVanChuyenAdapter(@NonNull Context context, int resource, ArrayList<PhieuVanChuyen> data) {
        super(context, resource);
        this.parentContext = context;
        this.resource = resource;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parentContext).inflate(R.layout.danh_sach_phieu_van_chuyen_custom_listview, parent, false);
        //ánh xạ
        TextView ngayVanChuyen = convertView.findViewById(R.id.ngayVanChuyenTv_CTLV);
        ImageButton chiTietBtn = convertView.findViewById(R.id.chiTietPhieuVanChuyenBtn_CTLV);
        // gán dữ liệu
        PhieuVanChuyen phieuVanChuyen = data.get(position);
        ngayVanChuyen.setText(phieuVanChuyen.getNgayVanChuyen());

        //

        return convertView;
    }
}
