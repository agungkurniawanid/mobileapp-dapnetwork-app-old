package com.example.mobile_pegawai_dapnetwork_backup.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_pegawai_dapnetwork_backup.R;
import com.example.mobile_pegawai_dapnetwork_backup.model.ModelProduct;
import com.example.mobile_pegawai_dapnetwork_backup.view.ViewProduct;

import java.util.List;

public class AdapterProduct extends RecyclerView.Adapter<ViewProduct> {

    Context context;
    List<ModelProduct> modelProducts;

    public AdapterProduct(Context context, List<ModelProduct> modelProducts) {
        this.context = context;
        this.modelProducts = modelProducts;
    }

    @NonNull
    @Override
    public ViewProduct onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewProduct(LayoutInflater.from(context).inflate(R.layout.card_product, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewProduct holder, int position) {
    }
    @Override
    public int getItemCount() {
        return modelProducts.size();
    }
}
