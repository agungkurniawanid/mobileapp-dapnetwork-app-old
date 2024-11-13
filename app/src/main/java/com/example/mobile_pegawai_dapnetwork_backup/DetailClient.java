package com.example.mobile_pegawai_dapnetwork_backup;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.mobile_pegawai_dapnetwork_backup.classes.CONFIG_IP;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DetailClient extends AppCompatActivity {

    private TextView namaClient, idClient, statusClient,
    jenisKelamin, harga, BTN_OK,
    forCatatan, dtMasukkanPembayaran;
    private ImageView fotoClient;
    private EditText dtNomorTelfon, dtAlamat, dtEmail;

    private String catatan, alamatmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_client);

        // variable ui
        namaClient = findViewById(R.id.nama_client);
        idClient = findViewById(R.id.detal_id_client);
        fotoClient = findViewById(R.id.detail_foto_client);
        statusClient = findViewById(R.id.status_client);
        jenisKelamin = findViewById(R.id.jenis_kelamin);
        harga = findViewById(R.id.harga_paket_dipesan);
        dtNomorTelfon = findViewById(R.id.dt_nomor_telfon);
        dtAlamat = findViewById(R.id.dt_alamat);
        dtEmail = findViewById(R.id.dt_email);
        forCatatan = findViewById(R.id.for_catatan);
        dtMasukkanPembayaran = findViewById(R.id.dt_masukkan_pembayaran);

        // click show catatan
        forCatatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCatatan();
            }
        });


        RelativeLayout wa = findViewById(R.id.whatsapp);
        RelativeLayout telpon = findViewById(R.id.telfon);

        // fungsi telfon
        telpon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + dtNomorTelfon.getText().toString()));
                startActivity(intent);
            }
        });

        // fungsi whatsapp
        wa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(
                                String.format("https://api.whatsapp.com/send?phone=%s&text=%s", dtNomorTelfon.getText().toString(), "" +
                                        "Assalamualaikum, kami ingin mengingatkan mengenai pembayaran WiFi bulanan. Mohon segera mempersiapkan pembayaran untuk kelancaran layanan. Terima kasih.")
                        )
                );

                // Start the activity to open WhatsApp
                startActivity(intent);
            }
        });


        // set variable ui put extra
        Intent getNamaClient = getIntent();
        Intent getIdClient = getIntent();
        Intent getFotoClient = getIntent();
        namaClient.setText(getNamaClient.getStringExtra("namaClient"));
        idClient.setText(getIdClient.getStringExtra("idClient"));
        Glide.with(this).load(getFotoClient.getStringExtra("fotoClient")).into(fotoClient);

        // click ke frame pembayaran tagihan
        dtMasukkanPembayaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // put id client
                Intent intent = new Intent(DetailClient.this, TagihanBulanan.class);
                intent.putExtra("idClient", getIdClient.getStringExtra("idClient"));
                startActivity(intent);
            }
        });

        // function
        getDetailClient(getIdClient.getStringExtra("idClient"));
    }


    private void getDetailClient(String idClient) {
        String URL_GET_DETAIL = CONFIG_IP.HTTP + CONFIG_IP.URL_IP + CONFIG_IP.PATH + "detailclient.php?id_client=" + idClient;

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_GET_DETAIL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.substring(response.indexOf('{'), response.length()));
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        statusClient.setText(jsonObject1.getString("status_client"));
                        jenisKelamin.setText(jsonObject1.getString("jenis_kelamin"));
                        harga.setText(jsonObject1.getString("harga"));
                        dtNomorTelfon.setText(jsonObject1.getString("nomor_telepon"));
                        dtAlamat.setText(jsonObject1.getString("alamat"));
                        dtEmail.setText(jsonObject1.getString("email"));
                        catatan = jsonObject1.getString("catatan");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(stringRequest);
    }

    public void showCatatan() {
        View dialogView = LayoutInflater.from(DetailClient.this).inflate(R.layout.catatan, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(DetailClient.this);
        builder.setView(dialogView);

        TextView txtCatatan = dialogView.findViewById(R.id.txt_catatan);

        BTN_OK = dialogView.findViewById(R.id.btn_close_catatan);

        final AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();

        txtCatatan.setText(catatan);

        BTN_OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
            }
        });
    }

}