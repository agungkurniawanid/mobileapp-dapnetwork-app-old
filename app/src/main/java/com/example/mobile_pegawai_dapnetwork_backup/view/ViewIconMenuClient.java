package com.example.mobile_pegawai_dapnetwork_backup.view;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_pegawai_dapnetwork_backup.R;

public class ViewIconMenuClient extends RecyclerView.ViewHolder {
    public ImageView vw_image_menuclient;
    public TextView vw_text_icon_menuclient;

    public ViewIconMenuClient(@NonNull View itemView) {
        super(itemView);
        vw_image_menuclient = itemView.findViewById(R.id.icon_menuclient);
        vw_text_icon_menuclient = itemView.findViewById(R.id.text_icon_menuclient);
    }
}
