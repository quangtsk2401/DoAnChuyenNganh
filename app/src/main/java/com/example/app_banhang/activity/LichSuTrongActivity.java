package com.example.app_banhang.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.app_banhang.R;

public class LichSuTrongActivity extends AppCompatActivity {
    Toolbar toolbar;
    Button dangnhap, trangchu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lich_su_trong);
        initView();
        initControl();
    }
    private void initControl() {
        dangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DangNhapActivity.class);
                startActivity(intent);
            }
        });
        trangchu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
    private void initView() {
        toolbar = findViewById(R.id.toobarxemdontrong);
        dangnhap = findViewById(R.id.btn_dangnhap);
        trangchu = findViewById(R.id.btn_trangchu);
    }
}