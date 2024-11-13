package com.example.mobile_pegawai_dapnetwork_backup.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_pegawai_dapnetwork_backup.R;
import com.example.mobile_pegawai_dapnetwork_backup.model.ModelIconMenuClient;
import com.example.mobile_pegawai_dapnetwork_backup.view.ViewIconMenuClient;

import java.util.List;

public class AdapterIconMenuClient extends RecyclerView.Adapter<ViewIconMenuClient> {

    private Context context;
    private List<ModelIconMenuClient> iconMenuClients;

    public AdapterIconMenuClient(Context context, List<ModelIconMenuClient> iconMenuClients) {
        this.context = context;
        this.iconMenuClients = iconMenuClients;
    }

    @NonNull
    @Override
    public ViewIconMenuClient onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewIconMenuClient(LayoutInflater.from(context).inflate(R.layout.card_menuclient_icon, parent, false));
    }

    public void onBindViewHolder(@NonNull ViewIconMenuClient holder, int position) {
        holder.vw_image_menuclient.setImageResource(iconMenuClients.get(position).getMdl_icon_menuclient());
        holder.vw_text_icon_menuclient.setText(iconMenuClients.get(position).getMdl_text_icon_menuclient());
    }

    @Override
    public int getItemCount() {
        return iconMenuClients.size();
    }
}
