package com.example.mobile_pegawai_dapnetwork_backup.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mobile_pegawai_dapnetwork_backup.R;
import com.example.mobile_pegawai_dapnetwork_backup.adapter.AdapterCardPemesananPembatalan;
import com.example.mobile_pegawai_dapnetwork_backup.adapter.AdapterIconMenuClient;
import com.example.mobile_pegawai_dapnetwork_backup.adapter.AdapterLayananPaket;
import com.example.mobile_pegawai_dapnetwork_backup.model.ModelCardPemesananPembatalan;
import com.example.mobile_pegawai_dapnetwork_backup.model.ModelIconMenuClient;
import com.example.mobile_pegawai_dapnetwork_backup.model.ModelPaketLayanan;

import java.util.ArrayList;
import java.util.List;

public class Client extends Fragment {

    RecyclerView card_pemesanan_pembatalan;
    RecyclerView card_icon_menuclient;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_client, container, false);

        // todo : untuk set card pemesanan pembatalan
        card_pemesanan_pembatalan = view.findViewById(R.id.recycel_card_2_menuclient);
        setCardPemesananPembatalan();

        // todo : untuk set card icon menu client
        card_icon_menuclient = view.findViewById(R.id.recycle_menuclient_icon);
        setCardIconMenuClient();
        return view;
    }
    private void setCardPemesananPembatalan() {
        List<ModelCardPemesananPembatalan> cardPemesananPembatalans = new ArrayList<>();
        cardPemesananPembatalans.add(new ModelCardPemesananPembatalan("Pemesanan Pasang", "Instalasi", "Lihat apakah masih ada pemesanan instalasi!", R.drawable.illustration_pemesanan));
        cardPemesananPembatalans.add(new ModelCardPemesananPembatalan("Pembatalan Pesan", "Instalasi", "Lihat apakah ada pembatalan instalasi!", R.drawable.illustration_pembatalan));
        int numberOfColumns = 2;
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), numberOfColumns);
        card_pemesanan_pembatalan.setLayoutManager(layoutManager);
        AdapterCardPemesananPembatalan adapter = new AdapterCardPemesananPembatalan(getContext(), cardPemesananPembatalans);
        card_pemesanan_pembatalan.setAdapter(adapter);
    }
    private void setCardIconMenuClient(){
        List<ModelIconMenuClient> cardIconMenuClient = new ArrayList<>();
        cardIconMenuClient.add(new ModelIconMenuClient(R.drawable.icon_client_semua, "Semua Client"));
        cardIconMenuClient.add(new ModelIconMenuClient(R.drawable.icon_bayar_double, "Bayar Double"));
        cardIconMenuClient.add(new ModelIconMenuClient(R.drawable.icon_bayar_lunas, "Bayar Lunas"));
        cardIconMenuClient.add(new ModelIconMenuClient(R.drawable.icon_client_aktif, "Client Aktif"));
        cardIconMenuClient.add(new ModelIconMenuClient(R.drawable.icon_client_tidak_aktif, "Client Non-Aktif"));
        cardIconMenuClient.add(new ModelIconMenuClient(R.drawable.icon_client_baru, "Client Baru"));

        card_icon_menuclient.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        AdapterIconMenuClient adapter = new AdapterIconMenuClient(getContext(), cardIconMenuClient);
        card_icon_menuclient.setAdapter(adapter);
    }
}