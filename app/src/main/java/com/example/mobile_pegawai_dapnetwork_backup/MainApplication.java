package com.example.mobile_pegawai_dapnetwork_backup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.mobile_pegawai_dapnetwork_backup.classes.CONFIG_IP;
import com.example.mobile_pegawai_dapnetwork_backup.classes.IMAGES;
import com.example.mobile_pegawai_dapnetwork_backup.databinding.ActivityMainApplicationBinding;
import com.example.mobile_pegawai_dapnetwork_backup.fragment.Client;
import com.example.mobile_pegawai_dapnetwork_backup.fragment.Dashboard;
import com.example.mobile_pegawai_dapnetwork_backup.fragment.Help;
import com.example.mobile_pegawai_dapnetwork_backup.fragment.Pemesanan;
import com.example.mobile_pegawai_dapnetwork_backup.fragment.Penagihan;
import com.example.mobile_pegawai_dapnetwork_backup.fragment.Schedule;
import com.example.mobile_pegawai_dapnetwork_backup.fragment.Transaction;
import com.example.mobile_pegawai_dapnetwork_backup.fragment.User;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainApplication extends AppCompatActivity {

    ActivityMainApplicationBinding binding;
    private int defaultHomeIcon = R.drawable.icon_home_bottomnav;
    private int defaultClientIcon = R.drawable.icon_transaction;
    private int defaultScheduleIcon = R.drawable.icon_schedule_bottomnav;
    private int defaultHelpIcon = R.drawable.icon_pemesanan;
    private int defaultUserIcon = R.drawable.icon_user_bottomnav;
    private View overlaySidebar;
    private RelativeLayout wrapperSidebar;
    private ImageView barmenuSidebar, closeSidebar, fotoProfileNav;
    private boolean sidebarExpanded = false;
    String fotoPegawai;
    EditText sharedNamaPegawai;
    public TextView judulHalaman;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainApplicationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ReplaceFragment(new Dashboard());

        // variable judul halaman
        judulHalaman = findViewById(R.id.judulHalaman);

        // todo untuk animasi navigation
        RelativeLayout nav = findViewById(R.id.navigation);
        nav.post(new Runnable() {
            @Override
            public void run() {
                // Atur translasi awal di luar layar
                nav.setAlpha(0);
                // Animasi translasi masuk
                ObjectAnimator translationXAnimator = ObjectAnimator.ofFloat(nav, "translationY", 0f);
                translationXAnimator.setDuration(1500); // Durasi animasi dalam milidetik
                translationXAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
                translationXAnimator.start();

                // Animasi mengubah opacity
                ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(nav, "alpha", 1f);
                alphaAnimator.setDuration(2000); // Durasi animasi dalam milidetik
                alphaAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
                alphaAnimator.start();
            }
        });

        // todo untuk animasi  bottom nav
        CardView bottomNav = findViewById(R.id.assets_bottom_nav);
        bottomNav.post(new Runnable() {
            @Override
            public void run() {
                // Atur translasi awal di luar layar
                bottomNav.setAlpha(0);
                // Animasi translasi masuk
                ObjectAnimator translationXAnimator = ObjectAnimator.ofFloat(bottomNav, "translationY", 0f);
                translationXAnimator.setDuration(1500); // Durasi animasi dalam milidetik
                translationXAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
                translationXAnimator.start();

                // Animasi mengubah opacity
                ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(bottomNav, "alpha", 1f);
                alphaAnimator.setDuration(2000); // Durasi animasi dalam milidetik
                alphaAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
                alphaAnimator.start();
            }
        });

        // todo agar foto profile nav dapat di klik
        fotoProfileNav = findViewById(R.id.foto_profile_nav);
        fotoProfileNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainApplication.this, ProfilePegawai.class);
                startActivity(intent);
            }
        });

        // todo set foto pegawai dengan glide yang diambil dari shared preferense
        SharedPreferences preferencesFoto = getSharedPreferences("user", MODE_PRIVATE);
        fotoPegawai = preferencesFoto.getString("foto_pegawai", null);
        if (fotoPegawai != null) {
            fotoProfileNav = findViewById(R.id.foto_profile_nav);
            Glide.with(this)
                    .load(fotoPegawai)
                    .into(fotoProfileNav);
        }

        // todo : logika klik bottom navigation
        binding.bottomNav.setOnItemSelectedListener(item -> {
            resetAllIcons(binding.bottomNav.getMenu());
            if (item.getItemId() == R.id.home) {
                item.setIcon(R.drawable.icon_home_bottomnav_checked);
                ReplaceFragment(new Dashboard());
                judulHalaman.setText("Dashboard");
            } else if (item.getItemId() == R.id.client) {
                item.setIcon(R.drawable.icon_transaction_checked);
                ReplaceFragment(new Transaction());
                judulHalaman.setText("Transaksi");
            } else if (item.getItemId() == R.id.schedule) {
                item.setIcon(R.drawable.icon_schedule_bottomnav_checked);
                ReplaceFragment(new Penagihan());
                judulHalaman.setText("Penagihan");
            } else if (item.getItemId() == R.id.pemesanan) {
                item.setIcon(R.drawable.icon_pemesanan_checked);
                ReplaceFragment(new Pemesanan());
                judulHalaman.setText("Pemesanan");
            } else if (item.getItemId() == R.id.user) {
                item.setIcon(R.drawable.icon_user_bottomnav_checked);
                Intent intent = new Intent(MainApplication.this, ProfilePegawai.class);
                startActivity(intent);
                judulHalaman.setText("Profile");
            }
            return true;
        });

        // todo : fungsi barmenu sidebar
        barmenuSidebar = findViewById(R.id.barmenu);
        overlaySidebar = findViewById(R.id.overlay_sidebar);
        wrapperSidebar = findViewById(R.id.wrapper_sidebar);
        closeSidebar = findViewById(R.id.close_sidebar);
        barmenuSidebar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!sidebarExpanded) {
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) wrapperSidebar.getLayoutParams();
                    params.leftMargin = 0;
                    overlaySidebar.setVisibility(View.VISIBLE);
                    wrapperSidebar.setLayoutParams(params);
                    sidebarExpanded = true;
                }
                TransitionManager.beginDelayedTransition(wrapperSidebar);
            }
        });
        closeSidebar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (sidebarExpanded) {
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) wrapperSidebar.getLayoutParams();
                    params.leftMargin = -1000;
                    overlaySidebar.setVisibility(View.GONE);
                    wrapperSidebar.setLayoutParams(params);
                    sidebarExpanded = false;
                }
                TransitionManager.beginDelayedTransition(wrapperSidebar);
            }
        });

        // todo fungsi sidebar button
        LinearLayout maHome = findViewById(R.id.ma_home);
        LinearLayout maPelangganBaru = findViewById(R.id.ma_pelanggan_baru);
        LinearLayout maPenagihan = findViewById(R.id.ma_penagihan);
        LinearLayout maPemesanan = findViewById(R.id.ma_pemesanan);
        LinearLayout maProfile = findViewById(R.id.ma_profile);

        maHome.setOnClickListener(v -> {
            startActivity(new Intent(MainApplication.this, MainApplication.class));
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) wrapperSidebar.getLayoutParams();
            layoutParams.leftMargin = 2000;
            wrapperSidebar.setLayoutParams(layoutParams);
            overlaySidebar.setVisibility(View.GONE);
            sidebarExpanded = false;
            judulHalaman.setText("Dashboard");
        });

        maPelangganBaru.setOnClickListener(v -> {
            ReplaceFragment(new Transaction());
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) wrapperSidebar.getLayoutParams();
            layoutParams.leftMargin = -2000;
            wrapperSidebar.setLayoutParams(layoutParams);
            overlaySidebar.setVisibility(View.GONE);
            sidebarExpanded = false;
            judulHalaman.setText("Transaksi");
        });

        maPenagihan.setOnClickListener(v -> {
            ReplaceFragment(new Penagihan());
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) wrapperSidebar.getLayoutParams();
            layoutParams.leftMargin = -2000;
            wrapperSidebar.setLayoutParams(layoutParams);
            overlaySidebar.setVisibility(View.GONE);
            sidebarExpanded = false;
            judulHalaman.setText("Penagihan");
        });

        maPemesanan.setOnClickListener(v -> {
            ReplaceFragment(new Pemesanan());
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) wrapperSidebar.getLayoutParams();
            layoutParams.leftMargin = -2000;
            wrapperSidebar.setLayoutParams(layoutParams);
            overlaySidebar.setVisibility(View.GONE);
            sidebarExpanded = false;
            judulHalaman.setText("Pemesanan");
        });

        maProfile.setOnClickListener(v -> {
            startActivity(new Intent(MainApplication.this, ProfilePegawai.class));
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) wrapperSidebar.getLayoutParams();
            layoutParams.leftMargin = -2000;
            wrapperSidebar.setLayoutParams(layoutParams);
            overlaySidebar.setVisibility(View.GONE);
            sidebarExpanded = false;
        });
    }

    private void getSharedData(String nama) {
        String URL = CONFIG_IP.HTTP + CONFIG_IP.URL_IP + CONFIG_IP.PATH + "getshared.php?id=" + nama;
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
                        fotoPegawai = IMAGES.URL_IMAGES + dataPegawai.getString("foto_pegawai");
                        Glide.with(MainApplication.this).load(fotoPegawai).into(fotoProfileNav);
                    }
                } catch (Exception error) {
                    error.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }

    // todo : fungsi logic menu icon bottom navigation
    private void resetAllIcons(Menu menu) {
        for (int i = 0; i < menu.size(); i++) {
            MenuItem menuItem = menu.getItem(i);
            int defaultIcon = getDefaultIconForMenuItem(menuItem.getItemId());
            menuItem.setIcon(defaultIcon);
        }
    }

    // todo : fungsi get icon ketika di klik menu
    private int getDefaultIconForMenuItem(int menuItemId) {
        if (menuItemId == R.id.home) {
            return defaultHomeIcon;
        } else if (menuItemId == R.id.client) {
            return defaultClientIcon;
        } else if (menuItemId == R.id.schedule) {
            return defaultScheduleIcon;
        } else if (menuItemId == R.id.pemesanan) {
            return defaultHelpIcon;
        } else if (menuItemId == R.id.user) {
            return defaultUserIcon;
        }
        return 0;
    }

    // todo : fungsi untuk replace fragment ketika di klik menu
    private void ReplaceFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.assets_frame_layout, fragment)
                .commit();
    }
}