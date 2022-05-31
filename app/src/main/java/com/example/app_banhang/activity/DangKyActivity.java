package com.example.app_banhang.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.app_banhang.R;
import com.example.app_banhang.model.UserModel;
import com.example.app_banhang.retrofit.ApiBanHang;
import com.example.app_banhang.retrofit.RetrofitClient;
import com.example.app_banhang.ultil.Utils;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DangKyActivity extends AppCompatActivity {
    EditText username, tendangky, matkhaudangky, nhaplaimatkhau, sodienthoai;
    Button button;
    ApiBanHang apiBanHang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);
        initView();
        initControl();
    }

    private void initControl() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dangKy();
            }

            private void dangKy() {
                String str_tendangky= tendangky.getText().toString().trim();
                String str_matkhaudangky = matkhaudangky.getText().toString().trim();
                String str_nhaplaimatkhau = nhaplaimatkhau.getText().toString().trim();
                String str_sodienthoai = sodienthoai.getText().toString().trim();
                String str_username= username.getText().toString().trim();
                if (TextUtils.isEmpty(str_tendangky)){
                    Toast.makeText(getApplicationContext(),"Bạn chưa nhập tên tài khoản!", Toast.LENGTH_LONG).show();
                } else
                    if(TextUtils.isEmpty(str_matkhaudangky)){
                        Toast.makeText(getApplicationContext(),"Bạn chưa nhập mật khẩu!", Toast.LENGTH_LONG).show();
                    } else if(TextUtils.isEmpty(str_nhaplaimatkhau)){
                        Toast.makeText(getApplicationContext(),"Bạn chưa nhập lại mật khẩu!", Toast.LENGTH_LONG).show();
                    } else if(TextUtils.isEmpty(str_username)){
                        Toast.makeText(getApplicationContext(),"Bạn chưa nhập Họ và Tên!", Toast.LENGTH_LONG).show();
                    } else if(TextUtils.isEmpty(str_sodienthoai)){
                        Toast.makeText(getApplicationContext(),"Bạn chưa nhập số điện thoại!", Toast.LENGTH_LONG).show();
                    } else {
                        if (str_matkhaudangky.equals(str_nhaplaimatkhau)){
                            compositeDisposable.add(apiBanHang.dangky(str_tendangky, str_matkhaudangky,str_username, str_sodienthoai)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(
                                            userModel -> {
                                                if (userModel.isSuccess()){
                                                    Toast.makeText(getApplicationContext(),"Đăng ký thành công", Toast.LENGTH_LONG).show();
                                                    Utils.user_current.setTendangnhap(str_tendangky);
                                                    Utils.user_current.setPassword(str_matkhaudangky);
                                                    Intent intent = new Intent(getApplicationContext(), DangNhapActivity.class);
                                                    startActivity(intent);
                                                    finish();
                                                }
                                                else {
                                                    Toast.makeText(getApplicationContext(),userModel.getMessage(), Toast.LENGTH_LONG).show();
                                                }
                                            }, throwable -> {
                                                Toast.makeText(getApplicationContext(),throwable.getMessage(), Toast.LENGTH_LONG).show();
                                            }
                                    ));
                        } else {
                            Toast.makeText(getApplicationContext(),"Mật khẩu không trùng khớp!", Toast.LENGTH_LONG).show();
                        }
                    }
            }
        });
    }
    private void initView() {
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        username = findViewById(R.id.edt_username);
        tendangky = findViewById(R.id.edt_tendangky);
        matkhaudangky = findViewById(R.id.edt_matkhaudangky);
        nhaplaimatkhau = findViewById(R.id.edt_nhaplaimatkhau);
        sodienthoai = findViewById(R.id.edt_sodienthoai);
        button = findViewById(R.id.btn_dangky);
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}