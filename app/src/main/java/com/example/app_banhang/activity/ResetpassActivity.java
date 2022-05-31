package com.example.app_banhang.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.app_banhang.R;
import com.example.app_banhang.retrofit.ApiBanHang;
import com.example.app_banhang.retrofit.RetrofitClient;
import com.example.app_banhang.ultil.Utils;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ResetpassActivity extends AppCompatActivity {
    EditText tendangnhap;
    Button btnreset;
    ApiBanHang apiBanHang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ProgressBar rsprogressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpass);
        initView();
        initControl();
    }

    private void initControl() {
        btnreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_tendangnhap = tendangnhap.getText().toString().trim();
                if (TextUtils.isEmpty(str_tendangnhap)){
                    Toast.makeText(getApplicationContext(), "Bạn chưa nhập email đăng nhập", Toast.LENGTH_LONG).show();
                }
                else{
                    rsprogressBar.setVisibility(View.VISIBLE);
                    compositeDisposable.add(apiBanHang.resetpass(str_tendangnhap)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    userModel -> {
                                        if (userModel.isSuccess()){
                                            Toast.makeText(getApplicationContext(), userModel.getMessage(), Toast.LENGTH_SHORT).show();
                                            Intent intent= new Intent(getApplicationContext(), DangNhapActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                        else {
                                            Toast.makeText(getApplicationContext(), userModel.getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                        rsprogressBar.setVisibility(View.VISIBLE);
                                    },
                                    throwable -> {
                                        Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_LONG).show();
                                        rsprogressBar.setVisibility(View.VISIBLE);
                                    }
                            ));
                }
            }
        });
    }

    private void initView() {
        apiBanHang= RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        tendangnhap = findViewById(R.id.edt_resetpass);
        btnreset = findViewById(R.id.btn_resetpass);
        rsprogressBar = findViewById(R.id.progressbar);
    }
    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}