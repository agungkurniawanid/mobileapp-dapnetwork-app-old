package com.example.mobile_pegawai_dapnetwork_backup;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.transition.TransitionManager;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
import com.example.mobile_pegawai_dapnetwork_backup.classes.IMAGES;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class LoginRegister extends AppCompatActivity {

    private TextView BTN_OK, BTN_OK_REG;
    private CardView loginCardView, registerCardView;
    private Button loginButton, registerButton, buttonRegisterApi, btnupload, btnLogin;
    private RelativeLayout relativeLayout;
    private TextView loginText, registerText, forgotPassword;
    private ImageView close, closeRegister, fotoPegawaiUploaded;
    private View hover;
    private boolean isCardExpanded = false, isCardExpanded2 = false;
    private Spinner spinner, spinnerAgama;
    private EditText patch_img, tanggal_lahir, namaAwal, namaAkhir, NIK, alamatLengkap, nomorTelfon, email, username, password, usernameLogin, passwordLogin;
    private static final String REGISTER_URL = CONFIG_IP.HTTP + CONFIG_IP.URL_IP + CONFIG_IP.PATH + "register.php";
    private static final String LOGIN_URL =  CONFIG_IP.HTTP + CONFIG_IP.URL_IP + CONFIG_IP.PATH + "login.php";
    Bitmap bitmap;
    private  String nama_pegawai, foto_pegawai;
    ImageView gambar_et;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);

        // todo fungsi untuk ke frame lupa password
        forgotPassword = findViewById(R.id.textForgotPassword);
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ResetPassword.class));
            }
        });

        gambar_et = findViewById(R.id.upload_foto);
        patch_img = findViewById(R.id.patchImage);

        // Fungsi upload gambar
        patch_img = findViewById(R.id.patchImage);

        ActivityResultLauncher<Intent> activityResultLauncher =
                registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            Uri uri = data.getData();
                            try {
                                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                                gambar_et.setImageBitmap(bitmap);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                });

        gambar_et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                activityResultLauncher.launch(intent);
            }
        });
        // todo untuk fungsi register
        namaAwal = findViewById(R.id.namaawalReg);
        namaAkhir = findViewById(R.id.namaakhirReg);
        NIK = findViewById(R.id.nikReg);
        spinner = findViewById(R.id.spinnerRegisterReg);
        tanggal_lahir = findViewById(R.id.tanggallahirReg);
        alamatLengkap = findViewById(R.id.alamatlengkapReg);
        nomorTelfon = findViewById(R.id.nomortelfonReg);
        email = findViewById(R.id.emailReg);
        username = findViewById(R.id.usernameReg);
        password = findViewById(R.id.passwordReg);
        spinnerAgama = findViewById(R.id.agamaReg);
        buttonRegisterApi = findViewById(R.id.registertombolReg);
        fotoPegawaiUploaded = findViewById(R.id.upload_foto);

        // todo : untuk inisialisasi text heading alert info
        buttonRegisterApi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String is_namaawal = namaAwal.getText().toString();
                String is_namaakhir = namaAkhir.getText().toString();
                String is_NIK = NIK.getText().toString();
                String is_jeniskelamin = spinner.getSelectedItem().toString();
                String is_tanggallahir = tanggal_lahir.getText().toString();
                String is_alamat = alamatLengkap.getText().toString();
                String is_notel = nomorTelfon.getText().toString();
                String is_email = email.getText().toString();
                String is_username = username.getText().toString();
                String is_password = password.getText().toString();
                String is_agama = spinnerAgama.getSelectedItem().toString();
                String is_patch_img = patch_img.getText().toString();
                if (is_namaawal.isEmpty()) {
                    showAlertInfoNamaAwal();
                } else if (is_namaawal.length() < 3 || is_namaawal.length() > 20) {
                    showAlertLengthNamaAwal();
                } else if (is_namaakhir.isEmpty()) {
                    showAlertInfoNamaAkhir();
                } else if (is_namaakhir.length() < 3 || is_namaakhir.length() > 20) {
                    showAlertLengthNamaAkhir();
                } else if (is_NIK.isEmpty()) {
                    showAlertNIK();
                } else if (!isNumeric(is_NIK)) {
                    showAlertNIKIsNumeric();
                } else if (is_NIK.length() < 16 || is_NIK.length() > 16) {
                    showAlertNIKLength();
                } else if (is_username.isEmpty()) {
                    showAlertUsername();
                } else if (is_username.length() < 3 || is_username.length() > 20) {
                    showUsernameLength();
                } else if (is_password.isEmpty()) {
                    showAlertPassword();
                } else if (is_password.length() < 8 || is_password.length() > 20) {
                    showAlertPasswordLength();
                } else if (is_notel.isEmpty()) {
                    showAlertNotel();
                } else if (is_notel.length() < 12 || is_notel.length() > 13) {
                    showAlertNotellength();
                } else if (is_email.isEmpty()) {
                    showAlertEmail();
                } else if (is_alamat.isEmpty()) {
                    showAlertAlamat();
                } else {
                    ByteArrayOutputStream byteArrayOutputStream;
                    byteArrayOutputStream = new ByteArrayOutputStream();
                    if (bitmap != null) {
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                        byte[] bytes = byteArrayOutputStream.toByteArray();
                        final String base64 = Base64.encodeToString(bytes, Base64.DEFAULT);
                        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

                        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        showAlertRegisterBerhasil();
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
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
                                params.put("namaawal", namaAwal.getText().toString());
                                params.put("namaakhir", namaAkhir.getText().toString());
                                params.put("nik", NIK.getText().toString());
                                params.put("jeniskelamin", spinner.getSelectedItem().toString());
                                params.put("tanggallahir", tanggal_lahir.getText().toString());
                                params.put("alamat", alamatLengkap.getText().toString());
                                params.put("notel", nomorTelfon.getText().toString());
                                params.put("email", email.getText().toString());
                                params.put("img", base64);
                                params.put("username", username.getText().toString());
                                params.put("password", password.getText().toString());
                                params.put("agama", spinnerAgama.getSelectedItem().toString());
                                return params;
                            }
                        };
                        requestQueue.add(stringRequest);
                    }
                }
            }
        });
        // todo : fungsi login
        usernameLogin = findViewById(R.id.inputUsernameLog);
        passwordLogin = findViewById(R.id.inputPasswordLog);
        btnLogin = findViewById(R.id.loginBtn);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String is_username_log = usernameLogin.getText().toString();
                String is_password_log = passwordLogin.getText().toString();
                if (is_username_log.isEmpty() && is_password_log.isEmpty()) {
                    showAlertUsernamePasswordLog();
                } else if (is_username_log.isEmpty()) {
                    showAlertUsernameLog();
                } else if (is_password_log.isEmpty()) {
                    showAlertPasswordLog();
                } else {
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN_URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("API_RESPONSE", response);
                            try {
                                JSONObject jsonResponse = new JSONObject(response.substring(response.indexOf("{"), response.length()));
                                String status = jsonResponse.getString("status");
                                String message = jsonResponse.getString("message");
                                if (status.equals("Success")) {
                                    nama_pegawai = jsonResponse.getString("nama_pegawai");
                                    foto_pegawai = IMAGES.URL_IMAGES + jsonResponse.getString("foto_pegawai");

                                    // Simpan foto_pegawai ke SharedPreferences
                                    SharedPreferences preferences = getSharedPreferences("user", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.putString("foto_pegawai", foto_pegawai);
                                    editor.apply();

                                    // simpan nama pegawai login
                                    SharedPreferences preferences1 = getSharedPreferences("user_name", MODE_PRIVATE);
                                    SharedPreferences.Editor editor1 = preferences1.edit();
                                    editor1.putString("nama_pegawai", nama_pegawai);
                                    editor1.apply();

                                    // simpan id pegawai
                                    SharedPreferences preferences2 = getSharedPreferences("user_id", MODE_PRIVATE);
                                    SharedPreferences.Editor editor2 = preferences2.edit();
                                    editor2.putString("id_pegawai", jsonResponse.getString("id_pegawai"));
                                    editor2.apply();

                                    showAlertLoginBerhasil();
                                } else {
                                    showAlertLoginGagal();
                                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(getApplicationContext(), "Error parsing JSON", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<>();
                            params.put("username", is_username_log);
                            params.put("password", is_password_log);
                            params.put("hakakses", "Teknisi");
                            return params;
                        }
                    };
                    requestQueue.add(stringRequest);
                }
            }
        });


        // todo fungsi input tanggal lahir
        tanggal_lahir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        // todo Inisialisasi elemen-elemen tampilan dengan mengambil nilai id komponen (deklarasi)
        loginCardView = findViewById(R.id.loginCardView);
        registerCardView = findViewById(R.id.registerCardView);
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);
        relativeLayout = findViewById(R.id.relativeLayout);
        close = findViewById(R.id.closeLogin);
        closeRegister = findViewById(R.id.closeRegister);
        hover = findViewById(R.id.hoverBackground);
        loginText = findViewById(R.id.loginText);
        registerText = findViewById(R.id.registerText);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isCardExpanded) {
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) loginCardView.getLayoutParams();
                    params.bottomMargin = -100;
                    hover.setAlpha(1);
                    loginCardView.setLayoutParams(params);
                    isCardExpanded = true;
                }
                TransitionManager.beginDelayedTransition(relativeLayout);
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCardExpanded) {
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) loginCardView.getLayoutParams();
                    params.bottomMargin = -2000;
                    loginCardView.setLayoutParams(params);
                    hover.setAlpha(0);
                    isCardExpanded = false;
                }
                TransitionManager.beginDelayedTransition(relativeLayout);
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isCardExpanded2) {
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) registerCardView.getLayoutParams();
                    params.bottomMargin = -80;
                    hover.setAlpha(1);
                    registerCardView.setLayoutParams(params);
                    isCardExpanded2 = true;
                }
                TransitionManager.beginDelayedTransition(relativeLayout);
            }
        });

        closeRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCardExpanded2) {
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) registerCardView.getLayoutParams();
                    params.bottomMargin = -2000;
                    registerCardView.setLayoutParams(params);
                    hover.setAlpha(0);
                    isCardExpanded2 = false;
                }
                TransitionManager.beginDelayedTransition(relativeLayout);
            }
        });


        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isCardExpanded) {
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) loginCardView.getLayoutParams();
                    params.bottomMargin = -100;
                    hover.setAlpha(1);
                    loginCardView.setLayoutParams(params);
                    isCardExpanded = true;
                }
                if (isCardExpanded2) {
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) registerCardView.getLayoutParams();
                    params.bottomMargin = -2000;
                    registerCardView.setLayoutParams(params);
                    hover.setAlpha(1);
                    isCardExpanded2 = false;
                }
                TransitionManager.beginDelayedTransition(relativeLayout);
            }
        });
        registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isCardExpanded2) {
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) registerCardView.getLayoutParams();
                    params.bottomMargin = -80;
                    hover.setAlpha(1);
                    registerCardView.setLayoutParams(params);
                    isCardExpanded2 = true;
                }
                if (isCardExpanded) {
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) loginCardView.getLayoutParams();
                    params.bottomMargin = -2000;
                    loginCardView.setLayoutParams(params);
                    hover.setAlpha(1);
                    isCardExpanded = false;
                }
                TransitionManager.beginDelayedTransition(relativeLayout);
            }
        });


        // todo : Inisialisasi Spinner untuk pilihan gender
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        adapter.add("Laki-Laki");
        adapter.add("Perempuan");
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // todo : Inisialisasi Spinner untuk pilihan agama
        ArrayAdapter<String> adapterAgama = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        adapterAgama.add("Islam");
        adapterAgama.add("Kristen");
        adapterAgama.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAgama.setAdapter(adapterAgama);
    }

    // todo function kolom tanggal lahir
    private void showDatePickerDialog() {
        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);

        // Buat dan tampilkan DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(LoginRegister.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Tetapkan nilai yang dipilih ke EditText dengan format tahun/bulan/hari
                        String formattedDate = String.format(Locale.getDefault(), "%04d/%02d/%02d", year, monthOfYear + 1, dayOfMonth);
                        tanggal_lahir.setText(formattedDate);
                    }
                }, year, month, day);
        datePickerDialog.show();
    }

    // todo : fungsi cek numeric nik
    private boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }

    // todo : fungsi untuk menampilkan dialog alert info register
    public void showAlertInfoNamaAwal() {
        View dialogView = LayoutInflater.from(LoginRegister.this).inflate(R.layout.alert_info_namaawal_reg, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginRegister.this);
        builder.setView(dialogView);

        BTN_OK = (TextView) dialogView.findViewById(R.id.btndone_alert_info);

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

    public void showAlertLengthNamaAwal() {
        View dialogView = LayoutInflater.from(LoginRegister.this).inflate(R.layout.alert_info_namaawallength_reg, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginRegister.this);
        builder.setView(dialogView);

        BTN_OK = (TextView) dialogView.findViewById(R.id.btndone_alert_namaawallength);

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

    public void showAlertInfoNamaAkhir() {
        View dialogView = LayoutInflater.from(LoginRegister.this).inflate(R.layout.alert_info_namaakhir_reg, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginRegister.this);
        builder.setView(dialogView);

        BTN_OK = (TextView) dialogView.findViewById(R.id.btndone_alert_info_namaakhir_reg);

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

    public void showAlertLengthNamaAkhir() {
        View dialogView = LayoutInflater.from(LoginRegister.this).inflate(R.layout.alert_info_namaakhirlength_reg, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginRegister.this);
        builder.setView(dialogView);

        BTN_OK = (TextView) dialogView.findViewById(R.id.btndone_alert_info_namaakhirlength_reg);

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

    public void showAlertNIK() {
        View dialogView = LayoutInflater.from(LoginRegister.this).inflate(R.layout.alert_info_nik_reg, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginRegister.this);
        builder.setView(dialogView);

        BTN_OK = (TextView) dialogView.findViewById(R.id.btndone_alert_info_nik_reg);

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

    public void showAlertNIKIsNumeric() {
        View dialogView = LayoutInflater.from(LoginRegister.this).inflate(R.layout.alert_info_niknumeric_reg, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginRegister.this);
        builder.setView(dialogView);

        BTN_OK = (TextView) dialogView.findViewById(R.id.btndone_alert_info_niknumeric_reg);

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

    public void showAlertNIKLength() {
        View dialogView = LayoutInflater.from(LoginRegister.this).inflate(R.layout.alert_info_niklength_reg, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginRegister.this);
        builder.setView(dialogView);

        BTN_OK = (TextView) dialogView.findViewById(R.id.btndone_alert_info_niklength_reg);

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

    public void showAlertUsername() {
        View dialogView = LayoutInflater.from(LoginRegister.this).inflate(R.layout.alert_info_username_reg, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginRegister.this);
        builder.setView(dialogView);

        BTN_OK = (TextView) dialogView.findViewById(R.id.btndone_alert_info_username_reg);

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

    public void showUsernameLength() {
        View dialogView = LayoutInflater.from(LoginRegister.this).inflate(R.layout.alert_info_usernamelength_reg, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginRegister.this);
        builder.setView(dialogView);

        BTN_OK = (TextView) dialogView.findViewById(R.id.btndone_alert_info_usernamelength_reg);

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

    public void showAlertPassword() {
        View dialogView = LayoutInflater.from(LoginRegister.this).inflate(R.layout.alert_info_password_reg, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginRegister.this);
        builder.setView(dialogView);

        BTN_OK = (TextView) dialogView.findViewById(R.id.btndone_alert_info_password_reg);

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

    public void showAlertPasswordLength() {
        View dialogView = LayoutInflater.from(LoginRegister.this).inflate(R.layout.alert_info_passwordlength_reg, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginRegister.this);
        builder.setView(dialogView);

        BTN_OK = (TextView) dialogView.findViewById(R.id.btndone_alert_info_passwordlength_reg);

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

    public void showAlertNotel() {
        View dialogView = LayoutInflater.from(LoginRegister.this).inflate(R.layout.alert_info_notel_reg, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginRegister.this);
        builder.setView(dialogView);

        BTN_OK = (TextView) dialogView.findViewById(R.id.btndone_alert_info_notel_reg);

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

    public void showAlertNotellength() {
        View dialogView = LayoutInflater.from(LoginRegister.this).inflate(R.layout.alert_info_notellength_reg, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginRegister.this);
        builder.setView(dialogView);

        BTN_OK = (TextView) dialogView.findViewById(R.id.btndone_alert_info_notellength_reg);

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

    public void showAlertEmail() {
        View dialogView = LayoutInflater.from(LoginRegister.this).inflate(R.layout.alert_info_email_reg, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginRegister.this);
        builder.setView(dialogView);

        BTN_OK = (TextView) dialogView.findViewById(R.id.btndone_alert_info_email_reg);

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

    public void showAlertAlamat() {
        View dialogView = LayoutInflater.from(LoginRegister.this).inflate(R.layout.alert_info_alamat_reg, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginRegister.this);
        builder.setView(dialogView);

        BTN_OK = (TextView) dialogView.findViewById(R.id.btndone_alert_info_alamat_reg);

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

    public void showAlertRegisterBerhasil() {
        View dialogView = LayoutInflater.from(LoginRegister.this).inflate(R.layout.alert_info_register_berhasil, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginRegister.this);
        builder.setView(dialogView);

        BTN_OK = (TextView) dialogView.findViewById(R.id.btndone_alert_register_berhasil);

        final AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();

        BTN_OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
                startActivity(new Intent(getApplicationContext(), LoginRegister.class));
            }
        });
    }

    // todo : fungsi alert untuk login
    public void showAlertUsernameLog() {
        View dialogView = LayoutInflater.from(LoginRegister.this).inflate(R.layout.alert_info_username_log, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginRegister.this);
        builder.setView(dialogView);

        BTN_OK = (TextView) dialogView.findViewById(R.id.btndone_alert_info_username_log);

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

    public void showAlertPasswordLog() {
        View dialogView = LayoutInflater.from(LoginRegister.this).inflate(R.layout.alert_info_password_log, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginRegister.this);
        builder.setView(dialogView);

        BTN_OK = (TextView) dialogView.findViewById(R.id.btndone_alert_info_password_log);

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

    public void showAlertUsernamePasswordLog() {
        View dialogView = LayoutInflater.from(LoginRegister.this).inflate(R.layout.alert_info_usernamepassword_log, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginRegister.this);
        builder.setView(dialogView);

        BTN_OK = (TextView) dialogView.findViewById(R.id.btndone_alert_info_usernamepassword_log);

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

    public void showAlertLoginBerhasil() {
        View dialogView = LayoutInflater.from(LoginRegister.this).inflate(R.layout.alert_info_loginberhasil, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginRegister.this);
        builder.setView(dialogView);

        BTN_OK = (TextView) dialogView.findViewById(R.id.btndone_alert_info_loginberhasil);

        final AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();

        BTN_OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
                Intent intent = new Intent(getApplicationContext(), MainApplication.class);
                intent.putExtra("nama_pegawai", nama_pegawai);
                intent.putExtra("foto_pegawai", foto_pegawai);
                startActivity(intent);
            }
        });
    }

    public void showAlertLoginGagal() {
        View dialogView = LayoutInflater.from(LoginRegister.this).inflate(R.layout.alert_info_logingagal, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginRegister.this);
        builder.setView(dialogView);

        BTN_OK = (TextView) dialogView.findViewById(R.id.btndone_alert_info_loginbergagal);

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

    // Method to get path from URI
    private String getPathFromUri(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String imagePath = cursor.getString(column_index);
        cursor.close();
        return imagePath;
    }
}
