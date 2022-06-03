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
import com.example.app_banhang.R;
import com.example.app_banhang.model.Item;
import com.example.app_banhang.ultil.Utils;

import java.text.DecimalFormat;
import java.util.List;

public class ChitietAdapter extends RecyclerView.Adapter<ChitietAdapter.MyViewHolder> {
    Context context;
    List<Item> itemList;
    long tongtien;


    public ChitietAdapter(Context context, List<Item> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chitiet,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Item item= itemList.get(position);
        holder.txtten.setText(item.getTensp() + " ");
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtsoluong.setText("Số lượng: "+item.getSoluong());
        Glide.with(context).load(item.getHinhanh()).into(holder.imgchitiet);
        holder.txtgiatien.setText("Giá: "+(decimalFormat.format(Double.parseDouble(item.getGia())*item.getSoluong())));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imgchitiet;
        TextView txtten,txtsoluong,txtgiatien;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgchitiet=itemView.findViewById(R.id.item_imgchitiet);
            txtten=itemView.findViewById(R.id.item_tenspchitiet);
            txtsoluong=itemView.findViewById(R.id.item_soluongchitiet);
            txtgiatien=itemView.findViewById(R.id.item_giachitiet);


        }
    }
}
