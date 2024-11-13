package com.example.mobile_pegawai_dapnetwork_backup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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

import org.json.JSONArray;
import org.json.JSONObject;

public class DetailPegawai extends AppCompatActivity {

    EditText dpIdPegawai, dpNik, dpAlamat, dpEmail, dpTanggalMasuk,
    dpJabatan, dpJenisKelamin, dpTanggalLahir, dpUsername, dpPassword,
    dpGaji, dpNomor, dpStatus, dpHakAkses, dpAgama;
    ImageView dpBtnKembali, dpFotoPegawai;
    TextView dpNamaPegawai, dpIdPegawai2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pegawai);

        // variable
        dpIdPegawai = findViewById(R.id.dp_id_pegawai);
        dpBtnKembali = findViewById(R.id.dp_btn_kembali);
        dpFotoPegawai = findViewById(R.id.dp_foto_pegawai);
        dpNamaPegawai = findViewById(R.id.dp_nama_pegawai);
        dpIdPegawai2 = findViewById(R.id.dp_id_pegawai2);
        dpNik = findViewById(R.id.dp_nik);
        dpAlamat = findViewById(R.id.dp_alamat);
        dpEmail = findViewById(R.id.dp_email);
        dpTanggalMasuk = findViewById(R.id.dp_tanggal_masuk);
        dpJabatan = findViewById(R.id.dp_jabatan);
        dpJenisKelamin = findViewById(R.id.dp_kelamin);
        dpTanggalLahir = findViewById(R.id.dp_tanggal_lahir);
        dpUsername = findViewById(R.id.dp_username);
        dpPassword = findViewById(R.id.dp_password);
        dpGaji = findViewById(R.id.dp_gaji);
        dpNomor = findViewById(R.id.dp_nomor);
        dpStatus = findViewById(R.id.dp_status);
        dpHakAkses = findViewById(R.id.dp_hak_akses);
        dpAgama = findViewById(R.id.dp_agama);

        // get id pegawai
        String idPegawai = getIntent().getStringExtra("id_pegawai");
        dpIdPegawai.setText(idPegawai);
        dpIdPegawai2.setText(idPegawai);

        // kembali ke profile pegawai
        dpBtnKembali.setOnClickListener(click -> {
            Intent intent = new Intent(DetailPegawai.this, ProfilePegawai.class);
            startActivity(intent);
        });

        getDataPegawai(idPegawai);
    }
    private void getDataPegawai(String idPegawai){
        String URL = CONFIG_IP.HTTP + CONFIG_IP.URL_IP + CONFIG_IP.PATH + "getdatapegawai.php?id=" + idPegawai;

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.substring(response.indexOf('{'), response.length()));
                    String status = jsonObject.getString("status");
                    if(status.equals("success")){
                        JSONArray data = jsonObject.getJSONArray("data");
                        JSONObject dataPegawai = data.getJSONObject(0);
                        String URL_FOTO = IMAGES.URL_IMAGES + dataPegawai.getString("foto_pegawai");

                        Glide.with(DetailPegawai.this).load(URL_FOTO).into(dpFotoPegawai);
                        dpNamaPegawai.setText(dataPegawai.getString("namaawal") + " " + dataPegawai.getString("namaakhir"));
                        dpNik.setText(dataPegawai.getString("nik"));
                        dpAlamat.setText(dataPegawai.getString("alamat"));
                        dpEmail.setText(dataPegawai.getString("email"));
                        dpTanggalMasuk.setText(dataPegawai.getString("tanggal_masuk"));
                        dpJabatan.setText(dataPegawai.getString("jabatan"));
                        dpJenisKelamin.setText(dataPegawai.getString("jenis_kelamin"));
                        dpTanggalLahir.setText(dataPegawai.getString("tanggal_lahir"));
                        dpUsername.setText(dataPegawai.getString("username"));
                        dpPassword.setText(dataPegawai.getString("password"));
                        dpGaji.setText(dataPegawai.getString("gaji"));
                        dpNomor.setText(dataPegawai.getString("nomor"));
                        dpStatus.setText(dataPegawai.getString("status"));
                        dpHakAkses.setText(dataPegawai.getString("hak_akses"));
                        dpAgama.setText(dataPegawai.getString("agama"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }
}