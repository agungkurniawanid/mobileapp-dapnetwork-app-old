package com.example.mobile_pegawai_dapnetwork_backup;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
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

public class EnterOOTP extends AppCompatActivity {

    EditText in1, in2, in3, in4, in5, in6;
    TextView BTN_OK;
    private ProgressDialog progressDialog;
    String URL_OOTP = CONFIG_IP.HTTP + CONFIG_IP.URL_IP + CONFIG_IP.PATH + "cekootp.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_ootp);

        // todo untuk kembali ke halaman lupa password
        ImageView btnKembaliOOTP = findViewById(R.id.btnkembali_ootp);
        btnKembaliOOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EnterOOTP.this, LoginRegister.class);
                startActivity(intent);
                finish();
            }
        });

        // todo untuk variable yang digunakan input OOTP
        in1 = findViewById(R.id.inOTP1);
        in2 = findViewById(R.id.inOTP2);
        in3 = findViewById(R.id.inOTP3);
        in4 = findViewById(R.id.inOTP4);
        in5 = findViewById(R.id.inOTP5);
        in6 = findViewById(R.id.inOTP6);
        setupEditTextListeners();

        // todo fungsi untuk input kode ootp
        TextView btnCekOOTP = findViewById(R.id.btn_cek_ootp);
        btnCekOOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String kodeotp = valueOOTP();
                if (kodeotp.isEmpty()) {
                    showAlertEoHarusTerisi();
                    return;
                }

                progressDialog = new ProgressDialog(EnterOOTP.this);
                progressDialog.setMessage("Verivikasiasi OOTP...");
                progressDialog.show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_OOTP,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        progressDialog.dismiss();
                                        try {
                                            JSONObject jsonObject = new JSONObject(response.substring(response.indexOf("{"), response.length()));
                                            String status = jsonObject.getString("status");
                                            String otopPut = jsonObject.getString("ootp");
                                            if (status.equals("success")) {
                                                showAlertEoValid();
                                            }
                                        } catch (Exception e) {
                                            showAlertEoTidakValid();
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
                                params.put("ootp", kodeotp);
                                return params;
                            }
                        };
                        requestQueue.add(stringRequest);
                    }
                }, 1500);
            }
        });
    }

    public void showAlertEoTidakValid() {
        View dialogView = LayoutInflater.from(EnterOOTP.this).inflate(R.layout.alert_info_eo_otp_tidakterdaftar, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(EnterOOTP.this);
        builder.setView(dialogView);

        BTN_OK = (TextView) dialogView.findViewById(R.id.btndone_alert_eo_tidakterdaftar);

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

    public void showAlertEoValid() {
        View dialogView = LayoutInflater.from(EnterOOTP.this).inflate(R.layout.alert_info_eo_otp_valid, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(EnterOOTP.this);
        builder.setView(dialogView);

        BTN_OK = (TextView) dialogView.findViewById(R.id.btndone_alert_eo_valid);

        final AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();

        BTN_OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
                Intent intent = new Intent(EnterOOTP.this, ChangePassword.class);
                intent.putExtra("OTP_PUT", valueOOTP());
                startActivity(intent);
                finish();
            }
        });
    }

    public void showAlertEoHarusTerisi() {
        View dialogView = LayoutInflater.from(EnterOOTP.this).inflate(R.layout.alert_info_eo_otp_harus_terisi, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(EnterOOTP.this);
        builder.setView(dialogView);

        BTN_OK = (TextView) dialogView.findViewById(R.id.btndone_alert_eo_harusterisi);

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

    private String valueOOTP() {
        String OOTP = in1.getText().toString() + in2.getText().toString() + in3.getText().toString() + in4.getText().toString() + in5.getText().toString() + in6.getText().toString();
        return OOTP;
    }

    private void setupEditTextListeners() {
        in1.addTextChangedListener(new EnterOOTP.GenericTextWatcher(in1, null, in2));
        in2.addTextChangedListener(new EnterOOTP.GenericTextWatcher(in2, in1, in3));
        in3.addTextChangedListener(new EnterOOTP.GenericTextWatcher(in3, in2, in4));
        in4.addTextChangedListener(new EnterOOTP.GenericTextWatcher(in4, in3, in5));
        in5.addTextChangedListener(new EnterOOTP.GenericTextWatcher(in5, in4, in6));
        in6.addTextChangedListener(new EnterOOTP.GenericTextWatcher(in6, in5, null)); // Set nextEditText to null for the last EditText

        in1.setOnKeyListener(new EnterOOTP.GenericKeyListener(in1, null, in2));
        in2.setOnKeyListener(new EnterOOTP.GenericKeyListener(in2, in1, in3));
        in3.setOnKeyListener(new EnterOOTP.GenericKeyListener(in3, in2, in4));
        in4.setOnKeyListener(new EnterOOTP.GenericKeyListener(in4, in3, in5));
        in5.setOnKeyListener(new EnterOOTP.GenericKeyListener(in5, in4, in6));
        in6.setOnKeyListener(new EnterOOTP.GenericKeyListener(in6, in5, null)); // Set nextEditText to null for the last EditText
    }

    private class GenericTextWatcher implements TextWatcher {
        private final EditText currentEditText;
        private final EditText previousEditText;
        private final EditText nextEditText;

        public GenericTextWatcher(EditText currentEditText, EditText previousEditText, EditText nextEditText) {
            this.currentEditText = currentEditText;
            this.previousEditText = previousEditText;
            this.nextEditText = nextEditText;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            // Do nothing
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            // Do nothing
        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (editable.length() == 1 && currentEditText != null && nextEditText != null) {
                // If a digit is entered, move focus to the next EditText
                focusOnNextEditText();
            } else if (editable.length() == 0 && previousEditText != null) {
                // If the current EditText is empty, move focus to the previous EditText and delete from right to left
                focusOnPreviousEditText();
            }
        }

        private void focusOnNextEditText() {
            nextEditText.requestFocus();
        }

        private void focusOnPreviousEditText() {
            if (previousEditText != null) {
                previousEditText.requestFocus();
                previousEditText.setSelection(previousEditText.getText().length());
            }
        }
    }

    private class GenericKeyListener implements View.OnKeyListener {
        private final EditText currentEditText;
        private final EditText previousEditText;
        private final EditText nextEditText;

        public GenericKeyListener(EditText currentEditText, EditText previousEditText, EditText nextEditText) {
            this.currentEditText = currentEditText;
            this.previousEditText = previousEditText;
            this.nextEditText = nextEditText;
        }

        @Override
        public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
            if (keyCode == KeyEvent.KEYCODE_DEL && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                // Handle backspace key press
                if (currentEditText.getText().length() == 0 && previousEditText != null) {
                    // If the current EditText is empty, move focus to the previous EditText and delete from right to left
                    focusOnPreviousEditText();
                }
            }
            return false;
        }

        private void focusOnPreviousEditText() {
            if (previousEditText != null) {
                previousEditText.requestFocus();
                previousEditText.setSelection(previousEditText.getText().length());
            }
        }
    }
}