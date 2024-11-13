package com.example.mobile_pegawai_dapnetwork_backup.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_pegawai_dapnetwork_backup.R;
import com.example.mobile_pegawai_dapnetwork_backup.model.ModelClientLunas;
import com.example.mobile_pegawai_dapnetwork_backup.view.ViewClientLunas;

import java.util.List;

public class AdapterClientLunas extends RecyclerView.Adapter<ViewClientLunas> {
    private Context context;
    private List<ModelClientLunas> modelClienLunas;

    public AdapterClientLunas(Context context, List<ModelClientLunas> modelClienLunas) {
        this.context = context;
        this.modelClienLunas = modelClienLunas;
    }

    @NonNull
    @Override
    public ViewClientLunas onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewClientLunas(LayoutInflater.from(context).inflate(R.layout.card_client_tagihan_lunas, parent, false));
    }

    public void onBindViewHolder(@NonNull ViewClientLunas holder, int position) {
        holder.vw_img_client_lunas.setImageResource(modelClienLunas.get(position).getMdl_img_client_lunas());
        holder.vw_name_client_lunas.setText(modelClienLunas.get(position).getMdl_name_client_lunas());
        holder.vw_status_client_lunas.setText(modelClienLunas.get(position).getMdl_status_client_lunas());
        holder.vw_tagihan_client_lunas.setText(String.valueOf(modelClienLunas.get(position).getMdl_tagihan_client_lunas()));
    }

    @Override
    public int getItemCount() {
        return modelClienLunas.size();
    }
}
