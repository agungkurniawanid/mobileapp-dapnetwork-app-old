package com.example.mobile_pegawai_dapnetwork_backup.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mobile_pegawai_dapnetwork_backup.AddClient;
import com.example.mobile_pegawai_dapnetwork_backup.R;
public class Transaction extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_transaction, container, false);

        // todo btn klik tombol transaksi
        TextView btnKlik = view.findViewById(R.id.btn_ke_transaksi);
        btnKlik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddClient.class);
                startActivity(intent);
            }
        });

        return view;
    }
}