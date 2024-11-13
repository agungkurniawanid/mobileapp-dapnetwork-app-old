package com.example.mobile_pegawai_dapnetwork_backup.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_pegawai_dapnetwork_backup.R;
import com.example.mobile_pegawai_dapnetwork_backup.model.ModelListBtnProduct;
import com.example.mobile_pegawai_dapnetwork_backup.view.ViewListBtnProduct;

import java.util.List;

public class AdapterListBtnProduct extends RecyclerView.Adapter<ViewListBtnProduct> {
    private Context context;
    private List<ModelListBtnProduct> modelListBtnProducts;

    public AdapterListBtnProduct(Context context, List<ModelListBtnProduct> modelListBtnProducts) {
        this.context = context;
        this.modelListBtnProducts = modelListBtnProducts;
    }

    @NonNull
    @Override
    public ViewListBtnProduct onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewListBtnProduct(LayoutInflater.from(context).inflate(R.layout.card_btn_product, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewListBtnProduct holder, int position) {
        holder.vw_listbtn_icon_product.setImageResource(modelListBtnProducts.get(position).getMdl_listbtn_icon_product());
        holder.vw_listbtn_text_product.setText(modelListBtnProducts.get(position).getMdl_listbtn_text_product());
    }
    @Override
    public int getItemCount() {
        return modelListBtnProducts.size();
    }
}
