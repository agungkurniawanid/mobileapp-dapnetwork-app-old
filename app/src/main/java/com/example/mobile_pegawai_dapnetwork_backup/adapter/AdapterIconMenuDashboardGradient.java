package com.example.mobile_pegawai_dapnetwork_backup.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_pegawai_dapnetwork_backup.R;
import com.example.mobile_pegawai_dapnetwork_backup.model.ModelIconMenuDashboardGradient;
import com.example.mobile_pegawai_dapnetwork_backup.view.ViewIconMenuDashboardGradient;

import java.util.List;

public class AdapterIconMenuDashboardGradient extends RecyclerView.Adapter<ViewIconMenuDashboardGradient> {
    private Context context;
    private List<ModelIconMenuDashboardGradient> modelIconMenuDashboardGradients;
    private ItemClickListener itemClickListener;

    public AdapterIconMenuDashboardGradient(Context context, List<ModelIconMenuDashboardGradient> modelIconMenuDashboardGradients, ItemClickListener itemClickListener) {
        this.context = context;
        this.modelIconMenuDashboardGradients = modelIconMenuDashboardGradients;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewIconMenuDashboardGradient onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewIconMenuDashboardGradient(LayoutInflater.from(context).inflate(R.layout.card_informasi_dashboard1, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewIconMenuDashboardGradient holder, int position) {
        holder.text1_view.setText(modelIconMenuDashboardGradients.get(position).getText1_model());
        holder.text2_view.setText(modelIconMenuDashboardGradients.get(position).getText2_model());
        holder.backgroundIcon_view.setBackgroundResource(modelIconMenuDashboardGradients.get(position).getBackgroundIcon_model());
        holder.imageIcon_view.setImageResource(modelIconMenuDashboardGradients.get(position).getImageIcon_model());
        holder.itemView.setOnClickListener(view -> itemClickListener.onItemClick(modelIconMenuDashboardGradients.get(position)));
    }

    @Override
    public int getItemCount() {
        return modelIconMenuDashboardGradients.size();
    }

    public interface ItemClickListener {
        void onItemClick(ModelIconMenuDashboardGradient modelIconMenuDashboardGradient);
    }
}
