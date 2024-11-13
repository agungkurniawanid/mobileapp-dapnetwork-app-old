package com.example.mobile_pegawai_dapnetwork_backup.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_pegawai_dapnetwork_backup.R;
import com.example.mobile_pegawai_dapnetwork_backup.model.ModelIconMenuDashboardGradient2;
import com.example.mobile_pegawai_dapnetwork_backup.view.ViewIconMenuDashboardGradient2;

import java.util.List;

public class AdapterIconMenuDashboardGradient2 extends RecyclerView.Adapter<ViewIconMenuDashboardGradient2> {
    private Context context;
    private List<ModelIconMenuDashboardGradient2> modelIconMenuDashboardGradients2;
    private ItemClickListener itemClickListener;

    public AdapterIconMenuDashboardGradient2(Context context, List<ModelIconMenuDashboardGradient2> modelIconMenuDashboardGradients2, ItemClickListener itemClickListener) {
        this.context = context;
        this.modelIconMenuDashboardGradients2 = modelIconMenuDashboardGradients2;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewIconMenuDashboardGradient2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewIconMenuDashboardGradient2(LayoutInflater.from(context).inflate(R.layout.card_informasi_dashboard2, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewIconMenuDashboardGradient2 holder, int position) {
        holder.text1_db2_view.setText(modelIconMenuDashboardGradients2.get(position).getText1_db2_model());
        holder.backgroundIcon_db2_view.setBackgroundResource(modelIconMenuDashboardGradients2.get(position).getBackgroundIcon_db2_model());
        holder.imageIcon_db2_view.setImageResource(modelIconMenuDashboardGradients2.get(position).getImageIcon_db2_model());
        holder.itemView.setOnClickListener(view -> itemClickListener.onItemClick(modelIconMenuDashboardGradients2.get(position)));
    }

    @Override
    public int getItemCount() {
        return modelIconMenuDashboardGradients2.size();
    }

    public interface ItemClickListener {
        void onItemClick(ModelIconMenuDashboardGradient2 modelIconMenuDashboardGradient);
    }
}
