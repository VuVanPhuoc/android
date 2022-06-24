package com.example.appdoctruyen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.appdoctruyen.database.databasedoctruyen;

public class ManHinhDangNhap extends AppCompatActivity {

    //Tạo biến cho màn hình đăng nhập
    EditText edtTaiKhoan,edtMatKhau;
    Button btnDangNhap,btnDangKy;

    //tạo đối tượng cho databasetruyen
    databasedoctruyen databasedoctruyen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_dang_nhap);

        AnhXa();
        //Đối tượng databasedoctruyen
        databasedoctruyen =new databasedoctruyen(this);

        //Tạo sự kiện click button chuyển sang màn hình đăng ký với intent
       btnDangKy.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent=new Intent(ManHinhDangNhap.this,ManHinhDangKy.class);
               startActivity(intent);
           }
       });

        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Gán cho các biến là giá trị nhập vào từ edittext
                String tentaikoan =edtTaiKhoan.getText().toString();
                String matkhau =edtMatKhau.getText().toString();

                //Sử dụng con trỏ để lấy dữ liệu,gọi tới getData() để lấy tất cả tài khoản ơ database
                Cursor cursor=databasedoctruyen.getData();
                //Thực hiện vồng lập để lấy dữ liệu từ cursor với moveToNext() di chuyển tiếp
                while (cursor.moveToNext()){
                    //Lấy dữ liệu và gán vào biến,dữ liệu tài khoản ở ô 1 và mật khẩu ở ô 2,ô 0 là idtaikhoan ,ô 3 là email,ô 4 là phân quyền
                    String datatentaikhoan = cursor.getString(1);
                    String datamatkhau = cursor.getString(2);

                    //Nếu tk và mk nhập tù bàn phím khớp với database
                    if (datatentaikhoan.equals(tentaikoan)&& datamatkhau.equals(matkhau)){
                        //lấy dữ liệu phân quyền và id
                        int phanquyen=cursor.getInt(4);
                        int idd=cursor.getInt(0);
                        String email=cursor.getString(3);
                        String tentk=cursor.getString(1);

                        //Chuyển qua màn hình MailActivity
                        Intent intent=new Intent(ManHinhDangNhap.this,MainActivity.class);

                        //Gửi dữ liệu qua Activity là MaiActivity
                        intent.putExtra("phanq",phanquyen);
                        intent.putExtra("idd",idd);
                        intent.putExtra("email",email);
                        intent.putExtra("tentaikhoan",tentk);

                        startActivity(intent);


                    }
                }
                //Thực hiện trả cursor về đầu
                cursor.moveToFirst();
                //Đóng khi ko dùng
                cursor.close();
            }
        });
    }

    private void AnhXa(){
        edtMatKhau = findViewById(R.id.matkhau);
        edtTaiKhoan = findViewById(R.id.taikhoan);
        btnDangKy = findViewById(R.id.dangky);
        btnDangNhap = findViewById(R.id.dangnhap);

    }

}