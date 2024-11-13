package com.example.mobile_pegawai_dapnetwork_backup.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_pegawai_dapnetwork_backup.R;
import com.example.mobile_pegawai_dapnetwork_backup.model.ModelPeraturanPegawai;
import com.example.mobile_pegawai_dapnetwork_backup.view.ViewPeraturanPegawai;

import java.util.List;

public class AdapterPeraturanPegawai extends RecyclerView.Adapter<ViewPeraturanPegawai> {
    private Context context;
    private List<ModelPeraturanPegawai> modelPeraturanPegawais;

    public AdapterPeraturanPegawai(Context context, List<ModelPeraturanPegawai> modelPeraturanPegawais) {
        this.context = context;
        this.modelPeraturanPegawais = modelPeraturanPegawais;
    }

    @NonNull
    @Override
    public ViewPeraturanPegawai onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewPeraturanPegawai(LayoutInflater.from(context).inflate(R.layout.card_peraturan_pegawai, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewPeraturanPegawai holder, int position) {
        holder.vw_img_number_peraturan.setImageResource(modelPeraturanPegawais.get(position).getMdl_img_number_peraturan());
        holder.vw_text_peraturan_pegawai.setText(modelPeraturanPegawais.get(position).getMdl_text_peraturan_pegawai());
    }
    @Override
    public int getItemCount() {
        return modelPeraturanPegawais.size();
    }
}
