package com.example.mobile_pegawai_dapnetwork_backup;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.mobile_pegawai_dapnetwork_backup.classes.CONFIG_IP;
import com.example.mobile_pegawai_dapnetwork_backup.classes.IMAGES;
import com.example.mobile_pegawai_dapnetwork_backup.fragment.Dashboard;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class ProfilePegawai extends AppCompatActivity {

    private String fotoPegawai;

    private LinearLayout btnkembali;
    private EditText idPegawaiPrIn;
    public ImageView fotoPegawaiPr;
    EditText sharedNamaPegawai;
    TextView namaPegawaiPr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_pegawai);

        // variable ui
        fotoPegawaiPr = findViewById(R.id.foto_pegawai_pr);
        idPegawaiPrIn = findViewById(R.id.id_pegawai_pr_in);
        TextView idPegawaiPr = findViewById(R.id.id_pegawai_pr);
        namaPegawaiPr = findViewById(R.id.nama_pegawai_pr);
        Button btnEditProfile = findViewById(R.id.btn_edit_profile);
        LinearLayout menuDetailPegawai = findViewById(R.id.menu_detail_pegawai);
        LinearLayout menuLogout = findViewById(R.id.menu_logout_pegawai);
        LinearLayout menuLayananWifi = findViewById(R.id.menu_layanan_wifi);

        menuLayananWifi.setOnClickListener(v -> {
            Intent intent = new Intent(ProfilePegawai.this, PaketLayanan.class);
            startActivity(intent);
        });

        // todo fungsi logout
        menuLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ProfilePegawai.this);
                builder.setTitle("Konfirmasi Logout");
                builder.setMessage("Apakah Anda yakin ingin logout?");

                // Tombol Oke
                builder.setPositiveButton("Oke", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Aksi jika tombol Oke diklik (contoh: jalankan fungsi logout)
                        performLogout();
                    }
                });

                // Tombol Batal
                builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Aksi jika tombol Batal diklik (contoh: tutup dialog)
                        dialog.dismiss();
                    }
                });

                // Menampilkan AlertDialog
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        // todo : fungsi btn kembali
        btnkembali = findViewById(R.id.btn_kembali_profile);
        btnkembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfilePegawai.this, MainApplication.class);
                startActivity(intent);
            }
        });

        // todo fungsi btn edit
        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfilePegawai.this, EditProfile.class);
                intent.putExtra("id_pegawai", idPegawaiPrIn.getText().toString());
                startActivity(intent);
            }
        });

        // todo get nama dari sharedpreferns
        sharedNamaPegawai = findViewById(R.id.shared_nama_pegawai_pr);
        SharedPreferences sharedPreferences = getSharedPreferences("user_id", MODE_PRIVATE);
        String namaPegawai = sharedPreferences.getString("id_pegawai", "");
        sharedNamaPegawai.setText(namaPegawai);
        getSharedData(namaPegawai);

        // todo get id pegawai
        SharedPreferences preferences3 = getSharedPreferences("user_id", MODE_PRIVATE);
        String idPegawai = preferences3.getString("id_pegawai", "");
        idPegawaiPrIn.setText(idPegawai);
        idPegawaiPr.setText(idPegawai);

        // todo fungsi menu detail pelanggan
        menuDetailPegawai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfilePegawai.this, DetailPegawai.class);
                intent.putExtra("id_pegawai", idPegawaiPrIn.getText().toString());
                startActivity(intent);
            }
        });
    }
    private void performLogout(){
        SharedPreferences preferences = getSharedPreferences("user_id", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
        Intent intent = new Intent(ProfilePegawai.this, LoginRegister.class);
        startActivity(intent);
    }
    private void getSharedData(String nama) {
        String URL = CONFIG_IP.HTTP + CONFIG_IP.URL_IP + CONFIG_IP.PATH + "getshared.php?id=" + nama;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.substring(response.indexOf('{'), response.length()));
                    String status = jsonObject.getString("status");
                    if (status.equals("success")) {
                        JSONArray data = jsonObject.getJSONArray("data");
                        JSONObject dataPegawai = data.getJSONObject(0);
                        namaPegawaiPr.setText(dataPegawai.getString("namaawal") + " " + dataPegawai.getString("namaakhir"));
                        fotoPegawai = IMAGES.URL_IMAGES + dataPegawai.getString("foto_pegawai");;
                        Glide.with(ProfilePegawai.this).load(fotoPegawai).into(fotoPegawaiPr);
                    }
                } catch (Exception error) {
                    error.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Glide.get(getApplicationContext()).clearDiskCache();
        Glide.get(getApplicationContext()).clearMemory();
    }
}
