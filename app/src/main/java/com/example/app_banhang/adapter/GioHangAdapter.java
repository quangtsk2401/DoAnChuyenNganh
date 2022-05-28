package com.example.app_banhang.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.IInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AlphabetIndexer;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.app_banhang.Interface.I_ImageClickListenner;
import com.example.app_banhang.model.EventBus.TinhTongEvent;
import com.example.app_banhang.model.GioHang;
import com.example.app_banhang.R;
import com.example.app_banhang.ultil.Utils;

import org.greenrobot.eventbus.EventBus;

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
    holder.setListenner(new I_ImageClickListenner() {
        @Override
        public void onImageClick(View view, int pos, int giatri) {
            if(giatri == 1){
                if (gioHangList.get(pos).getSoluong() > 1){
                    int soluongmoi = gioHangList.get(pos).getSoluong() -1;
                    gioHangList.get(pos).setSoluong(soluongmoi);
                    holder.itemgiohang_soluong.setText(gioHang.getSoluong() + "");
                    long gia = gioHangList.get(pos).getSoluong() * gioHangList.get(pos).getGia();
                    holder.itemgiohang_tonggiasp.setText(decimalFormat.format(gia));
                    EventBus.getDefault().postSticky(new TinhTongEvent());
                }
                else if(gioHangList.get(pos).getSoluong() == 1){
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
                    builder.setTitle("Thông báo");
                    builder.setMessage("Bạn có chắc chắn muốn xóa sản phẩm này khỏi giỏ hàng không?");
                    builder.setPositiveButton("XÓA", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Utils.manggiohang.remove(pos);
                            notifyDataSetChanged();
                            EventBus.getDefault().postSticky(new TinhTongEvent());
                        }
                    });
                    builder.setNegativeButton("KHÔNG", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    builder.show();
                }
            }
            else
            if(giatri == 2){
                if (gioHangList.get(pos).getSoluong() < 1000){
                    int soluongmoi = gioHangList.get(pos).getSoluong() + 1;
                    gioHangList.get(pos).setSoluong(soluongmoi);
                }
                holder.itemgiohang_soluong.setText(gioHang.getSoluong() + "");
                long gia = gioHangList.get(pos).getSoluong() * gioHangList.get(pos).getGia();
                holder.itemgiohang_tonggiasp.setText(decimalFormat.format(gia));
                EventBus.getDefault().postSticky(new TinhTongEvent());
            }
        }
    });
    }

    @Override
    public int getItemCount() {
        return gioHangList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView itemgiohang_img, imgtru, imgcong;
        TextView itemgiohang_tensp, itemgiohang_giasp,itemgiohang_soluong, itemgiohang_tonggiasp;
        I_ImageClickListenner listenner;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemgiohang_img = itemView.findViewById(R.id.itemgiohang_img);
            itemgiohang_tensp = itemView.findViewById(R.id.itemgiohang_tensp);
            itemgiohang_giasp = itemView.findViewById(R.id.itemgiohang_giasp);
            itemgiohang_soluong = itemView.findViewById(R.id.itemgiohang_soluong);
            itemgiohang_tonggiasp = itemView.findViewById(R.id.itemgiohang_tonggiasp);
            imgtru = itemView.findViewById(R.id.itemgiohang_tru);
            imgcong = itemView.findViewById(R.id.itemgiohang_cong);

            imgtru.setOnClickListener(this);
            imgcong.setOnClickListener(this);
        }

        public void setListenner(I_ImageClickListenner listenner) {
            this.listenner = listenner;
        }

        @Override
        public void onClick(View view) {
            if( view == imgtru){
                listenner.onImageClick(view, getAdapterPosition(),1);
            }
            else{
                if (view == imgcong){
                    listenner.onImageClick(view, getAdapterPosition(), 2);
                }
            }
        }
    }
}
