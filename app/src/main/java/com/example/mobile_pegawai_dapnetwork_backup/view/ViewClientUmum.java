package com.example.mobile_pegawai_dapnetwork_backup.view;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.mobile_pegawai_dapnetwork_backup.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewClientUmum extends RecyclerView.ViewHolder {
    public ImageView vw_image_client_umum;
    public TextView vw_name_client_umum, vw_id_client_umum, vw_harga_paket;
    public ViewClientUmum(@NonNull View itemView) {
        super(itemView);
        vw_image_client_umum = itemView.findViewById(R.id.image_client_umum);
        vw_name_client_umum = itemView.findViewById(R.id.name_client_umum);
        vw_id_client_umum = itemView.findViewById(R.id.id_client_umum);
        vw_harga_paket = itemView.findViewById(R.id.harga_paket);
    }
}
