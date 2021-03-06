package com.example.app_banhang.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_banhang.R;
import com.example.app_banhang.model.UserModel;
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
    boolean isLogin = false;

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
                    Toast.makeText(getApplicationContext(),"B???n ch??a nh???p t??n t??i kho???n!", Toast.LENGTH_LONG).show();
                } else
                if(TextUtils.isEmpty(str_password)){
                    Toast.makeText(getApplicationContext(),"B???n ch??a nh???p m???t kh???u!", Toast.LENGTH_LONG).show();}
                else {
                    //L??u ??ang nh???p
                    Paper.book().write("T??n ????ng nh???p", str_tendangnhap);
                    Paper.book().write("M???t kh???u", str_password);
                    dangnhap(str_tendangnhap, str_password);
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
        //?????c d??? li???u
        if (Paper.book().read("T??n ????ng nh???p") != null && Paper.book().read("M???t kh???u") != null){
            taikhoan.setText(Paper.book().read("T??n ????ng nh???p"));
            matkhau.setText(Paper.book().read("M???t kh???u"));
            if (Paper.book().read("islogin") != null){
                boolean flag = Paper.book().read("islogin");
                if (flag){
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //X??t t??? d???ng ????ng nh???p
                            //dangnhap(Paper.book().read("T??n ????ng nh???p"), Paper.book().read("M???t kh???u"));
                        }
                    },1000);
                }
            }
        }
    }
    private void dangnhap(String tendangnhap, String password) {
        compositeDisposable.add(apiBanHang.dangNhap(tendangnhap, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        userModel -> {
                            if (userModel.isSuccess()){
                                isLogin = true;
                                Paper.book().write("islogin", isLogin);
                                Utils.user_current = userModel.getResult().get(0);
                                // Luu lai thong tin nguoi dung
                                Paper.book().write("User", userModel.getResult().get(0));
                                Toast.makeText(getApplicationContext(),"????ng nh???p th??nh c??ng", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            else {
                                Toast.makeText(getApplicationContext(),"Sai t??n ????ng nh???p ho???c m???t kh???u", Toast.LENGTH_LONG).show();
                            }

                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(),throwable.getMessage(), Toast.LENGTH_LONG).show();
                        }
                ));
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