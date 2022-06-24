package com.example.appdoctruyen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ManHinhThongTin extends AppCompatActivity {
    TextView txtThongtinapp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_thong_tin);

        txtThongtinapp=findViewById(R.id.textviewthongtin);
        String thongtin="Vũ Văn Phước";
        txtThongtinapp.setText(thongtin);
    }
}