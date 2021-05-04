package com.buoi2.quanlyvanchuyen;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.buoi2.quanlyvanchuyen.DAO.ChiTietPhieuVanChuyenDAO;
import com.buoi2.quanlyvanchuyen.DAO.CongTrinhDAO;
import com.buoi2.quanlyvanchuyen.DAO.VatTuDAO;
import com.buoi2.quanlyvanchuyen.bean.ChiTietPhieuVanChuyen;
import com.buoi2.quanlyvanchuyen.bean.CongTrinh;
import com.buoi2.quanlyvanchuyen.bean.VatTu;

import java.util.ArrayList;

public class ChiTietPhieuVanChuyenAdapter extends ArrayAdapter<ChiTietPhieuVanChuyen> {
    Context parentContext;
    int resource;
    ArrayList<ChiTietPhieuVanChuyen> data;
    CSDLVanChuyen db;

    public ChiTietPhieuVanChuyenAdapter(@NonNull Context context, int resource, @NonNull ArrayList<ChiTietPhieuVanChuyen> data) {
        super(context, resource, data);
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

        // ánh xạ
        TextView tenVatTuTv = convertView.findViewById(R.id.tenVatTuTv_CTP_CTLV);
        TextView soLuongTv = convertView.findViewById(R.id.soLuongTv_CTP_CTLV);
        TextView cuLyTv = convertView.findViewById(R.id.cuLyTv_CTP_CTLV);
        ImageButton xoaVatTuBtn = convertView.findViewById(R.id.xoaVatTuBtn_CTP_CTLV);

        // gán data lên view
        ChiTietPhieuVanChuyen chiTietPhieuVanChuyen = data.get(position);
        String tenVT = VatTuDAO.layTenVatTuTheoMaVatTu(chiTietPhieuVanChuyen.getMaVatTu(), db.getReadableDatabase());
        tenVatTuTv.setText(tenVT);
        soLuongTv.setText(chiTietPhieuVanChuyen.getSoLuong());
        cuLyTv.setText(chiTietPhieuVanChuyen.getCuLy());


        // cài đặt listener
        xoaVatTuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (db == null) {
                    Toast.makeText(parentContext, "Mất kết nối đến cơ sở dữ liệu !", Toast.LENGTH_SHORT).show();
                } else {
                    if (ChiTietPhieuVanChuyenDAO.xoaCTPVCById(data.get(position).getId(), db.getWritableDatabase()) == 0) {
                        Toast.makeText(parentContext, "Xoá thành công !", Toast.LENGTH_SHORT).show();
                        data.remove(position);
                        notifyDataSetChanged();
                    } else
                        Toast.makeText(parentContext, "Xoá thất bại !", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return convertView;

    }

    public void setDb(CSDLVanChuyen db) {
        this.db = db;
    }
}
