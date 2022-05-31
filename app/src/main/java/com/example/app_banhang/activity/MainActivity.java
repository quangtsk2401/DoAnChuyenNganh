package com.example.app_banhang.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.example.app_banhang.R;
import com.example.app_banhang.adapter.LoaispAdapter;
import com.example.app_banhang.adapter.SanPhamMoiAdapter;
import com.example.app_banhang.model.LoaiSP;
import com.example.app_banhang.model.SanPhamMoi;
import com.example.app_banhang.model.UserModel;
import com.example.app_banhang.retrofit.ApiBanHang;
import com.example.app_banhang.retrofit.RetrofitClient;
import com.example.app_banhang.ultil.Utils;
import com.google.android.material.navigation.NavigationView;
import com.nex3z.notificationbadge.NotificationBadge;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    NotificationBadge badge;
    DrawerLayout drawerLayout;
    ViewFlipper viewFlipper;
    Animation in,out;
    RecyclerView recyclerViewmanhinhchinh;
    NavigationView navigationView;
    ListView listViewmanhinhchinh;
    LoaispAdapter loaispAdpter;
    List<LoaiSP> mangloaisp;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiBanHang apiBanHang;
    List<SanPhamMoi> mangSpMoi;
    SanPhamMoiAdapter spAdapter;
    FrameLayout frameLayout;
    TextView txtdangky,txtdangnhap;
    LinearLayout dangnhapdangky;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        Anhxa();
        ActionBar();
        ActionViewFlipper();
        getLoaiSanPham();
        //Kiểm tra kết nối internet
        if (isConnect(this)){
            ActionViewFlipper();
            getLoaiSanPham();
            getSpMoi();
            getEventClick();
        }
        else {
            Toast.makeText(getApplicationContext(), "Không có kết nối, vui lòng kết nối Internet", Toast.LENGTH_LONG).show();
        }
    }
    private void getEventClick() {
        listViewmanhinhchinh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        Intent trangchu = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(trangchu);
                        break;
                    case 1:
                        Intent phone = new Intent(getApplicationContext(), LoaiSPActivity.class);
                        phone.putExtra("loai", 1);
                        startActivity(phone);
                        break;
                    case 2:
                        Intent pc = new Intent(getApplicationContext(),LoaiSPActivity.class);
                        pc.putExtra("loai", 2);
                        startActivity(pc);
                        break;
                    case 3:
                        Intent laptop = new Intent(getApplicationContext(),LoaiSPActivity.class);
                        laptop.putExtra("loai", 3);
                        startActivity(laptop);
                        break;
                    case 4:
                        Intent linhkien = new Intent(getApplicationContext(),LoaiSPActivity.class);
                        linhkien.putExtra("loai", 4);
                        startActivity(linhkien);
                        break;
                    case 5:
                        Intent gear = new Intent(getApplicationContext(),LoaiSPActivity.class);
                        gear.putExtra("loai", 5);
                        startActivity(gear);
                        break;
                    case 6:
                        Intent usb = new Intent(getApplicationContext(),LoaiSPActivity.class);
                        usb.putExtra("loai", 6);
                        startActivity(usb);
                        break;
                    case 7:
                        Intent lienhe = new Intent(getApplicationContext(),LienHeActivity.class);
                        startActivity(lienhe);
                        break;
                    case 8:
                        Intent infor = new Intent(getApplicationContext(),InfoActivity.class);
                        startActivity(infor);
                        break;
                }
            }
        });
    }

    //Lấy dữ liệu từ database sp mới(Sản phẩm đang HOT )
    private void getSpMoi() {
        compositeDisposable.add(apiBanHang.getSpMoi()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
                sanPhamMoiModel -> {
                    if (sanPhamMoiModel.isSuccess()){
                        mangSpMoi = sanPhamMoiModel.getResult();
                        spAdapter = new SanPhamMoiAdapter(getApplicationContext(), mangSpMoi);
                        recyclerViewmanhinhchinh.setAdapter(spAdapter);
                    }
                },
                throwable -> {
                    Toast.makeText(getApplicationContext(), "Không kết nối được với server"+throwable.getMessage(), Toast.LENGTH_LONG).show();
                }
        ));
    }


    //Lấy dữ liệu từ server
    private void getLoaiSanPham() {
        compositeDisposable.add(apiBanHang.getLoaiSp()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(loaiSpModel -> {
                    if (loaiSpModel.isSuccess()){
                        //Đỗ dữ liệu vào mãng loại sp
                        mangloaisp = loaiSpModel.getResult();
                        loaispAdpter = new LoaispAdapter(getApplicationContext(),mangloaisp);
                        listViewmanhinhchinh.setAdapter(loaispAdpter);
                    }
                }
        ));
    }

    private void ActionViewFlipper() {
        ArrayList<String> mangquangcao = new ArrayList<>();
        mangquangcao.add("https://bizweb.sapocdn.net/100/329/122/themes/835213/assets/slider1_5.jpg");
        mangquangcao.add("https://24hcomputer.vn/img/g/g85.jpg");
        mangquangcao.add("https://mediaonlinevn.com/wp-content/uploads/2021/08/210831-asus-oled-laptop-01-680x365_c.jpg");
        mangquangcao.add("https://anphat.com.vn/media/news/4922_vga_get_pugio_n_claymore_oct_2018_fb_group_banner.jpg");
        mangquangcao.add("https://www.asus.com/microsite/Graphics-Cards/GeForce-RTX-30-Series/au/img/bg-header.jpg");
        mangquangcao.add("https://dienmay.vatbau.com/Products/Images/44/244567/Slider/vi-vn-asus-tuf-gaming-fx706he-i7-hx011t-6.jpg");
        for(int i =0; i<mangquangcao.size();i++){
            ImageView imageView = new ImageView(getApplicationContext());
            Glide.with(getApplicationContext()).load(mangquangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(10000);
        viewFlipper.setAutoStart(true);
        Animation slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        Animation slide_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out);
        viewFlipper.setInAnimation(slide_in);
        viewFlipper.setOutAnimation(slide_out);
    }

    private void ActionBar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void Anhxa() {
        txtdangky = findViewById(R.id.maindangky);
        txtdangnhap = findViewById(R.id.maindangnhap);
        toolbar = findViewById(R.id.toolbarmhc);
        badge = findViewById(R.id.menu_sl);
        drawerLayout = findViewById(R.id.drawerlayoutmhc);
        viewFlipper = findViewById(R.id.viewflippermhc);
        recyclerViewmanhinhchinh = findViewById(R.id.recyclerviewmhc);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,2);
        recyclerViewmanhinhchinh.setLayoutManager(layoutManager);
        recyclerViewmanhinhchinh.setHasFixedSize(true);
        navigationView = findViewById(R.id.navigationviewmhc);
        listViewmanhinhchinh = findViewById(R.id.listviewmhc);
        dangnhapdangky = findViewById(R.id.dangnhapdangky);
        badge = findViewById(R.id.menu_sl);
        frameLayout = findViewById(R.id.frame_giohang);
        mangloaisp = new ArrayList<>();
        mangSpMoi = new ArrayList<>();
        if (Utils.manggiohang == null){
            Utils.manggiohang = new ArrayList<>();
        }
        else {
                int totalItem =0;
                for (int i = 0; i < Utils.manggiohang.size(); i++){
                    totalItem= totalItem + Utils.manggiohang.get(i).getSoluong();
                }
                badge.setText(String.valueOf(Utils.manggiohang.size()));
        }
        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent giohang = new Intent(getApplicationContext(), GioHangActivity.class);
                startActivity(giohang);
            }
        });
        if (Utils.user_current.getTendangnhap() != null)
        {
            dangnhapdangky.setVisibility(View.GONE);
        }
        txtdangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DangKyActivity.class);
                startActivity(intent);
            }
        });
        txtdangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DangNhapActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        int totalItem =0;
        for (int i = 0; i < Utils.manggiohang.size(); i++){
            totalItem= totalItem + Utils.manggiohang.get(i).getSoluong();
        }
        badge.setText(String.valueOf(Utils.manggiohang.size()));
    }

    private boolean isConnect (Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if ((wifi != null && wifi.isConnected()) || (mobile != null && mobile.isConnected())){
            return true;
        } else
        {
            return false;
        }
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}