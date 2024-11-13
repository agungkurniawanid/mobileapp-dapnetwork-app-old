package com.example.mobile_pegawai_dapnetwork_backup.adapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mobile_pegawai_dapnetwork_backup.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mobile_pegawai_dapnetwork_backup.model.ModelCardInformation;
import com.example.mobile_pegawai_dapnetwork_backup.model.ModelClientUmum;
import com.example.mobile_pegawai_dapnetwork_backup.view.ViewClientUmum;

import java.util.List;

public class AdapterClientUmum extends RecyclerView.Adapter<ViewClientUmum> {

    List<ModelClientUmum> listData;
    LayoutInflater inflater;
    Context context;
    ItemClickListener itemClickListener;

    public AdapterClientUmum(Context context, List<ModelClientUmum> listData, ItemClickListener itemClickListener) {
        this.listData = listData;
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewClientUmum onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.card_client_umum, parent, false);
        return new ViewClientUmum(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewClientUmum holder, int position) {
        Glide.with(context).load(listData.get(position).getMdl_image_client_umum()).into(holder.vw_image_client_umum);
        holder.vw_name_client_umum.setText(listData.get(position).getMdl_name_client_umum());
        holder.vw_id_client_umum.setText(listData.get(position).getMdl_id_client_umum());
        holder.vw_harga_paket.setText(String.valueOf(listData.get(position).getMdl_harga_paket()));

        holder.itemView.setOnClickListener(view -> itemClickListener.onItemClick(listData.get(position)));
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public interface ItemClickListener {
        void onItemClick(ModelClientUmum modelClientUmum);
    }
}