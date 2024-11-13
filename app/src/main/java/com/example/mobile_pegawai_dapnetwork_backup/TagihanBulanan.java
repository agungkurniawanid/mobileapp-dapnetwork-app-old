package com.example.mobile_pegawai_dapnetwork_backup;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mobile_pegawai_dapnetwork_backup.classes.CONFIG_IP;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

public class TagihanBulanan extends AppCompatActivity {

    EditText tgIdClient, tgHarusDibayar, tgNominalPembayaran,
            tgIdPemesanan, tgId_pembayaran, tgIdPaketLayanan,
            tgKurangBayar, tgKembalian, tgTotalPembayaran, tgStatusPembayaran;
    CheckBox checkBox;
    TextView tgBtnSimpanPembayaran;
    private int tgHarusDibayarkan, tg_fixKembalian, tg_fixKurangBayar, tg_fixTotalPembayaran;
    ProgressDialog progressDialog;
    Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tagihan_bulanan);

        // variable ui
        tgIdClient = findViewById(R.id.tg_id_client);
        tgHarusDibayar = findViewById(R.id.tg_harus_dibayar);
        checkBox = findViewById(R.id.checkbox_bayar_double);
        tgNominalPembayaran = findViewById(R.id.tg_nominal_bayar);
        tgKurangBayar = findViewById(R.id.tg_kurang_bayar);
        tgKembalian = findViewById(R.id.tg_kembalian_bayar);
        tgTotalPembayaran = findViewById(R.id.tg_total_bayar);
        tgStatusPembayaran = findViewById(R.id.tg_status_pembayaran);
        tgBtnSimpanPembayaran = findViewById(R.id.tg_btn_simpan_pembayaran);

        tgIdPemesanan = findViewById(R.id.tg_id_pemesanan);
        tgId_pembayaran = findViewById(R.id.tg_id_pembayaran);
        tgIdPaketLayanan = findViewById(R.id.tg_id_paket_layanan);


        // put extra
        Intent getIdClient = getIntent();
        tgIdClient.setText(getIdClient.getStringExtra("idClient"));

        // fungsi checkbox
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Checkbox di-check
                    tgNominalPembayaran.setText("0");
                    tgNominalPembayaran.setEnabled(false);
                    tgKurangBayar.setText("0");
                    tgKembalian.setText("0");
                    tgTotalPembayaran.setText("0");
                    tgStatusPembayaran.setText("Bayar Double");
                } else {
                    // Checkbox tidak di-check
                    tgNominalPembayaran.setText("");
                    tgNominalPembayaran.setEnabled(true);
                    tgKurangBayar.setText("");
                    tgKembalian.setText("");
                    tgTotalPembayaran.setText("");
                    tgStatusPembayaran.setText("");
                }
            }
        });

        // get total pembayaran
        getTotalHarusDibayar(tgIdClient.getText().toString());

        // pembayaran field otomatis
        tgNominalPembayaran.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String getValue = editable.toString();
                int value = getValue.isEmpty() ? 0 : Integer.parseInt(getValue);

                if (value >= tgHarusDibayarkan) {
                    // kurang bayar
                    tgKurangBayar.setText(formatCurrency(0));
                    tg_fixKurangBayar = 0;

                    // kembalian
                    int kembalian = value - tgHarusDibayarkan;
                    tgKembalian.setText(formatCurrency(kembalian));
                    tg_fixKembalian = kembalian;

                    // total pembayaran
                    tgTotalPembayaran.setText(formatCurrency(tgHarusDibayarkan));
                    tg_fixTotalPembayaran = tgHarusDibayarkan;

                    // status pembayaran
                    tgStatusPembayaran.setText("Lunas");

                } else if (value <= tgHarusDibayarkan) {
                    // kurang bayar
                    int kurang = tgHarusDibayarkan - value;
                    tgKurangBayar.setText(formatCurrency(kurang));
                    tg_fixKurangBayar = kurang;

                    // kembalian
                    tgKembalian.setText(formatCurrency(0));
                    tg_fixKembalian = 0;

                    // total pembayaran
                    tgTotalPembayaran.setText(formatCurrency(value));
                    tg_fixTotalPembayaran = value;

                    // status pembayaran
                    tgStatusPembayaran.setText("Belum Lunas");
                }
            }
        });

        // btn simpan pembayaran
        tgBtnSimpanPembayaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(TagihanBulanan.this);
                progressDialog.setMessage("Menyimpan Pembayaran");
                progressDialog.show();

                String URL_PEMBAYARAN = CONFIG_IP.HTTP + CONFIG_IP.URL_IP + CONFIG_IP.PATH + "tambahdanupdatepmb.php";
                RequestQueue requestQueue = Volley.newRequestQueue(TagihanBulanan.this);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_PEMBAYARAN,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("Server_Response", response);
                                progressDialog.dismiss();
                                try {
                                    JSONObject jsonObject = new JSONObject(response.substring(response.indexOf('{'), response.length()));
                                    int status = jsonObject.getInt("status");
                                    if(status == 200) {
                                        ToastCustomSuccess("Data Pembayaran berhasil disimpan!");
                                        startActivity(new Intent(TagihanBulanan.this, MainApplication.class));
                                    } else if (status == 500) {
                                        ToastCustomWarning("Data pembayaran gagal disimpan! Error: " + jsonObject.getString("error"));
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        ToastCustomWarning("Terjadi kesalahan saat menyimpan pembayaran");
                        error.printStackTrace();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("id_pembayaran", tgId_pembayaran.getText().toString());
                        params.put("id_client", tgIdClient.getText().toString());
                        params.put("id_pemesanan", tgIdPemesanan.getText().toString());
                        params.put("id_paket_layanan", tgIdPaketLayanan.getText().toString());
                        params.put("total_pembayaran", String.valueOf(tg_fixTotalPembayaran));
                        params.put("nominal_pembayaran", tgNominalPembayaran.getText().toString());
                        params.put("kurang_pembayaran", String.valueOf(tg_fixKurangBayar));
                        params.put("kembalian_pembayaran", String.valueOf(tg_fixKembalian));
                        params.put("status_pembayaran", tgStatusPembayaran.getText().toString());
                        return params;
                    }
                };
                requestQueue.add(stringRequest);
            }
        });

    }


    private void getTotalHarusDibayar(String idClient) {
        String URL_GET_TOTAL = CONFIG_IP.HTTP + CONFIG_IP.URL_IP + CONFIG_IP.PATH + "tagihanbulanan.php?id_client=" + idClient;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_GET_TOTAL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("API_RESPONSE", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response.substring(response.indexOf('{'), response.length()));
                            JSONObject jsonData = jsonObject.getJSONObject("data");

                            // set total pembayaran
                            String totalPembayaranString = jsonData.getString("total_pembayaran");
                            String cleanTotalPembayaran = totalPembayaranString.replaceAll("[.,]", "");
                            int totalPembayaran = Integer.parseInt(cleanTotalPembayaran);
                            String formattedTotal = formatCurrency(totalPembayaran);
                            tgHarusDibayar.setText(formattedTotal);
                            tgHarusDibayarkan = totalPembayaran;

                            // set id
                            tgIdPemesanan.setText(jsonData.getString("id_pemesanan"));
                            tgId_pembayaran.setText(jsonData.getString("id_pembayaran"));
                            tgIdPaketLayanan.setText(jsonData.getString("id_paket_layanan"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }
        );
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

    private String formatCurrency(int value) {
        NumberFormat formatter = NumberFormat.getNumberInstance();
        return formatter.format(value);
    }
}