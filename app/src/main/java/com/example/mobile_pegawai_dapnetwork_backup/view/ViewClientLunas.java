package com.example.mobile_pegawai_dapnetwork_backup.view;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import com.example.mobile_pegawai_dapnetwork_backup.R;
import androidx.recyclerview.widget.RecyclerView;

public class ViewClientLunas extends RecyclerView.ViewHolder {
    public ImageView vw_img_client_lunas;
    public TextView vw_name_client_lunas, vw_status_client_lunas, vw_tagihan_client_lunas;
    public ViewClientLunas(@NonNull View itemView) {
        super(itemView);
        this.vw_img_client_lunas = itemView.findViewById(R.id.img_client_lunas);
        this.vw_name_client_lunas = itemView.findViewById(R.id.name_client_lunas);
        this.vw_status_client_lunas = itemView.findViewById(R.id.status_client_lunas);
        this.vw_tagihan_client_lunas = itemView.findViewById(R.id.tagihan_client_lunas);
    }
}
