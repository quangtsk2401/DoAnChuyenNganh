package com.example.app_banhang.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.usage.UsageEvents;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_banhang.R;
import com.example.app_banhang.adapter.GioHangAdapter;
import com.example.app_banhang.model.EventBus.TinhTongEvent;
import com.example.app_banhang.model.GioHang;
import com.example.app_banhang.ultil.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
import java.util.List;

public class GioHangActivity extends AppCompatActivity {
    TextView giohangtrong, tongtien;
    Toolbar toolbar;
    RecyclerView recyclerView;
    Button btnmuahang, tieptucmuahang, thanhtoan;
    GioHangAdapter adapter;
    LinearLayout layout1;
    long tongtiensp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
        initView();
        initControl();
        tinhTongTien();
        evenButton();
    }

    private void evenButton() {
        tieptucmuahang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
    }
    private void tinhTongTien() {
        tongtiensp = 0;
        for(int i = 0;i<Utils.mangmuahang.size(); i++){
            tongtiensp= tongtiensp+ (Utils.mangmuahang.get(i).getGia()*Utils.mangmuahang.get(i).getSoluong());
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,### ");
        tongtien.setText(decimalFormat.format(tongtiensp));
    }
    private void initControl() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        if (Utils.manggiohang.size() == 0){
            giohangtrong.setVisibility(View.VISIBLE);
            layout1.setVisibility(View.GONE);
            thanhtoan.setVisibility(View.GONE);
        }
        else{
            adapter = new GioHangAdapter(getApplicationContext(), Utils.manggiohang);
            recyclerView.setAdapter(adapter);
        }
        thanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Utils.user_current.getTendangnhap() == null){
                    Toast.makeText(getApplicationContext(), "Bạn chưa đăng nhập", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), DangNhapActivity.class);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(getApplicationContext(), ThanhToanActivity.class);
                    intent.putExtra("tongtien", tongtiensp);
                    startActivity(intent);
                }
            }
        });
    }
    @SuppressLint("WrongViewCast")
    private void initView() {
        giohangtrong = findViewById(R.id.txtgiohangtrong);
        tongtien = findViewById(R.id.txttongtien);
        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recyclerviewgiohang);
        btnmuahang = findViewById(R.id.btnthemvaogio);
        layout1 = findViewById(R.id.layout1);
        thanhtoan = findViewById(R.id.btn_thanhtoangiohang);
        tieptucmuahang = findViewById(R.id.btn_tieptucmuahang);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void eventTinhTien(TinhTongEvent event){
        if (event != null){
            tinhTongTien();
        }
    }
}