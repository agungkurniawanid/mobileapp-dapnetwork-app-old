package com.example.mobile_pegawai_dapnetwork_backup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class Welcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ImageView loadingImg = findViewById(R.id.loading);

        Glide.with(this)
                .asGif()
                .load(R.drawable.loading)
                .into(loadingImg);
        // Menggunakan Handler untuk menunda perpindahan ke frame LoginRegister
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Intent untuk beralih ke frame LoginRegister
                Intent intent = new Intent(Welcome.this, LoginRegister.class);
                startActivity(intent);
                finish();
                // Menutup frame Welcome agar tidak dapat dikembalikan dengan tombol back
                finish();
            }
        }, 2500);
    }
}