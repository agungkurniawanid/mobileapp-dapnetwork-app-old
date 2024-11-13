package com.example.mobile_pegawai_dapnetwork_backup.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_pegawai_dapnetwork_backup.R;
import com.example.mobile_pegawai_dapnetwork_backup.model.ModelPaketLayanan;
import com.example.mobile_pegawai_dapnetwork_backup.view.ViewPaketLayanan;

import java.util.List;

public class AdapterLayananPaket extends RecyclerView.Adapter<ViewPaketLayanan> {
    private Context context;
    private List<ModelPaketLayanan> modelPaketLayanans;

    public AdapterLayananPaket(Context context, List<ModelPaketLayanan> modelPaketLayanans) {
        this.context = context;
        this.modelPaketLayanans = modelPaketLayanans;
    }

    @NonNull
    @Override
    public ViewPaketLayanan onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewPaketLayanan(LayoutInflater.from(context).inflate(R.layout.card_paket_layanan, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewPaketLayanan holder, int position) {
        holder.background_paket.setImageResource(modelPaketLayanans.get(position).getBackgroundPaket());
        holder.harga_paket.setText(modelPaketLayanans.get(position).getHargaPaket());
        holder.kecepatan_paket.setText(modelPaketLayanans.get(position).getKecepatanPaket());
    }

    @Override
    public int getItemCount() {
        return modelPaketLayanans.size();
    }
}