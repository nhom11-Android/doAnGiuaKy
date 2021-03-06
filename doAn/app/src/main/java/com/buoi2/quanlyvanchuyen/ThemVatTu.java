package com.buoi2.quanlyvanchuyen;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.buoi2.quanlyvanchuyen.DAO.LoaiDonViTinhDAO;
import com.buoi2.quanlyvanchuyen.DAO.VatTuDAO;
import com.buoi2.quanlyvanchuyen.bean.LoaiDonViTinh;
import com.buoi2.quanlyvanchuyen.bean.VatTu;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import myHelp.MySuperFunc;

public class ThemVatTu extends AppCompatActivity {
    private static final int REQUEST_CAMERA = 0;
    private static final int SELECT_FILE = 1;
    Button themVatTuBtn, btnSelectPhoto;
    EditText tenVatTuEdt, giaVatTuEdt;
    Spinner donViTinhSp;
    ImageView targetImage;
    ArrayList<LoaiDonViTinh> danhSachDonViTinh;
    String userChoosenTask = "";

    String i1;
    String img;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_vat_tu);
        setActionBar();
        getId();

        btnSelectPhoto.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
    }

    private void setActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Th??m V???t T??");
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.capnhat_bar, menu);
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
                lamMoi();
                break;
            default:break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getId() {
        themVatTuBtn = findViewById(R.id.btnThem_themVatTu);
        tenVatTuEdt = findViewById(R.id.edtTenVatTu_themVatTu);
        giaVatTuEdt = findViewById(R.id.edtGiaVatTu_themVatTu);
        donViTinhSp = findViewById(R.id.spDonVi_themVatTu);
        btnSelectPhoto= findViewById(R.id.UploadBtn);
        setDanhSachDonViTinh();
        donViTinhSp.setAdapter(taoArraySpinner());
        targetImage = findViewById(R.id.targetImageIv);
    }

    private int lamMoi(){
        try{
            tenVatTuEdt.setText("");
            giaVatTuEdt.setText("");
            setDanhSachDonViTinh();
            donViTinhSp.setAdapter(taoArraySpinner());
            donViTinhSp.setSelection(0);
            return 0;
        }
        catch (Exception e){
            return -1;
        }
    }

    private void setDanhSachDonViTinh(){
        CSDLVanChuyen database = new CSDLVanChuyen(this);
        this.danhSachDonViTinh = LoaiDonViTinhDAO.layDanhSachDonViTinh(database.getReadableDatabase());
    }

    private ArrayAdapter taoArraySpinner(){
        ArrayList<String> arraySpinner = new ArrayList<>();
        arraySpinner.add("---");
        for(LoaiDonViTinh donViTinh : this.danhSachDonViTinh){
            arraySpinner.add(donViTinh.getTenDonViTinh());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }

    public int xuLyThemVatTu(View view) {
        /**
         *   H??m x??? l?? th??m v???t t??
         *   @return 0 n???u th??nh c??ng, -1 n???u th???t b???i
         */
        boolean check = true;
        String msg = "L??u ??: ";
        //Ki???m tra View tenVatTu
        String tenVatTu = tenVatTuEdt.getText().toString();
        if(tenVatTu.length() == 0){
            check = false;
            msg += "\n  - Kh??ng ????? tr???ng t??n v???t t??.";
        }
        //Ki???m tra View giaVatTu
        String tmp = giaVatTuEdt.getText().toString();
        int giaVatTu = 0;
        if(tmp.length() == 0){
            check = false;
            msg += "\n  - Kh??ng ????? tr???ng gi?? v???t t??.";
        }
        else{
            giaVatTu = Integer.parseInt(tmp);
        }
        //Ki???m tra donViTinh
        String donViTinh = "";
        int pos = donViTinhSp.getSelectedItemPosition();
        if(pos == 0 | pos >= donViTinhSp.getCount()){
            check = false;
            msg += "\n  - Ch???n ????n v??? t??nh.";
        }
        else{
            donViTinh += donViTinhSp.getItemAtPosition(pos);
        }
        //Ki???m tra View ???nh v???t t??
        byte[] anh = null;
        BitmapDrawable bitmapDrawable = (BitmapDrawable) targetImage.getDrawable();
        if (bitmapDrawable == null){
            check = false;
            msg += "\n  - Ch???n ???nh v???t t??.";
        }
        else{
            anh = MySuperFunc.getBitmapAsByteArray(bitmapDrawable.getBitmap());
        }

        if (!check){
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            return -1;
        }

        CSDLVanChuyen database = new CSDLVanChuyen(getBaseContext());
        SQLiteDatabase db = database.getReadableDatabase();
        ArrayList<VatTu> ds = VatTuDAO.danhSachVatTu(db);
        //Ki???m tra v???t t?? ???? c?? trong danh s??ch ch??a
        for(VatTu v : ds){
            if(tenVatTu.equals(v.getTenVatTu())){
                Toast.makeText(this, "V???t t?? \"" + tenVatTu + "\" ???? c??.", Toast.LENGTH_LONG).show();
                return -1;
            }
        }
        //Th??m v???t t?? m???i
        // kh???i t???o m?? v???t t??
        String mvt = MySuperFunc.generateCodeRamdom();
        VatTu vatTu = new VatTu(mvt , tenVatTu, donViTinh, giaVatTu, anh);
        int kiemTra = VatTuDAO.themVatTu(vatTu, database.getWritableDatabase());
        if(kiemTra == -1){
            Toast.makeText(this, "L???i! Vui l??ng th??? l???i sau.", Toast.LENGTH_LONG).show();
            return -1;
        }
        else{
            thoatActivity();
            return 0;
        }
    }

    public void huyThemVatTu(View view) {
        /**
         *   H??m x??? l?? khi nh???n n??t Tho??t.
         */
        thoatActivity();
    }

    private void thoatActivity(){
        finish();
    }

    public int themLoaiDonViTinh(View view) {
        /**
         *   G???i Dialog t??m ki???m
         *   @return 0 n???u th??nh c??ng
         */
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View themDonViTinhDialog = layoutInflater.inflate(R.layout.them_don_vi_tinh_dialog, null);
        MaterialAlertDialogBuilder alertDialogBuilder = new MaterialAlertDialogBuilder(this);
        alertDialogBuilder.setView(themDonViTinhDialog);
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Th??m",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                EditText donViTinhDialogEdt = themDonViTinhDialog.findViewById(R.id.donViTinhEdt_dialog);
                                String str = donViTinhDialogEdt.getText().toString();
                                if(kiemTraThemDonViTinh(str) == 0){
                                    CSDLVanChuyen database = new CSDLVanChuyen(getBaseContext());
                                    int kt = LoaiDonViTinhDAO.themDonViTinh(new LoaiDonViTinh(str), database.getWritableDatabase());
                                    if (kt == -1){
                                        Toast.makeText(getApplicationContext(), "L???i! Vui l??ng th??? l???i sau.", Toast.LENGTH_LONG).show();
                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(), "Th??m th??nh c??ng.", Toast.LENGTH_LONG).show();
                                    }
                                }
                                else{
                                    Toast.makeText(getApplicationContext(), "Lo???i ????n v??? t??nh ???? c??", Toast.LENGTH_LONG).show();
                                }
                            }
                        })
                .setNegativeButton("Hu???", // c??i ?????t n??t hu??? h??nh ?????ng
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alertDialog = alertDialogBuilder.create(); // t???o dialog t??? dialog builder
        alertDialog.show();//show diaglog
        return 0;
    }

    private int kiemTraThemDonViTinh(String donViTinh){
        for(LoaiDonViTinh d : danhSachDonViTinh){
            if(donViTinh.equals(d.getTenDonViTinh())){
                return -1;
            }
        }
        return 0;
    }


    private void selectImage(){
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(ThemVatTu.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result=Utility.checkPermission(ThemVatTu.this);
                if (items[item].equals("Take Photo")) {
                    userChoosenTask="Take Photo";
                    if(result)
                        cameraIntent();
                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask="Choose from Library";
                    if(result)
                        galleryIntent();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void cameraIntent(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    private void galleryIntent(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }


    public void onRequestPermissionResult(int requestCode, String[] permission, int[] grantResults){
        switch(requestCode){
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                   if(userChoosenTask.equals("Take Photo"))
                       cameraIntent();
                   else if(userChoosenTask.equals("Choose from Library"));
                }
                else{
                    // code for deny
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK){
            if (requestCode == SELECT_FILE) {
                System.out.println("Select file");
                onSelectFromGalleryResult(data);
            }
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File destination = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");
        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        targetImage.setImageBitmap(thumbnail);
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bm = null;
        if (data != null){
            try{
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        targetImage.setImageBitmap(bm);
    }
}