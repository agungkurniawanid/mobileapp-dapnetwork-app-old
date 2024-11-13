package com.example.mobile_pegawai_dapnetwork_backup;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
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

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class Pembayaran extends AppCompatActivity {

    Toast toast;
    ProgressDialog progressDialog;
    int fixKembalian;
    int fixKurangBayar;
    int fixTotalPembayaran;
    String URL_POST_PEMBAYARAN = CONFIG_IP.HTTP + CONFIG_IP.URL_IP + CONFIG_IP.PATH + "pembayaran.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembayaran);

        // variable UI
        EditText harusDibayar  = findViewById(R.id.harus_dibayar);
        EditText idpemesanan = findViewById(R.id.idpemesanan_byr);
        EditText idclient = findViewById(R.id.idclient_byr);
        EditText idpaket = findViewById(R.id.idpaket_byr);
        EditText nominalBayar = findViewById(R.id.nominal_bayar);
        EditText kurangBayar = findViewById(R.id.kurang_bayar);
        EditText kembalianBayar = findViewById(R.id.kembalian_bayar);
        EditText totalPembayaran = findViewById(R.id.total_bayar);
        EditText statusPembayaran = findViewById(R.id.status_pembayaran);
        TextView btnSimpanPembayaran = findViewById(R.id.btn_simpan_pembayaran);
        ImageView btnKembali = findViewById(R.id.btn_kembali_pembayaran);

        // todo btn kembali
        btnKembali.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(Pembayaran.this);
            builder.setTitle("Konfirmasi Tidak Lanjut");
            builder.setMessage("Apakah Anda yakin tidak lanjut?");

            // Tombol Oke
            builder.setPositiveButton("Oke", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Aksi jika tombol Oke diklik (contoh: jalankan fungsi logout)
                    Intent intent = new Intent(Pembayaran.this, MainApplication.class);
                    startActivity(intent);
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

        // todo get id client
        Intent getIdClient = getIntent();
        String id_client = getIdClient.getStringExtra("id_client");
        idclient.setText(id_client);

        // todo get id pemesanan
        Intent getIdPemesanan = getIntent();
        String id_pemesanan = getIdPemesanan.getStringExtra("id_pemesanan");
        idpemesanan.setText(id_pemesanan);

        // todo get id paket
        Intent getIdPaket = getIntent();
        String id_paket = getIdPaket.getStringExtra("id_paket_layanan");
        idpaket.setText(id_paket);

        // todo get harga paket
        Intent hargaPaket = getIntent();
        int harga_paket = hargaPaket.getIntExtra("harga_paket", 0);

        // todo harus dibayar
        int total = harga_paket;
        harusDibayar.setText(formatCurrency(total));

        nominalBayar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable editable) {
                String getValue = editable.toString();
                int value = getValue.isEmpty() ? 0 : Integer.parseInt(getValue);

                if(value >= total) {
                    // kurang bayar
                    kurangBayar.setText(formatCurrency(0));
                    fixKurangBayar = 0;

                    // kembalian
                    int kembalian = value - total;
                    kembalianBayar.setText(formatCurrency(kembalian));
                    fixKembalian= kembalian;

                    // total pembayaran
                    totalPembayaran.setText(formatCurrency(total));
                    fixTotalPembayaran = total;

                    // status pembayaran
                    statusPembayaran.setText("Lunas");

                } else if (value <= total) {
                    // kurang bayar
                    int kurang = total - value;
                    kurangBayar.setText(formatCurrency(kurang));
                    fixKurangBayar= kurang;

                    // kembalian
                    kembalianBayar.setText(formatCurrency(0));
                    fixKembalian= 0;

                    // total pembayaran
                    totalPembayaran.setText(formatCurrency(value));
                    fixTotalPembayaran = value;

                    // status pembayaran
                    statusPembayaran.setText("Belum Lunas");
                }
            }
        });

        // todo insert pembayaran
        btnSimpanPembayaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = new ProgressDialog(Pembayaran.this);
                progressDialog.setMessage("Menyimpan Pemesanan");
                progressDialog.show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        RequestQueue requestQueue = Volley.newRequestQueue(Pembayaran.this);
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_POST_PEMBAYARAN, new Response.Listener<String>(){
                            @Override
                            public void onResponse(String response) {
                                progressDialog.dismiss();
                                try {
                                    JSONObject jsonObject = new JSONObject(response.substring(response.indexOf('{'), response.length()));
                                    int status = jsonObject.getInt("status");
                                    String message = jsonObject.getString("message");
                                    if(status == 200) {
                                        if(message.equals("Data pembayaran berhasil ditambahkan!")) {
                                           ToastCustomSuccess(message);
                                           startActivity(new Intent(Pembayaran.this, MainApplication.class));
                                        }
                                    } else if (status == 400) {
                                        if(message.equals("Data pembayaran gagal ditambahkan!")) {
                                            ToastCustomWarning(message);
                                        }
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressDialog.dismiss();
                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() {
                                Map<String, String> params = new HashMap<>();
                                params.put("id_client", idclient.getText().toString());
                                params.put("id_pemesanan", idpemesanan.getText().toString());
                                params.put("id_paket_layanan", idpaket.getText().toString());
                                params.put("total_pembayaran", String.valueOf(fixTotalPembayaran));
                                params.put("nominal_pembayaran", nominalBayar.getText().toString());
                                params.put("kurang_pembayaran", String.valueOf(fixKurangBayar));
                                params.put("kembalian_pembayaran", String.valueOf(fixKembalian));
                                params.put("status_pembayaran", statusPembayaran.getText().toString());
                                return params;
                            }
                        };
                        requestQueue.add(stringRequest);
                    }
                }, 1500);
            }
        });
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

    // todo format mata uang
    private String formatCurrency(int value) {
        DecimalFormat currencyFormat = new DecimalFormat("###,###,###");
        return currencyFormat.format(value);
    }
}