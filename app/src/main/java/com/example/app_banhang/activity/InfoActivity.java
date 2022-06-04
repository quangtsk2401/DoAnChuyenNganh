package com.example.app_banhang.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.app_banhang.R;
import com.example.app_banhang.retrofit.ApiBanHang;
import com.example.app_banhang.retrofit.RetrofitClient;
import com.example.app_banhang.ultil.Utils;
import com.google.android.material.textfield.TextInputEditText;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class InfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        initView();
        initToolbar();
        initControl();
    }
    TextView doimatkhau, mail,ten,sdt;
    Toolbar toolbar;
    AppCompatButton button;
    CompositeDisposable compositeDisposable=new CompositeDisposable();
    ApiBanHang apiBanHang;


    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initControl() {
        mail.setText(Utils.user_current.getTendangnhap());
        ten.setText(Utils.user_current.getUsername());
        sdt.setText(Utils.user_current.getSdt());
        doimatkhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getApplicationContext(),ResetpassActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        apiBanHang= RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        toolbar=findViewById(R.id.toobarthongtintaikhoan);
        mail=findViewById(R.id.thongtin_email);
        ten=findViewById(R.id.thongtin_ten);
        sdt=findViewById(R.id.thongtin_sdt);
        doimatkhau=findViewById(R.id.doimatkhau);
    }
}