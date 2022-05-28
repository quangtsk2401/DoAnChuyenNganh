package com.example.app_banhang.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.app_banhang.Interface.ItemClickListener;
import com.example.app_banhang.R;
import com.example.app_banhang.activity.ChiTietActivity;
import com.example.app_banhang.model.SanPhamMoi;

import java.text.DecimalFormat;
import java.util.List;

public class PhoneAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    Context context;
    List<SanPhamMoi> array;
    private static final int VIEW_TYPE_DATA = 0;
    private static final int VIEW_TYPE_LOADING = 1;

    public PhoneAdapter(Context context, List<SanPhamMoi> array)
    {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        if(viewType == VIEW_TYPE_DATA)
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sanpham, parent, false);
            return new MyViewHolder(view);
        }
        else
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    public class LoadingViewHolder extends RecyclerView.ViewHolder
    {
        ProgressBar progressBar;
        public LoadingViewHolder(@NonNull View itemView)
        {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressbar);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position)
    {
        if( holder instanceof MyViewHolder)
        {
            MyViewHolder myViewHolder = (MyViewHolder) holder;
            SanPhamMoi sanPhamMoi = array.get(position);
            myViewHolder.tensp.setText(sanPhamMoi.getTensp());
            myViewHolder.idps.setText("Mã sản phẩm: "+sanPhamMoi.getId() +"");
            DecimalFormat decimalFormat = new DecimalFormat("###,###,### ");
            myViewHolder.giasp.setText("Giá: "+decimalFormat.format(Double.parseDouble(sanPhamMoi.getGiasp()))+"VNĐ");
            myViewHolder.mota.setText(sanPhamMoi.getMota());
            Glide.with(context).load(sanPhamMoi.getHinhanh()).into(myViewHolder.imghinhanh);
            myViewHolder.setItemClickListener(new ItemClickListener() {
                @Override
                public void onClick(View view, int pos, boolean isLongClick) {
                    if(!isLongClick)
                    {
                        Intent intent = new Intent(context, ChiTietActivity.class);
                        intent.putExtra("chitiet", sanPhamMoi);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                }
            });
        }
        else
        {
            LoadingViewHolder loadingViewHolder =(LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }

    }

    //Vị trí = null thì xuất loading
    @Override
    public int getItemViewType(int position)
    {
        return array.get(position)==null ? VIEW_TYPE_LOADING:VIEW_TYPE_DATA;
    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView tensp, idps, giasp, mota;
        ImageView imghinhanh;
        private ItemClickListener itemClickListener;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tensp = itemView.findViewById(R.id.itemphone_ten);
            idps = itemView.findViewById(R.id.itemphone_idps);
            giasp = itemView.findViewById(R.id.itemphone_gia);
            mota = itemView.findViewById(R.id.itemphone_mota);
            imghinhanh = itemView.findViewById(R.id.itemphone_image);
            itemView.setOnClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }
        @Override
        public void onClick(View view)
        {
            itemClickListener.onClick(view, getAdapterPosition(), false);
        }
    }
}
