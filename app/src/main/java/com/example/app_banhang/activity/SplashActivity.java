package com.example.app_banhang.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.app_banhang.R;

import io.paperdb.Paper;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Paper.init(this);

        Thread thread= new Thread(){
            public void run(){
                try {
                    sleep(1500);
                }catch (Exception ex){

                }finally {

                        Intent home=new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(home);
                        finish();
                }
            }
        };
        thread.start();
    }
}