package com.example.mobile_pegawai_dapnetwork_backup.view;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import com.example.mobile_pegawai_dapnetwork_backup.R;
import androidx.recyclerview.widget.RecyclerView;

public class ViewPeraturanPegawai extends RecyclerView.ViewHolder {
    public ImageView vw_img_number_peraturan;
    public TextView vw_text_peraturan_pegawai;
    public ViewPeraturanPegawai(@NonNull View itemView) {
        super((itemView));
        vw_img_number_peraturan = itemView.findViewById(R.id.img_number_peraturan);
        vw_text_peraturan_pegawai = itemView.findViewById(R.id.text_peraturan_pegawai);
    }
}
