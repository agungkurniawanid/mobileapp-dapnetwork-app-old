package com.example.mobile_pegawai_dapnetwork_backup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class FiturPenagihan extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitur_penagihan);
        ImageView imgFpen1 = findViewById(R.id.img_fpen1);
        ImageView imgFpen2 = findViewById(R.id.img_fpen2);
        ImageView imgFpen3 = findViewById(R.id.img_fpen3);
        ImageView imgFpen4 = findViewById(R.id.img_fpen4);

        Glide.with(this).load(R.drawable.pen_1).into(imgFpen1);
        Glide.with(this).load(R.drawable.pen_2).into(imgFpen2);
        Glide.with(this).load(R.drawable.pen_3).into(imgFpen3);
        Glide.with(this).load(R.drawable.pen_4).into(imgFpen4);

        ImageView fpenBtnKembali = findViewById(R.id.fpen_btn_kembali);
        fpenBtnKembali.setOnClickListener(v -> {
            Intent intent = new Intent(FiturPenagihan.this, MainApplication.class);
            startActivity(intent);
            finish();
        });
    }
}