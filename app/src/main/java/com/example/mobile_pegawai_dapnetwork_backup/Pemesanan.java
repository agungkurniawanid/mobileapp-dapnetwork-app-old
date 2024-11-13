package com.example.mobile_pegawai_dapnetwork_backup;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mobile_pegawai_dapnetwork_backup.adapter.AdapterLayananPaket;
import com.example.mobile_pegawai_dapnetwork_backup.adapter.AdapterPeraturanPegawai;
import com.example.mobile_pegawai_dapnetwork_backup.classes.CONFIG_IP;
import com.example.mobile_pegawai_dapnetwork_backup.model.ModelPaketLayanan;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Pemesanan extends AppCompatActivity {
    String URL_GET_PAKET_LAYANAN = CONFIG_IP.HTTP + CONFIG_IP.URL_IP + CONFIG_IP.PATH + "getpaket.php";
    String URL_POST_PEMESANAN = CONFIG_IP.HTTP + CONFIG_IP.URL_IP + CONFIG_IP.PATH + "tambahpemesanan.php";
    RecyclerView paket_layanan;
    ArrayAdapter<String> adapter_paket;
    ProgressDialog progressDialog;
    Toast toast;
    List<ModelPaketLayanan> listData = new ArrayList<>();
    EditText clientID, idid, norek, pesanClient;
    Spinner bank, paket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pemesanan);

        // todo variable komponen ui
        clientID = findViewById(R.id.clientID);
        idid = findViewById(R.id.idid);
        bank = findViewById(R.id.bank_pemesanan);
        paket = findViewById(R.id.jenis_paket);
        norek = findViewById(R.id.nomor_rekening);
        pesanClient = findViewById(R.id.pesan_client);
        TextView btnSimpanPemesanan = findViewById(R.id.btn_simpan_pemesanan);


        // todo set nama pemesan
        EditText pemesan = findViewById(R.id.nama_pemesan);
        Intent getNamaPemesanan = getIntent();
        pemesan.setText(getNamaPemesanan.getStringExtra("nama_client"));

        // todo fungsi btn kembali
        ImageView btnKembali = findViewById(R.id.btn_kembali_pemesanan);
        btnKembali.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(Pemesanan.this);
            builder.setTitle("Konfirmasi Tidak Lanjut");
            builder.setMessage("Apakah Anda yakin>>>>>>> tidak lanjut?");

            // Tombol Oke
            builder.setPositiveButton("Iya", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Pemesanan.this, MainApplication.class);
                    startActivity(intent);
                    finish();
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
        });

        // todo set id client
        Intent getIDClient = getIntent();
        clientID.setText(getIDClient.getStringExtra("id_client"));

        // todo menampilkan card rcycle paket layanan
        paket_layanan = findViewById(R.id.recycle_paket_pesan);
        setCardPaket();

        // todo untuk spinnet bank dan spinner jenis paket
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        adapter.add("BCA");
        adapter.add("MANDIRI");
        adapter.add("BRI");
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bank.setAdapter(adapter);

        adapter_paket = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        getDataPaket();
        adapter_paket.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        paket.setAdapter(adapter_paket);

        paket.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Mendapatkan item yang dipilih dari Spinner
                String selectedPaket = (String) parentView.getItemAtPosition(position);

                // Memisahkan ID dan jenis paket dari string
                String[] parts = selectedPaket.split(" - ");
                String idPaket = parts[0];

                // Menempatkan ID paket ke dalam EditText
                idid.setText(idPaket);

                // Lakukan apa pun yang perlu dilakukan dengan ID paket di sini
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Handle event ketika tidak ada item yang dipilih
            }
        });

        // todo fungsi untuk insert pemesanan
        btnSimpanPemesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(Pemesanan.this);
                progressDialog.setMessage("Menyimpan Pemesanan");
                progressDialog.show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (bank.getSelectedItem().toString().equals("BCA")) {
                            if (norek.getText().toString().trim().length() > 10 || norek.getText().toString().trim().length() < 10) {
                                ToastCustomWarning("Nomor rekening BCA harus 10 angka / digit");
                                progressDialog.dismiss();
                            } else {
                                simpanPemesanan();
                                progressDialog.dismiss();
                            }
                        } else if (bank.getSelectedItem().toString().equals("MANDIRI")) {
                            if (norek.getText().toString().trim().length() > 16 || norek.getText().toString().trim().length() < 16) {
                                ToastCustomWarning("Nomor rekening MANDIRI harus 16 angka / digit");
                                progressDialog.dismiss();
                            } else {
                                simpanPemesanan();
                                progressDialog.dismiss();
                            }
                        } else if (bank.getSelectedItem().toString().equals("BRI")) {
                            if (norek.getText().toString().trim().length() > 15 || norek.getText().toString().trim().length() < 15) {
                                ToastCustomWarning("Nomor rekening BRI harus 15 angka / digit");
                                progressDialog.dismiss();
                            } else {
                                simpanPemesanan();
                                progressDialog.dismiss();
                            }
                        }
                    }
                }, 1500);

            }
        });
    }

    private void simpanPemesanan() {
        RequestQueue requestQueue = Volley.newRequestQueue(Pemesanan.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_POST_PEMESANAN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response.substring(response.indexOf('{'), response.length()));
                    String status = jsonObject.getString("status");
                    String message = jsonObject.getString("message");
                    String idPemesanan = jsonObject.optString("id_pemesanan", "");
                    String idClient = jsonObject.optString("id_client", "");
                    String idPaketLayanan = jsonObject.optString("id_paket_layanan", "");
                    int hargaPaket = jsonObject.optInt("total_harga", 0);

                    if (status.equals("200")) {
                        ToastCustomSuccess("Berhasil Menyimpan Pemesanan!");
                        Intent intent = new Intent(Pemesanan.this, Pembayaran.class);
                        intent.putExtra("id_pemesanan", idPemesanan);
                        intent.putExtra("id_client", idClient);
                        intent.putExtra("id_paket_layanan", idPaketLayanan);
                        intent.putExtra("harga_paket", hargaPaket);
                        startActivity(intent);
                    } else if (status.equals("400")) {
                        ToastCustomWarning(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                ToastCustomWarning(error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id_client", clientID.getText().toString());
                params.put("nama_bank", bank.getSelectedItem().toString());
                params.put("rekening", norek.getText().toString());
                params.put("catatan_client", pesanClient.getText().toString());
                params.put("id_paket_layanan", idid.getText().toString());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    // todo gata
    private void getDataPaket() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_GET_PAKET_LAYANAN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.substring(response.indexOf('{'), response.length()));
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String paket = object.getString("jenis_paket_layanan");
                        String idpaket = object.getString("id_paket_layanan");
                        adapter_paket.add(idpaket + " - " + paket);
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

    // todo untuk set card paket
    private void setCardPaket() {
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

                        if (i % 2 == 0) {
                            modelPaketLayanan.setBackgroundPaket(R.drawable.background_paket_blue);
                        } else {
                            modelPaketLayanan.setBackgroundPaket(R.drawable.background_paket_orange);
                        }
                        modelPaketLayanan.setHargaPaket("Rp. " + object.getString("harga"));
                        modelPaketLayanan.setKecepatanPaket(object.getString("jenis_paket_layanan"));

                        listData.add(modelPaketLayanan);
                    }
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Pemesanan.this, LinearLayoutManager.HORIZONTAL, false);
                    paket_layanan.setLayoutManager(linearLayoutManager);
                    AdapterLayananPaket adapter = new AdapterLayananPaket(Pemesanan.this, listData);
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

    private void ToastCustomWarning(String message) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast, findViewById(R.id.tast));
        ImageView toastIcon = layout.findViewById(R.id.toast_icon);
        TextView toastText = layout.findViewById(R.id.toast_text);
        toastText.setText(message);
        toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.setGravity(Gravity.TOP, 0, 100);
        toast.show();
    }

    private void ToastCustomSuccess(String message) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_success, findViewById(R.id.tastSuccess));
        ImageView toastIcon = layout.findViewById(R.id.toas_icon_suc);
        TextView toastText = layout.findViewById(R.id.toast_text_success);
        toastText.setText(message);
        toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.setGravity(Gravity.TOP, 0, 100);
        toast.show();
    }
}