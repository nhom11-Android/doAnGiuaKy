package com.buoi2.quanlyvanchuyen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.TableRow;
import android.widget.TextView;

import com.buoi2.quanlyvanchuyen.DAO.ChiTietPhieuVanChuyenDAO;
import com.buoi2.quanlyvanchuyen.DAO.PhieuVanChuyenDAO;
import com.buoi2.quanlyvanchuyen.DAO.VatTuDAO;
import com.buoi2.quanlyvanchuyen.bean.ChiTietPhieuVanChuyen;
import com.buoi2.quanlyvanchuyen.bean.PhieuVanChuyen;
import com.buoi2.quanlyvanchuyen.bean.VatTu;

import java.util.ArrayList;
import java.util.Arrays;

public class ThongKeDoanhThuTheoThang extends AppCompatActivity {
    CSDLVanChuyen csdlVanChuyen = new CSDLVanChuyen(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke_doanh_thu_theo_thang);
        doanhThuTheoThang();
    }

    private ArrayList<String> doanhThuTheoThang(){
        ArrayList<String> doanhThu = new ArrayList<>();
        ArrayList<String> danhSachPvc = PhieuVanChuyenDAO.getAllPVC(csdlVanChuyen.getReadableDatabase());
        int[] doanhThuCuaThang = new int[12];// 12 thang
        Arrays.fill(doanhThuCuaThang,0);
        for(int i=0;i< danhSachPvc.size();i+=2){
            int month = Integer.parseInt(danhSachPvc.get(i+1).split("/")[1]);
            int mp = Integer.parseInt(danhSachPvc.get(i));
            //tính doanh thu của phiếu
            int doanhThuCuaPhieu = tinhDoanhThuTheoMaPhieu(mp);
            doanhThuCuaThang[month] += doanhThuCuaPhieu;
            System.out.println("phieu: "+mp+" "+ doanhThuCuaPhieu);
        }
        return doanhThu;
    }

    private int tinhDoanhThuTheoMaPhieu(int maPhieuVanChuyen) {
        ArrayList<ChiTietPhieuVanChuyen> danhSachVatTu = ChiTietPhieuVanChuyenDAO.danhSachVatTuTheoPhieuVanChuyen(maPhieuVanChuyen, csdlVanChuyen.getReadableDatabase());
        int tongTien = 0;

        //set data cho table
        for(ChiTietPhieuVanChuyen i:danhSachVatTu){
            VatTu vattu = VatTuDAO.layVatTuTheoMaVatTu(i.getMaVatTu(),csdlVanChuyen.getReadableDatabase());
            int tienVcVt = i.getSoLuong()*vattu.getGia()*i.getCuLy();
            tongTien+=tienVcVt;
        }
        return tongTien;
    }

    ;

}