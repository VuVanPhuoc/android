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

        // Nhận dữ liệu từ màn hình đăng nhập gửi qua
        Intent intentpq=getIntent();
        int i =intentpq.getIntExtra("phanq",0);
        int idd=intentpq.getIntExtra("idd",0);
        email=intentpq.getStringExtra("email");
        tentaikhoan=intentpq.getStringExtra("tentaikhoan");


        AnhXa();
        ActionBar();
        ActionViewFlipper();

        // Sự kiện click item
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

        //bắt clíck item cho listView
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                //Đăng bài
                if(position==0){
                    if(i==2){

                        Intent intent=new Intent(MainActivity.this,ManHinhAdmin.class);
                        //Gửi id tài khoản qua admin
                        intent.putExtra("Id",idd);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(MainActivity.this,"Bạn không có quyền đăng bài",Toast.LENGTH_SHORT).show();
                        Log.e("Đăng Bài :","Bạn không có quyền");

                    }
                }//Nếu vị trí ấn vào là thông tin thì sẽ chuyển qua màn hình thông tin
                else if(position==1){
                    Intent intent=new Intent(MainActivity.this,ManHinhThongTin.class);
                    startActivity(intent);

                }//Đăng xuất
                else if(position==2) {
                    finish();

                }



            }
        });
    }

    //Thanh actionbar với toolbar
    private void ActionBar() {

        //Hàm hỗ trợ actionbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Tạo icon cho toolbar
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        //bắt sự kiện Click
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

    }

    //Phương thức cho chạy quảng cáo với ViewFilipper
    private void ActionViewFlipper() {

        //Mảng chứa ảnh cho quảng cáo
        ArrayList<String> mangquangcao=new ArrayList<>();
        //Add ảnh vào mảng
        mangquangcao.add("https://i.bigschool.vn/w/f1d058/420/News/images/2017/10/c2/9-cau-chuyen-ngu-ngon-ai-cung-nen-biet-song-ngu.png");
        mangquangcao.add("https://i.bigschool.vn/w/91cbc9/420/News/images/2017/10/c3/9-cau-chuyen-ngu-ngon-ai-cung-nen-biet-song-ngu.png");
        mangquangcao.add("https://i.bigschool.vn/w/6c48f1/420/News/images/2017/10/c4/9-cau-chuyen-ngu-ngon-ai-cung-nen-biet-song-ngu.png");
        mangquangcao.add("https://i.bigschool.vn/w/bd631d/420/News/images/2017/10/c6/9-cau-chuyen-ngu-ngon-ai-cung-nen-biet-song-ngu.png");

        //Thực hiện vòng lặp for gán ảnh voà ImageView,tồi từ imgview lên app
        for(int i=0;i<mangquangcao.size();i++){
          ImageView imageView =new ImageView(getApplicationContext());
          //Sử dụng hàng thư viện Picasso
            Picasso.get().load(mangquangcao.get(i)).into(imageView);
            //Phương thức chỉnh tấm hình vùa khung quảng cáo
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            //Thêm ảnh từ imageview vào viewFilipper
            viewFlipper.addView(imageView);
        }

        //Thiết lập tự dộng chạy cho viewflipper trong 3 giây
        viewFlipper.setFlipInterval(3000);
        //chạy auto
        viewFlipper.setAutoStart(true);
        //Gọi animation vào và ra
        Animation animation_slide_in= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_right);
        Animation animation_slide_out= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out_right);
        //Gọi animation vào viewflipper
        viewFlipper.setInAnimation(animation_slide_in);
        viewFlipper.setInAnimation(animation_slide_out);
    }

    //Phương thức ánh xạ
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


        //Thông tin
        taiKhoanArrayList=new ArrayList<>();
        taiKhoanArrayList.add(new TaiKhoan(tentaikhoan,email));
        adapterthongtin=new adapterthongtin(this,R.layout.navigation_thongtin,taiKhoanArrayList);
        listViewThongTin.setAdapter(adapterthongtin);

        //chuyên mục
        chuyenmucArrayList=new ArrayList<>();
        chuyenmucArrayList.add(new chuyenmuc("Admin",R.drawable.admin));
        chuyenmucArrayList.add(new chuyenmuc("Thông Tin",R.drawable.ic_fa));

        chuyenmucArrayList.add(new chuyenmuc("Đăng Xuất",R.drawable.ic_baseline_login_24));
        adapterchuyenmuc=new adapterchuyenmuc(this,R.layout.chuyenmuc,chuyenmucArrayList);
        listView.setAdapter(adapterchuyenmuc);

    }

    //Nạp 1 menu tìm kiếm vào action
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //Click vào icon tìm kiếm sẽ chuyển qua màn hình tìm kiếm
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