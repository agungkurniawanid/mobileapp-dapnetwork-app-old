package com.example.mobile_pegawai_dapnetwork_backup.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_pegawai_dapnetwork_backup.R;
import com.example.mobile_pegawai_dapnetwork_backup.model.ModelPemesanan;
import com.example.mobile_pegawai_dapnetwork_backup.model.ModelPeraturanPegawai;
import com.example.mobile_pegawai_dapnetwork_backup.view.ViewClientUmum;
import com.example.mobile_pegawai_dapnetwork_backup.view.ViewPemesanan;
import com.example.mobile_pegawai_dapnetwork_backup.view.ViewPeraturanPegawai;

import java.util.List;
import java.util.zip.Inflater;

public class AdapterPemesanan extends RecyclerView.Adapter<ViewPemesanan> {

    Context context;
    List<ModelPemesanan> pemesanans;
    LayoutInflater inflater;

    // Antarmuka untuk menanggapi klik tombol
    public interface OnSelesaiInstalasiClickListener {
        void onSelesaiInstalasiClick(int position);
    }
    private OnSelesaiInstalasiClickListener selesaiInstalasiClickListener;


    public AdapterPemesanan(Context context, List<ModelPemesanan> pemesanans) {
        this.context = context;
        this.pemesanans = pemesanans;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewPemesanan onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.card_pemesanan, parent, false);
        return new ViewPemesanan(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewPemesanan holder, int position) {
        holder.vw_nama_client.setText(pemesanans.get(position).getMdl_nama_client());
        holder.vw_tanggal_pesan_instalasi.setText(pemesanans.get(position).getMdl_tanggal_pesan_instalasi());
        holder.vw_status_pemesanan_instalasi.setText(pemesanans.get(position).getMdl_status_pemesanan_instalasi());
        holder.vw_tanggal_instalasi.setText(pemesanans.get(position).getMdl_tanggal_instalasi());
        holder.vw_catatan.setText(pemesanans.get(position).getMdl_catatan());
        holder.vw_jenis_paket_layanan.setText(pemesanans.get(position).getMdl_jenis_paket_layanan());
        holder.vw_harga.setText(String.valueOf(pemesanans.get(position).getMdl_harga()));
        holder.vw_status_pembayaran.setText(pemesanans.get(position).getMdl_status_pembayaran());
        holder.vw_id_pemesanan.setText(String.valueOf(pemesanans.get(position).getMdl_id_pemesanan()));
        holder.vw_id_client.setText(String.valueOf(pemesanans.get(position).getMdl_id_client()));
        holder.vw_id_paket_layanan.setText(String.valueOf(pemesanans.get(position).getMdl_id_paket_layanan()));
        holder.vw_id_pembayaran.setText(String.valueOf(pemesanans.get(position).getMdl_id_pembayaran()));

        // Menanggapi klik tombol di dalam adapter
        holder.vw_btn_selesai_instalasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selesaiInstalasiClickListener != null) {
                    int adapterPosition = holder.getAdapterPosition();
                    if (adapterPosition != RecyclerView.NO_POSITION) {
                        selesaiInstalasiClickListener.onSelesaiInstalasiClick(adapterPosition);
                    }
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return pemesanans.size();
    }

    public void setOnSelesaiInstalasiClickListener(OnSelesaiInstalasiClickListener listener) {
        this.selesaiInstalasiClickListener = listener;
    }
}
