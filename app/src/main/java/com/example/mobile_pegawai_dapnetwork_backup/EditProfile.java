package com.example.mobile_pegawai_dapnetwork_backup;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
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
import com.bumptech.glide.Glide;
import com.example.mobile_pegawai_dapnetwork_backup.classes.CONFIG_IP;
import com.example.mobile_pegawai_dapnetwork_backup.classes.IMAGES;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class EditProfile extends AppCompatActivity {

    ImageView fotoProfileEp, saveEditProfile;
    EditText namaAwalEp, namaAkhirEp,
            nikEp, nohpEp, alamatEp, emailEp;
    Bitmap bitmap;
    String base64;
    String foto_url;
    Toast toast;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        // variable ui
        fotoProfileEp = findViewById(R.id.foto_profile_ep);
        EditText idPegawaiEp = findViewById(R.id.id_pegawai_ep);
        namaAwalEp = findViewById(R.id.namaawal_ep);
        namaAkhirEp = findViewById(R.id.namaakhir_ep);
        nikEp = findViewById(R.id.nik_ep);
        nohpEp = findViewById(R.id.nomor_ep);
        alamatEp = findViewById(R.id.alamat_ep);
        emailEp = findViewById(R.id.email_ep);
        saveEditProfile = findViewById(R.id.save_edit_profile);

        // fungsi upload image
        ActivityResultLauncher<Intent> activityResultLauncher =
                registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            Uri uri = data.getData();
                            try {
                                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                                fotoProfileEp.setImageBitmap(bitmap);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                });

        fotoProfileEp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                activityResultLauncher.launch(intent);
            }
        });

        // get id pegawai
        String idPegawai = getIntent().getStringExtra("id_pegawai");
        idPegawaiEp.setText(idPegawai);

        // function getDataPegawai
        getDataPegawai(idPegawai);

        saveEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(EditProfile.this);
                progressDialog.setMessage("Menyimpan Perubahan Profile...");
                progressDialog.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Pastikan foto_url sudah terinisialisasi sebelum memanggil getParams
                        if (foto_url != null) {
                            ByteArrayOutputStream byteArrayOutputStream;
                            byteArrayOutputStream = new ByteArrayOutputStream();

                            // Pengecekan null sebelum mengompres bitmap
                            if (bitmap != null) {
                                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                                byte[] bytes = byteArrayOutputStream.toByteArray();
                                base64 = Base64.encodeToString(bytes, Base64.DEFAULT);
                            }

                            String URL = CONFIG_IP.HTTP + CONFIG_IP.URL_IP + CONFIG_IP.PATH + "editprofile.php?id=" + idPegawai;
                            RequestQueue requestQueue = Volley.newRequestQueue(EditProfile.this);
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    progressDialog.dismiss();
                                    Log.d("EditProfile", "Response: " + response);
                                    try {
                                        JSONObject jsonObject = new JSONObject(response.substring(response.indexOf('{'), response.length()));
                                        String status = jsonObject.getString("status");
                                        if (status.equals("success")) {
                                            SharedPreferences preferences = getSharedPreferences("user", MODE_PRIVATE);
                                            SharedPreferences.Editor editor = preferences.edit();
                                            editor.putString("foto_pegawai", foto_url);
                                            editor.apply();
                                            Log.d("ProfilePegawai", "Foto Pegawai diupdate: " + foto_url);
                                            Log.d("EditProfile", "Foto URL diunggah: " + foto_url);
                                            startActivity(new Intent(EditProfile.this, ProfilePegawai.class));
                                            ToastCustomSuccess("Perubahan berhasil disimpan!");
                                            finish();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    progressDialog.dismiss();
                                    Log.e("EditProfile", "Error: " + error.toString());
                                    error.printStackTrace();
                                }
                            }) {
                                @Override
                                protected Map<String, String> getParams() {
                                    Map<String, String> params = new HashMap<>();
                                    params.put("namaawal", namaAwalEp.getText().toString().trim());
                                    params.put("namaakhir", namaAkhirEp.getText().toString().trim());
                                    params.put("nik", nikEp.getText().toString().trim());
                                    params.put("nomor", nohpEp.getText().toString().trim());
                                    params.put("alamat", alamatEp.getText().toString().trim());
                                    params.put("email", emailEp.getText().toString().trim());

                                    // Hanya gunakan base64 jika bitmap tidak null
                                    if (bitmap != null) {
                                        params.put("foto_pegawai", base64);
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

    private void getDataPegawai(String idpegawai) {
        String URL = CONFIG_IP.HTTP + CONFIG_IP.URL_IP + CONFIG_IP.PATH + "getdatapegawai.php?id=" + idpegawai;
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
                        foto_url = IMAGES.URL_IMAGES + dataPegawai.getString("foto_pegawai");
                        Glide.with(EditProfile.this).load(foto_url).into(fotoProfileEp);
                        namaAwalEp.setText(dataPegawai.getString("namaawal"));
                        namaAkhirEp.setText(dataPegawai.getString("namaakhir"));
                        nikEp.setText(dataPegawai.getString("nik"));
                        nohpEp.setText(dataPegawai.getString("nomor_telepon"));
                        alamatEp.setText(dataPegawai.getString("alamat"));
                        emailEp.setText(dataPegawai.getString("email"));
                    } else if (status.equals("error")) {
                        Toast.makeText(EditProfile.this, "Gagal", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
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