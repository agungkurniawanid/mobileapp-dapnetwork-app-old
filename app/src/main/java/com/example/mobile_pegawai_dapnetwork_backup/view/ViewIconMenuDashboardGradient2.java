package com.example.mobile_pegawai_dapnetwork_backup.view;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_pegawai_dapnetwork_backup.R;

public class ViewIconMenuDashboardGradient2 extends RecyclerView.ViewHolder {
    public TextView text1_db2_view;
    public RelativeLayout backgroundIcon_db2_view;
    public ImageView imageIcon_db2_view;
    public ViewIconMenuDashboardGradient2(@NonNull View itemView) {
        super(itemView);
        text1_db2_view = itemView.findViewById(R.id.informasiclienttext_db1);
        backgroundIcon_db2_view = itemView.findViewById(R.id.wrapper_icon_db2);
        imageIcon_db2_view = itemView.findViewById(R.id.icon_informasiclient2);
    }
}
