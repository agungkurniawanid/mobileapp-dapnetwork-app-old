package com.example.mobile_pegawai_dapnetwork_backup.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_pegawai_dapnetwork_backup.R;
import com.example.mobile_pegawai_dapnetwork_backup.model.ModelClientBelumLunas;
import com.example.mobile_pegawai_dapnetwork_backup.model.ModelClientLunas;
import com.example.mobile_pegawai_dapnetwork_backup.view.ViewClientBelumLunas;
import com.example.mobile_pegawai_dapnetwork_backup.view.ViewClientLunas;

import java.util.List;

public class AdapterClientBelumLunas extends RecyclerView.Adapter<ViewClientBelumLunas> {
    private Context context;
    private List<ModelClientBelumLunas> modelClientBelumLunas;

    public AdapterClientBelumLunas(Context context, List<ModelClientBelumLunas> modelClientBelumLunas) {
        this.context = context;
        this.modelClientBelumLunas = modelClientBelumLunas;
    }

    @NonNull
    @Override
    public ViewClientBelumLunas onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewClientBelumLunas(LayoutInflater.from(context).inflate(R.layout.card_client_tagihan_belumlunas, parent, false));
    }

    public void onBindViewHolder(@NonNull ViewClientBelumLunas holder, int position) {
        holder.vw_img_client_belumlunas.setImageResource(modelClientBelumLunas.get(position).getMdl_img_client_belumlunas());
        holder.vw_name_client_belumlunas.setText(modelClientBelumLunas.get(position).getMdl_name_client_belumlunas());
        holder.vw_status_client_belumlunas.setText(modelClientBelumLunas.get(position).getMdl_status_client_belumlunas());
        holder.vw_tagihan_client_belumlunas.setText(String.valueOf(modelClientBelumLunas.get(position).getMdl_tagihan_client_belumlunas()));
    }

    @Override
    public int getItemCount() {
        return modelClientBelumLunas.size();
    }
}
