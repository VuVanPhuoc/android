package com.example.appdoctruyen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.appdoctruyen.adapter.adapterTruyen;
import com.example.appdoctruyen.adapter.adapterchuyenmuc;
import com.example.appdoctruyen.adapter.adapterthongtin;
import com.example.appdoctruyen.database.databasedoctruyen;
import com.example.appdoctruyen.model.TaiKhoan;
import com.example.appdoctruyen.model.Truyen;
import com.example.appdoctruyen.model.chuyenmuc;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    ViewFlipper viewFlipper;
    NavigationView navigationView;
    ListView listView,listViewNew,listViewThongTin;
    DrawerLayout drawerLayout;


    String email;
    String tentaikhoan;

    ArrayList<Truyen> TruyenArrayList;

   adapterTruyen adapterTruyen;
   ArrayList<chuyenmuc> chuyenmucArrayList;
   ArrayList<TaiKhoan> taiKhoanArrayList;
  databasedoctruyen databasedoctruyen;

   adapterchuyenmuc adapterchuyenmuc;
   adapterthongtin adapterthongtin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databasedoctruyen =new databasedoctruyen(this);

        // Nh???n d??? li???u t??? m??n h??nh ????ng nh???p g???i qua
        Intent intentpq=getIntent();
        int i =intentpq.getIntExtra("phanq",0);
        int idd=intentpq.getIntExtra("idd",0);
        email=intentpq.getStringExtra("email");
        tentaikhoan=intentpq.getStringExtra("tentaikhoan");


        AnhXa();
        ActionBar();
        ActionViewFlipper();

        // S??? ki???n click item
        listViewNew.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent =new Intent(MainActivity.this,ManHinhNoiDung.class);

                String tent=TruyenArrayList.get(position).getTenTruyen();
                String noidungt=TruyenArrayList.get(position).getNoiDung();
                intent.putExtra("tentruyen",tent);
                intent.putExtra("noidung",noidungt);
                startActivity(intent);


            }
        });

        //b???t cl??ck item cho listView
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                //????ng b??i
                if(position==0){
                    if(i==2){

                        Intent intent=new Intent(MainActivity.this,ManHinhAdmin.class);
                        //G???i id t??i kho???n qua admin
                        intent.putExtra("Id",idd);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(MainActivity.this,"B???n kh??ng c?? quy???n ????ng b??i",Toast.LENGTH_SHORT).show();
                        Log.e("????ng B??i :","B???n kh??ng c?? quy???n");

                    }
                }//N???u v??? tr?? ???n v??o l?? th??ng tin th?? s??? chuy???n qua m??n h??nh th??ng tin
                else if(position==1){
                    Intent intent=new Intent(MainActivity.this,ManHinhThongTin.class);
                    startActivity(intent);

                }//????ng xu???t
                else if(position==2) {
                    finish();

                }



            }
        });
    }

    //Thanh actionbar v???i toolbar
    private void ActionBar() {

        //H??m h??? tr??? actionbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //T???o icon cho toolbar
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        //b???t s??? ki???n Click
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

    }

    //Ph????ng th???c cho ch???y qu???ng c??o v???i ViewFilipper
    private void ActionViewFlipper() {

        //M???ng ch???a ???nh cho qu???ng c??o
        ArrayList<String> mangquangcao=new ArrayList<>();
        //Add ???nh v??o m???ng
        mangquangcao.add("https://i.bigschool.vn/w/f1d058/420/News/images/2017/10/c2/9-cau-chuyen-ngu-ngon-ai-cung-nen-biet-song-ngu.png");
        mangquangcao.add("https://i.bigschool.vn/w/91cbc9/420/News/images/2017/10/c3/9-cau-chuyen-ngu-ngon-ai-cung-nen-biet-song-ngu.png");
        mangquangcao.add("https://i.bigschool.vn/w/6c48f1/420/News/images/2017/10/c4/9-cau-chuyen-ngu-ngon-ai-cung-nen-biet-song-ngu.png");
        mangquangcao.add("https://i.bigschool.vn/w/bd631d/420/News/images/2017/10/c6/9-cau-chuyen-ngu-ngon-ai-cung-nen-biet-song-ngu.png");

        //Th???c hi???n v??ng l???p for g??n ???nh vo?? ImageView,t???i t??? imgview l??n app
        for(int i=0;i<mangquangcao.size();i++){
          ImageView imageView =new ImageView(getApplicationContext());
          //S??? d???ng h??ng th?? vi???n Picasso
            Picasso.get().load(mangquangcao.get(i)).into(imageView);
            //Ph????ng th???c ch???nh t???m h??nh v??a khung qu???ng c??o
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            //Th??m ???nh t??? imageview v??o viewFilipper
            viewFlipper.addView(imageView);
        }

        //Thi???t l???p t??? d???ng ch???y cho viewflipper trong 3 gi??y
        viewFlipper.setFlipInterval(3000);
        //ch???y auto
        viewFlipper.setAutoStart(true);
        //G???i animation v??o v?? ra
        Animation animation_slide_in= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_right);
        Animation animation_slide_out= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out_right);
        //G???i animation v??o viewflipper
        viewFlipper.setInAnimation(animation_slide_in);
        viewFlipper.setInAnimation(animation_slide_out);
    }

    //Ph????ng th???c ??nh x???
    public void AnhXa(){
        toolbar =findViewById(R.id.toolbarmanhinhchinh);
        viewFlipper =findViewById(R.id.viewflipper);
        listViewNew =findViewById(R.id.listviewNew);
        listView =findViewById(R.id.listviewmanhinhchinh);
        listViewThongTin =findViewById(R.id.listviewthongtin);
        navigationView =findViewById(R.id.navigationView);
        drawerLayout =findViewById(R.id.drawerlayout);

        TruyenArrayList =new ArrayList<>();
        Cursor cursor1=databasedoctruyen.getData1();
        while (cursor1.moveToNext()){
            int id=cursor1.getInt(0);
            String tentruyen=cursor1.getString(1);
            String noidung=cursor1.getString(2);
            String anh=cursor1.getString(3);
            int id_tk =cursor1.getInt(4);

            TruyenArrayList.add(new Truyen(id,tentruyen,noidung,anh,id_tk));
            adapterTruyen=new adapterTruyen(getApplicationContext(),TruyenArrayList);
            listViewNew.setAdapter(adapterTruyen);
        }
        cursor1.moveToFirst();
        cursor1.close();


        //Th??ng tin
        taiKhoanArrayList=new ArrayList<>();
        taiKhoanArrayList.add(new TaiKhoan(tentaikhoan,email));
        adapterthongtin=new adapterthongtin(this,R.layout.navigation_thongtin,taiKhoanArrayList);
        listViewThongTin.setAdapter(adapterthongtin);

        //chuy??n m???c
        chuyenmucArrayList=new ArrayList<>();
        chuyenmucArrayList.add(new chuyenmuc("Admin",R.drawable.admin));
        chuyenmucArrayList.add(new chuyenmuc("Th??ng Tin",R.drawable.ic_fa));

        chuyenmucArrayList.add(new chuyenmuc("????ng Xu???t",R.drawable.ic_baseline_login_24));
        adapterchuyenmuc=new adapterchuyenmuc(this,R.layout.chuyenmuc,chuyenmucArrayList);
        listView.setAdapter(adapterchuyenmuc);

    }

    //N???p 1 menu t??m ki???m v??o action
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //Click v??o icon t??m ki???m s??? chuy???n qua m??n h??nh t??m ki???m
        switch (item.getItemId()){
            case R.id.menu1:
                Intent intent=new Intent(MainActivity.this,ManHinhTimKiem.class);
                startActivity(intent);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}