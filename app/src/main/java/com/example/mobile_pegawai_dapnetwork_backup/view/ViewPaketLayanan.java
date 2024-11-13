package com.example.mobile_pegawai_dapnetwork_backup.view;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_pegawai_dapnetwork_backup.R;

public class ViewPaketLayanan extends RecyclerView.ViewHolder {
    public ImageView background_paket;
    public TextView kecepatan_paket, harga_paket;

    public ViewPaketLayanan(@NonNull View itemView) {
        super(itemView);
        background_paket = itemView.findViewById(R.id.background_paket);
        kecepatan_paket = itemView.findViewById(R.id.kecepatan_paket);
        harga_paket = itemView.findViewById(R.id.harga_paket);
    }
}
