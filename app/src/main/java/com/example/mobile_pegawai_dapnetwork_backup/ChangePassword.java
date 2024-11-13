package com.example.mobile_pegawai_dapnetwork_backup;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ChangePassword extends AppCompatActivity {

    String URL_CHANGE_PASS = CONFIG_IP.HTTP + CONFIG_IP.URL_IP + CONFIG_IP.PATH + "changepass.php";
    private ProgressDialog progressDialog;
    private TextView BTN_OK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        // todo untuk kembali ke halaman change pass
        ImageView btnKembaliOOTP = findViewById(R.id.btnkembali_changepass);
        btnKembaliOOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChangePassword.this, LoginRegister.class);
                startActivity(intent);
                finish();
            }
        });

        // todo ambil kode ootp dari enter ootp
        EditText takeOOTP = findViewById(R.id.putOOTP);
        Intent getOOTP = getIntent();
        takeOOTP.setText(getOOTP.getStringExtra("OTP_PUT"));

        // todo fungsi reset pass
        EditText aturSandi = findViewById(R.id.atur_sandi);
        EditText konfirmSandi = findViewById(R.id.konfirm_sandi);
        TextView btnSubmitPass = findViewById(R.id.btn_submit_new_pass);
        btnSubmitPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(ChangePassword.this);
                progressDialog.setMessage("Mengganti Password...");
                progressDialog.show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_CHANGE_PASS,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        progressDialog.dismiss();
                                        try {
                                            JSONObject jsonObject = new JSONObject(response.substring(response.indexOf('{'), response.length()));
                                            String status = jsonObject.getString("status");
                                            String message = jsonObject.getString("message");
                                            if (status.equals("success")) {
                                                alertSuccessChangePass();
                                            } else if (status.equals("failed")) {
                                                if (message.equals("Konfirmasi password tidak boleh kosong.")) {
                                                    alertConfirmKosong();
                                                } else if (message.equals("Password tidak sama.")) {
                                                    alertConfirmPassTidakSama();
                                                } else if (message.equals("Password kurang dari 8 karakter.")) {
                                                    alertConfirmPassKurangDari8();
                                                } else if (message.equals("Password lebih dari 20 karakter.")) {
                                                    alertConfirmPassLebihDari20();
                                                } else {
                                                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        } catch (Exception e) {
                                            Toast.makeText(ChangePassword.this, "Gagal hapus", Toast.LENGTH_SHORT).show();
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
                                params.put("changepass", aturSandi.getText().toString());
                                params.put("confirmpass", konfirmSandi.getText().toString());
                                params.put("ootp", takeOOTP.getText().toString());
                                return params;
                            }
                        };
                        requestQueue.add(stringRequest);
                    }
                }, 1500);
            }
        });
    }

    private void alertSuccessChangePass() {
        View dialogView = LayoutInflater.from(ChangePassword.this).inflate(R.layout.alert_info_cp_success, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(ChangePassword.this);
        builder.setView(dialogView);

        BTN_OK = (TextView) dialogView.findViewById(R.id.btndone_alert_cp_success);

        final AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();

        BTN_OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
                startActivity(new Intent(ChangePassword.this, LoginRegister.class));
                finish();
            }
        });
    }

    private void alertConfirmKosong() {
        View dialogView = LayoutInflater.from(ChangePassword.this).inflate(R.layout.alert_info_cp_confirmkosong, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(ChangePassword.this);
        builder.setView(dialogView);

        BTN_OK = (TextView) dialogView.findViewById(R.id.btndone_alert_cp_confirmkosong);

        final AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();

        BTN_OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
            }
        });
    }

    private void alertConfirmPassTidakSama() {
        View dialogView = LayoutInflater.from(ChangePassword.this).inflate(R.layout.alert_info_cp_confirmtidaksama, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(ChangePassword.this);
        builder.setView(dialogView);

        BTN_OK = (TextView) dialogView.findViewById(R.id.btndone_alert_cp_confirmtidaksama);

        final AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();

        BTN_OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
            }
        });
    }

    private void alertConfirmPassKurangDari8() {
        View dialogView = LayoutInflater.from(ChangePassword.this).inflate(R.layout.alert_info_cp_confirmkurang8, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(ChangePassword.this);
        builder.setView(dialogView);

        BTN_OK = (TextView) dialogView.findViewById(R.id.btndone_alert_cp_confirmkurang8);

        final AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();

        BTN_OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
            }
        });
    }
    private void alertConfirmPassLebihDari20() {
        View dialogView = LayoutInflater.from(ChangePassword.this).inflate(R.layout.alert_info_cp_confirmlebih20, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(ChangePassword.this);
        builder.setView(dialogView);

        BTN_OK = (TextView) dialogView.findViewById(R.id.btndone_alert_cp_confirmlebih20);

        final AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();

        BTN_OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
            }
        });
    }

}