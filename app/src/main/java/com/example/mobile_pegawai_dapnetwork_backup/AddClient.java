package com.example.mobile_pegawai_dapnetwork_backup;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mobile_pegawai_dapnetwork_backup.classes.CONFIG_IP;
import com.example.mobile_pegawai_dapnetwork_backup.fragment.Transaction;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AddClient extends AppCompatActivity {

    ProgressDialog progressDialog;
    Bitmap bitmap;
    Toast toast;
    String base64;
    String URL_TAMBAH_CLIENT = CONFIG_IP.HTTP + CONFIG_IP.URL_IP + CONFIG_IP.PATH + "tambahclient.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_client);

        // todo variable component ui xml
        EditText namaClient = findViewById(R.id.nama_lengkap);
        Spinner addClientSpinner = findViewById(R.id.addclient_spinner);
        EditText alamat = findViewById(R.id.alamat);
        EditText nomorTelepon = findViewById(R.id.nomor_telepon);
        EditText email = findViewById(R.id.email);
        ImageView fotoClient = findViewById(R.id.foto_client);
        TextView btnSimpanClient = findViewById(R.id.btn_simpan_client);

        // todo button back
        ImageView btnBack = findViewById(R.id.btn_back_addclient);
        btnBack.setOnClickListener(v -> {
            startActivity(new Intent(AddClient.this, MainApplication.class));
        });

        // todo spinner untuk jenis kelamin
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        adapter.add("Laki-Laki");
        adapter.add("Perempuan");
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        addClientSpinner.setAdapter(adapter);

        // todo fungsi replace img
        ActivityResultLauncher<Intent> activityResultLauncher =
                registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            Uri uri = data.getData();
                            try {
                                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                                fotoClient.setImageBitmap(bitmap);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                });

        fotoClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                activityResultLauncher.launch(intent);
            }
        });

        // todo simpan client fungsi ke database
        btnSimpanClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(AddClient.this);
                progressDialog.setMessage("Menyimpan Data");
                progressDialog.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (namaClient.getText().toString().isEmpty()) {
                            progressDialog.dismiss();
                            ToastCustomWarning("Nama lengkap kosong! harap masukkan nama lengkap pelanggan!");
                        } else if (alamat.getText().toString().isEmpty()) {
                            progressDialog.dismiss();
                            ToastCustomWarning("Alamat kosong! harap masukkan alamat pelanggan!");
                        } else if (nomorTelepon.getText().toString().isEmpty()) {
                            progressDialog.dismiss();
                            ToastCustomWarning("Nomor telepon kosong! harap masukkan nomor telepon pelanggan!");
                        } else if (nomorTelepon.getText().toString().length() < 11 || nomorTelepon.getText().toString().length() > 13) {
                            progressDialog.dismiss();
                            ToastCustomWarning("Nomor telepon kurang dari 11 atau lebih dari 13! Harap masukkan nomor yang valid!");
                        } else {

                            String rawPhoneNumber = nomorTelepon.getText().toString().trim();
                            final String phoneNumber;

                            if (rawPhoneNumber.matches("^0.*")) {
                                phoneNumber = "62" + rawPhoneNumber.substring(1);
                            } else {
                                phoneNumber = rawPhoneNumber.startsWith("62") ? rawPhoneNumber : "62" + rawPhoneNumber;
                            }

                            nomorTelepon.setText(phoneNumber);

                            ByteArrayOutputStream byteArrayOutputStream;
                            byteArrayOutputStream = new ByteArrayOutputStream();

                            // Pengecekan null sebelum mengompres bitmap
                            if (bitmap != null) {
                                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                                byte[] bytes = byteArrayOutputStream.toByteArray();
                                base64 = Base64.encodeToString(bytes, Base64.DEFAULT);
                            }

                            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_TAMBAH_CLIENT,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            progressDialog.dismiss();
                                            Log.d("JSON_RESPONSE", response);
                                            try {
                                                JSONObject jsonObject = new JSONObject(response.substring(response.indexOf('{'), response.length()));
                                                String id = jsonObject.getString("id_client");
                                                String nama = jsonObject.getString("nama_client");
                                                ToastCustomSuccess("Data Berhasil Ditambahkan! Silahkan lanjutkan pemesanan pelanggan!");
                                                Intent intent = new Intent(AddClient.this, Pemesanan.class);
                                                intent.putExtra("nama_client", nama);
                                                intent.putExtra("id_client", id);
                                                startActivity(intent);
                                                finish();
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    progressDialog.dismiss();
                                    if (error instanceof TimeoutError) {
                                        Toast.makeText(getApplicationContext(), "Timeout Error. Coba lagi nanti.", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Error: " + error.toString(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }) {
                                @Override
                                protected Map<String, String> getParams() {
                                    Map<String, String> params = new HashMap<>();
                                    params.put("nama_lengkap", namaClient.getText().toString());
                                    params.put("jenis_kelamin", addClientSpinner.getSelectedItem().toString());
                                    params.put("alamat", alamat.getText().toString());
                                    params.put("nomor_telepon", phoneNumber);
                                    params.put("email", email.getText().toString());
                                    // Hanya gunakan base64 jika bitmap tidak null
                                    if (bitmap != null) {
                                        params.put("foto", base64);
                                    }
                                    return params;
                                }
                            };
                            requestQueue.add(stringRequest);
                        }
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
    private void ReplaceFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.assets_frame_layout, fragment)
                .commit();
    }
}