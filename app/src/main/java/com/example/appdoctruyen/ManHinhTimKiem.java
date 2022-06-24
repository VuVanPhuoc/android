package com.example.appdoctruyen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.example.appdoctruyen.adapter.adapterTruyen;
import com.example.appdoctruyen.database.databasedoctruyen;
import com.example.appdoctruyen.model.Truyen;

import java.util.ArrayList;
import java.util.Locale;

public class ManHinhTimKiem extends AppCompatActivity {

    ListView listView;
    EditText edt;

    ArrayList<Truyen> TruyenArrayList;

    ArrayList<Truyen> arrayList;


    adapterTruyen adapterTruyen;
    databasedoctruyen databasedoctruyen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_tim_kiem);

       listView = findViewById(R.id.listviewTimKiem);
        edt = findViewById(R.id.timkiem);
        initList();

        // Bắt click cho item
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent=new Intent(ManHinhTimKiem.this,ManHinhNoiDung.class);
                String tent =arrayList.get(position).getTenTruyen();
                String noidungt =arrayList.get(position).getNoiDung();
                intent.putExtra("tentruyen",tent);
                intent.putExtra("noidung",noidungt);
                startActivity(intent);
            }
        });

        //editText tkiem
        edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                filter(s.toString());
            }
        });

    }
    //Tìm kiếm
    private void filter(String text){
        //Xoá dữ liệu mảng
        arrayList.clear();

        ArrayList<Truyen> filteredList=new ArrayList<>();
        for (Truyen item : TruyenArrayList){
            if(item.getTenTruyen().toLowerCase().contains(text.toLowerCase())){
                //Thêm item vào filteredlist
                filteredList.add(item);
                //Thêm vào mảng
                arrayList.add(item);

            }

        }
        adapterTruyen.fiilteredList(filteredList);
    }

    //Phương thức lấy dữ liệu,gán vào listview
    private void initList() {

        TruyenArrayList=new ArrayList<>();

        arrayList=new ArrayList<>();

        databasedoctruyen=new databasedoctruyen(this);
        Cursor cursor=databasedoctruyen.getData2();


        while (cursor.moveToNext()){
            int id =cursor.getInt(0);
            String tentruyen=cursor.getString(1);
            String noidung=cursor.getString(2);
            String anh=cursor.getString(3);
            int id_tk =cursor.getInt(4);

            TruyenArrayList.add(new Truyen(id,tentruyen,noidung,anh,id_tk));

           arrayList.add(new Truyen(id,tentruyen,noidung,anh,id_tk));

            adapterTruyen=new adapterTruyen(getApplicationContext(),TruyenArrayList);
            listView.setAdapter(adapterTruyen);
        }


        cursor.moveToFirst();
        cursor.close();
    }
}