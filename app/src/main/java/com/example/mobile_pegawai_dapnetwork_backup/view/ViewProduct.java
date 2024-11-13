package com.example.mobile_pegawai_dapnetwork_backup.view;

import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import com.example.mobile_pegawai_dapnetwork_backup.R;
import androidx.recyclerview.widget.RecyclerView;

public class ViewProduct extends RecyclerView.ViewHolder {
    public CheckBox vw_checkbox_product;
    public ImageView vw_image_product, vw_tambah_stok, vw_kurang_stok;
    public TextView vw_name_product, vw_stok_product;
    public EditText vw_id_product, vw_jumlah_stok;
    public ViewProduct(@NonNull View itemView) {
        super(itemView);
    }
}
