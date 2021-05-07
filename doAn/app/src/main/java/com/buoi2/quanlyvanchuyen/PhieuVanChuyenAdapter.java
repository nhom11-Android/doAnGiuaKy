package com.buoi2.quanlyvanchuyen;

import android.content.Context;
import android.content.DialogInterface;
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
import androidx.appcompat.app.AlertDialog;

import com.buoi2.quanlyvanchuyen.DAO.PhieuVanChuyenDAO;
import com.buoi2.quanlyvanchuyen.bean.ChiTietPhieuVanChuyen;
import com.buoi2.quanlyvanchuyen.bean.CongTrinh;
import com.buoi2.quanlyvanchuyen.bean.PhieuVanChuyen;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

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
        ImageButton xoaPhieuBtn = convertView.findViewById(R.id.xoaPhieuVanChuyenBtn_CTLV);
        // gán dữ liệu
        PhieuVanChuyen phieuVanChuyen = data.get(position);
        ngayVanChuyen.setText("Mã phiếu: "+phieuVanChuyen.getMaPhieuVanChuyen() +"\nNgày: "+ phieuVanChuyen.getNgayVanChuyen());

        //set listenner
        chiTietBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // gọi activity chiTietPhieuVanChuyen
                Intent intent  = new Intent(parentContext,SuaPhieuVanChuyen.class);
                intent.putExtra("maPhieuVanChuyen",String.valueOf(phieuVanChuyen.getMaPhieuVanChuyen()));
                intent.putExtra("maCongTrinh",String.valueOf(phieuVanChuyen.getMaCongTrinh()));
                intent.putExtra("ngayVanChuyen",phieuVanChuyen.getNgayVanChuyen());
                parentContext.startActivity(intent);
            }
        });
        xoaPhieuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = LayoutInflater.from(parentContext);
                View canhBaoDialog = layoutInflater.inflate(R.layout.canh_bao_dialog, null);
                MaterialAlertDialogBuilder alertDialogBuilder = new MaterialAlertDialogBuilder(parentContext);
                alertDialogBuilder.setView(canhBaoDialog);
                TextView tenCanhBaoTv = canhBaoDialog.findViewById(R.id.tenCanhBaoTv_dialog);
                TextView canhBaoTv = canhBaoDialog.findViewById(R.id.canhBaoTv_dialog);
                tenCanhBaoTv.setText("Cảnh báo xoá !!!");
                canhBaoTv.setText("Bạn chắc chưa ?");
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("Chắc",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        PhieuVanChuyenDAO.xoaPhieuVanChuyen(phieuVanChuyen.getMaPhieuVanChuyen(),db.getWritableDatabase());
                                        data.remove(position);
                                        notifyDataSetChanged();
                                        Toast.makeText(parentContext, "Xoá thành công !", Toast.LENGTH_SHORT).show();
                                    }
                                })
                        .setNegativeButton("Chưa", // cài đặt nút huỷ hành động
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                AlertDialog alertDialog = alertDialogBuilder.create(); // tạo dialog từ dialog builder
                alertDialog.show();//show diaglog
            }
        });

        return convertView;
    }
}
