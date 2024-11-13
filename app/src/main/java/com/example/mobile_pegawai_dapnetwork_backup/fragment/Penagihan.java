package com.example.mobile_pegawai_dapnetwork_backup.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mobile_pegawai_dapnetwork_backup.DetailClient;
import com.example.mobile_pegawai_dapnetwork_backup.R;
import com.example.mobile_pegawai_dapnetwork_backup.adapter.AdapterClientUmum;
import com.example.mobile_pegawai_dapnetwork_backup.classes.CONFIG_IP;
import com.example.mobile_pegawai_dapnetwork_backup.classes.IMAGES;
import com.example.mobile_pegawai_dapnetwork_backup.model.ModelClientUmum;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Penagihan extends Fragment implements AdapterClientUmum.ItemClickListener {

    RecyclerView card_client_umum;
    List<ModelClientUmum> listData = new ArrayList<>();
    Spinner filterLokasi;
    ImageView error;
    String URL_GET_PENAGIHAN = CONFIG_IP.HTTP+CONFIG_IP.URL_IP+CONFIG_IP.PATH+"clientpenagihan.php";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_penagihan, container, false);

        card_client_umum = view.findViewById(R.id.recycel_penagihan);

        // variable ui
        filterLokasi = view.findViewById(R.id.filter_lokasi);
        TextView waktuPenagihan = view.findViewById(R.id.waktu_penagihan);

        // set waktu title penagihan
        Calendar calendar = Calendar.getInstance();
        int bulan = calendar.get(Calendar.MONTH);
        int tahun = calendar.get(Calendar.YEAR);

        String[] namaBulan = new String[]{"Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember"};
        String teksWaktu = namaBulan[bulan] + " " + tahun;
        waktuPenagihan.setText(teksWaktu);

        // inisiasi spinner lokasi
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item);
        adapter.add("Semua");
        adapter.add("Kepatihan");
        adapter.add("Cawang");
        adapter.add("Rogojampi");
        adapter.add("Kedaleman");
        adapter.add("Puspan");
        adapter.add("Kepatihan");
        adapter.add("Lemahbang");
        adapter.add("Lincing");
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filterLokasi.setAdapter(adapter);

        // Setelah inisiasi spinner, tambahkan listener
        filterLokasi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Ambil nilai yang dipilih dari spinner
                String lokasi = filterLokasi.getSelectedItem().toString();

                // Modifikasi URL_GET_PENAGIHAN dengan menambahkan parameter lokasi
                String url = URL_GET_PENAGIHAN + "?lokasi=" + lokasi;

                // Panggil getDataClient setelah nilai spinner berubah
                getDataClient(url);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing here
            }
        });

        return view;
    }

    public void onItemClick(ModelClientUmum modelClientUmum) {
        // Mulai aktivitas baru dan kirim data menggunakan putExtra
        Intent intent = new Intent(requireContext(), DetailClient.class);
        intent.putExtra("namaClient", modelClientUmum.getMdl_name_client_umum());
        intent.putExtra("fotoClient", modelClientUmum.getMdl_image_client_umum());
        intent.putExtra("idClient", modelClientUmum.getMdl_id_client_umum());
        // Tambahkan data lainnya sesuai kebutuhan
        startActivity(intent);
    }

    private void getDataClient(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(requireContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    showDataMessage();
                    // Tambahkan log untuk melihat respons
                    Log.d("TagihanActivity", "Response: " + response);

                    // Periksa apakah respons dimulai dengan "<br"
                    if (response.startsWith("<br")) {
                        // Tangani pesan kesalahan di sini
                        Log.e("TagihanActivity", "Server error: " + response);
                        // Tampilkan pesan kesalahan atau set listData menjadi kosong
                        listData.clear();
                        showEmptyDataMessage();
                    } else {
                        // Membersihkan listData sebelum menambahkan data baru
                        listData.clear();
                        showDataMessage();

                        // Proses respons JSON
                        JSONObject jsonObject = new JSONObject(response.substring(response.indexOf('{'), response.length()));
                        JSONArray data = jsonObject.getJSONArray("data");

                        Log.d("TagihanActivity", "Number of items in response: " + data.length());

                        if (data.length() == 0) {
                            // Tampilkan pesan "Tidak ada data"
                            showEmptyDataMessage();
                        } else {
                            showDataMessage();
                            for (int i = 0; i < data.length(); i++) {
                                ModelClientUmum modelClientUmum = new ModelClientUmum();
                                JSONObject object = data.getJSONObject(i);

                                modelClientUmum.setMdl_image_client_umum(IMAGES.URL_IMAGES + object.getString("foto_client"));
                                modelClientUmum.setMdl_name_client_umum(object.getString("nama_client"));
                                modelClientUmum.setMdl_id_client_umum(object.getString("id_client"));
                                modelClientUmum.setMdl_harga_paket(object.getInt("harga_pemesanan"));

                                listData.add(modelClientUmum);
                            }

                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false);
                            card_client_umum.setLayoutManager(linearLayoutManager);

                            // Mengirimkan listener ke adapter
                            AdapterClientUmum adapterClientUmum = new AdapterClientUmum(requireContext(), listData, Penagihan.this);
                            card_client_umum.setAdapter(adapterClientUmum);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    // Tambahkan log untuk melihat kesalahan
                    Log.e("TagihanActivity", "Error: " + e.toString());
                    // Tampilkan pesan kesalahan atau set listData menjadi kosong
                    listData.clear();
                    showEmptyDataMessage();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Tangani kesalahan respons jaringan
                Log.e("TagihanActivity", "Error: " + error.toString());
                // Tampilkan pesan kesalahan atau set listData menjadi kosong
                listData.clear();
                showEmptyDataMessage();
            }
        });

        requestQueue.add(stringRequest);
    }

    private void showEmptyDataMessage(){
        card_client_umum.setVisibility(View.GONE);
    }

    private void showDataMessage(){
        card_client_umum.setVisibility(View.VISIBLE);
    }
}
