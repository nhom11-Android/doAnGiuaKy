package com.buoi2.quanlyvanchuyen;

import android.content.Context;
import android.content.DialogInterface;
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

import com.buoi2.quanlyvanchuyen.CSDLVanChuyen;
import com.buoi2.quanlyvanchuyen.DAO.VatTuDAO;
import com.buoi2.quanlyvanchuyen.R;
import com.buoi2.quanlyvanchuyen.bean.VatTu;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;

import myHelp.MySuperFunc;

public class VatTuSpinnerCustomAdapter extends ArrayAdapter<VatTu> {
    Context parentContext;
    int resource;
    ArrayList<VatTu> data;

    public VatTuSpinnerCustomAdapter(@NonNull Context context, int resource, ArrayList<VatTu> data) {
        super(context, resource);
        this.parentContext = context;
        this.resource = resource;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    public View getCustomView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View cvV = LayoutInflater.from(parentContext).inflate(R.layout.vat_tu_custom_spinner, parent, false);

        // ánh xạ
        ImageView anhVatTuIv = cvV.findViewById(R.id.imageVatTu_VTCS);
        TextView tenVatTuTv = cvV.findViewById(R.id.tenVatTu_VTCS);

        // gán data lên view
        VatTu vatTu = data.get(position);
        anhVatTuIv.setImageBitmap(MySuperFunc.getByteArrayAsBitmap(vatTu.getAnh()));
        tenVatTuTv.setText(vatTu.getTenVatTu());
//        if(position==0){
//            anhVatTuIv.setImageResource(R.drawable.image_file_48px);
//            tenVatTuTv.setText("");
//        }
        return cvV;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }
}
