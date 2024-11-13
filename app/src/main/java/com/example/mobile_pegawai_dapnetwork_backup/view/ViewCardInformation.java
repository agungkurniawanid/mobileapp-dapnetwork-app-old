package com.example.mobile_pegawai_dapnetwork_backup.view;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mobile_pegawai_dapnetwork_backup.R;

public class ViewCardInformation extends RecyclerView.ViewHolder {
    public ImageView card_image_view;
    public TextView card_judul_view, card_deskipsi_view, card_jumlah_plg_view;

    public ViewCardInformation(@NonNull View itemView) {
        super(itemView);
        card_image_view = itemView.findViewById(R.id.background_card);
        card_judul_view = itemView.findViewById(R.id.judul_card);
        card_deskipsi_view = itemView.findViewById(R.id.deskripsi_card);
        card_jumlah_plg_view = itemView.findViewById(R.id.jumlah_pelanggan);
    }
}
