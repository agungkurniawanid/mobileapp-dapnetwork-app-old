package com.example.mobile_pegawai_dapnetwork_backup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mobile_pegawai_dapnetwork_backup.adapter.AdapterLayananPaket;
import com.example.mobile_pegawai_dapnetwork_backup.classes.CONFIG_IP;
import com.example.mobile_pegawai_dapnetwork_backup.model.ModelPaketLayanan;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PaketLayanan extends AppCompatActivity {

    private RecyclerView paket_layanan;
    private List<ModelPaketLayanan> listData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paket_layanan);

        // todo fungsi btn kembali paket layanan
        ImageView btnKembaliPaketLayanan = findViewById(R.id.btn_kembali_paket_layanan);
        btnKembaliPaketLayanan.setOnClickListener(v -> {
            super.onBackPressed();
        });

        // todo : set paket layanan
        paket_layanan = findViewById(R.id.recycleview_paket);
        setPaket_layanan();

    }

    private void setPaket_layanan() {
        String URL_GET = CONFIG_IP.HTTP + CONFIG_IP.URL_IP + CONFIG_IP.PATH + "getpaketlist.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_GET, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    listData.clear();
                    JSONObject jsonObject = new JSONObject(response.substring(response.indexOf("{"), response.length()));
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        ModelPaketLayanan modelPaketLayanan = new ModelPaketLayanan();
                        JSONObject object = jsonArray.getJSONObject(i);

                        if(i % 2 == 0){
                            modelPaketLayanan.setBackgroundPaket(R.drawable.background_paket_blue);
                        } else {
                            modelPaketLayanan.setBackgroundPaket(R.drawable.background_paket_orange);
                        }
                        modelPaketLayanan.setHargaPaket("Rp. " + object.getString("harga"));
                        modelPaketLayanan.setKecepatanPaket(object.getString("jenis_paket_layanan"));

                        listData.add(modelPaketLayanan);
                    }
                    int numberOfColumns = 2;
                    GridLayoutManager layoutManager = new GridLayoutManager(PaketLayanan.this, numberOfColumns);
                    paket_layanan.setLayoutManager(layoutManager);
                    AdapterLayananPaket adapter = new AdapterLayananPaket(PaketLayanan.this, listData);
                    paket_layanan.setAdapter(adapter);
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