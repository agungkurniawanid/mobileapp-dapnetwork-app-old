package com.example.mobile_pegawai_dapnetwork_backup.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_pegawai_dapnetwork_backup.R;
import com.example.mobile_pegawai_dapnetwork_backup.model.ModelDashboardFix;
import com.example.mobile_pegawai_dapnetwork_backup.view.ViewDashboardFix;

import java.util.List;

public class AdapterDashboardFix extends RecyclerView.Adapter<ViewDashboardFix> {
    private Context context;
    private List<ModelDashboardFix> modelDashboardFixes;
    private AdapterDashboardFix.ItemClickListener itemClickListener;

    public AdapterDashboardFix(Context context, List<ModelDashboardFix> modelDashboardFixes, AdapterDashboardFix.ItemClickListener itemClickListener) {
        this.context = context;
        this.modelDashboardFixes = modelDashboardFixes;
        this.itemClickListener = itemClickListener;

    }

    @NonNull
    @Override
    public ViewDashboardFix onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewDashboardFix(LayoutInflater.from(context).inflate(R.layout.card_dashboard_fix, parent, false));
    }

    public void onBindViewHolder(@NonNull ViewDashboardFix holder, int position) {
        holder.vw_cardimage_dashboard_fix.setImageResource(modelDashboardFixes.get(position).getMdl_cardimage_dashboard_fix());
        holder.vw_cardtext1_dashboard_fix.setText(modelDashboardFixes.get(position).getMdl_cardtext_dashboard_fix_text1());
        holder.vw_cardtext2_dashboard_fix.setText(modelDashboardFixes.get(position).getMdl_cardtext_dashboard_fix_text2());
        holder.vw_carddeskripsi_dashboard_fix.setText(modelDashboardFixes.get(position).getMdl_carddeskripsi_dashboard_fix());
        holder.itemView.setOnClickListener(view -> itemClickListener.onItemClick(modelDashboardFixes.get(position)));
    }

    @Override
    public int getItemCount() {
        return modelDashboardFixes.size();
    }

    public interface ItemClickListener {
        void onItemClick(ModelDashboardFix modelDashboardFix);
    }
}
