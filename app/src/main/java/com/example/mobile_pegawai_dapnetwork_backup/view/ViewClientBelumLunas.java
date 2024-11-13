package com.example.mobile_pegawai_dapnetwork_backup.view;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_pegawai_dapnetwork_backup.R;

public class ViewClientBelumLunas extends RecyclerView.ViewHolder {
    public ImageView vw_img_client_belumlunas;
    public TextView vw_name_client_belumlunas, vw_status_client_belumlunas, vw_tagihan_client_belumlunas;
    public ViewClientBelumLunas(@NonNull View itemView) {
        super(itemView);
        this.vw_img_client_belumlunas = itemView.findViewById(R.id.img_client_belumlunas);
        this.vw_name_client_belumlunas = itemView.findViewById(R.id.name_client_belumlunas);
        this.vw_status_client_belumlunas = itemView.findViewById(R.id.status_client_belumlunas);
        this.vw_tagihan_client_belumlunas = itemView.findViewById(R.id.tagihan_client_belumlunas);
    }
}
