package com.example.mobile_pegawai_dapnetwork_backup.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_pegawai_dapnetwork_backup.R;
import com.example.mobile_pegawai_dapnetwork_backup.model.ModelBtnSemuaClient;
import com.example.mobile_pegawai_dapnetwork_backup.model.ModelPemesanan;
import com.example.mobile_pegawai_dapnetwork_backup.view.ViewBtnSemuaClient;
import com.example.mobile_pegawai_dapnetwork_backup.view.ViewPemesanan;

import java.util.List;

public class AdapterBtnSemuaClient extends RecyclerView.Adapter<ViewBtnSemuaClient> {
    private Context context;
    private List<ModelBtnSemuaClient> btnSemuaClients;
    ItemClickListener itemClickListener;

    public AdapterBtnSemuaClient(Context context, List<ModelBtnSemuaClient> btnSemuaClients, ItemClickListener itemClickListener) {
        this.context = context;
        this.btnSemuaClients = btnSemuaClients;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewBtnSemuaClient onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewBtnSemuaClient(LayoutInflater.from(context).inflate(R.layout.card_btn_filter_semuaclient, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewBtnSemuaClient holder, int position) {
        holder.vw_icon_btn_semuaclient.setImageResource(btnSemuaClients.get(position).getMdl_img_btn_semua_client());
        holder.vw_text_btn_semuaclient.setText(btnSemuaClients.get(position).getMdl_text_btn_semua_client());

        holder.itemView.setOnClickListener(view -> itemClickListener.onItemClick(btnSemuaClients.get(position)));
    }

    @Override
    public int getItemCount() {
        return btnSemuaClients.size();
    }

    public interface ItemClickListener {
        void onItemClick(ModelBtnSemuaClient modelBtnSemuaClient);
    }
}
