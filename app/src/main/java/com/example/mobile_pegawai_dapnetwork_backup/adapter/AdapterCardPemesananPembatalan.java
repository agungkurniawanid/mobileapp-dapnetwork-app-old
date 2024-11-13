package com.example.mobile_pegawai_dapnetwork_backup.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_pegawai_dapnetwork_backup.R;
import com.example.mobile_pegawai_dapnetwork_backup.model.ModelCardPemesananPembatalan;
import com.example.mobile_pegawai_dapnetwork_backup.view.ViewCardPemesananPembatalan;

import java.util.List;

public class AdapterCardPemesananPembatalan extends RecyclerView.Adapter<ViewCardPemesananPembatalan> {
    private Context context;
    private List<ModelCardPemesananPembatalan> cardPemesananPembatalans;

    public AdapterCardPemesananPembatalan(Context context, List<ModelCardPemesananPembatalan> cardPemesananPembatalans) {
        this.context = context;
        this.cardPemesananPembatalans = cardPemesananPembatalans;
    }

    @NonNull
    @Override
    public ViewCardPemesananPembatalan onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewCardPemesananPembatalan(LayoutInflater.from(context).inflate(R.layout.card_pemesanan_pembatalan, parent, false));
    }

    public void onBindViewHolder(@NonNull ViewCardPemesananPembatalan holder, int position) {
        holder.vw_image_pemesanan_pembatalan.setImageResource(cardPemesananPembatalans.get(position).getMdl_image_pemesanan_pembatalan());
        holder.vw_text_pemesanan_instalasi.setText(cardPemesananPembatalans.get(position).getMdl_text_pemesanan_instalasi());
        holder.vw_text_instalasi.setText(cardPemesananPembatalans.get(position).getMdl_text_instalasi());
        holder.vw_text_deskripsi_pemesanan_pembatalan.setText(cardPemesananPembatalans.get(position).getMdl_deskripsi_pemesanan_pembatalan());
    }

    @Override
    public int getItemCount() {
        return cardPemesananPembatalans.size();
    }
}
