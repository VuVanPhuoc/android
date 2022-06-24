package com.example.appdoctruyen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.appdoctruyen.adapter.adapterTruyen;
import com.example.appdoctruyen.database.databasedoctruyen;
import com.example.appdoctruyen.model.Truyen;

import java.util.ArrayList;

public class ManHinhAdmin extends AppCompatActivity {

    ListView listView;
    Button buttonThem;


    Button buttonThoat;
    ArrayList<Truyen> TruyenArrayList;
    adapterTruyen adapterTruyen;
    com.example.appdoctruyen.database.databasedoctruyen databasedoctruyen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_admin);

        listView = findViewById(R.id.listviewAdmin);
        buttonThem = findViewById(R.id.buttonThemtruyen);

        buttonThoat = findViewById(R.id.buttonThoat);
        initList();

        buttonThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Lấy id tài khoản để biết tài khoản admin nào đã vào chỉnh sủa
                Intent intent1 = getIntent();
                int id = intent1.getIntExtra("Id", 0);

                //Tiếp tục gửi id qua màn hình thêm truyện
                Intent intent = new Intent(ManHinhAdmin.this, ManHinhDangBai.class);
                intent.putExtra("Id", id);
                startActivity(intent);
            }
        });
        //Thoát
        buttonThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManHinhAdmin.this, MainActivity.class);
                startActivity(intent);
            }
        });


        //Xoá
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {

                DialogDelete(position);
                return false;
            }
        });


    }

    //Phương thức dialog hiểm thị cửa sổ xoá
    private void DialogDelete(int position) {
        //Tạo đối tượng dialog
        Dialog dialog = new Dialog(this);
        //Nạp layout vào dialog
        dialog.setContentView(R.layout.dialogdelete);
        //Tắt click ra ngoài là đóng ,chỉ click no mới đóng
        dialog.setCanceledOnTouchOutside(false);
        //Ánh xạ

        Button btnYes = dialog.findViewById(R.id.buttonYes);
        Button btnNo = dialog.findViewById(R.id.buttonNo);


        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int idtruyen = TruyenArrayList.get(position).getID();

                //Xoá dữ liệu
                databasedoctruyen.Delete(idtruyen);
                //cập nhật lại activity
                Intent intent = new Intent(ManHinhAdmin.this, ManHinhAdmin.class);
                finish();
                startActivity(intent);
                Toast.makeText(ManHinhAdmin.this, "Xoá truyện thành công ", Toast.LENGTH_SHORT).show();

            }
        });
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        //Chạy dialog
        dialog.show();
    }

    //Gán dữ liệu cho listview
    private void initList() {
        TruyenArrayList = new ArrayList<>();
        databasedoctruyen = new databasedoctruyen(this);
        Cursor cursor1 = databasedoctruyen.getData2();

        while (cursor1.moveToNext()) {
            int id = cursor1.getInt(0);
            String tentruyen = cursor1.getString(1);
            String noidung = cursor1.getString(2);
            String anh = cursor1.getString(3);
            int id_tk = cursor1.getInt(4);

            TruyenArrayList.add(new Truyen(id, tentruyen, noidung, anh, id_tk));
            adapterTruyen = new adapterTruyen(getApplicationContext(), TruyenArrayList);
            listView.setAdapter(adapterTruyen);

        }
        cursor1.moveToFirst();
        cursor1.close();
    }

}