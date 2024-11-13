package com.example.mobile_pegawai_dapnetwork_backup.view;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mobile_pegawai_dapnetwork_backup.R;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewDashboardFix extends RecyclerView.ViewHolder {
    public ImageView vw_cardimage_dashboard_fix;
    public TextView vw_cardtext1_dashboard_fix, vw_cardtext2_dashboard_fix, vw_carddeskripsi_dashboard_fix;
    public ViewDashboardFix(@NonNull View itemView) {
        super(itemView);
        vw_cardimage_dashboard_fix = itemView.findViewById(R.id.cardimg_dashboard_fix);
        vw_cardtext1_dashboard_fix = itemView.findViewById(R.id.cardtext1_dashboard_fix);
        vw_cardtext2_dashboard_fix = itemView.findViewById(R.id.cardtext2_dashboard_fix);
        vw_carddeskripsi_dashboard_fix = itemView.findViewById(R.id.carddeskripsi_dashboard_fix);
    }
}
