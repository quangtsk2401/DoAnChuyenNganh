package com.example.app_banhang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.app_banhang.model.GioHang;
import com.example.app_banhang.R;

import java.text.DecimalFormat;
import java.util.List;

public class GioHangAdapter extends RecyclerView.Adapter<GioHangAdapter.MyViewHolder> {
    Context context;
    List<GioHang> gioHangList;

    public GioHangAdapter(Context context, List<GioHang> gioHangList) {
        this.context = context;
        this.gioHangList = gioHangList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_giohang, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
    GioHang gioHang = gioHangList.get(position);
    holder.itemgiohang_tensp.setText(gioHang.getTensp());
    holder.itemgiohang_soluong.setText(gioHang.getSoluong() + "");
    Glide.with(context).load(gioHang.getHinhsp()).into(holder.itemgiohang_img);
    DecimalFormat decimalFormat = new DecimalFormat("###,###,### ");
    holder.itemgiohang_giasp.setText(decimalFormat.format(gioHang.getGia())+"VNĐ");
    long gia = gioHang.getSoluong() * gioHang.getGia();
    holder.itemgiohang_tonggiasp.setText(decimalFormat.format(gia));
    }

    @Override
    public int getItemCount() {
        return gioHangList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView itemgiohang_img;
        TextView itemgiohang_tensp, itemgiohang_giasp,itemgiohang_soluong, itemgiohang_tonggiasp;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemgiohang_img = itemView.findViewById(R.id.itemgiohang_img);
            itemgiohang_tensp = itemView.findViewById(R.id.itemgiohang_tensp);
            itemgiohang_giasp = itemView.findViewById(R.id.itemgiohang_giasp);
            itemgiohang_soluong = itemView.findViewById(R.id.itemgiohang_soluong);
            itemgiohang_tonggiasp = itemView.findViewById(R.id.itemgiohang_tonggiasp);
        }
    }
}
