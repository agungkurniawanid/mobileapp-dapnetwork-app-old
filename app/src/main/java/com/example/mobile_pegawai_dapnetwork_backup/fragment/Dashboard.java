package com.example.mobile_pegawai_dapnetwork_backup.fragment;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.mobile_pegawai_dapnetwork_backup.FiturPemesanan;
import com.example.mobile_pegawai_dapnetwork_backup.FiturPenagihan;
import com.example.mobile_pegawai_dapnetwork_backup.FiturTransaksi;
import com.example.mobile_pegawai_dapnetwork_backup.MainApplication;
import com.example.mobile_pegawai_dapnetwork_backup.PaketLayanan;
import com.example.mobile_pegawai_dapnetwork_backup.ProfilePegawai;
import com.example.mobile_pegawai_dapnetwork_backup.R;
import com.example.mobile_pegawai_dapnetwork_backup.adapter.AdapterDashboardFix;
import com.example.mobile_pegawai_dapnetwork_backup.classes.CONFIG_IP;
import com.example.mobile_pegawai_dapnetwork_backup.model.ModelDashboardFix;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Dashboard extends Fragment {

    RecyclerView card_information;
    EditText sharedNamaPegawai;
    TextView namaUser, btnCekFiturTransaksi;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        // todo untuk animasi text
        TextView textFiturLainnya = view.findViewById(R.id.judul_informasi);
        TextView tanyaKabar = view.findViewById(R.id.assets_title_tanya_kabar);
        TextView sapa = view.findViewById(R.id.assets_title_sapa);
        namaUser = view.findViewById(R.id.assetes_nama_user_login);
        btnCekFiturTransaksi = view.findViewById(R.id.btn_cek_penagihan);

        // todo fungsi beralih ke halaman fitur transaksi
        btnCekFiturTransaksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FiturPenagihan.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        sapa.post(new Runnable() {
            @Override
            public void run() {
                // Atur translasi awal di luar layar
                sapa.setAlpha(0);

                // Animasi translasi masuk
                ObjectAnimator translationXAnimator = ObjectAnimator.ofFloat(sapa, "translationY", 0f);
                translationXAnimator.setDuration(1500); // Durasi animasi dalam milidetik
                translationXAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
                translationXAnimator.start();

                // Animasi mengubah opacity
                ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(sapa, "alpha", 1f);
                alphaAnimator.setDuration(2000); // Durasi animasi dalam milidetik
                alphaAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
                alphaAnimator.start();
            }
        });

        namaUser.post(new Runnable() {
            @Override
            public void run() {
                // Atur translasi awal di luar layar
                namaUser.setAlpha(0);

                // Animasi translasi masuk
                ObjectAnimator translationXAnimator = ObjectAnimator.ofFloat(namaUser, "translationY", 0f);
                translationXAnimator.setDuration(1500); // Durasi animasi dalam milidetik
                translationXAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
                translationXAnimator.start();

                // Animasi mengubah opacity
                ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(namaUser, "alpha", 1f);
                alphaAnimator.setDuration(2000); // Durasi animasi dalam milidetik
                alphaAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
                alphaAnimator.start();
            }
        });

        tanyaKabar.post(new Runnable() {
            @Override
            public void run() {
                // Atur translasi awal di luar layar
                tanyaKabar.setAlpha(0);
                // Animasi translasi masuk
                ObjectAnimator translationXAnimator = ObjectAnimator.ofFloat(tanyaKabar, "translationX", 0f);
                translationXAnimator.setDuration(1500); // Durasi animasi dalam milidetik
                translationXAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
                translationXAnimator.start();

                // Animasi mengubah opacity
                ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(tanyaKabar, "alpha", 1f);
                alphaAnimator.setDuration(2000); // Durasi animasi dalam milidetik
                alphaAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
                alphaAnimator.start();
            }
        });

        textFiturLainnya.post(new Runnable() {
            @Override
            public void run() {
                // Atur translasi awal di luar layar
                textFiturLainnya.setAlpha(0);
                // Animasi translasi masuk
                ObjectAnimator translationXAnimator = ObjectAnimator.ofFloat(textFiturLainnya, "translationX", 0f);
                translationXAnimator.setDuration(1500); // Durasi animasi dalam milidetik
                translationXAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
                translationXAnimator.start();

                // Animasi mengubah opacity
                ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(textFiturLainnya, "alpha", 1f);
                alphaAnimator.setDuration(2000); // Durasi animasi dalam milidetik
                alphaAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
                alphaAnimator.start();
            }
        });

        // todo fungsi untuk set nama pegawai login
        sharedNamaPegawai = view.findViewById(R.id.shared_nama_pegawai_db);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user_name", Context.MODE_PRIVATE);
        String namaPegawai = sharedPreferences.getString("nama_pegawai", "");
        sharedNamaPegawai.setText(namaPegawai);
        getSharedData(namaPegawai);


        // todo untuk animasi card utama
        RelativeLayout cardUtama = view.findViewById(R.id.card_utama);
        cardUtama.post(new Runnable() {
            @Override
            public void run() {
                // Atur translasi awal di luar layar
                cardUtama.setAlpha(0);

                // Animasi translasi masuk
                ObjectAnimator translationXAnimator = ObjectAnimator.ofFloat(cardUtama, "translationX", 0f);
                translationXAnimator.setDuration(1500); // Durasi animasi dalam milidetik
                translationXAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
                translationXAnimator.start();

                // Animasi mengubah opacity
                ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(cardUtama, "alpha", 1f);
                alphaAnimator.setDuration(2000); // Durasi animasi dalam milidetik
                alphaAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
                alphaAnimator.start();
            }
        });

        // todo : untuk set card information
        card_information = view.findViewById(R.id.card_information_recycleview);
        setCardInformation();

        card_information.post(new Runnable() {
            @Override
            public void run() {
                // Atur translasi awal di luar layar
                card_information.setAlpha(0);

                // Animasi translasi masuk
                ObjectAnimator translationXAnimator = ObjectAnimator.ofFloat(card_information, "translationX", 0f);
                translationXAnimator.setDuration(1500); // Durasi animasi dalam milidetik
                translationXAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
                translationXAnimator.start();

                // Animasi mengubah opacity
                ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(card_information, "alpha", 1f);
                alphaAnimator.setDuration(2000); // Durasi animasi dalam milidetik
                alphaAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
                alphaAnimator.start();
            }
        });

        return view;
    }

    private void setCardInformation() {
        try {
            List<ModelDashboardFix> modelDashboardFixes = new ArrayList<>();
            modelDashboardFixes.add(new ModelDashboardFix(R.drawable.image_transaksi, "Fitur Transaksi", "Client Baru", "Lihat cara kerja fitur!"));
            modelDashboardFixes.add(new ModelDashboardFix(R.drawable.illustration_pembatalan, "Fitur Pemesanan", "Client Baru", "Fitur penting saat instalasi pada client!"));
            modelDashboardFixes.add(new ModelDashboardFix(R.drawable.image_halaman_profile, "Data Profile", "Pribadi Anda", "Edit dan ubah data profile jika perlu!"));
            modelDashboardFixes.add(new ModelDashboardFix(R.drawable.image_layanan, "Fitur List", "Layanan Tersedia", "Lihat untuk update layanan tersedia!"));

            int numberOfColumns = 2;
            GridLayoutManager layoutManager = new GridLayoutManager(getContext(), numberOfColumns);
            card_information.setLayoutManager(layoutManager);

            AdapterDashboardFix adapter = new AdapterDashboardFix(getContext(), modelDashboardFixes, new AdapterDashboardFix.ItemClickListener() {
                @Override
                public void onItemClick(ModelDashboardFix modelDashboardFix) {
                    Intent intent;
                    if (modelDashboardFix.mdl_cardtext_dashboard_fix_text1 == "Fitur Transaksi") {
                        intent = new Intent(getContext(), FiturTransaksi.class);
                        startActivity(intent);
                    } else if (modelDashboardFix.mdl_cardtext_dashboard_fix_text1 == "Fitur Pemesanan") {
                        intent = new Intent(getContext(), FiturPemesanan.class);
                        startActivity(intent);
                    } else if (modelDashboardFix.mdl_cardtext_dashboard_fix_text1 == "Fitur List") {
                        intent = new Intent(getContext(), PaketLayanan.class);
                        startActivity(intent);
                    } else if (modelDashboardFix.mdl_cardtext_dashboard_fix_text1 == "Data Profile") {
                        intent = new Intent(getContext(), ProfilePegawai.class);
                        startActivity(intent);
                    }
                }
            });
            card_information.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getSharedData(String nama) {
        String URL = CONFIG_IP.HTTP + CONFIG_IP.URL_IP + CONFIG_IP.PATH + "getshared.php?id=" + nama;
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.substring(response.indexOf("{"), response.length()));
                    String status = jsonObject.getString("status");
                    if (status.equals("success")) {
                        JSONArray data = jsonObject.getJSONArray("data");
                        JSONObject dataPegawai = data.getJSONObject(0);
                        namaUser.setText(dataPegawai.getString("namaawal") + " " + dataPegawai.getString("namaakhir"));
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
}