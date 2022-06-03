package com.example.app_banhang.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.example.app_banhang.R;
import com.example.app_banhang.adapter.PhoneAdapter;
import com.example.app_banhang.model.SanPhamMoi;
import com.example.app_banhang.retrofit.ApiBanHang;
import com.example.app_banhang.retrofit.RetrofitClient;
import com.example.app_banhang.ultil.Utils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LoaiSPActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    ApiBanHang apiBanHang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    int page =1;
    int loai;
    PhoneAdapter phoneAdapter;
    List<SanPhamMoi> sanPhamMoiList;
    LinearLayoutManager linearLayoutManager;
    Handler handler = new Handler();
    boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sanpham);
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        loai = getIntent().getIntExtra("loai",1);
        Anhxa();
        ActionToolBar();
        getData(page);
        addEventLoad();
        if(loai==1){
            isLoading=false;
            toolbar.setTitle("Gaming Phone");
        }
        else {
            isLoading=true;
            if(loai==2){
                toolbar.setTitle("PC| Máy bộ");
            }else if(loai==3){
                toolbar.setTitle("Laptop");
            }else if(loai==4){
                toolbar.setTitle("Linh kiện");
            }else if(loai==5){
                toolbar.setTitle("Gaming Gear");
            }
            else if(loai==6){
                toolbar.setTitle("USB");
            }
        }
    }

    private void addEventLoad()
    {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if( isLoading == false)
                {
                    if( linearLayoutManager.findLastCompletelyVisibleItemPosition() == sanPhamMoiList.size()-1)
                    {
                        isLoading = true;
                        loadMore();
                    }
                }
            }
        });
    }

    private void loadMore() {
        handler.post(new Runnable() {
            @Override
            public void run() {
           sanPhamMoiList.add(null);
           phoneAdapter.notifyItemInserted( sanPhamMoiList.size()-1);

            }
        });
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                sanPhamMoiList.remove(sanPhamMoiList.size()-1);
                phoneAdapter.notifyItemRemoved(sanPhamMoiList.size());
                page = page +1;
                getData(page);
                phoneAdapter.notifyDataSetChanged();
                isLoading = false;
            }
        },2000);
    }


    private void getData(int page)
    {
        compositeDisposable.add(apiBanHang.getSanPham(page,loai)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
                sanPhamMoiModel -> {
                    if (sanPhamMoiModel.isSuccess()){
                        if(phoneAdapter == null)
                        {
                            sanPhamMoiList = sanPhamMoiModel.getResult();
                            phoneAdapter = new PhoneAdapter(getApplicationContext(), sanPhamMoiList);
                            recyclerView.setAdapter(phoneAdapter);
                        }
                        else
                        {
                            int vitri = sanPhamMoiList.size()-1;
                            int soluongadd = sanPhamMoiModel.getResult().size();
                            for( int i =0; i < soluongadd; i++)
                            {
                                sanPhamMoiList.add(sanPhamMoiModel.getResult().get(i));
                            }
                            phoneAdapter.notifyItemRangeInserted(vitri,soluongadd);
                        }
                    }
                    else
                    {
                    Toast.makeText(getApplicationContext(), "Không còn sản phẩn nào!", Toast.LENGTH_LONG).show();
                    isLoading = true;
                    }
                },
                throwable -> {
                    Toast.makeText(getApplicationContext(), "Không có kết nối với sever", Toast.LENGTH_LONG).show();
                }
        ));
    }

    private void ActionToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void Anhxa() {
        toolbar = findViewById(R.id.toolbarchitiet);
        recyclerView = findViewById(R.id.recyclerviewphone);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        sanPhamMoiList = new ArrayList<>();
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}