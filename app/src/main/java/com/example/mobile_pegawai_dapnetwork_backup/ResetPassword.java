package com.example.mobile_pegawai_dapnetwork_backup;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mobile_pegawai_dapnetwork_backup.classes.CONFIG_IP;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ResetPassword extends AppCompatActivity {

    String URL_RESET_PASSWORD = CONFIG_IP.HTTP + CONFIG_IP.URL_IP + CONFIG_IP.PATH + "resetpassword.php";
    private ImageView btnkembaliLoginRegister;
    private TextView BTN_OK;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        // todo btn kembali ke frame login register
        btnkembaliLoginRegister = findViewById(R.id.btnkembali_loginregister);
        btnkembaliLoginRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ResetPassword.this, LoginRegister.class));
            }
        });

        // todo fungsi kirim kode ootp
        EditText emailResetPassword = findViewById(R.id.email_reset_password);
        TextView btnKirimOOTP = findViewById(R.id.btn_kirim_otp);
        btnKirimOOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(emailResetPassword.getText().toString().isEmpty()) {
                    showAlertEmailTidakBolehKosong();
                }

                progressDialog = new ProgressDialog(ResetPassword.this);
                progressDialog.setMessage("Mengirim kode OOTP...");
                progressDialog.show();

                btnKirimOOTP.setEnabled(false);
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_RESET_PASSWORD,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                progressDialog.dismiss();
                                try {
                                    JSONObject object = new JSONObject(response.substring(response.indexOf('{'), response.length()));

                                    if (object.has("status") && object.has("message")) {
                                        String status = object.getString("status");
                                        String message = object.getString("message");

                                        if (status.equals("success")) {
                                            showAlertOOTP();
                                        } else {
                                            Toast.makeText(getApplicationContext(), "Error: " + message, Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Invalid JSON format from server", Toast.LENGTH_SHORT).show();
                                        Log.e("Volley Error", "Invalid JSON format from server");
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(getApplicationContext(), "JSON Parsing Error", Toast.LENGTH_SHORT).show();
                                }
                                btnKirimOOTP.setEnabled(true);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        if (error instanceof TimeoutError) {
                            Log.e("Volley Error", "Timeout Error. Coba lagi nanti.", error);
                            Toast.makeText(getApplicationContext(), "Timeout Error. Coba lagi nanti.", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e("Volley Error", "Error: " + error.toString(), error);
                            Toast.makeText(getApplicationContext(), "Error: " + error.toString(), Toast.LENGTH_SHORT).show();
                        }
                        btnKirimOOTP.setEnabled(true);
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("telfon", emailResetPassword.getText().toString()); // Menggunakan parameter 'telfon' sesuai dengan PHP
                        return params;
                    }
                };
                requestQueue.add(stringRequest);

            }
        });
    }

    // todo alert berhasil kirim kode OOTP
    public void showAlertOOTP() {
        View dialogView = LayoutInflater.from(ResetPassword.this).inflate(R.layout.alert_info_otp_berhasil, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(ResetPassword.this);
        builder.setView(dialogView);

        BTN_OK = (TextView) dialogView.findViewById(R.id.btndone_alert_info_otp_berhasil);

        final AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();

        BTN_OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
                startActivity(new Intent(ResetPassword.this, EnterOOTP.class));
                finish();
            }
        });
    }

    // todo show alert email tidak boleh kosong
    public void showAlertEmailTidakBolehKosong() {
        View dialogView = LayoutInflater.from(ResetPassword.this).inflate(R.layout.alert_info_lp_email_tidak_boleh_kosong, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(ResetPassword.this);
        builder.setView(dialogView);

        BTN_OK = (TextView) dialogView.findViewById(R.id.btndone_alert_lp_email_tidak_boleh_kosong);

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
    public void showAlertEmailTidakTerdaftar() {
        View dialogView = LayoutInflater.from(ResetPassword.this).inflate(R.layout.alert_info_lp_email_tidak_terdaftar, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(ResetPassword.this);
        builder.setView(dialogView);

        BTN_OK = (TextView) dialogView.findViewById(R.id.btndone_alert_lp_email_tidak_terdaftar);

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