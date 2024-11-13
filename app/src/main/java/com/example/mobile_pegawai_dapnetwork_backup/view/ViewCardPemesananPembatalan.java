package com.example.mobile_pegawai_dapnetwork_backup.view;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_pegawai_dapnetwork_backup.R;

public class ViewCardPemesananPembatalan extends RecyclerView.ViewHolder {
    public ImageView vw_image_pemesanan_pembatalan;
    public TextView vw_text_pemesanan_instalasi, vw_text_instalasi, vw_text_deskripsi_pemesanan_pembatalan;

    public ViewCardPemesananPembatalan(@NonNull View itemView) {
        super(itemView);
        vw_image_pemesanan_pembatalan = itemView.findViewById(R.id.image_pemesanan_pembatalan);
        vw_text_pemesanan_instalasi = itemView.findViewById(R.id.text_instalasi);
        vw_text_instalasi = itemView.findViewById(R.id.text_pemesanan_pasang);
        vw_text_deskripsi_pemesanan_pembatalan = itemView.findViewById(R.id.deskripsi_pemesanan_pembatalan);
    }
}
