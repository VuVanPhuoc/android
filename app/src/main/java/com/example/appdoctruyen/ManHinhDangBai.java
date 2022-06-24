package com.example.appdoctruyen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appdoctruyen.database.databasedoctruyen;
import com.example.appdoctruyen.model.Truyen;

public class ManHinhDangBai extends AppCompatActivity {
    EditText edtTenTruyen,edtNoiDung,edtAnh;
    Button btnDangBai;
    databasedoctruyen databasedoctruyen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_dang_bai);
        edtAnh=findViewById(R.id.dbimg);
        edtTenTruyen=findViewById(R.id.dbTentruyen);
        edtNoiDung=findViewById(R.id.dbNoidung);
        btnDangBai=findViewById(R.id.dbdangbai);

        databasedoctruyen=new databasedoctruyen(this);


        //button đăng bài
        btnDangBai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tentruyen =edtTenTruyen.getText().toString();
                String noidung =edtNoiDung.getText().toString();
                String img =edtAnh.getText().toString();

                Truyen truyen=CreatTruyen();
                if(tentruyen.equals("")||noidung.equals("")||img.equals("")){
                    Toast.makeText(ManHinhDangBai.this,"Yêu cầu nhập đầy đủ dữ liệu",Toast.LENGTH_SHORT).show();
                    Log.e("ERR:","Nhập đầy đủ thông tin");


                }
                //Nếu nhập đầy đủ thông tin thì thêm dữ liệu
                else {
                    databasedoctruyen.AddTruyen(truyen);
                    //Chuyển qua nmàn hình admin và cập nhật lại dữ liệu
                    Intent intent=new Intent(ManHinhDangBai.this,ManHinhAdmin.class);
                    finish();
                    startActivity(intent);

                }
            }
        });

    }
    //Phương thức tạo truyện
    private Truyen CreatTruyen(){
        String tentruyen =edtTenTruyen.getText().toString();
        String noidung =edtNoiDung.getText().toString();
        String img =edtAnh.getText().toString();

        Intent intent =getIntent();
        int id =intent.getIntExtra("Id",0);

        Truyen truyen=new Truyen(tentruyen,noidung,img,id);
        return truyen;

    }



}