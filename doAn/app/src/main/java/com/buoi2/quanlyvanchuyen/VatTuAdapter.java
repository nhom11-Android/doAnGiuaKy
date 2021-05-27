package com.buoi2.quanlyvanchuyen;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.buoi2.quanlyvanchuyen.DAO.CongTrinhDAO;
import com.buoi2.quanlyvanchuyen.DAO.VatTuDAO;
import com.buoi2.quanlyvanchuyen.bean.CongTrinh;
import com.buoi2.quanlyvanchuyen.bean.VatTu;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;

import myHelp.MySuperFunc;


public class VatTuAdapter extends ArrayAdapter<VatTu> {
    Context parentContext;
    int resource;
    ArrayList<VatTu> data;
    CSDLVanChuyen database;

    public void setDb(CSDLVanChuyen db){
        this.database = db;
    }

    public VatTuAdapter(@NonNull Context context, int resource, ArrayList<VatTu> data) {
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
        convertView = LayoutInflater.from(parentContext).inflate(R.layout.vat_tu_custom_listview, parent, false);

        // ánh xạ
        ImageView anhVatTuIv = convertView.findViewById(R.id.anhVatTuIv_VTLV);
        TextView tenVatTuTv = convertView.findViewById(R.id.tenVatTuTv_VTLV);
        TextView donViTinhTv = convertView.findViewById(R.id.donViTinhTv_VTLV);
        TextView giaTv = convertView.findViewById(R.id.giaTv_VTLV);
        ImageButton suaVatTu = convertView.findViewById(R.id.suaBtn_VTLV);
        ImageButton xoaCongTrinhBtn = convertView.findViewById(R.id.xoaBtn_VTLV);

        // gán data lên view
        VatTu vatTu = data.get(position);
        anhVatTuIv.setImageBitmap(MySuperFunc.getByteArrayAsBitmap(vatTu.getAnh()));
        tenVatTuTv.setText(vatTu.getTenVatTu());
        donViTinhTv.setText("Đơn vị tính: " + vatTu.getDonViTinh());
        giaTv.setText("Giá: " + String.valueOf(vatTu.getGia()));

        // cài đặt listener
        suaVatTu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = LayoutInflater.from(parentContext);
                View suaVatTuDialog = layoutInflater.inflate(R.layout.sua_vat_tu_dialog, null);
                MaterialAlertDialogBuilder alertDialogBuilder = new MaterialAlertDialogBuilder(parentContext);
                alertDialogBuilder.setView(suaVatTuDialog);
                TextView tenVatTuTv = suaVatTuDialog.findViewById(R.id.tenVatTuTv_SuaVatTuDialog);
                EditText giaEdt = suaVatTuDialog.findViewById(R.id.giaEdt_SuaVatTuDialog);
                tenVatTuTv.setText("Tên: " + data.get(position).getTenVatTu());
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("Cập nhật",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        if (database == null) {
                                            Toast.makeText(parentContext, "Mất kết nối đến cơ sở dữ liệu !", Toast.LENGTH_SHORT).show();
                                        } else {
                                            VatTu vatTuCapNhat = new VatTu(vatTu.getMaVatTu(), vatTu.getTenVatTu(), vatTu.getDonViTinh(), vatTu.getGia(), vatTu.getAnh());
                                            vatTuCapNhat.setGia(Integer.parseInt(giaEdt.getText().toString()));
                                            if (VatTuDAO.capNhatVatTu(vatTuCapNhat, database.getWritableDatabase()) > 0) {
                                                Toast.makeText(parentContext, "Cập nhật thành công !", Toast.LENGTH_SHORT).show();
                                                vatTu.setGia(vatTuCapNhat.getGia());
                                                notifyDataSetChanged();
                                            } else
                                                Toast.makeText(parentContext, "Cập nhật thất bại !", Toast.LENGTH_SHORT).show();
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
                alertDialog.show();//show diaglog
            }
        });
        xoaCongTrinhBtn.setOnClickListener(new View.OnClickListener() {
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
                        .setPositiveButton("Xoá",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        if (database == null) {
                                            Toast.makeText(parentContext, "Mất kết nối đến cơ sở dữ liệu !", Toast.LENGTH_SHORT).show();
                                        } else {
                                            if (VatTuDAO.xoaVatTu(data.get(position).getMaVatTu(), database.getWritableDatabase()) != -1) {
                                                Toast.makeText(parentContext, "Xoá thành công !", Toast.LENGTH_SHORT).show();
                                                data.remove(position);
                                                notifyDataSetChanged();
                                            } else
                                                Toast.makeText(parentContext, "Xoá thất bại !", Toast.LENGTH_SHORT).show();
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
                alertDialog.show();//show diaglog
            }
        });
        return convertView;
    }


}