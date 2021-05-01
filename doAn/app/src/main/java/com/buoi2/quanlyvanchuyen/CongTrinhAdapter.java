package com.buoi2.quanlyvanchuyen;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.buoi2.quanlyvanchuyen.bean.CongTrinh;

import java.util.ArrayList;

public class CongTrinhAdapter extends ArrayAdapter<CongTrinh> {
    Context parentContext;
    int resource;
    ArrayList<CongTrinh> data;

    public CongTrinhAdapter(@NonNull Context context, int resource, ArrayList<CongTrinh> data) {
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
        convertView = LayoutInflater.from(parentContext).inflate(R.layout.cong_trinh_custom_listview,parent,false);
        TextView tenCongTrinhTv = convertView.findViewById(R.id.tenCongTrinhTv_CTLV);
        TextView diaChiCongTrinhTv = convertView.findViewById(R.id.diaChiCongTrinhTv_CTLV);
        CongTrinh congTrinh = data.get(position);
        tenCongTrinhTv.setText(congTrinh.getTenCongTrinh());
        diaChiCongTrinhTv.setText(congTrinh.getDiaChi());
        return convertView;
    }
}
