package com.example.mobile_pegawai_dapnetwork_backup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class FiturTransaksi extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitur_transaksi);

        ImageView gif1Ft = findViewById(R.id.gif1_ft);
        ImageView gif2Ft = findViewById(R.id.gif2_ft);
        ImageView gif3ft = findViewById(R.id.gif3_ft);
        ImageView gif4ft = findViewById(R.id.gif4_ft);
        ImageView gif5ft = findViewById(R.id.gif5_ft);
        ImageView gif6ft = findViewById(R.id.gif6_ft);
        ImageView gif7ft = findViewById(R.id.gif7_ft);
        ImageView ftBtnKembali = findViewById(R.id.ft_btn_kembali);

        ftBtnKembali.setOnClickListener(v -> {
            Intent intent = new Intent(FiturTransaksi.this, MainApplication.class);
            startActivity(intent);
            finish();
        });

        // set gambar dari internet
        Glide.with(this).asGif().load(R.drawable.ft_1).into(gif1Ft);
        Glide.with(this).load(R.drawable.ft_2).into(gif2Ft);
        Glide.with(this).load(R.drawable.ft_3).into(gif3ft);
        Glide.with(this).load(R.drawable.ft_4).into(gif4ft);
        Glide.with(this).load(R.drawable.ft_5).into(gif5ft);
        Glide.with(this).load(R.drawable.ft_6).into(gif6ft);
        Glide.with(this).load(R.drawable.ft_7).into(gif7ft);
    }
}