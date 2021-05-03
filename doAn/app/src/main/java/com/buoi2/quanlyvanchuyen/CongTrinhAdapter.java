package com.buoi2.quanlyvanchuyen;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.buoi2.quanlyvanchuyen.DAO.CongTrinhDAO;
import com.buoi2.quanlyvanchuyen.bean.CongTrinh;

import java.util.ArrayList;

public class CongTrinhAdapter extends ArrayAdapter<CongTrinh> {
    Context parentContext;
    int resource;
    ArrayList<CongTrinh> data;
    CSDLVanChuyen db;

    public void setDb(CSDLVanChuyen db) {
        this.db = db;
    }

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
        convertView = LayoutInflater.from(parentContext).inflate(R.layout.cong_trinh_custom_listview, parent, false);

        // ánh xạ
        TextView tenCongTrinhTv = convertView.findViewById(R.id.tenCongTrinhTv_CTLV);
        TextView diaChiCongTrinhTv = convertView.findViewById(R.id.diaChiCongTrinhTv_CTLV);
        ImageButton chiTietCongTrinhBtn = convertView.findViewById(R.id.chiTietBtn_CTLV);
        ImageButton xoaCongTrinhBtn = convertView.findViewById(R.id.xoaBtn_CTLV);

        // gán data lên view
        CongTrinh congTrinh = data.get(position);
        tenCongTrinhTv.setText(congTrinh.getTenCongTrinh());
        diaChiCongTrinhTv.setText(congTrinh.getDiaChi());

        // cài đặt listener
        chiTietCongTrinhBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(parentContext, "chi tiết công trình", Toast.LENGTH_SHORT).show();
                // đóng db
                db.close();
                Intent congTrinhIntent = new Intent(parentContext,ChiTietCongTrinh.class);
                congTrinhIntent.putExtra("maCongTrinh",data.get(position).getMaCongTrinh());
                parentContext.startActivity(congTrinhIntent);
            }
        });
        xoaCongTrinhBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(parentContext, "xoá công trình", Toast.LENGTH_SHORT).show();
                // TODO: 5/2/2021 xử lý xoá: Hỏi có đồng ý xoá hay không?
                if (db == null) {
                    Toast.makeText(parentContext, "Mất kết nối đến cơ sở dữ liệu !", Toast.LENGTH_SHORT).show();
                } else {
                    if (CongTrinhDAO.xoaCongTrinh(data.get(position).getMaCongTrinh(), db.getWritableDatabase()) == 0) {
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
}
