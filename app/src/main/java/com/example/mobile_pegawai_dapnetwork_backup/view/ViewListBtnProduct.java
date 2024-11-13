package com.example.mobile_pegawai_dapnetwork_backup.view;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.mobile_pegawai_dapnetwork_backup.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewListBtnProduct extends RecyclerView.ViewHolder {
    public ImageView vw_listbtn_icon_product;
    public TextView vw_listbtn_text_product;
    public ViewListBtnProduct(@NonNull View itemView) {
        super(itemView);
        this.vw_listbtn_icon_product = itemView.findViewById(R.id.listbtn_icon_product);
        this.vw_listbtn_text_product = itemView.findViewById(R.id.listbtn_text_product);
    }
}
