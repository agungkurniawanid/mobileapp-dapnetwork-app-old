// AdapterCardInformation
package com.example.mobile_pegawai_dapnetwork_backup.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_pegawai_dapnetwork_backup.R;
import com.example.mobile_pegawai_dapnetwork_backup.model.ModelCardInformation;
import com.example.mobile_pegawai_dapnetwork_backup.view.ViewCardInformation;

import java.util.List;

public class AdapterCardInformation extends RecyclerView.Adapter<ViewCardInformation> {

    private Context context;
    private List<ModelCardInformation> modelCardInformations;
    private ItemClickListener itemClickListener;

    public AdapterCardInformation(Context context, List<ModelCardInformation> modelCardInformations, ItemClickListener itemClickListener) {
        this.context = context;
        this.modelCardInformations = modelCardInformations;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewCardInformation onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewCardInformation(LayoutInflater.from(context).inflate(R.layout.card_dashboard_fix, parent, false));
    }

    public void onBindViewHolder(@NonNull ViewCardInformation holder, int position) {
        holder.card_image_view.setImageResource(modelCardInformations.get(position).getCard_image_model());
        holder.card_judul_view.setText(modelCardInformations.get(position).getCard_judul_model());
        holder.card_deskipsi_view.setText(modelCardInformations.get(position).getCard_deskripsi_model());
        holder.card_jumlah_plg_view.setText(String.valueOf(modelCardInformations.get(position).getCard_jumlah_plg_model()));

        holder.itemView.setOnClickListener(view -> itemClickListener.onItemClick(modelCardInformations.get(position)));
    }

    @Override
    public int getItemCount() {
        return modelCardInformations.size();
    }

    public interface ItemClickListener {
        void onItemClick(ModelCardInformation modelCardInformation);
    }
}
