package com.example.mobile_pegawai_dapnetwork_backup.view;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_pegawai_dapnetwork_backup.R;

public class ViewIconMenuDashboardGradient extends RecyclerView.ViewHolder {
    public TextView text1_view, text2_view;
    public RelativeLayout backgroundIcon_view;
    public ImageView imageIcon_view;
    public ViewIconMenuDashboardGradient(@NonNull View itemView) {
        super(itemView);
        text1_view = itemView.findViewById(R.id.informasiclienttext1);
        text2_view = itemView.findViewById(R.id.informasiclienttext2);
        backgroundIcon_view = itemView.findViewById(R.id.wrapper_icon);
        imageIcon_view = itemView.findViewById(R.id.icon_informasiclient);
    }
}
