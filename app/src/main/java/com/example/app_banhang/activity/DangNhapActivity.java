package com.example.app_banhang.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_banhang.R;
import com.example.app_banhang.retrofit.ApiBanHang;
import com.example.app_banhang.retrofit.RetrofitClient;
import com.example.app_banhang.ultil.Utils;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DangNhapActivity extends AppCompatActivity {
    TextView txtdangky, txtquenmk;
    EditText taikhoan, matkhau;
    Button buttondn;
    ApiBanHang apiBanHang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        intView();
        intControl();
    }

    private void intControl() {
        txtdangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DangKyActivity.class);
                startActivity(intent);
            }
        });
        txtquenmk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ResetpassActivity.class);
                startActivity(intent);
            }
        });
        buttondn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str_tendangnhap = taikhoan.getText().toString().trim();
                String str_password = matkhau.getText().toString().trim();
                if (TextUtils.isEmpty(str_tendangnhap)){
                    Toast.makeText(getApplicationContext(),"Bạn chưa nhập tên tài khoản!", Toast.LENGTH_LONG).show();
                } else
                if(TextUtils.isEmpty(str_password)){
                    Toast.makeText(getApplicationContext(),"Bạn chưa nhập mật khẩu!", Toast.LENGTH_LONG).show();}
                else {
                    //Lưu đang nhập
                    Paper.book().write("Tên đăng nhập", str_tendangnhap);
                    Paper.book().write("Mật khẩu", str_password);
                    compositeDisposable.add(apiBanHang.dangNhap(str_tendangnhap, str_password)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    userModel -> {
                                        if (userModel.isSuccess()){
                                            Utils.user_current = userModel.getResult().get(0);
                                            Toast.makeText(getApplicationContext(),"Đăng nhập thành công", Toast.LENGTH_LONG).show();
                                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }

                                    },
                                    throwable -> {
                                        Toast.makeText(getApplicationContext(),throwable.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                            ));
                }
            }
        });
    }

    private void intView() {
        Paper.init(this);
        txtdangky = findViewById(R.id.txtdangky);
        taikhoan = findViewById(R.id.edt_tendangnhap);
        matkhau = findViewById(R.id.edt_matkhaudangnhap);
        buttondn = findViewById(R.id.btn_dangnhap);
        txtquenmk = findViewById(R.id.txtquenmk);
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        //Đọc dữ liệu
        if (Paper.book().read("Tên đăng nhập") != null && Paper.book().read("Mật khẩu") != null){
            taikhoan.setText(Paper.book().read("Tên đăng nhập"));
            matkhau.setText(Paper.book().read("Mật khẩu"));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Utils.user_current.getTendangnhap() != null && Utils.user_current.getPassword() != null){
            taikhoan.setText(Utils.user_current.getTendangnhap());
            matkhau.setText(Utils.user_current.getPassword());
        }
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}