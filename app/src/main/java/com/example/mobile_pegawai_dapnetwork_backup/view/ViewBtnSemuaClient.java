package com.example.mobile_pegawai_dapnetwork_backup.view;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.mobile_pegawai_dapnetwork_backup.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewBtnSemuaClient extends RecyclerView.ViewHolder {
    public ImageView vw_icon_btn_semuaclient;
    public TextView vw_text_btn_semuaclient;
    public ViewBtnSemuaClient(@NonNull View itemView) {
        super(itemView);
        vw_icon_btn_semuaclient = itemView.findViewById(R.id.icon_btn_semuaclitn);
        vw_text_btn_semuaclient = itemView.findViewById(R.id.text_btn_semuaclient);
    }
}
