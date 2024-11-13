package com.example.mobile_pegawai_dapnetwork_backup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class FiturPemesanan extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitur_pemesanan);

        // variable ui
        ImageView imgFp1 = findViewById(R.id.img_fp1);
        ImageView imgFp2 = findViewById(R.id.img_fp2);
        ImageView imgFp3 = findViewById(R.id.img_fp3);
        ImageView fpBtnKembali = findViewById(R.id.fp_btn_kembali);

        // set onclick
        fpBtnKembali.setOnClickListener(v -> {
            Intent intent = new Intent(FiturPemesanan.this, MainApplication.class);
            startActivity(intent);
        });

        // set glide
        Glide.with(this).load(R.drawable.fp1).into(imgFp1);
        Glide.with(this).load(R.drawable.fp2).into(imgFp2);
        Glide.with(this).load(R.drawable.fp3).into(imgFp3);
    }

}